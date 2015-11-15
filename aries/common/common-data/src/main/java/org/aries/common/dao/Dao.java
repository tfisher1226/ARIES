package org.aries.common.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public interface Dao<T> {

	public void initialize(String name);
	
	public EntityManager getEntityManager();
	
	public void setEntityManager(EntityManager entityManager);
	
	public void setEntityClass(Class<T> entityClass);
	
	public Query createQuery(String sql);

	public boolean contains(T object);

	public <PK extends Serializable> T read(Class<T> type, PK id);
	
	public Collection<T> readAll(Class<T> type);

	public void refresh(T object);

	public void persist(T object);

	public T merge(T object);

	public void delete(T object);

	public void clear();

	public void flush();

}
