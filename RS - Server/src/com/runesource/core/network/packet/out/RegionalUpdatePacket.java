package com.runesource.core.network.packet.out;

import com.runesource.core.network.packet.OutboundPacket;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.StreamBuffer;
import com.runesource.util.StreamBuffer.OutBuffer;

import io.netty.buffer.ByteBuf;

/**
 * Sends the Regions update packet.
 * 
 * @author Dennis
 *
 */
public final class RegionalUpdatePacket implements OutboundPacket {

	@Override
	public ByteBuf dispatch(Player player) {
		OutBuffer out = StreamBuffer.newOutBuffer(5);
		out.writeHeader(player.getSecureWrite(), 73);
		out.writeShort(player.getPosition().getRegionX() + 6, StreamBuffer.ValueType.A);
		out.writeShort(player.getPosition().getRegionY() + 6);
		player.getCurrentRegion().setAs(player.getPosition());
		player.setNeedsPlacement(true);
		return out.getBuffer();
	}
}