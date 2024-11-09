package me.tolek.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.network.WebSocketServerHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(EntityRenderer.class)
public class PlayerNameTagMixin {

    @Unique
    //private final MflpPlayersWorker worker = MflpPlayersWorker.getInstance();
    private final WebSocketServerHandler serverHandler = WebSocketServerHandler.getInstance();
    @Unique
    private final Identifier logo = new Identifier("modforlazypeople", "textures/gui/sprites/mflp/user_logo.png");
    @Unique
    private final MinecraftClient client = MinecraftClient.getInstance();
    @Unique
    private final TextRenderer tx = client.textRenderer;
    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();

    @Inject(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target="Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"))
    private void drawLogo(@Coerce Object entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (this.serverHandler == null || !settingsList.NAMETAG_ICON_TOGGLE.getState()) return;
        ArrayList<String> returnMessage = serverHandler.mflpUsers;

        if (!(entity instanceof PlayerEntity e)) return;

        if (e.getGameProfile().getId() == null || e.getName() == null) return;

        if (returnMessage != null && client.world != null) {
            PlayerEntity playerByUUID = client.world.getPlayerByUuid(e.getGameProfile().getId());
            if (playerByUUID != null && returnMessage.contains(playerByUUID.getName().getString())) {
                int width = tx.getWidth(text);
                int x1 = width / 2 + 2;
                int x2 = width / 2 + tx.fontHeight + 2;
                int y1 = -1;
                int y2 = (-1 + width / 2 + tx.fontHeight + 2) - (-1 + width / 2 + 2);
                int z = 1;
                int u1 = 0;
                int u2 = 1;
                int v1 = 0;
                int v2 = 1;

                RenderSystem.setShaderTexture(0, logo);
                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                RenderSystem.enableBlend();
                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
                bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                bufferBuilder.vertex(matrix4f, x1, y1, z).texture(u1, v1).next();
                bufferBuilder.vertex(matrix4f, x1, y2, z).texture(u1, v2).next();
                bufferBuilder.vertex(matrix4f, x2, y2, z).texture(u2, v2).next();
                bufferBuilder.vertex(matrix4f, x2, y1, z).texture(u2, v1).next();
                BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
                RenderSystem.disableBlend();

            }
        }
    }

}
