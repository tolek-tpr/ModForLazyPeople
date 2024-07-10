package me.tolek.modules.settings.executor;

import me.tolek.event.EventManager;
import me.tolek.interfaces.TimerInterface;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.InstancedValues;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class TickSettingExecutor implements ClientModInitializer {

    private InstancedValues iv = InstancedValues.getInstance();

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register((client -> {
            if (client.player != null && client.player.clientWorld != null)
                iv.timeSinceLastInputInMils += 50;
            clientTick(client);
        }));
    }

    public void clientTick(MinecraftClient client) {
        //if (client.currentScreen == null) {
            for (int key = GLFW.GLFW_KEY_SPACE; key < GLFW.GLFW_KEY_LAST; key++) {
                if (GLFW.glfwGetKey(client.getWindow().getHandle(), key) == GLFW.GLFW_PRESS) {
                    iv.timeSinceLastInputInMils = 0;
                }
            }
        //}
    }

}