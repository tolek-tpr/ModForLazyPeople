package me.tolek.modules.settings;

import me.tolek.modules.settings.base.IntegerSetting;

public class AutoWbCooldown extends IntegerSetting {

    public AutoWbCooldown() {
        super("mflp.setting.autoWbCooldown.name", 0, "mflp.setting.autoWbCooldown.tooltip");
        this.setState(0);
    }

    @Override
    public void run() {}

}
