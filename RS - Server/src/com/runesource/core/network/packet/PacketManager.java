package com.runesource.core.network.packet;

import com.runesource.core.network.packet.in.ClickButtonPacket;
import com.runesource.core.network.packet.in.MovementPacket;
import com.runesource.core.world.model.entity.mobile.player.Player;

/**
 * Handles the incoming packets.
 * 
 * @author Dennis
 */
public final class PacketManager {

	/**
	 * The amount of packets that exist, some may not be defined.
	 */
	private final InboundPacket[] listeners = new InboundPacket[257];

	/**
	 * Constructs the list of current packets being sent to the Client.
	 */
	public PacketManager() {
		listeners[98] = new MovementPacket();
		listeners[164] = new MovementPacket();
		listeners[248] = new MovementPacket();
		listeners['\u00B9'] = new ClickButtonPacket();
	}

	/**
	 * Executes the Packet specified from the {@link #listeners}
	 * 
	 * @param player
	 * @param packet
	 */
	public void notify(Player player, Packet packet) {
		if (listeners[packet.getOpcode()] != null) {
			listeners[packet.getOpcode()].execute(player, packet);
		}
	}
}