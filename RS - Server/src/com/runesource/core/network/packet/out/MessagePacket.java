package com.runesource.core.network.packet.out;

import com.runesource.core.network.packet.OutboundPacket;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.StreamBuffer;
import com.runesource.util.StreamBuffer.OutBuffer;

import io.netty.buffer.ByteBuf;

/**
 * Sends the users message to for the client to handle.
 * 
 * @author Dennis
 *
 */
public final class MessagePacket implements OutboundPacket {

	/**
	 * The message in which the user has specified.
	 */
	private final String message;

	/**
	 * Constructs the new message from the user.
	 * 
	 * @param message
	 */
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