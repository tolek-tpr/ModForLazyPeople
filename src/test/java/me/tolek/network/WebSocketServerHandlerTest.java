package me.tolek.network;

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

        System.out.println(message);

        serverHandler.sendMessage(message.toString());
    }

}