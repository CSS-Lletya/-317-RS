package com.runesource.core.world.model.entity.mobile;

import com.runesource.core.world.Position;
import com.runesource.core.world.model.entity.Entity;

/**
 * Represents the Moving entity within the World.
 * 
 * @author Dennis
 *
 */
public abstract class MobileEntity extends Entity {

	/**
	 * The Walking direction of the Entity.
	 */
	private transient int walkingDirection = -1;

	/**
	 * The Running direction of the Entity.
	 */
	private transient int runningDirection = -1;

	/**
	 * The Index of the Entity.
	 */
	private transient int index;

	/**
	 * Constructions the Position of the Entity.
	 * 
	 * @param position
	 */
	public MobileEntity(Position position) {
		super(position);
	}

	/**
	 * Gets the Walking direction of the Entity.
	 * 
	 * @return {@link #walkingDirection}
	 */
	public int getWalkingDirection() {
		return walkingDirection;
	}

	/**
	 * Sets the {@link #walkingDirection} of the Entity.
	 * 
	 * @param walkingDirection
	 */
	public void setWalkingDirection(int walkingDirection) {
		this.walkingDirection = walkingDirection;
	}

	/**
	 * Gets the Running direction of the Entity.
	 * 
	 * @return {@link #runningDirection}
	 */
	public int getRunningDirection() {
		return runningDirection;
	}

	/**
	 * Sets the {@link #runningDirection} of the Entity.s
	 * 
	 * @param runningDirection
	 */
	public void setRunningDirection(int runningDirection) {
		this.runningDirection = runningDirection;
	}

	/**
	 * Gets the {@link #index} of an entity.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the {@link #index} of an entity.
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}