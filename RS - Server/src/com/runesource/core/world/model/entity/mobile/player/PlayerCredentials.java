package com.runesource.core.world.model.entity.mobile.player;

import com.runesource.core.network.security.SecureCipher;

import io.netty.channel.Channel;

/**
 * Represents the Player's credentials for connecting to the Game Servers
 * Network.
 * 
 * @author Dennis
 *
 */
public final class PlayerCredentials {

	/**
	 * Creates an instance of the Channel.
	 */
	private final Channel channel;

	/**
	 * The Username of a Player.
	 */
	private final String username;

	/**
	 * The Password of a Player.
	 */
	private final String password;

	/**
	 * Creates an instance of the Dechiper class.
	 */
	private final SecureCipher dechiper;

	/**
	 * Creates an instance of the Encripher class.
	 */
	private final SecureCipher encipher;

	/**
	 * Constructs the new Player.
	 * 
	 * @param channel
	 * @param username
	 * @param password
	 * @param dechiper
	 * @param encipher
	 */
	public PlayerCredentials(Channel channel, String username, String password, SecureCipher dechiper,
			SecureCipher encipher) {
		this.channel = channel;
		this.username = username;
		this.password = password;
		this.dechiper = dechiper;
		this.encipher = encipher;
	}

	/**
	 * Gets the {@link #channel}.
	 * 
	 * @return
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * Gets the {@link #username} of the Player.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the {@link #password} of the Player.
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the {@link #dechiper}.
	 * 
	 * @return
	 */
	public SecureCipher getDechiper() {
		return dechiper;
	}

	/**
	 * Gets the {@link #encipher}.
	 * 
	 * @return
	 */
	public SecureCipher getEncipher() {
		return encipher;
	}
}