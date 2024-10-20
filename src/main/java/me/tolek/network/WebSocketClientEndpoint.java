package me.tolek.network;

import me.tolek.ModForLazyPeople;
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
        ModForLazyPeople.LOGGER.info("Opening MFLP websocket");
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        ModForLazyPeople.LOGGER.info("Closing MFLP websocket");
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
        ModForLazyPeople.LOGGER.warn("Received byte buffer, not handled!");
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
