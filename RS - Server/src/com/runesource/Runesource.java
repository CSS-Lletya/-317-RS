package com.runesource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.runesource.core.network.ServerNetwork;
import com.runesource.core.world.World;

/**
 * Represents the Game Service to execute.
 * 
 * @author Dennis
 *
 */
public final class Runesource {

	/**
	 * Creates an instance of the Execution service.
	 */
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);

	/**
	 * Creates an instance of the Logging utility.
	 */
	private static Logger logger;

	/**
	 * Let the games begin.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logger.log(Level.FINE, "Initializing server communications.");
		EXECUTOR_SERVICE.submit(new ServerNetwork(GameConstants.PORT));
		EXECUTOR_SERVICE.submit(World.getSingleton());
		logger.log(Level.FINE, "Runesource framework is now online.");
	}

}