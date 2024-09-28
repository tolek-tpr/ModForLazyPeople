package me.tolek.modules.settings.base;

import java.util.ArrayList;

public abstract class ListSetting extends MflpSetting {

    public int stateIndex;
    private ArrayList<Object> selection = new ArrayList<>();

    public ListSetting(String name, int defaultIndex, String tt, ArrayList<Object> selection) {
        super(name, defaultIndex, "list", tt);
        this.selection = selection;
    }

    public abstract void run();

    public void setList(ArrayList<Object> selection) { this.selection = selection; }
    public void addOption(Object option) { this.selection.add(option); }
    public ArrayList<Object> getList() { return this.selection; }
    public void setState(int stateIndex) { this.stateIndex = stateIndex; }
    public Object getState() { return this.selection.get(stateIndex); }

}
