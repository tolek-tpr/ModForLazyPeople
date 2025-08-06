package me.tolek.network.packet.C2S.party;

import jakarta.websocket.Session;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;
import me.tolek.network.ServerPacketListener;

public class PartyPlayerInviteC2SPacket implements Packet<ServerPacketListener> {

    public final String invited, executor;

    public PartyPlayerInviteC2SPacket(String invited, String executor) {
        this.invited = invited;
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
