package com.runesource.core.network.packet.out;

import com.runesource.core.network.packet.OutboundPacket;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.StreamBuffer;

import io.netty.buffer.ByteBuf;

/**
 * Sends the request to the client for Gameframe components (Tabs)
 * 
 * @author Dennis
 *
 */
public final class GameFrameElementPacket implements OutboundPacket {

	/**
	 * The Tab id
	 */
	private int index;

	/**
	 * The interface to send to {@link #index}
	 */
	private int id;

	/**
	 * Constructions the menu interface. Example: Displays Quest Tab, and sends
	 * Quest Tab interface.
	 * 
	 * @param index
	 * @param id
	 */
	public GameFrameElementPacket(int index, int id) {
		this.index = index;
		this.id = id;
	}

	@Override
	public ByteBuf dispatch(Player player) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(4);
		out.writeHeader(player.getSecureWrite(), 71);
		out.writeShort(id);
		out.writeByte(index, StreamBuffer.ValueType.A);
		return out.getBuffer();
	}
}