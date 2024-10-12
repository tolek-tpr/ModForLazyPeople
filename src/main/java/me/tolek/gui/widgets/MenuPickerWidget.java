package me.tolek.gui.widgets;

import me.tolek.gui.screens.AutoReplyScreen;
import me.tolek.gui.screens.MflpConfig;
import me.tolek.gui.screens.MflpSettingsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MenuPickerWidget extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();

    public MenuPickerWidget(int x, int y, MinecraftClient client) {
        super(x, y, 150, 20, Text.literal("test"));

        ButtonWidget macrosButton = ButtonWidget.builder(Text.translatable("mflp.mainConfig.macrosButton"), (button) -> {
            client.setScreen(new MflpConfig(client));
        }).dimensions(width - width + 10, height - height + 22, 70, 20).build();
        ButtonWidget settingsWidget = ButtonWidget.builder(Text.translatable("mflp.mainConfig.settingsButton"), (button) -> {
            client.setScreen(new MflpSettingsScreen());
        }).dimensions(width - width + 82, height - height + 22, 70, 20).build();
        ButtonWidget autoReplyWidget = ButtonWidget.builder(Text.translatable("mflp.mainConfig.autoReplyButton"), (button) -> {
            client.setScreen(new AutoReplyScreen());
        }).dimensions(width - width + 154, height - height + 22, 80, 20).build();

        addChild(macrosButton);
        addChild(settingsWidget);
        addChild(autoReplyWidget);
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    public void addChild(ClickableWidget child) {
        children.add(child);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
