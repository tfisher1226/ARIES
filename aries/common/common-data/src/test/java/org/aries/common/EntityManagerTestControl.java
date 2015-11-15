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


public class EntityManagerTestControl {

	protected Log log = LogFactory.getLog(getClass());

	protected EntityManagerFactory entityManagerFactory;
	
	protected EntityManager entityManager;

	
	protected Map getEntityManagerFactoryProperties() {
		Map properties = new HashMap();
		initializeEntityManagerFactoryProperties(properties);
		return properties;
	}

	protected void initializeEntityManagerFactoryProperties(Map properties) {
		//properties.put("hibernate.connection.autocommit", "false");
	}
	
	public EntityManagerFactory createEntityManagerFactory(String persistenceUnit) throws Exception {
		return createEntityManagerFactory(persistenceUnit, getEntityManagerFactoryProperties());
	}
	
	public EntityManagerFactory createEntityManagerFactory(String persistenceUnit, Map properties) throws Exception {
		return Persistence.createEntityManagerFactory(persistenceUnit, properties);
	}

	public void setupEntityManagerFactory(String persistenceUnit) throws Exception {
		entityManagerFactory = createEntityManagerFactory(persistenceUnit, getEntityManagerFactoryProperties());
	}

	public void setupEntityManagerFactory(String persistenceUnit, Map properties) throws Exception {
		entityManagerFactory = createEntityManagerFactory(persistenceUnit, properties);
	}

	public EntityManager createEntityManager() throws Exception {
		return entityManagerFactory.createEntityManager();
	}

	public EntityManager setupEntityManager() throws Exception {
		closeEntityManager(entityManager);
		entityManager = createEntityManager();
		return entityManager;
	}


//	protected EntityManager createEntityManager(String persistenceUnit) {
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		return entityManager;
//	}
	
//	protected EntityManager createEntityManager(String persistenceUnit, Map properties) {
//		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit, properties);
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		return entityManager;
//	}

//	protected Map<String, String> getEntityManagerProperties() {
//		Map<String, String> properties = new HashMap<String, String>();
//		return properties;
//	}

	public void teardownEntityManagerFactory() throws Exception {
		if (entityManagerFactory != null && entityManagerFactory.isOpen())
			entityManagerFactory.close();
		entityManagerFactory = null;
	}
	
	public void teardownEntityManager() throws Exception {
		closeEntityManager(entityManager);
		entityManager = null;
	}

	public void resetEntityManager() throws Exception {
		closeEntityManager(entityManager);
	}
	
	public void closeEntityManager(EntityManager entityManager) throws Exception {
		if (entityManager != null && entityManager.isOpen())
			entityManager.close();
	}
	

	public EntityManagerTestControl() {
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
