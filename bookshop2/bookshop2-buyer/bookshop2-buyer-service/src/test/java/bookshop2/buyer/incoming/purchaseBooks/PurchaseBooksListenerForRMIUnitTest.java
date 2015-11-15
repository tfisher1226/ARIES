package bookshop2.buyer.incoming.purchaseBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aries.message.Message;
import org.aries.message.util.MessageConstants;
import org.aries.tx.AbstractListenerForRMIUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.PurchaseRequestMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseBooksListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private PurchaseBooksListenerForRMI fixture;
	
	private PurchaseBooksInterceptor mockPurchaseBooksInterceptor;
	
	private BuyerContext mockBuyerContext;
	
	
	@Override
	public String getServiceId() {
		return PurchaseBooks.ID;
	}
	
	@Override
	public String getDomain() {
		return "bookshop2.buyer";
	}
	
	@Override
	public String getModule() {
		return "bookshop2-buyer-service";
	}
	
	public BuyerContext getMockServiceContext() {
		return mockBuyerContext;
	}
	
	@Before
	public void setUp() throws Exception {
		mockBuyerContext = new BuyerContext();
		mockPurchaseBooksInterceptor = mock(PurchaseBooksInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockBuyerContext = null;
		mockPurchaseBooksInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected PurchaseBooksListenerForRMI createFixture() throws Exception {
		fixture = new PurchaseBooksListenerForRMI();
		FieldUtil.setFieldValue(fixture, "buyerContext", mockBuyerContext);
		FieldUtil.setFieldValue(fixture, "purchaseBooksInterceptor", mockPurchaseBooksInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_purchaseBooks_Success() throws Exception {
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(purchaseRequestMessage);
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("PurchaseRequestMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(null);
	}
	
	@Test
	public void testExecute_purchaseBooks_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("PurchaseRequestMessage");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.createEmpty_PurchaseRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(purchaseRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(purchaseRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(purchaseRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId null");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext("dummyCorrelationId", null);
		setupMessage(purchaseRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId empty");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext("dummyCorrelationId", "");
		setupMessage(purchaseRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	public void runTestExecute_purchaseBooks(PurchaseRequestMessage purchaseRequestMessage) throws Exception {
		try {
			Message message = new Message();
			message.addPart("purchaseRequestMessage",  purchaseRequestMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "purchaseBooks");
			
			fixture = createFixture();
			fixture.invoke(message, correlationId, 60000L);
			
			validateAfterInvocation(message);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(Message message) throws Exception {
		if (!isExpectedValidationError)
			verify(mockPurchaseBooksInterceptor).purchaseBooks(message);
	}
	
}
