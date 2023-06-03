package cn.zaink.mock3.application.event;

import cn.zaink.mock3.application.dto.ResponseDto;
import cn.zaink.mock3.infrastructure.domain.MockResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author zaink
 **/
public abstract class UrlResponseEvent extends ApplicationEvent {

    public UrlResponseEvent(MockResponse source) {
        super(source);
    }

    @Override
    public MockResponse getSource() {
        return (MockResponse) super.getSource();
    }

    public static class Create extends UrlResponseEvent {

        @Getter
        private final ResponseDto sourceRequest;

        public Create(MockResponse source, ResponseDto sourceRequest) {
            super(source);
            this.sourceRequest = sourceRequest;
        }
    }

    public static class Update extends UrlResponseEvent {
        @Getter
        private final ResponseDto sourceRequest;

        public Update(MockResponse source, ResponseDto sourceRequest) {
            super(source);
            this.sourceRequest = sourceRequest;
        }
    }

    public static class Delete extends UrlResponseEvent {

        public Delete(MockResponse source) {
            super(source);
        }
    }
}
