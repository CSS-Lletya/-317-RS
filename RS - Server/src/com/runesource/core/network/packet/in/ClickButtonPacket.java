package com.runesource.core.network.packet.in;

import com.runesource.core.network.packet.InboundPacket;
import com.runesource.core.network.packet.Packet;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.Misc;
import com.runesource.util.StreamBuffer;
import com.runesource.util.StreamBuffer.InBuffer;

public class ClickButtonPacket implements InboundPacket {

	@Override
	public void execute(Player player, Packet packet) {
		InBuffer incomingPacket = StreamBuffer.newInBuffer(packet.getPayload());
		int buttonId = Misc.hexToInt(incomingPacket.readBytes(2));
		System.out.println(buttonId);
		if (buttonId == 9154) {
			player.disconnect();
		}
	}

}