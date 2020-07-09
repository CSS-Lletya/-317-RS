package com.runesource.core.network.packet.out;

import com.runesource.core.network.packet.OutboundPacket;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.StreamBuffer;
import com.runesource.util.StreamBuffer.OutBuffer;

import io.netty.buffer.ByteBuf;

/**
 * Registers the Player to the game server. The response code identifies whether
 * or not they can access parts of the game fully, such as login, etc..
 * 
 * @author Dennis
 *
 */
public final class RegisterPlayerPacket implements OutboundPacket {

	/**
	 * The response code given, meaning what the Server is telling the Client about
	 * any possible issues (or none at all).
	 */
	private final int responseCode;

	/**
	 * Constructions the response code to send to the Client.
	 * 
	 * @param responseCode
	 */
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