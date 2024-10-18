package me.tolek.commands.client;

import me.tolek.network.WebSocketServerHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class MflpNetCommand implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("mflpnet")
                    .then(literal("reconnect").executes(context -> {
                        WebSocketServerHandler.getInstance().reconnect();
                        return 1;
                    }))
            );
        });
    }
}
