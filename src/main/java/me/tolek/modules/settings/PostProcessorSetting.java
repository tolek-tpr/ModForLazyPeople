package me.tolek.modules.settings;

import me.tolek.ModForLazyPeople;
import me.tolek.modules.settings.base.ListSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class PostProcessorSetting extends ListSetting {
    public static final Identifier[] SUPER_SECRET_SETTING_PROGRAMS = new Identifier[]{Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/notch.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/fxaa.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/art.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/bumpy.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/blobs2.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/pencil.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/color_convolve.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/deconverge.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/flip.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/invert.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/ntsc.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/outline.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/phosphor.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/scan_pincushion.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/sobel.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/bits.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/desaturate.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/green.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/blur.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/wobble.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/blobs.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/antialias.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/creeper.json"), Identifier.of(ModForLazyPeople.MOD_ID, "shaders/post/spider.json")};

    public PostProcessorSetting() {
        super("mflp.setting.postProcessor.name", 0, "mflp.setting.postProcessor.tooltip", null);

        this.setList(new ArrayList<>());
        this.addOption("mflp.setting.postProcessor.none");
        for (Identifier program : SUPER_SECRET_SETTING_PROGRAMS) {
            this.addOption("mflp.setting.postProcessor." + program.getPath());
        }

        this.renderHotkey = true;
    }

    @Override
    public void run() {
        if (stateIndex == getList().size() - 1) {
            stateIndex = 0;
        } else {
            stateIndex = stateIndex + 1;
        }

        setPostProcessor();
    }

    public void setPostProcessor(boolean bailOutIfNoneSelected) {
        GameRenderer gameRenderer = MinecraftClient.getInstance().gameRenderer;
        if (stateIndex == 0 && bailOutIfNoneSelected)
            return;
        gameRenderer.disablePostProcessor();
        if (stateIndex != 0)
            gameRenderer.loadPostProcessor(SUPER_SECRET_SETTING_PROGRAMS[stateIndex - 1]);
    }

    public void setPostProcessor() {
        setPostProcessor(false);
    }
}
