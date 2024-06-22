package me.tolek.mixin.client;

import me.tolek.settings.MflpSettingsList;
import me.tolek.settings.base.MflpSetting;
import me.tolek.util.InstancedValues;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    private MflpSettingsList settingsList = MflpSettingsList.getInstance();
    private InstancedValues iv = InstancedValues.getInstance();

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("RETURN"))
    public void addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getSession().getUsername() != null) {
            String playerName = MinecraftClient.getInstance().getSession().getUsername();

            for (MflpSetting setting : settingsList.getSettings()) {
                if (setting.getName().equals("Auto welcome back")) {
                    executeAutoWB(message, setting, playerName);
                } else if (setting.getName().equals("Auto welcome")) {
                    executeAutoWelcome(message, setting, playerName);
                }
            }

            if (!isFakeMessage(message) && message.getString().contains("[!] You are now AFK.")) {
                iv.isAfk = true;
            }
            if (!isFakeMessage(message) && message.getString().contains("[!] You are no longer AFK.")) {
                iv.isAfk = false;
            }

        }
    }

    public boolean isFakeMessage(Text message) {
        return message.getString().contains("From") || message.getString().contains("*") || message.getString().contains(":");
    }

    public void executeAutoWelcome(Text message, MflpSetting setting, String playerName) {
        if (!message.getString().contains(playerName)) {
            if (message.getString().contains("Welcome") && message.getString().contains(" to Synergy!")) {
                if (!isFakeMessage(message)) {
                    if (iv.timeSinceLastInputInMils / 1000 < 30 && !iv.isAfk) {
                        setting.refresh();
                        iv.pauseWelcomeBack = true;
                    }
                }
            }
        }
    }

    public boolean isWhitelisted(String message) {
        if (settingsList.AUTO_WB_WHITELIST.getState()) {
            String[] whitelistedNames = settingsList.WB_WHITELIST.getState().split(" ");
            for (String wn : whitelistedNames) {
                if (message.contains(wn)) return true;
            }
        }
        return false;
    }

    public boolean isBlacklisted(String message) {
        if (settingsList.AUTO_WB_BLACKLIST.getState()) {
            String[] blacklistedNames = settingsList.WB_BLACKLIST.getState().split(" ");
            for (String bn : blacklistedNames) {
                if (message.contains(bn)) return true;
            }
        }
        return false;
    }

    public void executeAutoWB(Text message, MflpSetting setting, String playerName) {
        if (message.getString().contains("banana")) {
            if (iv.timeSinceLastInputInMils / 1000 < 30) {
                setting.refresh();
            }
        }
        if (!message.getString().contains(playerName)) {
            if (!iv.pauseWelcomeBack) {
                if (message.getString().contains("has joined.") || message.getString().contains("is no longer AFK.")) {
                    if (!isFakeMessage(message)) {
                        if (iv.timeSinceLastInputInMils / 1000 < 30 && !iv.isAfk) {
                            if (settingsList.AUTO_WB_WHITELIST.getState()) {
                                if (isWhitelisted(message.getString())) setting.refresh();
                            } else if(settingsList.AUTO_WB_BLACKLIST.getState()) {
                                if (!isBlacklisted(message.getString())) setting.refresh();
                            } else setting.refresh();
                        }
                    }
                }
            } else {
                iv.pauseWelcomeBack = false;
            }
        }

    }

}
