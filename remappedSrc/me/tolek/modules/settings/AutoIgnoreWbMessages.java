package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class AutoIgnoreWbMessages extends BooleanSetting {
    public AutoIgnoreWbMessages() {
        super("mflp.setting.autoIgnoreWb.name", true, "mflp.setting.autoIgnoreWb.tooltip");
        this.setState(true);
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }
}
