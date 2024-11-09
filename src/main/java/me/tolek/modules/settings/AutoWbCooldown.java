package me.tolek.modules.settings;

import me.tolek.modules.settings.base.IntegerSetting;

public class AutoWbCooldown extends IntegerSetting {

    public AutoWbCooldown() {
        super("mflp.setting.autoWbCooldown.name", 1, "mflp.setting.autoWbCooldown.tooltip");
        this.setState(1);
    }

    @Override
    public void run() {}

}
