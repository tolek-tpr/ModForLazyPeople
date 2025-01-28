package me.tolek.events;

import me.tolek.interfaces.IClientTickHandler;
import me.tolek.modules.betterFreeCam.CameraEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class ClientTickHandler implements IClientTickHandler {
    @Override
    public void onClientTick(MinecraftClient mc) {
        if (mc.world != null && mc.player != null)
        {
            CameraEntity.movementTick();
        }
    }
}
