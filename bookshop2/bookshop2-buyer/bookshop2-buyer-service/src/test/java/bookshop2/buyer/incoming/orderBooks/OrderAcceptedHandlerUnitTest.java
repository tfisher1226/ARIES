package bookshop2.buyer.incoming.orderBooks;

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

import bookshop2.OrderAcceptedMessage;
import bookshop2.buyer.BuyerProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class OrderAcceptedHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private OrderAcceptedHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private BuyerProcess mockBuyerProcess;
	
	
	public String getName() {
		return "OrderAccepted";
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
	
	protected OrderAcceptedHandlerImpl createFixture() throws Exception {
		fixture = new OrderAcceptedHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "buyerProcess", mockBuyerProcess);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_orderAccepted_Success() throws Exception {
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderAcceptedMessage);
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	@Test
	public void testExecute_orderAccepted_NullRequest() throws Exception {
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_orderAccepted(null);
	}
	
	@Test
	public void testExecute_orderAccepted_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("OrderAcceptedMessage/order/count must be specified");
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.createEmpty_OrderAcceptedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderAcceptedMessage);
		isAbortExpected = true;
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	@Test
	public void testExecute_orderAccepted_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderAcceptedMessage);
		isAbortExpected = true;
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	@Test
	public void testExecute_orderAccepted_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderAcceptedMessage);
		isAbortExpected = true;
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	public void runTestExecute_orderAccepted(OrderAcceptedMessage orderAcceptedMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.orderAccepted(orderAcceptedMessage);
			validateProcessInvocation(orderAcceptedMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(OrderAcceptedMessage orderAcceptedMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockBuyerProcess).handle_OrderAccepted_response(orderAcceptedMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockBuyerProcess).fireOrderAcceptedDone();
	}
	
}
