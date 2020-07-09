package com.runesource.core.world;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.runesource.core.world.model.entity.mobile.npc.Npc;
import com.runesource.core.world.model.entity.mobile.player.Player;
import com.runesource.util.IndexQueue;

public final class World implements Runnable {

	private static final World singleton = new World();
	
	private final Map<Integer, Player> playerRespitory = new ConcurrentHashMap<>(2000);
	
	private final IndexQueue playerIndices = new IndexQueue(2000);
	
	private final Map<Integer, Npc> npcRespitory = new ConcurrentHashMap<>(8000);
	
	private final IndexQueue npcIndices = new IndexQueue(8000);
	
	private World() { }
	
	public void register(Player player) {
		player.setIndex(playerIndices.nextValue());
		playerRespitory.put(player.getIndex(), player);
	}
	
	public void unregister(Player player) {
		playerRespitory.remove(player);
		playerIndices.openValue(player.getIndex());
	}
	
	public void register(Npc npc) {
		npc.setIndex(npcIndices.nextValue());
		npcRespitory.put(npc.getIndex(), npc);
	}
	
	public void unregister(Npc npc) {
		npcRespitory.remove(npc);
		npcIndices.openValue(npc.getIndex());
	}
	
	public Collection<Player> getPlayers() {
		return playerRespitory.values();
	}
	
	public Collection<Npc> getNpcs() {
		return npcRespitory.values();
	}
	
	public static World getSingleton() {
		return singleton;
	}

	@Override
	public void run() {
		while (true) {
			try {
				playerRespitory.values().forEach(player -> {
					player.getEventHandler().updateMovement(player);
					player.getEventHandler().update(player);
					player.reset();
				});
				Thread.sleep(600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}