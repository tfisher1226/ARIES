package org.aries.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.aries.Assert;
import org.aries.common.entity.AbstractEventEntity;
import org.aries.util.ExceptionUtil;


public class AbstractEventDaoImpl<T extends AbstractEventEntity> extends DaoImpl<T> implements AbstractEventDao<T> {

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAllAbstractEvents() {
		Query query = entityManager.createNamedQuery("getAbstractEvents");
		List<T> result = query.getResultList();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAbstractEventsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("getAbstractEventsForPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}

	@Override
	public T getAbstractEventById(Long id) {
		Assert.notNull(id, "Id must be specified");
		Query query = entityManager.createNamedQuery("getAbstractEventById");
		query.setParameter("id", id);
		Object object = query.getSingleResult();
		@SuppressWarnings("unchecked")
		T result = (T) object;
		return result;
	}

	@Override
	//SEAM @Transactional
	public Long saveAbstractEvent(T abstractEvent) {
		//EntityManager em = entityManager;
		//EntityTransaction transaction = em.getTransaction();

		try {
			//transaction.begin();
			abstractEvent = merge(abstractEvent);
			persist(abstractEvent);

		} catch (Throwable e) {
			//transaction.rollback();
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}

		try {
			Long id = abstractEvent.getId();
			return id;

		} catch (Throwable e) {
			log.error(e);
			return null;
		}
	}

	@Override
	//SEAM @Transactional
	public void deleteAbstractEvent(T abstractEvent) {
		try {
			abstractEvent = merge(abstractEvent);
			delete(abstractEvent);

		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}


}
