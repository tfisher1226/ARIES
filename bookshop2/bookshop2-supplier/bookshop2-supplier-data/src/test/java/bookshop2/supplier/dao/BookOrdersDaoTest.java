package bookshop2.supplier.dao;

import java.util.List;

import org.aries.Assert;
import org.aries.tx.DataLayerTestControl;
import org.aries.tx.TransactionTestControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.supplier.entity.BookOrdersEntity;


@RunWith(MockitoJUnitRunner.class)
public class BookOrdersDaoTest {

	private static TransactionTestControl transactionControl;
	
	private static DataLayerTestControl bookOrderLogControl;

	private OrderDao<BookOrdersEntity> bookOrdersDao;
	
	//private BookOrderLogHelper helper;


	@BeforeClass
	public static void beforeClass() throws Exception {
		createTransactionControl();
		createDataLayerControl();
	}

	protected static void createTransactionControl() throws Exception {
		transactionControl = new TransactionTestControl();
		transactionControl.setupTransactionManager();
	}

	protected static void createDataLayerControl() throws Exception {
		bookOrderLogControl = new DataLayerTestControl(transactionControl);
		bookOrderLogControl.setDatabaseName("bookshop2_supplier_db");
		bookOrderLogControl.setDataSourceName("bookshop2_supplier_ds");
		bookOrderLogControl.setPersistenceUnitName("bookOrderLog");
		bookOrderLogControl.setupDataLayer();
	}
	
	@AfterClass
	public static void afterCLass() throws Exception {
		transactionControl.tearDownTransactionManager();
		bookOrderLogControl.tearDownPersistenceUnit();
	}

	@Before
	public void setUp() throws Exception {
		bookOrderLogControl.setUp();
		createFixture();
		//createHelper();
	}

	@After
	public void tearDown() throws Exception {
		transactionControl.tearDown();
		bookOrderLogControl.tearDown();
		//helper = null;
		bookOrdersDao = null;
	}

	
//	@Before
//	public void setUp() throws Exception {
//		bookOrdersDao = createFixture();
////		List<Member> membersList = OrgSgiusaModelFixture.createMember_List(7);
////		Iterator<Member> iterator = membersList.iterator();
////		while (iterator.hasNext()) {
////			Member member = (Member) iterator.next();
////			bookOrdersDao.saveMember(member);
////		}
//		super.setUp();
//	}

	
	protected OrderDao<BookOrdersEntity> createFixture() throws Exception {
		bookOrdersDao = new OrderDaoImpl<BookOrdersEntity>();
		bookOrdersDao.setEntityClass(BookOrdersEntity.class);
		//dao.setEntityManager(entityManager);
		openEntityManager();
		bookOrdersDao.initialize("BookOrders");
		return bookOrdersDao;
	}

//	protected BookOrderLogHelper createHelper() throws Exception {
//		helper = new BookOrderLogHelper(control);
//		return helper;
//	}
	
	protected void openEntityManager() throws Exception {
		bookOrdersDao.setEntityManager(bookOrderLogControl.setupEntityManager());
	}

	protected void closeEntityManager() throws Exception { 
		bookOrderLogControl.resetEntityManager();
		bookOrdersDao.setEntityManager(null);
	}
	
	
	@Test
	//@Ignore
	public void testGetBookOrders() throws Exception {
		List<BookOrdersEntity> bookOrders = bookOrdersDao.getAllOrderRecords();
		Assert.notNull(bookOrders, "BookOrders should exist");
		Assert.isEmpty(bookOrders, "Results not found");
		//Assert.equals(bookOrders.size(), 7, "Result count not correct");
	}

//	@Test
//	@Ignore
//	public void testGetMembers_ByOrganization() throws Exception {
//		MemberCriteria searchCriteria = new MemberCriteria();
//		searchCriteria.getOrganizationSet().add(createOrganizationForTest());
//		
//		//execute
//		List<MemberEntity> members = bookOrdersDao.getMemberListByMemberCriteria(searchCriteria);
//		Assert.notNull(members, "Result null");
//		Assert.notEmpty(members, "Results not found");
//		Assert.equals(members.size(), 7, "Result count not correct");
//	}
//
//	@Test
//	@Ignore
//	public void testGetMembers_ByOrganizations() throws Exception {
//		MemberCriteria searchCriteria = new MemberCriteria();
//		searchCriteria.getOrganizationSet().addAll(createOrganizationsForTest());
//		
//		//execute
//		List<MemberEntity> members = bookOrdersDao.getMemberListByMemberCriteria(searchCriteria);
//		Assert.notNull(members, "Result null");
//		Assert.notEmpty(members, "Results not found");
//		Assert.equals(members.size(), 7, "Result count not correct");
//	}
//
//	@Test
//	@Ignore
//	public void testGetMembers_ByOrganizationAndDivision() throws Exception {
//		MemberCriteria searchCriteria = new MemberCriteria();
//		searchCriteria.getOrganizationSet().add(createOrganizationForTest());
//		searchCriteria.getDivisionSet().add(Division.YWD);
//
//		//execute
//		List<MemberEntity> members = bookOrdersDao.getMemberListByMemberCriteria(searchCriteria);
//		Assert.notNull(members, "Result should not be null");
//		Assert.notEmpty(members, "Members should exist");
//		Assert.equals(members.size(), 2, "Result count not correct");
//	}
//
//	@Test
//	@Ignore
//	public void testGetMembers_ByOrganizationDivisionAndSubDivision() throws Exception {
//		MemberCriteria searchCriteria = new MemberCriteria();
//		searchCriteria.getOrganizationSet().add(createOrganizationForTest());
//		searchCriteria.getDivisionSet().add(Division.YWD);
//		searchCriteria.getSubDivisionSet().add(SubDivision.STUDENT);
//
//		//execute
//		List<MemberEntity> members = bookOrdersDao.getMemberListByMemberCriteria(searchCriteria);
//		Assert.notNull(members, "Result should not be null");
//		Assert.notEmpty(members, "Members should exist");
//		Assert.equals(members.size(), 1, "Result count not correct");
//	}
//
//	@Test
//	@Ignore
//	public void testGetMembers_ByOrganizationDivisionAndGroup() throws Exception {
//		MemberCriteria searchCriteria = new MemberCriteria();
//		searchCriteria.getOrganizationSet().add(createOrganizationForTest());
//		searchCriteria.getDivisionSet().add(Division.YWD);
//		searchCriteria.getActivityGroupsSet().add(ActivityGroup.BYAKUREN);
//
//		//execute
//		List<MemberEntity> members = bookOrdersDao.getMemberListByMemberCriteria(searchCriteria);
//		Assert.notNull(members, "Result should not be null");
//		Assert.notEmpty(members, "Members should exist");
//		Assert.equals(members.size(), 1, "Result count not correct");
//	}
//
//	@Test
//	@Ignore
//	public void testGetMembers_ByOrganizationDivisionAndSubDivisionAndGroup() throws Exception {
//		MemberCriteria searchCriteria = new MemberCriteria();
//		searchCriteria.getOrganizationSet().add(createOrganizationForTest());
//		searchCriteria.getDivisionSet().add(Division.YWD);
//		searchCriteria.getSubDivisionSet().add(SubDivision.STUDENT);
//		searchCriteria.getActivityGroupsSet().add(ActivityGroup.BYAKUREN);
//
//		//execute
//		List<MemberEntity> members = bookOrdersDao.getMemberListByMemberCriteria(searchCriteria);
//		Assert.notNull(members, "Result should not be null");
//		Assert.equals(members.size(), 0, "Result count not correct");
//	}
//
//	protected List<Organization> createOrganizationsForTest() {
//		List<Organization> organizations = new ArrayList<Organization>();
//		organizations.add(createOrganizationForTest(5L));
//		organizations.add(createOrganizationForTest(4L));
//		organizations.add(createOrganizationForTest(3L));
//		return organizations;
//	}
//
//	protected Organization createOrganizationForTest() {
//		return createOrganizationForTest(getOrganizationIdForTest());
//	}
//
//	protected Organization createOrganizationForTest(Long organizationId) {
//		Organization organization = new Organization();
//		organization.setId(organizationId);
//		return organization;
//	}
//
//	protected Long getOrganizationIdForTest() {
//		//String organizationId = "o1020455483983";
//		//Mira Mar District: 5L
//		Long id = 5L;
//		return id;
//	}


}
