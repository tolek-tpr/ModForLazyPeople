package me.tolek.mixin.client;

import me.tolek.modules.betterFreeCam.CameraEntity;
import me.tolek.util.CameraUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    private void overrideYaw(double yawChange, double pitchChange, CallbackInfo ci) {
        if ((Object) this instanceof ClientPlayerEntity) {
            if (CameraUtils.shouldPreventPlayerMovement()) {
                CameraUtils.updateCameraRotations((float) yawChange, (float) pitchChange);
                ci.cancel();
            }
        }
    }
}
