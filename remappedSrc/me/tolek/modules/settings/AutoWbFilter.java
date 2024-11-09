package me.tolek.modules.settings;

import me.tolek.modules.settings.base.ListSetting;

import java.util.ArrayList;

public class AutoWbFilter extends ListSetting {

    public AutoWbFilter() {
        super("mflp.setting.autoWbFilter.name", 0, "mflp.setting.autoWbFilter.tooltip", null);

        this.setList(new ArrayList<>());
        this.addOption("mflp.setting.autoWbFilter.none");
        this.addOption("mflp.setting.autoWbFilter.whitelist");
        this.addOption("mflp.setting.autoWbFilter.blacklist");
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
