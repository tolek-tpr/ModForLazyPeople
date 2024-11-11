package me.tolek.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import me.tolek.ModForLazyPeople;
import me.tolek.util.TextureUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class WPartyMember extends WPlainPanel {

    public final WLabel usernameLabel;
    public final WSprite faceSprite;
    public final WButton promoteButton;
    public final WButton demoteButton;

    public WPartyMember(boolean withButtons) {
        faceSprite = new WSprite(Objects.requireNonNull(Identifier.of(ModForLazyPeople.MOD_ID, "textures/gui/sprites/userloading.png")));
        faceSprite.setSize(16, 16);

        promoteButton = new WButton(new TextureIcon(Objects.requireNonNull(Identifier.of(ModForLazyPeople.MOD_ID, "textures/gui/sprites/promote.png"))));
        promoteButton.setSize(19, 19);
        promoteButton.setIconSize(15);

        demoteButton = new WButton(new TextureIcon(Objects.requireNonNull(Identifier.of(ModForLazyPeople.MOD_ID, "textures/gui/sprites/demote.png"))));
        demoteButton.setSize(19, 19);
        demoteButton.setIconSize(15);

        int currOffset = 0;
        int widthLeft = 9;

        this.add(faceSprite, currOffset, 0);
        currOffset += 19;
        widthLeft--;

        if (withButtons) {
            this.add(promoteButton, currOffset, 0);
            currOffset += 22;
            widthLeft--;

            this.add(demoteButton, currOffset, 0);
            currOffset += 22;
            widthLeft--;
        }

        usernameLabel = new WLabel(Text.empty());
        this.add(usernameLabel, currOffset, 5, 18 * widthLeft, 18);

        //this.setSize(6*18, 18);
    }

    public void setFace(String u) {
        Identifier playerFace = TextureUtil.getPlayerFace(u);
        if (playerFace != null)
            faceSprite.setImage(playerFace);
    }

}
