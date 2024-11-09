package me.tolek.modules.settings;

import me.tolek.modules.settings.base.ListSetting;

import java.util.ArrayList;

public class AutoWbRanksSetting extends ListSetting {



    public AutoWbRanksSetting() {
        super("mflp.setting.autoWbRank.name", 0, "mflp.setting.autoWbRank.tooltip", null);

        this.setList(new ArrayList<>());
        this.addOption("mflp.setting.autoWbRank.everyone");
        this.addOption("mflp.setting.autoWbRank.memberPlus");
        this.addOption("mflp.setting.autoWbRank.devotedPlus");
        this.addOption("mflp.setting.autoWbRank.trustedPlus");
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
