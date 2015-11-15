package bookshop2;

import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.management.ObjectName;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.client.JmsClient;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
import org.aries.util.concurrent.FutureResult;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import bookshop2.buyer.BuyerTestEARBuilder;
import bookshop2.buyer.client.orderBooks.OrderBooks;
import bookshop2.buyer.client.orderBooks.OrderBooksProxyForJMS;
import bookshop2.seller.SellerTestEARBuilder;
import bookshop2.shipper.ShipperTestEARBuilder;
import bookshop2.supplier.SupplierTestEARBuilder;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManagerMBean;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class OrderBooksJmsIT extends AbstractJMSListenerArquillionTest {
	
	private JmsClient orderBooksClient;

	private OrderRequestMessage orderRequestMessage;

	private MessageListener orderRejectedListener;

	private MessageListener purchaseRejectedListener;

	private MessageListener purchaseAcceptedListener;

	private String correlationId;
	
	private String errorMessage;
	
	private boolean orderAcceptedReceived;

	private boolean orderRejectedReceived;

	private boolean purchaseAcceptedReceived;

	private boolean purchaseRejectedReceived;

	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}

	@Override
	public String getDomainId() {
		return "bookshop2.buyer";
	}
	
	@Override
	public String getServiceId() {
		return "bookshop2.buyer.orderBooks";
	}

	public Class<?> getTestClass() {
		return OrderBooksJmsIT.class;
	}

	@Override
	public String getTargetArchive() {
		return SellerTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return getJNDINameForQueue("public_bookshop2_buyer_order_books_queue");
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
		startServer("hornetQ01_local");
		correlationId = "dummyCorrelationId";
		orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		orderRejectedListener = createOrderRejectedResponseListener();
		purchaseRejectedListener = createPurchaseRejectedResponseListener();
		purchaseAcceptedListener = createPurchaseAcceptedResponseListener();
	}

	@After
	public void tearDown() throws Exception {
		orderBooksClient.reset();
		orderBooksClient = null;
		orderRequestMessage = null;
		orderRejectedListener = null;
		purchaseRejectedListener = null;
		purchaseAcceptedListener = null;
		orderAcceptedReceived = false;
		orderRejectedReceived = false;
		purchaseAcceptedReceived = false;
		purchaseRejectedReceived = false;
		errorMessage = null;
		super.tearDown();
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "buyerEAR", order = 2)
	public static EnterpriseArchive createBuyerEAR() {
		BuyerTestEARBuilder builder = new BuyerTestEARBuilder();
		builder.setIncludeWar(true);
		return builder.createEAR();
	}

	@Deployment(name="sellerEAR", order=2)
	@TargetsContainer("hornetQ01_local")
	public static Archive<?> createSellerEAR() {
		SellerTestEARBuilder builder = new SellerTestEARBuilder();
		builder.setDeployJms(false);
		return builder.createEAR();
	}
	
	@Deployment(name="supplierEAR", order=3)
	@TargetsContainer("hornetQ01_local")
	public static EnterpriseArchive createSupplierEAR() {
		SupplierTestEARBuilder builder = new SupplierTestEARBuilder();
		builder.setDeployJms(false);
		return builder.createEAR();
	}
	
	@Deployment(name="shipperEAR", order=4)
	@TargetsContainer("hornetQ01_local")
	public static Archive<?> createShipperEAR() {
		ShipperTestEARBuilder builder = new ShipperTestEARBuilder();
		builder.setDeployJms(false);
		return builder.createEAR();
	}
	
	@Test
	@InSequence(value = 1)
	//@Transactional(TransactionMode.ROLLBACK)
	public void testOrderBooks_OrderRejected_BooksUnavailable() throws Exception {
		String testName = "testOrderBooks_OrderRejected_BooksUnavailable";
		log.info(testName+": started");
		runTest(orderRejectedListener);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	//@Transactional(TransactionMode.ROLLBACK)
	public void testOrderBooks_PurchaseRejected() throws Exception {
		String testName = "testOrderBooks_PurchaseRejected";
		log.info(testName+": started");
		addBooksToSupplierOrderCache();
		runTest(purchaseRejectedListener);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 3)
	//@Transactional(TransactionMode.ROLLBACK)
	public void testOrderBooks_PurchaseAccepted() throws Exception {
		String testName = "testOrderBooks_PurchaseAccepted";
		log.info(testName+": started");
		addBooksToSupplierOrderCache();
		runTest(purchaseAcceptedListener);
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest(MessageListener responseListener) throws Exception {
		// Prepare fixture execution
		removeMessagesFromTargetDestination();

		// prepare context
		//beginTransaction();
		
		// prepare mocks
		FutureResult<Object> futureResult = registerForResult();

		// start fixture execution
		orderBooksClient = startOrderBooksClient(responseListener);
		orderBooksClient.send(orderRequestMessage, correlationId);

		// wait for result
		Object result = waitForCompletion(futureResult);
		validateResult(result);
		
		// close context
		//commitTransaction();

		// validate fixture execution
		assertEmptyTargetDestination();
		removeMessagesFromTargetDestination();
	}

	protected JmsClient startOrderBooksClient(MessageListener messageListener) throws Exception {
		JmsClient client = new OrderBooksProxyForJMS(OrderBooks.ID);
		configureClient(client, getTargetDestination());
		client.setMessageListener(messageListener);
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	
	protected void addBooksToSupplierOrderCache() throws Exception {
		String mbeanName = SupplierOrderCacheManagerMBean.MBEAN_NAME;
		Map<BookKey, Book> books = Bookshop2Fixture.createMap_Book();
		addBooksToSupplierOrderCache(mbeanName, books);
	}

	public void addBooksToSupplierOrderCache(String mbeanName, Map<BookKey, Book> books) throws Exception {
		ObjectName objectName = new ObjectName(mbeanName);
		addBooksToSupplierOrderCache(objectName, books);
	}
	
	public void addBooksToSupplierOrderCache(ObjectName objectName, Map<BookKey, Book> books) throws Exception {
		Object[] parameters = { books };
		String[] signature = { "java.util.Map" };
		jmxManager.invoke(objectName, "addToBooksInStock", parameters, signature);
		jmxManager.invoke(objectName, "updateState");
	}
	

	protected MessageListener createOrderAcceptedResponseListener() {
		return new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof OrderAcceptedMessage, "Payload type not correct");
					OrderAcceptedMessage orderAcceptedMessage = (OrderAcceptedMessage) object;
					validate(orderAcceptedMessage);
					orderAcceptedReceived = true;
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				}
			}
		};
	}
	
	protected MessageListener createOrderRejectedResponseListener() {
		return new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof OrderRejectedMessage, "Payload type not correct");
					OrderRejectedMessage orderRejectedMessage = (OrderRejectedMessage) object;
					validate(orderRejectedMessage);
					orderRejectedReceived = true;
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				}
			}
		};
	}
	
	protected MessageListener createPurchaseAcceptedResponseListener() {
		return new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof PurchaseAcceptedMessage, "Payload type not correct");
					PurchaseAcceptedMessage purchaseAcceptedMessage = (PurchaseAcceptedMessage) object;
					validate(purchaseAcceptedMessage);
					purchaseAcceptedReceived = true;
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				}
			}
		};
	}

	protected MessageListener createPurchaseRejectedResponseListener() {
		return new MessageListener() {

			@Override
			public void onMessage(Message message) {
				try {
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof PurchaseRejectedMessage, "Payload type not correct");
					PurchaseRejectedMessage purchaseRejectedMessage = (PurchaseRejectedMessage) object;
					validate(purchaseRejectedMessage);
					purchaseRejectedReceived = true;
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				}
			}
		};
	}

	
	protected void validateResult(Object result) {
		if (result instanceof OrderAcceptedMessage) {
			validate((OrderAcceptedMessage) result);
		} else if (result instanceof OrderRejectedMessage) {
				validate((OrderRejectedMessage) result);
		} else if (result instanceof PurchaseAcceptedMessage) {
			validate((PurchaseAcceptedMessage) result);
		} else if (result instanceof PurchaseRejectedMessage) {
			validate((PurchaseRejectedMessage) result);
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}
	
	protected void validate(OrderAcceptedMessage orderAcceptedMessage) {
		try {
			Assert.notNull(orderAcceptedMessage, "Message must be specified");
			Order expectedOrder = orderRequestMessage.getOrder();
			Order actualOrder = orderAcceptedMessage.getOrder();
			Bookshop2Fixture.assertOrderCorrect(actualOrder);
			Bookshop2Fixture.assertSameOrder(expectedOrder, actualOrder);
		} catch (Throwable e) {
			errorMessage = ExceptionUtils.getRootCauseMessage(e);
		}
	}
	
	protected void validate(OrderRejectedMessage orderRejectedMessage) {
		try {
			Assert.notNull(orderRejectedMessage, "Message must be specified");
			Order expectedOrder = orderRequestMessage.getOrder();
			Order actualOrder = orderRejectedMessage.getOrder();
			Bookshop2Fixture.assertOrderCorrect(actualOrder);
			Bookshop2Fixture.assertSameOrder(expectedOrder, actualOrder);
			Assert.notNull(orderRejectedMessage.getReason(), "Reason not found");
			Assert.equals(orderRejectedMessage.getReason(), "Books Unavailable", "Reason not correct");
		} catch (Throwable e) {
			errorMessage = ExceptionUtils.getRootCauseMessage(e);
		}
	}
	
	protected void validate(PurchaseAcceptedMessage purchaseAcceptedMessage) {
		try {
			Assert.notNull(purchaseAcceptedMessage, "Message must be specified");
			Order expectedOrder = orderRequestMessage.getOrder();
			Order actualOrder = purchaseAcceptedMessage.getOrder();
			Bookshop2Fixture.assertOrderCorrect(actualOrder);
			Bookshop2Fixture.assertSameOrder(expectedOrder, actualOrder);
		} catch (Throwable e) {
			errorMessage = ExceptionUtils.getRootCauseMessage(e);
		}
	}
	
	protected void validate(PurchaseRejectedMessage purchaseRejectedMessage) {
		try {
			Assert.notNull(purchaseRejectedMessage, "Message must be specified");
			Order expectedOrder = orderRequestMessage.getOrder();
			Order actualOrder = purchaseRejectedMessage.getOrder();
			Bookshop2Fixture.assertOrderCorrect(actualOrder);
			Bookshop2Fixture.assertSameOrder(expectedOrder, actualOrder);
			Assert.notNull(purchaseRejectedMessage.getReason(), "Reason not found");
			Assert.equals(purchaseRejectedMessage.getReason(), "Funds not available", "Reason not correct");
		} catch (Throwable e) {
			errorMessage = ExceptionUtils.getRootCauseMessage(e);
		}
	}
	

}
