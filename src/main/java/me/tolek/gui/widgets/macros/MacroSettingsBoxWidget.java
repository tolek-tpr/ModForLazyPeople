package me.tolek.gui.widgets.macros;

import me.tolek.gui.widgets.InputBoxWidget;
import me.tolek.modules.Macro.Macro;
import me.tolek.modules.Macro.MacroList;
import me.tolek.gui.screens.MflpConfigureMacroScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MacroSettingsBoxWidget extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();
    private MacroList macroList = MacroList.getInstance();
    private TextRenderer tx;
    private int x;
    private int y;
    private Macro m;
    private Screen parent;
    private int width;
    private int height;

    public MacroSettingsBoxWidget(int x, int y, MinecraftClient client, Screen parent, Macro m, TextRenderer tx, int width, int height) {
        super(x, y, 150, 20, Text.literal("test"));

        this.tx = tx;
        this.x = x;
        this.y = y;
        this.m = m;
        this.parent = parent;
        this.width = width;
        this.height = height;

        ButtonWidget rename = ButtonWidget.builder(Text.translatable("mflp.rename"), (button) -> {
            InputBoxWidget inputBox = new InputBoxWidget(tx, width / 2 - 75, height / 2 - 40, 150, 20, Text.literal(this.m.getName()));
            inputBox.setKeyConsumer((keyCode -> {
                if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                    client.setScreen(new MflpConfigureMacroScreen(this.parent, this.m));
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER) {
                    this.m.setName(inputBox.getText());
                    client.setScreen(new MflpConfigureMacroScreen(this.parent, this.m));
                }
            }));

            addChild(inputBox);
        }).dimensions(x, y, 70, 20).build();
        ButtonWidget duplicate = ButtonWidget.builder(Text.translatable("mflp.duplicate"), (button) -> {
            Macro ma = this.m.copy();
            macroList.addMacro(ma);
            client.setScreen(parent);
        }).dimensions(x + 72, y, 70, 20).build();
        ButtonWidget addCommand = ButtonWidget.builder(Text.translatable("mflp.macroSettings.addCommandButton"), (button) -> {
            InputBoxWidget inputBox = new InputBoxWidget(tx, width / 2 - 75, height / 2 - 40, 150, 20, Text.literal("Command"));
            inputBox.setKeyConsumer((keyCode -> {
                if (keyCode == InputUtil.GLFW_KEY_ESCAPE) {
                    client.setScreen(new MflpConfigureMacroScreen(this.parent, this.m));
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER) {
                    this.m.addCommands(List.of(inputBox.getText()));
                    client.setScreen(new MflpConfigureMacroScreen(this.parent, this.m));
                }
            }));

            addChild(inputBox);
        }).dimensions(x + 144, y, 80, 20).build();

        addChild(rename);
        addChild(duplicate);
        addChild(addCommand);
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {}

    public void addChild(ClickableWidget child) {
        children.add(child);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
