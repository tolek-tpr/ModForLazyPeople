package me.tolek.network.packet.S2C;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class StatusPlayerJoinedS2CPacket implements Packet<ClientPacketListener> {

    private final String username;

    public StatusPlayerJoinedS2CPacket(String username) {
        this.username = username;
    }

    public String getUsername() { return this.username; }

    @Override
    public void accept(ClientPacketListener listener, Session ignored) {
        listener.onStatusPlayerJoinedS2C(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
