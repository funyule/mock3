package cn.zaink.mock3.application.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author zaink
 **/
@Slf4j
@Component
public class UrlEventListener {

    @EventListener(UrlEvent.Create.class)
    public void onEvent(UrlEvent.Create event) {

    }

    @EventListener(UrlEvent.Update.class)
    public void onEvent(UrlEvent.Update event) {

    }

    @EventListener(UrlEvent.Delete.class)
    public void onEvent(UrlEvent.Delete event) {

    }
}
