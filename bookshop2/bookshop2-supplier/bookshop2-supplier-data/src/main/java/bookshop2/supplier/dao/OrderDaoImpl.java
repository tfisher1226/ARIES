package bookshop2.supplier.dao;

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

import bookshop2.OrderCriteria;
import bookshop2.supplier.entity.AbstractOrderEntity;
import bookshop2.supplier.query.OrderQuery;


@Stateless
@Local(OrderDao.class)
public class OrderDaoImpl<T extends AbstractOrderEntity> extends DaoImpl<T> implements OrderDao<T> {
	
	@Override
	public T getOrderRecordById(Long id) {
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
	public T getOrderRecordByTrackingNumber(String trackingNumber) {
		Assert.notNull(trackingNumber, "TrackingNumber must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordByTrackingNumber");
		query.setParameter("trackingNumber", trackingNumber);
		try {
			Object object = query.getSingleResult();
			@SuppressWarnings("unchecked") T result = (T) object;
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public T getOrderRecordByCriteria(OrderCriteria orderCriteria) {
		Assert.notNull(orderCriteria, "Order record criteria must be specified");
		Assert.notNull(entityClass, "EntityClass must be specified");
		OrderQuery<T> query = new OrderQuery<T>(entityManager);
		query.setEntityClass(entityClass);
		query.integrateCriteria(orderCriteria);
		T result = query.getSingleResult();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAllOrderRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getOrderRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public List<T> getOrderRecordsByCriteria(OrderCriteria orderCriteria) {
		Assert.notNull(orderCriteria, "Order record criteria must be specified");
		Assert.notNull(entityClass, "EntityClass must be specified");
		OrderQuery<T> query = new OrderQuery<T>(entityManager);
		query.setEntityClass(entityClass);
		query.integrateCriteria(orderCriteria);
		List<T> results = query.getResultList();
		return results;
	}
	
	@Override
	public Long addOrderRecord(T orderRecord) {
		Assert.notNull(orderRecord, "Order record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Long id = addRecord(orderRecord);
			transactionHelper.close();
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addOrderRecords(Collection<T> orderRecords) {
		Assert.notNull(orderRecords, "Order records parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = orderRecords.iterator();
			while (iterator.hasNext()) {
				T orderRecord = iterator.next();
				Long id = addRecord(orderRecord);
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
	public void saveOrderRecord(T orderRecord) {
		Assert.notNull(orderRecord, "Order record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			saveRecord(orderRecord);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveOrderRecords(Collection<T> orderRecords) {
		Assert.notNull(orderRecords, "OrderRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = orderRecords.iterator();
			while (iterator.hasNext()) {
				T orderRecord = iterator.next();
				saveRecord(orderRecord);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllOrderRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T orderRecord = iterator.next();
				removeRecord(orderRecord);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeOrderRecord(T orderRecord) {
		Assert.notNull(orderRecord, "Order record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			removeRecord(orderRecord);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeOrderRecords(Collection<T> orderRecords) {
		Assert.notNull(orderRecords, "OrderRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = orderRecords.iterator();
			while (iterator.hasNext()) {
				T orderRecord = iterator.next();
				Assert.notNull(orderRecord.getId(), "Id value must exist");
				removeRecord(orderRecord);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeOrderRecords(OrderCriteria orderCriteria) {
		Assert.notNull(orderCriteria, "Order record criteria must be specified");
		Assert.notNull(entityClass, "Entity class must be specified");
		OrderQuery<T> query = new OrderQuery<T>(entityManager);
		query.setEntityClass(entityClass);
		query.integrateCriteria(orderCriteria);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked") 
	protected Long addRecord(T record) {
		T existing = (T) entityManager.find(record.getClass(), record.getId());
		Long id = null;
		if (existing != null) {
			entityManager.persist(existing);
			id = existing.getId();
		} else {
			T merged = entityManager.merge(record);
			entityManager.persist(merged);
			id = merged.getId();
		}
		record.setId(id);
		return id;
	}

	@SuppressWarnings("unchecked") 
	protected void saveRecord(T record) {
		T existing = (T) entityManager.find(record.getClass(), record.getId());
		if (existing != null) {
			entityManager.persist(existing);
		} else {
			T merged = entityManager.merge(record);
			entityManager.persist(merged);
		}
	}

	@SuppressWarnings("unchecked") 
	protected void removeRecord(T record) {
		T existing = (T) entityManager.find(record.getClass(), record.getId());
		if (existing != null) {
			entityManager.remove(existing);
		} else {
			T merged = entityManager.merge(record);
			entityManager.remove(merged);
		}
	}

}
