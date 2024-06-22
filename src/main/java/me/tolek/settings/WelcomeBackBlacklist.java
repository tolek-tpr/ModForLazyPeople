package me.tolek.settings;

import me.tolek.settings.base.StringSetting;

public class WelcomeBackBlacklist extends StringSetting {

    public WelcomeBackBlacklist() {
        super("Auto welcome back blacklist", "");
        this.setState("");
    }

    @Override
    public void run() {

    }

    @Override
    public boolean validateString(String toValidate) {
        return !toValidate.contains("-");
    }
}
