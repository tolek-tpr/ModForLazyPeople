package me.tolek.util;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class MflpUtil {

    public MflpUtil() { }

    public boolean showedHelloScreen = false;

    public final Text g6Message = Text.literal("Thanks for all the help with redstone G6! <3");
    public final Text avoMessage = Text.literal("Great vids, keep it up avo!");
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
