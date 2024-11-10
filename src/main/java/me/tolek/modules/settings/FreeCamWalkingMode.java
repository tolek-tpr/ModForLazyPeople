package me.tolek.modules.settings;

import me.tolek.modules.settings.base.ListSetting;

import java.util.ArrayList;

public class FreeCamWalkingMode extends ListSetting {
    public static final int NO_CLIP = 0;
    public static final int WALK = 1;

    public FreeCamWalkingMode() {
        super("mflp.setting.freeCamWalkingMode.name", 0, "mflp.setting.freeCamWalkingMode.tooltip", null);

        this.setList(new ArrayList<>());
        this.addOption("mflp.setting.freeCamWalkingMode.noClip");
        this.addOption("mflp.setting.freeCamWalkingMode.walk");
    }

    @Override
    public void run() {
        if (stateIndex == getList().size() - 1) {
            stateIndex = 0;
        } else {
            stateIndex = stateIndex + 1;
        }
    }
}
