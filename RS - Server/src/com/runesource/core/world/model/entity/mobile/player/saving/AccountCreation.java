package com.runesource.core.world.model.entity.mobile.player.saving;

import java.io.File;

import com.runesource.core.world.model.entity.mobile.player.Player;


/**
* @author -Andreas 27 jan. 2020
* @project Game
* 
*/

public class AccountCreation {

	public static Player loadPlayer(String username) {
		return (Player) GSONParser.load("data/characters/" + username + ".json", Player.class);
	}

	public static void savePlayer(Player player) {
		GSONParser.save(player, "data/characters/" + player.getUsername() + ".json", Player.class);
	}

	public static boolean exists(String username) {
		return new File("data/characters/" + username + ".json").exists();
	}

}
