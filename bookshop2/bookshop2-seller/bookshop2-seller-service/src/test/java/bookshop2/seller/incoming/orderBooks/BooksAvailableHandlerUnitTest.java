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

import bookshop2.BooksAvailableMessage;
import bookshop2.seller.SellerProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class BooksAvailableHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private BooksAvailableHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SellerProcess mockSellerProcess;
	
	
	public String getName() {
		return "BooksAvailable";
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
	
	protected BooksAvailableHandlerImpl createFixture() throws Exception {
		fixture = new BooksAvailableHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "sellerProcess", mockSellerProcess);
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
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_booksAvailable(null);
	}
	
	@Test
	public void testExecute_booksAvailable_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("BooksAvailableMessage must be specified");
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.createEmpty_BooksAvailableMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksAvailableMessage);
		isAbortExpected = true;
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	@Test
	public void testExecute_booksAvailable_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.create_BooksAvailableMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksAvailableMessage);
		isAbortExpected = true;
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	@Test
	public void testExecute_booksAvailable_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.create_BooksAvailableMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(booksAvailableMessage);
		isAbortExpected = true;
		runTestExecute_booksAvailable(booksAvailableMessage);
	}
	
	public void runTestExecute_booksAvailable(BooksAvailableMessage booksAvailableMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.booksAvailable(booksAvailableMessage);
			validateProcessInvocation(booksAvailableMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(BooksAvailableMessage booksAvailableMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSellerProcess).handle_BooksAvailable_response(booksAvailableMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSellerProcess).fireBooksAvailableDone();
	}

}
