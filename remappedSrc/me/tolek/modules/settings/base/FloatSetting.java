package me.tolek.modules.settings.base;

public abstract class FloatSetting extends MflpSetting {

    private float state;

    public FloatSetting(String name, float defaultValue, String tt) {
        super(name, defaultValue, "float", tt);
    }

    public abstract void run();

    public void setState(float state) { this.state = state; }

    public float getState() { return this.state; }
}
