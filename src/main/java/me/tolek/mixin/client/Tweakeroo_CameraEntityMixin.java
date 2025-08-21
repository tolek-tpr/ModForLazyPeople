package me.tolek.mixin.client;

import fi.dy.masa.tweakeroo.util.CameraEntity;
import me.tolek.modules.settings.FreeCamWalkingMode;
import me.tolek.modules.settings.MflpSettingsList;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CameraEntity.class)
public abstract class Tweakeroo_CameraEntityMixin extends AbstractEntityMixin {

    @Inject(method = "handleMotion", at = @At(value = "INVOKE", target = "Lfi/dy/masa/tweakeroo/util/CameraEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"), cancellable = true)
    private void addCollidingMoveIfNecessary(double forward, double up, double strafe, CallbackInfo ci) {
        if (MflpSettingsList.getInstance().FREE_CAM_WALKING_MODE.stateIndex == FreeCamWalkingMode.WALK) {
            collidingMove(this.getVelocity());
            ci.cancel();
        }
    }

    @Unique
    private void collidingMove(Vec3d movement) {
        CameraEntity entity = (CameraEntity)(Object)this;

        Box box = getBoundingBox();
        List<VoxelShape> list = getWorld().getEntityCollisions(entity, box.stretch(movement));
        Vec3d adjustedMovement = movement.lengthSquared() == 0.0 ? movement : Entity.adjustMovementForCollisions(entity, movement, box, this.getWorld(), list);

        final double x = movement.x + getX();
        double y = movement.y + getY();
        final double z = movement.z + getZ();

        if (movement.y < adjustedMovement.y)
            y = adjustedMovement.y + getY();

        this.setPosition(x, y, z);
    }

}
