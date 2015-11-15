package bookshop2.buyer.incoming.purchaseBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aries.runtime.BeanContext;
import org.aries.runtime.RequestContext;
import org.aries.tx.AbstractHandlerUnitTest;
import org.aries.tx.Transactional;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.PurchaseAcceptedMessage;
import bookshop2.buyer.BuyerProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseAcceptedHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private PurchaseAcceptedHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private BuyerProcess mockBuyerProcess;
	
	
	public String getName() {
		return "PurchaseAccepted";
	}
	
	public String getDomain() {
		return "bookshop2.buyer";
	}
	
	public Transactional getFixture() {
		return fixture;
	}
	
	public BuyerProcess getMockServiceProcess() {
		return mockBuyerProcess;
	}
	
	@Before
	public void setUp() throws Exception {
		mockRequestContext = mock(RequestContext.class);
		mockBuyerProcess = mock(BuyerProcess.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		mockRequestContext = null;
		mockBuyerProcess = null;
		fixture = null;
		super.tearDown();
	}
	
	protected PurchaseAcceptedHandlerImpl createFixture() throws Exception {
		fixture = new PurchaseAcceptedHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "buyerProcess", mockBuyerProcess);
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
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_purchaseAccepted(null);
	}
	
	@Test
	public void testExecute_purchaseAccepted_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("PurchaseAcceptedMessage/order/count must be specified");
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.createEmpty_PurchaseAcceptedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseAcceptedMessage);
		isAbortExpected = true;
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	@Test
	public void testExecute_purchaseAccepted_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseAcceptedMessage);
		isAbortExpected = true;
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	@Test
	public void testExecute_purchaseAccepted_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		PurchaseAcceptedMessage purchaseAcceptedMessage = Bookshop2Fixture.create_PurchaseAcceptedMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseAcceptedMessage);
		isAbortExpected = true;
		runTestExecute_purchaseAccepted(purchaseAcceptedMessage);
	}
	
	public void runTestExecute_purchaseAccepted(PurchaseAcceptedMessage purchaseAcceptedMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.purchaseAccepted(purchaseAcceptedMessage);
			validateProcessInvocation(purchaseAcceptedMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(PurchaseAcceptedMessage purchaseAcceptedMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockBuyerProcess).handle_PurchaseAccepted_response(purchaseAcceptedMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockBuyerProcess).firePurchaseAcceptedDone();
	}
	
}
