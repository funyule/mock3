package cn.zaink.mock3.core.event;

import cn.zaink.mock3.infrastructure.service.MockLogService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class MockEventListener {

    private final MockLogService mockLogService;

    public MockEventListener(MockLogService mockLogService) {
        this.mockLogService = mockLogService;
    }


    @SneakyThrows(Throwable.class)
    @Async
    @EventListener(MockUseEvent.class)
    public void logMockUse(MockUseEvent event) {
        mockLogService.save(event.getSource());
    }


}
