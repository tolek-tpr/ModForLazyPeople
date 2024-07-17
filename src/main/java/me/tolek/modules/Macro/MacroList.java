package me.tolek.modules.Macro;

import me.tolek.input.Hotkey;
import me.tolek.util.KeyBindingUtil;
import me.tolek.util.MflpUtil;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.util.ArrayList;
import java.util.List;

public class MacroList {

    private MacroList() {
        Macro a = new Macro(UNDEFINED_KEYBINDING.copy(), List.of("/mflp freezeGame"), "Tick freeze", 1);
        Macro b = new Macro(UNDEFINED_KEYBINDING.copy(), List.of("/tick step"), "Tick step", 1);
        this.addMacro(a);
        this.addMacro(b);
    }

    private final Hotkey UNDEFINED_KEYBINDING = new Hotkey(
            MflpUtil.asArray(InputUtil.UNKNOWN_KEY.getCode())
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

    public void removeMacro(Macro m) {
        m.setKeyBinding(null);
        macros.remove(m);
    }

    public void setList(ArrayList<Macro> ml) {
        macros = ml;
    }

}
