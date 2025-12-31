package me.tolek.mixin;

import me.tolek.mixin.client.Tweakeroo_CameraEntityMixin;
import me.tolek.util.MiscUtils;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ModForLazyPeopleMixinPlugin implements IMixinConfigPlugin {

    @SuppressWarnings("ReferenceToMixin")
    private final HashMap<String, String[]> mixinDependencyMap = new HashMap<>() {
        {
            put(Tweakeroo_CameraEntityMixin.class.getName(), new String[] { "tweakeroo" });
        }
    };

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
        // Check if it has dependencies
        if (mixinDependencyMap.containsKey(mixinClassName)) {
            // It has dependencies: go through them
            for (String dependency : mixinDependencyMap.get(mixinClassName)) {
                // Check if the dependency is loaded
                if (!MiscUtils.isModLoaded(dependency))
                    // It's not, we cannot apply this mixin.
                    return false;
            }
        }

        // All dependencies (if any) are loaded, we can apply it.
        return true;
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