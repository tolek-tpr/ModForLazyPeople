package me.tolek.mixin.client;

import me.tolek.util.CameraUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    private void overrideYaw(double yawChange, double pitchChange, CallbackInfo ci) {
        if ((Object) this instanceof ClientPlayerEntity) {
            if (CameraUtils.shouldPreventPlayerMovement()) {
                CameraUtils.updateCameraRotations((float) yawChange, (float) pitchChange);
            }

            if (CameraUtils.shouldPreventPlayerMovement()) {
                ci.cancel();
                return;
            }
        }
    }
}
