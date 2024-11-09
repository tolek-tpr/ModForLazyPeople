package me.tolek.modules.macro;

import me.tolek.util.KeyBindingUtil;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.util.ArrayList;
import java.util.List;

public class MacroList {

    private MacroList() {
        Macro a = new Macro(UNDEFINED_KEYBINDING, List.of("/mflp freezeGame"), "Tick freeze", 1);
        Macro b = new Macro(UNDEFINED_KEYBINDING1, List.of("/tick step"), "Tick step", 1);
        a.setKey(InputUtil.UNKNOWN_KEY.getCode());
        b.setKey(InputUtil.UNKNOWN_KEY.getCode());
        this.addMacro(a);
        this.addMacro(b);
    }

    private final KeyBinding UNDEFINED_KEYBINDING = new KeyBinding(
            "mflp.keybinding.defaultKey",
            InputUtil.Type.KEYSYM,
            InputUtil.UNKNOWN_KEY.getCode(),
            "mflp.keybindCategory.macro"
    );
    private final KeyBinding UNDEFINED_KEYBINDING1 = new KeyBinding(
            "mflp.keybinding.defaultKey",
            InputUtil.Type.KEYSYM,
            InputUtil.UNKNOWN_KEY.getCode(),
            "mflp.keybindCategory.macro"
    );

    private static MacroList instance;
    private ArrayList<Macro> macros = new ArrayList<>();

    public static MacroList getInstance() {
        if (MacroList.instance == null) MacroList.instance = new MacroList();
        return MacroList.instance;
    }

    public ArrayList<Macro> getMacros() {
        return macros;
    }

    public void addMacro(Macro macro) {
        macros.add(macro);
    }

    public KeyBinding setKeyBinding(KeyBinding keyBinding, InputUtil.Key key) {
        int i = 0;
        for (Macro m : macros) {
            KeyBinding macroKeyBinding = m.getKeyBinding();

            if (keyBinding == macroKeyBinding) {
                KeyBinding newKeyBinding = KeyBindingUtil.setKey(keyBinding, key);

                m.setKeyBinding(newKeyBinding);
                m.setKey(key.getCode());
                return newKeyBinding;
            }
        }
        return null;
    }

    public void removeMacro(Macro m) {
        m.setKeyBinding(null);
        macros.remove(m);
    }

    public Macro getMacroFromKeyBinding(KeyBinding keyBinding) {
        for (Macro m : macros) {
            if (m.getKeyBinding() == keyBinding) return m;
        }
        return null;
    }

    public void setList(ArrayList<Macro> ml) {
        macros = ml;
    }

}
