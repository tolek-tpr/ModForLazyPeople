package me.tolek.modules.settings;

import me.tolek.modules.settings.base.MflpSetting;

import java.util.ArrayList;

public class MflpSettingsList {

    private static MflpSettingsList instance;


    public AutoWelcomeBack AUTO_WELCOME_BACK = new AutoWelcomeBack();
    public AutoWelcome AUTO_WELCOME = new AutoWelcome();
    public AutoWbFilter WB_FILTER = new AutoWbFilter();
    public WelcomeBackWhitelist WB_WHITELIST = new WelcomeBackWhitelist();
    public WelcomeBackBlacklist WB_BLACKLIST = new WelcomeBackBlacklist();
    public WelcomeBackMessage WB_MESSAGE = new WelcomeBackMessage();
    public WelcomeMessage WELCOME_MESSAGE = new WelcomeMessage();
    public AutoWbRanksSetting WB_RANK_WHITELIST = new AutoWbRanksSetting();
    public AutoWbDelay WB_DELAY = new AutoWbDelay();
    public AutoWbCooldown WB_COOLDOWN = new AutoWbCooldown();
    public WbPlayerBlacklist WB_PLAYER_BLACKLIST = new WbPlayerBlacklist();
    public PerPlayerMessageButtonSetting PPMBS = new PerPlayerMessageButtonSetting();

    public MflpTabIconSetting TAB_ICON_TOGGLE = new MflpTabIconSetting();
    public MflpNametagIconSetting NAMETAG_ICON_TOGGLE = new MflpNametagIconSetting();

    public AutoIgnoreWbMessages AUTO_IGNORE_WB_MESSAGES = new AutoIgnoreWbMessages();
    public AutoIgnoreWbMessagesDuration AUTO_IGNORE_WB_MESSAGES_DURATION = new AutoIgnoreWbMessagesDuration();
    public EasyMsgSetting EASY_MSG_SETTING = new EasyMsgSetting();

    public ServerDisconnectionActionSetting SERVER_DISCONNECTION_ACTION = new ServerDisconnectionActionSetting();

    public AutoWbJoinRegex WB_JOIN_REGEX = new AutoWbJoinRegex();
    public AutoWbUnAfkRegex WB_UN_AFK_REGEX = new AutoWbUnAfkRegex();
    public CustomMessagePerServerSetting WB_REGEX_SETTINGS = new CustomMessagePerServerSetting();

    public DustUpdateSetting DUST_UPDATE_VIEW = new DustUpdateSetting();
    public RepeaterUpdateSetting REPEATER_UPDATE_VIEW = new RepeaterUpdateSetting();
    public ComparatorUpdateSetting COMPARATOR_UPDATE_VIEW = new ComparatorUpdateSetting();
    public ObserverUpdateSetting OBSERVER_UPDATE_VIEW = new ObserverUpdateSetting();
    public RailUpdateSetting RAILS_UPDATE_VIEW = new RailUpdateSetting();

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
        settings.add(WB_DELAY);
        settings.add(WB_MESSAGE);
        settings.add(WELCOME_MESSAGE);
        settings.add(WB_COOLDOWN);
        settings.add(WB_FILTER);
        settings.add(WB_WHITELIST);
        settings.add(WB_BLACKLIST);
        settings.add(WB_PLAYER_BLACKLIST);

        settings.add(TAB_ICON_TOGGLE);
        settings.add(NAMETAG_ICON_TOGGLE);

        settings.add(AUTO_IGNORE_WB_MESSAGES);
        settings.add(AUTO_IGNORE_WB_MESSAGES_DURATION);

        settings.add(EASY_MSG_SETTING);

        settings.add(SERVER_DISCONNECTION_ACTION);

        settings.add(PPMBS);

        settings.add(WB_REGEX_SETTINGS);
        settings.add(WB_JOIN_REGEX);
        settings.add(WB_UN_AFK_REGEX);

        settings.add(DUST_UPDATE_VIEW);
        settings.add(REPEATER_UPDATE_VIEW);
        settings.add(COMPARATOR_UPDATE_VIEW);
        settings.add(OBSERVER_UPDATE_VIEW);
        settings.add(RAILS_UPDATE_VIEW);

        return settings;
    }

}
