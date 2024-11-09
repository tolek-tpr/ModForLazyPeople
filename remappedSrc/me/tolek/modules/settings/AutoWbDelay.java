package me.tolek.modules.settings;

import me.tolek.modules.settings.base.IntegerSetting;

public class AutoWbDelay extends IntegerSetting {

    public AutoWbDelay() {
        super("mflp.setting.autoWbDelay.name", 5, "mflp.setting.autoWbDelay.tooltip");
        this.setState(5);
    }

    @Override
    public void run() {}

}
