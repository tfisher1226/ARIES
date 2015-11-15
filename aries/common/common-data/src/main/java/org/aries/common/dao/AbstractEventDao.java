package org.aries.common.dao;

import java.util.List;

import org.aries.common.dao.Dao;
import org.aries.common.entity.AbstractEventEntity;


public interface AbstractEventDao<T extends AbstractEventEntity> extends Dao<T> {

	public List<T> getAllAbstractEvents();

	public List<T> getAbstractEventsByPage(int pageIndex, int pageSize);

	public T getAbstractEventById(Long id);

	public Long saveAbstractEvent(T abstractEvent);

	public void deleteAbstractEvent(T abstractEvent);

}
