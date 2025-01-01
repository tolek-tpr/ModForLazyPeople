package me.tolek.util;

import me.tolek.mixin.client.RedstoneComponentUpdateRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;

/**
 *  <p><strong>THIS CLASS IS VERY BRAIN DEAD</strong></p> TO USE THE {@code drawBoxInWorld}
 *  methods if you can't get a {@code MatrixStack} and have a {@code Matrix4fStack} instead, you can
 *  probably use {@code new MatrixStack()} as demonstrated in {@link RedstoneComponentUpdateRenderer}
 */

public class RenderUtil {

    public static void drawBoxInWorld(MatrixStack matrices, Camera camera, BlockPos pos1, float r, float g, float b) {
        MinecraftClient client = MinecraftClient.getInstance();

        BlockPos origin = pos1;

        BlockPos size = pos1;
        size = size.subtract(origin);

        origin = origin.add(size.getX() < 0 ? 1 : 0, size.getY() < 0 ? 1 : 0, size.getZ() < 0 ? 1 : 0);
        size = size.add(size.getX() >= 0 ? 1 : -1, size.getY() >= 0 ? 1 : -1, size.getZ() >= 0 ? 1 : -1);

        matrices.push();

        VertexConsumer consumer = client.getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderLayer.getLines());
        matrices.translate(origin.getX() - camera.getPos().x, origin.getY() - camera.getPos().y, origin.getZ() - camera.getPos().z);

        RenderUtil.drawBox(matrices, consumer, 0, 0, 0, size.getX(), size.getY(), size.getZ(), r, g, b, 1);

        matrices.pop();
    }
    
    public static void drawBoxInWorld(MatrixStack matrices, Camera camera, BlockPos pos1, float r, float g, float b, float a) {
        MinecraftClient client = MinecraftClient.getInstance();

        BlockPos origin = pos1;

        BlockPos size = pos1;
        size = size.subtract(origin);

        origin = origin.add(size.getX() < 0 ? 1 : 0, size.getY() < 0 ? 1 : 0, size.getZ() < 0 ? 1 : 0);
        size = size.add(size.getX() >= 0 ? 1 : -1, size.getY() >= 0 ? 1 : -1, size.getZ() >= 0 ? 1 : -1);

        matrices.push();

        VertexConsumer consumer = client.getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderLayer.getLines());
        matrices.translate(origin.getX() - camera.getPos().x, origin.getY() - camera.getPos().y, origin.getZ() - camera.getPos().z);

        RenderUtil.drawBox(matrices, consumer, 0, 0, 0, size.getX(), size.getY(), size.getZ(), r, g, b, a);
        matrices.pop();
    }

    public static void disablePostProcessor() {
        GameRenderer gameRenderer = MinecraftClient.getInstance().gameRenderer;

        if (gameRenderer.postProcessorEnabled)
            gameRenderer.togglePostProcessorEnabled();
        gameRenderer.clearPostProcessor();
    }

    public static void drawBox(
            MatrixStack matrices,
            VertexConsumer vertexConsumer,
            double x1,
            double y1,
            double z1,
            double x2,
            double y2,
            double z2,
            float red,
            float green,
            float blue,
            float alpha,
            float xAxisRed,
            float yAxisGreen,
            float zAxisBlue
    ) {
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float f = (float)x1;
        float g = (float)y1;
        float h = (float)z1;
        float i = (float)x2;
        float j = (float)y2;
        float k = (float)z2;
        vertexConsumer.vertex(matrix4f, f, g, h).color(red, yAxisGreen, zAxisBlue, alpha).normal(matrices.peek(), 1.0F, 0.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, i, g, h).color(red, yAxisGreen, zAxisBlue, alpha).normal(matrices.peek(), 1.0F, 0.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, f, g, h).color(xAxisRed, green, zAxisBlue, alpha).normal(matrices.peek(), 0.0F, 1.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, f, j, h).color(xAxisRed, green, zAxisBlue, alpha).normal(matrices.peek(), 0.0F, 1.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, f, g, h).color(xAxisRed, yAxisGreen, blue, alpha).normal(matrices.peek(), 0.0F, 0.0F, 1.0F);
        vertexConsumer.vertex(matrix4f, f, g, k).color(xAxisRed, yAxisGreen, blue, alpha).normal(matrices.peek(), 0.0F, 0.0F, 1.0F);
        vertexConsumer.vertex(matrix4f, i, g, h).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, 1.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, i, j, h).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, 1.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, i, j, h).color(red, green, blue, alpha).normal(matrices.peek(), -1.0F, 0.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, f, j, h).color(red, green, blue, alpha).normal(matrices.peek(), -1.0F, 0.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, f, j, h).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, 0.0F, 1.0F);
        vertexConsumer.vertex(matrix4f, f, j, k).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, 0.0F, 1.0F);
        vertexConsumer.vertex(matrix4f, f, j, k).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, -1.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, f, g, k).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, -1.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, f, g, k).color(red, green, blue, alpha).normal(matrices.peek(), 1.0F, 0.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, i, g, k).color(red, green, blue, alpha).normal(matrices.peek(), 1.0F, 0.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, i, g, k).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, 0.0F, -1.0F);
        vertexConsumer.vertex(matrix4f, i, g, h).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, 0.0F, -1.0F);
        vertexConsumer.vertex(matrix4f, f, j, k).color(red, green, blue, alpha).normal(matrices.peek(), 1.0F, 0.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, i, j, k).color(red, green, blue, alpha).normal(matrices.peek(), 1.0F, 0.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, i, g, k).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, 1.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, i, j, k).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, 1.0F, 0.0F);
        vertexConsumer.vertex(matrix4f, i, j, h).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, 0.0F, 1.0F);
        vertexConsumer.vertex(matrix4f, i, j, k).color(red, green, blue, alpha).normal(matrices.peek(), 0.0F, 0.0F, 1.0F);
    }

    public static void drawBox(
            MatrixStack matrices,
            VertexConsumer vertexConsumer,
            double x1,
            double y1,
            double z1,
            double x2,
            double y2,
            double z2,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        drawBox(matrices, vertexConsumer, x1, y1, z1, x2, y2, z2, red, green, blue, alpha, red, green, blue);
    }

}
