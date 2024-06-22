package me.tolek.settings;

import me.tolek.settings.base.BooleanSetting;

public class AutoWBWhitelist extends BooleanSetting {

    public AutoWBWhitelist() {
        super("Auto WB whitelist toggle", false);
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
