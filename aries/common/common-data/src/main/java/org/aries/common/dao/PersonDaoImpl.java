package org.aries.common.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.aries.Assert;
import org.aries.common.entity.PersonEntity;
import org.aries.tx.TransactionHelper;
import org.aries.util.ExceptionUtil;


@Stateless
@Local(PersonDao.class)
public class PersonDaoImpl<T extends PersonEntity> extends DaoImpl<T> implements PersonDao<T> {
	
	@Override
	public T getPersonRecordById(Long id) {
		Assert.notNull(id, "Id must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordById");
		query.setParameter("id", id);
		try {
			Object object = query.getSingleResult();
			@SuppressWarnings("unchecked") T result = (T) object;
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAllPersonRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getPersonRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addPersonRecord(T personRecord) {
		Assert.notNull(personRecord, "Person record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			T merged = entityManager.merge(personRecord);
			entityManager.persist(merged);
			transactionHelper.close();
		
			Long id = merged.getId();
			personRecord.setId(id);
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addPersonRecords(Collection<T> personRecords) {
		Assert.notNull(personRecords, "Person records parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = personRecords.iterator();
			while (iterator.hasNext()) {
				T personRecord = iterator.next();
				T merged = entityManager.merge(personRecord);
				entityManager.persist(merged);
				Long id = merged.getId();
				personRecord.setId(id);
				idList.add(id);
			}
		
			transactionHelper.close();
			return idList;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void savePersonRecord(T personRecord) {
		Assert.notNull(personRecord, "Person record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			T merged = entityManager.merge(personRecord);
			entityManager.persist(merged);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void savePersonRecords(Collection<T> personRecords) {
		Assert.notNull(personRecords, "PersonRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = personRecords.iterator();
			while (iterator.hasNext()) {
				T personRecord = iterator.next();
				T merged = entityManager.merge(personRecord);
				entityManager.persist(merged);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllPersonRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T personRecord = iterator.next();
				T merged = entityManager.merge(personRecord);
				entityManager.remove(merged);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removePersonRecord(T personRecord) {
		Assert.notNull(personRecord, "Person record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			T merged = entityManager.merge(personRecord);
			entityManager.remove(merged);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removePersonRecords(Collection<T> personRecords) {
		Assert.notNull(personRecords, "PersonRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = personRecords.iterator();
			while (iterator.hasNext()) {
				T personRecord = iterator.next();
				T merged = entityManager.merge(personRecord);
				entityManager.remove(merged);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
