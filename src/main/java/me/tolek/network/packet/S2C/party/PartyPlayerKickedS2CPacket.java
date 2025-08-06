package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class PartyPlayerKickedS2CPacket implements Packet<ClientPacketListener> {

    public final String kicked;

    public PartyPlayerKickedS2CPacket(String kicked) {
        this.kicked = kicked;
    }

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onPlayerKicked(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
