package me.tolek.modules.settings.base;

import me.tolek.input.Hotkey;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ListSetting extends HotkeyableSetting {

    public int stateIndex;
    private transient ArrayList<Object> selection = new ArrayList<>();
    //private Hotkey hotkey;

    public ListSetting(String name, int defaultIndex, String tt, ArrayList<Object> selection) {
        super(name, defaultIndex, "list", tt);
        this.selection = selection;
        this.hotkey = new Hotkey(new HashMap<>());
    }

    public abstract void run();

    public void setList(ArrayList<Object> selection) { this.selection = selection; }
    public void addOption(Object option) { this.selection.add(option); }
    public ArrayList<Object> getList() { return this.selection; }
    public void setState(int stateIndex) { this.stateIndex = stateIndex; }
    public Object getState() { return this.selection.get(stateIndex); }

    @Override
    public void notifyPressed() {
        final Object selected = this.getState();

        MutableText text = Text.literal("Set ").append(Text.translatable(this.getName())).append(" ").append(Text.translatable(selected.toString())
                .formatted(Formatting.GREEN));
        MinecraftClient.getInstance().inGameHud.setOverlayMessage(text, false);
    }

    @Override
    public void cycle() {
        this.run();
    }

}
