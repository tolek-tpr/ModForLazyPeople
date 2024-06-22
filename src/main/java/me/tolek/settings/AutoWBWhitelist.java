package me.tolek.settings;

import me.tolek.settings.base.BooleanSetting;
import net.minecraft.text.Text;

public class AutoWBWhitelist extends BooleanSetting {

    public AutoWBWhitelist() {
        super("Auto WB whitelist toggle", false, "A toggle for auto welcome back whitelist");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
