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
import org.aries.common.PersonName;
import org.aries.common.dao.DaoImpl;
import org.aries.tx.TransactionHelper;
import org.aries.util.ExceptionUtil;

import admin.UserCriteria;
import admin.entity.UserEntity;
import admin.query.UserQuery;


@Stateless
@Local(UserDao.class)
public class UserDaoImpl<T extends UserEntity> extends DaoImpl<T> implements UserDao<T> {
	
	@Override
	public T getUserRecordById(Long id) {
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
	public T getUserRecordByUserName(String userName) {
		Assert.notNull(userName, "UserName must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordByUserName");
		query.setParameter("userName", userName);
		try {
			Object object = query.getSingleResult();
			@SuppressWarnings("unchecked") T result = (T) object;
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public T getUserRecordByCriteria(UserCriteria userCriteria) {
		Assert.notNull(userCriteria, "User record criteria must be specified");
		Assert.notNull(entityClass, "EntityClass must be specified");
		UserQuery<T> query = new UserQuery<T>(entityManager);
		query.setEntityClass(entityClass);
		query.integrateCriteria(userCriteria);
		T result = query.getSingleResult();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAllUserRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
//	@Override
//	@SuppressWarnings("unchecked")
//	public List<T> getUserRecordsByName(PersonName name) {
//		Assert.notNull(name, "Name must be specified");
//		Query query = entityManager.createNamedQuery("get"+name+"RecordsByName");
//		query.setParameter("name", name);
//		List<T> result = query.getResultList();
//		return result;
//	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getUserRecordsByPersonName(PersonName personName) {
		Assert.notNull(personName, "PersonName must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPersonName");
		query.setParameter("personName", personName);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getUserRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public List<T> getUserRecordsByCriteria(UserCriteria userCriteria) {
		Assert.notNull(userCriteria, "User record criteria must be specified");
		Assert.notNull(entityClass, "EntityClass must be specified");
		UserQuery<T> query = new UserQuery<T>(entityManager);
		query.setEntityClass(entityClass);
		query.integrateCriteria(userCriteria);
		List<T> results = query.getResultList();
		return results;
	}
	
	@Override
	public Long addUserRecord(T userRecord) {
		Assert.notNull(userRecord, "User parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			userRecord = entityManager.merge(userRecord);
			entityManager.persist(userRecord);
			transactionHelper.close();
		
			Long id = userRecord.getId();
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addUserRecords(Collection<T> userRecords) {
		Assert.notNull(userRecords, "UserRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = userRecords.iterator();
			while (iterator.hasNext()) {
				T user = iterator.next();
				user = entityManager.merge(user);
				entityManager.persist(user);
				Long id = user.getId();
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
	public void saveUserRecord(T userRecord) {
		Assert.notNull(userRecord, "UserRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			userRecord = entityManager.merge(userRecord);
			entityManager.persist(userRecord);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveUserRecords(Collection<T> userRecords) {
		Assert.notNull(userRecords, "UserRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = userRecords.iterator();
			while (iterator.hasNext()) {
				T user = iterator.next();
				user = entityManager.merge(user);
				entityManager.persist(user);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllUserRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T user = iterator.next();
				user = entityManager.merge(user);
				entityManager.remove(user);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeUserRecord(T userRecord) {
		Assert.notNull(userRecord, "UserRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			userRecord.getRoles().clear();
			userRecord = entityManager.merge(userRecord);
			entityManager.remove(userRecord);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeUserRecords(Collection<T> userRecords) {
		Assert.notNull(userRecords, "UserRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = userRecords.iterator();
			while (iterator.hasNext()) {
				T user = iterator.next();
				user.getRoles().clear();
				user = entityManager.merge(user);
				entityManager.remove(user);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeUserRecords(UserCriteria userCriteria) {
		Assert.notNull(userCriteria, "User record criteria must be specified");
		Assert.notNull(entityClass, "Entity class must be specified");
		UserQuery<T> query = new UserQuery<T>(entityManager);
		query.setEntityClass(entityClass);
		query.integrateCriteria(userCriteria);
		query.executeUpdate();
	}
	
}
