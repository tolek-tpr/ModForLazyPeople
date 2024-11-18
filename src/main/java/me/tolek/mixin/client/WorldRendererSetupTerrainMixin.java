package me.tolek.mixin.client;

import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.CameraUtils;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.MathHelper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererSetupTerrainMixin {

    @Shadow private int cameraChunkX;
    @Shadow private int cameraChunkZ;

    @Unique private int lastUpdatePosX;
    @Unique private int lastUpdatePosZ;

    // These injections will fail when Sodium is present, but the Free Camera
    // rendering seems to work fine with Sodium without these anyway
    @Inject(method = "setupTerrain", require = 0,
            at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD,
                    target = "Lnet/minecraft/client/render/WorldRenderer;lastCameraX:D"))
    private void rebuildChunksAroundCamera1(
            Camera camera, Frustum frustum, boolean hasForcedFrustum, boolean spectator, CallbackInfo ci)
    {
        if (MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState())
        {
            // Hold on to the previous update position before it gets updated
            this.lastUpdatePosX = this.cameraChunkX;
            this.lastUpdatePosZ = this.cameraChunkZ;
        }
    }

    // These injections will fail when Sodium is present, but the Free Camera
    // rendering seems to work fine with Sodium without these anyway
    @Inject(method = "setupTerrain", require = 0,
            at = @At(value = "INVOKE", shift = At.Shift.AFTER,
                    target = "Lnet/minecraft/client/render/BuiltChunkStorage;updateCameraPosition(DD)V"))
    private void rebuildChunksAroundCamera2(
            Camera camera, Frustum frustum, boolean hasForcedFrustum, boolean spectator, CallbackInfo ci)
    {
        // Mark the chunks at the edge of the free camera's render range for rebuilding
        // when the camera moves around.
        // Normally these rebuilds would happen when the server sends chunks to the client when the player moves around.
        // But in Free Camera mode moving the ViewFrustum/BuiltChunkStorage would cause the terrain
        // to disappear because of no dirty marking calls from chunk loading.
        if (MflpSettingsList.getInstance().FREE_CAM_ENABLED.getState())
        {
            int x = MathHelper.floor(camera.getPos().x) >> 4;
            int z = MathHelper.floor(camera.getPos().z) >> 4;
            CameraUtils.markChunksForRebuild(x, z, this.lastUpdatePosX, this.lastUpdatePosZ);
        }
    }

}