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
import org.aries.common.entity.PropertyEntity;
import org.aries.tx.TransactionHelper;
import org.aries.util.ExceptionUtil;


@Stateless
@Local(PropertyDao.class)
public class PropertyDaoImpl<T extends PropertyEntity> extends DaoImpl<T> implements PropertyDao<T> {
	
	@Override
	public T getPropertyRecordById(Long id) {
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
	public List<T> getAllPropertyRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getPropertyRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addPropertyRecord(T propertyRecord) {
		Assert.notNull(propertyRecord, "Property record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			T merged = entityManager.merge(propertyRecord);
			entityManager.persist(merged);
			transactionHelper.close();
		
			Long id = merged.getId();
			propertyRecord.setId(id);
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addPropertyRecords(Collection<T> propertyRecords) {
		Assert.notNull(propertyRecords, "Property records parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = propertyRecords.iterator();
			while (iterator.hasNext()) {
				T propertyRecord = iterator.next();
				T merged = entityManager.merge(propertyRecord);
				entityManager.persist(merged);
				Long id = merged.getId();
				propertyRecord.setId(id);
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
	public void savePropertyRecord(T propertyRecord) {
		Assert.notNull(propertyRecord, "Property record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			T merged = entityManager.merge(propertyRecord);
			entityManager.persist(merged);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void savePropertyRecords(Collection<T> propertyRecords) {
		Assert.notNull(propertyRecords, "PropertyRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = propertyRecords.iterator();
			while (iterator.hasNext()) {
				T propertyRecord = iterator.next();
				T merged = entityManager.merge(propertyRecord);
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
	public void removeAllPropertyRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T propertyRecord = iterator.next();
				T merged = entityManager.merge(propertyRecord);
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
	public void removePropertyRecord(T propertyRecord) {
		Assert.notNull(propertyRecord, "Property record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			T merged = entityManager.merge(propertyRecord);
			entityManager.remove(merged);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removePropertyRecords(Collection<T> propertyRecords) {
		Assert.notNull(propertyRecords, "PropertyRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = propertyRecords.iterator();
			while (iterator.hasNext()) {
				T propertyRecord = iterator.next();
				T merged = entityManager.merge(propertyRecord);
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
