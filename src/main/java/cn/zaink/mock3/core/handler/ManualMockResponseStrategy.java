package cn.zaink.mock3.core.handler;

import cn.hutool.core.lang.Assert;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.zaink.mock3.application.consts.ResponseType;
import cn.zaink.mock3.application.dto.HttpHeadersDto;
import cn.zaink.mock3.application.dto.ResponseDto;
import cn.zaink.mock3.application.dto.ResponseQry;
import cn.zaink.mock3.application.service.ResponseService;
import cn.zaink.mock3.core.exception.BizException;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class ManualMockResponseStrategy implements MockResponseStrategy {
    private final ResponseService responseService;

    public ManualMockResponseStrategy(ResponseService responseService) {
        this.responseService = responseService;
    }

    @Override
    public void doMockResponse(MockUrl url, HttpServletRequest request, HttpServletResponse response) {
        Long id = url.getId();
        IPage<ResponseDto> page = responseService.find(ResponseQry.builder()
                .urlId(id).enable(true)
                .current(1).size(Integer.MAX_VALUE)
                .build());
        Assert.notEmpty(page.getRecords(), () -> new BizException(404, "系统存在该路径，但是还没有启用一个返回体"));
        String requestMethodStr = request.getMethod();
        final HttpMethod requestHttpMethod = HttpMethod.resolve(requestMethodStr);
        ResponseDto mockResponse = page.getRecords()
                .stream()
                .filter(r -> requestHttpMethod == r.getHttpMethod())
                .findAny()
                .orElse(null);
        Assert.notNull(mockResponse, () -> new BizException(HttpStatus.METHOD_NOT_ALLOWED));
        assert mockResponse != null;

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
    }

    @Override
    public ResponseType type() {
        return ResponseType.MANUAL;
    }
}
