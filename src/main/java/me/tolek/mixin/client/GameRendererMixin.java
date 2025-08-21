package me.tolek.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import me.tolek.event.EventManager;
import me.tolek.event.RenderListener;
import me.tolek.modules.settings.MflpSettingsList;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class, priority = 1002)
public abstract class GameRendererMixin {

    @Inject(method = "onCameraEntitySet", at = @At("RETURN"))
    private void ensurePostProcessor(Entity entity, CallbackInfo ci) {
        final boolean bailOutIfNoneSelected = true;
        MflpSettingsList.getInstance().POST_PROCESSOR.setPostProcessor(bailOutIfNoneSelected);
    }

    @Inject(
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
                    opcode = Opcodes.GETFIELD,
                    ordinal = 0),
            method = "renderWorld(Lnet/minecraft/client/render/RenderTickCounter;)V")
    private void onRenderWorldHandRendering(RenderTickCounter tickCounter,
                                            CallbackInfo ci, @Local(ordinal = 2) Matrix4f matrix4f3,
                                            @Local(ordinal = 1) float tickDelta) {
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.multiplyPositionMatrix(matrix4f3);
        RenderListener.RenderEvent event = new RenderListener.RenderEvent(matrixStack, tickDelta);
        EventManager.getInstance().fire(event);
    }

}
