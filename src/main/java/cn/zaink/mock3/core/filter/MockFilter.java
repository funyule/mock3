package cn.zaink.mock3.core.filter;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.zaink.mock3.application.consts.ResponseType;
import cn.zaink.mock3.core.exception.BizException;
import cn.zaink.mock3.core.handler.MockResponseStrategyFactory;
import cn.zaink.mock3.core.handler.UrlHandlerChain;
import cn.zaink.mock3.core.pojo.Result;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author zaink
 **/
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(value = "/fake/*", filterName = "MockFilter")
public class MockFilter implements Filter {

    private final ObjectMapper objectMapper;

    public MockFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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
            MockResponseStrategyFactory.get(responseType).doMockResponse(mockUrl,request,response);
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
}
