package me.tolek.settings.executor;

import me.tolek.settings.AutoWelcomeBack;
import me.tolek.settings.MflpSettingsList;
import me.tolek.settings.base.MflpSetting;
import me.tolek.util.InstancedValues;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class TickSettingExecutor implements ClientModInitializer {

    private MflpSettingsList settingsList = MflpSettingsList.getInstance();
    private InstancedValues iv = InstancedValues.getInstance();

    @Override
    public void onInitializeClient() {
        /*ClientReceiveMessageEvents.CHAT.register((message, signedMessage, gameProfile, parameters, instant) -> {
            String playerName = MinecraftClient.getInstance().getSession().getUsername();
            System.out.println(message + "!!!!!!!!!!!!!");
            for (MflpSetting setting : settingsList.getSettings()) {
                if (setting.getName().equals("Auto welcome back")) {
                    if (message.getString().contains("has joined.")) {
                        if (!message.getString().contains(playerName)) {
                            //AutoWelcomeBack autoWbSetting = new AutoWelcomeBack();
                            setting.refresh();
                        }
                    }
                }
            }

        });*/
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
