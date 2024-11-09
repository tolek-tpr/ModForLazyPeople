package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class RepeaterUpdateSetting extends BooleanSetting {

    public RepeaterUpdateSetting() {
        super("mflp.setting.repeaterUpdate.name", false, "mflp.setting.repeaterUpdate.tooltip");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
