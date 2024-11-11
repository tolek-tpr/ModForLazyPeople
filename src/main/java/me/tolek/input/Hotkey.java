package me.tolek.input;

import me.tolek.util.MflpUtil;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class Hotkey {

    private HashMap<Integer, Integer> keys; // Key, scanCode
    private transient boolean pressed = false;

    public Hotkey(HashMap<Integer, Integer> key) {
        this.keys = key;
    }

    public ArrayList<Integer> getKeys() {
        return MflpUtil.arrayFromSet(keys.keySet());
    }

    public void setKeys(HashMap<Integer, Integer> key) {
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

    public Text getFormattedKeys() {
        if (this.getKeys().isEmpty()) {
            return Text.literal("NONE");
        } else {
            MutableText text = Text.literal("");
            keys.keySet().forEach(key -> {
                if (text.getString().isEmpty()) {
                    text.append(InputUtil.fromKeyCode(key, keys.get(key)).getLocalizedText());
                } else {
                    text.append(Text.literal(" + ").append(InputUtil.fromKeyCode(key, keys.get(key)).getLocalizedText()));
                }
            });
            return text;
        }
    }
}
