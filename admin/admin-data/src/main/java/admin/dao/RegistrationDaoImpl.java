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
import admin.entity.RegistrationEntity;
import admin.entity.UserEntity;


@Stateless
@Local(RegistrationDao.class)
public class RegistrationDaoImpl<T extends RegistrationEntity> extends DaoImpl<T> implements RegistrationDao<T> {
	
	@Override
	public T getRegistrationRecordById(Long id) {
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
	public T getRegistrationRecordByUser(User user) {
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
	public List<T> getAllRegistrationRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getRegistrationRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addRegistrationRecord(T registrationRecord) {
		Assert.notNull(registrationRecord, "Registration parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			registrationRecord = entityManager.merge(registrationRecord);
			entityManager.persist(registrationRecord);
			transactionHelper.close();
		
			Long id = registrationRecord.getId();
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addRegistrationRecords(Collection<T> registrationRecords) {
		Assert.notNull(registrationRecords, "RegistrationRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = registrationRecords.iterator();
			while (iterator.hasNext()) {
				T registration = iterator.next();
				registration = entityManager.merge(registration);
				entityManager.persist(registration);
				Long id = registration.getId();
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
	public void saveRegistrationRecord(T registrationRecord) {
		Assert.notNull(registrationRecord, "RegistrationRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			UserEntity user = entityManager.merge(registrationRecord.getUser());
			registrationRecord.setUser(user);
			
			registrationRecord = entityManager.merge(registrationRecord);
			entityManager.persist(registrationRecord);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveRegistrationRecords(Collection<T> registrationRecords) {
		Assert.notNull(registrationRecords, "RegistrationRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = registrationRecords.iterator();
			while (iterator.hasNext()) {
				T registration = iterator.next();
				registration = entityManager.merge(registration);
				entityManager.persist(registration);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllRegistrationRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T registration = iterator.next();
				registration = entityManager.merge(registration);
				entityManager.remove(registration);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeRegistrationRecord(T registrationRecord) {
		Assert.notNull(registrationRecord, "RegistrationRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			registrationRecord = entityManager.merge(registrationRecord);
			entityManager.remove(registrationRecord);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeRegistrationRecords(Collection<T> registrationRecords) {
		Assert.notNull(registrationRecords, "RegistrationRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = registrationRecords.iterator();
			while (iterator.hasNext()) {
				T registration = iterator.next();
				registration = entityManager.merge(registration);
				entityManager.remove(registration);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
