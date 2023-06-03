package cn.zaink.mock3.application.event;

import cn.zaink.mock3.application.service.UrlService;
import cn.zaink.mock3.infrastructure.domain.MockModule;
import cn.zaink.mock3.infrastructure.domain.MockUrl;
import cn.zaink.mock3.infrastructure.service.MockUrlService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class ModuleEventListener {

    private final ApplicationEventPublisher eventPublisher;
    private final UrlService urlService;
    private final MockUrlService mockUrlService;

    public ModuleEventListener(ApplicationEventPublisher eventPublisher, UrlService urlService, MockUrlService mockUrlService) {
        this.eventPublisher = eventPublisher;
        this.urlService = urlService;
        this.mockUrlService = mockUrlService;
    }

    @EventListener(ModuleEvent.Create.class)
    public void onEvent(ModuleEvent.Create event) {
        MockModule mockModule = event.getSource();
        if (mockModule.getPublishService() == 1) {
            eventPublisher.publishEvent(new ModulePublishEvent(mockModule));
        }
    }

    @Async
    @EventListener(ModuleEvent.Update.class)
    public void onEvent(ModuleEvent.Update event) {
        MockModule mockModule = event.getSource();
        eventPublisher.publishEvent(new ModulePublishEvent(mockModule));
    }

    @EventListener(ModuleEvent.Delete.class)
    public void onEvent(ModuleEvent.Delete event) {
        MockModule mockModule = event.getSource();
        List<MockUrl> mockUrls = mockUrlService.list(Wrappers.<MockUrl>lambdaQuery()
                .eq(MockUrl::getModuleId, mockModule.getId()));
        if (CollectionUtils.isNotEmpty(mockUrls)) {
            mockUrls.parallelStream()
                    .forEach(u -> urlService.delete(u.getId()));
        }
    }
}
