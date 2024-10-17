package me.tolek.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class WListPanelWithModifiableData<D, W extends WWidget> extends WListPanel<D, W> {
    public WListPanelWithModifiableData(List<D> data, Supplier<W> supplier, BiConsumer<D, W> configurator) {
        super(data, supplier, configurator);
    }

    public void setData(List<D> data) {
        this.data = data;
    }
}
