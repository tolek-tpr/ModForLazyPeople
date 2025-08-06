package me.tolek.network.packet.C2S.party;

import jakarta.websocket.Session;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;
import me.tolek.network.ServerPacketListener;

public class PartyClientDeclineC2SPacket implements Packet<ServerPacketListener> {

    public final String username;

    public PartyClientDeclineC2SPacket(String username) {
        this.username = username;
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
