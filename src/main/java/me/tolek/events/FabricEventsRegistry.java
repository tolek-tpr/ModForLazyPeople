package me.tolek.events;

import me.tolek.util.InstancedValues;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class FabricEventsRegistry implements ClientModInitializer {

    private InstancedValues iv = InstancedValues.getInstance();

    @Override
    public void onInitializeClient() {
        ClientLoginConnectionEvents.INIT.register((i, j) -> {
            iv.pauseWelcomeBack = true;
            //((TimerInterface) MinecraftClient.getInstance()).scheduleNonRepeating(200, b -> iv.pauseWelcomeBack = false);
        });
        ClientLoginConnectionEvents.DISCONNECT.register((i, j) -> {
            iv.pauseWelcomeBack = false;
            iv.isAfk = false;
        });
        ClientPlayConnectionEvents.JOIN.register((i, j, k) -> {
            iv.pauseWelcomeBack = false;
        });
        ClientPlayConnectionEvents.DISCONNECT.register((i, j) -> {
            iv.pauseWelcomeBack = false;
            iv.isAfk = false;
        });
        ClientLoginConnectionEvents.QUERY_START.register((i, j) -> {
            iv.pauseWelcomeBack = false;
        });
    }

}
