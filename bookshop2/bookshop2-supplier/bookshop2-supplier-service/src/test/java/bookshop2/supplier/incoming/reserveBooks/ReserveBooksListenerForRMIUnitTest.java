package bookshop2.supplier.incoming.reserveBooks;

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

import bookshop2.ReservationAbortedException;
import bookshop2.ReservationRequestMessage;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ReserveBooksListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private ReserveBooksListenerForRMI fixture;
	
	private ReserveBooksInterceptor mockReserveBooksInterceptor;
	
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
		mockReserveBooksInterceptor = mock(ReserveBooksInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-supplier-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSupplierContext = null;
		mockReserveBooksInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ReserveBooksListenerForRMI createFixture() throws Exception {
		fixture = new ReserveBooksListenerForRMI();
		FieldUtil.setFieldValue(fixture, "supplierContext", mockSupplierContext);
		FieldUtil.setFieldValue(fixture, "reserveBooksInterceptor", mockReserveBooksInterceptor);
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
		setupForExpectedAssertionFailure(ReservationAbortedException.class, "ReservationRequestMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(null);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure(ReservationAbortedException.class, "ReservationRequestMessage");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.createEmpty_ReservationRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(reservationRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure(ReservationAbortedException.class, "CorrelationId null");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(reservationRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure(ReservationAbortedException.class, "CorrelationId empty");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(reservationRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure(ReservationAbortedException.class, "TransactionId null");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		setupContext("dummyCorrelationId", null);
		setupMessage(reservationRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	@Test
	public void testExecute_reserveBooks_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure(ReservationAbortedException.class, "TransactionId empty");
		ReservationRequestMessage reservationRequestMessage = Bookshop2Fixture.create_ReservationRequestMessage();
		setupContext("dummyCorrelationId", "");
		setupMessage(reservationRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_reserveBooks(reservationRequestMessage);
	}
	
	public void runTestExecute_reserveBooks(ReservationRequestMessage reservationRequestMessage) throws Exception {
		try {
			Message message = new Message();
			message.addPart("reservationRequestMessage",  reservationRequestMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "reserveBooks");
			
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
			verify(mockReserveBooksInterceptor).reserveBooks(message);
	}
	
}
