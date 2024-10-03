package me.tolek.network;

import me.tolek.ModForLazyPeople;
import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.MinecraftStartListener;
import me.tolek.gui.widgets.autoReply.ArReplyWidget;
import me.tolek.interfaces.IScheduler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;

import java.io.IOException;

public class MflpPlayersWorker extends EventImpl implements MinecraftStartListener {

    private static MflpPlayersWorker instance;
    public static MflpPlayersWorker getInstance() {
        if (instance == null) instance = new MflpPlayersWorker();
        return instance;
    }

    private MflpPlayersWorker() {}
    private final MinecraftClient client = MinecraftClient.getInstance();
    public String data = null;
    private final MflpServerConnection mflpServer = new MflpServerConnection();

    @Override
    public void onEnable() {
        EventManager.getInstance().add(MinecraftStartListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(MinecraftStartListener.class, this);
    }

    @Override
    public void onStart() {
        ((IScheduler) client).scheduleRepeating(20 * 60, (s) -> {
            try {
                this.data = mflpServer.sendGetRequest("/api/mflp");
            } catch (Exception e) {
                ModForLazyPeople.LOGGER.info("Failed to connect to server! Make sure you're connected to the internet and the MFLP " +
                        "server is up at epsi.ddns.net:3000!");
            }
        });


    }
}
