package bookshop2.supplier.incoming.shipBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.tx.AbstractTransactionalServiceIT;
import org.aries.tx.UserTransaction;
import org.aries.tx.UserTransactionFactory;
import org.aries.util.FieldUtil;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.ShipmentRequestMessage;
import bookshop2.supplier.SupplierProcess;
import bookshop2.supplier.client.shipBooks.ShipBooksProxyForJAXWS;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCache;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheImpl;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManager;
import bookshop2.util.Bookshop2Fixture;
import common.jmx.MBeanUtil;

@RunWith(MockitoJUnitRunner.class)
public class ShipBooksTXLocalIT extends AbstractTransactionalServiceIT {

	private SupplierOrderCache supplierOrderCache;

	private SupplierOrderCache mockSupplierOrderCache;

	private SupplierOrderCacheManager mockSupplierOrderCacheManager;

	private bookshop2.ObjectFactory mockBookshopObjectFactory;

	private ShipBooksProxyForJAXWS mockShipBooksClient;

	private SupplierProcess supplierProcess;


	@Before
	public void setUp() throws Exception {
		super.setUp();
		mockSupplierOrderCache = mock(SupplierOrderCache.class);
		mockSupplierOrderCacheManager = mock(SupplierOrderCacheManager.class);

		when(mockSupplierOrderCacheManager.getSupplierOrderCache()).thenReturn(mockSupplierOrderCache);

		mockShipBooksClient = mock(bookshop2.supplier.client.shipBooks.ShipBooksProxyForJAXWS.class);
		mockBookshopObjectFactory = mock(bookshop2.ObjectFactory.class);

		addProcessInstance(createSupplierProcess());
		addProxyInstance("ShipBooks", mockShipBooksClient);
		CheckpointManager.addConfiguration("bookshop2-supplier-service-checks.xml");
		startService();
	}

	@After
	public void tearDown() throws Exception {
		MBeanUtil.unregisterMBeans();
		BeanContext.clear();
		super.tearDown();
		stopService();
	}

	public SupplierProcess createSupplierProcess() throws Exception {
		supplierProcess = new SupplierProcess();
		//supplierProcess.getSupplierOrderCacheManager().setSupplierOrderCache(createSupplierOrderCache());
		FieldUtil.setFieldValue(supplierProcess, "supplierOrderCacheManager", mockSupplierOrderCacheManager);
		FieldUtil.setFieldValue(supplierProcess, "bookshopObjectFactory", mockBookshopObjectFactory);
		return supplierProcess;
	}

	public SupplierOrderCache createSupplierOrderCache() throws Exception {
		supplierOrderCache = new SupplierOrderCacheImpl();
		return supplierOrderCache;
	}

	public void startService() throws Exception {
		bookshop2.supplier.incoming.shipBooks.ShipBooksLauncher.INSTANCE.initialize(hostName, httpPort);
	}

	public void stopService() throws Exception {
		bookshop2.supplier.incoming.shipBooks.ShipBooksLauncher.INSTANCE.shutdown();
	}

	@Test
	public void testInvoke_Commit_shipBooks() throws Exception {
		ShipmentRequestMessage shipmentRequestMessage = Bookshop2Fixture.create_ShipmentRequestMessage();

		when(mockBookshopObjectFactory.createShipmentRequestMessage()).thenReturn(shipmentRequestMessage);
		
		UserTransaction userTransaction = UserTransactionFactory.getUserTransaction();
		userTransaction.begin();
		
		try {
			ShipBooksProxyForJAXWS client = new ShipBooksProxyForJAXWS(hostName, httpPort);
			client.setCorrelationId(correlationId);
			client.send(shipmentRequestMessage);
		
			boolean status = taskExecutorService.waitForTermination();
			Assert.isTrue(status, "Unexpected result status");
		
			//TODO verify conditions for commit
			userTransaction.commit();
		
		} finally {
			if (userTransaction.getTransactionId() != null)
				userTransaction.rollback();
		}
		
		verify(mockShipBooksClient).send(shipmentRequestMessage);
		verify(mockShipBooksClient).setCorrelationId(correlationId);
		verifyNoMoreInteractions(mockShipBooksClient);
	}

}
