package me.tolek.modules.settings;

import me.tolek.modules.betterFreeCam.CameraEntity;
import me.tolek.modules.settings.base.BooleanSetting;

import java.util.function.Consumer;

public class FreeCamEnabled extends BooleanSetting {
    public FreeCamEnabled() {
        super("mflp.setting.freeCamEnabled.name", false, "mflp.setting.freeCamEnabled.tooltip");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
        CameraEntity.setCameraState(this.getState());
    }
}
