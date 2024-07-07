package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.modules.settings.base.IntegerSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class IntegerSettingWidgetContainer extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();
    private IntegerSetting is;
    private TextRenderer tx;
    private int x;
    private int y;
    private MinecraftClient client;
    public IntegerSettingWidget isw;

    public IntegerSettingWidgetContainer(int x, int y, Text text, IntegerSetting is, TextRenderer tx, MinecraftClient client) {
        super(x, y, 310, 20, text);
        this.is = is;
        this.tx = tx;
        this.x = x;
        this.y = y;
        this.client = client;

        IntegerSettingWidget isw = new IntegerSettingWidget(tx, x + 5, y, is);
        addChild(isw);
        this.isw = isw;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, is.getName(), x - 155, y + 10 - tx.fontHeight / 2, 0xffffff);
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    public void addChild(ClickableWidget child) {
        children.add(child);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
