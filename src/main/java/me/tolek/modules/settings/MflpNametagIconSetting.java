package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class MflpNametagIconSetting extends BooleanSetting {

    public MflpNametagIconSetting() {
        super("mflp.setting.nametagIconToggle.name", true, "mflp.setting.nametagIconToggle.tooltip");
        this.setState(true);
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
