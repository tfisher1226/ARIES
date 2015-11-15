package template1.entity;

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
/*@imports@*/


@RunWith(MockitoJUnitRunner.class)
public class TreeNodeEntityTest extends AbstractEntityTest {

	private TreeNodeEntity fixture;


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

	protected TreeNodeEntity createFixture() {
		fixture = new TreeNodeEntity();
		return fixture;
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		super.tearDown();
	}

	@Test
	public void testGetTreeNodeUsingQuery() throws Exception {
		Query query = entityManager.createNamedQuery("getTreeNodes");
		@SuppressWarnings("unchecked") List<TreeNodeEntity> treeNodes = query.getResultList();
		Assert.notNull(treeNodes, "TreeNodes should exist");
	}

	@Test
	public void testGetTreeNodesUsingCriteria() throws Exception {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TreeNodeEntity> criteria = builder.createQuery(TreeNodeEntity.class);
		Root<TreeNodeEntity> treeNode = criteria.from(TreeNodeEntity.class);
		criteria.select(treeNode);
		//criteria.orderBy(builder.asc(game.get(Game_.id)));
		List<TreeNodeEntity> treeNodes = entityManager.createQuery(criteria).getResultList();
		Assert.notNull(treeNodes, "TreeNodes should exist");
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
