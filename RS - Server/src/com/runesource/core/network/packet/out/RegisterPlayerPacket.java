package com.runesource.core.network.packet.out;

import com.runesource.core.network.packet.OutboundPacket;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.StreamBuffer;
import com.runesource.util.StreamBuffer.OutBuffer;

import io.netty.buffer.ByteBuf;

public final class RegisterPlayerPacket implements OutboundPacket {

	private final int responseCode;
	
	public RegisterPlayerPacket(int responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public ByteBuf dispatch(Player player) {
		OutBuffer out = StreamBuffer.newOutBuffer(3);
		out.writeByte(responseCode);
		out.writeByte(player.getStaffRights());
		out.writeByte(0);
		return out.getBuffer();
	}

}
