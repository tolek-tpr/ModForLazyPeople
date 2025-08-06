package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class PartyPlayerPromotedS2CPacket implements Packet<ClientPacketListener> {

    public final String promoted, executor;

    public PartyPlayerPromotedS2CPacket(String promoted, String executor) {
        this.promoted = promoted;
        this.executor = executor;
    }

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onPlayerPromoted(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
