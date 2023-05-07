package cn.zaink.mock3.core.handler;

import cn.hutool.core.lang.Assert;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.zaink.mock3.application.consts.MainEnum;
import cn.zaink.mock3.application.consts.ResponseType;
import cn.zaink.mock3.core.exception.BizException;
import cn.zaink.mock3.infrastructure.domain.MockResponse;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import cn.zaink.mock3.infrastructure.service.MockResponseService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class ManualMockResponseStrategy implements MockResponseStrategy {
    private final MockResponseService responseService;
    private final ObjectMapper objectMapper;

    public ManualMockResponseStrategy(MockResponseService responseService, ObjectMapper objectMapper) {
        this.responseService = responseService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doMockResponse(MockUrl url, HttpServletRequest request, HttpServletResponse response) {
        Long id = url.getId();
        MockResponse mockResponse = responseService.getOne(Wrappers.<MockResponse>lambdaQuery()
                .eq(MockResponse::getUrlId, id)
                .eq(MockResponse::getMain, MainEnum.ENABLE.getCode()), false);
        if (null == mockResponse) {
            throw new BizException(404, "系统存在该路径，但是还没有启用一个返回体");
        }
        String requestMethod = request.getMethod();
        Assert.isTrue(equalsAnyIgnoreCase(requestMethod, mockResponse.getMethod()), () -> new BizException(HttpStatus.METHOD_NOT_ALLOWED));

        String contentType = response.getContentType();
        if (isBlank(contentType)) {
            contentType = ContentType.JSON.getValue();
        }
        if (isNoneBlank(mockResponse.getCustomHeader())) {
            JavaType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class);
            try {
                Map<String, String> mapHeader = objectMapper.readValue(mockResponse.getCustomHeader(), mapType);
                mapHeader.forEach(response::addHeader);
            } catch (JsonProcessingException e) {
                throw new BizException(e, "处理自定义header异常");
            }
        }
        ServletUtil.write(response, mockResponse.getContent(), contentType);
    }

    @Override
    public ResponseType type() {
        return ResponseType.MANUAL;
    }
}
