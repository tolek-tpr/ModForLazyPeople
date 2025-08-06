package me.tolek.network.client;

import me.tolek.scheduler.MflpScheduler;
import me.tolek.util.LoggerUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class WebsocketHandler {

    private static WebsocketHandler instance;

    private WebsocketHandler() {
        //if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
            MflpScheduler.getInstance().scheduleRepeatingTask(Identifier.of("mflp-ws-reconnect"), () -> {
                if (this.endpoint == null || this.endpoint.userSession == null) reconnect();
            }, 2000L);
        //}
        connect();
    }

    WebsocketClientEndpoint endpoint;

    private final ArrayList<WebsocketClientEndpoint.MessageHandler> messageHandlers = new ArrayList<>();

    public static WebsocketHandler getInstance() {
        if (instance == null) instance = new WebsocketHandler();
        return instance;
    }

    public void addMessageHandler(WebsocketClientEndpoint.MessageHandler handler) {
        if (this.endpoint != null) {
            this.endpoint.addMessageHandler(handler);
            if (!messageHandlers.contains(handler)) {
                this.messageHandlers.add(handler);
            }
        }
    }

    public void sendMessage(String json) { if (this.endpoint != null && this.endpoint.userSession != null) endpoint.sendMessage(json); }
    public void sendBuffer(ByteBuffer buffer) { if (this.endpoint != null && this.endpoint.userSession != null) endpoint.sendBuffer(buffer); }

    private void connect() {
        try {
            if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                endpoint = new WebsocketClientEndpoint(new URI("ws://localhost:8081/ws/ws"));
            } else {
                endpoint = new WebsocketClientEndpoint(new URI("ws://epsi.ddns.net:8081/ws/ws"));
            }
        } catch (Exception ignored) {}
    }

    public void reconnect() {
        this.connect();
        if (this.endpoint == null || this.endpoint.userSession == null) {
            LoggerUtils.NETWORK.warn("Failed to Reconnect to MFLP WS");
        } else {
            this.messageHandlers.forEach(endpoint::addMessageHandler);
        }
    }

    public boolean isDisconnected() {
        return this.endpoint == null;
    }

}
