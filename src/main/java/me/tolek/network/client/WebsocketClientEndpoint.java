package me.tolek.network.client;

import me.tolek.network.ClientPacketHandler;
import me.tolek.util.LoggerUtils;

import javax.websocket.*;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;

@ClientEndpoint
public class WebsocketClientEndpoint {

    Session userSession = null;
    private final ArrayList<MessageHandler> messageHandlers = new ArrayList<>();

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            container.connectToServer(this, endpointURI);
        } catch (Exception ignored) {}
    }

    @OnMessage
    public void onMessage(String message) {
        if (!this.messageHandlers.isEmpty()) {
            this.messageHandlers.forEach(handler -> handler.handleMessage(message));
        }
    }

    @OnMessage
    public void onMessage(ByteBuffer bytes) {
        new ClientPacketHandler().onByteMessage(bytes, null);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        if (userSession != null) return;
        LoggerUtils.NETWORK.info("Opening MFLP websocket");

        this.userSession = session;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        LoggerUtils.NETWORK.info("Closing MFLP websocket");

        this.userSession = null;
        WebsocketHandler.getInstance().endpoint = null;
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandlers.add(msgHandler);
    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    public void sendBuffer(ByteBuffer buffer) {
        this.userSession.getAsyncRemote().sendBinary(buffer);
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }

}
