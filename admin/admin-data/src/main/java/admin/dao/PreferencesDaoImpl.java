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

import admin.User;
import admin.entity.PreferencesEntity;


@Stateless
@Local(PreferencesDao.class)
public class PreferencesDaoImpl<T extends PreferencesEntity> extends DaoImpl<T> implements PreferencesDao<T> {
	
	@Override
	public T getPreferencesRecordById(Long id) {
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
	public T getPreferencesRecordByUser(User user) {
		Assert.notNull(user, "User must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordByUser");
		query.setParameter("user", user);
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
	public List<T> getAllPreferencesRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getPreferencesRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addPreferencesRecord(T preferencesRecord) {
		Assert.notNull(preferencesRecord, "Preferences parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			preferencesRecord = entityManager.merge(preferencesRecord);
			entityManager.persist(preferencesRecord);
			transactionHelper.close();
		
			Long id = preferencesRecord.getId();
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addPreferencesRecords(Collection<T> preferencesRecords) {
		Assert.notNull(preferencesRecords, "PreferencesRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = preferencesRecords.iterator();
			while (iterator.hasNext()) {
				T preferences = iterator.next();
				preferences = entityManager.merge(preferences);
				entityManager.persist(preferences);
				Long id = preferences.getId();
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
	public void savePreferencesRecord(T preferencesRecord) {
		Assert.notNull(preferencesRecord, "PreferencesRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			preferencesRecord = entityManager.merge(preferencesRecord);
			entityManager.persist(preferencesRecord);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void savePreferencesRecords(Collection<T> preferencesRecords) {
		Assert.notNull(preferencesRecords, "PreferencesRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = preferencesRecords.iterator();
			while (iterator.hasNext()) {
				T preferences = iterator.next();
				preferences = entityManager.merge(preferences);
				entityManager.persist(preferences);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllPreferencesRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T preferences = iterator.next();
				preferences = entityManager.merge(preferences);
				entityManager.remove(preferences);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removePreferencesRecord(T preferencesRecord) {
		Assert.notNull(preferencesRecord, "PreferencesRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			preferencesRecord = entityManager.merge(preferencesRecord);
			entityManager.remove(preferencesRecord);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removePreferencesRecords(Collection<T> preferencesRecords) {
		Assert.notNull(preferencesRecords, "PreferencesRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = preferencesRecords.iterator();
			while (iterator.hasNext()) {
				T preferences = iterator.next();
				preferences = entityManager.merge(preferences);
				entityManager.remove(preferences);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
