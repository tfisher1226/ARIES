package bookshop2.buyer.client.orderBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import org.aries.launcher.TestUtil;
import org.aries.runtime.BeanContext;
import org.aries.tx.AbstractJaxwsClientTest;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import bookshop2.OrderRequestMessage;
import bookshop2.util.Bookshop2Fixture;
import common.jmx.MBeanUtil;


@RunWith(MockitoJUnitRunner.class)
public class OrderBooksClientHTTPUnitTest extends AbstractJaxwsClientTest {

	private OrderBooksProxyForJAXWS fixture;

	private OrderBooks mockOrderBooksPort;

	private OrderBooksService mockOrderBooksService;

	protected String correlationId = "dummyCorrelationId";

	//protected String hostName = "127.0.0.1";
	protected String hostName = "localhost";

	protected int httpPort = 8280;

	protected int jmxPort = 1234;
	

	@Before
	public void setUp() throws Exception {
		mockOrderBooksPort = mock(OrderBooks.class);
		mockOrderBooksService = mock(OrderBooksService.class);
		CheckpointManager.addConfiguration("bookshop2-buyer-client-checks.xml");
		MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();
		MBeanUtil.setMBeanServer(mbeanServer);
		fixture = createFixture();
	}

	@After
	public void tearDown() throws Exception {
		MBeanUtil.unregisterMBeans();
		mockOrderBooksPort = null;
		mockOrderBooksService = null;
		BeanContext.clear();
		fixture = null;
	}

	protected OrderBooksProxyForJAXWS createFixture() throws Exception {
		OrderBooksProxyForJAXWS client = new OrderBooksProxyForJAXWS(hostName, httpPort);
		TestUtil.setFieldValue(client, "service", mockOrderBooksService);
		return client;
	}

	@Test
	public void testOrderBooks_Success() throws Exception {
		runTestOrderBooks("dummyCorrelationId", Bookshop2Fixture.create_OrderRequestMessage());
	}

	@Test //(expected = IllegalArgumentException.class)
	public void testOrderBooks_NullRequest() throws Exception {
		runTestOrderBooks("dummyCorrelationId", null);
	}

	@Test //(expected = IllegalArgumentException.class)
	public void testOrderBooks_EmptyRequest() throws Exception {
		runTestOrderBooks("dummyCorrelationId", Bookshop2Fixture.createEmpty_OrderRequestMessage());
	}

	@Test
	public void testOrderBooks_NullCorrelationId() throws Exception {
		runTestOrderBooks(null, Bookshop2Fixture.create_OrderRequestMessage());
	}

	@Test
	public void testOrderBooks_EmptyCorrelationId() throws Exception {
		runTestOrderBooks("", Bookshop2Fixture.create_OrderRequestMessage());
	}

	public void runTestOrderBooks(String correlationId, OrderRequestMessage orderRequestMessage) throws Exception {
		when(mockOrderBooksService.getPort()).thenReturn(mockOrderBooksPort);
		
		//validateHeaderContext();
		//validateRequestContext();
		
		//$$$ Start fixture execution
		fixture.getDelegate().orderBooks(orderRequestMessage);
		
		//$$$ Finish fixture execution
		verify(mockOrderBooksPort).orderBooks(orderRequestMessage);
	}
	
}
