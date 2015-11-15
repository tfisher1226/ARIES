package bookshop2.supplier.incoming.reserveBooks;

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

import bookshop2.ReservationRequestMessage;
import bookshop2.supplier.SupplierProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ReserveBooksHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private ReserveBooksHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SupplierProcess mockSupplierProcess;
	
	
	public String getName() {
		return "ReserveBooks";
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
	
	protected ReserveBooksHandlerImpl createFixture() throws Exception {
		fixture = new ReserveBooksHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "supplierProcess", mockSupplierProcess);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_reserveBooks_Success() throws Exception {
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(reservationRequestMessage);
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_NullRequest() throws Exception {
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_reserveBooks(null);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("ReservationRequestMessage must include one or more Book(s)");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.createEmpty_ReservationRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(reservationRequestMessage);
		isAbortExpected = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(reservationRequestMessage);
		isAbortExpected = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(reservationRequestMessage);
		isAbortExpected = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_NullTransactionId() throws Exception {
		addExpectedServiceAbortedException("TransactionId null");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(reservationRequestMessage);
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyTransactionId() throws Exception {
		addExpectedServiceAbortedException("TransactionId empty");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(reservationRequestMessage);
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	public void runTestExecute_reserveBooks(ReservationRequestMessage reservationRequestMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.reserveBooks(reservationRequestMessage);
			
			if (isGlobalTransactionActive())
				validateEnrollTransaction(reservationRequestMessage);
			validateProcessInvocation(reservationRequestMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(ReservationRequestMessage reservationRequestMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSupplierProcess).handle_ReserveBooks_request(reservationRequestMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSupplierProcess).fireReserveBooksDone();
	}
	
	protected void validateAfterExecution() throws Exception {
		if (isAbortExpected)
			verify(mockSupplierProcess).handle_ReserveBooks_request_exception(expectedCorrelationId, expectedException);
		super.validateAfterExecution();
	}
	
}
