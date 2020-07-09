package com.runesource.core.world.model.entity.mobile;

import com.runesource.core.world.Position;
import com.runesource.core.world.model.entity.Entity;

public abstract class MobileEntity extends Entity {

	private int walkingDirection = -1;
	
	private int runningDirection = -1;
	
	private int index;
	
	public MobileEntity(Position position) {
		super(position);
	}

	public int getWalkingDirection() {
		return walkingDirection;
	}

	public void setWalkingDirection(int walkingDirection) {
		this.walkingDirection = walkingDirection;
	}

	public int getRunningDirection() {
		return runningDirection;
	}

	public void setRunningDirection(int runningDirection) {
		this.runningDirection = runningDirection;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
