package com.runesource.core.world.model.entity;

import com.runesource.core.world.Position;

public abstract class Entity {

	private final Position position;
	
	private int index;
	
	public Entity(Position position) {
		this.position = position;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Position getPosition() {
		return position;
	}
	
}
