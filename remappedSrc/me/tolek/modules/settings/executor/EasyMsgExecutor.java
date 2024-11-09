package me.tolek.modules.settings.executor;

import me.tolek.event.*;
import me.tolek.modules.settings.MflpSettingsList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class EasyMsgExecutor extends EventImpl implements UpdateListener, KeyboardListener, ChatListener {

    private static EasyMsgExecutor instance;

    public static EasyMsgExecutor getInstance() {
        if (instance == null) instance = new EasyMsgExecutor();
        return instance;
    }

    private EasyMsgExecutor() {}

    private int timeSinceLastMsgInMillis = 20000;
    private int timeSinceLastPcInMillis = 20000;
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onEnable() {
        EventManager.getInstance().add(UpdateListener.class, this);
        EventManager.getInstance().add(KeyboardListener.class, this);
        EventManager.getInstance().add(ChatListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(UpdateListener.class, this);
        EventManager.getInstance().remove(KeyboardListener.class, this);
        EventManager.getInstance().remove(ChatListener.class, this);
    }

    @Override
    public void onMessageAdd(Text message) {
        if (message.getString().startsWith("From") && !message.getString().startsWith("*")) {
            this.timeSinceLastMsgInMillis = 0;
        }
        if (message.getString().startsWith("MFLP Party > ") && !message.getString().startsWith("*")) {
            this.timeSinceLastPcInMillis = 0;
        }
    }

    @Override
    public void onKey(int keyCode, int scanCode, int modifiers) {
        if (client.options.chatKey.matchesKey(keyCode, scanCode) || client.options.commandKey.matchesKey(keyCode, scanCode)) {
            int time = Math.min(this.timeSinceLastMsgInMillis, this.timeSinceLastPcInMillis);
            if (time < 20 * 1000) {
                if (time == this.timeSinceLastMsgInMillis) {
                    MflpSettingsList.getInstance().EASY_MSG_SETTING.refresh();
                } else {
                    MflpSettingsList.getInstance().EASY_MSG_SETTING.refresh("/pc ");
                }
            }
        }
    }

    @Override
    public void onUpdate() {
        this.timeSinceLastMsgInMillis += 50;
        this.timeSinceLastPcInMillis += 50;
    }
}
