package cn.zaink.mock3.core.handler;

import cn.zaink.mock3.infrastructure.domain.MockUrl;
import lombok.Setter;
import org.springframework.core.Ordered;

/**
 * @author zaink
 **/
public abstract class AbstractUrlHandler implements UrlHandler, Ordered {

    @Setter
    protected AbstractUrlHandler next;

    @Override
    public MockUrl getByUri(String uri) {
        MockUrl mockUrl = this.doGetByUri(uri);
        if (null != mockUrl) {
            return mockUrl;
        }
        if (null != next) {
            return next.getByUri(uri);
        }
        return null;
    }

    public abstract MockUrl doGetByUri(String uri);
}
