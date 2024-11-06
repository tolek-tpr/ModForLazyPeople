package me.tolek.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

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

        WorldRenderer.drawBox(matrices, consumer, 0, 0, 0, size.getX(), size.getY(), size.getZ(), r, g, b, 1);

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

        WorldRenderer.drawBox(matrices, consumer, 0, 0, 0, size.getX(), size.getY(), size.getZ(), r, g, b, a);

        matrices.pop();
    }

}
