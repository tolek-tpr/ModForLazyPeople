package me.tolek.modules.Macro;

import me.tolek.util.KeyBindingUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;

import java.util.ArrayList;
import java.util.List;

public class Macro {

    private KeyBinding keyBinding;
    private ArrayList<String> commands;
    private String macroName;
    private int key;
    private int repeatAmount;
    private boolean isUneditable;
    private boolean isTurnedOn = true;

    public Macro(KeyBinding keyBinding, List<String> commands, String macroName, int repeatAmount) {
        this.keyBinding = keyBinding;
        ArrayList<String> cmds = new ArrayList<>();
        commands.forEach(cmd -> {cmds.add(cmd);});
        this.commands = cmds;
        this.macroName = macroName;
        this.repeatAmount = repeatAmount;
    }

    public Macro(KeyBinding keyBinding, List<String> commands, String macroName, int repeatAmount, boolean isUneditable, boolean isTurnedOn) {
        this.keyBinding = keyBinding;
        ArrayList<String> cmds = new ArrayList<>();
        commands.forEach(cmd -> {cmds.add(cmd);});
        this.commands = cmds;
        this.macroName = macroName;
        this.repeatAmount = repeatAmount;
        this.isUneditable = isUneditable;
        this.isTurnedOn = isTurnedOn;
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

    public Macro copy() {
        Macro m = new Macro(KeyBindingUtil.copyKeybinding(this.keyBinding, this.key), this.commands, this.macroName, this.repeatAmount);
        m.setKey(this.key);
        return m;
    }

    public int getRepeatAmount() { return this.repeatAmount; }
    public void setRepeatAmount(int amount) { this.repeatAmount = amount; }

    public KeyBinding getKeyBinding() {
        return this.keyBinding;
    }

    public void setKeyBinding(KeyBinding keyBinding) {
        this.keyBinding = keyBinding;
    }

    public String getName() { return macroName; }

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
    public void setKey(int key) { this.key = key; }
    public int getKey() { return this.key; }
    public void setUneditable(boolean uneditable) { this.isUneditable = uneditable; }
    public boolean getUneditable() { return this.isUneditable; }
    public void setTurnedOn(boolean turnedOn) { this.isTurnedOn = turnedOn; }
    public boolean getTurnedOn() { return this.isTurnedOn; }
}
