package me.tolek.commands.client;

import me.tolek.gui.screens.MflpHelloScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;

public class MflpWelcomeCommand implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("mflpwelcome")
                    .executes(context -> {
                        MinecraftClient client = context.getSource().getClient();
                        client.send(() -> client.setScreen(new MflpHelloScreen()));
                        return 1;
                    })
            );
        });
    }

}
