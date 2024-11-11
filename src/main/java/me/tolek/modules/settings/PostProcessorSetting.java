package me.tolek.modules.settings;

import me.tolek.modules.settings.base.ListSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class PostProcessorSetting extends ListSetting {
    public PostProcessorSetting() {
        super("mflp.setting.postProcessor.name", 0, "mflp.setting.postProcessor.tooltip", null);

        this.setList(new ArrayList<>());
        this.addOption("mflp.setting.postProcessor.none");
        for (Identifier program : GameRenderer.SUPER_SECRET_SETTING_PROGRAMS) {
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
            gameRenderer.loadPostProcessor(GameRenderer.SUPER_SECRET_SETTING_PROGRAMS[stateIndex - 1]);
    }

    public void setPostProcessor() {
        setPostProcessor(false);
    }
}
