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

import bookshop2.BooksAvailableMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class BooksAvailableListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private BooksAvailableListenerForRMI fixture;
	
	private BooksAvailableInterceptor mockBooksAvailableInterceptor;
	
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
		mockBooksAvailableInterceptor = mock(BooksAvailableInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockBooksAvailableInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected BooksAvailableListenerForRMI createFixture() throws Exception {
		fixture = new BooksAvailableListenerForRMI();
		FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "booksAvailableInterceptor", mockBooksAvailableInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_booksAvailable_Success() throws Exception {
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.create_BooksAvailableMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(booksAvailableMessage);
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	@Test
	public void testExecute_booksAvailable_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("BooksAvailableMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_booksAvailable(null);
	}
	
	@Test
	public void testExecute_booksAvailable_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("BooksAvailableMessage");
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.createEmpty_BooksAvailableMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(booksAvailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	@Test
	public void testExecute_booksAvailable_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.create_BooksAvailableMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(booksAvailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	@Test
	public void testExecute_booksAvailable_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.create_BooksAvailableMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(booksAvailableMessage);
		isExpectedValidationError = true;
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	public void runTestExecute_booksAvailable(BooksAvailableMessage booksAvailableMessage) throws Exception {
		try {
			Message message = new Message();
			message.addPart("booksAvailableMessage",  booksAvailableMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "booksAvailable");
			
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
			verify(mockBooksAvailableInterceptor).booksAvailable(message);
	}
	
}
