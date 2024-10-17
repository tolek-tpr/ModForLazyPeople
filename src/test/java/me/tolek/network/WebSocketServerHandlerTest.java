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

        JsonObject m2 = new JsonObject();
        JsonObject body = new JsonObject();
        body.addProperty("cmd", "INVITE");
        body.addProperty("player", "Player634");
        m2.addProperty("key", serverHandler.clientKey);
        m2.addProperty("id", "bearrr");
        m2.addProperty("cmd", "PARTY");
        m2.add("body", body);

        serverHandler.sendMessage(message.toString());
        serverHandler.sendMessage(m2.toString());

        serverHandler.addMessageHandler(System.out::println);
    }

}