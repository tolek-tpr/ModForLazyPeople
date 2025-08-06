package me.tolek.network.packet.C2S;

import jakarta.websocket.Session;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;
import me.tolek.network.ServerPacketListener;

public class StatusPlayerJoinC2SPacket implements Packet<ServerPacketListener> {

    private final String username;

    public StatusPlayerJoinC2SPacket(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public void accept(ServerPacketListener listener, Session ignored) {
        listener.onPacket(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.SERVERBOUND;
    }

}
