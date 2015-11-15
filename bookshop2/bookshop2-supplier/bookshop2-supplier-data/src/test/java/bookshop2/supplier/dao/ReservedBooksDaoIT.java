package bookshop2.supplier.dao;

import static org.junit.Assert.fail;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.Arrays;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.supplier.data.bookInventory.BookInventoryHelper;
import bookshop2.supplier.entity.ReservedBooksEntity;


@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(NAME_ASCENDING)
public class ReservedBooksDaoIT {

	private static TransactionTestControl transactionControl;
	
	private static DataLayerTestControl bookInventoryControl;

	private BookInventoryHelper bookInventoryHelper;
	
	private BookDao<ReservedBooksEntity> reservedBooksDao;

	
	@BeforeClass
	public static void beforeClass() throws Exception {
		createTransactionControl();
		createBookInventoryControl();
	}
	
	@AfterClass
	public static void afterCLass() throws Exception {
		transactionControl.tearDownTransactionManager();
		bookInventoryControl.tearDownPersistenceUnit();
	}

	protected static void createTransactionControl() throws Exception {
		transactionControl = new TransactionTestControl();
		transactionControl.setupTransactionManager();
	}
	
	protected static void createBookInventoryControl() throws Exception {
		bookInventoryControl = new DataLayerTestControl(transactionControl);
		bookInventoryControl.setDatabaseName("bookshop2_supplier_db");
		bookInventoryControl.setDataSourceName("bookshop2_supplier");
		bookInventoryControl.setPersistenceUnitName("bookInventory");
		bookInventoryControl.setupDataLayer();
	}
	
	@Before
	public void setUp() throws Exception {
		bookInventoryControl.setUp();
		createBookInventoryHelper();
		createReservedBooksDao();
	}

	@After
	public void tearDown() throws Exception {
		transactionControl.tearDown();
		bookInventoryControl.tearDown();
		bookInventoryHelper = null;
		reservedBooksDao = null;
	}

	protected void createBookInventoryHelper() throws Exception {
		bookInventoryHelper = new BookInventoryHelper();
		bookInventoryHelper.initialize(bookInventoryControl);
	}
	
	protected void createReservedBooksDao() throws Exception {
		reservedBooksDao = bookInventoryHelper.createReservedBooksDao();
		reservedBooksDao.setEntityManager(createEntityManager());
	}

	protected EntityManager createEntityManager() throws Exception {
		return bookInventoryControl.createEntityManager();
	}

	protected ReservedBooksEntity createReservedBooksEntity() throws Exception {
		return bookInventoryHelper.createReservedBooksEntity();
	}
	
	protected ReservedBooksEntity createEmptyReservedBooksEntity() {
		return bookInventoryHelper.createEmptyReservedBooksEntity();
	}

	@Test
	public void testGetAllRecords() throws Exception {
		// prepare environment
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooksRecord();
		
		//execute test
		List<ReservedBooksEntity> records = reservedBooksDao.getAllBookRecords();
		
		//validate results
		bookInventoryHelper.verifyReservedBooksCount(records, 1);
	}
		
	@Test
	public void testGetReservedBooksById() throws Exception {
		// prepare environment
		bookInventoryHelper.assureRemoveAll();
		Long id = bookInventoryHelper.addReservedBooksRecord();
		
		// prepare context
		//openEntityManager();
		
		// execute test
		ReservedBooksEntity reservedBooks = reservedBooksDao.getBookRecordById(id);
		
		// close context
		//closeEntityManager();
		
		// validate results
		Assert.notNull(reservedBooks, "ReservedBooks should exist");
		Assert.equals(reservedBooks.getId(), id, "Id should be correct");
	}

	@Test
	public void testGetReservedBooksById_Null() throws Exception {
		// prepare environment
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooksRecord();
		
		try {
			// prepare context
			//openEntityManager();
		
			// execute test
			reservedBooksDao.getBookRecordById(null);
			fail("Exception should have been thrown");
		
		} catch (AssertionFailure e) {
			Assert.exception(e, "Id must be specified");
		
		} finally {
			// close context
			//closeEntityManager();
		}
	}

	@Test
	public void testAddReservedBooks_Commit() throws Exception {
		// prepare environment
		bookInventoryHelper.assureRemoveAll();
		
		// prepare context
		//openEntityManager();
		transactionControl.beginTransaction();
		
		//execute test
		ReservedBooksEntity reservedBooks = createReservedBooksEntity();
		Long id = reservedBooksDao.addBookRecord(reservedBooks);
		Assert.notNull(id, "Id should exist");
		
		// close context
		transactionControl.commitTransaction();
		//closeEntityManager();
		
		// validate results
		//openEntityManager();
		bookInventoryHelper.verifyReservedBooksCount(1);
		bookInventoryHelper.verifyReservedBooksExists(id);
		//closeEntityManager();
	}

	@Test
	public void testAddReservedBooks_Rollback() throws Exception {
		// prepare environment
		bookInventoryHelper.assureRemoveAll();
		
		// prepare context
		//openEntityManager();
		transactionControl.beginTransaction();
		
		//execute test
		ReservedBooksEntity reservedBooks = createReservedBooksEntity();
		Long id = reservedBooksDao.addBookRecord(reservedBooks);
		Assert.notNull(id, "Id should exist");
		
		// close context
		transactionControl.rollbackTransaction();
		//closeEntityManager();
		
		// validate results
		//openEntityManager();
		bookInventoryHelper.verifyReservedBooksCount(0);
		//closeEntityManager();
	}

	@Test
	public void testAddToReservedBooks_Commit() throws Exception {
		// prepare environment
		bookInventoryHelper.assureRemoveAll();
		
		// prepare context
		//openEntityManager();
		transactionControl.beginTransaction();
		
		//execute test
		List<ReservedBooksEntity> reservedBooks = bookInventoryHelper.createReservedBooksEntityList();
		List<Long> ids = reservedBooksDao.addBookRecords(reservedBooks);
		Assert.notEmpty(ids, "Ids should exist");
		
		// close context
		transactionControl.commitTransaction();
		//closeEntityManager();
		
		// validate results
		//openEntityManager();
		bookInventoryHelper.verifyReservedBooksCount(2);
		bookInventoryHelper.verifyReservedBooksExists(ids);
		//closeEntityManager();
	}

	@Test
	public void testAddToReservedBooks_Rollback() throws Exception {
		// prepare environment
		bookInventoryHelper.assureRemoveAll();
		
		// prepare context
		//openEntityManager();
		transactionControl.beginTransaction();
		
		//execute test
		List<ReservedBooksEntity> reservedBooks = bookInventoryHelper.createReservedBooksEntityList();
		List<Long> ids = reservedBooksDao.addBookRecords(reservedBooks);
		Assert.notEmpty(ids, "Ids should exist");
		
		// close context
		transactionControl.rollbackTransaction();
		//closeEntityManager();
		
		// validate results
		//openEntityManager();
		bookInventoryHelper.verifyReservedBooksCount(0);
		//closeEntityManager();
	}
	
	@Test
	public void testRemoveAllReservedBooks_Commit() throws Exception {
		
		// prepare environment
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooksRecord();
		
		// prepare context
		//openEntityManager();
		transactionControl.beginTransaction();
		
		//execute test
		reservedBooksDao.removeAllBookRecords();
		
		// close context
		transactionControl.commitTransaction();
		//closeEntityManager();
		
		// validate results
		//openEntityManager();
		bookInventoryHelper.verifyNoReservedBooksRecordsExist();
		//closeEntityManager();
	}

	@Test
	public void testRemoveAllReservedBooks_Rollback() throws Exception {
		
		// prepare environment
		bookInventoryHelper.assureRemoveAll();
		bookInventoryHelper.addReservedBooksRecord();
		
		// prepare context
		//openEntityManager();
		transactionControl.beginTransaction();
		
		//execute test
		reservedBooksDao.removeAllBookRecords();
		
		// close context
		transactionControl.rollbackTransaction();
		//closeEntityManager();
		
		// validate results
		//openEntityManager();
		bookInventoryHelper.verifyReservedBooksCount(1);
		//closeEntityManager();
	}
	
//	protected Long assureAddReservedBooks() throws Exception {
//		BookEntity reservedBooksEntity = createBookEntity();
//		Long id = assureAddReservedBooks(reservedBooksEntity);
//		return id;
//	}
//
//	protected Long assureAddReservedBooks(BookEntity reservedBooks) throws Exception {
//		openEntityManager();
//		control.beginTransaction();
//		Long id = fixture.addReservedBooks(reservedBooks);
//		control.commitTransaction();
//		verifyReservedBooksExists(id);
//		closeEntityManager();
//		return id;
//	}
//
//	protected void assureDeleteReservedBooks(Long id) throws Exception {
//		BookEntity reservedBooks = fixture.getReservedBooksById(id);
//		assureDeleteReservedBooks(reservedBooks);
//	}
//
//	protected void assureDeleteReservedBooks(BookEntity reservedBooks) throws Exception {
//		openEntityManager();
//		control.beginTransaction();
//		fixture.deleteReservedBooks(reservedBooks);
//		control.commitTransaction();
//		verifyReservedBooksNotExists(reservedBooks);
//		closeEntityManager();
//	}
//
//	protected void verifyReservedBooksCount(int expectedCount) throws Exception {
//		List<BookEntity> records = fixture.getAllReservedBooksRecords();
//		verifyReservedBooksCount(records, expectedCount);
//	}
//
//	protected void verifyReservedBooksCount(List<BookEntity> records, int expectedCount) throws Exception {
//		Assert.notNull(records, "Result not found");
//		if (expectedCount > 0)
//			Assert.notNull(records, "ReservedBooks records should exist");
//		Assert.equals(expectedCount, records.size(), "ReservedBooks record count not correct");
//	}
//
//	protected void verifyReservedBooksExists(BookEntity reservedBooks) throws Exception {
//		verifyReservedBooksExists(reservedBooks.getId());
//	}
//
//	protected void verifyReservedBooksExist(List<Long> ids) throws Exception {
//		Iterator<Long> iterator = ids.iterator();
//		while (iterator.hasNext()) {
//			Long id = iterator.next();
//			verifyReservedBooksExists(id);
//		}
//	}
//	
//	protected void verifyReservedBooksExists(Long id) throws Exception {
//		BookEntity reservedBooks = fixture.getReservedBooksById(id);
//		Assert.notNull(reservedBooks, "ReservedBooks should exist");
//		Assert.equals(reservedBooks.getId(), id, "Ids should be same");
//	}
//
//	protected void verifyReservedBooksNotExists(BookEntity reservedBooks) throws Exception {
//		verifyReservedBooksNotExists(reservedBooks.getId());
//	}
//
//	protected void verifyReservedBooksNotExists(Long id) throws Exception {
//		BookEntity reservedBooks = fixture.getReservedBooksById(id);
//		Assert.isNull(reservedBooks, "ReservedBooks should not exist");
//	}

}