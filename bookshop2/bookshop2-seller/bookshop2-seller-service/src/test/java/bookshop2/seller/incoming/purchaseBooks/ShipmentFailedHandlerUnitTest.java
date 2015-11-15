package bookshop2.seller.incoming.purchaseBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.aries.runtime.BeanContext;
import org.aries.runtime.RequestContext;
import org.aries.tx.AbstractHandlerUnitTest;
import org.aries.tx.Transactional;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.ShipmentFailedMessage;
import bookshop2.seller.SellerProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentFailedHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private ShipmentFailedHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SellerProcess mockSellerProcess;
	
	
	public String getName() {
		return "ShipmentFailed";
	}
	
	public String getDomain() {
		return "bookshop2.seller";
	}
	
	public Transactional getFixture() {
		return fixture;
	}
	
	public SellerProcess getMockServiceProcess() {
		return mockSellerProcess;
	}
	
	@Before
	public void setUp() throws Exception {
		mockRequestContext = mock(RequestContext.class);
		mockSellerProcess = mock(SellerProcess.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-seller-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		mockRequestContext = null;
		mockSellerProcess = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipmentFailedHandlerImpl createFixture() throws Exception {
		fixture = new ShipmentFailedHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "sellerProcess", mockSellerProcess);
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
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_shipmentFailed(null);
	}
	
	@Test
	public void testExecute_shipmentFailed_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("ShipmentFailedMessage/reason must be specified");
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.createEmpty_ShipmentFailedMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentFailedMessage);
		isAbortExpected = true;
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	@Test
	public void testExecute_shipmentFailed_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentFailedMessage);
		isAbortExpected = true;
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	@Test
	public void testExecute_shipmentFailed_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentFailedMessage);
		isAbortExpected = true;
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	public void runTestExecute_shipmentFailed(ShipmentFailedMessage shipmentFailedMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.shipmentFailed(shipmentFailedMessage);
			validateProcessInvocation(shipmentFailedMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(ShipmentFailedMessage shipmentFailedMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSellerProcess).handle_ShipmentFailed_response(shipmentFailedMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSellerProcess).fireShipmentFailedDone();
	}
	
}
