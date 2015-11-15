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

import bookshop2.ShipmentFailedMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentFailedListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private ShipmentFailedListenerForRMI fixture;
	
	private ShipmentFailedInterceptor mockShipmentFailedInterceptor;
	
	private SellerContext mockSellerContext;
	
	
	@Override
	public String getServiceId() {
		return ShipmentFailed.ID;
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
		mockShipmentFailedInterceptor = mock(ShipmentFailedInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockShipmentFailedInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipmentFailedListenerForRMI createFixture() throws Exception {
		fixture = new ShipmentFailedListenerForRMI();
		FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "shipmentFailedInterceptor", mockShipmentFailedInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_shipmentFailed_Success() throws Exception {
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(shipmentFailedMessage);
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	@Test
	public void testExecute_shipmentFailed_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentFailedMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_shipmentFailed(null);
	}
	
	@Test
	public void testExecute_shipmentFailed_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentFailedMessage");
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.createEmpty_ShipmentFailedMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(shipmentFailedMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	@Test
	public void testExecute_shipmentFailed_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(shipmentFailedMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	@Test
	public void testExecute_shipmentFailed_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(shipmentFailedMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	public void runTestExecute_shipmentFailed(ShipmentFailedMessage shipmentFailedMessage) throws Exception {
		try {
			Message message = new Message();
			message.addPart("shipmentFailedMessage",  shipmentFailedMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "shipmentFailed");
			
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
			verify(mockShipmentFailedInterceptor).shipmentFailed(message);
	}
	
}
