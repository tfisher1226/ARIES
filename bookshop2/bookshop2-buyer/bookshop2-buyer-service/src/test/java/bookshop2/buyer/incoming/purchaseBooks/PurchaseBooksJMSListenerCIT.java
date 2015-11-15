package bookshop2.buyer.incoming.purchaseBooks;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.common.util.CommonFixture;
import org.aries.jms.client.JmsClient;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
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

import bookshop2.Invoice;
import bookshop2.Order;
import bookshop2.Payment;
import bookshop2.PurchaseAcceptedMessage;
import bookshop2.PurchaseRejectedMessage;
import bookshop2.PurchaseRequestMessage;
import bookshop2.buyer.BuyerTestEARBuilder;
import bookshop2.seller.client.purchaseBooks.PurchaseBooksProxyForJMS;
import bookshop2.util.Bookshop2Fixture;


@RunAsClient
@RunWith(Arquillian.class)
public class PurchaseBooksJMSListenerCIT extends AbstractJMSListenerArquillionTest {
	
	private TransactionTestControl transactionTestControl;
	
	private JmsClient purchaseBooksClient;
	
	/** Remote PurchaseBooks request handler */
	private JmsClient remotePurchaseBooksHandler;
	
	/** Local PurchaseRejected response handler */
	private JmsClient purchaseAcceptedHandler;
	
	/** Local PurchaseAccepted response handler */
	private JmsClient purchaseRejectedHandler;
	
	private JmsClient remotePurchaseRejectedSender;
	
	private JmsClient remotePurchaseAcceptedSender;
	
	/** Remote OrderBooks request listener */
	private MessageListener remotePurchaseBooksListener;

	/** Local PurchaseAccepted response listener */
	private MessageListener purchaseAcceptedListener;
	
	/** Local PurchaseRejected response listener */
	private MessageListener purchaseRejectedListener;
	
	private PurchaseRequestMessage purchaseRequestMessage;

	private PurchaseAcceptedMessage purchaseAcceptedMessage;

	private PurchaseRejectedMessage purchaseRejectedMessage;
	
	protected boolean purchaseAcceptedReceived;

	protected boolean purchaseRejectedReceived;

	protected boolean purchaseBooksReceived;
	
	protected String purchaseRejectedReason;

	
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
		return "bookshop2.buyer.PurchaseBooks";
	}
	
	@Override
	public String getTargetArchive() {
		return BuyerTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return getJNDINameForQueue("public_bookshop2_buyer_purchase_books_queue");
	}

	//outgoing response back to caller
	public String getPurchaseAcceptedDestination() {
		return getJNDINameForQueue("public_bookshop2_buyer_purchase_accepted_queue");
	}

	//outgoing response back to caller
	public String getPurchaseRejectedDestination() {
		return getJNDINameForQueue("public_bookshop2_buyer_purchase_rejected_queue");
	}

	//outgoing call to remote service 
	public String getPurchaseBooksDestination() {
		return getJNDINameForQueue("protected_bookshop2_seller_purchase_books_queue");
	}

	//outgoing response back to caller
	public String getLocalPurchaseAcceptedDestination() {
		return "test_message_domain_test_destination_queue_a";
	}

	//outgoing response back to caller
	public String getLocalPurchaseRejectedDestination() {
		return "test_message_domain_test_destination_queue_b";
	}
	
	@Override
	public Class<?> getTestClass() {
		return PurchaseBooksJMSListenerCIT.class;
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
		purchaseRequestMessage = createPurchaseRequestMessage();
		purchaseAcceptedListener = createPurchaseAcceptedResponseListener();
		purchaseRejectedListener = createPurchaseRejectedResponseListener();
		remotePurchaseBooksListener = createPurchaseBooksRequestListener();
		remotePurchaseAcceptedSender = createClient(getPurchaseAcceptedDestination());
		remotePurchaseRejectedSender = createClient(getPurchaseRejectedDestination());
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	@After
	public void tearDown() throws Exception {
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		purchaseBooksClient.reset();
		purchaseBooksClient = null;
		purchaseAcceptedHandler.reset();
		purchaseAcceptedHandler = null;
		purchaseAcceptedListener = null;
		purchaseRejectedHandler.reset();
		purchaseRejectedHandler = null;
		purchaseRejectedListener = null;
		remotePurchaseBooksHandler.reset();
		remotePurchaseBooksHandler = null;
		remotePurchaseAcceptedSender.reset();
		remotePurchaseAcceptedSender = null;
		remotePurchaseRejectedSender.reset();
		remotePurchaseRejectedSender = null;
	}

	protected void clearState() throws Exception {
		purchaseRequestMessage = null;
		purchaseAcceptedMessage = null;
		purchaseRejectedMessage = null;
		purchaseAcceptedReceived = false;
		purchaseRejectedReceived = false;
		purchaseRejectedReason = null;
		purchaseBooksReceived = false;
		super.clearState();
	}
	
//	@TargetsContainer("hornetQ01_local")
//	@Deployment(name = "txManagerEAR", order = 1)
//	public static EnterpriseArchive createTXManagerEAR() {
//		TXManagerTestEARBuilder builder = new TXManagerTestEARBuilder();
//		return builder.createEAR();
//	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "buyerEAR", order = 2)
	public static EnterpriseArchive createBuyerEAR() {
		BuyerTestEARBuilder builder = new BuyerTestEARBuilder();
		builder.setIncludeWar(true);
		return builder.createEAR();
	}
	
	public PurchaseRequestMessage createPurchaseRequestMessage() {
		return createPurchaseRequestMessage(false, false);
	}
	
	public PurchaseRequestMessage createPurchaseRequestCancelMessage() {
		return createPurchaseRequestMessage(true, false);
	}
	
	public PurchaseRequestMessage createPurchaseRequestUndoMessage() {
		return createPurchaseRequestMessage(false, true);
	}
	
	public PurchaseRequestMessage createPurchaseRequestMessage(boolean cancel, boolean undo) {
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage(cancel, undo);
		purchaseRequestMessage.addToReplyToDestinations("PurchaseAccepted", getLocalPurchaseAcceptedDestination());
		purchaseRequestMessage.addToReplyToDestinations("PurchaseRejected", getLocalPurchaseRejectedDestination());
		return initializeMessage(purchaseRequestMessage);
	}
	
	public PurchaseAcceptedMessage createPurchaseAcceptedMessage() {
		return initializeMessage(Bookshop2Fixture.create_PurchaseAcceptedMessage());
	}

	public PurchaseRejectedMessage createPurchaseRejectedMessage() {
		return initializeMessage(Bookshop2Fixture.create_PurchaseRejectedMessage());
	}
	
	@Test
	@InSequence(value = 1)
	public void testPurchaseBooks_PurchaseAccepted() throws Exception {
		String testName = "testPurchaseBooks_PurchaseAccepted";
		log.info(testName+": started");
		registerNotificationListeners();
		expectedEvent = "bookshop2.buyer.PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.buyer.PurchaseAccepted";
		//execution started
		runTest_purchaseBooks();
		//execution finished
		Assert.isTrue(purchaseAcceptedReceived);
		Assert.isFalse(purchaseRejectedReceived);
		Assert.isTrue(isFiredRequestDone("PurchaseBooks"));
		Assert.isTrue(isFiredResponseSent("PurchaseAccepted"));
		Assert.isFalse(isFiredResponseSent("PurchaseRejected"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	@Test
	//@Ignore
	@InSequence(value = 2)
	public void testPurchaseBooks_PurchaseRejected() throws Exception {
		String testName = "testPurchaseBooks_PurchaseRejected";
		log.info(testName+": started");
		registerNotificationListeners();
		expectedEvent = "bookshop2.buyer.PurchaseBooks_Request_Done";
		expectedMessage = "bookshop2.buyer.PurchaseRejected";
		purchaseRejectedReason = "purchase rejected";
		isExceptionExpected = true;
		//execution started
		runTest_purchaseBooks();
		//execution finished
		Assert.isFalse(purchaseAcceptedReceived);
		Assert.isTrue(purchaseRejectedReceived);
		Assert.isTrue(isFiredRequestDone("PurchaseBooks"));
		Assert.isFalse(isFiredResponseSent("PurchaseAccepted"));
		Assert.isTrue(isFiredResponseSent("PurchaseRejected"));
		log.info(testName+": done");
		if (errorMessage != null)
			fail(errorMessage);
	}
	
	public void runTest_purchaseBooks() throws Exception {
		// prepare the environment
		removeMessagesFromDestinations();
		
		// prepare context
		//beginTransaction();
		
		// prepare mocks
		registerForResult();
		
		// start fixture execution
		remotePurchaseBooksHandler = startRemotePurchaseBooksRequestHandler(remotePurchaseBooksListener);
		purchaseAcceptedHandler = startLocalPurchaseAcceptedResponseHandler(purchaseAcceptedListener);
		purchaseRejectedHandler = startLocalPurchaseRejectedResponseHandler(purchaseRejectedListener);
		purchaseBooksClient = startPurchaseBooksClient();
		sendRequest_PurchaseBooks();
		
		// wait for result
		Object result = waitForCompletion();
		validateResult(result);
		
		// close context
		//commitTransaction();
		
		// validate the environment
		assertEmptyTargetDestination();
		removeMessagesFromDestinations();
	}
	
	protected void sendRequest_PurchaseBooks() throws Exception {
		purchaseBooksClient.send(purchaseRequestMessage, correlationId, null);
	}

	protected void sendRequest_PurchaseBooks_Cancel() throws Exception {
		purchaseRequestMessage = createPurchaseRequestCancelMessage();
		sendRequest_PurchaseBooks();
	}

	protected void sendRequest_PurchaseBooks_Undo() throws Exception {
		purchaseRequestMessage = createPurchaseRequestUndoMessage();
		sendRequest_PurchaseBooks();
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("PurchaseBooks");
		addResponseNotificationListeners("PurchaseAccepted");
		addResponseNotificationListeners("PurchaseRejected");
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), getTargetDestination());
		removeMessagesFromQueue(getTargetArchive(), getPurchaseBooksDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalPurchaseAcceptedDestination());
		removeMessagesFromQueue(getTargetArchive(), getLocalPurchaseRejectedDestination());
	}
	
	protected JmsClient startPurchaseBooksClient() throws Exception {
		JmsClient client = new PurchaseBooksProxyForJMS(PurchaseBooks.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
	protected JmsClient startRemotePurchaseBooksRequestHandler(MessageListener messageListener) throws Exception {
		JmsClient client = createClient(getPurchaseBooksDestination());
		client.setMessageListener(messageListener);
		client.initialize();
		return client;
	}
	
	protected JmsClient startLocalPurchaseAcceptedResponseHandler(MessageListener messageListener) throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalPurchaseAcceptedDestination()));
		client.setMessageListener(messageListener);
		client.initialize();
		return client;
	}
	
	protected JmsClient startLocalPurchaseRejectedResponseHandler(MessageListener messageListener) throws Exception {
		JmsClient client = createClient(getJNDINameForQueue(getLocalPurchaseRejectedDestination()));
		client.setMessageListener(messageListener);
		client.initialize();
		return client;
	}
	
	protected MessageListener createPurchaseBooksRequestListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof PurchaseRequestMessage, "Payload type not correct");
					PurchaseRequestMessage purchaseRequestMessage = (PurchaseRequestMessage) object;
					validate(purchaseRequestMessage);
					process(purchaseRequestMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				} finally {
					purchaseBooksReceived = true;
				}
			}
		};
	}
	
	protected void process(PurchaseRequestMessage purchaseRequestMessage) {
		Order order = purchaseRequestMessage.getOrder();
		Payment payment = purchaseRequestMessage.getPayment();
		if (!isExceptionExpected) {
			Invoice invoice = Bookshop2Fixture.create_Invoice();
			sendPurchaseAcceptedMessage(order, invoice);
			
		} else {
			String reason = purchaseRejectedReason;
			sendPurchaseRejectedMessage(order, payment, reason);
		}
	}

	protected void sendPurchaseAcceptedMessage(Order order, Invoice invoice) {
		purchaseAcceptedMessage = createPurchaseAcceptedMessage();
		purchaseAcceptedMessage.setOrder(order);
		//purchaseAcceptedMessage.setPayment(payment);
		purchaseAcceptedMessage.setInvoice(invoice);
		remotePurchaseAcceptedSender.sendResponse(purchaseAcceptedMessage);
	}
	
	protected void sendPurchaseRejectedMessage(Order order, Payment payment, String reason) {
		purchaseRejectedMessage = createPurchaseRejectedMessage();
		purchaseRejectedMessage.setOrder(order);
		purchaseRejectedMessage.setPayment(payment);
		purchaseRejectedMessage.setReason(reason);
		remotePurchaseRejectedSender.sendResponse(purchaseRejectedMessage);
	}
	

	protected MessageListener createPurchaseAcceptedResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: PurchaseAccepted received");
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof PurchaseAcceptedMessage, "Payload type not correct");
					PurchaseAcceptedMessage purchaseAcceptedMessage = (PurchaseAcceptedMessage) object;
					validate(purchaseAcceptedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.buyer.PurchaseAccepted"))
						expectedMessageResult.set(true);
					purchaseAcceptedReceived = true;
				}
			}
		};
	}
	
	protected MessageListener createPurchaseRejectedResponseListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					log.info("#### [test]: PurchaseRejected received");
					Object object = getObjectFromMessage(message);
					Assert.notNull(object, "Payload not found");
					Assert.isTrue(object instanceof PurchaseRejectedMessage, "Payload type not correct");
					PurchaseRejectedMessage purchaseRejectedMessage = (PurchaseRejectedMessage) object;
					validate(purchaseRejectedMessage);
				} catch (Throwable e) {
					errorMessage = ExceptionUtils.getRootCauseMessage(e);
				} finally {
					if (expectedMessage != null && expectedMessage.equalsIgnoreCase("bookshop2.buyer.PurchaseRejected"))
						expectedMessageResult.set(true);
					purchaseRejectedReceived = true;
				}
			}
		};
	}

	protected void validateResult(Object result) {
		if (result instanceof PurchaseAcceptedMessage) {
			validate((PurchaseAcceptedMessage) result);
		} else if (result instanceof PurchaseRejectedMessage) {
			validate((PurchaseRejectedMessage) result);
		} else if (result instanceof String) {
			String resultString = result.toString();
			if ((expectedError != null && !expectedError.equalsIgnoreCase(resultString)) &&
				(expectedEvent != null && !expectedEvent.equalsIgnoreCase(resultString)))
					errorMessage = "Unexpected message: "+result;
		} else {
			errorMessage = "Unrecognized result: "+result;
		}
	}
	
	protected void validate(PurchaseRequestMessage purchaseRequestMessage) {
		Assert.notNull(purchaseRequestMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(purchaseRequestMessage.getOrder());
		Bookshop2Fixture.assertPaymentCorrect(purchaseRequestMessage.getPayment());
		CommonFixture.assertPersonNameCorrect(purchaseRequestMessage.getName());
		CommonFixture.assertStreetAddressCorrect(purchaseRequestMessage.getAddress());
	}
	
	protected void validate(PurchaseAcceptedMessage purchaseAcceptedMessage) {
		Assert.notNull(purchaseAcceptedMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(purchaseAcceptedMessage.getOrder());
		Bookshop2Fixture.assertPaymentCorrect(purchaseAcceptedMessage.getPayment());
		Bookshop2Fixture.assertInvoiceCorrect(purchaseAcceptedMessage.getInvoice());
	}
	
	protected void validate(PurchaseRejectedMessage purchaseRejectedMessage) {
		Assert.notNull(purchaseRejectedMessage, "Message must be specified");
		Bookshop2Fixture.assertOrderCorrect(purchaseRejectedMessage.getOrder());
		Bookshop2Fixture.assertPaymentCorrect(purchaseRejectedMessage.getPayment());
		Assert.equals(purchaseRejectedMessage.getReason(), purchaseRejectedReason);
	}

}
