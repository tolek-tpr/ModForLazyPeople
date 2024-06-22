package me.tolek.settings;

import me.tolek.settings.base.BooleanSetting;
import net.minecraft.text.Text;

public class AutoWBBlacklist extends BooleanSetting {

    public AutoWBBlacklist() {
        super("Auto WB blacklist toggle", false, "A toggle for auto welcome back blacklist");
    }

    @Override
    public void run() {
        this.setState(!this.getState());
    }

}
