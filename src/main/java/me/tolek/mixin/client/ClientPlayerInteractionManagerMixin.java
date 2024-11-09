package me.tolek.mixin.client;

import me.tolek.util.CameraUtils;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "interactItem", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;syncSelectedSlot()V"),
            cancellable = true)
    private void onProcessRightClickFirst(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir)
    {
        if (CameraUtils.shouldPreventPlayerInputs())
        {
            cir.setReturnValue(ActionResult.PASS);
            cir.cancel();
        }
    }

    @Inject(method = "interactEntity(" +
            "Lnet/minecraft/entity/player/PlayerEntity;" +
            "Lnet/minecraft/entity/Entity;" +
            "Lnet/minecraft/util/Hand;" +
            ")Lnet/minecraft/util/ActionResult;",
            at = @At("HEAD"),
            cancellable = true)
    private void onRightClickMouseOnEntityPre1(PlayerEntity player, Entity target, Hand hand, CallbackInfoReturnable<ActionResult> cir)
    {
        if (CameraUtils.shouldPreventPlayerInputs())
        {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    @Inject(method = "interactEntityAtLocation(" +
            "Lnet/minecraft/entity/player/PlayerEntity;" +
            "Lnet/minecraft/entity/Entity;" +
            "Lnet/minecraft/util/hit/EntityHitResult;" +
            "Lnet/minecraft/util/Hand;" +
            ")Lnet/minecraft/util/ActionResult;",
            at = @At("HEAD"),
            cancellable = true)
    private void onRightClickMouseOnEntityPre2(PlayerEntity player, Entity target, EntityHitResult trace, Hand hand, CallbackInfoReturnable<ActionResult> cir)
    {
        if (CameraUtils.shouldPreventPlayerInputs())
        {
            cir.setReturnValue(ActionResult.PASS);
        }
    }

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void preventEntityAttacksInFreeCameraMode(PlayerEntity player, Entity target, CallbackInfo ci)
    {
        if (CameraUtils.shouldPreventPlayerInputs())
        {
            ci.cancel();
        }
    }

    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void handleBreakingRestriction1(BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir)
    {
        if (CameraUtils.shouldPreventPlayerInputs())
        {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "updateBlockBreakingProgress", at = @At("HEAD"), cancellable = true) // MCP: onPlayerDamageBlock
    private void handleBreakingRestriction2(BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir)
    {
        if (CameraUtils.shouldPreventPlayerInputs())
        {
            cir.setReturnValue(true);
        }
    }
}
