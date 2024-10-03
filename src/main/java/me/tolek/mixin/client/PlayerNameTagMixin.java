package me.tolek.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(EntityRenderer.class)
public class PlayerNameTagMixin {

    @Inject(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target="Lnet/minecraft/client/render/entity/EntityRenderer;getTextRenderer()Lnet/minecraft/client/font/TextRenderer;"))
    private void drawLogo(@Coerce Object entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        // drawe the logo in the top right corner
    }

}
