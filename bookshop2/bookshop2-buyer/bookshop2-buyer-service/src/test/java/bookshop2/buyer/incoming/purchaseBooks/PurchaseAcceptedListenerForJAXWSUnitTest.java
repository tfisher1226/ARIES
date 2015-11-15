package bookshop2.buyer.incoming.purchaseBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aries.tx.AbstractListenerForJAXWSUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.PurchaseAcceptedMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseAcceptedListenerForJAXWSUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private PurchaseAcceptedListenerForJAXWS fixture;
	
	private PurchaseAcceptedHandler mockPurchaseAcceptedHandler;
	
	private BuyerContext mockBuyerContext;
	
	
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
	
	public BuyerContext getMockServiceContext() {
		return mockBuyerContext;
	}
	
	@Before
	public void setUp() throws Exception {
		mockBuyerContext = new BuyerContext();
		mockPurchaseAcceptedHandler = mock(PurchaseAcceptedHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockBuyerContext = null;
		mockPurchaseAcceptedHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected PurchaseAcceptedListenerForJAXWS createFixture() throws Exception {
		fixture = new PurchaseAcceptedListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "buyerContext", mockBuyerContext);
		FieldUtil.setFieldValue(fixture, "purchaseAcceptedHandler", mockPurchaseAcceptedHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_purchaseAccepted_Success() throws Exception {
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(purchaseAcceptedMessage);
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	@Test
	public void testExecute_purchaseAccepted_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("PurchaseAcceptedMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_purchaseAccepted(null);
	}
	
	@Test
	public void testExecute_purchaseAccepted_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("PurchaseAcceptedMessage");
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.createEmpty_PurchaseAcceptedMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(purchaseAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	@Test
	public void testExecute_purchaseAccepted_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(purchaseAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	@Test
	public void testExecute_purchaseAccepted_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(purchaseAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	public void runTestExecute_purchaseAccepted(PurchaseAcceptedMessage purchaseAcceptedMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.purchaseAccepted(purchaseAcceptedMessage);
			
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
