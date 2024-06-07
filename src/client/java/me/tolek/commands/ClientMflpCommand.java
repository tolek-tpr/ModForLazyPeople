package me.tolek.commands;

import me.tolek.Macro.Macro;
import me.tolek.gui.MflpHelloScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.network.message.ChatVisibility;

public class ClientMflpCommand implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("cmflp")
                    .then(ClientCommandManager.literal("showchat")
                        .executes((ctx -> {
                            return showBetterChat(ctx.getSource());
                        }))
                    )


            );
        });
    }

    public int showBetterChat(FabricClientCommandSource source) {
        //source.getClient().options.getChatVisibility().setValue(ChatVisibility.FULL);

        //source.getClient().setScreen(new ChatScreen(""));
        return 1;
    }
}
