package me.tolek.util;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MflpUtil {

    public MflpUtil() { }

    public boolean showedHelloScreen = false;
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
        System.out.println(command.startsWith("/") ?
                command.substring(1) : command);
    }

    public void sendMessage(ClientPlayerEntity source, Text message) {
        if (source == null) return;
        source.sendMessage(message, false);
    }

}
