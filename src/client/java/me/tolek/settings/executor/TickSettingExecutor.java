package me.tolek.settings.executor;

import me.tolek.settings.AutoWelcomeBack;
import me.tolek.settings.MflpSettingsList;
import me.tolek.settings.base.MflpSetting;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class TickSettingExecutor implements ClientModInitializer {

    private MflpSettingsList settingsList = MflpSettingsList.getInstance();

    @Override
    public void onInitializeClient() {
        ClientReceiveMessageEvents.CHAT.register((message, signedMessage, gameProfile, parameters, instant) -> {
            String playerName = MinecraftClient.getInstance().getSession().getUsername();

            for (MflpSetting setting : settingsList.getSettings()) {
                if (setting.getName().equals("Auto welcome back")) {
                    if (message.getString().contains("has joined.")) {
                        if (!message.getString().contains(playerName)) {
                            //AutoWelcomeBack autoWbSetting = new AutoWelcomeBack();
                            setting.refresh();
                        }
                    }
                }
            }

        });
    }

}
