package bookshop2.supplier.entity;

import static org.junit.Assert.fail;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.aries.Assert;
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

import bookshop2.supplier.data.bookOrderLog.BookOrderLogHelper;
import bookshop2.supplier.util.SupplierEntityFixture;


@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(NAME_ASCENDING)
public class BookOrdersEntityIT {

	private static TransactionTestControl transactionControl;
	
	private static DataLayerTestControl bookOrderLogControl;
	
	private BookOrderLogHelper bookOrderLogHelper;
	
	private BookOrdersEntity bookOrdersEntity;
	
	private EntityManager entityManager;


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
		createEntityManager();
		removeAll();
	}

	@After
	public void tearDown() throws Exception {
		entityManager.close();
		transactionControl.tearDown();
		bookOrderLogControl.tearDown();
		bookOrderLogHelper = null;
		bookOrdersEntity = null;
	}
	
	protected void createBookOrderLogHelper() throws Exception {
		bookOrderLogHelper = new BookOrderLogHelper();
		bookOrderLogHelper.initialize(bookOrderLogControl);
	}
	
	protected void createEntityManager() throws Exception {
		entityManager = bookOrderLogControl.createEntityManager();
	}
	
	protected BookOrdersEntity createBookOrdersEntity() {
		return bookOrderLogHelper.createBookOrdersEntity();
	}
	
	protected BookOrdersEntity createEmptyBookOrdersEntity() {
		return bookOrderLogHelper.createEmptyBookOrdersEntity();
	}

	@Test
	//@Ignore
	public void runTest_BookOrders_AddAsItem() throws Exception {
		bookOrdersEntity = createBookOrdersEntity();

		try {
			// execute
			removeAll();
			openTransaction();
			bookOrdersEntity = entityManager.merge(bookOrdersEntity);
			entityManager.flush();
			commitTransaction();

			// validate results
			Long id = bookOrdersEntity.getId();
			BookOrdersEntity entity = entityManager.find(BookOrdersEntity.class, id);
			Assert.equals(entity, bookOrdersEntity, "BookOrders records should be equal");
			
		} catch (Exception e) {
			Assert.exception(e, "Exception should not occur");
		
		} finally {
			commitTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_AddAsItem_constraint_PersonName_null() throws Exception {
		bookOrdersEntity = createBookOrdersEntity();
		bookOrdersEntity.setPersonName(null);
		
		try {
			openTransaction();
			entityManager.merge(bookOrdersEntity);
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "Column 'person_name_id' cannot be null");
			
		} finally {
			commitTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_AddAsItem_constraint_Count_null() throws Exception {
		bookOrdersEntity = createBookOrdersEntity();
		bookOrdersEntity.setCount(null);

		try {
			openTransaction();
			entityManager.merge(bookOrdersEntity);
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "Column 'count' cannot be null");
		
		} finally {
			commitTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_AddAsItem_exception_entityNotManaged() throws Exception {
		bookOrdersEntity = createBookOrdersEntity();

		try {
			openTransaction();
			entityManager.refresh(bookOrdersEntity);
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "Entity not managed");
		
		} finally {
			commitTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_AddAsItem_exception_detachedEntity() throws Exception {
		bookOrdersEntity = createBookOrdersEntity();

		try {
			openTransaction();
			entityManager.persist(bookOrdersEntity);
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "detached entity passed to persist");
		
		} finally {
			commitTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_SaveAsItem() throws Exception {
		// prepare environment
		bookOrdersEntity = createBookOrdersEntity();
		Long id = addBookOrdersRecord(bookOrdersEntity);
		BookOrdersEntity entity = getBookOrdersRecord(id);
		SupplierEntityFixture.assertSameBookOrdersEntity(bookOrdersEntity, entity);
		//Assert.equals(bookOrdersEntity, entity, "BookOrders records should be equal");

		try {
			// modify record
			entity.setCount(111);
			entity.setDateTime(new Date(111L));

			// save record
			openTransaction();
			bookOrdersEntity = entityManager.merge(entity);
			entityManager.flush();
			
		} finally {
			commitTransaction();
		}
		
		// validate results
		BookOrdersEntity entity2 = getBookOrdersRecord(id);
		Assert.equals(entity, entity2, "BookOrders records should be equal");
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_SaveAsItem_constraint_PersonName_null() throws Exception {
		// prepare environment 
		bookOrdersEntity = createBookOrdersEntity();
		Long id = addBookOrdersRecord(bookOrdersEntity);
		
		try {
			// lookup record
			BookOrdersEntity entity = getBookOrdersRecord(id);
			SupplierEntityFixture.assertSameBookOrdersEntity(bookOrdersEntity, entity);
		
			// modify record
			entity.setPersonName(null);
			
			// save record
			openTransaction();
			entityManager.merge(entity);
			entityManager.flush();
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "Column 'person_name_id' cannot be null");
			
		} finally {
			rollbackTransaction();
		}
		
		// validate results
		BookOrdersEntity entity = getBookOrdersRecord(id);
		Assert.notNull(entity.getPersonName(), "BookOrders PersonName should exist");
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_SaveAsItem_constraint_Count_null() throws Exception {
		// prepare environment 
		bookOrdersEntity = createBookOrdersEntity();
		Long id = addBookOrdersRecord(bookOrdersEntity);
		
		try {
			// lookup record
			BookOrdersEntity entity = getBookOrdersRecord(id);
			SupplierEntityFixture.assertSameBookOrdersEntity(bookOrdersEntity, entity);

			// modify record
			entity.setCount(null);
			
			// save record
			openTransaction();
			entityManager.merge(entity);
			entityManager.flush();
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "Column 'count' cannot be null");
			
		} finally {
			rollbackTransaction();
		}
		
		// validate results
		BookOrdersEntity entity = getBookOrdersRecord(id);
		Assert.notNull(entity.getCount(), "BookOrders Count should exist");
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_SaveAsItem_constraint_books_notNull_orphanRemoval() throws Exception {
		// prepare environment 
		bookOrdersEntity = createBookOrdersEntity();
		Long id = addBookOrdersRecord(bookOrdersEntity);
		BookOrdersEntity entity = getBookOrdersRecord(id);
		SupplierEntityFixture.assertSameBookOrdersEntity(bookOrdersEntity, entity);
		
		try {
			// modify and save record
			entity.setCount(111);
			entity.setDateTime(new Date(111L));
			openTransaction();
			bookOrdersEntity = entityManager.merge(entity);
			entityManager.flush();
			commitTransaction();
			
		} finally {
			commitTransaction();
		}
		
		// validate results
		BookOrdersEntity entity2 = getBookOrdersRecord(id);
		Assert.equals(entity, entity2, "BookOrders records should be equal");
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_SaveAsItem_exception_entityNotManaged() throws Exception {
		// prepare environment 
		bookOrdersEntity = createBookOrdersEntity();
		addBookOrdersRecord(bookOrdersEntity);
		entityManager.clear();
		
		try {
			// execute action
			openTransaction();
			entityManager.refresh(bookOrdersEntity);
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "Entity not managed");
			
		} finally {
			rollbackTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_SaveAsItem_exception_detachedEntity() throws Exception {
		// prepare environment 
		bookOrdersEntity = createBookOrdersEntity();
		Long id = addBookOrdersRecord(bookOrdersEntity);
		entityManager.detach(bookOrdersEntity);
		
		try {
			// execute action
			openTransaction();
			entityManager.persist(bookOrdersEntity);
			fail("Exception should have occured");
			
		} catch (Exception e) {
			Assert.exception(e, "detached entity passed to persist");
			
		} finally {
			rollbackTransaction();
		}
	}

	@Test
	//@Ignore
	public void runTest_BookOrders_RemoveAsItem() throws Exception {
		// prepare environment 
		openTransaction();
		bookOrdersEntity = createBookOrdersEntity();
		bookOrdersEntity = entityManager.merge(bookOrdersEntity);
		entityManager.persist(bookOrdersEntity);
		commitTransaction();

		try {
			// lookup record
			Long id = bookOrdersEntity.getId();
			BookOrdersEntity entity = entityManager.find(BookOrdersEntity.class, id);
			SupplierEntityFixture.assertSameBookOrdersEntity(bookOrdersEntity, entity);
			
			// remove record
			openTransaction();
			entityManager.remove(bookOrdersEntity);
			entityManager.flush();
			commitTransaction();

			// verify results
			entity = entityManager.find(BookOrdersEntity.class, id);
			Assert.isNull(entity, "BookOrders record should be removed");
			Assert.isTrue(getAllBookOrdersRecords().size() == 0, "No BookOrders record should exist");
			Assert.isTrue(getAllBookOrdersBookRecords().size() == 0, "No BookOrdersBook record should exist");
			Assert.isTrue(getAllPersonNameRecords().size() == 0, "No PersonName record should exist");
			
		} catch (Exception e) {
			fail("Exception should not occur: "+e.getMessage());
		
		} finally {
			commitTransaction();
		}
	}
	
	@Test
	//@Ignore
	public void runTest_BookOrders_RemoveAsList() throws Exception {
		// prepare environment 
		//openTransaction();
		List<BookOrdersEntity> entityList = bookOrderLogHelper.createBookOrdersEntityList();
		List<Long> idList = bookOrderLogHelper.addBookOrdersRecords(entityList);
		//commitTransaction();
		
		try {
			// lookup records
			entityList = bookOrderLogHelper.getBookOrdersRecordsByIds(idList);
			Assert.notNull(entityList, "BookOrders record should exist");
			Assert.equals(entityList.size(), idList.size(), "BookOrders record count should be correct");
			
			// remove records
			openTransaction();
			Iterator<BookOrdersEntity> iterator = entityList.iterator();
			while (iterator.hasNext()) {
				BookOrdersEntity entity = iterator.next();
				entity = entityManager.merge(entity);
				entityManager.remove(entity);
			}
			entityManager.flush();
			commitTransaction();
			
			// verify results
			entityList = bookOrderLogHelper.getBookOrdersRecordsByIds(idList);
			Assert.isEmpty(entityList, "BookOrders records should be removed");
			Assert.isTrue(getAllBookOrdersRecords().size() == 0, "No BookOrders record should exist");
			Assert.isTrue(getAllBookOrdersBookRecords().size() == 0, "No BookOrdersBook record should exist");
			Assert.isTrue(getAllPersonNameRecords().size() == 0, "No PersonName record should exist");
			
		} catch (Exception e) {
			rollbackTransaction();
			fail("Exception should not occur: "+e.getMessage());
		}
	}
	

	
	protected Collection<BookOrdersEntity> getAllBookOrdersRecords() {
		return getAllRecords("BookOrders");
	}
	
	protected Collection<BookOrdersBookEntity> getAllBookOrdersBookRecords() {
		return getAllRecords("BookOrdersBook");
	}
	
	protected Collection<BookOrdersEntity> getAllPersonNameRecords() {
		return getAllRecords("PersonName");
	}
	
	protected <T> Collection<T> getAllRecords(String tableName) {
		Query query = entityManager.createQuery("select x FROM "+tableName+" x");
		@SuppressWarnings("unchecked") Collection<T> resultList = (Collection<T>) query.getResultList();
		return resultList;
	}
	
	protected BookOrdersEntity getBookOrdersRecord(Long id) {
		//BookOrdersEntity entity = entityManager.find(BookOrdersEntity.class, id, LockModeType.NONE);
		BookOrdersEntity entity = entityManager.find(BookOrdersEntity.class, id);
		return entity;
	}

	protected void openTransaction() throws Exception {
		transactionControl.beginTransaction();
		entityManager.joinTransaction();
	}

	protected void commitTransaction() throws Exception {
		transactionControl.commitTransaction();
		transactionControl.clearTransactions();
	}
	
	protected void rollbackTransaction() throws Exception {
		transactionControl.rollbackTransaction();
		transactionControl.clearTransactions();
	}

	protected void clearTransaction() throws Exception {
		transactionControl.clearTransactions();
	}
	
	protected Long addBookOrdersRecord(BookOrdersEntity entity) throws Exception {
		try {
			entity = saveBookOrdersRecord(entity);
			entityManager.refresh(entity);
			Long id = entity.getId();
			return id;
		} finally {
			commitTransaction();
		}
	}

	protected BookOrdersEntity saveBookOrdersRecord(BookOrdersEntity entity) throws Exception {
		try {
			openTransaction();
			entity = entityManager.merge(entity);
			//entityManager.refresh(entity);
			entityManager.flush();
			return entity;
		} finally {
			commitTransaction();
		}
	}

	protected void removeAll() throws Exception {
		try {
			openTransaction();
			
			//Query query = entityManager.createNamedQuery("removeAllBookOrdersRecords");
			//query.executeUpdate();
			
			Query query = entityManager.createNamedQuery("getAllBookOrdersRecords");
			@SuppressWarnings("unchecked") List<BookOrdersEntity> records = query.getResultList();
			
			Iterator<BookOrdersEntity> iterator = records.iterator();
			while (iterator.hasNext()) {
				BookOrdersEntity bookOrdersEntity = iterator.next();
				//bookOrdersEntity = entityManager.merge(bookOrdersEntity);
				entityManager.remove(bookOrdersEntity);
			}
			entityManager.flush();
			
		} finally {
			commitTransaction();
		}
	}
	
}
