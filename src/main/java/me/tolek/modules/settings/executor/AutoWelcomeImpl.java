package me.tolek.modules.settings.executor;

import me.tolek.event.ChatListener;
import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.UpdateListener;
import me.tolek.modules.settings.AutoWelcome;
import me.tolek.modules.settings.AutoWelcomeBack;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.MflpSetting;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class AutoWelcomeImpl extends EventImpl implements ChatListener {

    private MinecraftClient client;
    private InstancedValues iv;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(ChatListener.class, this);
        this.client = MinecraftClient.getInstance();
        this.iv = InstancedValues.getInstance();
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(ChatListener.class, this);
    }

    @Override
    public void onMessageAdd(Text message) {
        executeAutoWelcome(message);
    }

    public void executeAutoWelcome(Text message) {
        MflpSettingsList settingsList = MflpSettingsList.getInstance();
        AutoWelcome setting = settingsList.AUTO_WELCOME;
        String playerName = MinecraftClient.getInstance().getSession().getUsername();

        if (!message.getString().contains(playerName)) {
            if (message.getString().contains("Welcome") && message.getString().contains(" to Synergy!")) {
                if (!MflpUtil.isFakeMessage(message)) {
                    if (iv.timeSinceLastInputInMils / 1000 < 30 && !iv.isAfk) {
                        setting.refresh();
                        iv.pauseWelcomeBack = true;
                    }
                }
            }
        }
    }

}
