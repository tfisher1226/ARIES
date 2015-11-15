package bookshop2.supplier.incoming.shipBooks;

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

import bookshop2.ShipmentRequestMessage;
import bookshop2.supplier.SupplierContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipBooksListenerForJAXWSUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private ShipBooksListenerForJAXWS fixture;
	
	private ShipBooksHandler mockShipBooksHandler;
	
	private SupplierContext mockSupplierContext;
	
	
	@Override
	public String getServiceId() {
		return ShipBooks.ID;
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
		mockShipBooksHandler = mock(ShipBooksHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-supplier-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSupplierContext = null;
		mockShipBooksHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipBooksListenerForJAXWS createFixture() throws Exception {
		fixture = new ShipBooksListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "supplierContext", mockSupplierContext);
		FieldUtil.setFieldValue(fixture, "shipBooksHandler", mockShipBooksHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_shipBooks_Success() throws Exception {
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(shipmentRequestMessage);
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentRequestMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_shipBooks(null);
	}
	
	@Test
	public void testExecute_shipBooks_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentRequestMessage");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.createEmpty_ShipmentRequestMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(shipmentRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(shipmentRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(shipmentRequestMessage);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_NullTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId null");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		setupContext("dummyCorrelationId", null);
		setupMessage(shipmentRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	@Test
	public void testExecute_shipBooks_EmptyTransactionId() throws Exception {
		setupForExpectedAssertionFailure("TransactionId empty");
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();
		setupContext("dummyCorrelationId", "");
		setupMessage(shipmentRequestMessage);
		setGlobalTransactionActive(true);
		isExpectedValidationError = true;
		runTestExecute_shipBooks(shipmentRequestMessage);
	}
	
	public void runTestExecute_shipBooks(ShipmentRequestMessage shipmentRequestMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.shipBooks(shipmentRequestMessage);
			
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
