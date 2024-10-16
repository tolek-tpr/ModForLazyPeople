package me.tolek.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.net.URI;

@Environment(EnvType.CLIENT)
public class WebSocketServerHandler {

    private static WebSocketServerHandler instance;

    private WebSocketServerHandler() {
        try {
            endpoint = new WebSocketClientEndpoint(new URI("wss://epsi.ddns.net:3000"));
        } catch (Exception ignored) {}
    }

    public WebSocketClientEndpoint endpoint;

    public static WebSocketServerHandler getInstance() {
        if (instance == null) instance = new WebSocketServerHandler();
        return instance;
    }

    public void addMessageHandler(WebSocketClientEndpoint.MessageHandler handler) { if (this.endpoint != null) this.endpoint.addMessageHandler(handler); }
    public void sendMessage(String json) { if (this.endpoint != null) endpoint.sendMessage(json); }

}
