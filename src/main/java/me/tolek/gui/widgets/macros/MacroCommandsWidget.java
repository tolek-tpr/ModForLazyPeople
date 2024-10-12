package me.tolek.gui.widgets.macros;

import me.tolek.modules.macro.Macro;
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
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MacroCommandsWidget extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();
    private TextRenderer tx;
    private String command;
    private int x;
    private int y;

    public MacroCommandsWidget(int x, int y, MinecraftClient client, String command, Macro macro, Screen parent, TextRenderer tx) {
        super(x, y, 150, 20, Text.literal("test"));

        this.tx = tx;
        this.command = command;
        this.x = x;
        this.y = y;

        ButtonWidget remove = ButtonWidget.builder(Text.translatable("mflp.remove"), (button) -> {
            macro.removeCommands(List.of(command));
            client.setScreen(new MflpConfigureMacroScreen(parent, macro));
        }).dimensions(x, y, 70, 20).build();
        addChild(remove);
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, command, 10, y + 10 - tx.fontHeight / 2, 0xffffff);
    }

    public void addChild(ClickableWidget child) {
        children.add(child);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
