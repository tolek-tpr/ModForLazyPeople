package me.tolek.events;

import me.tolek.interfaces.IScheduler;
import me.tolek.util.InstancedValues;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;

public class FabricEventsRegistry implements ClientModInitializer {

    private InstancedValues iv = InstancedValues.getInstance();

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
    }

}
