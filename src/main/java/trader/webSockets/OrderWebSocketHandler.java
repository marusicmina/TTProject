package trader.webSockets;


import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.CloseStatus;

import org.springframework.stereotype.Component; // Dodaj ovu anotaciju

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderWebSocketHandler implements WebSocketHandler {

    private static List<WebSocketSession> sessions = new ArrayList<>();  

    
    public void broadcastOrderUpdate(String orderJson) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(orderJson));  
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);  
        System.out.println("New WebSocket session established: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        
        System.out.println("Received message: " + message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Error with WebSocket session: " + session.getId());
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);  
        System.out.println("WebSocket session closed: " + session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;  
    }
}

