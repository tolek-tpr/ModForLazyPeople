package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class MflpTabIconSetting extends BooleanSetting {

    public MflpTabIconSetting() {
        super("mflp.setting.tabIconToggle.name", true, "mflp.setting.tabIconToggle.tooltip");
        this.setState(true);
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
