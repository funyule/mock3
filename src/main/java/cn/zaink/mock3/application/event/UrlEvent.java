package cn.zaink.mock3.application.event;

import cn.zaink.mock3.infrastructure.domain.MockUrl;
import org.springframework.context.ApplicationEvent;

/**
 * @author zaink
 **/
public abstract class UrlEvent extends ApplicationEvent {

    public UrlEvent(MockUrl source) {
        super(source);
    }

    @Override
    public MockUrl getSource() {
        return (MockUrl) super.getSource();
    }

    public static class Create extends UrlEvent {

        public Create(MockUrl source) {
            super(source);
        }
    }

    public static class Update extends UrlEvent {

        public Update(MockUrl source) {
            super(source);
        }
    }

    public static class Delete extends UrlEvent {

        public Delete(MockUrl source) {
            super(source);
        }
    }
}
