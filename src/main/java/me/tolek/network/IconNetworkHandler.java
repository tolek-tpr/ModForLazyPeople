package me.tolek.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.tolek.ModForLazyPeople;
import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.MinecraftStartListener;
import me.tolek.event.UpdateListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class IconNetworkHandler extends EventImpl implements UpdateListener, MinecraftStartListener {

    private final WebSocketServerHandler serverHandler = WebSocketServerHandler.getInstance();
    private final MinecraftClient client = MinecraftClient.getInstance();

    private int ticksPassed = 0;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(UpdateListener.class, this);
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
            CompletableFuture<String> gets = CompletableFuture.supplyAsync(() -> {
                String d = null;
                try {
                    d = mflpServer.sendGetRequest("/api/mflp");
                } catch (Exception e) {
                    ModForLazyPeople.LOGGER.info("Failed to connect to server! Make sure you're connected to the internet and the MFLP " +
                            "server is up at epsi.ddns.net:3000!");
                }
                return d;
            });
            gets.thenApply((r) -> {
                try {
                    this.data = gets.get();
                } catch (Exception ignored) {
                }
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

    }
}
