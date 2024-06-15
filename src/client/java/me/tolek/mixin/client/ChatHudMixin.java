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
                    if (message.getString().contains("banana")) {
                        if (iv.timeSinceLastInputInMils / 1000 < 30) {
                            setting.refresh();
                        }
                    }
                    if (message.getString().contains("has joined.") && !(message.getString().contains("From"))) {
                        if (!message.getString().contains(playerName) && !(message.getString().contains("*"))) {
                            /*if (settingsList.AUTO_WB_WHITELIST.getState()) {
                                String[] whiteListedNames = settingsList.WB_WHITELIST.getState().split(" ");
                                for (String s : whiteListedNames) {
                                    if (message.getString().contains(s)) {
                                        setting.refresh();
                                    }
                                }
                                return;
                            }
                            if (settingsList.AUTO_WB_BLACKLIST.getState()) {
                                String[] blackListedNames = settingsList.WB_BLACKLIST.getState().split(" ");
                                boolean opt = true;
                                for (String s : blackListedNames) {
                                    if (message.getString().contains(s)) {
                                        opt = false;
                                    }
                                }
                                if (opt) {
                                    setting.refresh();
                                }
                                return;
                            }*/
                            if (iv.timeSinceLastInputInMils / 1000 < 30) {
                                setting.refresh();
                            }
                        }
                    } else if (message.getString().contains("is no longer AFK.")  && !(message.getString().contains("From"))) {
                        if (!message.getString().contains(playerName) &&  !(message.getString().contains("*"))) {
                            setting.refresh();
                        }
                    }
                }
            }
        }
    }

}
