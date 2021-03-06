package bookshop2.seller.incoming.purchaseBooks;

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

import bookshop2.ShipmentScheduledMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentScheduledListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private ShipmentScheduledListenerForRMI fixture;
	
	private ShipmentScheduledInterceptor mockShipmentScheduledInterceptor;
	
	private SellerContext mockSellerContext;
	
	
	@Override
	public String getServiceId() {
		return ShipmentScheduled.ID;
	}
	
	@Override
	public String getDomain() {
		return "bookshop2.seller";
	}
	
	@Override
	public String getModule() {
		return "bookshop2-seller-service";
	}
	
	public SellerContext getMockServiceContext() {
		return mockSellerContext;
	}
	
	@Before
	public void setUp() throws Exception {
		mockSellerContext = new SellerContext();
		mockShipmentScheduledInterceptor = mock(ShipmentScheduledInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockShipmentScheduledInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipmentScheduledListenerForRMI createFixture() throws Exception {
		fixture = new ShipmentScheduledListenerForRMI();
		FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "shipmentScheduledInterceptor", mockShipmentScheduledInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_shipmentScheduled_Success() throws Exception {
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(shipmentScheduledMessage);
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentScheduledMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_shipmentScheduled(null);
	}
	
	@Test
	public void testExecute_shipmentScheduled_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentScheduledMessage");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.createEmpty_ShipmentScheduledMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(shipmentScheduledMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(shipmentScheduledMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(shipmentScheduledMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	public void runTestExecute_shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage) throws Exception {
		try {
			Message message = new Message();
			message.addPart("shipmentScheduledMessage",  shipmentScheduledMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "shipmentScheduled");
			
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
			verify(mockShipmentScheduledInterceptor).shipmentScheduled(message);
	}
	
}
