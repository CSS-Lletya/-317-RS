package com.runesource.core.world.model.entity.mobile;

import com.runesource.core.world.model.entity.EntityEventListener;

/**
 * Represents the Entity Event listener in the World.
 * 
 * @author Dennis
 *
 * @param <T>
 */
public interface MobileEntityEventListener<T extends MobileEntity> extends EntityEventListener<T> {

	/**
	 * Handles the updating for movement of the Entity.
	 * 
	 * @param entity
	 */
	public void updateMovement(T entity);

	/**
	 * Registers the Entity to the World.
	 * 
	 * @param entity
	 */
	public void register(T entity);

	/**
	 * Removes the Entity from the World.
	 * 
	 * @param entity
	 */
	public void unregister(T entity);

}
