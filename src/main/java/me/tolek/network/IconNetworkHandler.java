package me.tolek.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tolek.event.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class IconNetworkHandler extends EventImpl implements UpdateListener, MinecraftStartListener, MinecraftQuitListener {

    private final WebSocketServerHandler serverHandler = WebSocketServerHandler.getInstance();
    private final MinecraftClient client = MinecraftClient.getInstance();

    private int ticksPassed = 0;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(UpdateListener.class, this);

        serverHandler.addMessageHandler(message -> {
            JsonObject json = JsonParser.parseString(message).getAsJsonObject();

            String id = json.get("id").getAsString();
            String cmd = json.get("cmd").getAsString();
            String body = json.get("cmd").getAsString();

            if (cmd.equals("STATUS") && body.equals("JOIN")) {
                serverHandler.mflpUsers.add(id);
            } else if (cmd.equals("STATUS") && body.equals("PART")) {
                serverHandler.mflpUsers.remove(id);
            }
        });
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        ticksPassed += 1;
        if (ticksPassed == 60 * 20) {
            CompletableFuture.supplyAsync(() -> {
                if (this.client.getSession() == null || this.client.getSession().getUsername() == null) return "";

                JsonObject message = new JsonObject();
                message.addProperty("id", client.getSession().getUsername());
                message.addProperty("cmd", "STATUS");
                message.addProperty("body", "JOIN");

                serverHandler.sendMessage("");
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
        CompletableFuture.supplyAsync(() -> {
            if (this.client.getSession() == null || this.client.getSession().getUsername() == null) return "";

            JsonObject message = new JsonObject();
            message.addProperty("id", client.getSession().getUsername());
            message.addProperty("cmd", "STATUS");
            message.addProperty("body", "JOIN");

            serverHandler.sendMessage("");
            return "";
        });
    }

    @Override
    public void onQuit() {
        // Send leave message
        CompletableFuture.supplyAsync(() -> {
            if (this.client.getSession() == null || this.client.getSession().getUsername() == null) return "";

            JsonObject message = new JsonObject();
            message.addProperty("id", client.getSession().getUsername());
            message.addProperty("cmd", "STATUS");
            message.addProperty("body", "PART");

            serverHandler.sendMessage("");
            return "";
        });
    }
}