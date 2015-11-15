package bookshop2.buyer.incoming.orderBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aries.jaxb.JAXBSessionCache;
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

import bookshop2.OrderAcceptedMessage;
import bookshop2.buyer.BuyerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class OrderAcceptedListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private OrderAcceptedListenerForRMI fixture;
	
	private OrderAcceptedInterceptor mockOrderAcceptedInterceptor;
	
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
		mockOrderAcceptedInterceptor = mock(OrderAcceptedInterceptor.class);
		JAXBSessionCache jaxbSessionCache = new JAXBSessionCache(getDomain());
		CheckpointManager.setJAXBSessionCache(jaxbSessionCache);
		CheckpointManager.addConfiguration("bookshop2-buyer-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockBuyerContext = null;
		mockOrderAcceptedInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected OrderAcceptedListenerForRMI createFixture() throws Exception {
		fixture = new OrderAcceptedListenerForRMI();
		FieldUtil.setFieldValue(fixture, "buyerContext", mockBuyerContext);
		FieldUtil.setFieldValue(fixture, "orderAcceptedInterceptor", mockOrderAcceptedInterceptor);
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
			Message message = new Message();
			message.addPart("orderAcceptedMessage",  orderAcceptedMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "orderAccepted");
			
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
			verify(mockOrderAcceptedInterceptor).orderAccepted(message);
	}
	
}
