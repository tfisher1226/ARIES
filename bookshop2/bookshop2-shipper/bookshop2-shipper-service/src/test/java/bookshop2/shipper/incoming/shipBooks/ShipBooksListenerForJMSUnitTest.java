package bookshop2.shipper.incoming.shipBooks;

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

import bookshop2.ShipmentRequestMessage;
import bookshop2.shipper.ShipperContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipBooksListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private ShipBooksListenerForJMS fixture;
	
	private ShipBooksHandler mockShipBooksHandler;
	
	private ShipperContext mockShipperContext;
	
	
	@Override
	public String getServiceId() {
		return ShipBooks.ID;
	}
	
	@Override
	public String getDomain() {
		return "bookshop2.shipper";
	}
	
	@Override
	public String getModule() {
		return "bookshop2-shipper-service";
	}
	
	public ShipperContext getMockServiceContext() {
		return mockShipperContext;
	}
	
	@Before
	public void setUp() throws Exception {
		mockShipperContext = new ShipperContext();
		mockShipBooksHandler = mock(ShipBooksHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-shipper-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockShipperContext = null;
		mockShipBooksHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipBooksListenerForJMS createFixture() throws Exception {
		fixture = new ShipBooksListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "shipperContext", mockShipperContext);
		FieldUtil.setFieldValue(fixture, "shipBooksHandler", mockShipBooksHandler);
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
		setupForExpectedAssertionFailure("Object not found in message");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(null);
	}
	
	@Test
	public void testExecute_shipBooks_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentRequestMessage");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.createEmpty_ShipmentRequestMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId null");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId empty");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	public void runTestExecute_shipBooks(ShipmentRequestMessage shipmentRequestMessage) throws Exception {
		setupBeforeInvocation(shipmentRequestMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(shipmentRequestMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(ShipmentRequestMessage shipmentRequestMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockShipBooksHandler).shipBooks(shipmentRequestMessage);
	}
	
}
