package bookshop2.buyer.client.orderBooks;

import static org.junit.Assert.assertNotNull;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import org.aries.Assert;
import org.aries.jms.client.JmsManager;
import org.aries.runtime.BeanContext;
import org.aries.test.ArquillianConfiguration;
import org.aries.tx.AbstractJMSClientArquillionTXTest;
import org.aries.tx.service.jms.AbstractJMSClient;
import org.aries.validate.util.CheckpointManager;
import org.jboss.arquillian.container.test.api.ContainerController;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import bookshop2.OrderRequestMessage;
import bookshop2.util.Bookshop2Fixture;
import common.jmx.MBeanUtil;
import common.jmx.client.JmxManager;


@RunAsClient
@RunWith(Arquillian.class)
public class OrderBooksClientJMSArquillianIT extends AbstractJMSClientArquillionTXTest {

	private OrderBooksProxyForJMS fixture;

	@ArquillianResource
	private static ContainerController controller;

	private static ArquillianConfiguration configuration;
	
	private static JmxManager jmxManager;

	private static JmsManager jmsManager;


	public OrderBooksClientJMSArquillianIT() {
	}

	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}

	@Override
	public Class<?> getTestClass() {
		return OrderBooksClientJMSArquillianIT.class;
	}
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		configuration = new ArquillianConfiguration();
		configuration.setJndiPropertyFileName("provider/hornetq/node1-jndi-remote.properties");
		configuration.setJmsPropertyFileName("provider/hornetq/node1-jms-remote.properties");
		configuration.initialize();

		assertNotNull(configuration.getBindAddress());
		assertNotNull(configuration.getManagementPort());
		assertNotNull(configuration.getLocalConnectionFactoryName());
		assertNotNull(configuration.getRemoteConnectionFactoryName());
		assertNotNull(configuration.getInitialContextProperties());
		
		jmxManager = new JmxManager();
		jmxManager.setUsername(configuration.getUsername());
		jmxManager.setPassword(configuration.getPassword());
		jmxManager.setBindAddress(configuration.getBindAddress());
		jmxManager.setManagementPort(configuration.getManagementPort());
		jmxManager.setJndiProperties(configuration.getInitialContextProperties());
		jmxManager.initialize();

		jmsManager = new JmsManager(jmxManager);
		jmsManager.initialize();
		//log.info("beforeClass");
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		if (controller.isStarted("hornetQ01_local"))
			controller.stop("hornetQ01_local");
	}
	
	@Before
	public void setUp() throws Exception {
		CheckpointManager.addConfiguration("bookshop2-buyer-client-checks.xml");
		MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();
		MBeanUtil.setMBeanServer(mbeanServer);
		log.info("setUp");
	}

	@After
	public void tearDown() throws Exception {
		MBeanUtil.unregisterMBeans();
		BeanContext.clear();
		fixture = null;
		log.info("tearDown");
	}

	@Deployment
	@TargetsContainer("hornetQ01_remote")
	public static JavaArchive createTestArchive() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class, "test.jar");
		archive.addClass(AbstractJMSClient.class);
		archive.addClass(OrderBooksProxyForJMS.class);
		archive.addClass(OrderBooks.class);
		archive.addClass(OrderRequestMessage.class);
		//archive.addAsResource("hornetq-jms3.xml");
		//archive.addAsResource("jms-ds.xml");
		//archive.addAsResource("jms-ra.xml");
		return archive;
	}

	protected OrderBooksProxyForJMS createFixture(String correlationId) throws Exception {
		OrderBooksProxyForJMS client = new OrderBooksProxyForJMS(OrderBooks.ID);
		client.setUserName(configuration.getUsername());
		client.setPassword(configuration.getPassword());
		client.setInitialContextProperties(configuration.getInitialContextProperties());
		client.setConnectionFactoryName(configuration.getRemoteConnectionFactoryName());
		client.setDestinationName(configuration.getQueueDestinationName());
		return client;
	}

	@Test
	@InSequence(value = 1)
	public void testExecute_orderBooks_Success() throws Exception {
		runTestExecute_orderBooks("dummyCorrelationId", Bookshop2Fixture.create_OrderRequestMessage());
	}
	
	@Test
	@InSequence(value = 2)
	public void testExecute_orderBooks_NullRequest() throws Exception {
		try {
			runTestExecute_orderBooks("dummyCorrelationId", null);
			fail("Exception should be thrown");
		} catch (Throwable e) {
			Assert.exception(e, IllegalArgumentException.class, "OrderRequestMessage must be specified");
		}
	}

	@Test
	@InSequence(value = 3)
	public void testExecute_orderBooks_EmptyRequest() throws Exception {
		try {
			runTestExecute_orderBooks("dummyCorrelationId", Bookshop2Fixture.createEmpty_OrderRequestMessage());
			fail("Exception should be thrown");
		} catch (Throwable e) {
			Assert.exception(e, IllegalArgumentException.class, "OrderRequestMessage must include one or more books(s)");
		}
	}

	@Test
	@InSequence(value = 4)
	public void testExecute_orderBooks_NullCorrelationId() throws Exception {
		runTestExecute_orderBooks(null, Bookshop2Fixture.create_OrderRequestMessage());
	}

	@Test
	@InSequence(value = 5)
	public void testExecute_orderBooks_EmptyCorrelationId() throws Exception {
		runTestExecute_orderBooks("", Bookshop2Fixture.create_OrderRequestMessage());
	}

	public void runTestExecute_orderBooks(String correlationId, OrderRequestMessage orderRequestMessage) throws Exception {
		runTestExecute_orderBooks(correlationId, orderRequestMessage, false);
	}
	
	public void runTestExecute_orderBooks(String correlationId, OrderRequestMessage orderRequestMessage, boolean transacted) throws Exception {
		if (!controller.isStarted("hornetQ01_local"))
			controller.start("hornetQ01_local");
		
		//$$$ Prepare fixture execution
		jmsManager.removeMessagesFromQueue(configuration.getQueueDestinationName());
		//jmsManager.printObjectNames();

		//$$$ Start fixture execution
		fixture = createFixture(correlationId);
		fixture.send(orderRequestMessage);

		//$$$ Validate fixture execution
		Long messageCount = jmsManager.countMessagesForQueue(configuration.getQueueDestinationName());
		Assert.equals(messageCount, 1L, "Message count should be 1");
	}
    
}
