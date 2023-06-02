package cn.zaink.mock3.core.event;

import cn.zaink.mock3.infrastructure.domain.MockLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author zaink
 **/
public class MockUseEvent extends ApplicationEvent {

    public MockUseEvent(MockLog log) {
        super(log);
    }

    @Override
    public MockLog getSource() {
        return (MockLog) super.getSource();
    }
}
