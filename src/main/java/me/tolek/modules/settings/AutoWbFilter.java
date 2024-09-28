package me.tolek.modules.settings;

import me.tolek.modules.settings.base.ListSetting;

import java.util.ArrayList;

public class AutoWbFilter extends ListSetting {

    public AutoWbFilter() {
        super("Auto wb filter", 0, "A switch between no filter, a whitelist filter, and a blacklist", null);

        this.setList(new ArrayList<>());
        this.addOption("None");
        this.addOption("Whitelist");
        this.addOption("Blacklist");
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
