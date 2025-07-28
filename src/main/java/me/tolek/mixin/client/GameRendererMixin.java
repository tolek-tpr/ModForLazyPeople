package me.tolek.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.tolek.modules.settings.FreeCamInputModeSetting;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.CameraUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class, priority = 1002)
public abstract class GameRendererMixin {

    @Shadow @Final
    private MinecraftClient client;

    @ModifyExpressionValue(method = "getFov", at = @At(value = "CONSTANT", args = "floatValue=70.0"))
    private float applyFreeCameraFov(float original)
    {
        if (MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState())
        {
            return ((float) this.client.options.getFov().getValue());
        }

        return original;
    }

    @ModifyVariable(method = "getFov", at = @At(value = "LOAD", ordinal = 0), argsOnly = true)
    private boolean freezeFovOnFreeCamera(boolean value)
    {
        return !MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState() && value;
    }

    @Redirect(method = "updateCrosshairTarget", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/MinecraftClient;getCameraEntity()Lnet/minecraft/entity/Entity;"))
    private Entity overrideCameraEntityForRayTrace(MinecraftClient mc)
    {
        if (MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState() &&
                MflpSettingsList.getInstance().FREE_CAM_INPUT_MODE.stateIndex == FreeCamInputModeSetting.PLAYER  &&
                mc.player != null)
        {
            return mc.player;
        }

        return mc.getCameraEntity();
    }

    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void removeHandRendering(CallbackInfo ci)
    {
        if (MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState())
        {
            ci.cancel();
        }
    }

    @Inject(method = "onCameraEntitySet", at = @At("RETURN"))
    private void ensurePostProcessor(Entity entity, CallbackInfo ci) {
        final boolean bailOutIfNoneSelected = true;
        MflpSettingsList.getInstance().POST_PROCESSOR.setPostProcessor(bailOutIfNoneSelected);
    }

}
