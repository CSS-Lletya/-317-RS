package com.runesource.core.world.model.entity.mobile.player.saving;

import java.io.File;

import com.runesource.core.world.model.entity.mobile.player.Player;

/**
 * Represents the Account Creation & existence of a Player.
 * 
 * @author Dennis
 *
 */
public class AccountCreation {

	/**
	 * Loads the Player information made by non transient objects used in the Player
	 * class.
	 * 
	 * @param username
	 * @return
	 */
	public static Player loadPlayer(String username) {
		return (Player) GSONParser.load("data/characters/" + username + ".json", Player.class);
	}

	/**
	 * Saves the Player information made by non transient objects used in the Player
	 * class.
	 * 
	 * @param player
	 */
	public static void savePlayer(Player player) {
		GSONParser.save(player, "data/characters/" + player.getUsername() + ".json", Player.class);
	}

	/**
	 * Checks whether or not a Player name exists in the Characters folder located
	 * in the root Data folder.
	 * 
	 * @param username
	 * @return
	 */
	public static boolean exists(String username) {
		return new File("data/characters/" + username + ".json").exists();
	}
}