package me.tolek.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MiscUtils {

    public static boolean isModLoaded(String modId) {
        try {
            // Fabric API's Loader is used to check for mods
            return net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded(modId);
        } catch (Throwable t) {
            return false; // Assume not loaded if an error occurs
        }
    }

}
