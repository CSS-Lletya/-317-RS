package com.runesource.core.world.model.entity.mobile.player;

import java.util.Iterator;

import com.runesource.core.network.packet.out.GameFrameElementPacket;
import com.runesource.core.network.packet.out.MessagePacket;
import com.runesource.core.network.packet.out.PlayerUpdatePacket;
import com.runesource.core.network.packet.out.RegionalUpdatePacket;
import com.runesource.core.network.packet.out.RegisterPlayerPacket;
import com.runesource.core.world.Position;
import com.runesource.core.world.World;
import com.runesource.core.world.model.entity.mobile.player.saving.AccountCreation;
import com.runesource.util.Misc;
import com.runesource.util.StreamBuffer;
import com.runesource.util.StreamBuffer.OutBuffer;

public final class PlayerEventHandler implements PlayerEventListener {

	@Override
	public void register(Player player) {
		int responseCode = Misc.LOGIN_RESPONSE_OK;
		for (Player other : World.getSingleton().getPlayers()) {
			if (player == null) {
				continue;
			}
			if (other.getUsername().equals(player.getUsername())) {
				responseCode = Misc.LOGIN_RESPONSE_ACCOUNT_ONLINE;
			}
		}
		int status = 0;
		try {
			AccountCreation.loadPlayer(player.getUsername());/*PlayerSave.load(player);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (status == 2) {
			responseCode = Misc.LOGIN_RESPONSE_INVALID_CREDENTIALS;
		}
		player.dispatch(new RegisterPlayerPacket(responseCode));
		if (responseCode != 2) {
			player.disconnect();
			return;
		}
		player.dispatch(new RegionalUpdatePacket());
		player.setUpdateRequired(true);
		player.setAppearanceUpdateRequired(true);
		player.dispatch(new GameFrameElementPacket(0, 2423));
		player.dispatch(new GameFrameElementPacket(1, 3917));
		player.dispatch(new GameFrameElementPacket(2, 638));
		player.dispatch(new GameFrameElementPacket(3, 3213));
		player.dispatch(new GameFrameElementPacket(4, 1644));
		player.dispatch(new GameFrameElementPacket(5, 5608));
		player.dispatch(new GameFrameElementPacket(6, 1151));
		player.dispatch(new GameFrameElementPacket(8, 5065));
		player.dispatch(new GameFrameElementPacket(9, 5715));
		player.dispatch(new GameFrameElementPacket(10, 2449));
		player.dispatch(new GameFrameElementPacket(11, 4445));
		player.dispatch(new GameFrameElementPacket(12, 147));
		player.dispatch(new GameFrameElementPacket(13, 6299));
		player.dispatch(new MessagePacket("Welcome to Runescape."));
		World.getSingleton().register(player);
	}

	@Override
	public void unregister(Player player) {
		World.getSingleton().unregister(player);
	}

	@Override
	public void update(Player player) {
		player.dispatch(new PlayerUpdatePacket());
	}
	
	@Override
	public void updateMovement(Player player) {
		player.getMovementHandler().process();
	}
	
	@Override
	public void updateMovement(Player player, OutBuffer out) {
		boolean updateRequired = player.isUpdateRequired();
		if (player.needsPlacement()) {
			out.writeBit(true);
			out.writeBits(2, 3);
			out.writeBits(2, player.getPosition().getZ());
			out.writeBit(player.isResetMovementQueue());
			out.writeBit(player.isUpdateRequired());
			out.writeBits(7, player.getPosition().getLocalY(player.getCurrentRegion()));
			out.writeBits(7, player.getPosition().getLocalX(player.getCurrentRegion()));
		} else {
			if (player.getWalkingDirection() != -1) { 
				out.writeBit(true); 
				if (player.getRunningDirection() != -1) { 
					out.writeBits(2, 2);
					out.writeBits(3, player.getWalkingDirection());
					out.writeBits(3, player.getRunningDirection());
					out.writeBit(player.isUpdateRequired());
				} else { 
					out.writeBits(2, 1);
					out.writeBits(3, player.getWalkingDirection());
					out.writeBit(player.isUpdateRequired());
				}
			} else { 
				if (updateRequired) { 
					out.writeBit(true); 
					out.writeBits(2, 0);
				} else { 
					out.writeBit(false);
				}
			}
		}
	}

	@Override
	public void updateState(Player player, OutBuffer block, boolean forceAppearance, boolean noChat) {
		int mask = 0x0;
		if (player.isChatUpdateRequired() && !noChat) {
			mask |= 0x80;
		}
		if (player.isAppearanceUpdateRequired() || forceAppearance) {
			mask |= 0x10;
		}
		if (mask >= 0x100) {
			mask |= 0x40;
			block.writeShort(mask, StreamBuffer.ByteOrder.LITTLE);
		} else {
			block.writeByte(mask);
		}
		if (player.isChatUpdateRequired() && !noChat) {
			updateChat(player, block);
		}
		if (player.isAppearanceUpdateRequired() || forceAppearance) {
			updateAppearance(player, block);
		}
	}

	@Override
	public void updateAppearance(Player player, OutBuffer block) {
		StreamBuffer.OutBuffer properties = StreamBuffer.newOutBuffer(128);
		properties.writeByte(player.getAppearance().toArray()[PlayerAppearance.GENDER]); // Gender
		properties.writeByte(0); // Skull icon
		// Hat.
		//if (e[Misc.EQUIPMENT_SLOT_HEAD] > 1) {
			//block.writeShort(0x200 + e[Misc.EQUIPMENT_SLOT_HEAD]);
		//} else {
			properties.writeByte(0);
		//}

		// Cape.
		//if (e[Misc.EQUIPMENT_SLOT_CAPE] > 1) {
			//block.writeShort(0x200 + e[Misc.EQUIPMENT_SLOT_CAPE]);
		//} else {
			properties.writeByte(0);
		//}

		// Amulet.
		//if (e[Misc.EQUIPMENT_SLOT_AMULET] > 1) {
			//block.writeShort(0x200 + e[Misc.EQUIPMENT_SLOT_AMULET]);
		//} else {
			properties.writeByte(0);
		//}

		// Weapon.
		//if (e[Misc.EQUIPMENT_SLOT_WEAPON] > 1) {
			//block.writeShort(0x200 + e[Misc.EQUIPMENT_SLOT_WEAPON]);
		//} else {
			properties.writeByte(0);
		//}

		// Chest.
		//if (e[Misc.EQUIPMENT_SLOT_CHEST] > 1) {
			//block.writeShort(0x200 + e[Misc.EQUIPMENT_SLOT_CHEST]);
		//} else {
			properties.writeShort(0x100 + player.getAppearance().toArray()[PlayerAppearance.TORSO]);
		//}

		// Shield.
		//if (e[Misc.EQUIPMENT_SLOT_SHIELD] > 1) {
			//block.writeShort(0x200 + e[Misc.EQUIPMENT_SLOT_SHIELD]);
		//} else {
			properties.writeByte(0);
		//}

		// Arms TODO: Check platebody/non-platebody.
		//if (e[Misc.EQUIPMENT_SLOT_CHEST] > 1) {
			//block.writeShort(0x200 + e[Misc.EQUIPMENT_SLOT_CHEST]);
		//} else {
			properties.writeShort(0x100 + player.getAppearance().toArray()[PlayerAppearance.ARMS]);
		//}

		// Legs.
		//if (e[Misc.EQUIPMENT_SLOT_LEGS] > 1) {
			//block.writeShort(0x200 + e[Misc.EQUIPMENT_SLOT_LEGS]);
		//} else {
			properties.writeShort(0x100 + player.getAppearance().toArray()[PlayerAppearance.LEGS]);
		//}

		// Head (with a hat already on).
		//if (Misc.isFullHelm(e[Misc.EQUIPMENT_SLOT_HEAD]) || Misc.isFullMask(Misc.EQUIPMENT_SLOT_HEAD)) {
			//block.writeByte(0);
		//} else {
			properties.writeShort(0x100 + player.getAppearance().toArray()[PlayerAppearance.HEAD]);
		//}

		// Hands.
		//if (e[Misc.EQUIPMENT_SLOT_HANDS] > 1) {
			//block.writeShort(0x200 + e[Misc.EQUIPMENT_SLOT_HANDS]);
		//} else {
			properties.writeShort(0x100 + player.getAppearance().toArray()[PlayerAppearance.HANDS]);
		//}

		// Feet.
		//if (e[Misc.EQUIPMENT_SLOT_FEET] > 1) {
			//block.writeShort(0x200 + e[Misc.EQUIPMENT_SLOT_FEET]);
		//} else {
			properties.writeShort(0x100 + player.getAppearance().toArray()[PlayerAppearance.FEET]);
		//}

		// Beard.
		//if (Misc.isFullHelm(e[Misc.EQUIPMENT_SLOT_HEAD]) || Misc.isFullMask(Misc.EQUIPMENT_SLOT_HEAD)) {
			//block.writeByte(0);
		//} else {
			properties.writeShort(0x100 + player.getAppearance().toArray()[PlayerAppearance.BEARD]);
		//}

		// Player colors
		properties.writeByte(player.getAppearance().toArray()[PlayerAppearance.HAIR_COLOR]);
		properties.writeByte(player.getAppearance().toArray()[PlayerAppearance.TORSO_COLOR]);
		properties.writeByte(player.getAppearance().toArray()[PlayerAppearance.LEG_COLOR]);
		properties.writeByte(player.getAppearance().toArray()[PlayerAppearance.FEET_COLOR]);
		properties.writeByte(player.getAppearance().toArray()[PlayerAppearance.SKIN_COLOR]);

		// Movement animations
		properties.writeShort(0x328); // stand
		properties.writeShort(0x337); // stand turn
		properties.writeShort(0x333); // walk
		properties.writeShort(0x334); // turn 180
		properties.writeShort(0x335); // turn 90 cw
		properties.writeShort(0x336); // turn 90 ccw
		properties.writeShort(0x338); // run

		properties.writeLong(Misc.nameToLong(player.getUsername()));
		properties.writeByte(3); // Combat level.
		properties.writeShort(0); // Total level.

		// Append the block length and the block to the packet.
		block.writeByte(properties.getBuffer().writerIndex(), StreamBuffer.ValueType.C);
		block.writeBytes(properties.getBuffer());
	}

	@Override
	public void updateChat(Player player, OutBuffer block) {
		block.writeShort(((player.getChatColor() & 0xff) << 8) + (player.getChatEffects() & 0xff), StreamBuffer.ByteOrder.LITTLE);
		block.writeByte(player.getStaffRights());
		block.writeByte(player.getChatText().length, StreamBuffer.ValueType.C);
		block.writeBytesReverse(player.getChatText());
	}

	@Override
	public void updateLocal(Player player, OutBuffer out, OutBuffer block) {
		out.writeBits(8, player.getPlayers().size());
		for (Iterator<Player> i = player.getPlayers().iterator(); i.hasNext();) {
			Player other = i.next();
			if (other.getPosition().isViewableFrom(player.getPosition()) && !other.needsPlacement()) {
				player.getEventHandler().updateLocalMovement(other, out);
				if (other.isUpdateRequired()) {
					player.getEventHandler().updateState(other, block, false, false);
				}
			} else {
				out.writeBit(true);
				out.writeBits(2, 3);
				i.remove();
			}
		}
		for (Player other : World.getSingleton().getPlayers()) {
			if (player.getPlayers().size() >= 255) {
				break;
			}
			if (other == null || other == player) {
				continue;
			}
			if (!player.getPlayers().contains(other) && other.getPosition().isViewableFrom(player.getPosition())) {
				player.getPlayers().add(other);
				out.writeBits(11, other.getIndex());
				out.writeBit(true); 
				out.writeBit(true); 
				Position delta = Misc.delta(player.getPosition(), other.getPosition());
				out.writeBits(5, delta.getY());
				out.writeBits(5, delta.getX());
				player.getEventHandler().updateState(other, block, true, false);
			}
		}
	}

	@Override
	public void updateLocalMovement(Player player, OutBuffer out) {
		boolean updateRequired = player.isUpdateRequired();
		if (player.getWalkingDirection() != -1) {
			out.writeBit(true); 
			if (player.getRunningDirection() != -1) {
				out.writeBits(2, 2);
				out.writeBits(3, player.getWalkingDirection());
				out.writeBits(3, player.getRunningDirection());
				out.writeBit(player.isUpdateRequired());
			} else { 
				out.writeBits(2, 1);
				out.writeBits(3, player.getWalkingDirection());
				out.writeBit(player.isUpdateRequired());
			}
		} else {
			if (updateRequired) { 
				out.writeBit(true);
				out.writeBits(2, 0);
			} else {
				out.writeBit(false);
			}
		}
	}

}
