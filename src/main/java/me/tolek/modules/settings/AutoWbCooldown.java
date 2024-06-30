package me.tolek.modules.settings;

import me.tolek.modules.settings.base.IntegerSetting;

public class AutoWbCooldown extends IntegerSetting {

    public AutoWbCooldown() {
        super("Auto welcome back cooldown", 4, "How long the bot should wait before saying wb after someone joins in GAME TICKS");
        this.setState(5);
    }

    @Override
    public void run() {}

    @Override
    public boolean validateInt(String toValidate) {
        String regex = "\\d+";
        return toValidate.matches(regex) && Integer.parseInt(toValidate) > 4;
    }

}
