package me.tolek.events;

import me.tolek.event.*;
import me.tolek.gui.screens.PartyGui;
import me.tolek.modules.party.Party;
import me.tolek.util.ToastUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class PartyEvents extends EventImpl implements PartyListener {

    @Override
    public void onEnable() {
        EventManager.getInstance().add(PartyListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(PartyListener.class, this);
    }

    @Override
    public void onMessage(String message, String author) {
        assert MinecraftClient.getInstance().player != null;

        // Terrifying. Minecraft hates me and this was the only way it works. MutableText.formatting didn't work properly.
        MutableText authorText = Text.literal(author).styled(style -> style.withBold(true));
        MutableText colonText = Text.literal(": ").styled(style -> style.withBold(false)); // Reset the bold for colon
        MutableText messageText = Text.literal(message).styled(style -> style.withItalic(true).withColor(Formatting.GRAY).withBold(false));

        MutableText combinedText = authorText.append(colonText).append(messageText);

        MinecraftClient.getInstance().player.sendMessage(combinedText);
    }

    @Override
    public void onPlayerInvited(String playerUsername) {
        assert MinecraftClient.getInstance().player != null;

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerInvited", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onClientInvited(String partyOwnerUsername) {
        ToastUtil.showToast(Text.translatable("mflp.party.clientInvited.title", partyOwnerUsername), Text.translatable("mflp.party.clientInvited.description", partyOwnerUsername));
        //MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.clientInvited", partyOwnerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onClientRemoved() {
        ToastUtil.showToast(Text.translatable("mflp.party.clientRemoved.title"), Text.empty());
    }

    @Override
    public void onPlayerLeft(String playerUsername) {
        assert MinecraftClient.getInstance().player != null;

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerLeft", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerRemoved(String playerUsername) {
        assert MinecraftClient.getInstance().player != null;

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerRemoved", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerJoined(String playerUsername) {
        assert MinecraftClient.getInstance().player != null;

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerJoined", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPartyInviteFailed(String username) {
        ToastUtil.showToast(Text.translatable("mflp.party.failedToInvite.title", username), Text.translatable("mflp.party.failedToInvite.description"));
    }

    @Override
    public void onPartyChanged(String owner, ArrayList<String> moderators, ArrayList<String> members) {
        Party.setOwner(owner);
        Party.setModerators(moderators);
        Party.setMembers(members);
        PartyGui.notifyPartyChanged();
    }

    @Override
    public void onError(String errorTitleTranslationKey, String errorDescriptionTranslationKey) {
        ToastUtil.showToast(Text.translatable(errorTitleTranslationKey), Text.translatable(errorDescriptionTranslationKey));
    }

    @Override
    public void onPlayerDemoted(String player) {
        assert MinecraftClient.getInstance().player != null;

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerDemoted", player).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerPromoted(String player) {
        assert MinecraftClient.getInstance().player != null;

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerPromoted", player).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerDeclinedInvite(String player) {
        assert MinecraftClient.getInstance().player != null;

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerDeclined", player).formatted(Formatting.ITALIC, Formatting.GRAY));
    }
}
