package com.runesource.core.world;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.runesource.core.world.model.entity.mobile.npc.Npc;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.IndexQueue;

/**
 * Represents the Server's World functionality and handles the entities within
 * it, etc..
 * 
 * @author Dennis
 *
 */
public final class World implements Runnable {

	/**
	 * Creates an instance of the World.
	 */
	private static final World singleton = new World();

	/**
	 * The Limit of the Player repository allowed in a World Instance.
	 */
	private final Map<Integer, Player> playerRespitory = new ConcurrentHashMap<>(2000);

	/**
	 * The Players Queue limit.
	 */
	private final IndexQueue playerIndices = new IndexQueue(2000);

	/**
	 * The Limit of the NPC repository allowed in a World Instance.
	 */
	private transient final Map<Integer, Npc> npcRespitory = new ConcurrentHashMap<>(8000);

	/**
	 * The NPC Queue limit.
	 */
	private final IndexQueue npcIndices = new IndexQueue(4000);

	/**
	 * Constructions a new World instance.
	 */
	private World() {
	}

	/**
	 * Registers the Player to the World instance.
	 * 
	 * @param player
	 */
	public void register(Player player) {
		player.setIndex(playerIndices.nextValue());
		playerRespitory.put(player.getIndex(), player);
	}

	/**
	 * Removes the Player from the World instance.
	 * 
	 * @param player
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void unregister(Player player) {
		playerRespitory.remove(player);
		playerIndices.openValue(player.getIndex());
	}

	/**
	 * Registers the NPC to the World instance.
	 * 
	 * @param npc
	 */
	public void register(Npc npc) {
		npc.setIndex(npcIndices.nextValue());
		npcRespitory.put(npc.getIndex(), npc);
	}

	/**
	 * Removes the NPC from the World instance.
	 * 
	 * @param npc
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void unregister(Npc npc) {
		npcRespitory.remove(npc);
		npcIndices.openValue(npc.getIndex());
	}

	/**
	 * Gets the list of players currently in the World instance.
	 * 
	 * @return {@link #playerRespitory}
	 */
	public Collection<Player> getPlayers() {
		return playerRespitory.values();
	}

	/**
	 * Gets the list of NPC's current in the World instance.
	 * 
	 * @return {@link #npcRespitory}
	 */
	public Collection<Npc> getNpcs() {
		return npcRespitory.values();
	}

	/**
	 * Gets the Worlds instance.
	 * 
	 * @return
	 */
	public static World getSingleton() {
		return singleton;
	}

	/**
	 * Runs the World instance's events.
	 */
	@Override
	public void run() {
		while (true) {
			long start = System.currentTimeMillis();
			try {
				playerRespitory.values().forEach($it -> $it.getEventHandler().updateMovement($it));
				playerRespitory.values().forEach($it -> $it.getEventHandler().update($it));
				playerRespitory.values().forEach($it -> $it.reset());
				long elapsed = System.currentTimeMillis() - start;
				long sleepTime = 600 - elapsed;
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}