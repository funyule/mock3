package cn.zaink.mock3.core.handler;

import cn.zaink.mock3.infrastructure.domain.MockUrl;

/**
 * @author zaink
 **/
public interface UrlHandler {

    MockUrl getByUri(String uri);
}
