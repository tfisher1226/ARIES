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

import bookshop2.PurchaseRejectedMessage;
import bookshop2.buyer.BuyerProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseRejectedHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private PurchaseRejectedHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private BuyerProcess mockBuyerProcess;
	
	
	public String getName() {
		return "PurchaseRejected";
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
	
	protected PurchaseRejectedHandlerImpl createFixture() throws Exception {
		fixture = new PurchaseRejectedHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "buyerProcess", mockBuyerProcess);
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
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_purchaseRejected(null);
	}
	
	@Test
	public void testExecute_purchaseRejected_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("PurchaseRejectedMessage/reason must be specified");
		PurchaseRejectedMessage purchaseRejectedMessage = Bookshop2Fixture.createEmpty_PurchaseRejectedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseRejectedMessage);
		isAbortExpected = true;
		runTestExecute_purchaseRejected(purchaseRejectedMessage);
	}
	
	@Test
	public void testExecute_purchaseRejected_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		PurchaseRejectedMessage purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseRejectedMessage);
		isAbortExpected = true;
		runTestExecute_purchaseRejected(purchaseRejectedMessage);
	}
	
	@Test
	public void testExecute_purchaseRejected_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		PurchaseRejectedMessage purchaseRejectedMessage = Bookshop2Fixture.create_PurchaseRejectedMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseRejectedMessage);
		isAbortExpected = true;
		runTestExecute_purchaseRejected(purchaseRejectedMessage);
	}
	
	public void runTestExecute_purchaseRejected(PurchaseRejectedMessage purchaseRejectedMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.purchaseRejected(purchaseRejectedMessage);
			validateProcessInvocation(purchaseRejectedMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(PurchaseRejectedMessage purchaseRejectedMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockBuyerProcess).handle_PurchaseRejected_response(purchaseRejectedMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockBuyerProcess).firePurchaseRejectedDone();
	}
	
}
