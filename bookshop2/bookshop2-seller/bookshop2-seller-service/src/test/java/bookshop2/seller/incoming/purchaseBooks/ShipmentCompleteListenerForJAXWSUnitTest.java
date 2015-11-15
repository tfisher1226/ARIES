package bookshop2.seller.incoming.purchaseBooks;

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

import bookshop2.ShipmentCompleteMessage;
import bookshop2.seller.SellerContext;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentCompleteListenerForJAXWSUnitTest extends AbstractListenerForJAXWSUnitTest {
	
	private ShipmentCompleteListenerForJAXWS fixture;
	
	private ShipmentCompleteHandler mockShipmentCompleteHandler;
	
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
		mockShipmentCompleteHandler = mock(ShipmentCompleteHandler.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		mockSellerContext = null;
		mockShipmentCompleteHandler = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipmentCompleteListenerForJAXWS createFixture() throws Exception {
		fixture = new ShipmentCompleteListenerForJAXWS();
		FieldUtil.setFieldValue(fixture, "sellerContext", mockSellerContext);
		FieldUtil.setFieldValue(fixture, "shipmentCompleteHandler", mockShipmentCompleteHandler);
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
			fixture = createFixture();
			fixture.shipmentComplete(shipmentCompleteMessage);
			
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
