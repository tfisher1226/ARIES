package bookshop2.buyer.incoming.orderBooks;

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

import bookshop2.OrderAcceptedMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class OrderAcceptedListenerForJAXWSUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private OrderAcceptedListenerForJAXWS fixture;
	
	private OrderAcceptedHandler mockOrderAcceptedHandler;
	
	private BuyerContext mockBuyerContext;
	
	
	@Override
	public String getServiceId() {
		return OrderAccepted.ID;
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
		mockOrderAcceptedHandler = mock(OrderAcceptedHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockBuyerContext = null;
		mockOrderAcceptedHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected OrderAcceptedListenerForJAXWS createFixture() throws Exception {
		fixture = new OrderAcceptedListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "buyerContext", mockBuyerContext);
		FieldUtil.setFieldValue(fixture, "orderAcceptedHandler", mockOrderAcceptedHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_orderAccepted_Success() throws Exception {
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(orderAcceptedMessage);
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	@Test
	public void testExecute_orderAccepted_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("OrderAcceptedMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_orderAccepted(null);
	}
	
	@Test
	public void testExecute_orderAccepted_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("OrderAcceptedMessage");
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.createEmpty_OrderAcceptedMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(orderAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	@Test
	public void testExecute_orderAccepted_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(orderAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	@Test
	public void testExecute_orderAccepted_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(orderAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	public void runTestExecute_orderAccepted(OrderAcceptedMessage orderAcceptedMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.orderAccepted(orderAcceptedMessage);
			
			validateAfterInvocation(orderAcceptedMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(OrderAcceptedMessage orderAcceptedMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockOrderAcceptedHandler).orderAccepted(orderAcceptedMessage);
	}
	
}
