package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class ComparatorUpdateSetting extends BooleanSetting {

    public ComparatorUpdateSetting() {
        super("mflp.setting.comparatorUpdate.name", false, "mflp.setting.comparatorUpdate.tooltip");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
