package bookshop2.seller.incoming.purchaseBooks;

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

import bookshop2.PurchaseRequestMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseBooksListenerForJAXWSUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private PurchaseBooksListenerForJAXWS fixture;
	
	private PurchaseBooksHandler mockPurchaseBooksHandler;
	
	private SellerContext mockSellerContext;
	
	
	@Override
	public String getServiceId() {
		return PurchaseBooks.ID;
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
		mockPurchaseBooksHandler = mock(PurchaseBooksHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockPurchaseBooksHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected PurchaseBooksListenerForJAXWS createFixture() throws Exception {
		fixture = new PurchaseBooksListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "purchaseBooksHandler", mockPurchaseBooksHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_purchaseBooks_Success() throws Exception {
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(purchaseRequestMessage);
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("PurchaseRequestMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(null);
	}
	
	@Test
	public void testExecute_purchaseBooks_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("PurchaseRequestMessage");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.createEmpty_PurchaseRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(purchaseRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(purchaseRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(purchaseRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId null");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext("dummyCorrelationId", null);
		setupMessage(purchaseRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId empty");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext("dummyCorrelationId", "");
		setupMessage(purchaseRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	public void runTestExecute_purchaseBooks(PurchaseRequestMessage purchaseRequestMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.purchaseBooks(purchaseRequestMessage);
			
			validateAfterInvocation(purchaseRequestMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(PurchaseRequestMessage purchaseRequestMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockPurchaseBooksHandler).purchaseBooks(purchaseRequestMessage);
	}
	
}
