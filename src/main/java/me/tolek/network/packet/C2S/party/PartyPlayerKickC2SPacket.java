package me.tolek.network.packet.C2S.party;

import jakarta.websocket.Session;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;
import me.tolek.network.ServerPacketListener;

public class PartyPlayerKickC2SPacket implements Packet<ServerPacketListener> {

    public final String kicked, executor;

    public PartyPlayerKickC2SPacket(String kicked, String executor) {
        this.kicked = kicked;
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
