package me.tolek.gui.screens;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import me.tolek.gui.widgets.WListPanelWithModifiableData;
import me.tolek.gui.widgets.WPartyMember;
import me.tolek.modules.party.Party;
import net.minecraft.text.Text;

import java.util.function.BiConsumer;

public class PartyGui extends LightweightGuiDescription {

    private static WListPanelWithModifiableData<String, WPartyMember> moderatorsList;
    private static WListPanelWithModifiableData<String, WPartyMember> membersList;

    public PartyGui() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(256, 256);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel partyLabel = new WLabel(Text.translatable("mflp.party.screen.ownership", Party.getOwner()));
        root.add(partyLabel, 0, 1);

        BiConsumer<String, WPartyMember> configurator = (String s, WPartyMember partyMember) -> {
            partyMember.usernameLabel.setText(Text.literal(s));
        };

        WLabel moderatorsLabel = new WLabel(Text.translatable("mflp.party.moderators"));
        root.add(moderatorsLabel, 0, 2);

        moderatorsList = new WListPanelWithModifiableData<>(Party.getModerators(), WPartyMember::new, configurator);
        moderatorsList.setListItemHeight(1);
        root.add(moderatorsList, 0, 3, 5, 5);

        WLabel membersLabel = new WLabel(Text.translatable("mflp.party.members"));
        root.add(membersLabel, 8, 2);

        membersList = new WListPanelWithModifiableData<>(Party.getMembers(), WPartyMember::new, configurator);
        membersList.setListItemHeight(1);
        root.add(membersList, 8, 3, 5, 5);

        root.validate(this);
    }

    public static void notifyPartyChanged() {
        membersList.setData(Party.getMembers());
        moderatorsList.setData(Party.getModerators());
    }
}
