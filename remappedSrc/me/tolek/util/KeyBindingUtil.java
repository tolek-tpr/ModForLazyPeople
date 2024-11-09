package me.tolek.util;

import me.tolek.modules.macro.MacroList;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyBindingUtil {

    private MacroList macroList = MacroList.getInstance();

    public static KeyBinding setKey(KeyBinding keyBinding, InputUtil.Key key) {


        KeyBinding newKeyBinding = new KeyBinding(keyBinding.getTranslationKey(), key.getCode(), keyBinding.getCategory());


        return newKeyBinding;
    }

    public static KeyBinding copyKeybinding(KeyBinding kb, int key) {
        return new KeyBinding(kb.getTranslationKey(), key, kb.getCategory());
    }

}
