package org.aries.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;


public abstract class AbstractEntityTest {

	protected static EntityManager entityManager;
	
	protected Log log = LogFactory.getLog(getClass());

	
	protected static void setupEntityManager(String persistenceUnit) {
		entityManager = createEntityManager(persistenceUnit);
	}

	protected static EntityManager createEntityManager(String persistenceUnit) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

//	protected static Map<String, String> getEntityManagerProperties() {
//		Map<String, String> properties = new HashMap<String, String>();
//		return properties;
//	}

	protected static void teardownEntityManager() {
		entityManager.close();
		entityManager = null;
	}


	public AbstractEntityTest() {
		//nothing for now
	}
	
	@Before
	public void setUp() throws Exception {
		//nothing for now
		//super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		//nothing for now
		//super.tearDown();
	}
	
}
