package org.aries.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;


public abstract class DaoImpl<T> implements Dao<T> {

	protected Log log = LogFactory.getLog(getClass());
	
	protected EntityManager entityManager;
	
	protected Class<T> entityClass;
	
	protected String name;
	
	
	public DaoImpl() {
		//nothing yet
	}

	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public void initialize(String name) {
		this.name = name;
	}

	//SEAM @Create
	public void initialize() {
		log.info("initializing");
//		getEntityManager().setFlushMode(FlushModeType.AUTO);
	}
	
	//SEAM @Destroy
	public void close() {
		log.info("closing");
//		EntityManager em = getEntityManager();
//		if (em.getFlushMode() == FlushModeType.AUTO)
//			em.flush();
//		em.clear();
	}

	@Override
	public Query createQuery(String sql) {
		return getEntityManager().createQuery(sql);
	}
	
	@Deprecated
	// TODO only use this until we switch to JPA 2.0
	protected Session getSession() {
		return (Session) getEntityManager().getDelegate();
	}
	
	public <PK extends Serializable> T read(Class<T> type, PK id) {
		return getEntityManager().find(type, id);
	}
	
	//TODO
    @Override
    public Collection<T> readAll(Class<T> type) {
        return null;
    }
    
	public boolean contains(T object) {
		return getEntityManager().contains(object);
	}

	//@Transactional
	public void refresh(T object) {
		getEntityManager().refresh(object);
	}
	
	//@Transactional
	public void persist(T object) {
		//((SessionImpl) getSession()).getTransactionCoordinator().getTransactionContext().
		if (object != null) {
			EntityManager em = getEntityManager();
			//EntityTransaction transaction = em.getTransaction();
			try {
				//transaction.begin();
				em.persist(object);
				//transaction.commit();
			} catch (Throwable e) {
				//transaction.rollback();
				log.error(e);
			}
		}
	}

	//@Transactional
	public void persist(Collection<T> objects) {
		if (objects != null) {
			EntityManager em = getEntityManager();
			//EntityTransaction transaction = em.getTransaction();
			Iterator<T> iterator = objects.iterator();
			try {
				//transaction.begin();
				while (iterator.hasNext()) {
					T object = iterator.next();
					em.persist(object);
				}
				//transaction.commit();
			} catch (Throwable e) {
				//transaction.rollback();
				log.error(e);
			}
		}
	}

	//@Transactional
	public T merge(T object) {
		//EntityTransaction transaction = getEntityManager().getTransaction();
		T object2 = getEntityManager().merge(object);
		return object2;
	}

	//@Transactional
	public void delete(T object) {
		getEntityManager().remove(object);
		//getEntityManager().flush();
	}

	public void clear() {
		getEntityManager().clear();
	}
	
	public void flush() {
		getEntityManager().flush();
	}

	public void detach(T entity) {
		@SuppressWarnings("deprecation")
		Session session = getSession();
		session.evict(entity);
	}
	
	protected Criteria addPagedCriteria(Criteria criteria, int pageIndex, int pageSize) {
		criteria = criteria.setFetchSize(pageSize).setFirstResult(pageIndex * pageSize);
		return criteria;
	}

}
