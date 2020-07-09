package com.runesource.core.network.codec;

import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Represents the Handshake decoder for the 
 * Network.
 * @author Dennis
 *
 */
public final class HandshakeDecoder extends ByteToMessageDecoder {

	/**
	 * The default login opcode.
	 */
	private static final int LOGIN_OPCODE = 14;

	/**
	 * Creates an instance of the Logging services
	 */
	private final Logger logger;

	/**
	 * Constructions the Logging service for the Network
	 */
	public HandshakeDecoder() {
		this.logger = Logger.getLogger(getClass().getName());
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		final int opcode = in.readUnsignedByte();
		switch (opcode) {

		case LOGIN_OPCODE:
			in.skipBytes(1);
			ctx.writeAndFlush(ctx.alloc().buffer().writeByte(0).writeLong(0).writeLong(new SecureRandom().nextLong()));
			ctx.pipeline().replace("decoder", "decoder", new LoginDecoder());
			break;

		default:
			logger.info("Unsupported handshake operation.");
			throw new UnsupportedOperationException();
		}
	}
}