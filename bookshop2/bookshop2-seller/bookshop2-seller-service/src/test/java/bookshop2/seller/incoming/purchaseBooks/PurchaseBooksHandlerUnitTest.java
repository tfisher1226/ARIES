package bookshop2.seller.incoming.purchaseBooks;

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

import bookshop2.PurchaseRequestMessage;
import bookshop2.seller.SellerProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseBooksHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private PurchaseBooksHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SellerProcess mockSellerProcess;
	
	
	public String getName() {
		return "PurchaseBooks";
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
	
	protected PurchaseBooksHandlerImpl createFixture() throws Exception {
		fixture = new PurchaseBooksHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "sellerProcess", mockSellerProcess);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_purchaseBooks_Success() throws Exception {
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseRequestMessage);
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_NullRequest() throws Exception {
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_purchaseBooks(null);
	}
	
	@Test
	public void testExecute_purchaseBooks_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("PurchaseRequestMessage/name/lastName must be specified");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.createEmpty_PurchaseRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseRequestMessage);
		isAbortExpected = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseRequestMessage);
		isAbortExpected = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(purchaseRequestMessage);
		isAbortExpected = true;
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_NullTransactionId() throws Exception {
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(purchaseRequestMessage);
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	@Test
	public void testExecute_purchaseBooks_EmptyTransactionId() throws Exception {
		PurchaseRequestMessage purchaseRequestMessage = Bookshop2Fixture.create_PurchaseRequestMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(purchaseRequestMessage);
		runTestExecute_purchaseBooks(purchaseRequestMessage);
	}
	
	public void runTestExecute_purchaseBooks(PurchaseRequestMessage purchaseRequestMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.purchaseBooks(purchaseRequestMessage);
			
			if (isGlobalTransactionActive())
				validateEnrollTransaction(purchaseRequestMessage);
			validateProcessInvocation(purchaseRequestMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(PurchaseRequestMessage purchaseRequestMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSellerProcess).handle_PurchaseBooks_request(purchaseRequestMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSellerProcess).firePurchaseBooksDone();
	}
	
	protected void validateAfterExecution() throws Exception {
		if (isAbortExpected)
			verify(mockSellerProcess).handle_PurchaseBooks_request_exception(expectedCorrelationId, expectedException);
		super.validateAfterExecution();
	}
	
}
