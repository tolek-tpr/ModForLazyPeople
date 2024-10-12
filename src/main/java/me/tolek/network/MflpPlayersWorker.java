package me.tolek.network;

import me.tolek.ModForLazyPeople;
import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.MinecraftStartListener;
import me.tolek.event.UpdateListener;
import me.tolek.gui.widgets.autoReply.ArReplyWidget;
import me.tolek.interfaces.IScheduler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class MflpPlayersWorker extends EventImpl implements UpdateListener {

    private static MflpPlayersWorker instance;
    public static MflpPlayersWorker getInstance() {
        if (instance == null) instance = new MflpPlayersWorker();
        return instance;
    }

    private MflpPlayersWorker() {}
    private final MinecraftClient client = MinecraftClient.getInstance();
    public String data = null;
    private final MflpServerConnection mflpServer = new MflpServerConnection();
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
                sendInfoToServer();
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

    public static void sendInfoToServer() {
        MinecraftClient client = MinecraftClient.getInstance();
        MflpServerConnection mflpServer = ModForLazyPeople.serverConnection;

        try {
            if (client.getSession() == null) throw new Exception("Client Session is null!");
            mflpServer.sendPostRequest("/api/mflp", "{\"username\":\"" + client.getSession().getUsername() + "\"}");
        } catch (Exception e) {
            // Session is null error handling;
            if (e.getMessage().contains("Session is null")) {
                ModForLazyPeople.LOGGER.info("Failed to send a request to server. Cause: SESSION == NULL");
            } else {
                ModForLazyPeople.LOGGER.info("Failed to send put request.");
            }
        }
    }
}
