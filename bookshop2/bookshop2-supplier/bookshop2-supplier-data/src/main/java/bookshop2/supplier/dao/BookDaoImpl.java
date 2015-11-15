package bookshop2.supplier.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Synchronization;
import javax.transaction.Transaction;

import org.aries.Assert;
import org.aries.common.dao.DaoImpl;
import org.aries.tx.TransactionHelper;
import org.aries.tx.TransactionUtil;
import org.aries.util.ExceptionUtil;

import bookshop2.BookCriteria;
import bookshop2.supplier.entity.AbstractBookEntity;
import bookshop2.supplier.query.BookQuery;

import com.arjuna.ats.arjuna.coordinator.BasicAction;
import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;


@Stateless
@Local(BookDao.class)
public class BookDaoImpl<T extends AbstractBookEntity> extends DaoImpl<T> implements BookDao<T> {
	
	@Override
	public T getBookRecordById(Long id) {
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
	public T getBookRecordByBarCode(Long barCode) {
		Assert.notNull(barCode, "BarCode must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordByBarCode");
		query.setParameter("barCode", barCode);
		try {
			Object object = query.getSingleResult();
			@SuppressWarnings("unchecked") T result = (T) object;
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public T getBookRecordByCriteria(BookCriteria bookCriteria) {
		Assert.notNull(bookCriteria, "Book record criteria must be specified");
		Assert.notNull(entityClass, "EntityClass must be specified");
		BookQuery<T> query = new BookQuery<T>(entityManager);
		query.setEntityClass(entityClass);
		query.integrateCriteria(bookCriteria);
		T result = query.getSingleResult();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAllBookRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getBookRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public List<T> getBookRecordsByCriteria(BookCriteria bookCriteria) {
		Assert.notNull(bookCriteria, "Book record criteria must be specified");
		Assert.notNull(entityClass, "EntityClass must be specified");
		BookQuery<T> query = new BookQuery<T>(entityManager);
		query.setEntityClass(entityClass);
		query.integrateCriteria(bookCriteria);
		List<T> results = query.getResultList();
		return results;
	}
	
	
//	@Override
//	public List<T> getBookRecordsByCriteria(BookCriteria bookCriteria) {
//		Assert.notNull(bookCriteria, "Book criteria must be specified");
//		Iterator<Map<String, Object>> iterator = bookCriteria.getAllFieldSets().iterator();
//		List<T> resultList = new ArrayList<T>();
//		while (iterator.hasNext()) {
//			Map<String, Object> fields = iterator.next();
//			List<T> bookRecords = getBookRecordsByFields(fields);
//			resultList.addAll(bookRecords);
//		}
//		return resultList;
//	}

//	@SuppressWarnings("unchecked")
//	public List<T> getBookRecordsByFields(Map<String, Object> fields) {
//		Assert.notNull(fields, "Field set must exist");
//		Assert.notEmpty(fields, "At least one Field must be specified");
//		StringBuffer queryString = new StringBuffer("select x from ReservedBooks x where");
//		Iterator<String> iterator = fields.keySet().iterator();
//		while (iterator.hasNext()) {
//			String name = iterator.next();
//			queryString.append(" ");
//			queryString.append("x."+name+" = :"+name);
//		}
//		Query query = entityManager.createQuery(queryString.toString());
//		iterator = fields.keySet().iterator();
//		while (iterator.hasNext()) {
//			String name = iterator.next();
//			Object value = fields.get(name);
//			query.setParameter(name, value);
//		}
//		List<T> result = query.getResultList();
//		return result;
//	}
	
	@Override
	public Long addBookRecord(T bookRecord) {
		Assert.notNull(bookRecord, "Book record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Long id = addRecord(bookRecord);
			transactionHelper.close();
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addBookRecords(Collection<T> bookRecords) {
		Assert.notNull(bookRecords, "Book records parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = bookRecords.iterator();
			while (iterator.hasNext()) {
				T bookRecord = iterator.next();
				Long id = addRecord(bookRecord);
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
	
    final class SynchronizationImpl implements Synchronization {
        private String transactionID;

        SynchronizationImpl(String  transactionID) {
            this.transactionID = transactionID;
        }

        public void beforeCompletion() {
        	//EJBClientConfiguration ejbClientConfiguration = EJBClientContext.requireCurrent().getEJBClientConfiguration();
        	//ClusterContext clusterContext = EJBClientContext.requireCurrent().getClusterContext("");
        	
        	try {
        		BasicAction currentAction = ThreadActionData.currentAction();
        		if (currentAction != null) {
        			String transactionId = currentAction.get_uid().toString();
        			System.out.println(">>>>>>>>>>>>>>>>> "+transactionId);
        		}
        		TransactionManagerImple transactionManager = (TransactionManagerImple) TransactionUtil.getTransactionManager();
        		if (transactionManager != null) {
					Transaction transaction = transactionManager.getTransaction();
					System.out.println(transaction);
				}
				String transactionId2 = TransactionUtil.getTransactionId();
				System.out.println(">>>>>>>>>>>>>>>>> "+transactionId2);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }

        public void afterCompletion(int status) {
        	System.out.println(">>>>>>>>>>>>>>>>> "+transactionID+", "+status);
        }
    }
    
	@Override
	public void saveBookRecord(T bookRecord) {
		Assert.notNull(bookRecord, "Book record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			saveRecord(bookRecord);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveBookRecords(Collection<T> bookRecords) {
		Assert.notNull(bookRecords, "BookRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = bookRecords.iterator();
			while (iterator.hasNext()) {
				T bookRecord = iterator.next();
				saveRecord(bookRecord);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllBookRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();

			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T bookRecord = iterator.next();
				removeRecord(bookRecord);
			}
			
			transactionHelper.close();

		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeBookRecord(T bookRecord) {
		Assert.notNull(bookRecord, "Book record parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			removeRecord(bookRecord);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeBookRecords(Collection<T> bookRecords) {
		Assert.notNull(bookRecords, "BookRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = bookRecords.iterator();
			while (iterator.hasNext()) {
				T bookRecord = iterator.next();
				removeRecord(bookRecord);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeBookRecords(BookCriteria bookCriteria) {
		Assert.notNull(bookCriteria, "Book record criteria must be specified");
		Assert.notNull(entityClass, "Entity class must be specified");
		BookQuery<T> query = new BookQuery<T>(entityManager);
		query.setEntityClass(entityClass);
		query.integrateCriteria(bookCriteria);
		query.executeUpdate();
	}
	
//	protected void printTransactionId() {
//		if (transactionSynchronizationRegistry != null) {
//			Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();
//			//int transactionStatus = transactionSynchronizationRegistry.getTransactionStatus();
//			if (transactionKey == null)
//				System.out.println();
//			if (transactionKey != null) {
//				String transactionId = transactionKey.toString();
//				System.out.println("DAO>>>>>>>>>>>>>>>>>> transactionId = "+transactionId);
//			}
//		}
//	}

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
