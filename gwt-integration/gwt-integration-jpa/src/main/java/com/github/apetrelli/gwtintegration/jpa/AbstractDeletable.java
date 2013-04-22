package com.github.apetrelli.gwtintegration.jpa;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Base class for delete-checking entities.
 *
 */
@MappedSuperclass
public abstract class AbstractDeletable implements Deletable {

	@Transient
	private boolean deleted = false;
	
	@Override
	public void markAsDeleted() {
		deleted = true;
	}
	
	@Override
	public boolean isDeleted() {
		return deleted;
	}
}
