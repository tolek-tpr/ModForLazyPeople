package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class AutoWBBlacklist extends BooleanSetting {

    public AutoWBBlacklist() {
        super("Auto WB blacklist toggle", false, "A toggle for auto welcome back blacklist");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
