package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class AutoWBWhitelist extends BooleanSetting {

    public AutoWBWhitelist() {
        super("Auto WB whitelist toggle", false, "A toggle for auto welcome back whitelist");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
