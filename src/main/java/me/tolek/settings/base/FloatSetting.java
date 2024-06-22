package me.tolek.settings.base;

import net.minecraft.text.Text;

public abstract class FloatSetting extends MflpSetting {

    private float state;

    public FloatSetting(String name, float defaultValue, String tt) {
        super(name, defaultValue, "float", tt);
    }

    public abstract void run();

    public void setState(float state) { this.state = state; }

    public float getState() { return this.state; }
}
