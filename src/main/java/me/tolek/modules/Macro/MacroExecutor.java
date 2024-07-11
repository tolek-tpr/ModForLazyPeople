package me.tolek.modules.Macro;

import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.UpdateListener;
import net.minecraft.client.MinecraftClient;

public class MacroExecutor extends EventImpl implements UpdateListener {

    private MacroList macroList;
    private MinecraftClient client;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(UpdateListener.class, this);
        this.macroList = MacroList.getInstance();
        this.client = MinecraftClient.getInstance();
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        for (Macro m : macroList.getMacros()) {
            if (m.getKeyBinding().wasPressed()) {
                m.runMacro(client.player);
            }
        }
    }
}
