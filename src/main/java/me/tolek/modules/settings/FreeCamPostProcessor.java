package me.tolek.modules.settings;

import me.tolek.modules.betterFreeCam.CameraEntity;
import me.tolek.modules.settings.base.ListSetting;
import me.tolek.util.CameraUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

// Don't worry about SUPER_SECRET_SETTING_PROGRAMS, it's just IntelliJ shitting itself not realizing I put in an accesswidener
public class FreeCamPostProcessor extends ListSetting {
    public FreeCamPostProcessor() {
        super("mflp.setting.freeCamPostProcessor.name", 0, "mflp.unused", null);

        this.setList(new ArrayList<>());
        this.addOption("mflp.setting.freeCamPostProcessor.none");
        for (Identifier program : GameRenderer.SUPER_SECRET_SETTING_PROGRAMS) {
            this.addOption("mflp.setting.freeCamPostProcessor." + program.getPath());
        }

        this.renderHotkey = true;
        this.render = false;
    }

    @Override
    public void run() {
        if (stateIndex == getList().size() - 1) {
            stateIndex = 0;
        } else {
            stateIndex = stateIndex + 1;
        }

        GameRenderer gameRenderer = MinecraftClient.getInstance().gameRenderer;
        if (stateIndex == 0)
            gameRenderer.disablePostProcessor();
        else
            gameRenderer.loadPostProcessor(GameRenderer.SUPER_SECRET_SETTING_PROGRAMS[stateIndex - 1]);
    }
}
