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

import bookshop2.BooksAvailableMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class BooksAvailableListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private BooksAvailableListenerForJMS fixture;
	
	private BooksAvailableHandler mockBooksAvailableHandler;
	
	private SellerContext mockSellerContext;
	
	
	@Override
	public String getServiceId() {
		return BooksAvailable.ID;
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
		mockBooksAvailableHandler = mock(BooksAvailableHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockBooksAvailableHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected BooksAvailableListenerForJMS createFixture() throws Exception {
		fixture = new BooksAvailableListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "booksAvailableHandler", mockBooksAvailableHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_booksAvailable_Success() throws Exception {
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.create_BooksAvailableMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksAvailableMessage);
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	@Test
	public void testExecute_booksAvailable_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("Payload not found in message");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_booksAvailable(null);
	}
	
	@Test
	public void testExecute_booksAvailable_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("BooksAvailableMessage");
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.createEmpty_BooksAvailableMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksAvailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	@Test
	public void testExecute_booksAvailable_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.create_BooksAvailableMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksAvailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	@Test
	public void testExecute_booksAvailable_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.create_BooksAvailableMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksAvailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	public void runTestExecute_booksAvailable(BooksAvailableMessage booksAvailableMessage) throws Exception {
		setupBeforeInvocation(booksAvailableMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(booksAvailableMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(BooksAvailableMessage booksAvailableMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockBooksAvailableHandler).booksAvailable(booksAvailableMessage);
	}
	
}
