package com.runesource.core.network.packet.out;

import com.runesource.core.network.packet.OutboundPacket;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.StreamBuffer;
import com.runesource.util.StreamBuffer.OutBuffer;

import io.netty.buffer.ByteBuf;

public final class MessagePacket implements OutboundPacket {

	private final String message;
	
	public MessagePacket(String message) {
		this.message = message;
	}

	@Override
	public ByteBuf dispatch(Player player) {
		OutBuffer out = StreamBuffer.newOutBuffer(message.length() + 3);
		out.writeVariablePacketHeader(player.getSecureWrite(), 253);
		out.writeString(message);
		out.finishVariablePacketHeader();
		return out.getBuffer();
	}

}
