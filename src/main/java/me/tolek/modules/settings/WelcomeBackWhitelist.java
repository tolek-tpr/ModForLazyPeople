package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;

public class WelcomeBackWhitelist extends StringSetting {

    public WelcomeBackWhitelist() {
        super("Auto welcome back whitelist", "", "The list of names to say wb to when using auto wb, separated by a space");
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
