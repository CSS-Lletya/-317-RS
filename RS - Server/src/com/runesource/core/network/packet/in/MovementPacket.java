package com.runesource.core.network.packet.in;

import com.runesource.core.network.packet.Packet;
import com.runesource.core.network.packet.InboundPacket;
import com.runesource.core.world.Position;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.StreamBuffer;
import com.runesource.util.StreamBuffer.InBuffer;

public final class MovementPacket implements InboundPacket {

	@Override
	public void execute(Player player, Packet packet) {
		InBuffer in = StreamBuffer.newInBuffer(packet.getPayload());
		switch (packet.getOpcode()) {
		case 248: // Movement.
		case 164: // ^
		case 98: // ^
			int length = packet.getPayload().readableBytes();
			if (packet.getOpcode() == 248) {
				length -= 14;
			}
			int steps = (length - 5) / 2;
			int[][] path = new int[steps][2];
			int firstStepX = in.readShort(StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
			for (int i = 0; i < steps; i++) {
				path[i][0] = in.readByte();
				path[i][1] = in.readByte();
			}
			int firstStepY = in.readShort(StreamBuffer.ByteOrder.LITTLE);

			player.getMovementHandler().reset();
			player.getMovementHandler().setRunPath(in.readByte(StreamBuffer.ValueType.C) == 1);
			player.getMovementHandler().addToPath(new Position(firstStepX, firstStepY));
			for (int i = 0; i < steps; i++) {
				path[i][0] += firstStepX;
				path[i][1] += firstStepY;
				player.getMovementHandler().addToPath(new Position(path[i][0], path[i][1]));
			}
			player.getMovementHandler().finish();
			break;
		}
	}

}
