package me.tolek.gui.widgets.settingsWidgets.rework;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;

import java.util.ArrayList;
import java.util.List;

public class SettingContainerWidget extends ListEntry {

    private List<Selectable> selectableChildren = new ArrayList<>();
    private List<Element> children = new ArrayList<>();
    private AbstractSettingWidget widget;

    public SettingContainerWidget(AbstractSettingWidget widget) {
        this.widget = widget;
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        widget.render(context, mouseX, mouseY, tickDelta);
        widget.children().forEach((w) -> { w.render(context, mouseX, mouseY, tickDelta); });
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        widget.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifier) {
        widget.keyPressed(keyCode, scanCode, modifier);
        return super.keyPressed(keyCode, scanCode, modifier);
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return selectableChildren;
    }

    @Override
    public List<? extends Element> children() {
        return children;
    }
}
