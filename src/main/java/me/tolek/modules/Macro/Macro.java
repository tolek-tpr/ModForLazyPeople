package me.tolek.modules.Macro;

import me.tolek.input.Hotkey;
import me.tolek.util.KeyBindingUtil;
import me.tolek.util.MflpUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Macro {

    private Hotkey keyBinding = new Hotkey(MflpUtil.asArray(-1));
    private ArrayList<String> commands;
    private String macroName = "";
    private int repeatAmount;
    private boolean isUneditable;
    private boolean isTurnedOn = true;

    public Macro(Hotkey keyBinding, List<String> commands, String macroName, int repeatAmount) {
        this.keyBinding = keyBinding;
        ArrayList<String> cmds = new ArrayList<>();
        commands.forEach(cmd -> {cmds.add(cmd);});
        this.commands = cmds;
        this.macroName = macroName;
        this.repeatAmount = repeatAmount;

        if (this.keyBinding == null) {
            this.keyBinding = new Hotkey(MflpUtil.asArray(-1));
        }
    }

    public Macro(Hotkey keyBinding, List<String> commands, String macroName, int repeatAmount, boolean isUneditable, boolean isTurnedOn) {
        this.keyBinding = keyBinding;
        ArrayList<String> cmds = new ArrayList<>();
        commands.forEach(cmd -> {cmds.add(cmd);});
        this.commands = cmds;
        this.macroName = macroName;
        this.repeatAmount = repeatAmount;
        this.isUneditable = isUneditable;
        this.isTurnedOn = isTurnedOn;

        if (this.keyBinding == null) {
            this.keyBinding = new Hotkey(MflpUtil.asArray(-1));
        }
    }

    public void runMacro(ClientPlayerEntity p) {
        if (!this.isTurnedOn) return;
        for (int i = 0; i < repeatAmount; ++i) {
            commands.forEach((cmd) -> {
                p.networkHandler.sendChatCommand(cmd.startsWith("/") ?
                        cmd.substring(1) : cmd);
            });
        }
    }

    public Text getFormattedKeys() {
        MutableText formattedKeys = null;
        if (this.getKeys() == null) return Text.literal("");
        for (Integer keyCode : getKeys()) {
            InputUtil.Key key = InputUtil.fromKeyCode(keyCode, 0);
            if (formattedKeys == null) {
                formattedKeys = Text.translatable(key.getTranslationKey());
            } else {
                formattedKeys.append(Text.literal(" + ").
                        append(Text.translatable(key.getTranslationKey())));
            }
        }
        if (formattedKeys == null) {
            formattedKeys = Text.translatable(InputUtil.UNKNOWN_KEY.getTranslationKey());
        }
        return formattedKeys;
    }

    public Macro copy() {
        Macro m = new Macro(this.keyBinding.copy(), this.commands, this.macroName, this.repeatAmount);
        return m;
    }

    public int getRepeatAmount() { return this.repeatAmount; }
    public void setRepeatAmount(int amount) { this.repeatAmount = amount; }

    public Hotkey getKeyBinding() {
        return this.keyBinding;
    }

    public void setKeyBinding(Hotkey keyBinding) {
        this.keyBinding = keyBinding;
    }

    public String getName() {
        return this.macroName == null ? "" : this.macroName;
    }

    public void setName(String macroName) { this.macroName = macroName; }

    public ArrayList<String> getCommands() { return commands; }

    public void setCommands(ArrayList<String> commands) { this.commands = commands; }
    public void addCommands(List<String> commands) {
        for (String cmd : commands) {
            this.commands.add(cmd);
        }
    }

    public boolean removeCommands(List<String> commandss) {
        for (String ccmd : commandss) {
            this.commands.remove(ccmd);
        }
        return true;
    }
    public void setKeys(ArrayList<Integer> keys) { this.keyBinding.setKey(keys); }
    public ArrayList<Integer> getKeys() {
        return this.keyBinding == null ? new ArrayList<>() : this.keyBinding.getKeys();
    }
    public void setUneditable(boolean uneditable) { this.isUneditable = uneditable; }
    public boolean getUneditable() { return this.isUneditable; }
    public void setTurnedOn(boolean turnedOn) { this.isTurnedOn = turnedOn; }
    public boolean getTurnedOn() { return this.isTurnedOn; }
}
