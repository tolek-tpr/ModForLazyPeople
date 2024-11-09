package me.tolek.modules.settings;

import me.tolek.modules.settings.base.ListSetting;

import java.util.ArrayList;

public class FreeCamMovementMode extends ListSetting {
    public static final int CAMERA = 0;
    public static final int PLAYER = 1;

    public FreeCamMovementMode() {
        super("mflp.setting.freeCamMovementMode.name", 0, "mflp.setting.freeCamMovementMode.tooltip", null);

        this.setList(new ArrayList<>());
        this.addOption("mflp.camera");
        this.addOption("mflp.player");
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
