package me.tolek.modules.settings;

import me.tolek.modules.settings.base.ListSetting;

import java.util.ArrayList;

public class AutoWbRanksSetting extends ListSetting {



    public AutoWbRanksSetting() {
        super("Auto WB rank setting", 0, "Rank filter for auto wb", null);

        ArrayList<Object> options = new ArrayList<>();
        options.add("Everyone");
        options.add("Member +");
        options.add("Devoted +");
        options.add("Trusted +");
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
