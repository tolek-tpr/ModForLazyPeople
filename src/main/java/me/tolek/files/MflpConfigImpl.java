package me.tolek.files;

import me.tolek.event.*;
import me.tolek.modules.Macro.Macro;
import me.tolek.modules.Macro.MacroList;
import me.tolek.modules.autoReply.AutoRepliesList;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.updateChecker.UpdateChecker;
import me.tolek.util.InstancedValues;
import me.tolek.util.MflpUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import java.util.ArrayList;

public class MflpConfigImpl extends EventImpl implements MinecraftQuitListener, MinecraftStartListener {

    private InstancedValues iv;
    private MinecraftClient client;
    private MflpUtil util;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(MinecraftQuitListener.class, this);
        EventManager.getInstance().add(MinecraftStartListener.class, this);
        this.client = MinecraftClient.getInstance();
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
    public void onStart() {
        MflpConfigManager configManager = new MflpConfigManager();

        MacroList macroList = MacroList.getInstance();
        MflpSettingsList settings = MflpSettingsList.getInstance();
        AutoRepliesList arl = AutoRepliesList.getInstance();

        if (!iv.hasLoaded) {
            MflpConfigManager.ModData loadedData = configManager.load();

            if (loadedData != null) {
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
                    //settings.AUTO_PLOT_HOME = loadedData.getSettings().AUTO_PLOT_HOME;
                    settings.WB_FILTER = loadedData.getSettings().WB_FILTER;
                    settings.WB_BLACKLIST = loadedData.getSettings().WB_BLACKLIST;
                    settings.WB_WHITELIST = loadedData.getSettings().WB_WHITELIST;
                    //settings.PLAYER_ESP = loadedData.getSettings().PLAYER_ESP;
                    settings.WB_COOLDOWN = loadedData.getSettings().WB_COOLDOWN;
                }
                if (loadedData.getAutoReplies() != null) {
                    arl.setAutoReplies(loadedData.getAutoReplies());
                }
            }
            iv.hasLoaded = true;
        }

        UpdateChecker uc = new UpdateChecker("tolek-tpr", "ModForLazyPeople", iv.version);
        uc.check();
        if (uc.isUpdateAvailable()) {
            iv.updateAvailable = true;
        }
    }

}
