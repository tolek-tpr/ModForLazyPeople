package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

import java.util.ArrayList;

public class PartyChangeS2CPacket implements Packet<ClientPacketListener> {

    public final String owner;
    public final ArrayList<String> mods, players;

    public PartyChangeS2CPacket(String owner, ArrayList<String> mods, ArrayList<String> players) {
        this.owner = owner;
        this.mods = mods;
        this.players = players;
    }

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onPartyChange(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

}
