package com.github.apetrelli.gwtintegration.deletable;

/**
 * Indicates an object that can be deleted.
 *
 */
public interface Deletable {

    /**
     * Returns the deleted status.
     *
     * @return <code>true</code> if it has been deleted.
     */
    boolean isDeleted();

    /**
     * Marks this entity as deleted.
     */
    void markAsDeleted();
}
