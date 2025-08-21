package me.tolek.util;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.systems.RenderSystem;
import me.tolek.mixin.client.RedstoneComponentUpdateRenderer;
import me.tolek.render.MflpRenderLayers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class RenderUtil {

    public static VertexConsumerProvider.Immediate getVCP()
    {
        return MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
    }

    public static Vec3d getCameraPos() {
        Camera c = MinecraftClient.getInstance().getBlockEntityRenderDispatcher().camera;
        if (c == null) return Vec3d.ZERO;

        return c.getPos();
    }

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

    public static Box getBoundingBox(BlockPos pos)
    {
        return MinecraftClient.getInstance().world.getBlockState(pos).getOutlineShape(MinecraftClient.getInstance().world, pos).getBoundingBox().offset(pos);
    }

    public static void drawOutlinedBox(MatrixStack matrices, BlockPos pos, int color) {
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);

        VertexConsumerProvider.Immediate vcp = getVCP();
        RenderLayer layer = MflpRenderLayers.getLines(false);
        VertexConsumer consumer = vcp.getBuffer(layer);
        Vec3d camOffset = getCameraPos().negate();
        var box = getBoundingBox(pos);

        drawOutlinedBox(matrices, consumer, box.offset(camOffset), color);

        vcp.drawCurrentLayer();
    }

    public static void drawOutlinedBox(MatrixStack matrices, Box box, int color,
                                       boolean depthTest) {
        int depthFunc = depthTest ? GlConst.GL_LEQUAL : GlConst.GL_ALWAYS;
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(depthFunc);

        VertexConsumerProvider.Immediate vcp = getVCP();
        RenderLayer layer = MflpRenderLayers.getLines(depthTest);
        VertexConsumer buffer = vcp.getBuffer(layer);

        drawOutlinedBox(matrices, buffer, box.offset(getCameraPos().negate()),
                color);

        vcp.draw(layer);
    }

    public static void drawOutlinedBox(MatrixStack matrices, Box box, int color) {
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GlConst.GL_ALWAYS);

        VertexConsumerProvider.Immediate vcp = getVCP();
        RenderLayer layer = MflpRenderLayers.getLines(false);
        VertexConsumer consumer = vcp.getBuffer(layer);

        Vec3d camOffset = getCameraPos().negate();
        box = box.offset(camOffset);

        drawOutlinedBox(matrices, consumer, box, color);
    }

    public static void drawOutlinedBox(MatrixStack matrices, VertexConsumer consumer, Box box, int color) {
        MatrixStack.Entry entry = matrices.peek();
        float x1 = (float)box.minX;
        float y1 = (float)box.minY;
        float z1 = (float)box.minZ;
        float x2 = (float)box.maxX;
        float y2 = (float)box.maxY;
        float z2 = (float)box.maxZ;

        // bottom lines
        consumer.vertex(entry, x1, y1, z1).color(color).normal(entry, 1, 0, 0);
        consumer.vertex(entry, x2, y1, z1).color(color).normal(entry, 1, 0, 0);
        consumer.vertex(entry, x1, y1, z1).color(color).normal(entry, 0, 0, 1);
        consumer.vertex(entry, x1, y1, z2).color(color).normal(entry, 0, 0, 1);
        consumer.vertex(entry, x2, y1, z1).color(color).normal(entry, 0, 0, 1);
        consumer.vertex(entry, x2, y1, z2).color(color).normal(entry, 0, 0, 1);
        consumer.vertex(entry, x1, y1, z2).color(color).normal(entry, 1, 0, 0);
        consumer.vertex(entry, x2, y1, z2).color(color).normal(entry, 1, 0, 0);

        // top lines
        consumer.vertex(entry, x1, y2, z1).color(color).normal(entry, 1, 0, 0);
        consumer.vertex(entry, x2, y2, z1).color(color).normal(entry, 1, 0, 0);
        consumer.vertex(entry, x1, y2, z1).color(color).normal(entry, 0, 0, 1);
        consumer.vertex(entry, x1, y2, z2).color(color).normal(entry, 0, 0, 1);
        consumer.vertex(entry, x2, y2, z1).color(color).normal(entry, 0, 0, 1);
        consumer.vertex(entry, x2, y2, z2).color(color).normal(entry, 0, 0, 1);
        consumer.vertex(entry, x1, y2, z2).color(color).normal(entry, 1, 0, 0);
        consumer.vertex(entry, x2, y2, z2).color(color).normal(entry, 1, 0, 0);

        // side lines
        consumer.vertex(entry, x1, y1, z1).color(color).normal(entry, 0, 1, 0);
        consumer.vertex(entry, x1, y2, z1).color(color).normal(entry, 0, 1, 0);
        consumer.vertex(entry, x2, y1, z1).color(color).normal(entry, 0, 1, 0);
        consumer.vertex(entry, x2, y2, z1).color(color).normal(entry, 0, 1, 0);
        consumer.vertex(entry, x1, y1, z2).color(color).normal(entry, 0, 1, 0);
        consumer.vertex(entry, x1, y2, z2).color(color).normal(entry, 0, 1, 0);
        consumer.vertex(entry, x2, y1, z2).color(color).normal(entry, 0, 1, 0);
        consumer.vertex(entry, x2, y2, z2).color(color).normal(entry, 0, 1, 0);
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
