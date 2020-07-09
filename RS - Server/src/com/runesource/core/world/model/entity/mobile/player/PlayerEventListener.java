package com.runesource.core.world.model.entity.mobile.player;

import com.runesource.core.world.model.entity.mobile.MobileEntityEventListener;
import com.runesource.util.StreamBuffer.OutBuffer;

public interface PlayerEventListener extends MobileEntityEventListener<Player> {

	public void updateState(Player player, OutBuffer block, boolean forceAppearance, boolean noChat);
	
	public void updateAppearance(Player player, OutBuffer block);
	
	public void updateChat(Player player, OutBuffer block);
	
	public void updateLocal(Player player, OutBuffer out, OutBuffer block);
	
	public void updateMovement(Player player, OutBuffer out);
	
	public void updateLocalMovement(Player player, OutBuffer out);
}
