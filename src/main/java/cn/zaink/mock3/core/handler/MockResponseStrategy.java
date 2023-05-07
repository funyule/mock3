package cn.zaink.mock3.core.handler;

import cn.zaink.mock3.application.consts.ResponseType;
import cn.zaink.mock3.core.pojo.Result;
import cn.zaink.mock3.infrastructure.domain.MockUrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zaink
 **/
public interface MockResponseStrategy {

    void doMockResponse(MockUrl url, HttpServletRequest request, HttpServletResponse response);

    ResponseType type();
}
