package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;

public class WelcomeBackWhitelist extends StringSetting {

    public WelcomeBackWhitelist() {
        super("mflp.setting.autoWbWhitelist.name", "", "mflp.setting.autoWbWhitelist.tooltip");
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
