package bookshop2.buyer.incoming.orderBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aries.runtime.RequestContext;
import org.aries.tx.AbstractListenerForJMSUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.OrderAcceptedMessage;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class OrderAcceptedListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private OrderAcceptedListenerForJMS fixture;
	
	private OrderAcceptedHandler mockOrderAcceptedHandler;
	
	
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
	
	@Before
	public void setUp() throws Exception {
		//mockRequestContext = new RequestContext();
		mockOrderAcceptedHandler = mock(OrderAcceptedHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		//mockRequestContext = null;
		mockOrderAcceptedHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected OrderAcceptedListenerForJMS createFixture() throws Exception {
		fixture = new OrderAcceptedListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "orderAcceptedHandler", mockOrderAcceptedHandler);
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
		setupForExpectedAssertionFailure("Incoming message is null");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_orderAccepted(null);
	}
	
	@Test
	public void testExecute_orderAccepted_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("OrderAcceptedMessage");
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.createEmpty_OrderAcceptedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	@Test
	public void testExecute_orderAccepted_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	@Test
	public void testExecute_orderAccepted_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		OrderAcceptedMessage orderAcceptedMessage = Bookshop2Fixture.create_OrderAcceptedMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderAcceptedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderAccepted(orderAcceptedMessage);
	}
	
	public void runTestExecute_orderAccepted(OrderAcceptedMessage orderAcceptedMessage) throws Exception {
		setupBeforeInvocation(orderAcceptedMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
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
