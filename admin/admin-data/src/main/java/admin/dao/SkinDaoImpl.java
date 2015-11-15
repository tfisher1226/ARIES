package admin.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.aries.Assert;
import org.aries.common.dao.DaoImpl;
import org.aries.tx.TransactionHelper;
import org.aries.util.ExceptionUtil;

import admin.entity.SkinEntity;


@Stateless
@Local(SkinDao.class)
public class SkinDaoImpl<T extends SkinEntity> extends DaoImpl<T> implements SkinDao<T> {
	
	@Override
	public T getSkinRecordById(Long id) {
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
	public T getSkinRecordByName(String name) {
		Assert.notNull(name, "Name must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordByName");
		query.setParameter("name", name);
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
	public List<T> getAllSkinRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getSkinRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addSkinRecord(T skinRecord) {
		Assert.notNull(skinRecord, "Skin parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			skinRecord = entityManager.merge(skinRecord);
			entityManager.persist(skinRecord);
			transactionHelper.close();
		
			Long id = skinRecord.getId();
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addSkinRecords(Collection<T> skinRecords) {
		Assert.notNull(skinRecords, "SkinRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = skinRecords.iterator();
			while (iterator.hasNext()) {
				T skin = iterator.next();
				skin = entityManager.merge(skin);
				entityManager.persist(skin);
				Long id = skin.getId();
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
	public void saveSkinRecord(T skinRecord) {
		Assert.notNull(skinRecord, "SkinRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			skinRecord = entityManager.merge(skinRecord);
			entityManager.persist(skinRecord);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveSkinRecords(Collection<T> skinRecords) {
		Assert.notNull(skinRecords, "SkinRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = skinRecords.iterator();
			while (iterator.hasNext()) {
				T skin = iterator.next();
				skin = entityManager.merge(skin);
				entityManager.persist(skin);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllSkinRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T skin = iterator.next();
				skin = entityManager.merge(skin);
				entityManager.remove(skin);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeSkinRecord(T skinRecord) {
		Assert.notNull(skinRecord, "SkinRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			skinRecord = entityManager.merge(skinRecord);
			entityManager.remove(skinRecord);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeSkinRecords(Collection<T> skinRecords) {
		Assert.notNull(skinRecords, "SkinRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = skinRecords.iterator();
			while (iterator.hasNext()) {
				T skin = iterator.next();
				skin = entityManager.merge(skin);
				entityManager.remove(skin);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
