package me.tolek.modules.settings.executor;

import me.tolek.event.*;
import me.tolek.interfaces.IScheduler;
import me.tolek.modules.settings.*;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import me.tolek.util.RegexUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

public class AutoWelcomeBackImpl extends EventImpl implements ChatListener, UpdateListener {

    private MinecraftClient client;
    private InstancedValues iv;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(ChatListener.class, this);
        EventManager.getInstance().add(UpdateListener.class, this);
        this.client = MinecraftClient.getInstance();
        this.iv = InstancedValues.getInstance();
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(ChatListener.class, this);
        EventManager.getInstance().remove(UpdateListener.class, this);
    }

    @Override
    public void onMessageAdd(Text message) {
        executeAutoWB(message);
    }

    @Override
    public void onUpdate() {
        iv.timeSinceLastWbInMils += 50;
    }

    public void executeAutoWB(Text message) {
        MflpSettingsList settingsList = MflpSettingsList.getInstance();
        AutoWelcomeBack setting = settingsList.AUTO_WELCOME_BACK;
        String playerName = MinecraftClient.getInstance().getSession().getUsername();
        CustomMessagePerServerList serverMessagesList = CustomMessagePerServerList.getInstance();

        if (message.getString().contains("banana") && !message.getString().contains("To")) {
            if (iv.timeSinceLastInputInMils / 1000 < 30) {
                if (!setting.getState()) {
                    setting.run();
                    setting.refresh();
                    setting.run();
                } else {
                    setting.refresh();
                }
            }
        }

        if (!setting.getState() || iv.timeSinceLastWbInMils / 1000 < settingsList.WB_COOLDOWN.getState()) {
            return;
        }

        if (!message.getString().contains(playerName)) {
            if (!iv.pauseWelcomeBack) {
                boolean connectedToServer = client.getServer() != null;
                boolean joined = RegexUtil.evaluateRegex(settingsList.WB_JOIN_REGEX.getState(), message.getString());
                boolean unAfk = RegexUtil.evaluateRegex(settingsList.WB_UN_AFK_REGEX.getState(), message.getString());

                System.out.println(client.getServer());
                System.out.println(client.getNetworkHandler().getServerInfo().address);
                if (connectedToServer) {
                    joined = RegexUtil.evaluateRegex(serverMessagesList.getMessagesForServer(client.getServer().getServerIp()).value1, message.getString());
                    unAfk = RegexUtil.evaluateRegex(serverMessagesList.getMessagesForServer(client.getServer().getServerIp()).value2, message.getString());
                }

                if (joined || unAfk) {
                    if (!MflpUtil.isFakeMessage(message)) {
                        if (iv.timeSinceLastInputInMils / 1000 < 30 && !iv.isAfk) {
                            ((IScheduler) client).scheduleNonRepeating(settingsList.WB_DELAY.getState(), (b) -> {
                                if (validateRankWhitelist(message, client, settingsList)) {
                                    setting.lastName = message.getString().contains("is no longer AFK.") ?
                                            message.getString().split(" ")[1] : message.getString().split(" ")[0];
                                    if (settingsList.WB_FILTER.stateIndex == 0) {
                                        setting.refresh(message);
                                    } else if (settingsList.WB_FILTER.stateIndex == 1) {
                                        if (isWhitelisted(message.getString())) setting.refresh(message);
                                    } else if (settingsList.WB_FILTER.stateIndex == 2) {
                                        if (!isBlacklisted(message.getString())) setting.refresh(message);
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


}
