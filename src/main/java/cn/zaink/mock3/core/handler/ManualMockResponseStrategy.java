package cn.zaink.mock3.core.handler;

import cn.hutool.core.lang.Assert;
import cn.zaink.mock3.application.consts.ResponseType;
import cn.zaink.mock3.application.dto.ResponseDto;
import cn.zaink.mock3.application.dto.ResponseQry;
import cn.zaink.mock3.application.service.ResponseService;
import cn.zaink.mock3.core.exception.BizException;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseDto doMockResponse(MockUrl url, HttpServletRequest request) {
        Long id = url.getId();
        IPage<ResponseDto> page = responseService.find(ResponseQry.builder()
                .urlId(id).enable(true)
                .current(1L).size(Long.MAX_VALUE)
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
        return mockResponse;
    }

    @Override
    public ResponseType type() {
        return ResponseType.MANUAL;
    }
}
