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

import bookshop2.PurchaseRejectedMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseRejectedListenerForJAXWSUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private PurchaseRejectedListenerForJAXWS fixture;
	
	private PurchaseRejectedHandler mockPurchaseRejectedHandler;
	
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
		mockPurchaseRejectedHandler = mock(PurchaseRejectedHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockBuyerContext = null;
		mockPurchaseRejectedHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected PurchaseRejectedListenerForJAXWS createFixture() throws Exception {
		fixture = new PurchaseRejectedListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "buyerContext", mockBuyerContext);
		FieldUtil.setFieldValue(fixture, "purchaseRejectedHandler", mockPurchaseRejectedHandler);
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
			fixture = createFixture();
			fixture.purchaseRejected(purchaseRejectedMessage);
			
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
