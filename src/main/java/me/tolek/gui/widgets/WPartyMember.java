package me.tolek.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.text.Text;

public class WPartyMember extends WPlainPanel {

    public final WLabel usernameLabel;

    public WPartyMember() {
        usernameLabel = new WLabel(Text.literal("LOADING..."));
        this.add(usernameLabel, 0, 0, 6, 1);

        this.setSize(6, 1);
    }

}
