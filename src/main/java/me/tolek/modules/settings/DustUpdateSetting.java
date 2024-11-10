package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class DustUpdateSetting extends BooleanSetting {

    public DustUpdateSetting() {
        super("mflp.setting.dustUpdate.name", false, "mflp.setting.dustUpdate.tooltip");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
