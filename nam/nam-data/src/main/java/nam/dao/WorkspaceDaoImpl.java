package nam.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import nam.entity.WorkspaceEntity;

import org.aries.Assert;
import org.aries.common.dao.DaoImpl;
import org.aries.tx.TransactionHelper;
import org.aries.util.ExceptionUtil;


@Stateless
@Local(WorkspaceDao.class)
public class WorkspaceDaoImpl<T extends WorkspaceEntity> extends DaoImpl<T> implements WorkspaceDao<T> {
	
	@Override
	public T getWorkspaceRecordById(Long id) {
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
	public T getWorkspaceRecordByName(String name) {
		Assert.notNull(name, "Name must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordByName");
		query.setParameter("workspaceName", name);
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
	public List<T> getAllWorkspaceRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getWorkspaceRecordsByUser(String user) {
		Assert.notNull(user, "Name must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByUser");
		query.setParameter("user", user);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getWorkspaceRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addWorkspaceRecord(T workspaceRecord) {
		Assert.notNull(workspaceRecord, "Workspace parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			workspaceRecord = entityManager.merge(workspaceRecord);
			entityManager.persist(workspaceRecord);
			transactionHelper.close();
		
			Long id = workspaceRecord.getId();
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addWorkspaceRecords(Collection<T> workspaceRecords) {
		Assert.notNull(workspaceRecords, "WorkspaceRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = workspaceRecords.iterator();
			while (iterator.hasNext()) {
				T workspace = iterator.next();
				workspace = entityManager.merge(workspace);
				entityManager.persist(workspace);
				Long id = workspace.getId();
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
	public void saveWorkspaceRecord(T workspaceRecord) {
		Assert.notNull(workspaceRecord, "WorkspaceRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			workspaceRecord = entityManager.merge(workspaceRecord);
			entityManager.persist(workspaceRecord);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveWorkspaceRecords(Collection<T> workspaceRecords) {
		Assert.notNull(workspaceRecords, "WorkspaceRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = workspaceRecords.iterator();
			while (iterator.hasNext()) {
				T workspace = iterator.next();
				workspace = entityManager.merge(workspace);
				entityManager.persist(workspace);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllWorkspaceRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T workspace = iterator.next();
				workspace = entityManager.merge(workspace);
				entityManager.remove(workspace);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeWorkspaceRecord(T workspaceRecord) {
		Assert.notNull(workspaceRecord, "WorkspaceRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			workspaceRecord = entityManager.merge(workspaceRecord);
			entityManager.remove(workspaceRecord);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeWorkspaceRecords(Collection<T> workspaceRecords) {
		Assert.notNull(workspaceRecords, "WorkspaceRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = workspaceRecords.iterator();
			while (iterator.hasNext()) {
				T workspace = iterator.next();
				workspace = entityManager.merge(workspace);
				entityManager.remove(workspace);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
