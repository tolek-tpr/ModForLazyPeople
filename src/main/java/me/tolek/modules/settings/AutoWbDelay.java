package me.tolek.modules.settings;

import me.tolek.modules.settings.base.IntegerSetting;

public class AutoWbDelay extends IntegerSetting {

    public AutoWbDelay() {
        super("Auto welcome back delay", 4, "How long the bot should wait before saying wb after someone joins in GAME TICKS");
        this.setState(5);
    }

    @Override
    public void run() {}

    @Override
    public boolean validateInt(String toValidate) {
        String regex = "\\d+";
        try {
            return toValidate.matches(regex) && Integer.parseInt(toValidate) > 4
                    && Integer.parseInt(toValidate) < Integer.MAX_VALUE;
        } catch (Exception e) {
            return false;
        }
    }

}
