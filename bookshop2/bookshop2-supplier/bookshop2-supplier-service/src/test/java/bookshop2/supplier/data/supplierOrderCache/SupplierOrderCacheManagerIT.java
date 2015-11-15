package bookshop2.supplier.data.supplierOrderCache;

import java.util.Map;

import org.aries.Assert;
import org.aries.runtime.RequestContext;
import org.aries.tx.AbstractIntegrationTest;
import org.aries.tx.BytemanHelper;
import org.aries.tx.CacheModuleTestControl;
import org.aries.tx.TransactionTestControl;
import org.aries.util.FieldUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.Book;
import bookshop2.BookKey;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Fixture;
import common.jmx.client.JmxManager;


@RunWith(MockitoJUnitRunner.class)
public class SupplierOrderCacheManagerIT extends AbstractIntegrationTest {

	private static TransactionTestControl transactionControl;

	private static CacheModuleTestControl supplierOrderCacheControl;

	private SupplierOrderCacheHelper supplierOrderCacheHelper;

	private SupplierOrderCacheManager supplierOrderCacheManager;

	private SupplierOrderCacheImpl supplierOrderCache;

	private SupplierContext supplierContext;
	
	
	@Rule 
	public BytemanHelper byteman = BytemanHelper.create(SupplierOrderCacheManagerIT.class);


//	@Override
//	protected String getFixtureName() {
//		return "SupplierOrderCacheManager";
//	}
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		createTransactionControl();
		createSupplierOrderCacheControl();
		AbstractIntegrationTest.beforeClass();
	}

	protected static void createTransactionControl() throws Exception {
		transactionControl = new TransactionTestControl();
		transactionControl.setupTransactionManager();
	}
	
	protected static void createSupplierOrderCacheControl() throws Exception {
		supplierOrderCacheControl = new CacheModuleTestControl(transactionControl);
		supplierOrderCacheControl.setupCacheLayer();
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		AbstractIntegrationTest.afterClass();
		supplierOrderCacheControl.tearDown();
		transactionControl.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		isLocal = true;
		super.setUp();
		createSupplierOrderCacheHelper();
		createSupplierOrderCacheManager();
	}

	@After
	public void tearDown() throws Exception {
		supplierOrderCacheManager = null;
		supplierOrderCache = null;
		supplierContext = null;
		super.tearDown();
	}
	
	protected void createSupplierOrderCacheHelper() throws Exception {
		supplierOrderCacheHelper = new SupplierOrderCacheHelper();
		supplierOrderCacheHelper.setProxy(createSupplierOrderCacheProxy());
		//supplierOrderCacheHelper.initializeAsClient(supplierOrderCacheControl);
	}

	protected SupplierOrderCacheProxy createSupplierOrderCacheProxy() throws Exception {
		SupplierOrderCacheProxy supplierOrderCacheProxy = new SupplierOrderCacheProxy();
		supplierOrderCacheProxy.setJmxManager(jmxManager);
		return supplierOrderCacheProxy;
	}
	
	public SupplierOrderCacheManager createSupplierOrderCacheManager() throws Exception {
		supplierOrderCacheManager = new SupplierOrderCacheManager();
		FieldUtil.setFieldValue(supplierOrderCacheManager, "supplierOrderCache", createSupplierOrderCache());
		FieldUtil.setFieldValue(supplierOrderCacheManager, "requestContext", createRequestContext());
		supplierOrderCacheManager.registerWithJMX();
		return supplierOrderCacheManager;
	}
	
	public SupplierOrderCache createSupplierOrderCache() throws Exception {
		supplierOrderCache = new SupplierOrderCacheImpl();
		supplierOrderCache.initialize();
		return supplierOrderCache;
	}
	
//	public SupplierContext createSupplierContext() throws Exception {
//		supplierContext = new SupplierContext();
//		return supplierContext;
//	}
	
	public RequestContext createRequestContext() {
		RequestContext requestContext = new RequestContext();
		return requestContext;
	}

	@Test
	public void testUpdate_Commit() throws Exception {
		try {
			//transactionControl.beginUserTransaction();
			transactionControl.beginTransaction();
			
			//Transactional transactional = transactionControl.createMockTransactional(supplierOrderCacheManager);
			//TransactionParticipantManager.getInstance().enroll(transactional);

			//printTransactionStatus();
			Map<BookKey, Book> expectedBooksInStock = Bookshop2Fixture.createMap_Book();
			supplierOrderCacheManager.addToBooksInStock(expectedBooksInStock);
			//supplierOrderCacheManager.updateState();

			Assert.isEmpty(supplierOrderCacheHelper.getAllBooksInStock());
			//transactionControl.commitUserTransaction();
			transactionControl.commitTransaction();
			transactionControl.clearTransactions();
			
			//printTransactionStatus();
			transactionControl.beginTransaction();
			Bookshop2Fixture.assertSameBook(expectedBooksInStock.values(), supplierOrderCacheHelper.getAllBooksInStock());
			transactionControl.commitTransaction();
			transactionControl.clearTransactions();
			//transactionControl.assertCommitted();

		} catch (Exception e) {
			e.printStackTrace();
			transactionControl.rollbackTransaction();
			fail(e.getMessage());
		}
	}

	@Test
	//@Ignore
	public void testUpdate_Rollback() throws Exception {
		try {
			//transactionControl.beginUserTransaction();
			transactionControl.beginTransaction();
			
			//String transactionId = "xxx";
			//TransactionContext transactionContext = new TransactionContextImpl();
			//TransactionContextManager.INSTANCE.start(transactionContext, transactionId);
			//Transactional transactional = transactionControl.createMockTransactional(supplierOrderCacheManager, transactionId);
			//TransactionParticipantManager.getInstance().enroll(transactional);
			
			Map<BookKey, Book> expectedBooksInStock = Bookshop2Fixture.createMap_Book();
			supplierOrderCacheManager.addToBooksInStock(expectedBooksInStock);
			//supplierOrderCacheManager.updateState();
			
			//transactionControl.rollbackUserTransaction();
			transactionControl.rollbackTransaction();
			transactionControl.clearTransactions();
			//transactionControl.assertRolledBack();

			//printTransactionStatus();
			transactionControl.beginTransaction();
			Assert.isEmpty(supplierOrderCacheHelper.getAllBooksInStock());
			transactionControl.commitTransaction();
			transactionControl.clearTransactions();
			
		} catch (Exception e) {
			e.printStackTrace();
			transactionControl.rollbackTransaction();
			fail(e.getMessage());
		}
	}

}
