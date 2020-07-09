package com.runesource.core.world.model.entity.mobile.player;

import com.runesource.core.world.model.entity.mobile.MobileEntityEventListener;
import com.runesource.util.StreamBuffer.OutBuffer;

/**
 * Handles the Players Event handling.
 * 
 * @author Dennis
 *
 */
public interface PlayerEventListener extends MobileEntityEventListener<Player> {

	/**
	 * Updates the current state of the Player.
	 * 
	 * @param player
	 * @param block
	 * @param forceAppearance
	 * @param noChat
	 */
	public void updateState(Player player, OutBuffer block, boolean forceAppearance, boolean noChat);

	/**
	 * Updates teh Appearance of the Player.
	 * 
	 * @param player
	 * @param block
	 */
	public void updateAppearance(Player player, OutBuffer block);

	/**
	 * Updates the Chat for the Player.
	 * 
	 * @param player
	 * @param block
	 */
	public void updateChat(Player player, OutBuffer block);

	/**
	 * Updates the Local Player.
	 * 
	 * @param player
	 * @param out
	 * @param block
	 */
	public void updateLocal(Player player, OutBuffer out, OutBuffer block);

	/**
	 * Updates the Movement of the Player.
	 * 
	 * @param player
	 * @param out
	 */
	public void updateMovement(Player player, OutBuffer out);

	/**
	 * Updates the Local Movement of the Player.
	 * 
	 * @param player
	 * @param out
	 */
	public void updateLocalMovement(Player player, OutBuffer out);
}