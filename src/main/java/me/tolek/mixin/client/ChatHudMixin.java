package me.tolek.mixin.client;

import me.tolek.event.EventManager;
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

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("RETURN"))
    public void addMessage(Text message, MessageSignatureData signatureData, MessageIndicator indicator, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getSession().getUsername() != null) {
            ChatModifyEvent event = new ChatModifyEvent(message);
            EventManager.getInstance().fire(event);
        }
    }
}
