package me.tolek.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tolek.ModForLazyPeople;
import me.tolek.gui.screens.FailedToConnectToMflpNetworkScreen;
import me.tolek.util.InstancedValues;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

import java.net.URI;
import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class WebSocketServerHandler {

    private static WebSocketServerHandler instance;

    private WebSocketServerHandler() {
        connect();
        InstancedValues.getInstance().failedToConnect = this.endpoint == null;
    }

    public WebSocketClientEndpoint endpoint;
    public ArrayList<String> mflpUsers = new ArrayList<>();
    public String clientKey;

    private final ArrayList<WebSocketClientEndpoint.MessageHandler> messageHandlers = new ArrayList<>();

    public static WebSocketServerHandler getInstance() {
        if (instance == null) instance = new WebSocketServerHandler();
        return instance;
    }

    public void addMessageHandler(WebSocketClientEndpoint.MessageHandler handler) {
        if (this.endpoint != null) {
            this.endpoint.addMessageHandler(handler);
            if (!messageHandlers.contains(handler)) {
                this.messageHandlers.add(handler);
            }
        }
    }
    public void sendMessage(String json) { if (this.endpoint != null) endpoint.sendMessage(json); }

    private void connect() {
        try {
            endpoint = new WebSocketClientEndpoint(new URI("ws://epsi.ddns.net:8080"));

            //endpoint = new WebSocketClientEndpoint(new URI("ws://localhost:3000"));

            this.endpoint.addMessageHandler(message -> {
                try {
                    JsonObject json = JsonParser.parseString(message).getAsJsonObject();

                    String clientKey = json.get("key").getAsString();

                    if (clientKey != null) this.clientKey = clientKey;
                    ModForLazyPeople.LOGGER.info("Received MFLP client key, setting");
                } catch (Exception ignored) {}
            });

            //new IconNetworkHandler().requestListAndSendJoin();
        } catch (Exception ignored) {}
    }

    public void reconnect() {
        this.connect();
        if (this.endpoint == null) {
            ModForLazyPeople.LOGGER.warn("Failed to Reconnect");
            MinecraftClient client = MinecraftClient.getInstance();
            client.setScreen(new FailedToConnectToMflpNetworkScreen());
        } else {
            this.messageHandlers.forEach(endpoint::addMessageHandler);
            IconNetworkHandler.getInstance().requestListAndSendJoin();
        }
    }

    public boolean isDisconnected() {
        return this.endpoint == null;
    }

}
