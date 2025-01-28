package me.tolek.modules.settings;

import me.tolek.modules.settings.base.FloatSetting;

public class FreeCamMovementSpeedSetting extends FloatSetting {
    public FreeCamMovementSpeedSetting() {
        super("mflp.setting.freeCamMovementSpeed.name", 1.0f, "mflp.setting.freeCamMovementSpeed.tooltip");
        this.setState(1.0f);
    }

    @Override
    public void run() {}
}
