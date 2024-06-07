package me.tolek.gui;

import me.tolek.Macro.Macro;
import me.tolek.Macro.MacroList;
import me.tolek.gui.widgets.InputBoxWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.List;

public class MflpConfigureMacroScreen extends Screen {

    private Screen parent;
    private Macro macro;
    private MacroList macroList = MacroList.getInstance();

    public MflpConfigureMacroScreen(Screen parent, Macro macro) {
        super(Text.translatable("mflp.macroConfigScreen.title"));
        this.parent = parent;
        this.macro = macro;
    }

    @Override
    public void init() {
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            client.setScreen(parent);
        }).dimensions(width / 2 - 75, height - 30, 150, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Rename"), (button) -> {
            InputBoxWidget inputBox = new InputBoxWidget(textRenderer, width / 2 - 40, height / 2 - 40, 80, 20, Text.literal(macro.getName()), Text.literal("E"));
            inputBox.setKeyConsumer((keyCode -> {
                if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                    //remove(inputBox);
                    client.setScreen(new MflpConfigureMacroScreen(this.parent, this.macro));
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER) {
                    macro.setName(inputBox.getText());
                    remove(inputBox);
                    client.setScreen(new MflpConfigureMacroScreen(this.parent, this.macro));
                }
            }));

            addDrawableChild(inputBox);
        }).dimensions(width - width + 10, height - height + 25, 70, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Duplicate"), (button) -> {
            Macro m = macro.copy();
            macroList.addMacro(m);
            client.setScreen(parent);
        }).dimensions(width - width + 85, height - height + 25, 70, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Add command"), (button) -> {
            InputBoxWidget inputBox = new InputBoxWidget(textRenderer, width / 2 - 50, height / 2, 100, 20, Text.literal("Command"), Text.literal("E"));
            inputBox.setKeyConsumer((keyCode -> {
                if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                    client.setScreen(new MflpConfigureMacroScreen(this.parent, this.macro));
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER) {
                    macro.addCommands(List.of(inputBox.getText()));
                    remove(inputBox);
                    client.setScreen(new MflpConfigureMacroScreen(this.parent, this.macro));
                }
            }));

            addDrawableChild(inputBox);
        }).dimensions(width - width + 160, height - height + 25, 80, 20).build());

        if (!macro.getCommands().isEmpty()) {
            int step = 2;
            for (String command : macro.getCommands()) {
                addDrawableChild(ButtonWidget.builder(Text.literal("Remove"), (button) -> {
                    macro.removeCommands(List.of(command));
                    client.setScreen(new MflpConfigureMacroScreen(this.parent, this.macro));
                }).dimensions(width - width + 200, height - height + 50 + step, 70, 20).build());
                step += 22;
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (macro != null) {
            Text macroName = Text.literal(macro.getName());
            context.drawTextWithShadow(textRenderer, macroName, width / 2 - textRenderer.getWidth(macroName) / 2, height - height + 10, 0xffffff);

            if (!macro.getCommands().isEmpty()) {
                int step = 2;
                for (String command : macro.getCommands()) {
                    context.drawTextWithShadow(textRenderer, command, width - width + 10, height - height + 55 + step, 0xffffff);
                    step += 23.5;
                }
            }
        }
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

}
