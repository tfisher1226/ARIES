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
import org.aries.common.entity.AttachmentEntity;
import org.aries.tx.TransactionHelper;
import org.aries.util.ExceptionUtil;


@Stateless
@Local(AttachmentDao.class)
public class AttachmentDaoImpl<T extends AttachmentEntity> extends DaoImpl<T> implements AttachmentDao<T> {
	
	@Override
	public T getAttachmentRecordById(Long id) {
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
	public List<T> getAllAttachmentRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAttachmentRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addAttachmentRecord(T attachmentRecord) {
		Assert.notNull(attachmentRecord, "Attachment record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			T merged = entityManager.merge(attachmentRecord);
			entityManager.persist(merged);
			transactionHelper.close();
		
			Long id = merged.getId();
			attachmentRecord.setId(id);
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addAttachmentRecords(Collection<T> attachmentRecords) {
		Assert.notNull(attachmentRecords, "Attachment records parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = attachmentRecords.iterator();
			while (iterator.hasNext()) {
				T attachmentRecord = iterator.next();
				T merged = entityManager.merge(attachmentRecord);
				entityManager.persist(merged);
				Long id = merged.getId();
				attachmentRecord.setId(id);
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
	public void saveAttachmentRecord(T attachmentRecord) {
		Assert.notNull(attachmentRecord, "Attachment record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			T merged = entityManager.merge(attachmentRecord);
			entityManager.persist(merged);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveAttachmentRecords(Collection<T> attachmentRecords) {
		Assert.notNull(attachmentRecords, "AttachmentRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = attachmentRecords.iterator();
			while (iterator.hasNext()) {
				T attachmentRecord = iterator.next();
				T merged = entityManager.merge(attachmentRecord);
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
	public void removeAllAttachmentRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T attachmentRecord = iterator.next();
				T merged = entityManager.merge(attachmentRecord);
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
	public void removeAttachmentRecord(T attachmentRecord) {
		Assert.notNull(attachmentRecord, "Attachment record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			T merged = entityManager.merge(attachmentRecord);
			entityManager.remove(merged);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAttachmentRecords(Collection<T> attachmentRecords) {
		Assert.notNull(attachmentRecords, "AttachmentRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = attachmentRecords.iterator();
			while (iterator.hasNext()) {
				T attachmentRecord = iterator.next();
				T merged = entityManager.merge(attachmentRecord);
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
