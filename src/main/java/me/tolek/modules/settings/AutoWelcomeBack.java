package me.tolek.modules.settings;

import me.tolek.interfaces.TimerInterface;
import me.tolek.modules.settings.base.BooleanSetting;
import me.tolek.modules.settings.base.MflpSetting;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

public class AutoWelcomeBack extends BooleanSetting {

    public AutoWelcomeBack() {
        super("Auto welcome back", false, "Automatically says wb when someone comes back from being afk or joins synergy");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
        // if (this.getState == false) then do something
    }

    @Override
    public void refresh() {
        if (this.getState() && MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage(MflpSettingsList.getInstance().WB_MESSAGE.getState());
        }
    }

    public boolean validateRankWhitelist(Text message, MinecraftClient client, MflpSettingsList settingsList) {
        /*
            T: #30BAA3
            D: #F29C36
            M: #C7CCD7
            G: #565F68
         */
        if (client.player != null && client.player.networkHandler.getPlayerList() != null) {
            for (PlayerListEntry p : client.player.networkHandler.getPlayerList()) {
                if (p.getDisplayName() == null || p.getDisplayName().getSiblings() == null) return true;
                if (message.getString().contains(p.getDisplayName().getSiblings().get(0).getString())) {
                    for (Text t : p.getDisplayName().getSiblings()) {
                        if (t.getStyle() == null || t.getStyle().getColor() == null || t.getStyle().getColor().getHexCode() == null) return true;
                        if (t.getStyle().getColor().getHexCode().equals("#30BAA3")) {
                            return true;
                        } else if (t.getStyle().getColor().getHexCode().equals("#F29C36")) {
                            if (settingsList.WB_RANK_WHITELIST.stateIndex > 2) {
                                return false;
                            }
                        } else if (t.getStyle().getColor().getHexCode().equals("#C7CCD7")) {
                            if (settingsList.WB_RANK_WHITELIST.stateIndex > 1) {
                                return false;
                            }
                        } else if (t.getStyle().getColor().getHexCode().equals("#565F68")) {
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

    public boolean isWhitelisted(String message) {
        MflpSettingsList settingsList = MflpSettingsList.getInstance();

        String[] whitelistedNames = settingsList.WB_WHITELIST.getState().split(" ");
        for (String wn : whitelistedNames) {
            wn = wn.toLowerCase();
            message = message.toLowerCase();
            if (message.contains(wn)) return true;
        }

        return false;
    }

    public boolean isBlacklisted(String message) {
        MflpSettingsList settingsList = MflpSettingsList.getInstance();

        String[] blacklistedNames = settingsList.WB_BLACKLIST.getState().split(" ");
        for (String bn : blacklistedNames) {
            bn = bn.toLowerCase();
            message = message.toLowerCase();
            if (message.contains(bn)) return true;
        }

        return false;
    }

    public void executeAutoWB(Text message, MflpSetting setting, String playerName, MinecraftClient client) {
        InstancedValues iv = InstancedValues.getInstance();
        MflpSettingsList settingsList = MflpSettingsList.getInstance();

        if (message.getString().contains("banana") && !message.getString().contains("To:")) {
            if (iv.timeSinceLastInputInMils / 1000 < 30) {
                setting.refresh();
            }
        }

        if (!message.getString().contains(playerName)) {
            if (!iv.pauseWelcomeBack) {
                if (message.getString().contains("has joined.") || message.getString().contains("is no longer AFK.")) {
                    if (!MflpUtil.isFakeMessage(message)) {
                        if (iv.timeSinceLastInputInMils / 1000 < 30 && !iv.isAfk) {
                            ((TimerInterface) client).scheduleNonRepeating(settingsList.WB_COOLDOWN.getState(), (b) -> {
                                if (validateRankWhitelist(message, client, settingsList)) {
                                    if (settingsList.WB_FILTER.stateIndex == 0) {
                                        setting.refresh();
                                    } else if (settingsList.WB_FILTER.stateIndex == 1) {
                                        if (isWhitelisted(message.getString())) setting.refresh();
                                    } else if (settingsList.WB_FILTER.stateIndex == 2) {
                                        if (!isBlacklisted(message.getString())) setting.refresh();
                                    }
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
