package me.tolek.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.tolek.modules.settings.AnimationSettings;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @Inject(
            method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/" +
                    "minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/" +
                    "MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/render/item/HeldItemRenderer.renderItem " +
                            "(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;" +
                            "Lnet/minecraft/item/ModelTransformationMode;" +
                            "ZLnet/minecraft/client/util/math/MatrixStack;" +
                            "Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
            )
    )
    public void onRenderHeldItem(AbstractClientPlayerEntity player, float tickDelta, float pitch,
                                 Hand hand, float swingProgress, ItemStack item, float equipProgress,
                                 MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                                 CallbackInfo ci) {
        if (hand == Hand.MAIN_HAND) {
            float rotX = (float) AnimationSettings.getInstance().rotX.value();
            float rotY = (float) AnimationSettings.getInstance().rotY.value();
            float rotZ = (float) AnimationSettings.getInstance().rotZ.value();
            float posX = (float) AnimationSettings.getInstance().posX.value() / 100f;
            float posY = (float) AnimationSettings.getInstance().posY.value() / 100f;
            float posZ = (float) AnimationSettings.getInstance().posZ.value() / 100f;

            float scale = (float) AnimationSettings.getInstance().scale.value();
            matrices.translate(posX, posY, posZ);

            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rotX));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotY));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotZ));
            matrices.scale(scale, scale, scale);
        }
    }

    @ModifyExpressionValue(
            method = "updateHeldItems",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgress(F)F"
            )
    )
    public float attackCooldown(float original) {
        return 1f;
    }

    @Inject(
            method = "applyEquipOffset",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onApplyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress, CallbackInfo ci) {
        if (AnimationSettings.getInstance().cancelReequipAnimation.value()) {
            int i = arm == Arm.RIGHT ? 1 : -1;
            matrices.translate((float)i * 0.56f, -0.52f, -0.72f);
            ci.cancel();
        }
    }

    @Inject(
            method = "applyEatOrDrinkTransformation",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;pow(DD)D",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void onDrink(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack,
                        PlayerEntity player, CallbackInfo ci) {
        ci.cancel();
    }

}
