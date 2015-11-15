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

import admin.entity.PermissionEntity;


@Stateless
@Local(PermissionDao.class)
public class PermissionDaoImpl<T extends PermissionEntity> extends DaoImpl<T> implements PermissionDao<T> {
	
	@Override
	public T getPermissionRecordById(Long id) {
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
	public List<T> getAllPermissionRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getPermissionRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addPermissionRecord(T permissionRecord) {
		Assert.notNull(permissionRecord, "Permission parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			permissionRecord = entityManager.merge(permissionRecord);
			entityManager.persist(permissionRecord);
			transactionHelper.close();
		
			Long id = permissionRecord.getId();
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addPermissionRecords(Collection<T> permissionRecords) {
		Assert.notNull(permissionRecords, "PermissionRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = permissionRecords.iterator();
			while (iterator.hasNext()) {
				T permission = iterator.next();
				permission = entityManager.merge(permission);
				entityManager.persist(permission);
				Long id = permission.getId();
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
	public void savePermissionRecord(T permissionRecord) {
		Assert.notNull(permissionRecord, "PermissionRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			permissionRecord = entityManager.merge(permissionRecord);
			entityManager.persist(permissionRecord);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void savePermissionRecords(Collection<T> permissionRecords) {
		Assert.notNull(permissionRecords, "PermissionRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = permissionRecords.iterator();
			while (iterator.hasNext()) {
				T permission = iterator.next();
				permission = entityManager.merge(permission);
				entityManager.persist(permission);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllPermissionRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T permission = iterator.next();
				permission = entityManager.merge(permission);
				entityManager.remove(permission);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removePermissionRecord(T permissionRecord) {
		Assert.notNull(permissionRecord, "PermissionRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			permissionRecord = entityManager.merge(permissionRecord);
			entityManager.remove(permissionRecord);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removePermissionRecords(Collection<T> permissionRecords) {
		Assert.notNull(permissionRecords, "PermissionRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = permissionRecords.iterator();
			while (iterator.hasNext()) {
				T permission = iterator.next();
				permission = entityManager.merge(permission);
				entityManager.remove(permission);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
