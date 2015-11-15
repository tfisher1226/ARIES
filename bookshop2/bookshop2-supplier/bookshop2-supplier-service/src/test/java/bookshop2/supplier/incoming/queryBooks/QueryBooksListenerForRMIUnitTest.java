package bookshop2.supplier.incoming.queryBooks;

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

import bookshop2.QueryRequestMessage;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class QueryBooksListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private QueryBooksListenerForRMI fixture;
	
	private QueryBooksInterceptor mockQueryBooksInterceptor;
	
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
		mockQueryBooksInterceptor = mock(QueryBooksInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-supplier-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSupplierContext = null;
		mockQueryBooksInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected QueryBooksListenerForRMI createFixture() throws Exception {
		fixture = new QueryBooksListenerForRMI();
		FieldUtil.setFieldValue(fixture, "supplierContext", mockSupplierContext);
		FieldUtil.setFieldValue(fixture, "queryBooksInterceptor", mockQueryBooksInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_queryBooks_Success() throws Exception {
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(queryRequestMessage);
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("QueryRequestMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_queryBooks(null);
	}
	
	@Test
	public void testExecute_queryBooks_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("QueryRequestMessage");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.createEmpty_QueryRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(queryRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(queryRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(queryRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId null");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		setupContext("dummyCorrelationId", null);
		setupMessage(queryRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId empty");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		setupContext("dummyCorrelationId", "");
		setupMessage(queryRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	public void runTestExecute_queryBooks(QueryRequestMessage queryRequestMessage) throws Exception {
		try {
			Message message = new Message();
			message.addPart("queryRequestMessage",  queryRequestMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "queryBooks");
			
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
			verify(mockQueryBooksInterceptor).queryBooks(message);
	}
	
}
