package me.tolek.modules.settings.base;

import me.tolek.input.Hotkey;

import java.util.HashMap;

public abstract class HotkeyableSetting extends MflpSetting {

    protected boolean renderHotkey = false;
    Hotkey hotkey = new Hotkey(new HashMap<>());

    public HotkeyableSetting(String name, Object defaultValue, String type, String tooltip) {
        super(name, defaultValue, type, tooltip);
    }

    public abstract void notifyPressed();
    public Hotkey getHotkey() { return hotkey; }
    public void setHotkey(Hotkey hotkey) { this.hotkey = hotkey; }
    public abstract void cycle();
    public boolean renderHotkey() { return renderHotkey; }

}
