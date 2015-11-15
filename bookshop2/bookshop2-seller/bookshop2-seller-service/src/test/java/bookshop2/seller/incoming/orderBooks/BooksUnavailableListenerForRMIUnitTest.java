package bookshop2.seller.incoming.orderBooks;

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

import bookshop2.BooksUnavailableMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class BooksUnavailableListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private BooksUnavailableListenerForRMI fixture;
	
	private BooksUnavailableInterceptor mockBooksUnavailableInterceptor;
	
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
		mockBooksUnavailableInterceptor = mock(BooksUnavailableInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockBooksUnavailableInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected BooksUnavailableListenerForRMI createFixture() throws Exception {
		fixture = new BooksUnavailableListenerForRMI();
		FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "booksUnavailableInterceptor", mockBooksUnavailableInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_booksUnavailable_Success() throws Exception {
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.create_BooksUnavailableMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(booksUnavailableMessage);
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	@Test
	public void testExecute_booksUnavailable_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("BooksUnavailableMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_booksUnavailable(null);
	}
	
	@Test
	public void testExecute_booksUnavailable_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("BooksUnavailableMessage");
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.createEmpty_BooksUnavailableMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(booksUnavailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	@Test
	public void testExecute_booksUnavailable_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.create_BooksUnavailableMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(booksUnavailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	@Test
	public void testExecute_booksUnavailable_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.create_BooksUnavailableMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(booksUnavailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	public void runTestExecute_booksUnavailable(BooksUnavailableMessage booksUnavailableMessage) throws Exception {
		try {
			Message message = new Message();
			message.addPart("booksUnavailableMessage",  booksUnavailableMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "booksUnavailable");
			
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
			verify(mockBooksUnavailableInterceptor).booksUnavailable(message);
	}
	
}
