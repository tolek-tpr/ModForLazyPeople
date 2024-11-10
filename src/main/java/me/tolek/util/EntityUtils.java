package me.tolek.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class EntityUtils {

    @Nullable
    public static Entity getCameraEntity() {
        MinecraftClient mc = MinecraftClient.getInstance();
        Entity entity = mc.getCameraEntity();
        if (entity == null) {
            entity = mc.player;
        }

        return (Entity)entity;
    }

}
