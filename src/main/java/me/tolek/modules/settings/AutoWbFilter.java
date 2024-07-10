package me.tolek.modules.settings;

import me.tolek.modules.settings.base.ListSetting;

import java.util.ArrayList;

public class AutoWbFilter extends ListSetting {

    public AutoWbFilter() {
        super("Auto wb filter", 0, "A switch between no filter, a whitelist filter, and a blacklist", null);

        ArrayList<Object> options = new ArrayList<>();
        options.add("None");
        options.add("Whitelist");
        options.add("Blacklist");
        this.setList(options);
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
