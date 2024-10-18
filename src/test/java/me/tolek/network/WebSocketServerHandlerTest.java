package me.tolek.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebSocketServerHandlerTest {

    private final WebSocketServerHandler serverHandler = WebSocketServerHandler.getInstance();

    @Test
    public void sendMessageTest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JsonObject message = new JsonObject();
        message.addProperty("key", serverHandler.clientKey);
        message.addProperty("id", "bearrr");
        message.addProperty("cmd", "STATUS");
        message.addProperty("body", "JOIN");

        JsonObject m3 = new JsonObject();
        JsonObject body1 = new JsonObject();
        body1.addProperty("cmd", "INVITE");
        body1.addProperty("player", "Player23" /*username*/);
        m3.addProperty("key", serverHandler.clientKey);
        m3.addProperty("id", "bearrr");
        m3.addProperty("cmd", "PARTY");
        m3.add("body", body1);

        JsonObject m2 = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("cmd", "LEAVE");
        body.addProperty("player", "bearrr" /*username*/);
        m2.addProperty("key", serverHandler.clientKey);
        m2.addProperty("id", "bearrr");
        m2.addProperty("cmd", "PARTY");
        m2.add("body", body);

        serverHandler.sendMessage(message.toString());
        serverHandler.sendMessage(m3.toString());
        //serverHandler.sendMessage(m2.toString());

        serverHandler.addMessageHandler(System.out::println);
    }

}