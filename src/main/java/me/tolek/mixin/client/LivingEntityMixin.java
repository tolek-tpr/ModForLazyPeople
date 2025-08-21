package me.tolek.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.tolek.modules.settings.AnimationSettings;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {

    @Shadow
    public float handSwingProgress;

    @Unique
    private int animationTicks;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /**
     * Using a wrapMethod because another mod tries to cancel this
     */
    @WrapMethod(
            method = "tickHandSwing"
    )
    private void modifySwingPos(Operation<Void> original) {
        original.call();
        int swingDuration = AnimationSettings.getInstance().swingSpeed.value();
        if (swingDuration == 6) {
            return;
        }
        if (animationTicks > swingDuration) {
            animationTicks = 0;
        }
        if (animationTicks == 0) {
            handSwingProgress = 1F;
        } else {
            handSwingProgress = (animationTicks - 1F) / swingDuration;
            animationTicks++;
        }
    }

    @Inject(
            method = "swingHand(Lnet/minecraft/util/Hand;Z)V",
            at = @At("HEAD")
    )
    public void onSwing(Hand hand, boolean fromServerPlayer, CallbackInfo ci) {
        if (animationTicks == 0) {
            animationTicks = 1;
        }
    }
}
