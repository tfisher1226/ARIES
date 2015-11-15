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

import bookshop2.ShipmentCompleteMessage;
import bookshop2.supplier.SupplierProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentCompleteHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private ShipmentCompleteHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SupplierProcess mockSupplierProcess;
	
	
	public String getName() {
		return "ShipmentComplete";
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
	
	protected ShipmentCompleteHandlerImpl createFixture() throws Exception {
		fixture = new ShipmentCompleteHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "supplierProcess", mockSupplierProcess);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_shipmentComplete_Success() throws Exception {
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentCompleteMessage);
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_NullRequest() throws Exception {
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		//isAbortExpected = true;
		runTestExecute_shipmentComplete(null);
	}
	
	@Test
	public void testExecute_shipmentComplete_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("ShipmentCompleteMessage/shipment/date must be specified");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.createEmpty_ShipmentCompleteMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentCompleteMessage);
		isAbortExpected = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId not found in message");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentCompleteMessage);
		isAbortExpected = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentCompleteMessage);
		isAbortExpected = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_NullTransactionId() throws Exception {
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(shipmentCompleteMessage);
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_EmptyTransactionId() throws Exception {
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(shipmentCompleteMessage);
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	public void runTestExecute_shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.shipmentComplete(shipmentCompleteMessage);
			
			if (isGlobalTransactionActive())
				validateEnrollTransaction(shipmentCompleteMessage);
			validateProcessInvocation(shipmentCompleteMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(ShipmentCompleteMessage shipmentCompleteMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSupplierProcess).handle_ShipmentComplete_response(shipmentCompleteMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSupplierProcess).fireShipmentCompleteDone();
	}
	
}
