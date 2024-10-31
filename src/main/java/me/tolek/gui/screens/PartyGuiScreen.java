package me.tolek.gui.screens;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.text.Text;

/**
 * This subclass doesn't need to do anything, just be a distinct
 * class so that anyone making edits or adding buttons can find us
 * with an instanceof check
 */
public class PartyGuiScreen extends CottonClientScreen {
    public PartyGuiScreen(GuiDescription description) {
        super(Text.translatable("mflp.party.screen.title"), description);
    }
}
