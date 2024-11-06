package me.tolek.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

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

    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    @SafeVarargs
    public static <T> ArrayList<T> asArray(T... objects) {
        ArrayList<T> array = new ArrayList<>();
        Collections.addAll(array, objects);
        return array;
    }

    public static <T> ArrayList<T> arrayFromSet(Set<T> set) {
        return new ArrayList<>(set);
    }

    public static <K, V> HashMap<K, V> asHashMap(ArrayList<K> keys, ArrayList<V> objects) {
        HashMap<K, V> map = new HashMap<>();
        int i = 0;
        for (K key : keys) {
            if (objects.get(i) == null) {
                map.put(key, null);
            } else {
                map.put(key, objects.get(i));
            }
            i++;
        }
        return map;
    }

}
