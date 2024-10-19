package me.tolek.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.net.ssl.*;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class WebSocketServerHandler {

    private static WebSocketServerHandler instance;

    private WebSocketServerHandler() {
        this.connect();
    }

    public WebSocketClientEndpoint endpoint;
    public ArrayList<String> mflpUsers = new ArrayList<>();
    public String clientKey;

    public static WebSocketServerHandler getInstance() {
        if (instance == null) instance = new WebSocketServerHandler();
        return instance;
    }

    public void addMessageHandler(WebSocketClientEndpoint.MessageHandler handler) { if (this.endpoint != null) this.endpoint.addMessageHandler(handler); }
    public void sendMessage(String json) { if (this.endpoint != null) endpoint.sendMessage(json); }

    private void connect() {
        try {
            //endpoint = new WebSocketClientEndpoint(new URI("wss://epsi.ddns.net:3000"));

            endpoint = new WebSocketClientEndpoint(new URI("ws://localhost:3000"));

            this.endpoint.addMessageHandler(message -> {
                try {
                    JsonObject json = JsonParser.parseString(message).getAsJsonObject();

                    String clientKey = json.get("key").getAsString();

                    if (clientKey != null) this.clientKey = clientKey;
                    System.out.println("Received client key, setting");
                } catch (Exception ignored) {}
            });
        } catch (Exception ignored) {}
    }

    public void reconnect() {
        if (this.endpoint == null) {
            CompletableFuture.supplyAsync(() -> {
                this.connect();
                return this.endpoint == null;
            });
        }
    }

}
