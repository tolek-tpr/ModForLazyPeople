package me.tolek.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.websocket.*;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;

@ClientEndpoint
@Environment(EnvType.CLIENT)
public class WebSocketClientEndpoint {

    Session userSession = null;
    private final ArrayList<MessageHandler> messageHandlers = new ArrayList<>();

    public WebSocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        WebSocketServerHandler.getInstance().endpoint = null;
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        if (!this.messageHandlers.isEmpty()) {
            this.messageHandlers.forEach(handler -> handler.handleMessage(message));
        }
    }

    @OnMessage
    public void onMessage(ByteBuffer bytes) {
        System.out.println("Handle byte buffer");
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandlers.add(msgHandler);
    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }

}
