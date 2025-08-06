package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class PartyPlayerInvitedS2CPacket implements Packet<ClientPacketListener> {

    public final String invited, executor;

    public PartyPlayerInvitedS2CPacket(String invited, String executor) {
        this.invited = invited;
        this.executor = executor;
    }

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onPlayerInvited(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
