package cn.zaink.mock3.core.handler;

import cn.zaink.mock3.application.consts.ResponseType;
import cn.zaink.mock3.application.dto.ResponseDto;
import cn.zaink.mock3.infrastructure.domain.MockUrl;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zaink
 **/
public interface MockResponseStrategy {

    ResponseDto doMockResponse(MockUrl url, HttpServletRequest request);

    ResponseType type();
}
