package me.tolek.events;

import me.tolek.event.*;
import me.tolek.modules.party.Party;
import me.tolek.util.ToastUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
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
        // Terrifying. Minecraft hates me and this was the only way it works. MutableText.formatting didn't work properly.
        MutableText authorText = Text.literal(author).styled(style -> style.withBold(true));
        MutableText colonText = Text.literal(": ").styled(style -> style.withBold(false)); // Reset the bold for colon
        MutableText messageText = Text.literal(message).styled(style -> style.withItalic(true).withColor(Formatting.GRAY).withBold(false));

        MutableText combinedText = authorText.append(colonText).append(messageText);

        sendFeedback(combinedText, !author.equals(MinecraftClient.getInstance().getSession().getUsername()));
    }

    @Override
    public void onPlayerInvited(String playerUsername) {
        sendFeedback(Text.translatable("mflp.party.playerInvited", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onClientInvited(String partyOwnerUsername) {
        ToastUtil.showToast(Text.translatable("mflp.party.clientInvited.title", partyOwnerUsername), Text.translatable("mflp.party.clientInvited.description", partyOwnerUsername));
        sendFeedback(Text.translatable("mflp.party.clientInvited.title", partyOwnerUsername));
    }

    @Override
    public void onClientRemoved() {
        ToastUtil.showToast(Text.translatable("mflp.party.clientRemoved.title"), Text.empty());
        sendFeedback(Text.translatable("mflp.party.clientRemoved.title"));
    }

    @Override
    public void onPlayerLeft(String playerUsername) {
        sendFeedback(Text.translatable("mflp.party.playerLeft", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerRemoved(String playerUsername) {
        sendFeedback(Text.translatable("mflp.party.playerRemoved", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerJoined(String playerUsername) {
        sendFeedback(Text.translatable("mflp.party.playerJoined", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPartyInviteFailed(String username) {
        ToastUtil.showToast(Text.translatable("mflp.party.failedToInvite.title", username), Text.translatable("mflp.party.failedToInvite.description"));
        sendFeedback(Text.translatable("mflp.party.failedToInvite.title", username));
    }

    @Override
    public void onPartyChanged(String owner, ArrayList<String> moderators, ArrayList<String> members) {
        Party.setOwner(owner);
        Party.setModerators(moderators);
        Party.setMembers(members);
    }

    @Override
    public void onError(String errorTitleTranslationKey, String errorDescriptionTranslationKey) {
        ToastUtil.showToast(Text.translatable(errorTitleTranslationKey), Text.translatable(errorDescriptionTranslationKey));
        sendFeedback(Text.translatable(errorTitleTranslationKey).formatted(Formatting.ITALIC, Formatting.BOLD, Formatting.RED));
    }

    @Override
    public void onPlayerDemoted(String player) {
        sendFeedback(Text.translatable("mflp.party.playerDemoted", player).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerPromoted(String player) {
        sendFeedback(Text.translatable("mflp.party.playerPromoted", player).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerDeclinedInvite(String player) {
        sendFeedback(Text.translatable("mflp.party.playerDeclined", player).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    private static void sendFeedback(Text message, boolean playSound) {
        assert MinecraftClient.getInstance().player != null;

        MinecraftClient.getInstance().player.sendMessage(message);

        if (playSound)
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f));
    }

    private static void sendFeedback(Text message) {
        sendFeedback(message, true);
    }
}
