package me.tolek.mixin.client;

import me.tolek.interfaces.TimerInterface;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.modules.settings.base.MflpSetting;
import me.tolek.util.InstancedValues;
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
                wn = wn.toLowerCase();
                message = message.toLowerCase();
                if (message.contains(wn)) return true;
            }
        }
        return false;
    }

    public boolean isBlacklisted(String message) {
        if (settingsList.AUTO_WB_BLACKLIST.getState()) {
            String[] blacklistedNames = settingsList.WB_BLACKLIST.getState().split(" ");
            for (String bn : blacklistedNames) {
                bn = bn.toLowerCase();
                message = message.toLowerCase();
                if (message.contains(bn)) return true;
            }
        }
        return false;
    }

    public boolean validateRankWhitelist(Text message) {
        /*
            T: #30BAA3
            D: #F29C36
            M: #C7CCD7
            G: #565F68
         */
        if (this.client.player != null && this.client.player.networkHandler.getPlayerList() != null) {
            for (PlayerListEntry p : this.client.player.networkHandler.getPlayerList()) {
                if (p.getDisplayName() == null || p.getDisplayName().getSiblings() == null) return true;
                if (message.getString().contains(p.getDisplayName().getSiblings().get(0).getString())) {
                    for (Text t : p.getDisplayName().getSiblings()) {
                        if (t.getStyle() == null || t.getStyle().getColor() == null || t.getStyle().getColor().getHexCode() == null) return true;
                        if (t.getStyle().getColor().getHexCode().equals("#30BAA3")) {
                            return true;
                        } else if (t.getStyle().getColor().getHexCode().equals("#F29C36")) {
                            System.out.println(settingsList.WB_RANK_WHITELIST.stateIndex);
                            if (settingsList.WB_RANK_WHITELIST.stateIndex > 2) {
                                return false;
                            }
                        } else if (t.getStyle().getColor().getHexCode().equals("#C7CCD7")) {
                            System.out.println(settingsList.WB_RANK_WHITELIST.stateIndex);
                            if (settingsList.WB_RANK_WHITELIST.stateIndex > 1) {
                                return false;
                            }
                        } else if (t.getStyle().getColor().getHexCode().equals("#565F68")) {
                            System.out.println(settingsList.WB_RANK_WHITELIST.stateIndex);
                            if (settingsList.WB_RANK_WHITELIST.stateIndex > 0) {
                                return false;
                            }
                        } else if (settingsList.WB_RANK_WHITELIST.stateIndex == 0) return true;
                    }
                }
            }
        }
        return true;
    }

    public void executeAutoWB(Text message, MflpSetting setting, String playerName) {
        if (message.getString().contains("banana") && !message.getString().contains("To:")) {
            if (iv.timeSinceLastInputInMils / 1000 < 30) {
                setting.refresh();
            }
        }

        if (!message.getString().contains(playerName)) {
            if (!iv.pauseWelcomeBack) {
                if (message.getString().contains("has joined.") || message.getString().contains("is no longer AFK.")) {
                    if (!isFakeMessage(message)) {
                        if (iv.timeSinceLastInputInMils / 1000 < 30 && !iv.isAfk) {
                            ((TimerInterface) client).scheduleNonRepeating(settingsList.WB_COOLDOWN.getState(), (b) -> {
                                if (validateRankWhitelist(message)) {
                                    if (settingsList.AUTO_WB_WHITELIST.getState()) {
                                        if (isWhitelisted(message.getString())) setting.refresh();
                                    } else if (settingsList.AUTO_WB_BLACKLIST.getState()) {
                                        if (!isBlacklisted(message.getString())) setting.refresh();
                                    } else setting.refresh();
                                }
                            });

                        }
                    }
                }
            } else {
                iv.pauseWelcomeBack = false;
            }
        }
    }

}
