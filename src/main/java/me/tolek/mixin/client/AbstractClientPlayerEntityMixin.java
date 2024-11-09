package me.tolek.mixin.client;

import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.CameraUtils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {
    @Inject(method = "isSpectator", at = @At("HEAD"), cancellable = true)
    private void overrideIsSpectator(CallbackInfoReturnable<Boolean> cir)
    {
        if (MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState() && CameraUtils.getFreeCameraSpectator())
        {
            cir.setReturnValue(true);
        }
    }
}
