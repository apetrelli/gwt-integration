package com.github.apetrelli.gwtintegration.jpa;

import javax.persistence.PostRemove;

/**
 * Listener to mark {@link Deletable} entities as deleted when remove happens.
 *
 */
public class DeleteEntityListener {

	/**
	 * Marks an entity as deleted after a remove.
	 * 
	 * @param target The object to mark.
	 */
	@PostRemove
	public void markAsDeleted(Object target) {
		if (target instanceof Deletable) {
			((Deletable) target).markAsDeleted();
		}
	}
}
