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
import org.aries.common.entity.EmailAddressEntity;
import org.aries.tx.TransactionHelper;
import org.aries.util.ExceptionUtil;


@Stateless
@Local(EmailAddressDao.class)
public class EmailAddressDaoImpl<T extends EmailAddressEntity> extends DaoImpl<T> implements EmailAddressDao<T> {
	
	@Override
	public T getEmailAddressRecordById(Long id) {
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
	public List<T> getAllEmailAddressRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getEmailAddressRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addEmailAddressRecord(T emailAddressRecord) {
		Assert.notNull(emailAddressRecord, "EmailAddress record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			T merged = entityManager.merge(emailAddressRecord);
			entityManager.persist(merged);
			transactionHelper.close();
		
			Long id = merged.getId();
			emailAddressRecord.setId(id);
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addEmailAddressRecords(Collection<T> emailAddressRecords) {
		Assert.notNull(emailAddressRecords, "EmailAddress records parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = emailAddressRecords.iterator();
			while (iterator.hasNext()) {
				T emailAddressRecord = iterator.next();
				T merged = entityManager.merge(emailAddressRecord);
				entityManager.persist(merged);
				Long id = merged.getId();
				emailAddressRecord.setId(id);
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
	public void saveEmailAddressRecord(T emailAddressRecord) {
		Assert.notNull(emailAddressRecord, "EmailAddress record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			T merged = entityManager.merge(emailAddressRecord);
			entityManager.persist(merged);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveEmailAddressRecords(Collection<T> emailAddressRecords) {
		Assert.notNull(emailAddressRecords, "EmailAddressRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = emailAddressRecords.iterator();
			while (iterator.hasNext()) {
				T emailAddressRecord = iterator.next();
				T merged = entityManager.merge(emailAddressRecord);
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
	public void removeAllEmailAddressRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T emailAddressRecord = iterator.next();
				T merged = entityManager.merge(emailAddressRecord);
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
	public void removeEmailAddressRecord(T emailAddressRecord) {
		Assert.notNull(emailAddressRecord, "EmailAddress record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			T merged = entityManager.merge(emailAddressRecord);
			entityManager.remove(merged);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeEmailAddressRecords(Collection<T> emailAddressRecords) {
		Assert.notNull(emailAddressRecords, "EmailAddressRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = emailAddressRecords.iterator();
			while (iterator.hasNext()) {
				T emailAddressRecord = iterator.next();
				T merged = entityManager.merge(emailAddressRecord);
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
