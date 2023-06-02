package cn.zaink.mock3.conf.ws;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zaink
 **/
@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String id = session.getId();
        log.debug("websocket connected {}", id);
        sessions.putIfAbsent(id, session);
        String jsonStr = JSONUtil.toJsonStr(session);
        log.debug("jsonStr {}", jsonStr);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        session.sendMessage(new TextMessage("当前客户端id" + session.getId()));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String id = session.getId();
        log.debug("websocket disconnected {}", id);
        WebSocketSession removed = sessions.remove(id);
        if (null != removed) {
            removed.close(status);
        }
    }
}
