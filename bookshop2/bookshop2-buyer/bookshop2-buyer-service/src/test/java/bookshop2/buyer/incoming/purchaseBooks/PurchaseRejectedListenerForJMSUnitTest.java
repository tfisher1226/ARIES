package bookshop2.buyer.incoming.purchaseBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aries.tx.AbstractListenerForJMSUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.PurchaseRejectedMessage;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseRejectedListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private PurchaseRejectedListenerForJMS fixture;
	
	private PurchaseRejectedHandler mockPurchaseRejectedHandler;
	
	
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
	
	@Before
	public void setUp() throws Exception {
		//mockRequestContext = new RequestContext();
		mockPurchaseRejectedHandler = mock(PurchaseRejectedHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		//mockRequestContext = null;
		mockPurchaseRejectedHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected PurchaseRejectedListenerForJMS createFixture() throws Exception {
		fixture = new PurchaseRejectedListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "purchaseRejectedHandler", mockPurchaseRejectedHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_purchaseRejected_Success() throws Exception {
		PurchaseRejectedMessage purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseRejectedMessage);
		runTestExecute_purchaseRejected(purchaseRejectedMessage);
	}
	
	@Test
	public void testExecute_purchaseRejected_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("Incoming message is null");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_purchaseRejected(null);
	}
	
	@Test
	public void testExecute_purchaseRejected_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("PurchaseRejectedMessage");
		PurchaseRejectedMessage purchaseRejectedMessage = Bookshop2Fixture.createEmpty_PurchaseRejectedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseRejectedMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseRejected(purchaseRejectedMessage);
	}
	
	@Test
	public void testExecute_purchaseRejected_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		PurchaseRejectedMessage purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
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
		setupBeforeInvocation(purchaseRejectedMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(purchaseRejectedMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(PurchaseRejectedMessage purchaseRejectedMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockPurchaseRejectedHandler).purchaseRejected(purchaseRejectedMessage);
	}
	
}
