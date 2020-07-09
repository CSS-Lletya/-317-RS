package com.runesource.core.network.packet;

import com.runesource.core.world.model.entity.mobile.player.Player;

import io.netty.buffer.ByteBuf;

/**
 * Handles sending the packet being given by the server.
 * 
 * @author Dennis
 *
 */
public interface OutboundPacket {

	/**
	 * Sends the server packet request to the client.
	 * 
	 * @param player
	 * @param packet
	 */
	public ByteBuf dispatch(final Player player);
}