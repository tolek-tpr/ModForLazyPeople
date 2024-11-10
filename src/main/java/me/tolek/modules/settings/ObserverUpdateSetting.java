package me.tolek.modules.settings;

import me.tolek.modules.settings.base.BooleanSetting;

public class ObserverUpdateSetting extends BooleanSetting {

    public ObserverUpdateSetting() {
        super("mflp.setting.observerUpdate.name", false, "mflp.setting.observerUpdate.tooltip");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
