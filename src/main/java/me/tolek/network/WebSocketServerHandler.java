package me.tolek.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.net.ssl.*;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class WebSocketServerHandler {

    private static WebSocketServerHandler instance;

    private WebSocketServerHandler() {
        try {
            //endpoint = new WebSocketClientEndpoint(new URI("wss://epsi.ddns.net:3000"));

            endpoint = new WebSocketClientEndpoint(new URI("ws://localhost:3000"));
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        this.endpoint.addMessageHandler(message -> {
            JsonObject json = JsonParser.parseString(message).getAsJsonObject();

            String clientKey = json.get("key").getAsString();

            if (clientKey != null) this.clientKey = clientKey;
            System.out.println(clientKey);
        });
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



}
