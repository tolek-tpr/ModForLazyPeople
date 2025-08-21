package me.tolek.mixin.client;

import me.tolek.modules.settings.MflpSettingsList;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
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

}
