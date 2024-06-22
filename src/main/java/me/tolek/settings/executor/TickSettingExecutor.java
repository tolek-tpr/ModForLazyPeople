package me.tolek.settings.executor;

import me.tolek.settings.AutoWelcomeBack;
import me.tolek.settings.MflpSettingsList;
import me.tolek.settings.base.MflpSetting;
import me.tolek.util.InstancedValues;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class TickSettingExecutor implements ClientModInitializer {

    private MflpSettingsList settingsList = MflpSettingsList.getInstance();
    private InstancedValues iv = InstancedValues.getInstance();

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register((client -> {
            if (client.player != null && client.player.clientWorld != null)
                iv.timeSinceLastInputInMils += 50;
            clientTick(client);
        }));
        ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
            if (client.getServer() != null && client.getServer().getServerIp() != null) {
                System.out.println(client.getServer().getServerIp());
                if(client.getServer().getServerIp().equals("synergyserver.net")) {
                    settingsList.AUTO_PLOT_HOME.refresh();
                }
            }
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
