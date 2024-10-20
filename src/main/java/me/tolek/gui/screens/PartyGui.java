package me.tolek.gui.screens;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import me.tolek.ModForLazyPeople;
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

        if (!Party.isInParty()) {
            WLabel youAreNotInAPartyLabel = new WLabel(Text.translatable("mflp.party.screen.notInParty", Party.getOwner()));
            root.add(youAreNotInAPartyLabel, 0, 1, 8, 1);
            root.validate(this);
            return;
        }

        WLabel ownerLabel = new WLabel(Text.translatable("mflp.party.screen.ownership", Party.getOwner()));
        root.add(ownerLabel, 0, 1);

        WLabel moderatorsLabel = new WLabel(Text.translatable("mflp.party.moderators"));
        root.add(moderatorsLabel, 0, 2);

        WListPanel<String, WPartyMember> moderatorsList = new WListPanel<>(Party.getModerators(), () -> new WPartyMember(Party.isOwner()), Party.isOwner() ? OWNER_MODERATOR_CONFIGURATOR : MEMBER_MEMBER_CONFIGURATOR);
        moderatorsList.setListItemHeight(15);
        root.add(moderatorsList, 0, 3, 8, 6);

        WLabel membersLabel = new WLabel(Text.translatable("mflp.party.members"));
        root.add(membersLabel, 8, 2);

        WListPanel<String, WPartyMember> membersList = new WListPanel<>(Party.getMembers(), () -> new WPartyMember(Party.isOwner()), Party.isOwner() ? OWNER_MEMBER_CONFIGURATOR : MEMBER_MEMBER_CONFIGURATOR);
        membersList.setListItemHeight(15);
        root.add(membersList, 8, 3, 8, 6);

        WTextField inviteTextField = new WTextField();
        inviteTextField.setSuggestion(Text.translatable("mflp.party.screen.invitePlayer"));
        inviteTextField.setEditable(Party.isModeratorOrOwner());
        root.add(inviteTextField, 0, 10, 6, 1);

        WButton inviteButton = new WButton(Text.translatable("mflp.party.screen.invite"));
        inviteButton.setOnClick(() -> PartyNetworkHandler.invitePlayer(inviteTextField.getText()));
        inviteButton.setEnabled(Party.isModeratorOrOwner());
        root.add(inviteButton, 7, 10, 3, 1);

        final int closeButtonWidth = 6;
        WButton closeButton = new WButton(ScreenTexts.DONE);
        closeButton.setOnClick(() -> MinecraftClient.getInstance().setScreen(null));
        root.add(closeButton, root.getWidth() / 2 / pixelsPerCell - (closeButtonWidth / 2), 12, closeButtonWidth, 1);

        WButton leaveButton = new WButton(new TextureIcon(Objects.requireNonNull(Identifier.of(ModForLazyPeople.MOD_ID, "textures/gui/sprites/leave.png"))));
        leaveButton.setOnClick(PartyNetworkHandler::leaveParty);
        //leaveButton.addTooltip(new TooltipBuilder().add(Text.translatable("mflp.party.screen.leave.tooltip")));
        root.add(leaveButton, root.getWidth() / pixelsPerCell, 1, 1, 1);

        root.validate(this);
    }
}
