package me.tolek.gui.widgets.settingsWidgets.rework;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;

public abstract class AbstractSettingWidget extends ContainerWidget {

    private ArrayList<ClickableWidget> children = new ArrayList<>();

    public AbstractSettingWidget(int x, int y, int width, int height, Text text) {
        super(x, y, width, height, text);
    }

    public ArrayList<ClickableWidget> children() { return children; }

}
