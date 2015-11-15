package bookshop2.supplier.incoming.queryBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aries.common.AbstractMessage;
import org.aries.message.util.MessageConstants;
import org.aries.tx.AbstractListenerForJMSUnitTest;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.QueryRequestMessage;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class QueryBooksListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private QueryBooksListenerForJMS fixture;
	
	private QueryBooksHandler mockQueryBooksHandler;
	
	private SupplierContext mockSupplierContext;
	
	
	@Override
	public String getServiceId() {
		return QueryBooks.ID;
	}
	
	@Override
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	@Override
	public String getModule() {
		return "bookshop2-supplier-service";
	}
	
	public SupplierContext getMockServiceContext() {
		return mockSupplierContext;
	}
	
	@Before
	public void setUp() throws Exception {
		mockSupplierContext = new SupplierContext();
		mockQueryBooksHandler = mock(QueryBooksHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-supplier-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSupplierContext = null;
		mockQueryBooksHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected QueryBooksListenerForJMS createFixture() throws Exception {
		fixture = new QueryBooksListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "supplierContext", mockSupplierContext);
		FieldUtil.setFieldValue(fixture, "queryBooksHandler", mockQueryBooksHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_queryBooks_Success() throws Exception {
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(queryRequestMessage);
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("Object not found in message");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(null);
	}
	
	@Test
	public void testExecute_queryBooks_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("QueryRequestMessage");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.createEmpty_QueryRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(queryRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(queryRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(queryRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId null");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(queryRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId empty");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(queryRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	public void runTestExecute_queryBooks(QueryRequestMessage queryRequestMessage) throws Exception {
		setupBeforeInvocation(queryRequestMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(queryRequestMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(QueryRequestMessage queryRequestMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockQueryBooksHandler).queryBooks(queryRequestMessage);
	}
	
}
