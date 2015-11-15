package bookshop2.supplier.incoming.queryBooks;

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

import bookshop2.BooksAvailableMessage;
import bookshop2.QueryRequestMessage;
import bookshop2.supplier.SupplierProcess;
import bookshop2.supplier.client.queryBooks.QueryBooksProxyForJAXWS;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCache;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheImpl;
import bookshop2.supplier.data.supplierOrderCache.SupplierOrderCacheManager;
import bookshop2.supplier.outgoing.booksAvailable.BooksAvailableReplyProxyForJAXWS;
import bookshop2.util.Bookshop2Fixture;
import common.jmx.MBeanUtil;


@RunWith(MockitoJUnitRunner.class)
public class QueryBooksTXLocalIT extends AbstractTransactionalServiceIT {

	private SupplierOrderCache supplierOrderCache;

	private SupplierOrderCache mockSupplierOrderCache;

	private SupplierOrderCacheManager mockSupplierOrderCacheManager;

	private bookshop2.ObjectFactory mockBookshopObjectFactory;

	private BooksAvailableReplyProxyForJAXWS mockBooksAvailableClient;

	private SupplierProcess supplierProcess;


	@Before
	public void setUp() throws Exception {
		super.setUp();
		mockSupplierOrderCache = mock(SupplierOrderCache.class);
		mockSupplierOrderCacheManager = mock(SupplierOrderCacheManager.class);

		when(mockSupplierOrderCacheManager.getSupplierOrderCache()).thenReturn(mockSupplierOrderCache);

		mockBooksAvailableClient = mock(bookshop2.supplier.outgoing.booksAvailable.BooksAvailableReplyProxyForJAXWS.class);
		mockBookshopObjectFactory = mock(bookshop2.ObjectFactory.class);

		addProcessInstance(createSupplierProcess());
		addProxyInstance("BooksAvailable", mockBooksAvailableClient);
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
		FieldUtil.setFieldValue(supplierProcess, "supplierOrderCacheManager", mockSupplierOrderCacheManager);
		FieldUtil.setFieldValue(supplierProcess, "bookshopObjectFactory", mockBookshopObjectFactory);
		//supplierProcess.getSupplierOrderCacheManager().setSupplierOrderCache(createSupplierOrderCache());
		return supplierProcess;
	}

	public SupplierOrderCache createSupplierOrderCache() throws Exception {
		supplierOrderCache = new SupplierOrderCacheImpl();
		return supplierOrderCache;
	}

	public void startService() throws Exception {
		bookshop2.supplier.incoming.queryBooks.QueryBooksLauncher.INSTANCE.initialize(hostName, httpPort);
	}

	public void stopService() throws Exception {
		bookshop2.supplier.incoming.queryBooks.QueryBooksLauncher.INSTANCE.shutdown();
	}

	@Test
	public void testInvoke_Commit_queryBooks() throws Exception {
		QueryRequestMessage queryRequestMessage = Bookshop2Fixture.create_QueryRequestMessage();
		BooksAvailableMessage booksAvailableMessage = Bookshop2Fixture.create_BooksAvailableMessage();

		when(mockBookshopObjectFactory.createQueryRequestMessage()).thenReturn(queryRequestMessage);
		when(mockBookshopObjectFactory.createBooksAvailableMessage()).thenReturn(booksAvailableMessage);
		
		UserTransaction userTransaction = UserTransactionFactory.getUserTransaction();
		userTransaction.begin();
		
		try {
			QueryBooksProxyForJAXWS client = new QueryBooksProxyForJAXWS(hostName, httpPort);
			client.setCorrelationId(correlationId);
			client.send(queryRequestMessage);
		
			boolean status = taskExecutorService.waitForTermination();
			Assert.isTrue(status, "Unexpected result status");
		
			//TODO verify conditions for commit
			userTransaction.commit();
		
		} finally {
			if (userTransaction.getTransactionId() != null)
				userTransaction.rollback();
		}
		
		verify(mockBooksAvailableClient).send(booksAvailableMessage);
		verify(mockBooksAvailableClient).setCorrelationId(correlationId);
		verifyNoMoreInteractions(mockBooksAvailableClient);
	}

}
