package me.tolek.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import me.tolek.ModForLazyPeople;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class WPartyMember extends WPlainPanel {

    public final WLabel usernameLabel;
    public final WButton demotePromoteButton;

    public WPartyMember(boolean withDemotionOrPromotionButton) {
        demotePromoteButton = new WButton(new TextureIcon(Objects.requireNonNull(Identifier.of(ModForLazyPeople.MOD_ID, "textures/gui/sprites/promote.png"))));
        demotePromoteButton.setSize(19, 19);
        demotePromoteButton.setIconSize(15);

        if (withDemotionOrPromotionButton) {
            this.add(demotePromoteButton, 0, 0);
        }

        usernameLabel = new WLabel(Text.empty());
        this.add(usernameLabel, withDemotionOrPromotionButton ? 22 : 0, 5, withDemotionOrPromotionButton ? 18*5 : 18*6, 18);

        //this.setSize(6*18, 18);
    }

}
