package bookshop2.seller.incoming.purchaseBooks;

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

import bookshop2.ShipmentFailedMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentFailedListenerForJMSUnitTest extends AbstractListenerForJMSUnitTest {
	
	private ShipmentFailedListenerForJMS fixture;
	
	private ShipmentFailedHandler mockShipmentFailedHandler;
	
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
		mockShipmentFailedHandler = mock(ShipmentFailedHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockShipmentFailedHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipmentFailedListenerForJMS createFixture() throws Exception {
		fixture = new ShipmentFailedListenerForJMS();
		//FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "shipmentFailedHandler", mockShipmentFailedHandler);
		initialize(fixture);
		return fixture;
	}
	
	@Test
	public void testExecute_shipmentFailed_Success() throws Exception {
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentFailedMessage);
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	@Test
	public void testExecute_shipmentFailed_NullRequest() throws Exception {
		setupForExpectedAssertionFailure("Object not found in message");
		setupContext(expectedCorrelationId, expectedTransactionId);
		isExpectedValidationError = true;
		runTestExecute_shipmentFailed(null);
	}
	
	@Test
	public void testExecute_shipmentFailed_EmptyRequest() throws Exception {
		setupForExpectedAssertionFailure("ShipmentFailedMessage");
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.createEmpty_ShipmentFailedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentFailedMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	@Test
	public void testExecute_shipmentFailed_NullCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId not found in message");
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentFailedMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	@Test
	public void testExecute_shipmentFailed_EmptyCorrelationId() throws Exception {
		setupForExpectedAssertionFailure("CorrelationId empty");
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentFailedMessage);
		isExpectedValidationError = true;
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	public void runTestExecute_shipmentFailed(ShipmentFailedMessage shipmentFailedMessage) throws Exception {
		setupBeforeInvocation(shipmentFailedMessage);
		
		try {
			fixture = createFixture();
			fixture.onMessage(mockMessage);
			
			validateAfterInvocation(shipmentFailedMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateAfterExecution();
		}
	}
	
	public void validateAfterInvocation(ShipmentFailedMessage shipmentFailedMessage) throws Exception {
		if (!isExpectedValidationError)
			verify(mockShipmentFailedHandler).shipmentFailed(shipmentFailedMessage);
	}
	
}
