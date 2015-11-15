package org.aries.common;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EntityManagerTestFixture {

	public static Log log = LogFactory.getLog(EntityManagerTestFixture.class);
	
	public EntityManagerFactory entityManagerFactory;
	
	public EntityManager entityManager;

	public String persistenceUnitName;
	
	
	@SuppressWarnings("unused")
	private EntityManagerTestFixture() {
		//nothing for now
	}
	
	public EntityManagerTestFixture(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}
	
	public void setupEntityManagerFactory(String persistenceUnit) {
		entityManagerFactory = createEntityManagerFactory(persistenceUnit);
	}

	public void setupEntityManagerFactory(String persistenceUnit, Map properties) {
		entityManagerFactory = createEntityManagerFactory(persistenceUnit, properties);
	}

	public EntityManager createEntityManager() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}
	

	public static Map getEntityManagerFactoryProperties() {
		Map properties = new HashMap();
		//properties.put("hibernate.connection.autocommit", "false");
		return properties;
	}

	public static EntityManagerFactory createEntityManagerFactory(String persistenceUnit) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit, getEntityManagerFactoryProperties());
		return entityManagerFactory;
	}
	
	public static EntityManagerFactory createEntityManagerFactory(String persistenceUnit, Map properties) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit, properties);
		return entityManagerFactory;
	}

	public static EntityManager createEntityManager(String persistenceUnit) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager;
	}
}
