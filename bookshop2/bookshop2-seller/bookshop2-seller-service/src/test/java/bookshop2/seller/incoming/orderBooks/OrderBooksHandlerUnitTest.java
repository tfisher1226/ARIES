package bookshop2.seller.incoming.orderBooks;

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

import bookshop2.OrderRequestMessage;
import bookshop2.seller.SellerContext;
import bookshop2.seller.SellerProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class OrderBooksHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private OrderBooksHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SellerProcess mockSellerProcess;
	
	
	public String getName() {
		return "OrderBooks";
	}
	
	public String getDomain() {
		return "bookshop2.seller";
	}
	
	public Transactional getFixture() {
		return fixture;
	}
	
	public SellerProcess getMockServiceProcess() {
		return mockSellerProcess;
	}
	
	@Before
	public void setUp() throws Exception {
		mockRequestContext = mock(RequestContext.class);
		mockSellerProcess = mock(SellerProcess.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		mockRequestContext = null;
		mockSellerProcess = null;
		fixture = null;
		super.tearDown();
	}
	
	protected OrderBooksHandlerImpl createFixture() throws Exception {
		fixture = new OrderBooksHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "sellerProcess", mockSellerProcess);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_orderBooks_Success() throws Exception {
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRequestMessage);
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	@Test
	public void testExecute_orderBooks_NullRequest() throws Exception {
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_orderBooks(null);
	}
	
	@Test
	public void testExecute_orderBooks_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("OrderRequestMessage/order/trackingNumber must be specified");
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.createEmpty_OrderRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRequestMessage);
		isAbortExpected = true;
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	@Test
	public void testExecute_orderBooks_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRequestMessage);
		isAbortExpected = true;
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	@Test
	public void testExecute_orderBooks_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(orderRequestMessage);
		isAbortExpected = true;
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	@Test
	public void testExecute_orderBooks_NullTransactionId() throws Exception {
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(orderRequestMessage);
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	@Test
	public void testExecute_orderBooks_EmptyTransactionId() throws Exception {
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(orderRequestMessage);
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	public void runTestExecute_orderBooks(OrderRequestMessage orderRequestMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.orderBooks(orderRequestMessage);
			
			if (isGlobalTransactionActive())
				validateEnrollTransaction(orderRequestMessage);
			validateProcessInvocation(orderRequestMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(OrderRequestMessage orderRequestMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSellerProcess).handle_OrderBooks_request(orderRequestMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSellerProcess).fireOrderBooksDone();
	}
	
	protected void validateAfterExecution() throws Exception {
		if (isAbortExpected)
			verify(mockSellerProcess).handle_OrderBooks_request_exception(expectedCorrelationId, expectedException);
		super.validateAfterExecution();
	}
	
}
