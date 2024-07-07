package me.tolek.gui.widgets.settingsWidgets.rework;

import me.tolek.gui.screens.MflpSettingsScreen;
import me.tolek.modules.settings.base.ListSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class ListWidget extends AbstractSettingWidget {

    private final ListSetting setting;
    private final TextRenderer tx;
    private ArrayList<ClickableWidget> children = new ArrayList<>();

    private final int x;
    private final int y;

    public ListWidget(int x, int y, Text text, ListSetting setting, TextRenderer tx) {
        super(x, y, 310, 20, text);
        this.setting = setting;
        this.tx = tx;
        this.x = x;
        this.y =y;

        // Rendering code
        Text buttonText = Text.literal(String.valueOf(setting.getState()));
        ButtonWidget cycleButton = ButtonWidget.builder(buttonText, (button) -> {
                    setting.run();
                    MinecraftClient.getInstance().setScreen(new MflpSettingsScreen());
                }).tooltip(Tooltip.of(Text.literal(setting.getTooltip())))
                .dimensions(x + 5, y, 150, 20)
                .build();
        children.add(cycleButton);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, setting.getName(), x - 155,
                y + 10 - tx.fontHeight / 2, 0xffffff);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public ArrayList<ClickableWidget> children() {
        return children;
    }

}
