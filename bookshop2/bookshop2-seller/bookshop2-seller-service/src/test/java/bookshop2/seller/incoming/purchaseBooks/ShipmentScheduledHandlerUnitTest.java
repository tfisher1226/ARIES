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

import bookshop2.ShipmentScheduledMessage;
import bookshop2.seller.SellerProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentScheduledHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private ShipmentScheduledHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SellerProcess mockSellerProcess;
	
	
	public String getName() {
		return "ShipmentScheduled";
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
	
	protected ShipmentScheduledHandlerImpl createFixture() throws Exception {
		fixture = new ShipmentScheduledHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "sellerProcess", mockSellerProcess);
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
		addExpectedServiceAbortedException("Incoming message is null");
		expectedCorrelationId = null;
		isRequestExpected = true;
		runTestExecute_shipmentScheduled(null);
	}
	
	@Test
	public void testExecute_shipmentScheduled_EmptyRequest() throws Exception {
		addExpectedServiceAbortedException("ShipmentScheduledMessage/shipment/date must be specified");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.createEmpty_ShipmentScheduledMessage();
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		isAbortExpected = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_NullCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId null");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		expectedCorrelationId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		isAbortExpected = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	@Test
	public void testExecute_shipmentScheduled_EmptyCorrelationId() throws Exception {
		addExpectedServiceAbortedException("CorrelationId empty");
		ShipmentScheduledMessage shipmentScheduledMessage = Bookshop2Fixture.create_ShipmentScheduledMessage();
		expectedCorrelationId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		setupMessage(shipmentScheduledMessage);
		isAbortExpected = true;
		runTestExecute_shipmentScheduled(shipmentScheduledMessage);
	}
	
	public void runTestExecute_shipmentScheduled(ShipmentScheduledMessage shipmentScheduledMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.shipmentScheduled(shipmentScheduledMessage);
			validateProcessInvocation(shipmentScheduledMessage);
			
		} catch (Throwable e) {
			validateAfterException(e);
			
		} finally {
			validateProcessNotification();
			validateAfterExecution();
		}
	}
	
	protected void validateProcessInvocation(ShipmentScheduledMessage shipmentScheduledMessage) throws Exception {
		if (!isAbortExpected)
			verify(mockSellerProcess).handle_ShipmentScheduled_response(shipmentScheduledMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSellerProcess).fireShipmentScheduledDone();
	}
	
}
