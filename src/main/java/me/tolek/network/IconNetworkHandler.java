package me.tolek.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tolek.event.*;
import me.tolek.gui.screens.FailedToConnectToMflpNetworkScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class IconNetworkHandler extends EventImpl implements UpdateListener, MinecraftStartListener, MinecraftQuitListener {

    private final WebSocketServerHandler serverHandler = WebSocketServerHandler.getInstance();
    private final MinecraftClient client = MinecraftClient.getInstance();
    private boolean sentQuit = false;

    private int ticksPassed = 0;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(UpdateListener.class, this);
        EventManager.getInstance().add(MinecraftStartListener.class, this);
        EventManager.getInstance().add(MinecraftQuitListener.class, this);

        serverHandler.addMessageHandler(message -> {
            try {
                if (serverHandler.endpoint == null) return;

                JsonObject json = JsonParser.parseString(message).getAsJsonObject();

                String id = json.get("id").getAsString();
                String cmd = json.get("cmd").getAsString();
                String body = json.get("body").getAsString();

                if (cmd.equalsIgnoreCase("STATUS") && body.equalsIgnoreCase("JOIN")) {
                    serverHandler.mflpUsers.add(id);
                } else if (cmd.equalsIgnoreCase("STATUS") && body.equalsIgnoreCase("PART")) {
                    serverHandler.mflpUsers.remove(id);
                } else if (cmd.equalsIgnoreCase("STATUS")) {
                    JsonObject bodyObject = JsonParser.parseString(body).getAsJsonObject();

                    if (bodyObject.get("body_cmd").getAsString().equalsIgnoreCase("FULL_LIST")) {
                        ArrayList<String> clients = new ArrayList<>();

                        bodyObject.get("clients").getAsJsonArray().forEach(element -> clients.add(element.getAsString()));
                        serverHandler.mflpUsers = clients;
                    }
                }
            } catch (Exception ignored) { }
        });
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(UpdateListener.class, this);
        EventManager.getInstance().remove(MinecraftStartListener.class, this);
        EventManager.getInstance().remove(MinecraftQuitListener.class, this);
    }

    @Override
    public void onUpdate() {
        ticksPassed += 1;
        if (ticksPassed == 60 * 20) {
            CompletableFuture.supplyAsync(() -> {
                if (this.client.getSession() == null || this.client.getSession().getUsername() == null) return "";

                JsonObject message = new JsonObject();
                message.addProperty("key", serverHandler.clientKey);
                message.addProperty("id", client.getSession().getUsername());
                message.addProperty("cmd", "STATUS");
                message.addProperty("body", "JOIN");

                serverHandler.sendMessage(message.toString());
                return "";
            });
            ticksPassed = 0;
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStartFinished() {
        // Send join message
        requestListAndSendJoin();
    }

    public void requestListAndSendJoin() {
        CompletableFuture.supplyAsync(() -> {
            if (this.client.getSession() == null || this.client.getSession().getUsername() == null) return "";

            JsonObject requestListMessage = new JsonObject();
            requestListMessage.addProperty("key", serverHandler.clientKey);
            requestListMessage.addProperty("id", this.client.getSession().getUsername());
            requestListMessage.addProperty("cmd", "STATUS");
            requestListMessage.addProperty("body", "REQUEST_LIST");

            serverHandler.sendMessage(requestListMessage.toString());
            return "";
        });

        CompletableFuture.supplyAsync(() -> {
            if (this.client.getSession() == null || this.client.getSession().getUsername() == null) return "";

            JsonObject message = new JsonObject();
            message.addProperty("key", serverHandler.clientKey);
            message.addProperty("id", client.getSession().getUsername());
            message.addProperty("cmd", "STATUS");
            message.addProperty("body", "JOIN");
            serverHandler.sendMessage(message.toString());

            return "";
        });
    }

    @Override
    public void onQuit() {
        // Send leave message
        if (sentQuit) return;
        CompletableFuture.supplyAsync(() -> {
            if (this.client.getSession() == null || this.client.getSession().getUsername() == null) return "";

            JsonObject message = new JsonObject();
            message.addProperty("key", serverHandler.clientKey);
            message.addProperty("id", client.getSession().getUsername());
            message.addProperty("cmd", "STATUS");
            message.addProperty("body", "PART");

            serverHandler.sendMessage(message.toString());
            sentQuit = true;
            return "";
        });
    }
}
