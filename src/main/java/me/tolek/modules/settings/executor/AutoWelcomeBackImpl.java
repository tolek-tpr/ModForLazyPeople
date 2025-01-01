package me.tolek.modules.settings.executor;

import me.tolek.event.*;
import me.tolek.modules.settings.*;
import me.tolek.util.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;

import java.util.LinkedList;
import java.util.Queue;

@Environment(EnvType.CLIENT)
public class AutoWelcomeBackImpl extends EventImpl implements ChatListener, UpdateListener {

    private MinecraftClient client;
    private InstancedValues iv;

    private final Queue<Text> messagesToRespondTo = new LinkedList<>();

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
        if (shouldRespond(message)) {
            if (messagesToRespondTo.isEmpty())
                iv.timeUntilNextWbMillis = MflpSettingsList.getInstance().WB_COOLDOWN.getState();

            messagesToRespondTo.add(message);
        }
    }

    private boolean shouldRespond(Text message) {
        MflpSettingsList settingsList = MflpSettingsList.getInstance();
        String playerName = MinecraftClient.getInstance().getSession().getUsername();
        CustomMessagePerServerList serverMessagesList = CustomMessagePerServerList.getInstance();
        AutoWelcomeBack setting = settingsList.AUTO_WELCOME_BACK;

        if (iv.timeSinceLastInputMillis / 1000 > 30)
            return false;

        if (message.getString().contains("meWhen911") && !message.getString().contains("To")) { // WHY BEAR WHY DO YOU WANT THIS :sob:
            if (!setting.getState()) {
                setting.run();
                setting.refresh();
                setting.run();
            } else {
                setting.refresh();
            }

            return false; // Already responded...
        }

        boolean connectedToServer = client.getNetworkHandler() != null && client.getNetworkHandler().getServerInfo() != null;

        String wb_join_regex = settingsList.WB_JOIN_REGEX.getState();
        String wb_un_afk_regex = settingsList.WB_UN_AFK_REGEX.getState();
        String usernameRegex = "[a-zA-Z0-9_]{3,16}";

        wb_join_regex = wb_join_regex.replaceAll("%u", usernameRegex);
        wb_un_afk_regex = wb_un_afk_regex.replaceAll("%u", usernameRegex);

        boolean joined = RegexUtil.evaluateRegex(wb_join_regex, message.getString());
        boolean unAfk = RegexUtil.evaluateRegex(wb_un_afk_regex, message.getString());

        if (connectedToServer) {
            ServerInfo serverInfo = client.getNetworkHandler().getServerInfo();
            Tuple<String, String> messagesForServer = serverMessagesList.getMessagesForServer(serverInfo.address);

            if (messagesForServer != null) {
                joined = RegexUtil.evaluateRegex(messagesForServer.value1, message.getString());
                unAfk = RegexUtil.evaluateRegex(messagesForServer.value2, message.getString());
            }
        }

        if (iv.pauseWelcomeBack) {
            iv.pauseWelcomeBack = false;
            return false;
        }

        if (message.getString().contains(playerName) ||
                !(joined || unAfk) ||
                MflpUtil.isFakeMessage(message) ||
                iv.isAfk ||
                !validateRankWhitelist(message, client, settingsList))
            return false;

        if (settingsList.WB_FILTER.stateIndex == 0)
            return true;

        if (settingsList.WB_FILTER.stateIndex == 1)
            return isWhitelisted(message.getString());

        // settingsList.WB_FILTER.stateIndex = 2
        return !isBlacklisted(message.getString());

    }

    @Override
    public void onUpdate() {
        iv.timeUntilNextWbMillis -= 50;
        iv.timeSinceLastWbMillis += 50;

        if (!messagesToRespondTo.isEmpty() && iv.timeUntilNextWbMillis <= 0) {
            executeAutoWB(messagesToRespondTo.remove());
        }
    }

    public void executeAutoWB(Text message) {
        if (!ChatUtil.canSendWb())
            return;

        MflpSettingsList settingsList = MflpSettingsList.getInstance();
        AutoWelcomeBack setting = settingsList.AUTO_WELCOME_BACK;

        setting.lastName = message.getString().contains("is no longer AFK.") ?
                message.getString().split(" ")[1] : message.getString().split(" ")[0];
        setting.refresh(message);
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
