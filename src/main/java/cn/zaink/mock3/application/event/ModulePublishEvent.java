package cn.zaink.mock3.application.event;

import cn.zaink.mock3.infrastructure.domain.MockModule;
import org.springframework.context.ApplicationEvent;

/**
 * @author zaink
 **/
public class ModulePublishEvent extends ApplicationEvent {

    public ModulePublishEvent(Object source) {
        super(source);
    }

    @Override
    public MockModule getSource() {
        return (MockModule) super.getSource();
    }
}
