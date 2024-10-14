package me.tolek.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MflpUtil {

    public MflpUtil() { }

    public static Identifier pencilIcon = new Identifier("modforlazypeople", "pencil");
    public boolean didSave = false;

    public void sendMessage(ClientPlayerEntity source, String message) {
        if (source == null) return;
        source.sendMessage(Text.literal(message));
    }

    public void sendCommand(ClientPlayerEntity source, String command) {
        if (source == null) return;
        source.networkHandler.sendChatCommand(command.startsWith("/") ?
                command.substring(1) : command);
    }

    public void sendMessage(ClientPlayerEntity source, Text message) {
        if (source == null) return;
        source.sendMessage(message, false);
    }

    public static boolean isFakeMessage(Text message) {
        return message.getString().contains("From") || message.getString().contains("*") || message.getString().contains(":");
    }

    /**
     *
     * @param message - The message to process
     * @return boolean - Returns true if the player that sent the message is the client, otherwise returns false
     */
    public static boolean isPlayerMessageAuthor(Text message) {
        String m = message.getString();
        String username = MinecraftClient.getInstance().getSession().getUsername();
        String usernameRegex = "[a-zA-Z0-9_]{3,16}";

        return m.replaceFirst(usernameRegex, username).equals(message.getString());
    }

}
