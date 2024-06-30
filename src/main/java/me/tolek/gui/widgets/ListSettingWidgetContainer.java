package me.tolek.gui.widgets;

import me.tolek.modules.settings.base.ListSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ListSettingWidgetContainer extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();
    private ListSetting ls;
    private TextRenderer tx;
    private int x;
    private int y;
    private MinecraftClient client;
    public ListSettingWidget lsw;

    public ListSettingWidgetContainer(int x, int y, Text text, ListSetting ls, TextRenderer tx, MinecraftClient client) {
        super(x, y, 310, 20, text);
        this.ls = ls;
        this.tx = tx;
        this.x = x;
        this.y = y;
        this.client = client;

        Text buttonText = Text.literal(String.valueOf(ls.getState()));
        ListSettingWidget lsw = new ListSettingWidget(x + 5, y, buttonText, ls, client);
        addChild(lsw);
        this.lsw = lsw;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, ls.getName(), x - 155, y + 10 - tx.fontHeight / 2, 0xffffff);
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
