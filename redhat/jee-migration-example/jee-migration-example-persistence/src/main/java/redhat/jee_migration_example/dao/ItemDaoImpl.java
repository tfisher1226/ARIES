package redhat.jee_migration_example.dao;

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

import redhat.jee_migration_example.entity.AbstractItemEntity;


@Stateless
@Local(ItemDao.class)
public class ItemDaoImpl<T extends AbstractItemEntity> extends DaoImpl<T> implements ItemDao<T> {
	
	@Override
	public T getItemRecordById(Long id) {
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
	public T getItemRecordByKey(String key) {
		Assert.notNull(key, "Key must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordByKey");
		query.setParameter("itemKey", key);
		try {
			Object object = query.getSingleResult();
			@SuppressWarnings("unchecked") T result = (T) object;
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public T getItemRecordByName(String name) {
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
	public List<T> getAllItemRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getItemRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addItemRecord(T itemRecord) {
		Assert.notNull(itemRecord, "Item record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			T merged = entityManager.merge(itemRecord);
			entityManager.persist(merged);
			transactionHelper.close();
		
			Long id = merged.getId();
			itemRecord.setId(id);
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addItemRecords(Collection<T> itemRecords) {
		Assert.notNull(itemRecords, "Item records parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = itemRecords.iterator();
			while (iterator.hasNext()) {
				T itemRecord = iterator.next();
				T merged = entityManager.merge(itemRecord);
				entityManager.persist(merged);
				Long id = merged.getId();
				itemRecord.setId(id);
				idList.add(id);
				
//				T persisted = getItemRecordById(id);
//				if (persisted != null) {
//					id = persisted.getId();
//					itemRecord.setId(id);
//					idList.add(id);
//				}
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
	public void saveItemRecord(T itemRecord) {
		Assert.notNull(itemRecord, "Item record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			T merged = entityManager.merge(itemRecord);
			entityManager.persist(merged);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveItemRecords(Collection<T> itemRecords) {
		Assert.notNull(itemRecords, "ItemRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = itemRecords.iterator();
			while (iterator.hasNext()) {
				T itemRecord = iterator.next();
				T merged = entityManager.merge(itemRecord);
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
	public void removeAllItemRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T itemRecord = iterator.next();
				T merged = entityManager.merge(itemRecord);
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
	public void removeItemRecord(T itemRecord) {
		Assert.notNull(itemRecord, "Item record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			T merged = entityManager.merge(itemRecord);
			entityManager.remove(merged);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeItemRecords(Collection<T> itemRecords) {
		Assert.notNull(itemRecords, "ItemRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = itemRecords.iterator();
			while (iterator.hasNext()) {
				T itemRecord = iterator.next();
				T merged = entityManager.merge(itemRecord);
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
	public void removeItemRecordByKey(String key) {
		Assert.notNull(key, "Record Key parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			T record = getItemRecordByKey(key);
			if (record != null) {
				T merged = entityManager.merge(record);
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
