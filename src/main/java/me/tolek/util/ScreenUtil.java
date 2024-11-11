package me.tolek.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class ScreenUtil {

    public static void openScreen(Screen screen) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.send(() -> client.setScreen(screen));
    }

    public static Screen getScreen() {
        return MinecraftClient.getInstance().currentScreen;
    }

}
