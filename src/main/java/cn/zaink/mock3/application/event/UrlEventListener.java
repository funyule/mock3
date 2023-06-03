package cn.zaink.mock3.application.event;

import cn.zaink.mock3.application.service.ResponseService;
import cn.zaink.mock3.infrastructure.domain.MockResponse;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import cn.zaink.mock3.infrastructure.domain.MockUrlLogic;
import cn.zaink.mock3.infrastructure.service.MockResponseService;
import cn.zaink.mock3.infrastructure.service.MockUrlLogicService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class UrlEventListener {

    private final MockUrlLogicService mockUrlLogicService;

    private final MockResponseService mockResponseService;
    private final ResponseService responseService;

    public UrlEventListener(MockUrlLogicService mockUrlLogicService, MockResponseService mockResponseService, ResponseService responseService) {
        this.mockUrlLogicService = mockUrlLogicService;
        this.mockResponseService = mockResponseService;
        this.responseService = responseService;
    }

    @Transactional(rollbackFor = Throwable.class)
    @EventListener(UrlEvent.Create.class)
    public void onEvent(UrlEvent.Create event) {

    }

    @Transactional(rollbackFor = Throwable.class)
    @EventListener(UrlEvent.Update.class)
    public void onEvent(UrlEvent.Update event) {

    }

    @Transactional(rollbackFor = Throwable.class)
    @EventListener(UrlEvent.Delete.class)
    public void onEvent(UrlEvent.Delete event) {
        MockUrl mockUrl = event.getSource();
        if (null != mockUrl.getLogic()) {
            mockUrlLogicService.remove(Wrappers.<MockUrlLogic>lambdaQuery()
                    .eq(MockUrlLogic::getLogicId, mockUrl.getLogic()));
        }

        List<MockResponse> responseList = mockResponseService.list(Wrappers.<MockResponse>lambdaQuery()
                .eq(MockResponse::getUrlId, mockUrl.getId()));
        responseList.parallelStream()
                .forEach(r -> responseService.delete(r.getId()));
    }
}
