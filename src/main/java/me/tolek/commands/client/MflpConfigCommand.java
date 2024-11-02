package me.tolek.commands.client;

import me.tolek.gui.screens.MflpMacroConfig;
import me.tolek.util.ScreenUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;

public class MflpConfigCommand implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("mflpconfig")
                    .executes((source) -> {
                        ScreenUtil.openScreen(new MflpMacroConfig(MinecraftClient.getInstance()));
                        return 1;
                    })
            );
        });
    }

}
