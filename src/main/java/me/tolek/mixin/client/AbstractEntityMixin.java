package me.tolek.mixin.client;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class AbstractEntityMixin {
    @Shadow
    public abstract Vec3d getVelocity();

    @Shadow public abstract Box getBoundingBox();
    @Shadow public abstract World getWorld();

    @Shadow public abstract double getX();
    @Shadow public abstract double getY();
    @Shadow public abstract double getZ();

    @Shadow public abstract void setPosition(double x, double y, double z);
}
