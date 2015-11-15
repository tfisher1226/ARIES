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

import bookshop2.BooksUnavailableMessage;
import bookshop2.seller.SellerProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class BooksUnavailableHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private BooksUnavailableHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SellerProcess mockSellerProcess;
	
	
	public String getName() {
		return "BooksUnavailable";
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
	
	protected BooksUnavailableHandlerImpl createFixture() throws Exception {
		fixture = new BooksUnavailableHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "sellerProcess", mockSellerProcess);
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
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_booksUnavailable(null);
	}
	
	@Test
	public void testExecute_booksUnavailable_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("BooksUnavailableMessage must be specified");
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.createEmpty_BooksUnavailableMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksUnavailableMessage);
		isAbortExpected = true;
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	@Test
	public void testExecute_booksUnavailable_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.create_BooksUnavailableMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksUnavailableMessage);
		isAbortExpected = true;
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	@Test
	public void testExecute_booksUnavailable_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		BooksUnavailableMessage booksUnavailableMessage = Bookshop2Fixture.create_BooksUnavailableMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksUnavailableMessage);
		isAbortExpected = true;
		runTestExecute_booksUnavailable(booksUnavailableMessage);
	}
	
	public void runTestExecute_booksUnavailable(BooksUnavailableMessage booksUnavailableMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.booksUnavailable(booksUnavailableMessage);
			validateProcessInvocation(booksUnavailableMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(BooksUnavailableMessage booksUnavailableMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSellerProcess).handle_BooksUnavailable_response(booksUnavailableMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSellerProcess).fireBooksUnavailableDone();
	}
	
}
