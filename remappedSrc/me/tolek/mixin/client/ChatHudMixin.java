package me.tolek.mixin.client;

import me.tolek.event.EventManager;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.settings.AutoWelcome;
import me.tolek.modules.settings.AutoWelcomeBack;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.MflpSetting;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.tolek.event.ChatListener.ChatModifyEvent;

import java.util.Arrays;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("RETURN"))
    public void addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getSession().getUsername() != null) {
            ChatModifyEvent event = new ChatModifyEvent(message);
            EventManager.getInstance().fire(event);
        }
    }
}
