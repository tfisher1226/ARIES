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

import bookshop2.PurchaseAcceptedMessage;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseAcceptedListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private PurchaseAcceptedListenerForJMS fixture;
	
	private PurchaseAcceptedHandler mockPurchaseAcceptedHandler;
	
	
	@Override
	public String getServiceId() {
		return PurchaseAccepted.ID;
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
		mockPurchaseAcceptedHandler = mock(PurchaseAcceptedHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		//mockRequestContext = null;
		mockPurchaseAcceptedHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected PurchaseAcceptedListenerForJMS createFixture() throws Exception {
		fixture = new PurchaseAcceptedListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "purchaseAcceptedHandler", mockPurchaseAcceptedHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_purchaseAccepted_Success() throws Exception {
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseAcceptedMessage);
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	@Test
	public void testExecute_purchaseAccepted_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("Incoming message is null");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_purchaseAccepted(null);
	}
	
	@Test
	public void testExecute_purchaseAccepted_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("PurchaseAcceptedMessage");
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.createEmpty_PurchaseAcceptedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	@Test
	public void testExecute_purchaseAccepted_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	@Test
	public void testExecute_purchaseAccepted_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	public void runTestExecute_purchaseAccepted(PurchaseAcceptedMessage purchaseAcceptedMessage) throws Exception {
		setupBeforeInvocation(purchaseAcceptedMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(purchaseAcceptedMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(PurchaseAcceptedMessage purchaseAcceptedMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockPurchaseAcceptedHandler).purchaseAccepted(purchaseAcceptedMessage);
	}
	
}
