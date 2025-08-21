package me.tolek.events;

import me.tolek.event.EventImpl;
import me.tolek.event.EventManager;
import me.tolek.event.RenderListener;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.ColorUtil;
import me.tolek.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class BlockOverlayRenderer extends EventImpl implements RenderListener {

    @Override
    public void onEnable() {
        EventManager.getInstance().add(RenderListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(RenderListener.class, this);
    }

    @Override
    public void onRender(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        final HitResult target = client.crosshairTarget;
        final ClientWorld world = client.world;
        if ((target == null) || world == null) return;
        BlockPos targetPos = new BlockPos(target.getType() == HitResult.Type.BLOCK ? ((BlockHitResult) target).getBlockPos() : BlockPos.ofFloored(target.getPos()));
        Block block = world.getBlockState(targetPos).getBlock();

        if (block == Blocks.AIR) return;

        if (MflpSettingsList.getInstance().CUSTOM_BLOCK_OUTLINE.getState()) {
            String[] hexVal = MflpSettingsList.getInstance().CUSTOM_BLOCK_OUTLINE_COLOR.getArgb();
            int a = ColorUtil.hex2Dec(hexVal[0]);
            int r = ColorUtil.hex2Dec(hexVal[1]);
            int g = ColorUtil.hex2Dec(hexVal[2]);
            int b = ColorUtil.hex2Dec(hexVal[3]);
            int color = ColorUtil.argbToInt(a, r, g, b);
            try {
                RenderUtil.drawOutlinedBox(matrices, RenderUtil.getBoundingBox(targetPos), color, false);
            } catch (Exception ignored) {
            }
        }
    }

}
