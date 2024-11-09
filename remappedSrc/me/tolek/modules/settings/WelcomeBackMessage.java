package me.tolek.modules.settings;

import me.tolek.modules.settings.base.StringSetting;

public class WelcomeBackMessage extends StringSetting {

    public WelcomeBackMessage() {
        super("mflp.setting.wbMessage.name", "wb", "mflp.setting.wbMessage.tooltip");
        this.setState("wb");
    }

    @Override
    public void run() {

    }

    @Override
    public boolean validateString(String s) {
        return !s.contains("-");
    }
}
