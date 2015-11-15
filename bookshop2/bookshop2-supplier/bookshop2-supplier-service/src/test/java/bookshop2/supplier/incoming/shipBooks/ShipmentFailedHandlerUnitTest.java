package bookshop2.supplier.incoming.shipBooks;

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
import bookshop2.supplier.SupplierProcess;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class ShipmentFailedHandlerUnitTest extends AbstractHandlerUnitTest {
	
	private ShipmentFailedHandlerImpl fixture;
	
	private RequestContext mockRequestContext;
	
	private SupplierProcess mockSupplierProcess;
	
	
	public String getName() {
		return "ShipmentFailed";
	}
	
	public String getDomain() {
		return "bookshop2.supplier";
	}
	
	public Transactional getFixture() {
		return fixture;
	}
	
	public SupplierProcess getMockServiceProcess() {
		return mockSupplierProcess;
	}
	
	@Before
	public void setUp() throws Exception {
		mockRequestContext = mock(RequestContext.class);
		mockSupplierProcess = mock(SupplierProcess.class);
		CheckpointManager.setJAXBSessionCache(getJAXBSessionCache());
		CheckpointManager.addConfiguration("bookshop2-supplier-service-checks.xml");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		mockRequestContext = null;
		mockSupplierProcess = null;
		fixture = null;
		super.tearDown();
	}
	
	protected ShipmentFailedHandlerImpl createFixture() throws Exception {
		fixture = new ShipmentFailedHandlerImpl();
		FieldUtil.setFieldValue(fixture, "requestContext", mockRequestContext);
		FieldUtil.setFieldValue(fixture, "supplierProcess", mockSupplierProcess);
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
		addExpectedServiceAbortedException("CorrelationId not found in message");
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
	
	@Test
	public void testExecute_shipmentFailed_NullTransactionId() throws Exception {
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		expectedTransactionId = null;
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(shipmentFailedMessage);
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	@Test
	public void testExecute_shipmentFailed_EmptyTransactionId() throws Exception {
		ShipmentFailedMessage shipmentFailedMessage = Bookshop2Fixture.create_ShipmentFailedMessage();
		expectedTransactionId = "";
		setupContext(expectedCorrelationId, expectedTransactionId);
		//setGlobalTransactionActive(true);
		setupMessage(shipmentFailedMessage);
		runTestExecute_shipmentFailed(shipmentFailedMessage);
	}
	
	public void runTestExecute_shipmentFailed(ShipmentFailedMessage shipmentFailedMessage) throws Exception {
		try {
			fixture = createFixture();
			fixture.shipmentFailed(shipmentFailedMessage);
			
			if (isGlobalTransactionActive())
				validateEnrollTransaction(shipmentFailedMessage);
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
			verify(mockSupplierProcess).handle_ShipmentFailed_response(shipmentFailedMessage);
	}
	
	protected void validateProcessNotification() throws Exception {
		//verify(mockSupplierProcess).fireShipmentFailedDone();
	}
	
}
