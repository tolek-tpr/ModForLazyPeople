package me.tolek.settings;

import me.tolek.settings.base.StringSetting;

public class WelcomeBackWhitelist extends StringSetting {

    public WelcomeBackWhitelist() {
        super("Auto welcome back whitelist", "");
        this.setState("");
    }

    @Override
    public void run() {

    }
}
