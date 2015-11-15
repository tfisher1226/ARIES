package bookshop2.supplier.incoming.queryBooks;

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

import bookshop2.QueryRequestMessage;
import bookshop2.supplier.SupplierProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class QueryBooksHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private QueryBooksHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SupplierProcess mockSupplierProcess;
	
	
	public String getName() {
		return "QueryBooks";
	}
	
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	public Transactional getFixture() {
		return fixture;
	}
	
	public SupplierProcess getMockServiceProcess() {
		return mockSupplierProcess;
	}
	
	@Before
	public void setUp() throws Exception {
		mockRequestContext = mock(RequestContext.class);
		mockSupplierProcess = mock(SupplierProcess.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-supplier-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		mockRequestContext = null;
		mockSupplierProcess = null;
		fixture = null;
		super.tearDown();
	}
	
	protected QueryBooksHandlerImpl createFixture() throws Exception {
		fixture = new QueryBooksHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "supplierProcess", mockSupplierProcess);
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
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_queryBooks(null);
	}
	
	@Test
	public void testExecute_queryBooks_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("QueryRequestMessage must include one or more Book(s)");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.createEmpty_QueryRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(queryRequestMessage);
		isAbortExpected = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(queryRequestMessage);
		isAbortExpected = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}

	@Test
	public void testExecute_queryBooks_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(queryRequestMessage);
		isAbortExpected = true;
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_NullTransactionId() throws Exception {
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(queryRequestMessage);
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	@Test
	public void testExecute_queryBooks_EmptyTransactionId() throws Exception {
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(queryRequestMessage);
		runTestExecute_queryBooks(queryRequestMessage);
	}
	
	public void runTestExecute_queryBooks(QueryRequestMessage queryRequestMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.queryBooks(queryRequestMessage);
			
			if (isGlobalTransactionActive())
				validateEnrollTransaction(queryRequestMessage);
			validateProcessInvocation(queryRequestMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(QueryRequestMessage queryRequestMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSupplierProcess).handle_QueryBooks_request(queryRequestMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSupplierProcess).fireQueryBooksDone();
	}
	
	protected void validateAfterExecution() throws Exception {
		if (isAbortExpected)
			verify(mockSupplierProcess).handle_QueryBooks_request_exception(expectedCorrelationId, expectedException);
		super.validateAfterExecution();
	}
	
}
