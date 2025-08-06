package me.tolek.network.packet.S2C.party;

import jakarta.websocket.Session;
import me.tolek.network.ClientPacketListener;
import me.tolek.network.Packet;
import me.tolek.network.PacketType;

public class PartyErrorS2CPacket implements Packet<ClientPacketListener> {

    public final ErrorCode code;
    public String player = null;

    public PartyErrorS2CPacket(ErrorCode code) {
        this.code = code;
    }
    public PartyErrorS2CPacket(ErrorCode code, String player) {
        this.code = code;
        this.player = player;
    }

    @Override
    public void accept(ClientPacketListener listener, Session session) {
        listener.onPartyError(this);
    }

    @Override
    public PacketType getPacketType() {
        return PacketType.CLIENTBOUND;
    }

    public static enum ErrorCode {

        NO_PENDING_INVITE,
        ALREADY_IN_PARTY,
        NOT_IN_PARTY,
        NO_PERMISSION,
        PLAYER_NOT_IN_PARTY,
        UNSUPPORTED_OPERATION,
        ALREADY_INVITED,
        SELF_INVITE,
        INVALID_PLAYER

    }

}
