package me.tolek.mixin.client;

import me.tolek.interfaces.TimerInterface;
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
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    private MinecraftClient client = MinecraftClient.getInstance();
    private MflpSettingsList settingsList = MflpSettingsList.getInstance();
    private AutoRepliesList autoReplies = AutoRepliesList.getInstance();
    private InstancedValues iv = InstancedValues.getInstance();

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("RETURN"))
    public void addMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getSession().getUsername() != null) {
            String playerName = MinecraftClient.getInstance().getSession().getUsername();

            for (MflpSetting setting : settingsList.getSettings()) {
                if (setting.getName().equals("Auto welcome back")) {
                    AutoWelcomeBack s = (AutoWelcomeBack) setting;
                    s.executeAutoWB(message, setting, playerName, client);
                } else if (setting.getName().equals("Auto welcome")) {
                    AutoWelcome s = (AutoWelcome) setting;
                    s.executeAutoWelcome(message, setting, playerName);
                }
            }

            if (!MflpUtil.isFakeMessage(message) && message.getString().contains("[!] You are now AFK.")) {
                iv.isAfk = true;
            }
            if (!MflpUtil.isFakeMessage(message) && message.getString().contains("[!] You are no longer AFK.")) {
                iv.isAfk = false;
            }

            handleAutoReply(message);
        }
    }

    public void handleAutoReply(Text a) {
        String message = a.getString();

        autoReplies.getAutoReplies().forEach((ar) -> {
            ar.getKeywords().forEach((String s) -> {
                if (message.contains(s) && ar.isTurnedOn()) {
                    int index = (int) Math.round(Math.random() * ar.getReplies().size()) - 1;
                    String reply = ar.getReplies().get(index == -1 ? 0 : index);

                    if (reply.startsWith("/") && reply != null) {
                        MinecraftClient.getInstance().player.networkHandler.sendCommand(reply.substring(1));
                    } else {
                        MinecraftClient.getInstance().player.networkHandler.sendChatMessage(reply);
                    }
                }

            });
        });
    }
}
