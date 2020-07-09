package com.runesource.core.network.packet.out;

import com.runesource.core.network.packet.OutboundPacket;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.StreamBuffer;

import io.netty.buffer.ByteBuf;

public final class PlayerUpdatePacket implements OutboundPacket {

	@Override
	public ByteBuf dispatch(Player player) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(2048);
		StreamBuffer.OutBuffer block = StreamBuffer.newOutBuffer(1024);
		out.writeVariableShortPacketHeader(player.getSecureWrite(), 81);
		out.setAccessType(StreamBuffer.AccessType.BIT_ACCESS);
		player.getEventHandler().updateMovement(player, out);
		if (player.isUpdateRequired()) {
			player.getEventHandler().updateState(player, block, false, true);
		}
		player.getEventHandler().updateLocal(player, out, block);
		if (block.getBuffer().writerIndex() > 0) {
			out.writeBits(11, 2047);
			out.setAccessType(StreamBuffer.AccessType.BYTE_ACCESS);
			out.writeBytes(block.getBuffer());
		} else {
			out.setAccessType(StreamBuffer.AccessType.BYTE_ACCESS);
		}
		out.finishVariableShortPacketHeader();
		return out.getBuffer();
	}

}
