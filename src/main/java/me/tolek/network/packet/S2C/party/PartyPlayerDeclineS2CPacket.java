package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class PartyPlayerDeclineS2CPacket implements Packet<ClientPacketListener> {

    public final String username;

    public PartyPlayerDeclineS2CPacket(String username) {
        this.username = username;
    }

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onPlayerDecline(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }
}
