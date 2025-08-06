package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class PartyClientKickedS2CPacket implements Packet<ClientPacketListener> {

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onClientKicked(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
