package me.tolek.mixin.client;

import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.CameraUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class, priority = 1002)
public class GameRendererMixin {

    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/MinecraftClient;getCameraEntity()Lnet/minecraft/entity/Entity;"))
    private Entity overrideCameraEntityForRayTrace(MinecraftClient mc)
    {
        // Return the real player for the hit target ray tracing if the
        // player inputs option is enabled in Free Camera mode.
        // Normally in Free Camera mode the MFLP CameraEntity is set as the
        // render view/camera entity, which would then also ray trace from the camera point of view.
        if (MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState() &&
                !CameraUtils.shouldPreventPlayerInputs() &&
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

}
