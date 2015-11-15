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

import bookshop2.ShipmentCompleteMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentCompleteListenerForRMIUnitTest extends AbstractListenerForRMIUnitTest {
	
	private ShipmentCompleteListenerForRMI fixture;
	
	private ShipmentCompleteInterceptor mockShipmentCompleteInterceptor;
	
	private SellerContext mockSellerContext;
	
	
	@Override
	public String getServiceId() {
		return ShipmentComplete.ID;
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
		mockShipmentCompleteInterceptor = mock(ShipmentCompleteInterceptor.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockShipmentCompleteInterceptor = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipmentCompleteListenerForRMI createFixture() throws Exception {
		fixture = new ShipmentCompleteListenerForRMI();
		FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "shipmentCompleteInterceptor", mockShipmentCompleteInterceptor);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_shipmentComplete_Success() throws Exception {
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(shipmentCompleteMessage);
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentCompleteMessage");
		setupContext("dummyCorrelationId", "dummyTransactionId");
		isExpectedValidationError = true;
		runTestExecute_shipmentComplete(null);
	}
	
	@Test
	public void testExecute_shipmentComplete_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentCompleteMessage");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.createEmpty_ShipmentCompleteMessage();
		setupContext("dummyCorrelationId", "dummyTransactionId");
		setupMessage(shipmentCompleteMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId null");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		setupContext(null, "dummyTransactionId");
		setupMessage(shipmentCompleteMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	@Test
	public void testExecute_shipmentComplete_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		ShipmentCompleteMessage shipmentCompleteMessage = Bookshop2Fixture.create_ShipmentCompleteMessage();
		setupContext("", "dummyTransactionId");
		setupMessage(shipmentCompleteMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentComplete(shipmentCompleteMessage);
	}
	
	public void runTestExecute_shipmentComplete(ShipmentCompleteMessage shipmentCompleteMessage) throws Exception {
		try {
			Message message = new Message();
			message.addPart("shipmentCompleteMessage",  shipmentCompleteMessage);
			message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, "shipmentComplete");
			
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
			verify(mockShipmentCompleteInterceptor).shipmentComplete(message);
	}
	
}
