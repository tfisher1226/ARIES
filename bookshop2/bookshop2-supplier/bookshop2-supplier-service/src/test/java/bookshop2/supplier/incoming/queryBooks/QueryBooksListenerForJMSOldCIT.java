package bookshop2.supplier.incoming.queryBooks;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.management.ObjectName;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
import org.aries.tx.BytemanUtil;
import org.aries.tx.TXManagerTestEARBuilder;
import org.aries.util.concurrent.FutureResult;
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
import bookshop2.BookKey;
import bookshop2.BooksAvailableMessage;
import bookshop2.BooksUnavailableMessage;
import bookshop2.Order;
import bookshop2.OrderRequestMessage;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.client.queryBooks.QueryBooks;
import bookshop2.supplier.client.queryBooks.QueryBooksProxyForJMS;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManagerMBean;
import bookshop2.util.Bookshop2Fixture;
import bookshop2.util.Bookshop2Helper;


@RunAsClient
@RunWith(Arquillian.class)
public class QueryBooksListenerForJMSOldCIT extends AbstractJMSListenerArquillionTest {
	
	private JmsClient queryBooksClient;
	
	private JmsClient booksUnavailableSender;
	
	private JmsClient booksUnavailableHandler;
	
	private JmsClient booksAvailableSender;
	
	private JmsClient booksAvailableHandler;
	
	private OrderRequestMessage orderRequestMessage;
	
	private MessageListener booksUnavailableListener;
	
	private MessageListener booksAvailableListener;
	
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		BytemanUtil.initialize();
		AbstractArquillianTest.beforeClass();
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		AbstractArquillianTest.afterClass();
	}
	
	@Override
	public Class<?> getTestClass() {
		return QueryBooksListenerForJMSOldCIT.class;
	}
	
	@Override
	public String getTargetArchive() {
		return SupplierTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return getJNDINameForQueue("inventory_bookshop2_supplier_query_books_queue");
	}
	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}
	
	@Override
	public String getDomainId() {
		return "bookshop2.supplier";
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		orderRequestMessage = createOrderRequestMessage();
		booksUnavailableListener = createBooksUnavailableResponseListener();
		booksAvailableListener = createBooksAvailableResponseListener();
		booksUnavailableSender = createClient("jms/queue/inventory_bookshop2_seller_books_unavailable_queue");
		booksAvailableSender = createClient("jms/queue/inventory_bookshop2_seller_books_available_queue");
	}
	
	@After
	public void tearDown() throws Exception {
		queryBooksClient.reset();
		queryBooksClient = null;
		booksUnavailableSender.reset();
		booksUnavailableHandler.reset();
		booksUnavailableSender = null;
		booksUnavailableHandler = null;
		booksUnavailableListener = null;
		booksAvailableSender.reset();
		booksAvailableHandler.reset();
		booksAvailableSender = null;
		booksAvailableHandler = null;
		booksAvailableListener = null;
		orderRequestMessage = null;
		super.tearDown();
	}
	
	@Deployment(name = "txManagerEAR", order = 1)
	@TargetsContainer("hornetQ01_local")
	public static EnterpriseArchive createTXManagerEAR() {
		TXManagerTestEARBuilder builder = new TXManagerTestEARBuilder();
		return builder.createEAR();
	}
	
	@Deployment(name = "supplierEAR", order = 2)
	@TargetsContainer("hornetQ01_local")
	public static EnterpriseArchive createTestEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	public OrderRequestMessage createOrderRequestMessage() {
		OrderRequestMessage inputMessage = Bookshop2Fixture.create_OrderRequestMessage();
		inputMessage.setCorrelationId(correlationId);
		inputMessage.setTransactionId(transactionId);
		return inputMessage;
	}
	
	@Test
	@InSequence(value = 1)
	public void testQueryBooks_BooksUnavailable() throws Exception {
		String testName = "testQueryBooks_BooksUnavailable";
		log.info(testName+": started");
		clearSupplierOrderCache();
		runTest(booksUnavailableListener);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	//@Transactional(TransactionMode.ROLLBACK)
	public void testQueryBooks_BooksAvailable_All() throws Exception {
		String testName = "testQueryBooks_BooksAvailable_All";
		log.info(testName+": started");
		clearSupplierOrderCache();
		addBooksToSupplierOrderCache();
		runTest(booksAvailableListener);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	//@Transactional(TransactionMode.ROLLBACK)
	public void testQueryBooks_BooksAvailable_Some() throws Exception {
		String testName = "testQueryBooks_BooksAvailable_Some";
		log.info(testName+": started");
		clearSupplierOrderCache();
		addBooksToSupplierOrderCache();
		runTest(booksAvailableListener);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest(MessageListener responseListener) throws Exception {
		String queueName = "jms/queue/inventory_bookshop2_supplier_query_books_queue";

		// Prepare fixture execution
		removeMessagesFromQueue("bookshop2-supplier.ear", queueName);

		// prepare context
		//beginTransaction();
		
		// prepare mocks
		FutureResult<Object> futureResult = registerForResult("bookshop2.supplier.queryBooks");

		// start fixture execution
		booksUnavailableHandler = startMockBooksUnavailableHandler(booksUnavailableListener);
		booksAvailableHandler = startMockBooksAvailableHandler(booksAvailableListener);
		queryBooksClient = startQueryBooksClient(queueName, responseListener);
		queryBooksClient.send(orderRequestMessage, correlationId, null);

		// wait for result
		Object result = waitForCompletion(futureResult);
		validateResult(result);
		
		// close context
		//commitTransaction();

		// validate fixture execution
		assertMessageCountForQueue("bookshop2-supplier.ear", queueName, 0L);
		
		// cleanup
		removeMessagesFromQueue("bookshop2-supplier.ear", queueName);
	}	
	
	protected JmsClient startQueryBooksClient(String destinationName, MessageListener messageListener) throws Exception {
		JmsClient client = new QueryBooksProxyForJMS(QueryBooks.ID);
		configureClient(client, destinationName);
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient startMockBooksUnavailableHandler(MessageListener responseListener) throws Exception {
		String destinationName = "jms/queue/protected_bookshop2_seller_books_unavailable_queue";
		JmsClient client = createClient(destinationName);
		client.initialize();
		return client;
	}
	
	protected JmsClient startMockBooksAvailableHandler(MessageListener responseListener) throws Exception {
		String destinationName = "jms/queue/protected_bookshop2_seller_books_available_queue";
		JmsClient client = createClient(destinationName);
		client.initialize();
		return client;
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
				}
			}
		};
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
				}
			}
		};
	}


	protected void clearSupplierOrderCache() throws Exception {
		String mbeanName = SupplierOrderCacheManagerMBean.MBEAN_NAME;
		clearSupplierOrderCache(mbeanName);
	}

	public void clearSupplierOrderCache(String mbeanName) throws Exception {
		ObjectName objectName = new ObjectName(mbeanName);
		clearSupplierOrderCache(objectName);
	}
	
	public void clearSupplierOrderCache(ObjectName objectName) throws Exception {
		jmxManager.invoke(objectName, "removeAllBooksInStock");
		//jmxManager.invoke(objectName, "commitState");
	}

	
	protected void addBooksToSupplierOrderCache() throws Exception {
		//Map<Object, Book> books = Bookshop2Fixture.createBook_Map();
		//List<Book> books = orderRequestMessage.getBooks();
		List<Book> books = Bookshop2Fixture.createList_Book();
		String mbeanName = SupplierOrderCacheManagerMBean.MBEAN_NAME;
		addBooksToSupplierOrderCache(mbeanName, Bookshop2Helper.createBookMap(books));
	}

	public void addBooksToSupplierOrderCache(String mbeanName, Map<BookKey, Book> books) throws Exception {
		ObjectName objectName = new ObjectName(mbeanName);
		addBooksToSupplierOrderCache(objectName, books);
	}
	
	public void addBooksToSupplierOrderCache(ObjectName objectName, Map<BookKey, Book> books) throws Exception {
		Object[] parameters = { books };
		String[] signature = { "java.util.Map" };
		jmxManager.invoke(objectName, "addToBooksInStock", parameters, signature);
		//jmxManager.invoke(objectName, "commitState");
	}

	protected void validateResult(Object result) {
		if (result instanceof BooksUnavailableMessage) {
			validate((BooksUnavailableMessage) result);
		} else if (result instanceof BooksAvailableMessage) {
			validate((BooksAvailableMessage) result);
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}
	
	protected void validate(BooksUnavailableMessage booksUnavailableMessage) {
		try {
			Assert.notNull(booksUnavailableMessage, "Message must be specified");
			Assert.notNull(booksUnavailableMessage.getBooks(), "Books not found");
			Order expectedOrder = orderRequestMessage.getOrder();
			Set<Book> expectedBooks = expectedOrder.getBooks();
			Set<Book> actualBooks = booksUnavailableMessage.getBooks();
			Bookshop2Fixture.assertBookCorrect(actualBooks);
			Bookshop2Fixture.assertSameBook(expectedBooks, actualBooks);
		} catch (Throwable e) {
			errorMessage = ExceptionUtils.getRootCauseMessage(e);
		}
	}
	
	protected void validate(BooksAvailableMessage booksAvailableMessage) {
		try {
			Assert.notNull(booksAvailableMessage, "Message must be specified");
			Assert.notNull(booksAvailableMessage.getBooks(), "Books not found");
			Order expectedOrder = orderRequestMessage.getOrder();
			Set<Book> expectedBooks = expectedOrder.getBooks();
			Set<Book> actualBooks = booksAvailableMessage.getBooks();
			Bookshop2Fixture.assertBookCorrect(actualBooks);
			Bookshop2Fixture.assertSameBook(expectedBooks, actualBooks);
		} catch (Throwable e) {
			errorMessage = ExceptionUtils.getRootCauseMessage(e);
		}
	}

	@Override
	public String getServiceId() {
		// TODO Auto-generated method stub
		return null;
	}

}
