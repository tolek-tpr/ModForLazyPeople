package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class CustomBlockOutline extends BooleanSetting {

    public CustomBlockOutline() {
        super("mflp.setting.customBlockOutline.name", false, "mflp.setting.customBlockOutline.tooltip");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
