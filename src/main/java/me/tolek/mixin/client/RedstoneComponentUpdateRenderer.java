package me.tolek.mixin.client;

import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.MflpUtil;
import me.tolek.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class RedstoneComponentUpdateRenderer {

    @Unique
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final MflpSettingsList settingsList = MflpSettingsList.getInstance();

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;", opcode = Opcodes.GETFIELD, ordinal = 1))
    private void drawAreaSelection(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
        final HitResult target = client.crosshairTarget;
        final ClientWorld world = client.world;
        if ((target == null) || world == null) return;
        BlockPos targetPos = new BlockPos(target.getType() == HitResult.Type.BLOCK ? ((BlockHitResult) target).getBlockPos() : BlockPos.ofFloored(target.getPos()));
        Block block = world.getBlockState(targetPos).getBlock();

        if (block == Blocks.AIR) return;

        // Redstone Dust
        if (block == Blocks.REDSTONE_WIRE && settingsList.DUST_UPDATE_VIEW.getState()) {
            String[] hexVal = settingsList.DUST_UPDATE_COLOR.getArgb();
            float a = (float) MflpUtil.hex2DecBetween1And0(hexVal[0]);
            float r = (float) MflpUtil.hex2DecBetween1And0(hexVal[1]);
            float g = (float) MflpUtil.hex2DecBetween1And0(hexVal[2]);
            float b = (float) MflpUtil.hex2DecBetween1And0(hexVal[3]);

            // Main block
            RenderUtil.drawBoxInWorld(matrices, camera, targetPos, r, g, b, a);

            // Lines
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);

            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 2, targetPos.getY(), targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 2, targetPos.getY(), targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() + 2), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() - 2), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 2, targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 2, targetPos.getZ()), r, g, b, a);

            // Corners
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);

            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);

            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ() + 1), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ() - 1), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ() + 1), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ() - 1), r, g, b, a);
        } else if ((block == Blocks.REPEATER && settingsList.REPEATER_UPDATE_VIEW.getState()) || (block == Blocks.COMPARATOR && settingsList.COMPARATOR_UPDATE_VIEW.getState())) {
            if (world.getBlockState(targetPos).get(Properties.HORIZONTAL_FACING) == null) return;
            float a;
            float r;
            float g;
            float b;

            if (block == Blocks.REPEATER) {
                String[] hexVal = settingsList.REPEATER_UPDATE_COLOR.getArgb();
                a = (float) MflpUtil.hex2DecBetween1And0(hexVal[0]);
                r = (float) MflpUtil.hex2DecBetween1And0(hexVal[1]);
                g = (float) MflpUtil.hex2DecBetween1And0(hexVal[2]);
                b = (float) MflpUtil.hex2DecBetween1And0(hexVal[3]);
            } else {
                String[] hexVal = settingsList.COMPARATOR_UPDATE_COLOR.getArgb();
                a = (float) MflpUtil.hex2DecBetween1And0(hexVal[0]);
                r = (float) MflpUtil.hex2DecBetween1And0(hexVal[1]);
                g = (float) MflpUtil.hex2DecBetween1And0(hexVal[2]);
                b = (float) MflpUtil.hex2DecBetween1And0(hexVal[3]);
            }

            switch (world.getBlockState(targetPos).get(Properties.HORIZONTAL_FACING)) {
                case NORTH -> {
                    // Z - 1
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() + 2), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ() + 1), r, g, b, a);
                }
                case SOUTH -> {
                    // Z + 1
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() - 2), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ() - 1), r, g, b, a);
                }
                case EAST -> {
                    // X + 1
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 2, targetPos.getY(), targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
                }
                case WEST -> {
                    // X - 1
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 2, targetPos.getY(), targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
                }
            }
        } else if (block == Blocks.OBSERVER && settingsList.OBSERVER_UPDATE_VIEW.getState()) {
            if (world.getBlockState(targetPos).get(Properties.FACING) == null) return;

            String[] hexVal = settingsList.OBSERVER_UPDATE_COLOR.getArgb();
            float a = (float) MflpUtil.hex2DecBetween1And0(hexVal[0]);
            float r = (float) MflpUtil.hex2DecBetween1And0(hexVal[1]);
            float g = (float) MflpUtil.hex2DecBetween1And0(hexVal[2]);
            float b = (float) MflpUtil.hex2DecBetween1And0(hexVal[3]);

            switch (world.getBlockState(targetPos).get(Properties.FACING)) {
                case NORTH -> {
                    // Z - 1
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() + 2), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ() + 1), r, g, b, a);
                }
                case SOUTH -> {
                    // Z + 1
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() - 2), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ() - 1), r, g, b, a);
                }
                case EAST -> {
                    // X + 1
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 2, targetPos.getY(), targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
                }
                case WEST -> {
                    // X - 1
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 2, targetPos.getY(), targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
                }
                case UP -> {
                    // Y + 1
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 2, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ() - 1), r, g, b, a);
                }
                case DOWN -> {
                    // Y - 1
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 2, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ() + 1), r, g, b, a);
                    RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ() - 1), r, g, b, a);
                }
            }
        } else if ((block == Blocks.POWERED_RAIL || block == Blocks.ACTIVATOR_RAIL) && settingsList.RAILS_UPDATE_VIEW.getState()) {
            String[] hexVal = settingsList.RAILS_UPDATE_COLOR.getArgb();
            float a = (float) MflpUtil.hex2DecBetween1And0(hexVal[0]);
            float r = (float) MflpUtil.hex2DecBetween1And0(hexVal[1]);
            float g = (float) MflpUtil.hex2DecBetween1And0(hexVal[2]);
            float b = (float) MflpUtil.hex2DecBetween1And0(hexVal[3]);

            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 2, targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ()), r, g, b, a);

            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY(), targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY(), targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() + 1), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ() - 1), r, g, b, a);

            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() + 1, targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX() - 1, targetPos.getY() - 1, targetPos.getZ()), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ() + 1), r, g, b, a);
            RenderUtil.drawBoxInWorld(matrices, camera, new BlockPos(targetPos.getX(), targetPos.getY() - 1, targetPos.getZ() - 1), r, g, b, a);
        }
    }

}
