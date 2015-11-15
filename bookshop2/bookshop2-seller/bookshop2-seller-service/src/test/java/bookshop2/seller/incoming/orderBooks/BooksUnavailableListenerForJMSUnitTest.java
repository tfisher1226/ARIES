package bookshop2.seller.incoming.orderBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aries.tx.AbstractListenerForJMSUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.BooksUnavailableMessage;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class BooksUnavailableListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private BooksUnavailableListenerForJMS fixture;
	
	private BooksUnavailableHandler mockBooksUnavailableHandler;
	
	private SellerContext mockSellerContext;
	
	
	@Override
	public String getServiceId() {
		return BooksUnavailable.ID;
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
		mockBooksUnavailableHandler = mock(BooksUnavailableHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockBooksUnavailableHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected BooksUnavailableListenerForJMS createFixture() throws Exception {
		fixture = new BooksUnavailableListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "booksUnavailableHandler", mockBooksUnavailableHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_booksUnavailable_Success() throws Exception {
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.create_BooksUnavailableMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksUnavailableMessage);
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	@Test
	public void testExecute_booksUnavailable_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("Payload not found in message");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_booksUnavailable(null);
	}
	
	@Test
	public void testExecute_booksUnavailable_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("BooksUnavailableMessage");
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.createEmpty_BooksUnavailableMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksUnavailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	@Test
	public void testExecute_booksUnavailable_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.create_BooksUnavailableMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksUnavailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	@Test
	public void testExecute_booksUnavailable_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.create_BooksUnavailableMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksUnavailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	public void runTestExecute_booksUnavailable(BooksUnavailableMessage booksUnavailableMessage) throws Exception {
		setupBeforeInvocation(booksUnavailableMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(booksUnavailableMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(BooksUnavailableMessage booksUnavailableMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockBooksUnavailableHandler).booksUnavailable(booksUnavailableMessage);
	}
	
}
