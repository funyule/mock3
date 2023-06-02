package cn.zaink.mock3.core.filter;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.json.JSONObject;
import cn.zaink.mock3.application.consts.ResponseType;
import cn.zaink.mock3.application.dto.HttpHeadersDto;
import cn.zaink.mock3.application.dto.ResponseDto;
import cn.zaink.mock3.core.event.MockUseEvent;
import cn.zaink.mock3.core.exception.BizException;
import cn.zaink.mock3.core.handler.MockResponseStrategyFactory;
import cn.zaink.mock3.core.handler.UrlHandlerChain;
import cn.zaink.mock3.core.pojo.Result;
import cn.zaink.mock3.infrastructure.domain.MockLog;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author zaink
 **/
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(value = "/fake/*", filterName = "MockFilter")
public class MockFilter implements Filter {

    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher eventPublisher;

    public MockFilter(ObjectMapper objectMapper, ApplicationEventPublisher eventPublisher) {
        this.objectMapper = objectMapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("MockFilter started");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ServletUtil.setHeader(response, HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setCharacterEncoding("UTF-8");

        String requestUriWithPrefix = request.getRequestURI();
        String requestUri = StrUtil.blankToDefault(StrUtil.removePrefix(requestUriWithPrefix, "/fake"), StrUtil.SLASH);
        String accept = ServletUtil.getHeader(request, Header.ACCEPT.name(), Charset.defaultCharset());
        if (log.isTraceEnabled()) {
            log.trace("requestUri: {}, accept: {}", requestUri, accept);
        }
        try {
            MockUrl mockUrl = UrlHandlerChain.getByUri(requestUri);
            assert null != mockUrl;

            ResponseType responseType = ResponseType.byCode(mockUrl.getResponseType());
            ResponseDto mockResponse = MockResponseStrategyFactory.get(responseType).doMockResponse(mockUrl, request);
            String contentType = response.getContentType();
            if (isBlank(contentType)) {
                contentType = ContentType.JSON.getValue();
            }
            if (CollectionUtils.isNotEmpty(mockResponse.getCustomHeader())) {

                for (HttpHeadersDto customHeader : mockResponse.getCustomHeader()) {
                    response.addHeader(customHeader.getName(), customHeader.getValue());
                }
            }
            ServletUtil.write(response, mockResponse.getContent(), contentType);

            eventPublisher.publishEvent(new MockUseEvent(MockLog.builder()
                    .id(IdWorker.getId()).createTime(LocalDateTime.now())
                    .requestUrl(request.getRequestURI())
                    .requestMethod(request.getMethod())
                    .requestIp(ServletUtil.getClientIP(request))
                    .responseHeader(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockResponse.getCustomHeader()))
                    .responseBody(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mockResponse.getContent()))
                    .build()));
        } catch (BizException e) {
            String eMessage = e.getMessage();
            if (log.isTraceEnabled()) {
                log.trace("{}", eMessage);
            }
            String value = objectMapper.writeValueAsString(Result.fail(e.getCode(), eMessage));
            ServletUtil.write(response, IOUtils.toInputStream(value, Charset.defaultCharset()), ContentType.JSON.getValue());
        } catch (RuntimeException e) {
            if (log.isErrorEnabled()) {
                log.error("系统错误", e);
            }
            String message = ExceptionUtil.getRootCauseMessage(e);
            String value = objectMapper.writeValueAsString(Result.fail(message));
            ServletUtil.write(response, IOUtils.toInputStream(value, Charset.defaultCharset()), ContentType.JSON.getValue());
        }
    }

    @SneakyThrows(Throwable.class)
    private JSONObject requestDetail(HttpServletRequest request) {
        Map<String, String> headerMap = ServletUtil.getHeaderMap(request);
        Map<String, String> paramMap = ServletUtil.getParamMap(request);
        return new JSONObject().set("headers", headerMap).set("params", paramMap);
    }
}
