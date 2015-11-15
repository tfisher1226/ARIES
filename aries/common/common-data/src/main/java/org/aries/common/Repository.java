package org.aries.common;

import java.io.Serializable;


public interface Repository {

	public <T> boolean contains(T object);

	public <T> void create(T object);

	public <T, PK extends Serializable> T read(Class<T> type, PK id);
	
	//public <T> List<T> readAll(Class<T> type);

	public <T> T update(T object);

	public <T> void refresh(T object);

	public <T> void delete(T object);

	public void flush();

}
