package me.tolek.input;

import java.util.ArrayList;
import java.util.HashMap;

public class Hotkey {

    private HashMap<Integer, Boolean> keys;

    public Hotkey(ArrayList<Integer> keys) {
        HashMap<Integer, Boolean> a = new HashMap<>();
        keys.forEach(k -> a.put(k, false));
        this.keys = a;
    }

    public ArrayList<Integer> getKeys() {
        ArrayList<Integer> a = new ArrayList<>();
        this.keys.keySet().forEach(a::add);
        return a;
    }

    public void setKey(ArrayList<Integer> keys) {
        HashMap<Integer, Boolean> a = new HashMap<>();
        keys.forEach(k -> a.put(k, false));
        this.keys = a;
    }

    public Hotkey copy() {
        ArrayList<Integer> a = new ArrayList<>();
        this.keys.keySet().forEach(a::add);
        Hotkey e = new Hotkey(a);
        return e;
    }

    public void setKeyState(int key, boolean state) {
        keys.keySet().forEach(k -> {
            if (k == key) keys.replace(key, state);
        });
    }

    public boolean isPressed() {
        for (int key : keys.keySet()) {
            if (!keys.get(key)) return false;
        }

        return true;
    }
}
