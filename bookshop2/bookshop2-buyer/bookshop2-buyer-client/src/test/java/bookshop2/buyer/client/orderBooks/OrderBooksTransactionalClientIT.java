package bookshop2.buyer.client.orderBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.tx.AbstractTransactionalClientIT;
import org.aries.tx.UserTransaction;
import org.aries.tx.UserTransactionFactory;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.OrderRequestMessage;
import bookshop2.buyer.client.orderBooks.OrderBooksProxyForJAXWS;
import bookshop2.util.Bookshop2Fixture;
import common.jmx.MBeanUtil;


@RunWith(MockitoJUnitRunner.class)
public class OrderBooksTransactionalClientIT extends AbstractTransactionalClientIT {

	private bookshop2.ObjectFactory mockBookshopObjectFactory;

	private OrderBooksProxyForJAXWS fixture;


	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = mock(bookshop2.buyer.client.orderBooks.OrderBooksProxyForJAXWS.class);
		mockBookshopObjectFactory = mock(bookshop2.ObjectFactory.class);
		CheckpointManager.addConfiguration("bookshop2-buyer-client-checks.xml");
	}

	@After
	public void tearDown() throws Exception {
		MBeanUtil.unregisterMBeans();
		BeanContext.clear();
		super.tearDown();
	}

	@Test
	public void testInvoke_Commit_orderBooks() throws Exception {
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();

		when(mockBookshopObjectFactory.createOrderRequestMessage()).thenReturn(orderRequestMessage);
		
		UserTransaction userTransaction = UserTransactionFactory.getUserTransaction();
		userTransaction.begin();
		
		try {
			OrderBooksProxyForJAXWS client = new OrderBooksProxyForJAXWS(hostName, httpPort);
			client.setCorrelationId(correlationId);
			client.send(orderRequestMessage);
		
			boolean status = taskExecutorService.waitForTermination();
			Assert.isTrue(status, "Unexpected result status");
		
			//TODO verify conditions for commit
			userTransaction.commit();
		
		} finally {
			if (userTransaction.getTransactionId() != null)
				userTransaction.rollback();
		}
		
		verify(fixture).send(orderRequestMessage);
		verify(fixture).setCorrelationId(correlationId);
		verifyNoMoreInteractions(fixture);
	}

}
