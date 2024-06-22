package me.tolek.settings.base;

import net.minecraft.text.Text;

public abstract class IntegerSetting extends MflpSetting {

    private int state;

    public IntegerSetting(String name, int defaultValue, String tt) {
        super(name, defaultValue, "int", tt);
    }

    public abstract void run();
    public boolean validateInt(String toValidate) {
        String regex = "\\d+";
        return toValidate.matches(regex);
    }

    public void setState(int state) { this.state = state; }
    public int getState() { return this.state; }

}
