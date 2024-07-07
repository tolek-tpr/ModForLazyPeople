package me.tolek.gui.widgets;

import me.tolek.gui.widgets.settingsWidgets.AbstractSettingWidget;
import me.tolek.gui.widgets.settingsWidgets.rework.ListEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;

public class ScrollableListWidget extends ElementListWidget<ListEntry> {

    public ScrollableListWidget(MinecraftClient minecraftClient, int width, int height, int y, int itemHeight) {
        super(minecraftClient, width, height, y, itemHeight);
    }

    public void addChild(ListEntry c) {
        children().add(c);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifier) {
        children().forEach(c -> {
            c.keyPressed(keyCode, scanCode, modifier);
        });
        return super.keyPressed(keyCode, scanCode, modifier);
    }

}
