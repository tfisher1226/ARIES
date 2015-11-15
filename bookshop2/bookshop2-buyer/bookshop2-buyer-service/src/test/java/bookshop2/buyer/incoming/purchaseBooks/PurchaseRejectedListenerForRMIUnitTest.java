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

import bookshop2.PurchaseRejectedMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseRejectedListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private PurchaseRejectedListenerForRMI fixture;
	
	private PurchaseRejectedInterceptor mockPurchaseRejectedInterceptor;
	
	private BuyerContext mockBuyerContext;
	
	
	@Override
	public String getServiceId() {
		return PurchaseRejected.ID;
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
		mockPurchaseRejectedInterceptor = mock(PurchaseRejectedInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockBuyerContext = null;
		mockPurchaseRejectedInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected PurchaseRejectedListenerForRMI createFixture() throws Exception {
		fixture = new PurchaseRejectedListenerForRMI();
		FieldUtil.setFieldValue(fixture, "buyerContext", mockBuyerContext);
		FieldUtil.setFieldValue(fixture, "purchaseRejectedInterceptor", mockPurchaseRejectedInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_purchaseRejected_Success() throws Exception {
		PurchaseRejectedMessage purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(purchaseRejectedMessage);
		runTestExecute_purchaseRejected(purchaseRejectedMessage);
	}
	
	@Test
	public void testExecute_purchaseRejected_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("PurchaseRejectedMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_purchaseRejected(null);
	}
	
	@Test
	public void testExecute_purchaseRejected_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("PurchaseRejectedMessage");
		PurchaseRejectedMessage purchaseRejectedMessage = Bookshop2Fixture.createEmpty_PurchaseRejectedMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(purchaseRejectedMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseRejected(purchaseRejectedMessage);
	}
	
	@Test
	public void testExecute_purchaseRejected_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		PurchaseRejectedMessage purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(purchaseRejectedMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseRejected(purchaseRejectedMessage);
	}
	
	@Test
	public void testExecute_purchaseRejected_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		PurchaseRejectedMessage purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(purchaseRejectedMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseRejected(purchaseRejectedMessage);
	}
	
	public void runTestExecute_purchaseRejected(PurchaseRejectedMessage purchaseRejectedMessage) throws Exception {
		try {
			Message message = new Message();
			message.addPart("purchaseRejectedMessage",  purchaseRejectedMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "purchaseRejected");
			
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
			verify(mockPurchaseRejectedInterceptor).purchaseRejected(message);
	}
	
}
