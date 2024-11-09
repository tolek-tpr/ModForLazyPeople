package me.tolek.modules.settings;

import me.tolek.modules.settings.base.IntegerSetting;

public class AutoIgnoreWbMessagesDuration extends IntegerSetting {

    public AutoIgnoreWbMessagesDuration() {
        super("mflp.setting.autoIgnoreWbDuration.name", 5, "mflp.setting.autoIgnoreWbDuration.tooltip");
        this.setState(5);
    }

    @Override
    public void run() {}
}
