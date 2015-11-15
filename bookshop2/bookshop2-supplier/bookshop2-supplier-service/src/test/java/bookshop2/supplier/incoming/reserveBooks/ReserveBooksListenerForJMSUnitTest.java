package bookshop2.supplier.incoming.reserveBooks;

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

import bookshop2.ReservationAbortedException;
import bookshop2.ReservationRequestMessage;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ReserveBooksListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private ReserveBooksListenerForJMS fixture;
	
	private ReserveBooksHandler mockReserveBooksHandler;
	
	private SupplierContext mockSupplierContext;

	
	@Override
	public String getServiceId() {
		return ReserveBooks.ID;
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
		mockReserveBooksHandler = mock(ReserveBooksHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-supplier-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSupplierContext = null;
		mockReserveBooksHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ReserveBooksListenerForJMS createFixture() throws Exception {
		fixture = new ReserveBooksListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "supplierContext", mockSupplierContext);
		FieldUtil.setFieldValue(fixture, "reserveBooksHandler", mockReserveBooksHandler);
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
		setupForExpectedAssertionFailure("Object not found in message");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(null);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("ReservationRequestMessage");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.createEmpty_ReservationRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(reservationRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(reservationRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(reservationRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId null");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(reservationRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId empty");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(reservationRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	public void runTestExecute_reserveBooks(ReservationRequestMessage reservationRequestMessage) throws Exception {
		setupBeforeInvocation(reservationRequestMessage);

		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(reservationRequestMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(ReservationRequestMessage reservationRequestMessage) throws Exception {
		if (!isExpectedValidationError)
		verify(mockReserveBooksHandler).reserveBooks(reservationRequestMessage);
	}
	
}
