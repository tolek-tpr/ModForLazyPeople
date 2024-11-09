package me.tolek.modules.macro;

import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.UpdateListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
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
