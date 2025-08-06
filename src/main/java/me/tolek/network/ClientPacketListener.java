package me.tolek.network;

import me.tolek.network.depracated.IconHandler;
import me.tolek.network.depracated.PartyHandler;
import me.tolek.network.packet.S2C.StatusPlayerJoinedS2CPacket;
import me.tolek.network.packet.S2C.StatusPlayerLeftS2CPacket;
import me.tolek.network.packet.S2C.StatusReturnListS2CPacket;
import me.tolek.network.packet.S2C.party.*;

public class ClientPacketListener implements PacketListener {

    // region Status Packet Handling
    public void onStatusPlayerJoinedS2C(StatusPlayerJoinedS2CPacket packet) {
        IconHandler.getInstance().mflpUsers.add(packet.getUsername());
    }

    public void onStatusPlayerLeftS2C(StatusPlayerLeftS2CPacket packet) {
        IconHandler.getInstance().mflpUsers.remove(packet.getUsername());
    }

    public void onStatusReturnListS2C(StatusReturnListS2CPacket packet) {
        IconHandler.getInstance().mflpUsers = packet.getUsers();
    }
    // endregion

    // region Party Packet Handling
    public void onPartyError(PartyErrorS2CPacket packet) {
        PartyHandler.handleError(packet.code);
    }

    public void onPlayerAccept(PartyPlayerAcceptS2CPacket packet) {
        PartyHandler.responsePlayerAccepted(packet.player);
    }

    public void onPlayerChat(PartyPlayerChatS2CPacket packet) {
        PartyHandler.responsePlayerChat(packet.username, packet.message);
    }

    public void onPlayerDecline(PartyPlayerDeclineS2CPacket packet) {
        PartyHandler.responsePlayerDeclined(packet.username);
    }

    public void onPlayerLeave(PartyPlayerLeftS2CPacket packet) {
        PartyHandler.responsePlayerLeft(packet.username);
    }

    public void onPlayerDemote(PartyPlayerDemotedS2CPacket packet) {
        PartyHandler.responsePlayerDemoted(packet.demoted);
    }

    public void onPlayerInvited(PartyPlayerInvitedS2CPacket packet) {
        PartyHandler.responsePlayerInvited(packet.invited);
    }

    public void onClientInvited(PartyClientInvitedS2CPacket packet) {
        PartyHandler.responseClientInvited(packet.executor);
    }

    public void onClientKicked(PartyClientKickedS2CPacket packet) {
        PartyHandler.responseClientKicked();
    }

    public void onPlayerKicked(PartyPlayerKickedS2CPacket packet) {
        PartyHandler.responsePlayerKicked(packet.kicked);
    }

    public void onPlayerPromoted(PartyPlayerPromotedS2CPacket packet) {
        PartyHandler.responsePlayerPromoted(packet.promoted, packet.executor);
    }

    public void onPartyChange(PartyChangeS2CPacket packet) {
        PartyHandler.partyChanged(packet);
    }
    // endregion

}
