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

import bookshop2.OrderRejectedMessage;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class OrderRejectedListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private OrderRejectedListenerForJMS fixture;
	
	private OrderRejectedHandler mockOrderRejectedHandler;
	
	
	@Override
	public String getServiceId() {
		return OrderRejected.ID;
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
		mockOrderRejectedHandler = mock(OrderRejectedHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		//mockRequestContext = null;
		mockOrderRejectedHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected OrderRejectedListenerForJMS createFixture() throws Exception {
		fixture = new OrderRejectedListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "orderRejectedHandler", mockOrderRejectedHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_orderRejected_Success() throws Exception {
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRejectedMessage);
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	@Test
	public void testExecute_orderRejected_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("Incoming message is null");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_orderRejected(null);
	}
	
	@Test
	public void testExecute_orderRejected_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("OrderRejectedMessage");
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.createEmpty_OrderRejectedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRejectedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	@Test
	public void testExecute_orderRejected_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRejectedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	@Test
	public void testExecute_orderRejected_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRejectedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	public void runTestExecute_orderRejected(OrderRejectedMessage orderRejectedMessage) throws Exception {
		setupBeforeInvocation(orderRejectedMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(orderRejectedMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(OrderRejectedMessage orderRejectedMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockOrderRejectedHandler).orderRejected(orderRejectedMessage);
	}
	
}
