package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class PartyPlayerDemotedS2CPacket implements Packet<ClientPacketListener> {

    public final String demoted, executor;

    public PartyPlayerDemotedS2CPacket(String demoted, String executor) {
        this.demoted = demoted;
        this.executor = executor;
    }

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onPlayerDemote(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
