package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;

public class WelcomeBackBlacklist extends StringSetting {

    public WelcomeBackBlacklist() {
        super("mflp.setting.autoWbBlacklist.name", "", "mflp.setting.autoWbBlacklist.tooltip");
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
