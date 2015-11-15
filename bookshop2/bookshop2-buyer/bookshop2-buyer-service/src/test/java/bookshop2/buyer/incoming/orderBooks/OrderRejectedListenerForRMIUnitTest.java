package bookshop2.buyer.incoming.orderBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aries.message.Message;
import org.aries.message.util.MessageConstants;
import org.aries.tx.AbstractListenerForRMIUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.OrderRejectedMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class OrderRejectedListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private OrderRejectedListenerForRMI fixture;
	
	private OrderRejectedInterceptor mockOrderRejectedInterceptor;
	
	private BuyerContext mockBuyerContext;
	
	
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
	
	public BuyerContext getMockServiceContext() {
		return mockBuyerContext;
	}
	
	@Before
	public void setUp() throws Exception {
		mockBuyerContext = new BuyerContext();
		mockOrderRejectedInterceptor = mock(OrderRejectedInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockBuyerContext = null;
		mockOrderRejectedInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected OrderRejectedListenerForRMI createFixture() throws Exception {
		fixture = new OrderRejectedListenerForRMI();
		FieldUtil.setFieldValue(fixture, "buyerContext", mockBuyerContext);
		FieldUtil.setFieldValue(fixture, "orderRejectedInterceptor", mockOrderRejectedInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_orderRejected_Success() throws Exception {
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(orderRejectedMessage);
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	@Test
	public void testExecute_orderRejected_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("OrderRejectedMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_orderRejected(null);
	}
	
	@Test
	public void testExecute_orderRejected_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("OrderRejectedMessage");
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.createEmpty_OrderRejectedMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(orderRejectedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	@Test
	public void testExecute_orderRejected_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(orderRejectedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	@Test
	public void testExecute_orderRejected_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(orderRejectedMessage);
		isExpectedValidationError = true;
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	public void runTestExecute_orderRejected(OrderRejectedMessage orderRejectedMessage) throws Exception {
		try {
			Message message = new Message();
			message.addPart("orderRejectedMessage",  orderRejectedMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "orderRejected");
			
			fixture = createFixture();
			fixture.invoke(message, correlationId, 60000L);
			
			validateAfterInvocation(message);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(Message message) throws Exception {
		if (!isExpectedValidationError)
			verify(mockOrderRejectedInterceptor).orderRejected(message);
	}
	
}
