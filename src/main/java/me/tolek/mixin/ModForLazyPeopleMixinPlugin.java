package me.tolek.mixin;

import me.tolek.util.MiscUtils;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ModForLazyPeopleMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
        // Called when mixin configuration is loaded
    }

    @Override
    public String getRefMapperConfig() {
        return null; // Use default
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        boolean isTweakerooLoaded = MiscUtils.isModLoaded("tweakeroo");

        // TODO: this is hella messy, but it works for now
        if (mixinClassName.equals("me.tolek.mixin.client.Tweakeroo_CameraEntityMixin")) {
            return isTweakerooLoaded; // Only apply if tweakeroo is loaded
        }
        return true; // Apply other mixins
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        // No-op
    }

    @Override
    public List<String> getMixins() {
        return null; // Use mixins defined in JSON
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {
        // No-op
    }

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {
        // No-op
    }
}