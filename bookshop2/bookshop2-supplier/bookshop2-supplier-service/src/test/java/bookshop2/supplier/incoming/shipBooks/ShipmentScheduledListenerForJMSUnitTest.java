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

import bookshop2.ShipmentScheduledMessage;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentScheduledListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private ShipmentScheduledListenerForJMS fixture;
	
	private ShipmentScheduledHandler mockShipmentScheduledHandler;
	
	private SupplierContext mockSupplierContext;
	
	
	@Override
	public String getServiceId() {
		return ShipmentScheduled.ID;
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
		mockShipmentScheduledHandler = mock(ShipmentScheduledHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-supplier-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSupplierContext = null;
		mockShipmentScheduledHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipmentScheduledListenerForJMS createFixture() throws Exception {
		fixture = new ShipmentScheduledListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "supplierContext", mockSupplierContext);
		FieldUtil.setFieldValue(fixture, "shipmentScheduledHandler", mockShipmentScheduledHandler);
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
		setupForExpectedAssertionFailure("Object not found in message");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_shipmentScheduled(null);
	}
	
	@Test
	public void testExecute_shipmentScheduled_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentScheduledMessage");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.createEmpty_ShipmentScheduledMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId null");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId empty");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	public void runTestExecute_shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage) throws Exception {
		setupBeforeInvocation(shipmentScheduledMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(shipmentScheduledMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(ShipmentScheduledMessage shipmentScheduledMessage) throws Exception {
		if (!isExpectedValidationError)
		verify(mockShipmentScheduledHandler).shipmentScheduled(shipmentScheduledMessage);
	}
	
}
