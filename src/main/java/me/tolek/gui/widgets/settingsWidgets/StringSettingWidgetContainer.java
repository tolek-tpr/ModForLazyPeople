package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.modules.settings.base.StringSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class StringSettingWidgetContainer extends ContainerWidget {

    private List<ClickableWidget> children = new ArrayList<>();
    private StringSetting ss;
    private TextRenderer tx;
    private int x;
    private int y;
    private MinecraftClient client;
    public StringSettingWidget ssw;

    public StringSettingWidgetContainer(int x, int y, Text text, StringSetting ss, TextRenderer tx, MinecraftClient client) {
        super(x, y, 310, 20, text);
        this.ss = ss;
        this.tx = tx;
        this.x = x;
        this.y = y;
        this.client = client;

        StringSettingWidget ssw = new StringSettingWidget(tx, x + 5, y, ss);
        addChild(ssw);
        this.ssw = ssw;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTextWithShadow(tx, ss.getName(), x - 155, y + 10 - tx.fontHeight / 2, 0xffffff);
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
