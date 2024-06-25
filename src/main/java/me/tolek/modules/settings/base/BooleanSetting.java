package me.tolek.modules.settings.base;

public abstract class BooleanSetting extends MflpSetting {

    private boolean state;

    public BooleanSetting(String name, boolean defaultValue, String tt) {
        super(name, defaultValue, "boolean", tt);
    }

    public abstract void run();

    public void setState(boolean state) { this.state = state; }

    public boolean getState() { return this.state; }


}
