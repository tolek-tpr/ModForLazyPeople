package me.tolek.interfaces;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public interface IClientTickHandler {
    void onClientTick(MinecraftClient mc);
}
