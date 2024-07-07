package me.tolek.modules.settings;

import me.tolek.modules.settings.base.MflpSetting;

import java.util.ArrayList;

public class MflpSettingsList {

    private static MflpSettingsList instance;


    public AutoWelcomeBack AUTO_WELCOME_BACK = new AutoWelcomeBack();
    public AutoWelcome AUTO_WELCOME = new AutoWelcome();
    public AutoPlotHome AUTO_PLOT_HOME = new AutoPlotHome();
    public AutoWBWhitelist AUTO_WB_WHITELIST = new AutoWBWhitelist();
    public WelcomeBackWhitelist WB_WHITELIST = new WelcomeBackWhitelist();
    public AutoWBBlacklist AUTO_WB_BLACKLIST = new AutoWBBlacklist();
    public WelcomeBackBlacklist WB_BLACKLIST = new WelcomeBackBlacklist();
    public WelcomeBackMessage WB_MESSAGE = new WelcomeBackMessage();
    public WelcomeMessage WELCOME_MESSAGE = new WelcomeMessage();
    public AutoWbRanksSetting WB_RANK_WHITELIST = new AutoWbRanksSetting();
    public AutoWbCooldown WB_COOLDOWN = new AutoWbCooldown();
    public PlayerEsp PLAYER_ESP = new PlayerEsp();

    private MflpSettingsList() {

    }

    public static MflpSettingsList getInstance() {
        if (instance == null) MflpSettingsList.instance = new MflpSettingsList();
        return instance;
    }

    public ArrayList<MflpSetting> getSettings() {
        ArrayList<MflpSetting> settings = new ArrayList<>();
        settings.add(AUTO_WELCOME_BACK);
        settings.add(AUTO_WELCOME);
        settings.add(WB_RANK_WHITELIST);
        settings.add(WB_COOLDOWN);
        settings.add(WB_MESSAGE);
        settings.add(WELCOME_MESSAGE);
        //settings.add(AUTO_PLOT_HOME);
        settings.add(AUTO_WB_WHITELIST);
        settings.add(AUTO_WB_BLACKLIST);
        settings.add(WB_WHITELIST);
        settings.add(WB_BLACKLIST);
        settings.add(PLAYER_ESP);
        return settings;
    }

}
