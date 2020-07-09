package com.runesource.core.world.model.entity;

import com.runesource.core.world.Position;

/**
 * The Entity in which exists in the World.
 * 
 * @author Dennis
 *
 */
public abstract class Entity {

	/**
	 * The position of the Entity.
	 */
	private final Position position;

	/**
	 * The index of the Entity.
	 */
	private int index;

	/**
	 * Constructions the Entity.
	 * 
	 * @param position
	 */
	public Entity(Position position) {
		this.position = position;
	}

	/**
	 * Gets the Entity index.
	 * 
	 * @return {@link #index}
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index value of the Entity.
	 * 
	 * @param index
	 * @return {@link #index}
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Gets the position of the Entity.
	 * 
	 * @return {@link #position}
	 */
	public Position getPosition() {
		return position;
	}
}