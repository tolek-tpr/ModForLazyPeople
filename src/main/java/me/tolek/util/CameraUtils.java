package me.tolek.util;

import me.tolek.modules.betterFreeCam.CameraEntity;
import me.tolek.modules.settings.MflpSettingsList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.ChunkStatus;

public class CameraUtils {

    private static float cameraYaw;
    private static float cameraPitch;
    private static boolean freeCameraSpectator;

    public static void setFreeCameraSpectator(boolean isSpectator)
    {
        freeCameraSpectator = isSpectator;
    }

    public static boolean getFreeCameraSpectator()
    {
        return freeCameraSpectator;
    }

    public static boolean shouldPreventPlayerInputs()
    {
        return MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState();
    }

    public static boolean shouldPreventPlayerMovement()
    {
        return MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState();
    }

    public static float getCameraYaw()
    {
        return net.minecraft.util.math.MathHelper.wrapDegrees(cameraYaw);
    }

    public static float getCameraPitch()
    {
        return net.minecraft.util.math.MathHelper.wrapDegrees(cameraPitch);
    }

    public static void setCameraYaw(float yaw)
    {
        cameraYaw = yaw;
    }

    public static void setCameraPitch(float pitch)
    {
        cameraPitch = pitch;
    }

    public static void setCameraRotations(float yaw, float pitch)
    {
        CameraEntity camera = CameraEntity.getCamera();

        if (camera != null)
        {
            camera.setCameraRotations(yaw, pitch);
        }
    }

    public static void updateCameraRotations(float yawChange, float pitchChange)
    {
        CameraEntity camera = CameraEntity.getCamera();

        if (camera != null)
        {
            camera.updateCameraRotations(yawChange, pitchChange);
        }
    }

    public static void markChunksForRebuild(int chunkX, int chunkZ, int lastChunkX, int lastChunkZ)
    {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (mc.world == null || (chunkX == lastChunkX && chunkZ == lastChunkZ))
        {
            return;
        }

        final int viewDistance = mc.options.getViewDistance().getValue();

        if (chunkX != lastChunkX)
        {
            final int minCX = chunkX > lastChunkX ? lastChunkX + viewDistance : chunkX     - viewDistance;
            final int maxCX = chunkX > lastChunkX ? chunkX     + viewDistance : lastChunkX - viewDistance;

            for (int cx = minCX; cx <= maxCX; ++cx)
            {
                for (int cz = chunkZ - viewDistance; cz <= chunkZ + viewDistance; ++cz)
                {
                    if (isClientChunkLoaded(mc.world, cx, cz))
                    {
                        markChunkForReRender(mc.worldRenderer, cx, cz);
                    }
                }
            }
        }

        if (chunkZ != lastChunkZ)
        {
            final int minCZ = chunkZ > lastChunkZ ? lastChunkZ + viewDistance : chunkZ     - viewDistance;
            final int maxCZ = chunkZ > lastChunkZ ? chunkZ     + viewDistance : lastChunkZ - viewDistance;

            for (int cz = minCZ; cz <= maxCZ; ++cz)
            {
                for (int cx = chunkX - viewDistance; cx <= chunkX + viewDistance; ++cx)
                {
                    if (isClientChunkLoaded(mc.world, cx, cz))
                    {
                        markChunkForReRender(mc.worldRenderer, cx, cz);
                    }
                }
            }
        }
    }

    public static void markChunksForRebuildOnDeactivation(int lastChunkX, int lastChunkZ)
    {
        Entity entity = EntityUtils.getCameraEntity();
        MinecraftClient mc = MinecraftClient.getInstance();
        final int viewDistance = mc.options.getViewDistance().getValue();
        final int chunkX = MathHelper.floor(entity.getX() / 16.0) >> 4;
        final int chunkZ = MathHelper.floor(entity.getZ() / 16.0) >> 4;

        final int minCameraCX = lastChunkX - viewDistance;
        final int maxCameraCX = lastChunkX + viewDistance;
        final int minCameraCZ = lastChunkZ - viewDistance;
        final int maxCameraCZ = lastChunkZ + viewDistance;
        final int minCX = chunkX - viewDistance;
        final int maxCX = chunkX + viewDistance;
        final int minCZ = chunkZ - viewDistance;
        final int maxCZ = chunkZ + viewDistance;

        for (int cz = minCZ; cz <= maxCZ; ++cz)
        {
            for (int cx = minCX; cx <= maxCX; ++cx)
            {
                // Mark all chunks that were not in free camera range
                if ((cx < minCameraCX || cx > maxCameraCX || cz < minCameraCZ || cz > maxCameraCZ)) {
                    assert mc.world != null;
                    if (isClientChunkLoaded(mc.world, cx, cz)) {
                        markChunkForReRender(mc.worldRenderer, cx, cz);
                    }
                }
            }
        }
    }

    public static void markChunkForReRender(WorldRenderer renderer, int chunkX, int chunkZ)
    {
        for (int cy = 0; cy < 16; ++cy)
        {
            renderer.scheduleBlockRender(chunkX, cy, chunkZ);
        }
    }

    public static boolean isClientChunkLoaded(ClientWorld world, int chunkX, int chunkZ)
    {
        return world.getChunkManager().getChunk(chunkX, chunkZ, ChunkStatus.FULL, false) != null;
    }

}
