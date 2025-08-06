package me.tolek.network.packet.S2C;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

import java.util.ArrayList;

public class StatusReturnListS2CPacket implements Packet<ClientPacketListener> {

    private final ArrayList<String> users;

    public StatusReturnListS2CPacket(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<String> getUsers() { return this.users; }

    @Override
    public void accept(ClientPacketListener listener, Session ignored) {
        listener.onStatusReturnListS2C(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
