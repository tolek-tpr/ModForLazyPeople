package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class PartyClientInvitedS2CPacket implements Packet<ClientPacketListener> {

    public final String executor;

    public PartyClientInvitedS2CPacket(String executor) {
        this.executor = executor;
    }

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onClientInvited(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
