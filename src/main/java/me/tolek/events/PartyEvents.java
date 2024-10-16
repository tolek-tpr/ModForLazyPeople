package me.tolek.events;

import me.tolek.ModForLazyPeople;
import me.tolek.event.*;
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

        if (err != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.translatable(err).formatted(Formatting.RED, Formatting.BOLD));
            ModForLazyPeople.LOGGER.warn(err);
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

        if (err != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.translatable(err).formatted(Formatting.RED, Formatting.BOLD));
            ModForLazyPeople.LOGGER.warn(err);
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.literal(playerUsername + " was invited to the party!").formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onClientInvited(byte partyId, String partyOwnerUsername, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (err != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.translatable(err).formatted(Formatting.RED, Formatting.BOLD));
            ModForLazyPeople.LOGGER.warn(err);
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.literal(partyOwnerUsername + " invited you to their party! User /party accept to accept the invite, or /party decline to decline the invite!").formatted(Formatting.ITALIC, Formatting.GRAY));
    }

    @Override
    public void onPlayerLeft(String playerUsername, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (err != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.translatable(err).formatted(Formatting.RED, Formatting.BOLD));
            ModForLazyPeople.LOGGER.warn(err);
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.literal(playerUsername + " has left the party!"));
    }

    @Override
    public void onPlayerRemoved(String playerUsername, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (err != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.translatable(err).formatted(Formatting.RED, Formatting.BOLD));
            ModForLazyPeople.LOGGER.warn(err);
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.literal(playerUsername + " was removed from the party!"));
    }

    @Override
    public void onPlayerJoined(String playerUsername, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (err != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.translatable(err).formatted(Formatting.RED, Formatting.BOLD));
            ModForLazyPeople.LOGGER.warn(err);
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.literal(playerUsername + " has joined the party!"));
    }

    @Override
    public void onPartyInfoReturned(String owner, ArrayList<String> members, String err) {
        assert MinecraftClient.getInstance().player != null;

        if (err != null) {
            MinecraftClient.getInstance().player.sendMessage(Text.translatable(err).formatted(Formatting.RED, Formatting.BOLD));
            ModForLazyPeople.LOGGER.warn(err);
            return;
        }

        MinecraftClient.getInstance().player.sendMessage(Text.literal("PARTY INFORMATION:")); // TODO: Proper message.
    }

}
