package me.tolek.gui.screens;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import me.tolek.ModForLazyPeople;
import me.tolek.gui.widgets.WListPanelWithModifiableData;
import me.tolek.gui.widgets.WPartyMember;
import me.tolek.modules.party.Party;
import me.tolek.network.PartyNetworkHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;
import java.util.function.BiConsumer;

public class PartyGui extends LightweightGuiDescription {

    private static WListPanelWithModifiableData<String, WPartyMember> moderatorsList;
    private static WListPanelWithModifiableData<String, WPartyMember> membersList;
    private static WLabel ownerLabel;
    private static WTextField inviteTextField;
    private static WButton inviteButton;

    private static final BiConsumer<String, WPartyMember> MEMBER_MEMBER_CONFIGURATOR = (String s, WPartyMember partyMember) -> {
        partyMember.usernameLabel.setText(Text.literal(s));
    };

    private static final BiConsumer<String, WPartyMember> OWNER_MEMBER_CONFIGURATOR = (String s, WPartyMember partyMember) -> {
        partyMember.usernameLabel.setText(Text.literal(s));
        partyMember.demotePromoteButton.setOnClick(() -> PartyNetworkHandler.promotePlayer(s));
        partyMember.demotePromoteButton.setIcon(new TextureIcon(Objects.requireNonNull(Identifier.of(ModForLazyPeople.MOD_ID, "textures/gui/sprites/promote.png"))));
    };

    private static final BiConsumer<String, WPartyMember> OWNER_MODERATOR_CONFIGURATOR = (String s, WPartyMember partyMember) -> {
        partyMember.usernameLabel.setText(Text.literal(s));
        partyMember.demotePromoteButton.setOnClick(() -> PartyNetworkHandler.demotePlayer(s));
        partyMember.demotePromoteButton.setIcon(new TextureIcon(Objects.requireNonNull(Identifier.of(ModForLazyPeople.MOD_ID, "textures/gui/sprites/demote.png"))));
    };

    public PartyGui() {
        final int pixelsPerCell = 18;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setInsets(Insets.ROOT_PANEL);

        ownerLabel = new WLabel(Text.translatable("mflp.party.screen.ownership", Party.getOwner()));
        root.add(ownerLabel, 0, 1);

        WLabel moderatorsLabel = new WLabel(Text.translatable("mflp.party.moderators"));
        root.add(moderatorsLabel, 0, 2);

        moderatorsList = new WListPanelWithModifiableData<>(Party.getModerators(), () -> new WPartyMember(Party.isOwner()), Party.isOwner() ? OWNER_MODERATOR_CONFIGURATOR : MEMBER_MEMBER_CONFIGURATOR);
        moderatorsList.setListItemHeight(15);
        root.add(moderatorsList, 0, 3, 8, 6);

        WLabel membersLabel = new WLabel(Text.translatable("mflp.party.members"));
        root.add(membersLabel, 8, 2);

        membersList = new WListPanelWithModifiableData<>(Party.getMembers(), () -> new WPartyMember(Party.isOwner()), Party.isOwner() ? OWNER_MEMBER_CONFIGURATOR : MEMBER_MEMBER_CONFIGURATOR);
        membersList.setListItemHeight(15);
        root.add(membersList, 8, 3, 8, 6);

        inviteTextField = new WTextField();
        inviteTextField.setSuggestion(Text.translatable("mflp.party.screen.invitePlayer"));
        root.add(inviteTextField, 0, 10, 6, 1);

        inviteButton = new WButton(Text.translatable("mflp.party.screen.invite"));
        inviteButton.setOnClick(() -> PartyNetworkHandler.invitePlayer(inviteTextField.getText()));
        root.add(inviteButton, 7, 10, 3, 1);

        final int closeButtonWidth = 6;
        WButton closeButton = new WButton(ScreenTexts.DONE);
        closeButton.setOnClick(() -> MinecraftClient.getInstance().setScreen(null));
        root.add(closeButton, root.getWidth() / 2 / pixelsPerCell - (closeButtonWidth / 2), 12, closeButtonWidth, 1);

        notifyPartyChanged();

        root.validate(this);
    }

    public static void notifyPartyChanged() {
        if (membersList == null)
            return;

        membersList.setData(Party.getMembers());
        moderatorsList.setData(Party.getModerators());
        ownerLabel.setText(Text.translatable("mflp.party.screen.ownership", Party.getOwner()));

        moderatorsList.setConfigurator(Party.isOwner() ? OWNER_MODERATOR_CONFIGURATOR : MEMBER_MEMBER_CONFIGURATOR);
        membersList.setConfigurator(Party.isOwner() ? OWNER_MEMBER_CONFIGURATOR : MEMBER_MEMBER_CONFIGURATOR);

        inviteButton.setEnabled(Party.isModeratorOrOwner());
        inviteTextField.setEditable(Party.isModeratorOrOwner());
    }
}
