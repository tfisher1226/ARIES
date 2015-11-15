package org.aries.common;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;


public class RepositoryImpl /*extends JpaDaoSupport*/ implements Repository {

	protected final Log log = LogFactory.getLog(getClass());

	private EntityManager entityManager; 

	
	public RepositoryImpl() {
		//nothing yet
	}

	//SEAM @Create
	@PostConstruct
	public void initialize() {
		log.info("initializing");
		getEntityManager().setFlushMode(FlushModeType.AUTO);
	}
	
	//SEAM @Destroy
	@PreDestroy
	public void close() {
		log.info("closing");
		EntityManager em = getEntityManager();
		if (em.getFlushMode() == FlushModeType.AUTO)
			em.flush();
		em.clear();
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
	    this.entityManager = entityManager;
	}
	
	// XXX this is used until we fully switch to JPA 2.0
	/**
	 * Returns the hibernate session
	 * 
	 * @return hibernate session
	 */
	protected Session getSession() {
		return (Session) getEntityManager().getDelegate();
	}
	
	//SEAM @Transactional
	public <T> void create(T object) {
		getEntityManager().persist(object);
	}

	public <T, PK extends Serializable> T read(Class<T> type, PK id) {
		return getEntityManager().find(type, id);
	}

	public <T> boolean contains(T object) {
		return getEntityManager().contains(object);
	}

	//SEAM @Transactional
	public <T> T update(T object) {
		//EntityTransaction transaction = getEntityManager().getTransaction();
		T object2 = getEntityManager().merge(object);
		return object2;
	}

	//SEAM @Transactional
	public <T> void refresh(T object) {
		getEntityManager().refresh(object);
	}

	//SEAM @Transactional
	public <T> void delete(T object) {
		getEntityManager().remove(object);
	}

	public void flush() {
		getEntityManager().flush();
	}

	protected Criteria addPagedCriteria(Criteria criteria, int pageIndex, int pageSize) {
		criteria = criteria.setFetchSize(pageSize).setFirstResult(pageIndex * pageSize);
		return criteria;
	}
	
//    @Override
//    public <T> List<T> readAll(Class<T> type) {
//        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(type);
//        cq.select(cq.from(type));        
//        return getEntityManager().createQuery(cq).getResultList();
//    }
}
