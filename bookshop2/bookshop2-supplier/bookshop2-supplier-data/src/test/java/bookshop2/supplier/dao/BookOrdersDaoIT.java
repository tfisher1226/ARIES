package bookshop2.supplier.dao;

import static org.junit.Assert.fail;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.aries.Assert;
import org.aries.AssertionFailure;
import org.aries.tx.DataLayerTestControl;
import org.aries.tx.TransactionTestControl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.supplier.BookOrdersFixture;
import bookshop2.supplier.data.bookOrderLog.BookOrderLogHelper;
import bookshop2.supplier.entity.BookOrdersEntity;


@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(NAME_ASCENDING)
public class BookOrdersDaoIT {

	private static TransactionTestControl transactionControl;
	
	private static DataLayerTestControl bookOrderLogControl;

	private BookOrderLogHelper bookOrderLogHelper;

	private OrderDao<BookOrdersEntity> bookOrdersDao;

	
	@BeforeClass
	public static void beforeClass() throws Exception {
		createTransactionControl();
		createBookOrderLogControl();
	}
	
	@AfterClass
	public static void afterCLass() throws Exception {
		transactionControl.tearDownTransactionManager();
		bookOrderLogControl.tearDownPersistenceUnit();
	}
	
	protected static void createTransactionControl() throws Exception {
		transactionControl = new TransactionTestControl();
		transactionControl.setupTransactionManager();
	}

	protected static void createBookOrderLogControl() throws Exception {
		bookOrderLogControl = new DataLayerTestControl(transactionControl);
		bookOrderLogControl.setDatabaseName("bookshop2_supplier_db");
		bookOrderLogControl.setDataSourceName("bookshop2_supplier_ds");
		bookOrderLogControl.setPersistenceUnitName("bookOrderLog");
		bookOrderLogControl.setupDataLayer();
	}
	
	@Before
	public void setUp() throws Exception {
		bookOrderLogControl.setUp();
		createBookOrderLogHelper();
		createBookOrdersDao();
	}

	@After
	public void tearDown() throws Exception {
		transactionControl.tearDown();
		bookOrderLogControl.tearDown();
		bookOrderLogHelper = null;
		bookOrdersDao = null;
	}

	protected void createBookOrderLogHelper() throws Exception {
		bookOrderLogHelper = new BookOrderLogHelper();
		bookOrderLogHelper.initialize(bookOrderLogControl);
	}
	
	protected void createBookOrdersDao() throws Exception {
		bookOrdersDao = bookOrderLogHelper.createBookOrdersDao();
		bookOrdersDao.setEntityManager(createEntityManager());
	}

	protected EntityManager createEntityManager() throws Exception {
		return bookOrderLogControl.createEntityManager();
	}

	protected BookOrdersEntity createBookOrdersEntity() {
		return bookOrderLogHelper.createBookOrdersEntity();
	}
	
	protected BookOrdersEntity createEmptyBookOrdersEntity() {
		return bookOrderLogHelper.createEmptyBookOrdersEntity();
	}

	@Test
	public void runTest_BookOrders_GetAllRecords() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders();
		
		// execute action
		List<BookOrdersEntity> records = bookOrdersDao.getAllOrderRecords();
		
		//validate results
		Assert.notNull(records, "BookOrders result must exist");
		Assert.equals(records.size(), 1, "BookOrders record count not correct");
	}

	@Test
	public void runTest_BookOrders_GetRecordById() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// execute test
		BookOrdersEntity bookOrders = bookOrdersDao.getOrderRecordById(id);
		
		// validate results
		Assert.notNull(bookOrders, "BookOrders should exist");
		Assert.equals(bookOrders.getId(), id, "Id should be correct");
	}

	@Test
	public void runTest_BookOrders_GetRecordById_null() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders();
		
		try {
			// execute test
			bookOrdersDao.getOrderRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (Exception e) {
			assertAssertionFailure(e, "Id must be specified");
		}
	}

	@Test
	public void runTest_BookOrders_AddAsItem() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// execute action
		BookOrdersEntity bookOrders = createBookOrdersEntity();
		Long id = bookOrdersDao.addOrderRecord(bookOrders);
		Assert.notNull(id, "Id should exist");
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(1);
		bookOrderLogHelper.verifyBookOrdersExist(id);
	}
	
	@Test
	public void runTest_BookOrders_AddAsItem_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		BookOrdersEntity bookOrders = createBookOrdersEntity();
		Long id = bookOrdersDao.addOrderRecord(bookOrders);
		Assert.notNull(id, "Id should exist");
		
		// close context
		transactionControl.commitTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(1);
		bookOrderLogHelper.verifyBookOrdersExist(id);
	}

	@Test
	public void runTest_BookOrders_AddAsItem_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		BookOrdersEntity bookOrders = createBookOrdersEntity();
		Long id = bookOrdersDao.addOrderRecord(bookOrders);
		Assert.notNull(id, "Id should exist");

		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}

	@Test
	public void runTest_BookOrders_AddAsItem_utx_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		BookOrdersEntity bookOrders = createBookOrdersEntity();
		Long id = bookOrdersDao.addOrderRecord(bookOrders);
		Assert.notNull(id, "Id should exist");
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(1);
		bookOrderLogHelper.verifyBookOrdersExist(id);
	}

	@Test
	public void runTest_BookOrders_AddAsItem_utx_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		BookOrdersEntity bookOrders = createBookOrdersEntity();
		Long id = bookOrdersDao.addOrderRecord(bookOrders);
		Assert.notNull(id, "Id should exist");

		// close context
		transactionControl.rollbackUserTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_AddAsItem_exception_person_name_null() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		try {
			// execute action
			BookOrdersEntity bookOrders = createBookOrdersEntity();
			bookOrders.setPersonName(null);
			bookOrdersDao.addOrderRecord(bookOrders);
			fail("Exception should have been thrown");
		
		} catch (Exception e) {
			assertConstraintViolation(e, "Column 'person_name_id' cannot be null");
		}
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_AddAsItem_exception_count_null() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		try {
			// execute action
			BookOrdersEntity bookOrders = createBookOrdersEntity();
			bookOrders.setCount(null);
			bookOrdersDao.addOrderRecord(bookOrders);
			fail("Exception should have been thrown");
		
		} catch (Exception e) {
			assertConstraintViolation(e, "Column 'count' cannot be null");
		}
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_AddAsItem_exception_books_orphanRemoval() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		try {
			// get existing record
			BookOrdersEntity bookOrders = bookOrdersDao.getOrderRecordById(id);
			Assert.notNull(bookOrders, "BookOrders record should exist");
			
			// save null Books
			bookOrders.setBooks(null);
			bookOrdersDao.saveOrderRecord(bookOrders);
			fail("Exception should have been thrown");
		
		} catch (Exception e) {
			Assert.exception(e, "no longer referenced by the owning entity instance");
		}
		
		// validate results
		bookOrderLogHelper.verifyOrderBookCount(id, 2);
		bookOrderLogHelper.verifyBookOrdersCount(1);
	}
	
	@Test
	public void runTest_BookOrders_AddAsList() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// execute action
		List<BookOrdersEntity> bookOrdersList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrdersDao.addOrderRecords(bookOrdersList);
		Assert.notNull(idList, "Id list should exist");
		Assert.equals(idList.size(), bookOrdersList.size(), "Id list should have correct length");
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(idList.size());
		bookOrderLogHelper.verifyBookOrdersExists(idList);
		List<BookOrdersEntity> bookOrdersList2 = bookOrderLogHelper.getBookOrdersRecordsByIds(idList);
		BookOrdersFixture.assertSameBookOrders(bookOrdersList, bookOrdersList2, true);
	}
	
	@Test
	public void runTest_BookOrders_AddAsList_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		List<BookOrdersEntity> bookOrdersList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrdersDao.addOrderRecords(bookOrdersList);
		Assert.notNull(idList, "Id list should exist");
		Assert.equals(idList.size(), bookOrdersList.size(), "Id list should have correct length");
		
		// close context
		transactionControl.commitTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(idList.size());
		bookOrderLogHelper.verifyBookOrdersExists(idList);
		List<BookOrdersEntity> bookOrdersList2 = bookOrderLogHelper.getBookOrdersRecordsByIds(idList);
		BookOrdersFixture.assertSameBookOrders(bookOrdersList, bookOrdersList2, true);
	}
	
	@Test
	public void runTest_BookOrders_AddAsList_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		List<BookOrdersEntity> bookOrdersList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrdersDao.addOrderRecords(bookOrdersList);
		Assert.notNull(idList, "Id list should exist");
		Assert.equals(idList.size(), bookOrdersList.size(), "Id list should have correct length");
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_AddAsList_utx_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		List<BookOrdersEntity> bookOrdersList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrdersDao.addOrderRecords(bookOrdersList);
		Assert.notNull(idList, "Id list should exist");
		Assert.equals(idList.size(), bookOrdersList.size(), "Id list should have correct length");
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(idList.size());
		bookOrderLogHelper.verifyBookOrdersExists(idList);
		List<BookOrdersEntity> bookOrdersList2 = bookOrderLogHelper.getBookOrdersRecordsByIds(idList);
		BookOrdersFixture.assertSameBookOrders(bookOrdersList, bookOrdersList2, true);
	}
	
	@Test
	public void runTest_BookOrders_AddAsList_utx_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		List<BookOrdersEntity> bookOrdersList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrdersDao.addOrderRecords(bookOrdersList);
		Assert.notNull(idList, "Id list should exist");
		Assert.equals(idList.size(), bookOrdersList.size(), "Id list should have correct length");
		
		// close context
		transactionControl.rollbackUserTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_SaveAsItem() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// read prepared record
		BookOrdersEntity bookOrdersToSave = bookOrdersDao.getOrderRecordById(id);
		Assert.notNull(bookOrdersToSave, "BookOrders record should exist");
		
		// modify and save record
		bookOrdersToSave.setCount(111);
		bookOrdersToSave.setDateTime(new Date(111L));
		bookOrdersDao.saveOrderRecord(bookOrdersToSave);
		
		// validate results
		BookOrdersEntity bookOrders = bookOrderLogHelper.getBookOrdersRecordById(id);
		Assert.equals(bookOrders.getCount(), new Integer(111), "BookOrders Count field not correct");
		Assert.equals(bookOrders.getDateTime(), new Date(111L), "BookOrders DateTime field not correct");
		BookOrdersFixture.assertSameBookOrders(bookOrders, bookOrdersToSave);
	}
	
	@Test
	public void runTest_BookOrders_SaveAsItem_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// read prepared record
		BookOrdersEntity bookOrdersToSave = bookOrdersDao.getOrderRecordById(id);
		Assert.notNull(bookOrdersToSave, "BookOrders record should exist");
		
		// modify and save record
		bookOrdersToSave.setCount(111);
		bookOrdersToSave.setDateTime(new Date(111L));
		bookOrdersDao.saveOrderRecord(bookOrdersToSave);
		
		// close context
		transactionControl.commitTransaction();

		// validate results
		BookOrdersEntity bookOrders = bookOrderLogHelper.getBookOrdersRecordById(id);
		Assert.equals(bookOrders.getCount(), new Integer(111), "BookOrders Count field not correct");
		Assert.equals(bookOrders.getDateTime(), new Date(111L), "BookOrders DateTime field not correct");
		BookOrdersFixture.assertSameBookOrders(bookOrders, bookOrdersToSave);
	}
	
	@Test
	public void runTest_BookOrders_SaveAsItem_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		BookOrdersEntity bookOrdersToAdd = createBookOrdersEntity();
		Long id = bookOrderLogHelper.addBookOrdersRecord(bookOrdersToAdd);
		
		// prepare context
		transactionControl.beginTransaction();
		
		// read prepared record
		BookOrdersEntity bookOrdersToSave = bookOrdersDao.getOrderRecordById(id);
		Assert.notNull(bookOrdersToSave, "BookOrders record should exist");
		
		// modify and save record
		bookOrdersToSave.setCount(111);
		bookOrdersToSave.setDateTime(new Date(111L));
		bookOrdersDao.saveOrderRecord(bookOrdersToSave);
		
		// close context
		transactionControl.rollbackTransaction();

		// validate results
		BookOrdersEntity bookOrders = bookOrderLogHelper.getBookOrdersRecordById(id);
		BookOrdersFixture.assertSameBookOrders(bookOrders, bookOrdersToAdd, true);
	}
	
	@Test
	public void runTest_BookOrders_SaveAsItem_utx_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// read prepared record
		BookOrdersEntity bookOrdersToSave = bookOrdersDao.getOrderRecordById(id);
		Assert.notNull(bookOrdersToSave, "BookOrders record should exist");
		
		// modify and save record
		bookOrdersToSave.setCount(111);
		bookOrdersToSave.setDateTime(new Date(111L));
		bookOrdersDao.saveOrderRecord(bookOrdersToSave);
		
		// close context
		transactionControl.commitUserTransaction();

		// validate results
		BookOrdersEntity bookOrders = bookOrderLogHelper.getBookOrdersRecordById(id);
		Assert.equals(bookOrders.getCount(), new Integer(111), "BookOrders Count field not correct");
		Assert.equals(bookOrders.getDateTime(), new Date(111L), "BookOrders DateTime field not correct");
		BookOrdersFixture.assertSameBookOrders(bookOrders, bookOrdersToSave);
	}
	
	@Test
	public void runTest_BookOrders_SaveAsItem_utx_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		BookOrdersEntity bookOrdersToAdd = createBookOrdersEntity();
		Long id = bookOrderLogHelper.addBookOrdersRecord(bookOrdersToAdd);
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// read prepared record
		BookOrdersEntity bookOrdersToSave = bookOrdersDao.getOrderRecordById(id);
		Assert.notNull(bookOrdersToSave, "BookOrders record should exist");
		
		// modify and save record
		bookOrdersToSave.setCount(111);
		bookOrdersToSave.setDateTime(new Date(111L));
		bookOrdersDao.saveOrderRecord(bookOrdersToSave);
		
		// close context
		transactionControl.rollbackUserTransaction();

		// validate results
		BookOrdersEntity bookOrders = bookOrderLogHelper.getBookOrdersRecordById(id);
		BookOrdersFixture.assertSameBookOrders(bookOrders, bookOrdersToAdd, true);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAll() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders();
		
		// execute action
		bookOrdersDao.removeAllOrderRecords();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAll_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		bookOrdersDao.removeAllOrderRecords();

		// close context
		transactionControl.commitTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAll_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders();
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		bookOrdersDao.removeAllOrderRecords();

		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(1);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAll_utx_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		bookOrdersDao.removeAllOrderRecords();
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAll_utx_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		bookOrderLogHelper.addBookOrders();
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		bookOrdersDao.removeAllOrderRecords();
		
		// close context
		transactionControl.rollbackUserTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(1);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAsItem() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		Assert.notNull(id, "Id should exist");
		
		// execute action
		BookOrdersEntity bookOrders = bookOrdersDao.getOrderRecordById(id);
		Assert.notNull(bookOrders, "BookOrders record should exist");
		bookOrdersDao.removeOrderRecord(bookOrders);

		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAsItem_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		Assert.notNull(id, "Id should exist");
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		BookOrdersEntity bookOrders = bookOrdersDao.getOrderRecordById(id);
		Assert.notNull(bookOrders, "BookOrders record should exist");
		bookOrdersDao.removeOrderRecord(bookOrders);

		// close context
		transactionControl.commitTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAsItem_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		Assert.notNull(id, "Id should exist");
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		BookOrdersEntity bookOrders = bookOrdersDao.getOrderRecordById(id);
		Assert.notNull(bookOrders, "BookOrders record should exist");
		bookOrdersDao.removeOrderRecord(bookOrders);
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(1);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAsItem_utx_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		Assert.notNull(id, "Id should exist");
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		BookOrdersEntity bookOrders = bookOrdersDao.getOrderRecordById(id);
		Assert.notNull(bookOrders, "BookOrders record should exist");
		bookOrdersDao.removeOrderRecord(bookOrders);
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAsItem_utx_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		Long id = bookOrderLogHelper.addBookOrders();
		Assert.notNull(id, "Id should exist");
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		BookOrdersEntity bookOrders = bookOrdersDao.getOrderRecordById(id);
		Assert.notNull(bookOrders, "BookOrders record should exist");
		bookOrdersDao.removeOrderRecord(bookOrders);
		
		// close context
		transactionControl.rollbackUserTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(1);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAsList() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		List<BookOrdersEntity> bookOrdersList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrderLogHelper.addBookOrdersRecords(bookOrdersList);
		Assert.notNull(idList, "Id list should exist");
		
		// execute action
		bookOrdersDao.removeOrderRecords(bookOrdersList);
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAsList_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		List<BookOrdersEntity> bookOrdersList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrderLogHelper.addBookOrdersRecords(bookOrdersList);
		Assert.notNull(idList, "Id list should exist");
		
		// prepare context
		transactionControl.beginTransaction();

		// execute action
		bookOrdersDao.removeOrderRecords(bookOrdersList);
		
		// close context
		transactionControl.commitTransaction();

		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAsList_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		List<BookOrdersEntity> bookOrdersList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrderLogHelper.addBookOrdersRecords(bookOrdersList);
		Assert.notNull(idList, "Id list should exist");
		
		// prepare context
		transactionControl.beginTransaction();
		
		// execute action
		bookOrdersDao.removeOrderRecords(bookOrdersList);
		
		// close context
		transactionControl.rollbackTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersExists(idList);
		bookOrderLogHelper.verifyBookOrdersCount(idList.size());
		List<BookOrdersEntity> bookOrdersList2 = bookOrderLogHelper.getBookOrdersRecordsByIds(idList);
		BookOrdersFixture.assertSameBookOrders(bookOrdersList, bookOrdersList2, true);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAsList_utx_commit() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		List<BookOrdersEntity> bookOrdersList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrderLogHelper.addBookOrdersRecords(bookOrdersList);
		Assert.notNull(idList, "Id list should exist");
		
		// prepare context
		transactionControl.beginUserTransaction();
		
		// execute action
		bookOrdersDao.removeOrderRecords(bookOrdersList);
		
		// close context
		transactionControl.commitUserTransaction();
		
		// validate results
		bookOrderLogHelper.verifyBookOrdersCount(0);
	}
	
	@Test
	public void runTest_BookOrders_RemoveAsList_utx_rollback() throws Exception {
		// prepare environment
		bookOrderLogHelper.assureRemoveAll();
		List<BookOrdersEntity> bookOrdersList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrderLogHelper.addBookOrdersRecords(bookOrdersList);
		Assert.notNull(idList, "Id list should exist");
		
		// prepare context
		transactionControl.beginUserTransaction();
				
		// execute action
		bookOrdersDao.removeOrderRecords(bookOrdersList);
		
		// close context
		transactionControl.rollbackUserTransaction();
				
		// validate results
		bookOrderLogHelper.verifyBookOrdersExists(idList);
		bookOrderLogHelper.verifyBookOrdersCount(idList.size());
		List<BookOrdersEntity> bookOrdersList2 = bookOrderLogHelper.getBookOrdersRecordsByIds(idList);
		BookOrdersFixture.assertSameBookOrders(bookOrdersList, bookOrdersList2, true);
	}
	
	
	protected void assertAssertionFailure(Exception e, String message) {
		Assert.exception(e, AssertionFailure.class, message);
	}

	protected void assertConstraintViolation(Exception e, String message) {
		Assert.exception(e, org.hibernate.exception.ConstraintViolationException.class, message);
		Assert.exception(e.getCause(), com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class, message);
	}


	
//	protected Long assureAddBookOrders() throws Exception {
//		BookOrdersEntity bookOrdersEntity = createBookOrdersEntity();
//		Long id = assureAddBookOrders(bookOrdersEntity);
//		return id;
//	}
//
//	protected Long assureAddBookOrders(BookOrdersEntity bookOrders) throws Exception {
//		openEntityManager();
//		control.beginTransaction();
//		Long id = fixture.addOrderRecord(bookOrders);
//		control.commitTransaction();
//		verifyBookOrdersExists(id);
//		closeEntityManager();
//		return id;
//	}
//
//	protected void assureDeleteBookOrders(Long id) throws Exception {
//		BookOrdersEntity bookOrders = fixture.getOrderRecordById(id);
//		assureDeleteBookOrders(bookOrders);
//	}
//
//	protected void assureDeleteBookOrders(BookOrdersEntity bookOrders) throws Exception {
//		openEntityManager();
//		control.beginTransaction();
//		fixture.removeOrderRecord(bookOrders);
//		control.commitTransaction();
//		verifyBookOrdersNotExists(bookOrders);
//		closeEntityManager();
//	}
//
//	protected void verifyBookOrdersCount(int expectedCount) throws Exception {
//		List<BookOrdersEntity> records = fixture.getAllOrderRecords();
//		verifyBookOrdersCount(records, expectedCount);
//	}
//
//	protected void verifyBookOrdersCount(List<BookOrdersEntity> records, int expectedCount) throws Exception {
//		Assert.notNull(records, "Result not found");
//		if (expectedCount > 0)
//			Assert.notNull(records, "BookOrders records should exist");
//		Assert.equals(expectedCount, records.size(), "BookOrders record count not correct");
//	}
//
//	protected void verifyBookOrdersExists(BookOrdersEntity bookOrders) throws Exception {
//		verifyBookOrdersExists(bookOrders.getId());
//	}
//
//	protected void verifyBookOrdersExists(Long id) throws Exception {
//		BookOrdersEntity bookOrders = fixture.getOrderRecordById(id);
//		Assert.notNull(bookOrders, "BookOrders should exist");
//		Assert.equals(bookOrders.getId(), id, "Ids should be same");
//	}
//
//	protected void verifyBookOrdersNotExists(BookOrdersEntity bookOrders) throws Exception {
//		verifyBookOrdersNotExists(bookOrders.getId());
//	}
//
//	protected void verifyBookOrdersNotExists(Long id) throws Exception {
//		BookOrdersEntity bookOrders = fixture.getOrderRecordById(id);
//		Assert.isNull(bookOrders, "BookOrders should not exist");
//	}

}
