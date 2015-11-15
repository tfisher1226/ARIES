package bookshop2.supplier.incoming.reserveBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aries.tx.AbstractListenerForJAXWSUnitTest;
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
public class ReserveBooksListenerForJAXWSUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private ReserveBooksListenerForJAXWS fixture;
	
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
	
	protected ReserveBooksListenerForJAXWS createFixture() throws Exception {
		fixture = new ReserveBooksListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "supplierContext", mockSupplierContext);
		FieldUtil.setFieldValue(fixture, "reserveBooksHandler", mockReserveBooksHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_reserveBooks_Success() throws Exception {
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(reservationRequestMessage);
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_NullRequest() throws Exception {
		setupForExpectedException(ReservationAbortedException.class, "ReservationRequestMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(null);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyRequest() throws Exception {
		setupForExpectedException(ReservationAbortedException.class, "ReservationRequestMessage");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.createEmpty_ReservationRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(reservationRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_NullCorrelationId() throws Exception {
		setupForExpectedException(ReservationAbortedException.class, "CorrelationId null");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(reservationRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyCorrelationId() throws Exception {
		setupForExpectedException(ReservationAbortedException.class, "CorrelationId empty");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(reservationRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_NullTransactionId() throws Exception {
		setupForExpectedException(ReservationAbortedException.class, "TransactionId null");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		setupContext("dummyCorrelationId", null);
		setupMessage(reservationRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyTransactionId() throws Exception {
		setupForExpectedException(ReservationAbortedException.class, "TransactionId empty");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		setupContext("dummyCorrelationId", "");
		setupMessage(reservationRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	public void runTestExecute_reserveBooks(ReservationRequestMessage reservationRequestMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.reserveBooks(reservationRequestMessage);
			
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
