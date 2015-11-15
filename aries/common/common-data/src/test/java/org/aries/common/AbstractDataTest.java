package org.aries.common;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;


public abstract class AbstractDataTest extends AbstractTestCase {

	protected static EntityManagerFactory entityManagerFactory;
	
	protected static EntityManager entityManager;

	protected Log log = LogFactory.getLog(getClass());

	
	protected static Map getEntityManagerFactoryProperties() {
		Map properties = new HashMap();
		//properties.put("hibernate.connection.autocommit", "false");
		return properties;
	}

	protected static EntityManagerFactory createEntityManagerFactory(String persistenceUnit, Map properties) throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit, properties);
		return entityManagerFactory;
	}

	protected static void setupEntityManagerFactory(String persistenceUnit) throws Exception {
		entityManagerFactory = createEntityManagerFactory(persistenceUnit, getEntityManagerFactoryProperties());
	}

	protected static void setupEntityManagerFactory(String persistenceUnit, Map properties) throws Exception {
		entityManagerFactory = createEntityManagerFactory(persistenceUnit, properties);
	}

	public static EntityManager createEntityManager() throws Exception {
		entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}

	public static void setupEntityManager() throws Exception {
		resetEntityManager();
		createEntityManager();
	}


//	protected static EntityManager createEntityManager(String persistenceUnit) {
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		return entityManager;
//	}
	
//	protected static EntityManager createEntityManager(String persistenceUnit, Map properties) {
//		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit, properties);
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		return entityManager;
//	}

//	protected static Map<String, String> getEntityManagerProperties() {
//		Map<String, String> properties = new HashMap<String, String>();
//		return properties;
//	}

	protected static void teardownEntityManagerFactory() throws Exception {
		if (entityManagerFactory != null && entityManagerFactory.isOpen())
			entityManagerFactory.close();
		entityManagerFactory = null;
	}
	
	protected static void teardownEntityManager() throws Exception {
		resetEntityManager();
		entityManager = null;
	}

	public static void resetEntityManager() throws Exception {
		if (entityManager != null && entityManager.isOpen())
			entityManager.close();
	}
	

	public AbstractDataTest() {
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
