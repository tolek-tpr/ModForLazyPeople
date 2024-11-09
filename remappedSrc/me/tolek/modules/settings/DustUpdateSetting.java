package me.tolek.modules.settings;

import me.tolek.input.Hotkey;
import me.tolek.modules.settings.base.BooleanSetting;

import java.util.HashMap;

public class DustUpdateSetting extends BooleanSetting {

    public DustUpdateSetting() {
        super("mflp.setting.dustUpdate.name", false, "mflp.setting.dustUpdate.tooltip");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
