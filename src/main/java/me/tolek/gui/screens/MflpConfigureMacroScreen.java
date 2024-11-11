package me.tolek.gui.screens;

import me.tolek.gui.widgets.TextInputWidget;
import me.tolek.modules.macro.Macro;
import me.tolek.gui.widgets.macros.MacroCommandsWidget;
import me.tolek.gui.widgets.macros.MacroSettingsBoxWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class MflpConfigureMacroScreen extends Screen {

    private final Screen parent;
    private final Macro macro;

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

        int buttonBegin = 280 + textRenderer.getWidth(Text.translatable("mflp.macroSettings.worldOptionButton")) + 10;
        ButtonWidget worldSpecificOption = ButtonWidget.builder(Text.translatable(macro.getNameForSpecificWorld()), (button) -> {
            macro.nextWorldSpecificSetting();
            clearAndInit();
        }).dimensions(buttonBegin, 52, 100, 20).build();
        worldSpecificOption.setTooltip(Tooltip.of(Text.translatable("mflp.macroSettings.worldOptionButton.tooltip")));
        addDrawableChild(worldSpecificOption);

        int textFieldBegin = buttonBegin + 110 + textRenderer.getWidth(Text.translatable("mflp.macroSettings.worldOptionTextField")) + 10;
        TextInputWidget inputWidget = new TextInputWidget(textRenderer, textFieldBegin, 52, 150, 20, Text.literal(macro.getAllowedServers()));
        inputWidget.setChangedListener(macro::setAllowedServers);
        inputWidget.setTooltip(Tooltip.of(Text.translatable("mflp.macroSettings.worldOptionTextField.tooltip")));
        addDrawableChild(inputWidget);

        ButtonWidget executeOption = ButtonWidget.builder(Text.translatable(macro.getNameForExecuteOption()), (button) -> {
            macro.nextExecuteOption();
            clearAndInit();
        }).dimensions(280, 74, 100, 20).build();
        executeOption.setTooltip(Tooltip.of(Text.translatable("mflp.macroSettings.executeOptionTooltip")));
        addDrawableChild(executeOption);
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

        int buttonBegin = 280 + textRenderer.getWidth(Text.translatable("mflp.macroSettings.worldOptionButton")) + 10;
        int yOffset = 10 - textRenderer.fontHeight / 2;

        context.drawTextWithShadow(textRenderer, Text.translatable("mflp.macroSettings.worldOptionButton"), 280, 52 + yOffset, 0xffffff);
        context.drawTextWithShadow(textRenderer, Text.translatable("mflp.macroSettings.worldOptionTextField"), buttonBegin + 110, 52 + yOffset, 0xffffff);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

}
