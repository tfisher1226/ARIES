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

import bookshop2.ShipmentScheduledMessage;
import bookshop2.supplier.SupplierProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentScheduledHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private ShipmentScheduledHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SupplierProcess mockSupplierProcess;
	
	
	public String getName() {
		return "ShipmentScheduled";
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
	
	protected ShipmentScheduledHandlerImpl createFixture() throws Exception {
		fixture = new ShipmentScheduledHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "supplierProcess", mockSupplierProcess);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_shipmentScheduled_Success() throws Exception {
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_NullRequest() throws Exception {
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_shipmentScheduled(null);
	}
	
	@Test
	public void testExecute_shipmentScheduled_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("ShipmentScheduledMessage/shipment/date must be specified");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.createEmpty_ShipmentScheduledMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		isAbortExpected = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId not found in message");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		isAbortExpected = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		isAbortExpected = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_NullTransactionId() throws Exception {
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(shipmentScheduledMessage);
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_EmptyTransactionId() throws Exception {
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(shipmentScheduledMessage);
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	public void runTestExecute_shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.shipmentScheduled(shipmentScheduledMessage);
			
			if (isGlobalTransactionActive())
				validateEnrollTransaction(shipmentScheduledMessage);
			validateProcessInvocation(shipmentScheduledMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(ShipmentScheduledMessage shipmentScheduledMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSupplierProcess).handle_ShipmentScheduled_response(shipmentScheduledMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSupplierProcess).fireShipmentScheduledDone();
	}
	
}
