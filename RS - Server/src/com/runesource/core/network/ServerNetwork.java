package com.runesource.core.network;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.runesource.core.network.codec.HandshakeDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Represents the Server network & configuration.
 * @author Dennis
 *
 */
public final class ServerNetwork implements Runnable {

	/**
	 * The Port required for connection.
	 */
	private final int port;

	/**
	 * Gets the port for connection
	 * 
	 * @return port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Constructs a port to specify
	 * 
	 * @param port
	 */
	public ServerNetwork(int port) {
		this.port = port;
	}

	/**
	 * Initiates the Networking service
	 * 
	 * @throws InterruptedException
	 */
	private void init() throws InterruptedException {
		final ServerBootstrap bootstrap = new ServerBootstrap();
		final EventLoopGroup parentEvent = new NioEventLoopGroup();
		final EventLoopGroup childEvent = new NioEventLoopGroup();
		final SocketAddress address = new InetSocketAddress(getPort());

		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel channel) throws Exception {
				channel.pipeline().addLast("decoder", new HandshakeDecoder());
				channel.pipeline().addLast("handler", new ChannelHandler());
			}

		});
		bootstrap.group(parentEvent, childEvent);
		bootstrap.bind(address).channel().closeFuture().sync();
	}

	/**
	 * The thread service execution
	 */
	@Override
	public void run() {
		try {
			init();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}