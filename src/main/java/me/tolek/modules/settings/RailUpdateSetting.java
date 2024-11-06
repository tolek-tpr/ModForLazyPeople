package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class RailUpdateSetting extends BooleanSetting {

    public RailUpdateSetting() {
        super("mflp.setting.railUpdates.name", false, "mflp.setting.railUpdates.tooltip");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
