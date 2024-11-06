package me.tolek.files;

import me.tolek.ModForLazyPeople;
import me.tolek.event.*;
import me.tolek.modules.macro.Macro;
import me.tolek.modules.macro.MacroList;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.settings.CustomMessagePerServerList;
import me.tolek.modules.settings.CustomPlayerMessageList;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.updateChecker.UpdateChecker;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import net.minecraft.client.option.KeyBinding;
import org.slf4j.Logger;

import java.util.ArrayList;

public class MflpConfigImpl extends EventImpl implements MinecraftQuitListener, MinecraftStartListener {

    private InstancedValues iv;
    private MflpUtil util;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(MinecraftQuitListener.class, this);
        EventManager.getInstance().add(MinecraftStartListener.class, this);
        this.iv = InstancedValues.getInstance();
        this.util = new MflpUtil();
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(MinecraftQuitListener.class, this);
        EventManager.getInstance().remove(MinecraftStartListener.class, this);
    }

    @Override
    public void onQuit() {
        MflpConfigManager configManager = new MflpConfigManager();

        MacroList macroList = MacroList.getInstance();
        MflpSettingsList settings = MflpSettingsList.getInstance();
        AutoRepliesList arl = AutoRepliesList.getInstance();

        if (!util.didSave) {
            configManager.save(macroList.getMacros(), iv.shownWelcomeScreen, settings, arl);
            util.didSave = true;
        }
    }

    @Override
    public void onStartFinished() {}

    @Override
    public void onStart() {
        MflpConfigManager configManager = new MflpConfigManager();

        MacroList macroList = MacroList.getInstance();
        MflpSettingsList settings = MflpSettingsList.getInstance();
        AutoRepliesList arl = AutoRepliesList.getInstance();

        if (!iv.hasLoaded) {
            MflpConfigManager.ModData loadedData = configManager.load();

            if (loadedData != null) {
                if (loadedData.getFileVersion() != null) {
                    ModForLazyPeople.LOGGER.info("Loaded file Version is " + loadedData.getFileVersion() + " applying changes");
                }

                ArrayList<MflpConfigManager.ShortMacro> shortMacros = loadedData.getShortMacros();
                boolean loadedShownWelcomeScreen = loadedData.isShownWelcomeScreen();

                iv.shownWelcomeScreen = loadedShownWelcomeScreen;
                if (!loadedData.getShortMacros().isEmpty() && loadedData.getShortMacros() != null) {
                    macroList.getMacros().clear();
                }

                if (loadedData.getSettings() != null) {
                    settings.getSettings().clear();
                }

                if (loadedData.getAutoReplies() != null) {
                    arl.getAutoReplies().clear();
                }

                for (MflpConfigManager.ShortMacro sm : shortMacros) {
                    KeyBinding kb = new KeyBinding("mflp.keybinding.undefined",
                            sm.key,
                            "mflp.keybindCategory.MFLP");

                    Macro m = new Macro(kb, sm.commands, sm.name, sm.repeatAmt, sm.isUneditable, sm.isOn);
                    m.setKey(sm.key);
                    macroList.addMacro(m);
                }
                if (loadedData.getSettings() != null) {
                    settings.AUTO_WELCOME_BACK = loadedData.getSettings().AUTO_WELCOME_BACK;
                    settings.AUTO_WELCOME = loadedData.getSettings().AUTO_WELCOME;
                    settings.WB_MESSAGE = loadedData.getSettings().WB_MESSAGE;
                    settings.WELCOME_MESSAGE = loadedData.getSettings().WELCOME_MESSAGE;
                    settings.WB_RANK_WHITELIST = loadedData.getSettings().WB_RANK_WHITELIST;
                    settings.WB_DELAY = loadedData.getSettings().WB_DELAY;
                    settings.WB_FILTER = loadedData.getSettings().WB_FILTER;
                    settings.WB_BLACKLIST = loadedData.getSettings().WB_BLACKLIST;
                    settings.WB_WHITELIST = loadedData.getSettings().WB_WHITELIST;
                    settings.WB_COOLDOWN = loadedData.getSettings().WB_COOLDOWN;
                    settings.WB_PLAYER_BLACKLIST = loadedData.getSettings().WB_PLAYER_BLACKLIST;

                    if (loadedData.getFileVersion().equals(iv.getFileVersion())) {
                        // File version is up to date;
                        settings.WB_UN_AFK_REGEX = loadedData.getSettings().WB_UN_AFK_REGEX;
                        settings.WB_JOIN_REGEX = loadedData.getSettings().WB_JOIN_REGEX;
                    }

                    settings.EASY_MSG_SETTING = loadedData.getSettings().EASY_MSG_SETTING;

                    settings.AUTO_IGNORE_WB_MESSAGES = loadedData.getSettings().AUTO_IGNORE_WB_MESSAGES;
                    settings.AUTO_IGNORE_WB_MESSAGES_DURATION = loadedData.getSettings().AUTO_IGNORE_WB_MESSAGES_DURATION;
                    settings.TAB_ICON_TOGGLE = loadedData.getSettings().TAB_ICON_TOGGLE;
                    settings.NAMETAG_ICON_TOGGLE = loadedData.getSettings().NAMETAG_ICON_TOGGLE;
                    settings.SERVER_DISCONNECTION_ACTION = loadedData.getSettings().SERVER_DISCONNECTION_ACTION;

                    settings.DUST_UPDATE_VIEW = loadedData.getSettings().DUST_UPDATE_VIEW;
                    settings.REPEATER_UPDATE_VIEW = loadedData.getSettings().REPEATER_UPDATE_VIEW;
                    settings.COMPARATOR_UPDATE_VIEW = loadedData.getSettings().COMPARATOR_UPDATE_VIEW;
                    settings.OBSERVER_UPDATE_VIEW = loadedData.getSettings().OBSERVER_UPDATE_VIEW;
                    settings.RAILS_UPDATE_VIEW = loadedData.getSettings().RAILS_UPDATE_VIEW;
                }
                if (loadedData.getAutoReplies() != null) {
                    arl.setAutoReplies(loadedData.getAutoReplies());
                }
                if (loadedData.getCustomPlayerMessages() != null) {
                    CustomPlayerMessageList.getInstance().setMessages(loadedData.getCustomPlayerMessages());
                }
                if (loadedData.getCustomServerMessages() != null) {
                    CustomMessagePerServerList.getInstance().setMessagesPerServer(loadedData.getCustomServerMessages());
                }
            }
            iv.hasLoaded = true;
        }

        UpdateChecker uc = new UpdateChecker("tolek-tpr", "ModForLazyPeople", iv.getMflpVersion());
        uc.check();
        Logger logger = ModForLazyPeople.LOGGER;
        if (uc.isUpdateAvailable()) {
            logger.warn("New version available: v" + uc.getLatestVersion() + " (current: v" + uc.currentVersion + ")");
            logger.warn("Download it at " + iv.modrinthUrl);
        }
        if (uc.isUpdateAvailable()) {
            iv.updateAvailable = true;
        }
    }

}
