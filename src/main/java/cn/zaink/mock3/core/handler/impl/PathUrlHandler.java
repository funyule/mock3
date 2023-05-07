package cn.zaink.mock3.core.handler.impl;

import cn.zaink.mock3.core.handler.AbstractUrlHandler;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import cn.zaink.mock3.infrastructure.domain.MockUrlLogic;
import cn.zaink.mock3.infrastructure.service.MockUrlLogicService;
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
public class PathUrlHandler extends AbstractUrlHandler {
    private final MockUrlService mockUrlService;
    private final MockUrlLogicService mockUrlLogicService;

    public PathUrlHandler(MockUrlService mockUrlService, MockUrlLogicService mockUrlLogicService) {
        this.mockUrlService = mockUrlService;
        this.mockUrlLogicService = mockUrlLogicService;
    }

    @Override
    public MockUrl doGetByUri(String uri) {
        MockUrlLogic mockUrlLogic = mockUrlLogicService.matchUrl(uri);
        if (null != mockUrlLogic) {
            return mockUrlService.getOne(Wrappers.<MockUrl>lambdaQuery()
                    .eq(MockUrl::getLogic, mockUrlLogic.getLogicId()));
        }
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE / 2;
    }
}
