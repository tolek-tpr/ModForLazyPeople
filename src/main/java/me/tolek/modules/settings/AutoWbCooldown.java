package me.tolek.modules.settings;

import me.tolek.modules.settings.base.IntegerSetting;

public class AutoWbCooldown extends IntegerSetting {

    public AutoWbCooldown() {
        super("Auto welcome back cooldown", 0, "The cooldown between the bot saying wb in seconds");
        this.setState(0);
    }

    @Override
    public void run() {}

    @Override
    public boolean validateInt(String toValidate) {
        String regex = "\\d+";
        this.setTooltip("The cooldown between the bot saying wb in seconds");
        try {
            return toValidate.matches(regex) && Integer.parseInt(toValidate) < Integer.MAX_VALUE;
        } catch (Exception e) {
            return false;
        }
    }

}
