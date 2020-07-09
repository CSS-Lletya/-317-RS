package com.runesource.core.network.packet;

import com.runesource.core.world.model.entity.mobile.player.Player;

import io.netty.buffer.ByteBuf;

public interface OutboundPacket {

	public ByteBuf dispatch(final Player player);
}
