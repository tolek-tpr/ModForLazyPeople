package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class PartyPlayerAcceptS2CPacket implements Packet<ClientPacketListener> {

    public final String player;

    public PartyPlayerAcceptS2CPacket(String player) {
        this.player = player;
    }

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onPlayerAccept(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
