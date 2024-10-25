package me.tolek.modules.settings;

import me.tolek.modules.settings.base.ListSetting;

import java.util.ArrayList;

public class ServerDisconnectionActionSetting extends ListSetting {

    public ServerDisconnectionActionSetting() {
        super("mflp.setting.serverDisconnectionAction.name", 0, "mflp.setting.serverDisconnectionAction.tooltip", null);

        this.setList(new ArrayList<>());
        this.addOption("mflp.setting.serverDisconnectionAction.chatMessage");
        this.addOption("mflp.setting.serverDisconnectionAction.toast");
        this.addOption("mflp.setting.serverDisconnectionAction.screen");
    }

    @Override
    public void run() {
        if (stateIndex == getList().size() - 1) {
            stateIndex = 0;
        } else {
            stateIndex++;
        }
    }
}
