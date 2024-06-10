package me.tolek.settings.base;

public abstract class IntegerSetting extends MflpSetting {

    private int state;

    public IntegerSetting(String name, int defaultValue) {
        super(name, defaultValue, "int");
    }

    public abstract void run();

    public void setState(int state) { this.state = state; }
    public int getState() { return this.state; }

}
