package com.runesource.core.network.packet;

import io.netty.buffer.ByteBuf;

/**
 * The Packet & Attributes.
 * 
 * @author Dennis
 *
 */
public final class Packet {

	/**
	 * The Packet id.
	 */
	private final int opcode;

	/**
	 * The Length of the packet.
	 */
	private final ByteBuf payload;

	/**
	 * Constructs a new Packet.
	 * 
	 * @param opcode
	 * @param payload
	 */
	public Packet(int opcode, ByteBuf payload) {
		this.opcode = opcode;
		this.payload = payload;
	}

	/**
	 * Gets the Packet id.
	 * 
	 * @return {@link #payload}
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Gets the Length of the packet.
	 * 
	 * @return {@link #opcode}
	 */
	public ByteBuf getPayload() {
		return payload;
	}
}