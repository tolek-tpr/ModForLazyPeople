package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;

public class WbPlayerBlacklist extends StringSetting {

    public WbPlayerBlacklist() {
        super("mflp.setting.playerNameWbBlacklist.name", "", "mflp.setting.playerNameWbBlacklist.tooltip");
        this.setState("");
    }

    @Override
    public void run() {

    }

    @Override
    public boolean validateString(String s) { return !s.contains("-"); }

}
