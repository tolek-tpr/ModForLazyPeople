package me.tolek.network.depracated;

import me.tolek.event.*;
import me.tolek.network.client.WebsocketHandler;
import me.tolek.network.packet.C2S.StatusPlayerJoinC2SPacket;
import me.tolek.network.packet.C2S.StatusPlayerLeaveC2SPacket;
import me.tolek.network.packet.C2S.StatusRequestListC2SPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class IconHandler extends EventImpl implements MinecraftStartListener, MinecraftQuitListener {

    private static IconHandler instance;
    private IconHandler() {}

    public static IconHandler getInstance() {
        if (instance == null) instance = new IconHandler();
        return instance;
    }

    public ArrayList<String> mflpUsers = new ArrayList<>();

    @Override
    public void onEnable() {
        EventManager.getInstance().add(MinecraftStartListener.class, this);
        EventManager.getInstance().add(MinecraftQuitListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(MinecraftStartListener.class, this);
        EventManager.getInstance().remove(MinecraftQuitListener.class, this);
    }

    @Override
    public void onStart() {}

    @Override
    public void onStartFinished() {
        requestListAndSendJoin();
    }

    public void requestListAndSendJoin() {
        WebsocketHandler.getInstance().sendBuffer(new StatusRequestListC2SPacket().serialize());
        WebsocketHandler.getInstance().sendBuffer(new StatusPlayerJoinC2SPacket(MinecraftClient.getInstance().getSession().getUsername()).serialize());
    }

    @Override
    public void onQuit() {
        WebsocketHandler.getInstance().sendBuffer(new StatusPlayerLeaveC2SPacket(MinecraftClient.getInstance().getSession().getUsername()).serialize());
    }
}
