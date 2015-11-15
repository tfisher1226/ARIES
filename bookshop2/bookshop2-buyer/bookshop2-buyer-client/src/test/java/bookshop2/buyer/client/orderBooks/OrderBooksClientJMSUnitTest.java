package bookshop2.buyer.client.orderBooks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import org.aries.TestContext;
import org.aries.TestContextFactory;
import org.aries.runtime.BeanContext;
import org.aries.validate.util.CheckpointManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import common.jmx.MBeanUtil;
import bookshop2.OrderRequestMessage;
import bookshop2.buyer.client.orderBooks.OrderBooksProxyForJMS;
import bookshop2.util.Bookshop2Fixture;


@RunWith(MockitoJUnitRunner.class)
public class OrderBooksClientJMSUnitTest {

	private OrderBooksProxyForJMS fixture;

	private String destinationName = "jms/testQueue";

	private String connectionFactoryName = "/ConnectionFactory";

	private Destination mockDestination;

	private ConnectionFactory mockConnectionFactory;

	private Connection mockConnection;

	private Session mockSession;

	private MessageProducer mockMessageProducer;
	
	private ObjectMessage mockObjectMessage;

	private OrderRequestMessage orderRequestMessage;
	
	
	@Before
	public void setUp() throws Exception {
		mockDestination = mock(Destination.class);
		mockConnectionFactory = mock(ConnectionFactory.class);
		mockConnection = mock(Connection.class);
		mockSession = mock(Session.class);
		mockMessageProducer = mock(MessageProducer.class);
		mockObjectMessage = mock(ObjectMessage.class);
		orderRequestMessage = new OrderRequestMessage();
		
		TestContext context = new TestContext();
		context.bind(connectionFactoryName, mockConnectionFactory);
		context.bind(destinationName, mockDestination);
		TestContextFactory.setContext(context);	
		
		CheckpointManager.addConfiguration("bookshop2-buyer-client-checks.xml");
		MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();
		MBeanUtil.setMBeanServer(mbeanServer);
	}

	@After
	public void tearDown() throws Exception {
		MBeanUtil.unregisterMBeans();
		BeanContext.clear();
		fixture = null;
	}

	protected OrderBooksProxyForJMS createFixture(String correlationId) throws Exception {
		OrderBooksProxyForJMS client = new OrderBooksProxyForJMS(OrderBooks.ID);
		client.setInitialContextFactory(new TestContextFactory());
		client.setConnectionFactoryName(connectionFactoryName);
		client.setDestinationName(destinationName);
		return client;
	}

	@Ignore
	@Test
	public void testExecute_orderBooks_Success() throws Exception {
		runTestExecute_orderBooks("dummyCorrelationId", Bookshop2Fixture.create_OrderRequestMessage());
	}

	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void testExecute_orderBooks_NullRequest() throws Exception {
		runTestExecute_orderBooks("dummyCorrelationId", null);
	}

	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void testExecute_orderBooks_EmptyRequest() throws Exception {
		runTestExecute_orderBooks("dummyCorrelationId", Bookshop2Fixture.createEmpty_OrderRequestMessage());
	}

	@Ignore
	@Test
	public void testExecute_orderBooks_NullCorrelationId() throws Exception {
		runTestExecute_orderBooks(null, Bookshop2Fixture.create_OrderRequestMessage());
	}

	@Ignore
	@Test
	public void testExecute_orderBooks_EmptyCorrelationId() throws Exception {
		runTestExecute_orderBooks("", Bookshop2Fixture.create_OrderRequestMessage());
	}

	public void runTestExecute_orderBooks(String correlationId, OrderRequestMessage orderRequestMessage) throws Exception {
		when(mockConnectionFactory.createConnection()).thenReturn(mockConnection);
		when(mockConnection.createSession(false, Session.AUTO_ACKNOWLEDGE)).thenReturn(mockSession);
		when(mockSession.createObjectMessage(orderRequestMessage)).thenReturn(mockObjectMessage);
		when(mockSession.createProducer(mockDestination)).thenReturn(mockMessageProducer);
		
		//$$$ Start fixture execution
		fixture = createFixture(correlationId);
		fixture.send(orderRequestMessage);

		//$$$ Finish fixture execution
		verify(mockMessageProducer).send(mockObjectMessage);
		verify(mockSession).close();
	}

}
