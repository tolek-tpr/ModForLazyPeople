package me.tolek.input;

import java.util.ArrayList;

public class Hotkey {

    private ArrayList<Integer> keys;
    private boolean pressed = false;

    public Hotkey(ArrayList<Integer> key) {
        this.keys = key;
    }

    public ArrayList<Integer> getKey() {
        return keys;
    }

    public void setKey(ArrayList<Integer> key) {
        this.keys = key;
    }

    public Hotkey copy() {
        Hotkey e = new Hotkey(this.keys);
        return e;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
