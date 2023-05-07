package cn.zaink.mock3.core.handler.impl;

import cn.zaink.mock3.core.handler.AbstractUrlHandler;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import cn.zaink.mock3.infrastructure.service.MockUrlService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class DirectUrlHandler extends AbstractUrlHandler {

    private final MockUrlService mockUrlService;

    public DirectUrlHandler(MockUrlService mockUrlService) {
        this.mockUrlService = mockUrlService;
    }

    @Override
    public MockUrl doGetByUri(String uri) {
        return mockUrlService.getOne(Wrappers.<MockUrl>lambdaQuery()
                .eq(MockUrl::getUrl, uri));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
