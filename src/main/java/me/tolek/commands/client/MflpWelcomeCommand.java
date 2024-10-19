package me.tolek.commands.client;

import me.tolek.gui.screens.MflpHelloScreen;
import me.tolek.util.ScreenUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class MflpWelcomeCommand implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("mflpwelcome")
                    .executes(context -> {
                        ScreenUtil.openScreenAfterDelay(new MflpHelloScreen());
                        return 1;
                    })
            );
        });
    }

}
