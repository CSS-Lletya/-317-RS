package com.runesource.core.network.packet;

import com.runesource.core.world.model.entity.mobile.player.Player;

public interface InboundPacket {

	public void execute(Player player, Packet packet);
}