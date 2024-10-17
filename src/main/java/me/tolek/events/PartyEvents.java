package me.tolek.events;

import me.tolek.ModForLazyPeople;
import me.tolek.event.*;
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
    public void onMessage(String message, String author, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (handleError(err)) {
            return;
        }

        // Terrifying. Minecraft hates me and this was the only way it works. MutableText.formatting didn't work properly.
        MutableText authorText = Text.literal(author).styled(style -> style.withBold(true));
        MutableText colonText = Text.literal(": ").styled(style -> style.withBold(false)); // Reset the bold for colon
        MutableText messageText = Text.literal(message).styled(style -> style.withItalic(true).withColor(Formatting.GRAY).withBold(false));

        MutableText combinedText = authorText.append(colonText).append(messageText);

        MinecraftClient.getInstance().player.sendMessage(combinedText);
    }

    @Override
    public void onPlayerInvited(String playerUsername, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (handleError(err)) {
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerInvited", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onClientInvited(byte partyId, String partyOwnerUsername) {
        ToastUtil.showToast(Text.translatable("mflp.party.clientInvited.title", partyOwnerUsername), Text.translatable("mflp.party.clientInvited.description", partyOwnerUsername));
        //MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.clientInvited", partyOwnerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onClientRemoved() {
        ToastUtil.showToast(Text.translatable("mflp.party.clientRemoved.title"), Text.empty());
    }

    @Override
    public void onPlayerLeft(String playerUsername, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (handleError(err)) {
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerLeft", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerRemoved(String playerUsername, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (handleError(err)) {
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerRemoved", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerJoined(String playerUsername, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (handleError(err)) {
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.translatable("mflp.party.playerJoined", playerUsername).formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPartyInfoReturned(String owner, ArrayList<String> members, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (handleError(err)) {
            return;
        }

        MutableText newLine = Text.literal("\n");
        MutableText title = Text.translatable("mflp.party.infoTitle").styled(style -> style.withBold(true));
        MutableText ownerText = Text.translatable("mflp.party.infoOwner", owner).styled(style -> style.withBold(false).withItalic(true));
        MutableText membersTitle = Text.translatable("mflp.party.infoMembers").styled(style -> style.withBold(true));
        MutableText membersText = Text.literal("").styled(style -> style.withBold(false).withItalic(true));
        for (String member : members) {
            membersText.append("\n%s".formatted(member));
        }

        MutableText info = title.append(newLine).append(ownerText).append(newLine).append(membersTitle).append(membersText);
        MinecraftClient.getInstance().player.sendMessage(info);
    }

    private static boolean handleError(String err) {
        if (err != null) {
            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.sendMessage(Text.translatable(err).formatted(Formatting.RED, Formatting.BOLD));
            ModForLazyPeople.LOGGER.warn(err);
            return true;
        }

        return false;
    }

}
