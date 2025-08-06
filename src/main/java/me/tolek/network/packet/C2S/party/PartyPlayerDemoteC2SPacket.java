package me.tolek.network.packet.C2S.party;

import jakarta.websocket.Session;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;
import me.tolek.network.ServerPacketListener;

public class PartyPlayerDemoteC2SPacket implements Packet<ServerPacketListener> {

    public final String demoted, executor;

    public PartyPlayerDemoteC2SPacket(String demoted, String executor) {
        this.demoted = demoted;
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
