package makamys.neodymium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment.Phase;
import org.spongepowered.asm.mixin.MixinEnvironment.Side;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import makamys.neodymium.config.Config;
import makamys.neodymium.util.OFUtil;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    
    @Override
    public void onLoad(String mixinPackage) {
        if(MixinEnvironment.getCurrentEnvironment().getSide() == Side.SERVER) return;
        
        Config.reloadConfig();
        
        Phase phase = MixinEnvironment.getCurrentEnvironment().getPhase();
        if(phase == Phase.INIT) {
            Compat.forceEnableOptiFineDetectionOfFastCraft();
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        if(MixinEnvironment.getCurrentEnvironment().getSide() == Side.SERVER) return Collections.emptyList();
        
        List<String> mixins = new ArrayList<>();
        Phase phase = MixinEnvironment.getCurrentEnvironment().getPhase();
        if(phase == Phase.DEFAULT) {
            mixins.addAll(Arrays.asList(
                "MixinRenderGlobal",
                "MixinWorldRenderer",
                "MixinTessellator"));
                
            if (OFUtil.isOptiFinePresent()) {
                System.out.println("Detected OptiFine");
                mixins.add("MixinRenderGlobal_OptiFine");
                mixins.add("MixinGameSettings_OptiFine");
            }
            
            if(Config.replaceOpenGLSplash) {
                mixins.add("MixinGuiMainMenu");
            }
        }
        
        return mixins;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
    
}
