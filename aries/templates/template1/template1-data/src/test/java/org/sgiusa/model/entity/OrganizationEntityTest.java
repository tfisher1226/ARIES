package org.sgiusa.model.entity;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.aries.Assert;
import org.aries.common.AbstractEntityTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.sgiusa.model.entity.OrganizationEntity;


@RunWith(MockitoJUnitRunner.class)
public class OrganizationEntityTest extends AbstractEntityTest {

	private OrganizationEntity fixture;


	@BeforeClass  
	public static void beforeClass() {
		setupEntityManager("Template1TestDS");
	}  

	@AfterClass  
	public static void afterClass() {
		teardownEntityManager();
	}


	@Before
	public void setUp() throws Exception {
		fixture = createFixture();
		super.setUp();
	}

	protected OrganizationEntity createFixture() {
		fixture = new OrganizationEntity();
		return fixture;
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		super.tearDown();
	}

	@Test
	public void testGetOrganizationUsingQuery() throws Exception {
		Query query = entityManager.createNamedQuery("getOrganizations");
		@SuppressWarnings("unchecked") List<OrganizationEntity> organizations = query.getResultList();
		Assert.notNull(organizations, "Organizations should exist");
	}

	@Test
	public void testGetOrganizationsUsingCriteria() throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<OrganizationEntity> criteria = builder.createQuery(OrganizationEntity.class);
		Root<OrganizationEntity> organization = criteria.from(OrganizationEntity.class);
		criteria.select(organization);
		//criteria.orderBy(builder.asc(game.get(Game_.id)));
		List<OrganizationEntity> organizations = entityManager.createQuery(criteria).getResultList();
		Assert.notNull(organizations, "Organizations should exist");
	}

	/*
	 * Helper methods
	 * --------------
	 */

	public void assureExpectedResults() throws Exception {
		//List<RequestEntity> requests = fixture.getRequests(criteria);
		//assureExpectedResults(requests, expectedCount);
	}

}
