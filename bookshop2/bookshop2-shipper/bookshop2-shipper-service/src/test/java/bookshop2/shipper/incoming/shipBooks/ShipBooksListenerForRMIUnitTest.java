package bookshop2.shipper.incoming.shipBooks;

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

import bookshop2.ShipmentRequestMessage;
import bookshop2.shipper.ShipperContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipBooksListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private ShipBooksListenerForRMI fixture;
	
	private ShipBooksInterceptor mockShipBooksInterceptor;
	
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
		mockShipBooksInterceptor = mock(ShipBooksInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-shipper-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockShipperContext = null;
		mockShipBooksInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipBooksListenerForRMI createFixture() throws Exception {
		fixture = new ShipBooksListenerForRMI();
		FieldUtil.setFieldValue(fixture, "shipperContext", mockShipperContext);
		FieldUtil.setFieldValue(fixture, "shipBooksInterceptor", mockShipBooksInterceptor);
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
			Message message = new Message();
			message.addPart("shipmentRequestMessage",  shipmentRequestMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "shipBooks");
			
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
			verify(mockShipBooksInterceptor).shipBooks(message);
	}
	
}
