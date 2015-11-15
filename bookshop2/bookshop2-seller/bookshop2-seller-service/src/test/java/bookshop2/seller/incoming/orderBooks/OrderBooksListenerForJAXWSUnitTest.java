package bookshop2.seller.incoming.orderBooks;

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

import bookshop2.OrderRequestMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class OrderBooksListenerForJAXWSUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private OrderBooksListenerForJAXWS fixture;
	
	private OrderBooksHandler mockOrderBooksHandler;
	
	private SellerContext mockSellerContext;
	
	
	@Override
	public String getServiceId() {
		return OrderBooks.ID;
	}
	
	@Override
	public String getDomain() {
		return "bookshop2.seller";
	}
	
	@Override
	public String getModule() {
		return "bookshop2-seller-service";
	}
	
	public SellerContext getMockServiceContext() {
		return mockSellerContext;
	}
	
	@Before
	public void setUp() throws Exception {
		mockSellerContext = new SellerContext();
		mockOrderBooksHandler = mock(OrderBooksHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockOrderBooksHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected OrderBooksListenerForJAXWS createFixture() throws Exception {
		fixture = new OrderBooksListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "orderBooksHandler", mockOrderBooksHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_orderBooks_Success() throws Exception {
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(orderRequestMessage);
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	@Test
	public void testExecute_orderBooks_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("OrderRequestMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_orderBooks(null);
	}
	
	@Test
	public void testExecute_orderBooks_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("OrderRequestMessage");
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.createEmpty_OrderRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(orderRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	@Test
	public void testExecute_orderBooks_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(orderRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	@Test
	public void testExecute_orderBooks_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(orderRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	@Test
	public void testExecute_orderBooks_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId null");
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		setupContext("dummyCorrelationId", null);
		setupMessage(orderRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	@Test
	public void testExecute_orderBooks_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId empty");
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		setupContext("dummyCorrelationId", "");
		setupMessage(orderRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_orderBooks(orderRequestMessage);
	}
	
	public void runTestExecute_orderBooks(OrderRequestMessage orderRequestMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.orderBooks(orderRequestMessage);
			
			validateAfterInvocation(orderRequestMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(OrderRequestMessage orderRequestMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockOrderBooksHandler).orderBooks(orderRequestMessage);
	}
	
}
