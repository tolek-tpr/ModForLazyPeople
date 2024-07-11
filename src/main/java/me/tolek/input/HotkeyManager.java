package me.tolek.input;

import java.util.ArrayList;

public class HotkeyManager {

    private ArrayList<Hotkey> hotkeys = new ArrayList<>();
    private static HotkeyManager instance;

    private HotkeyManager() {}

    public static HotkeyManager getInstance() {
        if (instance == null) instance = new HotkeyManager();
        return instance;
    }

    public Hotkey createHotkey(ArrayList<Integer> keys) {
        Hotkey hotkey = new Hotkey(keys);
        hotkeys.add(hotkey);
        return hotkey;
    }

    public ArrayList<Hotkey> getHotkeys() {
        return hotkeys;
    }
}
