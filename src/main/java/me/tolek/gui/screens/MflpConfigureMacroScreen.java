package me.tolek.gui.screens;

import me.tolek.modules.Macro.Macro;
import me.tolek.gui.widgets.macros.MacroCommandsWidget;
import me.tolek.gui.widgets.macros.MacroSettingsBoxWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class MflpConfigureMacroScreen extends Screen {

    private Screen parent;
    private Macro macro;

    private MacroSettingsBoxWidget msbw;

    public MflpConfigureMacroScreen(Screen parent, Macro macro) {
        super(Text.translatable("mflp.macroConfigScreen.title"));
        this.parent = parent;
        this.macro = macro;

    }

    @Override
    public void init() {
        msbw = new MacroSettingsBoxWidget(10, 25, client, parent, macro, textRenderer, width, height);

        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen(parent);
        }).dimensions(width / 2 - 75, height - 30, 150, 20).build());

        addDrawableChild(msbw);
        msbw.children().forEach(this::addDrawableChild);

        if (!macro.getCommands().isEmpty()) {
            int step = 2;
            for (String command : macro.getCommands()) {
                MacroCommandsWidget mcw = new MacroCommandsWidget(200, 50 + step, client, command, macro, this.parent,
                        textRenderer);
                addDrawableChild(mcw);
                mcw.children().forEach(this::addDrawableChild);
                step += 22;
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        if (macro != null) {
            Text macroName = Text.literal(macro.getName());
            context.drawTextWithShadow(textRenderer, macroName, width / 2 - textRenderer.getWidth(macroName) / 2, 10, 0xffffff);
        }

        remove(msbw);
        msbw.children().forEach(this::remove);
        addDrawableChild(msbw);
        msbw.children().forEach(this::addDrawableChild);
        context.getScaledWindowHeight();
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

}
