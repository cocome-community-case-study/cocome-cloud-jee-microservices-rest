package org.cocome.storesservice.repository;

import java.util.Collection;

/**
 * Repository interface for standard CRUD functionality.
 * 
 * @author Nils Sommer
 *
 * @param <EntityType> Type of the entity
 * @param <KeyType> Type of the entity's primary key
 */
public interface Repository<EntityType, KeyType> {
	/**
	 * Create a new instance by persisting it.
	 * 
	 * @param entity the entity
	 * @return the primary key of the entity
	 */
	KeyType create(EntityType entity);
	
	/**
	 * Find an entity by its primary key.
	 * 
	 * @param id the primary key
	 * @return the entity
	 */
	EntityType find(KeyType id);
	
	/**
	 * Update an entity.
	 * 
	 * @param entity the entity
	 */
	EntityType update(EntityType entity);
	
	/**
	 * Delete an entity.
	 * 
	 * @param entity the entity
	 */
	boolean delete(KeyType key);
	
	/**
	 * Find all entities of a kind.
	 * 
	 * @return the entities
	 */
	Collection<EntityType> all();
}
