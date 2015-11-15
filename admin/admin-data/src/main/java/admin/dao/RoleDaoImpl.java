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

import admin.RoleType;
import admin.entity.RoleEntity;


@Stateless
@Local(RoleDao.class)
public class RoleDaoImpl<T extends RoleEntity> extends DaoImpl<T> implements RoleDao<T> {
	
	@Override
	public T getRoleRecordById(Long id) {
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
	public T getRoleRecordByName(String name) {
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
	public List<T> getAllRoleRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getRoleRecordsByRoleType(RoleType roleType) {
		Assert.notNull(roleType, "RoleType must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByRoleType");
		query.setParameter("roleType", roleType);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getRoleRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addRoleRecord(T roleRecord) {
		Assert.notNull(roleRecord, "Role parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			roleRecord = entityManager.merge(roleRecord);
			entityManager.persist(roleRecord);
			transactionHelper.close();
		
			Long id = roleRecord.getId();
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addRoleRecords(Collection<T> roleRecords) {
		Assert.notNull(roleRecords, "RoleRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = roleRecords.iterator();
			while (iterator.hasNext()) {
				T role = iterator.next();
				role = entityManager.merge(role);
				entityManager.persist(role);
				Long id = role.getId();
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
	public void saveRoleRecord(T roleRecord) {
		Assert.notNull(roleRecord, "RoleRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			roleRecord = entityManager.merge(roleRecord);
			entityManager.persist(roleRecord);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveRoleRecords(Collection<T> roleRecords) {
		Assert.notNull(roleRecords, "RoleRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = roleRecords.iterator();
			while (iterator.hasNext()) {
				T role = iterator.next();
				role = entityManager.merge(role);
				entityManager.persist(role);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllRoleRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T role = iterator.next();
				role = entityManager.merge(role);
				entityManager.remove(role);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeRoleRecord(T roleRecord) {
		Assert.notNull(roleRecord, "RoleRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			roleRecord = entityManager.merge(roleRecord);
			entityManager.remove(roleRecord);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeRoleRecords(Collection<T> roleRecords) {
		Assert.notNull(roleRecords, "RoleRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = roleRecords.iterator();
			while (iterator.hasNext()) {
				T role = iterator.next();
				role = entityManager.merge(role);
				entityManager.remove(role);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
