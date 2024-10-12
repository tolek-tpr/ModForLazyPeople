package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class AutoIgnoreWbMessages extends BooleanSetting {
    public AutoIgnoreWbMessages() {
        super("Auto Ignore wb", true, "Automatically ignore wb messages within a few seconds of someone joining");
        this.setState(true);
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }
}
