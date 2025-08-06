package me.tolek.network.packet.C2S.party;

import jakarta.websocket.Session;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;
import me.tolek.network.ServerPacketListener;

public class PartyPlayerPromoteC2SPacket implements Packet<ServerPacketListener> {

    public final String promoted, executor;

    public PartyPlayerPromoteC2SPacket(String promoted, String executor) {
        this.promoted = promoted;
        this.executor = executor;
    }

    @Override
    public void accept(ServerPacketListener listener, Session session) {
        listener.onPacket(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.SERVERBOUND;
    }

}
