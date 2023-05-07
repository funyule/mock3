package cn.zaink.mock3.core.handler;

import cn.zaink.mock3.infrastructure.domain.MockUrl;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zaink
 **/
public final class UrlHandlerChain {

    private static final List<UrlHandler> HANDLERS = new ArrayList<>(8);

    public static void init(List<AbstractUrlHandler> handlers) {
        // 构造责任链
        if (CollectionUtils.isNotEmpty(handlers)) {
            AbstractUrlHandler preHandler = null;
            for (int i = 0, handlersSize = handlers.size(); i < handlersSize; i++) {
                AbstractUrlHandler handler = handlers.get(i);
                if (null != preHandler) {
                    preHandler.setNext(handler);
                }
                preHandler = handler;
                HANDLERS.add(i, handler);
            }
        }
    }

    public static MockUrl getByUri(String uri) {
        assert HANDLERS.size() >= 1;
        UrlHandler urlHandler = HANDLERS.get(0);
        return urlHandler.getByUri(uri);
    }
}
