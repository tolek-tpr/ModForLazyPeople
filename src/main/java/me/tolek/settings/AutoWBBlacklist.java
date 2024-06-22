package me.tolek.settings;

import me.tolek.settings.base.BooleanSetting;

public class AutoWBBlacklist extends BooleanSetting {

    public AutoWBBlacklist() {
        super("Auto WB blacklist toggle", false);
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
