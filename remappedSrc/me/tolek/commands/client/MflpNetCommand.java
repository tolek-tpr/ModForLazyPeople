package me.tolek.commands.client;

import me.tolek.network.WebSocketServerHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class MflpNetCommand implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("mflpnet")
                    .then(literal("reconnect").executes(context -> {
                        WebSocketServerHandler.getInstance().reconnect();
                        if (WebSocketServerHandler.getInstance().endpoint == null) {
                            context.getSource().sendFeedback(Text.translatable("mflp.net.reconnect.failure").formatted(Formatting.RED));
                        } else {
                            context.getSource().sendFeedback(Text.translatable("mflp.net.reconnect.success").formatted(Formatting.GREEN));
                        }
                        return 1;
                    }))
                    .then(literal("status").executes(context -> {
                        if (WebSocketServerHandler.getInstance().endpoint == null) {
                            context.getSource().sendFeedback(Text.translatable("mflp.net.status.notConnected").formatted(Formatting.RED));
                        } else {
                            context.getSource().sendFeedback(Text.translatable("mflp.net.status.connected").formatted(Formatting.GREEN));
                        }
                        return 1;
                    }))
            );
        });
    }
}
