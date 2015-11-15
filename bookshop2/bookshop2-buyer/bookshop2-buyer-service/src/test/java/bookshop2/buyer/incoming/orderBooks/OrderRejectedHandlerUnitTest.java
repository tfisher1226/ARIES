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

import bookshop2.OrderRejectedMessage;
import bookshop2.buyer.BuyerProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class OrderRejectedHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private OrderRejectedHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private BuyerProcess mockBuyerProcess;
	
	
	public String getName() {
		return "OrderRejected";
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
	
	protected OrderRejectedHandlerImpl createFixture() throws Exception {
		fixture = new OrderRejectedHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "buyerProcess", mockBuyerProcess);
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
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_orderRejected(null);
	}
	
	@Test
	public void testExecute_orderRejected_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("OrderRejectedMessage/reason must be specified");
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.createEmpty_OrderRejectedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRejectedMessage);
		isAbortExpected = true;
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	@Test
	public void testExecute_orderRejected_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRejectedMessage);
		isAbortExpected = true;
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	@Test
	public void testExecute_orderRejected_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		OrderRejectedMessage orderRejectedMessage = Bookshop2Fixture.create_OrderRejectedMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRejectedMessage);
		isAbortExpected = true;
		runTestExecute_orderRejected(orderRejectedMessage);
	}
	
	public void runTestExecute_orderRejected(OrderRejectedMessage orderRejectedMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.orderRejected(orderRejectedMessage);
			validateProcessInvocation(orderRejectedMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(OrderRejectedMessage orderRejectedMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockBuyerProcess).handle_OrderRejected_response(orderRejectedMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockBuyerProcess).fireOrderRejectedDone();
	}
	
}
