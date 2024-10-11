package me.tolek.modules.settings;

import me.tolek.modules.settings.base.IntegerSetting;

public class AutoIgnoreWbMessagesDuration extends IntegerSetting {

    public AutoIgnoreWbMessagesDuration() {
        super("Auto Ignore wb Duration", 5, "The duration that your messages containing wb will be ignored.");
        this.setState(5);
    }

    @Override
    public void run() {}
}
