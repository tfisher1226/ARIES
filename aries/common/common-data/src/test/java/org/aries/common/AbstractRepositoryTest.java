package org.aries.common;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public abstract class AbstractRepositoryTest {

    protected EntityManagerFactory entityManagerFactory;

    protected EntityManager entityManager;

    protected String unitName;


	public AbstractRepositoryTest(String unitName) {
		this.unitName = unitName;
	}


	public void setup() throws Exception {
		//BasicConfigurator.configure();
        //Logger.getLogger("org").setLevel(Level.ERROR);
        entityManagerFactory = Persistence.createEntityManagerFactory(unitName);
        entityManager = entityManagerFactory.createEntityManager();
	}

	public void teardown() throws Exception {
        entityManagerFactory = null;
        entityManager = null;
	}

}
