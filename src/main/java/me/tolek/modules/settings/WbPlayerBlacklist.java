package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;

public class WbPlayerBlacklist extends StringSetting {

    public WbPlayerBlacklist() {
        super("Player name wb blacklist", "", "Stops the player from mentioning specific names when using %p");
        this.setState("");
    }

    @Override
    public void run() {

    }

    @Override
    public boolean validateString(String s) { return !s.contains("-"); }

}
