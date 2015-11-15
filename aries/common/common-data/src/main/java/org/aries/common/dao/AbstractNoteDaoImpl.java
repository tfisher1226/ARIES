package org.aries.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.aries.Assert;
import org.aries.common.entity.AbstractNoteEntity;
import org.aries.util.ExceptionUtil;


public class AbstractNoteDaoImpl<T extends AbstractNoteEntity> extends DaoImpl<T> implements AbstractNoteDao<T> {

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAllAbstractNotes() {
		Query query = entityManager.createNamedQuery("getAbstractNotes");
		List<T> result = query.getResultList();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAbstractNotesByAuthorId(Long abstractUserId) {
		Assert.notNull(abstractUserId, "AuthorId must be specified");
		Query query = entityManager.createNamedQuery("getAbstractNotesByAuthorId");
		query.setParameter("abstractUserId", abstractUserId);
		List<T> result = query.getResultList();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAbstractNotesByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("getAbstractNotesForPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}

	@Override
	public T getAbstractNoteById(Long id) {
		Assert.notNull(id, "Id must be specified");
		Query query = entityManager.createNamedQuery("getAbstractNoteById");
		query.setParameter("id", id);
		Object object = query.getSingleResult();
		@SuppressWarnings("unchecked")
		T result = (T) object;
		return result;
	}

	@Override
	//SEAM @Transactional
	public Long saveAbstractNote(T abstractNote) {
		//EntityManager em = entityManager;
		//EntityTransaction transaction = em.getTransaction();

		try {
			//transaction.begin();
			abstractNote = merge(abstractNote);
			persist(abstractNote);

		} catch (Throwable e) {
			//transaction.rollback();
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}

		try {
			Long id = abstractNote.getId();
			return id;

		} catch (Throwable e) {
			log.error(e);
			return null;
		}
	}

	@Override
	//SEAM @Transactional
	public void deleteAbstractNote(T abstractNote) {
		try {
			abstractNote = merge(abstractNote);
			delete(abstractNote);

		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}


}
