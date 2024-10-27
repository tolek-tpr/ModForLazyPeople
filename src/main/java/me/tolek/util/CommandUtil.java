package me.tolek.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class CommandUtil {

    public static void sendCommand(String command) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null)
            player.networkHandler.sendChatCommand(command.startsWith("/") ? command.substring(1) : command);
    }

}
