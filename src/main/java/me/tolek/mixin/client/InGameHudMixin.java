package me.tolek.mixin.client;

import me.tolek.modules.settings.MflpSettingsList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "getCameraPlayer", at = @At("HEAD"), cancellable = true)
    private void overridePlayerForRendering(CallbackInfoReturnable<PlayerEntity> cir)
    {
        // Fix the hotbar rendering in the Free Camera mode by using the actual player
        if (MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState() && this.client.player != null)
        {
            cir.setReturnValue(this.client.player);
        }
    }

}
