package me.tolek.modules.settings.base;

public abstract class IntegerSetting extends MflpSetting {

    private int state;

    public IntegerSetting(String name, int defaultValue, String tt) {
        super(name, defaultValue, "int", tt);
    }

    public abstract void run();
    public boolean validateInt(String toValidate) {
        String regex = "[0-9]+";
        return toValidate.matches(regex);
    }

    public void setState(int state) { this.state = state; }
    public int getState() { return this.state; }

}
