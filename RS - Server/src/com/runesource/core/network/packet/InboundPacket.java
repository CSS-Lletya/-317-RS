package com.runesource.core.network.packet;

import com.runesource.core.world.model.entity.mobile.player.Player;

/**
 * Handles the incoming packing being received by the client.
 * 
 * @author Dennis
 *
 */
public interface InboundPacket {

	/**
	 * Executes the Server Packet request received from the Client.
	 * 
	 * @param player
	 * @param packet
	 */
	public void execute(Player player, Packet packet);
}