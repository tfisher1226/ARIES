package bookshop2.supplier;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSServiceSideArquillionTest;
import org.aries.tx.CacheModuleTestControl;
import org.aries.tx.ServiceModuleTestControl;
import org.aries.tx.TransactionTestControl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import bookshop2.Book;
import bookshop2.BooksAvailableMessage;
import bookshop2.BooksUnavailableMessage;
import bookshop2.QueryRequestMessage;
import bookshop2.supplier.data.bookInventory.BookInventoryHelper;
import bookshop2.supplier.data.bookInventory.BookInventoryManager;
import bookshop2.supplier.data.bookOrderLog.BookOrderLogHelper;
import bookshop2.supplier.data.bookOrderLog.BookOrderLogManager;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheHelper;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManager;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheProxy;
import bookshop2.supplier.outgoing.booksAvailable.BooksAvailableReply;
import bookshop2.supplier.outgoing.booksUnavailable.BooksUnavailableReply;
import bookshop2.util.Bookshop2Fixture;

import common.tx.state.AbstractStateManager;


@RunWith(Arquillian.class)
public class SupplierProcessCIT extends AbstractJMSServiceSideArquillionTest {
	
	protected ServiceModuleTestControl bookInventoryControl;

	protected ServiceModuleTestControl bookOrderLogControl;

	protected CacheModuleTestControl supplierOrderCacheControl;

	@Inject
	private BookInventoryManager bookInventoryManager;
	
	@Inject
	private BookInventoryHelper bookInventoryHelper;
	
	@Inject
	private BookOrderLogManager bookOrderLogManager;
	
	@Inject
	private BookOrderLogHelper bookOrderLogHelper;
	
	@Inject
	private SupplierOrderCacheManager supplierOrderCacheManager;
	
	@Inject
	private SupplierOrderCacheHelper supplierOrderCacheHelper;
	
	private SupplierOrderCacheProxy supplierOrderCacheProxy;

	@Inject
	private SupplierProcess supplierProcess;
	
	private JmsClient booksUnavailableSender;
	
	private JmsClient booksUnavailableHandler;
	
	private JmsClient booksAvailableSender;
	
	private JmsClient booksAvailableHandler;
	
	private QueryRequestMessage queryRequestMessage;

	private boolean booksAvailableReceived;

	private boolean booksUnavailableReceived;

	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}

	@Override
	public String getDomainId() {
		return "bookshop2.supplier";
	}

//	@Override
//	public String getServiceId() {
//		return "bookshop2.supplier.queryBooks";
//	}

	@Override
	public AbstractStateManager<?> getStateManager() {
		return supplierOrderCacheManager;
	}
	
	@Override
	public Class<?> getTestClass() {
		return SupplierProcessCIT.class;
	}
	
	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
	}

//	@Override
//	public String getTargetDestination() {
//		return getDestinationName("inventory_bookshop2_supplier_query_books_queue");
//	}
	
	public String getLocalBooksAvailableDestination() {
		return "test_message_domain_test_destination_queue_a";
	}
	
	public String getLocalBooksUnavailableDestination() {
		return "test_message_domain_test_destination_queue_b";
	}
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		AbstractArquillianTest.beforeClass();
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		AbstractArquillianTest.afterClass();
	}
	
	@Before
	public void setUp() throws Exception {
		if (!isServerStarted()) {
			startServer();
		} else {
			super.setUp();
			createTransactionControl();
			createBookInventoryControl();
			createBookInventoryHelper();
			createBookOrderLogControl();
			createBookOrderLogHelper();
			createSupplierOrderCacheControl();
			createSupplierOrderCacheHelper();
			createQueryRequestMessage();
			booksAvailableSender = createClient(getJNDINameForQueue(getLocalBooksAvailableDestination()));
			booksUnavailableSender = createClient(getJNDINameForQueue(getLocalBooksUnavailableDestination()));
		}
	}
	
	@After
	public void tearDown() throws Exception {
		booksUnavailableSender.reset();
		booksUnavailableHandler.reset();
		booksUnavailableSender = null;
		booksUnavailableHandler = null;
		booksAvailableSender.reset();
		booksAvailableHandler.reset();
		booksAvailableSender = null;
		booksAvailableHandler = null;
		queryRequestMessage = null;
		booksAvailableReceived = false;
		booksUnavailableReceived = false;
		transactionTestControl.tearDown();
		super.tearDown();
	}
	
//	@Deployment(name = "txManagerEAR", order = 1)
//	@TargetsContainer("hornetQ01_local")
//	public static EnterpriseArchive createTXManagerEAR() {
//		TXManagerTestEARBuilder builder = new TXManagerTestEARBuilder();
//		return builder.createEAR();
//	}
	
	@Deployment(name = "supplierEAR", order = 2)
	@TargetsContainer("hornetQ01_local")
	public static EnterpriseArchive createSupplierEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.addAsModule(createTestWAR(), "supplier");
		return builder.createEAR();
	}
	
	public static WebArchive createTestWAR() {
		SupplierTestWARBuilder builder = new SupplierTestWARBuilder("test.war");
		builder.addClass(SupplierProcessCIT.class);
		return builder.create();
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	protected void createBookInventoryControl() throws Exception {
		bookInventoryControl = new ServiceModuleTestControl(transactionTestControl);
		//bookInventoryTestControl.setDatabaseName("bookshop2_supplier_db");
		//bookInventoryTestControl.setDataSourceName("bookshop2_supplier");
		//bookInventoryTestControl.setPersistenceUnitName("bookInventory");
		//bookInventoryTestControl.setupDataLayer();
	}
	
	protected void createBookOrderLogControl() throws Exception {
		bookOrderLogControl = new ServiceModuleTestControl(transactionTestControl);
		//bookOrderLogTestControl.setDatabaseName("bookshop2_supplier_db");
		//bookOrderLogTestControl.setDataSourceName("bookshop2_supplier");
		//bookOrderLogTestControl.setPersistenceUnitName("bookOrderLog");
		//bookOrderLogTestControl.setupDataLayer();
	}
	
	protected void createSupplierOrderCacheControl() throws Exception {
		supplierOrderCacheControl = new CacheModuleTestControl(transactionTestControl);
		supplierOrderCacheControl.setupCacheLayer();
	}

	protected void createBookInventoryHelper() throws Exception {
		//bookInventoryHelper = new BookInventoryHelper();
		bookInventoryHelper.initialize(bookInventoryControl);
	}
	
	protected void createBookOrderLogHelper() throws Exception {
		//bookOrderLogHelper = new BookOrderLogHelper();
		bookOrderLogHelper.initialize(bookInventoryControl);
	}

	public void createSupplierOrderCacheHelper() throws Exception {
		//supplierOrderCacheHelper = new SupplierOrderCacheHelper();
		supplierOrderCacheHelper.setProxy(createSupplierOrderCacheProxy());
		supplierOrderCacheHelper.initialize(supplierOrderCacheControl);
	}
	
	public SupplierOrderCacheProxy createSupplierOrderCacheProxy() throws Exception {
		supplierOrderCacheProxy = new SupplierOrderCacheProxy();
		supplierOrderCacheProxy.setJmxManager(jmxManager);
		return supplierOrderCacheProxy;
	}
	
	public QueryRequestMessage createQueryRequestMessage() {
		return createQueryRequestMessage(false, false);
	}
	
	public QueryRequestMessage createQueryRequestCancelMessage() {
		return createQueryRequestMessage(true, false);
	}
	
	public QueryRequestMessage createQueryRequestUndoMessage() {
		return createQueryRequestMessage(false, true);
	}
	
	public QueryRequestMessage createQueryRequestMessage(boolean cancel, boolean undo) {
		queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage(cancel, undo);
		queryRequestMessage.addToReplyToDestinations("BooksAvailable", getLocalBooksAvailableDestination());
		queryRequestMessage.addToReplyToDestinations("BooksUnavailable", getLocalBooksUnavailableDestination());
		return initializeMessage(queryRequestMessage);
	}
	
	public QueryRequestMessage createEmptyQueryRequestMessage() {
		QueryRequestMessage message = Bookshop2Fixture.createEmpty_QueryRequestMessage();
		return initializeMessage(queryRequestMessage);
	}
	
	@Test
	@InSequence(value = 1)
	public void testQueryBooks_EmptyRequest_BooksInStock_Size_EQ_0__BooksUnavailable() throws Exception {
		String testName = "testQueryBooks_EmptyRequest_BooksInStock_Size_EQ_0__BooksUnavailable";
		log.info(testName+": started");
		supplierOrderCacheHelper.clearSupplierOrderCache();
		queryRequestMessage = createQueryRequestMessage();
		runTest_handleRequestQueryBooks();
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@InSequence(value = 2)
	public void testQueryBooks_EmptyRequest_BooksInStock_Size_EQ_0__BooksUnavailable2() throws Exception {
		String testName = "testQueryBooks_EmptyRequest_BooksInStock_Size_EQ_0__BooksUnavailable2";
		log.info(testName+": started");
		supplierOrderCacheHelper.clearSupplierOrderCache();
		List<Book> books = Bookshop2Fixture.createList_Book(2, 2);
		supplierOrderCacheHelper.addBookListToSupplierOrderCache(books);
		queryRequestMessage = createQueryRequestMessage();
		runTest_handleRequestQueryBooks();
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

	@Test
	@InSequence(value = 3)
	public void testQueryBooks_BooksInStock_Size_GT_0__BooksAvailable() throws Exception {
		String testName = "testQueryBooks_BooksInStock_Size_GT_0__BooksAvailable";
		log.info(testName+": started");
		Set<Book> books = Bookshop2Fixture.createSet_Book();
		supplierOrderCacheHelper.clearSupplierOrderCache();
		supplierOrderCacheHelper.addBookListToSupplierOrderCache(books);
		queryRequestMessage = createQueryRequestMessage();
		queryRequestMessage.setBooks(books);
		runTest_handleRequestQueryBooks();
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}

	public void runTest_handleRequestQueryBooks() throws Exception {
		// prepare context
		//beginTransaction();
		
		// start fixture execution
		booksUnavailableHandler = startBooksUnavailableHandler();
		booksAvailableHandler = startBooksAvailableHandler();
		supplierProcess.handle_QueryBooks_request(queryRequestMessage);
		
		// close context
		//commitTransaction();
	}
	
	protected JmsClient startBooksAvailableHandler() throws Exception {
		String destinationName = getJNDINameForQueue(getLocalBooksAvailableDestination());
		MessageListener responseListener = createBooksAvailableResponseListener();
		JmsClient client = createMockServiceListener(destinationName, responseListener);
		client.initialize();
		return client;
	}
	
	protected JmsClient startBooksUnavailableHandler() throws Exception {
		String destinationName = getJNDINameForQueue(getLocalBooksUnavailableDestination());
		MessageListener responseListener = createBooksUnavailableResponseListener();
		JmsClient client = createMockServiceListener(destinationName, responseListener);
		client.initialize();
		return client;
	}
	
	protected MessageListener createBooksAvailableResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof BooksAvailableMessage, "Payload type not correct");
					BooksAvailableMessage booksAvailableMessage = (BooksAvailableMessage) object;
					validate(booksAvailableMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.BooksAvailable"))
						expectedMessageResult.set(true);
					booksAvailableReceived = true;
				}
			}
		};
	}

	protected MessageListener createBooksUnavailableResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof BooksUnavailableMessage, "Payload type not correct");
					BooksUnavailableMessage booksUnavailableMessage = (BooksUnavailableMessage) object;
					validate(booksUnavailableMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.supplier.BooksUnavailable"))
						expectedMessageResult.set(true);
					booksUnavailableReceived = true;
				}
			}
		};
	}
	
	protected void validateResult(Object result) {
		if (result instanceof BooksUnavailableMessage) {
			validate((BooksUnavailableMessage) result);
		} else if (result instanceof BooksAvailableMessage) {
			validate((BooksAvailableMessage) result);
		} else if (result instanceof String) {
			String resultString = result.toString();
			if ((expectedError != null && !expectedError.equalsIgnoreCase(resultString)) &&
				(expectedEvent != null && !expectedEvent.equalsIgnoreCase(resultString)))
					errorMessage = "Unexpected message: "+result;
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}
	
	protected void validate(BooksAvailableMessage booksAvailableMessage) {
		Assert.notNull(booksAvailableMessage, "Message must be specified");
		Assert.notNull(booksAvailableMessage.getBooks(), "Books not found");
		Set<Book> expectedBooks = queryRequestMessage.getBooks();
		Set<Book> actualBooks = booksAvailableMessage.getBooks();
		Bookshop2Fixture.assertSameBook(expectedBooks, actualBooks);
	}

	protected void validate(BooksUnavailableMessage booksUnavailableMessage) {
		Assert.notNull(booksUnavailableMessage, "Message must be specified");
		Assert.notNull(booksUnavailableMessage.getBooks(), "Books not found");
		Set<Book> expectedBooks = queryRequestMessage.getBooks();
		Set<Book> actualBooks = booksUnavailableMessage.getBooks();
		Bookshop2Fixture.assertSameBook(expectedBooks, actualBooks);
	}
	
}
