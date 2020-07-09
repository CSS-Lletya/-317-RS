package com.runesource.core.network.codec;

import java.util.List;
import java.util.logging.Logger;

import com.runesource.core.network.security.SecureCipher;
import com.runesource.core.world.model.entity.mobile.player.PlayerCredentials;
import com.runesource.util.StreamBuffer;
import com.runesource.util.StreamBuffer.InBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Represents the Login Decoder.
 * @author Dennis
 *
 */
public final class LoginDecoder extends ByteToMessageDecoder {

	/**
	 * Creates an instance of the Logging services
	 */
	private final Logger logger;

	/**
	 * Constructions the Logging service for the Network
	 */
	public LoginDecoder() {
		this.logger = Logger.getLogger(getClass().getName());
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		final int opcode = in.readUnsignedByte();
		if (opcode != 16 && opcode != 18) {
			ctx.close();
			logger.info("Login - [description = invalid opcode, action = close]");
			return;
		}
		final int size = in.readUnsignedByte();
		if (size > in.readableBytes()) {
			ctx.close();
			logger.info("Login - [description = message size exceeds buffer capacity, action = close]");
			return;
		}
		final int magicId = in.readUnsignedByte();
		if (magicId != 255) {
			ctx.close();
			logger.info("Login - [description = invalid magic identifier, action = close]");
			return;
		}
		final int version = in.readShort();
		if (version != 317) {
			ctx.close();
			logger.info("Login - [description = invalid client version, action = close]");
			return;
		}
		in.skipBytes(39);
		final long clientSeed = in.readLong();
		final long serverSeed = in.readLong();
		final int seed[] = { (int) (clientSeed >> 32), (int) clientSeed, (int) (serverSeed >> 32), (int) serverSeed };
		SecureCipher decipher = new SecureCipher(seed);
		for (int i = 0; i < seed.length; i++) {
			seed[i] += 50;
		}
		SecureCipher encipher = new SecureCipher(seed);
		in.skipBytes(4);
		InBuffer reader = StreamBuffer.newInBuffer(in);
		final String username = reader.readString();
		final String password = reader.readString();
		out.add(new PlayerCredentials(ctx.channel(), username, password, decipher, encipher));
		ctx.pipeline().replace("decoder", "decoder", new PacketDecoder(decipher));
		logger.info("Login - [username = " + username + " address = " + ctx.channel().remoteAddress() + "]");
	}
}