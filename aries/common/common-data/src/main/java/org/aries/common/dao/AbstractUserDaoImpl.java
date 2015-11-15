package org.aries.common.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.aries.Assert;
import org.aries.common.EmailAddress;
import org.aries.common.entity.AbstractUserEntity;
import org.aries.util.ExceptionUtil;


public class AbstractUserDaoImpl<T extends AbstractUserEntity> extends DaoImpl<T> implements AbstractUserDao<T> {

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAllAbstractUsers() {
		Query query = entityManager.createNamedQuery("getAbstractUsers");
		List<T> result = query.getResultList();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAbstractUsersByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("getAbstractUsersForPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}

	@Override
	public T getAbstractUserById(Long id) {
		Assert.notNull(id, "Id must be specified");
		Query query = entityManager.createNamedQuery("getAbstractUserById");
		query.setParameter("id", id);
		Object object = query.getSingleResult();
		@SuppressWarnings("unchecked")
		T result = (T) object;
		return result;
	}

	@Override
	public T getAbstractUserByUserId(String userId) {
		Assert.notNull(userId, "UserId must be specified");
		Query query = entityManager.createNamedQuery("getAbstractUserByUserId");
		query.setParameter("userId", userId);
		try {
			Object object = query.getSingleResult();
			@SuppressWarnings("unchecked")
			T result = (T) object;
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public T getAbstractUserByEmailAddressId(EmailAddress emailAddress) {
		Assert.notNull(emailAddress, "EmailAddress must be specified");
		Query query = entityManager.createNamedQuery("getAbstractUserByEmailAddress");
		query.setParameter("emailAddress", emailAddress);
		try {
			Object object = query.getSingleResult();
			@SuppressWarnings("unchecked")
			T result = (T) object;
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	//SEAM @Transactional
	public Long saveAbstractUser(T abstractUser) {
		//EntityManager em = entityManager;
		//EntityTransaction transaction = em.getTransaction();

		try {
			//transaction.begin();
			abstractUser = merge(abstractUser);
			persist(abstractUser);

		} catch (Throwable e) {
			//transaction.rollback();
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}

		try {
			Long id = abstractUser.getId();
			return id;

		} catch (Throwable e) {
			log.error(e);
			return null;
		}
	}

	@Override
	//SEAM @Transactional
	public void deleteAbstractUser(T abstractUser) {
		try {
			abstractUser = merge(abstractUser);
			delete(abstractUser);

		} catch (Throwable e) {
			log.error(e);
			throw ExceptionUtil.rewrap(e);
		}
	}


}
