package me.tolek.modules.betterFreeCam;

import me.tolek.modules.settings.FreeCamMovementMode;
import me.tolek.modules.settings.FreeCamWalkingMode;
import me.tolek.modules.settings.MflpSettingsList;
import me.tolek.util.CameraUtils;
import me.tolek.util.MiscUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.stat.StatHandler;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CameraEntity extends ClientPlayerEntity
{
    @Nullable private static CameraEntity camera;
    @Nullable private static Entity originalCameraEntity;
    private static Vec3d cameraMotion = new Vec3d(0.0, 0.0, 0.0);
    private static boolean cullChunksOriginal;
    private static boolean sprinting;
    private static boolean originalCameraWasPlayer;

    private CameraEntity(MinecraftClient mc, ClientWorld world,
                         ClientPlayNetworkHandler netHandler, StatHandler stats,
                         ClientRecipeBook recipeBook)
    {
        super(mc, world, netHandler, stats, recipeBook, false, false);
    }

    @Override
    public boolean isSpectator()
    {
        return true;
    }

    public static boolean isActive() { return getCamera() != null; }

    public static void movementTick()
    {
        CameraEntity camera = getCamera();

        if (camera != null && MflpSettingsList.getInstance().FREE_CAM_MOVEMENT_MODE.stateIndex == FreeCamMovementMode.CAMERA)
        {
            GameOptions options = MinecraftClient.getInstance().options;

            camera.updateLastTickPosition();

            if (options.sprintKey.isPressed())
            {
                sprinting = true;
            }
            else if (!options.forwardKey.isPressed() && !options.backKey.isPressed())
            {
                sprinting = false;
            }

            cameraMotion = MiscUtils.calculatePlayerMotionWithDeceleration(cameraMotion, 0.15, 0.4);
            double forward = sprinting ? cameraMotion.x * 3 : cameraMotion.x;

            camera.handleMotion(forward, cameraMotion.y, cameraMotion.z);
        }
    }

    private static double getMoveSpeed()
    {
        return 0.7 * MflpSettingsList.getInstance().FREE_CAM_MOVEMENT_SPEED.getState();
    }

    private void handleMotion(double forward, double up, double strafe)
    {
        float yaw = this.getYaw();
        double scale = getMoveSpeed();
        double xFactor = Math.sin(yaw * Math.PI / 180.0);
        double zFactor = Math.cos(yaw * Math.PI / 180.0);

        double x = (strafe * zFactor - forward * xFactor) * scale;
        double y = up * scale;
        double z = (forward * zFactor + strafe * xFactor) * scale;

        this.setVelocity(new Vec3d(x, y, z));

        if (MflpSettingsList.getInstance().FREE_CAM_WALKING_MODE.stateIndex == FreeCamWalkingMode.WALK)
            this.collidingMove(this.getVelocity());
        else
            this.move(MovementType.SELF, this.getVelocity());
    }

    private void collidingMove(Vec3d movement) {
        Box box = this.getBoundingBox();
        List<VoxelShape> list = this.getWorld().getEntityCollisions(this, box.stretch(movement));
        Vec3d adjustedMovement = movement.lengthSquared() == 0.0 ? movement : Entity.adjustMovementForCollisions(this, movement, box, this.getWorld(), list);

        final double x = movement.x + getX();
        double y = movement.y + getY();
        final double z = movement.z + getZ();

        if (movement.y < adjustedMovement.y)
            y = getY();

        this.setPosition(x, y, z);
    }

    private void updateLastTickPosition()
    {
        this.lastRenderX = this.getX();
        this.lastRenderY = this.getY();
        this.lastRenderZ = this.getZ();

        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();

        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();

        this.prevHeadYaw = this.headYaw;
    }

    public void setCameraRotations(float yaw, float pitch)
    {
        this.setYaw(yaw);
        this.setPitch(pitch);

        this.headYaw = yaw;

        //this.prevRotationYaw = this.rotationYaw;
        //this.prevRotationPitch = this.rotationPitch;

        //this.prevRotationYawHead = this.rotationYaw;
        //this.setRenderYawOffset(this.rotationYaw);
    }

    public void updateCameraRotations(float yawChange, float pitchChange)
    {
        float yaw = this.getYaw() + yawChange * 0.15F;
        float pitch = MathHelper.clamp(this.getPitch() + pitchChange * 0.15F, -90F, 90F);

        this.setYaw(yaw);
        this.setPitch(pitch);

        this.setCameraRotations(yaw, pitch);
    }

    private static CameraEntity createCameraEntity(MinecraftClient mc)
    {
        ClientPlayerEntity player = mc.player;
        assert player != null;
        CameraEntity camera = new CameraEntity(mc, mc.world, player.networkHandler, player.getStatHandler(), player.getRecipeBook());
        camera.noClip = true;
        float yaw = player.getYaw();
        float pitch = player.getPitch();

        camera.refreshPositionAndAngles(player.getX(), player.getY(), player.getZ(), yaw, pitch);
        camera.setRotation(yaw, pitch);

        return camera;
    }

    @Nullable
    public static CameraEntity getCamera()
    {
        return camera;
    }

    public static void setCameraState(boolean enabled)
    {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (mc.world != null && mc.player != null)
        {
            if (enabled)
            {
                createAndSetCamera(mc);
            }
            else
            {
                removeCamera(mc);
            }

            mc.gameRenderer.setRenderHand(!enabled);
        }
    }

    public static boolean originalCameraWasPlayer()
    {
        return originalCameraWasPlayer;
    }

    private static void createAndSetCamera(MinecraftClient mc)
    {
        camera = createCameraEntity(mc);
        originalCameraEntity = mc.getCameraEntity();
        originalCameraWasPlayer = originalCameraEntity == mc.player;
        cullChunksOriginal = mc.chunkCullingEnabled;

        mc.setCameraEntity(camera);
        mc.chunkCullingEnabled = false; // Disable chunk culling

        // Disable the motion option when entering camera mode
        MflpSettingsList.getInstance().FREE_CAM_MOVEMENT_MODE.stateIndex = 0;
    }

    private static void removeCamera(MinecraftClient mc)
    {
        if (mc.world != null && camera != null)
        {
            // Re-fetch the player entity, in case the player died while in Free Camera mode and the instance changed
            mc.setCameraEntity(originalCameraWasPlayer ? mc.player : originalCameraEntity);
            mc.chunkCullingEnabled = cullChunksOriginal;

            final int chunkX = MathHelper.floor(camera.getX() / 16.0) >> 4;
            final int chunkZ = MathHelper.floor(camera.getZ() / 16.0) >> 4;
            CameraUtils.markChunksForRebuildOnDeactivation(chunkX, chunkZ);
        }

        originalCameraEntity = null;
        camera = null;
    }
}
