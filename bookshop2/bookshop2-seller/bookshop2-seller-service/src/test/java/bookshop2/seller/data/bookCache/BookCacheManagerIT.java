package bookshop2.seller.data.bookCache;

import java.util.List;

import org.aries.Assert;
import org.aries.common.AbstractTestCase;
import org.aries.tx.BytemanHelper;
import org.aries.tx.BytemanUtil;
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
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class BookCacheManagerIT extends AbstractTestCase {

	private static TransactionTestControl transactionControl;

	private static CacheModuleTestControl bookCacheControl;

	private BookCacheHelper bookCacheHelper;

	private BookCacheManager bookCacheManager;

	private BookCacheImpl bookCache;
	
	private SellerContext sellerContext;
	
	@Rule 
	public BytemanHelper byteman = BytemanHelper.create(BookCacheManagerIT.class);
	

//	@Override
//	protected String getFixtureName() {
//		return "BookCacheManager";
//	}
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		BytemanUtil.initialize();
		createTransactionControl();
		createBookCacheControl();
	}

	protected static void createTransactionControl() throws Exception {
		transactionControl = new TransactionTestControl();
		transactionControl.setupTransactionManager();
	}
	
	protected static void createBookCacheControl() throws Exception {
		bookCacheControl = new CacheModuleTestControl(transactionControl);
		bookCacheControl.setupCacheLayer();
	}
	
	@AfterClass
	public static void afterCLass() throws Exception {
		transactionControl.tearDown();
		bookCacheControl.tearDown();
	}

	@Before
	public void setUp() throws Exception {
		//super.setUp();
		createBookCacheManager();
	}

	@After
	public void tearDown() throws Exception {
		bookCacheManager = null;
		bookCache = null;
		sellerContext = null;
		//super.tearDown();
	}
	
	public BookCacheManager createBookCacheManager() throws Exception {
		bookCacheManager = new BookCacheManager();
		FieldUtil.setFieldValue(bookCacheManager, "bookCache", createBookCache());
		FieldUtil.setFieldValue(bookCacheManager, "sellerContext", createSellerContext());
		return bookCacheManager;
	}
	
	public BookCache createBookCache() throws Exception {
		bookCache = new BookCacheImpl();
		bookCache.initialize();
		return bookCache;
	}
	
	public SellerContext createSellerContext() throws Exception {
		sellerContext = new SellerContext();
		return sellerContext;
	}
	
	@Test
	public void testUpdate_Commit() throws Exception {
		try {
			//transactionControl.beginUserTransaction();
			transactionControl.beginTransaction();
			
			//Transactional transactional = transactionControl.createMockTransactional(bookCacheManager);
			//TransactionParticipantManager.getInstance().enroll(transactional);

			//printTransactionStatus();
			List<Book> expectedBookOrders = Bookshop2Fixture.createList_Book();
			bookCacheManager.addToBookOrders(expectedBookOrders);
			//bookCacheManager.updateState();

			Assert.isEmpty(bookCacheManager.getAllBookOrders());
			//transactionControl.commitUserTransaction();
			transactionControl.commitTransaction();
			transactionControl.clearTransactions();
			
			//printTransactionStatus();
			transactionControl.beginTransaction();
			Bookshop2Fixture.assertSameBook(expectedBookOrders, bookCacheManager.getAllBookOrders());
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
			
			//Transactional transactional = transactionControl.createMockTransactional(bookCacheManager);
			//TransactionParticipantManager.getInstance().enroll(transactional);

			List<Book> expectedBookOrders = Bookshop2Fixture.createList_Book();
			bookCacheManager.addToBookOrders(expectedBookOrders);
			//bookCacheManager.updateState();

			//transactionControl.rollbackUserTransaction();
			transactionControl.rollbackTransaction();
			transactionControl.clearTransactions();
			//transactionControl.assertRolledBack();

			//printTransactionStatus();
			transactionControl.beginTransaction();
			Assert.isEmpty(bookCacheManager.getAllBookOrders());
			transactionControl.commitTransaction();
			transactionControl.clearTransactions();
			
		} catch (Exception e) {
			e.printStackTrace();
			transactionControl.rollbackTransaction();
			fail(e.getMessage());
		}
	}

}
