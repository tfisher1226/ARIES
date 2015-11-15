package nam.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import nam.entity.ProjectEntity;

import org.aries.Assert;
import org.aries.common.dao.DaoImpl;
import org.aries.tx.TransactionHelper;
import org.aries.util.ExceptionUtil;


@Stateless
@Local(ProjectDao.class)
public class ProjectDaoImpl<T extends ProjectEntity> extends DaoImpl<T> implements ProjectDao<T> {
	
	@Override
	public T getProjectRecordById(Long id) {
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
	public T getProjectRecordByName(String name) {
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
	public List<T> getAllProjectRecords() {
		Query query = entityManager.createNamedQuery("getAll"+name+"Records");
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getProjectRecordsByOwner(String owner) {
		Assert.notNull(owner, "Owner must be specified");
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByOwner");
		query.setParameter("owner", owner);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getProjectRecordsByPage(int pageIndex, int pageSize) {
		Query query = entityManager.createNamedQuery("get"+name+"RecordsByPage");
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		List<T> result = query.getResultList();
		return result;
	}
	
	@Override
	public Long addProjectRecord(T projectRecord) {
		Assert.notNull(projectRecord, "Project parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			projectRecord = entityManager.merge(projectRecord);
			entityManager.persist(projectRecord);
			transactionHelper.close();
		
			Long id = projectRecord.getId();
			return id;
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public List<Long> addProjectRecords(Collection<T> projectRecords) {
		Assert.notNull(projectRecords, "ProjectRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			List<Long> idList = new ArrayList<Long>();
			Iterator<T> iterator = projectRecords.iterator();
			while (iterator.hasNext()) {
				T project = iterator.next();
				project = entityManager.merge(project);
				entityManager.persist(project);
				Long id = project.getId();
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
	public void saveProjectRecord(T projectRecord) {
		Assert.notNull(projectRecord, "ProjectRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			projectRecord = entityManager.merge(projectRecord);
			entityManager.persist(projectRecord);
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void saveProjectRecords(Collection<T> projectRecords) {
		Assert.notNull(projectRecords, "ProjectRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
		
			Iterator<T> iterator = projectRecords.iterator();
			while (iterator.hasNext()) {
				T project = iterator.next();
				project = entityManager.merge(project);
				entityManager.persist(project);
			}
		
			transactionHelper.close();
		
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeAllProjectRecords() {
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Query query = entityManager.createNamedQuery("getAll"+name+"Records");
			@SuppressWarnings("unchecked") List<T> records = query.getResultList();
			
			Iterator<T> iterator = records.iterator();
			while (iterator.hasNext()) {
				T project = iterator.next();
				project = entityManager.merge(project);
				entityManager.remove(project);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeProjectRecord(T projectRecord) {
		Assert.notNull(projectRecord, "ProjectRecord parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			projectRecord = entityManager.merge(projectRecord);
			entityManager.remove(projectRecord);
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	@Override
	public void removeProjectRecords(Collection<T> projectRecords) {
		Assert.notNull(projectRecords, "ProjectRecords parameter must be specified");
		TransactionHelper transactionHelper = new TransactionHelper();
		
		try {
			transactionHelper.open();
			entityManager.joinTransaction();
			
			Iterator<T> iterator = projectRecords.iterator();
			while (iterator.hasNext()) {
				T project = iterator.next();
				project = entityManager.merge(project);
				entityManager.remove(project);
			}
			
			transactionHelper.close();
			
		} catch (Throwable e) {
			log.error(e);
			transactionHelper.fault(e);
			throw ExceptionUtil.rewrap(e);
		}
	}
	
}
