package bookshop2.supplier.incoming.queryBooks;

import java.util.Collection;
import java.util.Set;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
import org.aries.tx.CacheModuleTestControl;
import org.aries.tx.TransactionTestControl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
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
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.client.queryBooks.QueryBooks;
import bookshop2.supplier.client.queryBooks.QueryBooksProxyForJMS;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheHelper;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheProxy;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class QueryBooksHandlerCIT extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private CacheModuleTestControl supplierOrderCacheControl;

	private SupplierOrderCacheHelper supplierOrderCacheHelper;

	private SupplierOrderCacheProxy supplierOrderCacheProxy;
	
	private JmsClient queryBooksClient;
	
	//private JmsClient booksAvailableSender;
	
	private JmsClient booksAvailableHandler;
	
	//private JmsClient booksUnavailableSender;
	
	private JmsClient booksUnavailableHandler;
	
	private QueryRequestMessage queryRequestMessage;
	
	protected boolean booksUnavailableReceived;
	
	protected boolean booksAvailableReceived;
	
	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}

	@Override
	public String getDomainId() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getServiceId() {
		return "Supplier_QueryBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_query_books_queue");
	}
	
	public String getLocalBooksAvailableDestination() {
		return "test_message_domain_test_destination_queue_a";
	}
	
	public String getLocalBooksUnavailableDestination() {
		return "test_message_domain_test_destination_queue_b";
	}

	@Override
	public Class<?> getTestClass() {
		return QueryBooksHandlerCIT.class;
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
		super.setUp();
		startServer();
		createTransactionControl();
		createSupplierOrderCacheControl();
		createSupplierOrderCacheHelper();
		queryRequestMessage = createQueryRequestMessage();
		//booksUnavailableSender = createClient(getBooksUnavailableDestination());
		//booksAvailableSender = createClient(getBooksAvailableDestination());
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	protected void createSupplierOrderCacheControl() throws Exception {
		supplierOrderCacheControl = new CacheModuleTestControl(transactionTestControl);
		supplierOrderCacheControl.setupCacheLayer();
	}
	
	@After
	public void tearDown() throws Exception {
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		queryBooksClient.reset();
		queryBooksClient = null;
		booksAvailableHandler.reset();
		booksAvailableHandler = null;
		booksUnavailableHandler.reset();
		booksUnavailableHandler = null;
		queryRequestMessage = null;
	}

	protected void clearState() throws Exception {
		booksUnavailableReceived = false;
		booksAvailableReceived = false;
		super.clearState();
	}
	
//	@TargetsContainer("hornetQ01_local")
//	@Deployment(name = "txManagerEAR", order = 1)
//	public static EnterpriseArchive createTXManagerEAR() {
//		TXManagerTestEARBuilder builder = new TXManagerTestEARBuilder();
//		return builder.createEAR();
//	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "supplierEAR", order = 2)
	public static EnterpriseArchive createSupplierEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	public void createSupplierOrderCacheHelper() throws Exception {
		supplierOrderCacheHelper = new SupplierOrderCacheHelper();
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
	
	@Test
	@InSequence(value = 1)
	public void testQueryBooks_BooksAvailable_BooksInStock_Size_GT__0() throws Exception {
		String testName = "testQueryBooks_BooksAvailable_BooksInStock_Size_GT__0";
		log.info(testName+": started");
		registerNotificationListeners();
		Collection<Book> books = Bookshop2Fixture.createSet_Book();
		supplierOrderCacheHelper.clearSupplierOrderCache();
		supplierOrderCacheHelper.addBookListToSupplierOrderCache(books);
		queryRequestMessage.setBooks(books);

		//execution started
		runTest_queryBooks();
		//execution finished
		Assert.isTrue(booksAvailableReceived);
		Assert.isTrue(isFiredRequestDone("Supplier_QueryBooks"));
		Assert.isTrue(isFiredResponseSent("Supplier_BooksAvailable"));
		Assert.isFalse(isFiredResponseSent("Supplier_BooksUnavailable"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	@InSequence(value = 2)
	public void testQueryBooks_BooksUnavailable_BooksInStock_Size_EQ__0() throws Exception {
		String testName = "testQueryBooks_BooksUnavailable_BooksInStock_Size_EQ__0";
		log.info(testName+": started");
		registerNotificationListeners();
		supplierOrderCacheHelper.clearSupplierOrderCache();
		//execution started
		runTest_queryBooks();
		//execution finished
		Assert.isTrue(booksUnavailableReceived);
		Assert.isTrue(isFiredRequestDone("Supplier_QueryBooks"));
		Assert.isFalse(isFiredResponseSent("Supplier_BooksAvailable"));
		Assert.isTrue(isFiredResponseSent("Supplier_BooksUnavailable"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_queryBooks() throws Exception {
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// prepare response handlers
		booksAvailableHandler = startBooksAvailableHandler();
		booksUnavailableHandler = startBooksUnavailableHandler();
		queryBooksClient = startQueryBooksClient();
		sendRequest_QueryBooks();
		
		// wait for result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();
		
		// validate the environment
		assertEmptyTargetDestination();
		removeMessagesFromDestinations();
	}
	
	protected void sendRequest_QueryBooks() throws Exception {
		queryBooksClient.send(queryRequestMessage, correlationId, null);
	}

	protected void sendRequest_QueryBooks_Cancel() throws Exception {
		queryRequestMessage = createQueryRequestCancelMessage();
		sendRequest_QueryBooks();
	}

	protected void sendRequest_QueryBooks_Undo() throws Exception {
		queryRequestMessage = createQueryRequestUndoMessage();
		sendRequest_QueryBooks();
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("Supplier_QueryBooks");
		addResponseNotificationListeners("Supplier_BooksAvailable");
		addResponseNotificationListeners("Supplier_BooksUnavailable");
		super.registerNotificationListeners();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalBooksAvailableDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalBooksUnavailableDestination());
	}
	
	protected JmsClient startQueryBooksClient() throws Exception {
		JmsClient client = new QueryBooksProxyForJMS(QueryBooks.ID);
		configureClient(client, getTargetDestination());
		//client.setMessageListener(responseListener);
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient startBooksAvailableHandler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalBooksAvailableDestination()));
		client.setMessageListener(createBooksAvailableResponseListener());
		client.initialize();
		return client;
	}
	
	protected JmsClient startBooksUnavailableHandler() throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalBooksUnavailableDestination()));
		client.setMessageListener(createBooksUnavailableResponseListener());
		client.initialize();
		return client;
	}
	
	protected MessageListener createBooksAvailableResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: BooksAvailable received");
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
					log.info("#### [test]: BooksUnavailable received");
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
	
	protected void validate(BooksUnavailableMessage booksUnavailableMessage) {
		Assert.notNull(booksUnavailableMessage, "Message must be specified");
		Assert.notNull(booksUnavailableMessage.getBooks(), "Books not found");
		Set<Book> expectedBooks = queryRequestMessage.getBooks();
		Set<Book> actualBooks = booksUnavailableMessage.getBooks();
		Bookshop2Fixture.assertBookCorrect(actualBooks);
		Bookshop2Fixture.assertSameBook(expectedBooks, actualBooks);
	}
	
	protected void validate(BooksAvailableMessage booksAvailableMessage) {
		Assert.notNull(booksAvailableMessage, "Message must be specified");
		Assert.notNull(booksAvailableMessage.getBooks(), "Books not found");
		Set<Book> expectedBooks = queryRequestMessage.getBooks();
		Set<Book> actualBooks = booksAvailableMessage.getBooks();
		Bookshop2Fixture.assertBookCorrect(actualBooks);
		Bookshop2Fixture.assertSameBook(expectedBooks, actualBooks);
	}

}
