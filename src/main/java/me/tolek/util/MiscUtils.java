package me.tolek.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MiscUtils {
    public static Vec3d calculatePlayerMotionWithDeceleration(Vec3d lastMotion,
                                                              double rampAmount,
                                                              double decelerationFactor)
    {
        GameOptions options = MinecraftClient.getInstance().options;
        int forward = 0;
        int vertical = 0;
        int strafe = 0;

        if (options.forwardKey.isPressed()) { forward += 1;  }
        if (options.backKey.isPressed())    { forward -= 1;  }
        if (options.leftKey.isPressed())    { strafe += 1;   }
        if (options.rightKey.isPressed())   { strafe -= 1;   }
        if (options.jumpKey.isPressed())    { vertical += 1; }
        if (options.sneakKey.isPressed())   { vertical -= 1; }

        double speed = (forward != 0 && strafe != 0) ? 1.2 : 1.0;
        double forwardRamped  = getRampedMotion(lastMotion.x, forward , rampAmount, decelerationFactor) / speed;
        double verticalRamped = getRampedMotion(lastMotion.y, vertical, rampAmount, decelerationFactor);
        double strafeRamped   = getRampedMotion(lastMotion.z, strafe  , rampAmount, decelerationFactor) / speed;

        return new Vec3d(forwardRamped, verticalRamped, strafeRamped);
    }

    public static double getRampedMotion(double current, int input, double rampAmount, double decelerationFactor)
    {
        if (input != 0)
        {
            if (input < 0)
            {
                rampAmount *= -1.0;
            }

            // Immediately kill the motion when changing direction to the opposite
            if ((input < 0) != (current < 0.0))
            {
                current = 0.0;
            }

            current = MathHelper.clamp(current + rampAmount, -1.0, 1.0);
        }
        else
        {
            current *= decelerationFactor;
        }

        return current;
    }

    public static boolean isModLoaded(String modId) {
        try {
            // Fabric API's Loader is used to check for mods
            return net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded(modId);
        } catch (Throwable t) {
            return false; // Assume not loaded if an error occurs
        }
    }
}
