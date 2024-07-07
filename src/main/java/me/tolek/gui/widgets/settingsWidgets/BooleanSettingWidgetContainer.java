package me.tolek.gui.widgets.settingsWidgets;

import me.tolek.modules.settings.base.BooleanSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.text.Text;

import java.util.List;

public class BooleanSettingWidgetContainer extends AbstractSettingWidget {

    public BooleanSettingWidgetContainer(int x, int y, Text settingName, BooleanSetting bs, TextRenderer tx,
                                         MinecraftClient client) {

    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return null;
    }

    @Override
    public List<? extends Element> children() {
        return null;
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {

    }

}
