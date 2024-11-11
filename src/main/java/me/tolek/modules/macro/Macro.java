package me.tolek.modules.macro;

import me.tolek.ModForLazyPeople;
import me.tolek.util.KeyBindingUtil;
import net.minecraft.client.MinecraftClient;
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
    private int worldSpecificOptionIndex = 0;
    private String allowedServers = "";
    private int executeOption = 0;

    // Don't serialize
    private int currentCommand = 0;

    public Macro(KeyBinding keyBinding, List<String> commands, String macroName, int repeatAmount) {
        this.keyBinding = keyBinding;
        this.commands = new ArrayList<>(commands);
        this.macroName = macroName;
        this.repeatAmount = repeatAmount;
        this.allowedServers = "";
    }

    public Macro(KeyBinding keyBinding, List<String> commands, String macroName, int repeatAmount, boolean isUneditable, boolean isTurnedOn) {
        this.keyBinding = keyBinding;
        this.commands = new ArrayList<>(commands);
        this.macroName = macroName;
        this.repeatAmount = repeatAmount;
        this.isUneditable = isUneditable;
        this.isTurnedOn = isTurnedOn;
        this.allowedServers = "";
    }

    public void runMacro(ClientPlayerEntity p) {
        MinecraftClient client = MinecraftClient.getInstance();
        boolean connectedToServer = client.getNetworkHandler() != null && client.getNetworkHandler().getServerInfo() != null;

        if (!this.isTurnedOn) return;

        if (this.worldSpecificOptionIndex == 1) { // SP ONLY
            if (connectedToServer) return;
        } else if (this.worldSpecificOptionIndex == 2) { // SERVER SPECIFIC
            if (!connectedToServer || !allowedServers.contains(client.getNetworkHandler().getServerInfo().address)) return;
        } else if (this.worldSpecificOptionIndex == 3) {
            if (!connectedToServer) return;
        }
        for (int i = 0; i < repeatAmount; ++i) {
            if (this.executeOption  == 0) {
                commands.forEach((cmd) -> p.networkHandler.sendChatCommand(cmd.startsWith("/") ?
                        cmd.substring(1) : cmd));
            } else {
                try {
                    if (commands.isEmpty()) continue;
                    String currentCmd = commands.get(this.currentCommand).startsWith("/") ? commands.get(this.currentCommand).substring(1) : commands.get(this.currentCommand);
                    p.networkHandler.sendChatCommand(currentCmd);
                    if (this.currentCommand == commands.size() - 1) {
                        this.currentCommand = 0;
                    } else {
                        this.currentCommand++;
                    }
                } catch (Exception e) {
                    ModForLazyPeople.LOGGER.error("A exception occurred when trying to run the macro!");
                }
            }
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

    public void setCommands(ArrayList<String> commands) {
        this.currentCommand = 0;
        this.commands = commands;
    }
    public void addCommands(List<String> commands) {
        this.currentCommand = 0;
        this.commands.addAll(commands);
    }

    public boolean removeCommands(List<String> commands) {
        this.currentCommand = 0;
        for (String cmd : commands) {
            this.commands.remove(cmd);
        }
        return true;
    }
    public void setKey(int key) { this.key = key; }
    public int getKey() { return this.key; }
    public void setUneditable(boolean uneditable) { this.isUneditable = uneditable; }
    public boolean getUneditable() { return this.isUneditable; }
    public void setTurnedOn(boolean turnedOn) { this.isTurnedOn = turnedOn; }
    public boolean getTurnedOn() { return this.isTurnedOn; }
    public int getWorldSpecificOptionIndex() { return this.worldSpecificOptionIndex; }
    public int getExecuteOption() { return this.executeOption; }

    public void setWorldSpecificOptionIndex(int index) { this.worldSpecificOptionIndex = index; }
    public void setExecuteOption(int option) { this.executeOption = option; }

    public void nextWorldSpecificSetting() {
        int maxWorldSpecificOptionIndex = 3;
        if (this.worldSpecificOptionIndex == maxWorldSpecificOptionIndex) {
            this.worldSpecificOptionIndex = 0;
        } else {
            this.worldSpecificOptionIndex++;
        }
    }

    public void nextExecuteOption() {
        if (this.executeOption == 1) {
            this.executeOption = 0;
        } else {
            this.executeOption++;
        }
    }

    public String getNameForExecuteOption() {
        switch (this.executeOption) {
            case 0 -> { return "mflp.macroSettings.all"; }
            case 1 -> { return "mflp.macroSettings.oneAtATime"; }
        }
        return null;
    }

    public String getNameForSpecificWorld() {
        switch (this.worldSpecificOptionIndex) {
            case 0 -> { return "mflp.macroSettings.anyWorld"; }
            case 1 -> { return "mflp.macroSettings.spOnly"; }
            case 2 -> { return "mflp.macroSettings.serverSpecific"; }
            case 3 -> { return "mflp.macroSettings.mpOnly"; }
        }
        return null;
    }

    public void setAllowedServers(String allowedServers) { this.allowedServers = allowedServers == null ? "" : allowedServers; }
    public String getAllowedServers() { return this.allowedServers == null ? "" : this.allowedServers; }

}
