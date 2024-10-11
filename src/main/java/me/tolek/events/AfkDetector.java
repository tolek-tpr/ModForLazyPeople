package me.tolek.events;

import me.tolek.event.*;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class AfkDetector extends EventImpl implements KeyboardListener, UpdateListener, ChatListener {

    InstancedValues iv = InstancedValues.getInstance();
    private MinecraftClient client;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(KeyboardListener.class, this);
        EventManager.getInstance().add(UpdateListener.class, this);
        EventManager.getInstance().add(ChatListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(KeyboardListener.class, this);
        EventManager.getInstance().remove(UpdateListener.class, this);
        EventManager.getInstance().remove(ChatListener.class, this);
    }

    @Override
    public void onKey(int keyCode, int scanCode, int modifiers) {
        iv.timeSinceLastInputMillis = 0;
    }

    @Override
    public void onUpdate() {
        if (client == null) this.client = MinecraftClient.getInstance();
        if (client.player != null && client.player.clientWorld != null) {
            iv.timeSinceLastInputMillis += 50;
        }
    }

    @Override
    public void onMessageAdd(Text message) {
        if (!MflpUtil.isFakeMessage(message) && message.getString().contains("[!] You are now AFK.")) {
            iv.isAfk = true;
        }
        if (!MflpUtil.isFakeMessage(message) && message.getString().contains("[!] You are no longer AFK.")) {
            iv.isAfk = false;
        }
    }
}
