package cn.zaink.mock3.application.event;

import cn.zaink.mock3.infrastructure.domain.MockModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class ModuleEventListener {

    private final ApplicationEventPublisher eventPublisher;

    public ModuleEventListener(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
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

    }
}
