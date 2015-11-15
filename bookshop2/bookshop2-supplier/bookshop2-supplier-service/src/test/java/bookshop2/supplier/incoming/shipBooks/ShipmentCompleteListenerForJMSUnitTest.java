package bookshop2.supplier.incoming.shipBooks;

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

import bookshop2.ShipmentCompleteMessage;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentCompleteListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private ShipmentCompleteListenerForJMS fixture;
	
	private ShipmentCompleteHandler mockShipmentCompleteHandler;
	
	private SupplierContext mockSupplierContext;
	
	
	@Override
	public String getServiceId() {
		return ShipmentComplete.ID;
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
		mockShipmentCompleteHandler = mock(ShipmentCompleteHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-supplier-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSupplierContext = null;
		mockShipmentCompleteHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipmentCompleteListenerForJMS createFixture() throws Exception {
		fixture = new ShipmentCompleteListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "supplierContext", mockSupplierContext);
		FieldUtil.setFieldValue(fixture, "shipmentCompleteHandler", mockShipmentCompleteHandler);
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
		setupForExpectedAssertionFailure("Object not found in message");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_shipmentComplete(null);
	}
	
	@Test
	public void testExecute_shipmentComplete_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentCompleteMessage");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.createEmpty_ShipmentCompleteMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentCompleteMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentCompleteMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentCompleteMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId null");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentCompleteMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId empty");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentCompleteMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	public void runTestExecute_shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage) throws Exception {
		setupBeforeInvocation(shipmentCompleteMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(shipmentCompleteMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(ShipmentCompleteMessage shipmentCompleteMessage) throws Exception {
		if (!isExpectedValidationError)
		verify(mockShipmentCompleteHandler).shipmentComplete(shipmentCompleteMessage);
	}
	
}
