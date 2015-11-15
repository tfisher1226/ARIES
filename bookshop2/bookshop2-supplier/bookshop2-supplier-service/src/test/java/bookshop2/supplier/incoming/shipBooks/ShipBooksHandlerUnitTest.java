package bookshop2.supplier.incoming.shipBooks;

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

import bookshop2.ShipmentRequestMessage;
import bookshop2.supplier.SupplierProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipBooksHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private ShipBooksHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SupplierProcess mockSupplierProcess;

	
	public String getName() {
		return "ShipBooks";
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
	
	protected ShipBooksHandlerImpl createFixture() throws Exception {
		fixture = new ShipBooksHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "supplierProcess", mockSupplierProcess);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_shipBooks_Success() throws Exception {
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentRequestMessage);
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_NullRequest() throws Exception {
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_shipBooks(null);
	}
	
	@Test
	public void testExecute_shipBooks_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("ShipmentRequestMessage/shipment/date must be specified");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.createEmpty_ShipmentRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentRequestMessage);
		isAbortExpected = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentRequestMessage);
		isAbortExpected = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentRequestMessage);
		isAbortExpected = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_NullTransactionId() throws Exception {
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(shipmentRequestMessage);
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_EmptyTransactionId() throws Exception {
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(shipmentRequestMessage);
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	public void runTestExecute_shipBooks(ShipmentRequestMessage shipmentRequestMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.shipBooks(shipmentRequestMessage);
			
			if (isGlobalTransactionActive())
				validateEnrollTransaction(shipmentRequestMessage);
			validateProcessInvocation(shipmentRequestMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(ShipmentRequestMessage shipmentRequestMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSupplierProcess).handle_ShipBooks_request(shipmentRequestMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSupplierProcess).fireShipBooksDone();
	}
	
	protected void validateAfterExecution() throws Exception {
		if (isAbortExpected)
			verify(mockSupplierProcess).handle_ShipBooks_request_exception(expectedCorrelationId, expectedException);
		super.validateAfterExecution();
	}
	
}
