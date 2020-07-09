package com.runesource.core.world.model.entity;

/**
 * Represents the Event Listener for an Entity.
 * 
 * @author Dennis
 *
 * @param <T>
 */
public interface EntityEventListener<T extends Entity> {

	/**
	 * Updates the Entity.
	 * 
	 * @param entity
	 */
	public void update(T entity);
}
