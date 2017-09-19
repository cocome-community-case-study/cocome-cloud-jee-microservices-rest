package org.cocome.storesservice.repository;

import java.util.Collection;

public interface Repository<EntityType, KeyType> {
	void create(EntityType entity);
	
	EntityType find(KeyType id);
	
	void update(EntityType entity);
	
	void delete(EntityType entity);
	
	Collection<EntityType> all();
}
