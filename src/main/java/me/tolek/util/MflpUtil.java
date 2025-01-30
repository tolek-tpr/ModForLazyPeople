package me.tolek.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

    public static void print(Object... objects) {
        for (Object object : objects) {
            System.out.println(object);
        }
    }

    public static double hex2DecBetween1And0(String hexInput) {
        int dec = Integer.parseInt(hexInput, 16);
        int maxVal = (int) Math.pow(16, hexInput.length()) - 1;
        return dec / (double) maxVal;
    }

    // Useful if i want to add a sky thing that was suggested to me (shader thing)
    public static InputStream getResourceAsInputStream(String filePath) {
        ClassLoader classLoader = MflpUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);

        return inputStream;
    }

    public static Logger getConfigLogger() { return LoggerFactory.getLogger("MFLP-Config"); }

    /**
     *
     * @param in InputStream of the file you want to read
     * @return String, the entire file in one string.
     * @throws IOException
     */
    public static String readFile(InputStream in) throws IOException {
        final StringBuffer sBuffer = new StringBuffer();
        final BufferedReader br = new BufferedReader(new InputStreamReader(in));
        final char[] buffer = new char[1024];

        int cnt;
        while ((cnt = br.read(buffer, 0, buffer.length)) > -1) {
            sBuffer.append(buffer, 0, cnt);
        }
        br.close();
        in.close();
        return sBuffer.toString();
    }

    public static InputStream asInputStream(File f) throws FileNotFoundException {
        return new FileInputStream(f);
    }

}
