package me.tolek.modules.Macro;

import me.tolek.util.MflpUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MacroExecutor implements ClientModInitializer {

    private MflpUtil mflpUtil = new MflpUtil();
    private MacroList macroList = MacroList.getInstance();

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (Macro m : macroList.getMacros()) {
                if (m.getKeyBinding().wasPressed()) {
                        m.runMacro(client.player);
                }
            }
        });
    }

}
