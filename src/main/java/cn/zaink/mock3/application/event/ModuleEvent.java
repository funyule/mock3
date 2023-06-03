package cn.zaink.mock3.application.event;

import cn.zaink.mock3.infrastructure.domain.MockModule;
import org.springframework.context.ApplicationEvent;

/**
 * @author zaink
 **/
public abstract class ModuleEvent extends ApplicationEvent {

    public ModuleEvent(MockModule source) {
        super(source);
    }

    @Override
    public MockModule getSource() {
        return (MockModule) super.getSource();
    }

    public static class Create extends ModuleEvent {

        public Create(MockModule source) {
            super(source);
        }
    }

    public static class Update extends ModuleEvent {

        public Update(MockModule source) {
            super(source);
        }
    }

    public static class Delete extends ModuleEvent {

        public Delete(MockModule source) {
            super(source);
        }
    }
}
