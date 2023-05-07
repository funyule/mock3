package cn.zaink.mock3.core.handler.impl;

import cn.zaink.mock3.core.exception.BizException;
import cn.zaink.mock3.core.handler.AbstractUrlHandler;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class UrlNotFoundHandler extends AbstractUrlHandler {
    @Override
    public MockUrl doGetByUri(String uri) {
        throw new BizException(404, "找不到路径 {}", uri);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
