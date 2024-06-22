package me.tolek.settings;

import me.tolek.settings.base.StringSetting;
import net.minecraft.text.Text;

public class WelcomeBackBlacklist extends StringSetting {

    public WelcomeBackBlacklist() {
        super("Auto welcome back blacklist", "", "The list of names to ignore people when using auto wb, separated by a space");
        this.setState("");
    }

    @Override
    public void run() {

    }

    @Override
    public boolean validateString(String toValidate) {
        return !toValidate.contains("-");
    }
}
