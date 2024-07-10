package me.tolek.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;

public class RenderUtils {

    public static void drawTexture(Identifier texture, MatrixStack matrices, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        drawTexture(texture, matrices, (float)x, (float)y, (float)z, (float) (x + width), (float) (y + height), (float) u, (float) v, textureWidth, textureHeight);
    }

    private static void drawTexture(Identifier texture, MatrixStack matrices, float x1, float y1, float z, float x2, float y2, float u1, float u2, float v1, float v2) {
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix4f, x1, y1, z).texture(u1, v1).next();
        bufferBuilder.vertex(matrix4f, x1, y2, z).texture(u1, v2).next();
        bufferBuilder.vertex(matrix4f, x2, y2, z).texture(u2, v2).next();
        bufferBuilder.vertex(matrix4f, x2, y1, z).texture(u2, v1).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    public static void drawOutlinedBox(Box bb, MatrixStack matrixStack)
    {
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES,
                VertexFormats.POSITION);
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
                .next();
        tessellator.draw();
    }

}
