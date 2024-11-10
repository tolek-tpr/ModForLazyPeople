package me.tolek.modules.settings;

import me.tolek.modules.settings.base.FloatSetting;

public class FreeCamMovementSpeed extends FloatSetting {
    public FreeCamMovementSpeed() {
        super("mflp.setting.freeCamMovementSpeed.name", 1.0f, "mflp.setting.freeCamMovementSpeed.tooltip");
        this.setState(1.0f);
    }

    @Override
    public void run() {}
}
