package me.tolek.events;

import me.tolek.event.EventManager;
import me.tolek.event.RenderListener;
import me.tolek.event.WorldRenderListener;
import me.tolek.modules.betterFreeCam.CameraEntity;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.InstancedValues;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class FabricEventsRegistry implements ClientModInitializer {

    private final InstancedValues iv = InstancedValues.getInstance();
    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();

    @Override
    public void onInitializeClient() {
        /*ClientLoginConnectionEvents.INIT.register((i, j) -> {
            iv.pauseWelcomeBack = true;
            // bad idea but w/e
            ((IScheduler) MinecraftClient.getInstance()).scheduleNonRepeating(200, b -> iv.pauseWelcomeBack = false);
        });
        ClientLoginConnectionEvents.DISCONNECT.register((i, j) -> {
            // when the connection is ended.
            iv.pauseWelcomeBack = false;
            iv.isAfk = false;
        });
        ClientPlayConnectionEvents.JOIN.register((i, j, k) -> {
            iv.pauseWelcomeBack = false;
        });
        ClientPlayConnectionEvents.DISCONNECT.register((i, j) -> {
            iv.pauseWelcomeBack = false;
            iv.isAfk = false;
        });*/
        //HudRenderCallback.EVENT.register((context, tickDelta) -> { EventManager.getInstance().fire(new RenderListener.RenderEvent(context, tickDelta)); });
        ClientSendMessageEvents.ALLOW_CHAT.register((msg) -> !(settingsList.AUTO_WELCOME_BACK.getState() && settingsList.AUTO_IGNORE_WB_MESSAGES.getState() && iv.timeSinceLastWbMillis < settingsList.AUTO_IGNORE_WB_MESSAGES_DURATION.getState() * 1000 && msg.contains("wb")));

        ClientTickEvents.START_CLIENT_TICK.register((mcClient) -> {
            CameraEntity.movementTick();
        });
    }

}
