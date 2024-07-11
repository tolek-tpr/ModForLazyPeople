package me.tolek.commands.client;

import me.tolek.gui.screens.MflpHelloScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

public class MflpWelcomeCommand implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        /*ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("mflpwelcome")
                    .executes(context -> {
                        openWelcomeScreen(context.getSource());
                        return 1;
                    })
            );
        });*/
    }

    public int openWelcomeScreen(FabricClientCommandSource source) {
        MinecraftClient.getInstance().setScreen(new MflpHelloScreen());
        System.out.println("opening");
        return 1;
    }

}
