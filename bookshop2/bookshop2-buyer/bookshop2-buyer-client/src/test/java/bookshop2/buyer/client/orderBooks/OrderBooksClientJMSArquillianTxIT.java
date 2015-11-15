package bookshop2.buyer.client.orderBooks;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.jms.client.JmsManager;
import org.aries.runtime.BeanContext;
import org.aries.test.ArquillianConfiguration;
import org.aries.tx.AbstractJMSClientArquillionTXTest;
import org.aries.validate.util.CheckpointManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenFormatStage;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import bookshop2.OrderRequestMessage;
import bookshop2.util.Bookshop2Fixture;
import common.jmx.MBeanUtil;
import common.jmx.client.JmxManager;


@RunWith(Arquillian.class)
public class OrderBooksClientJMSArquillianTxIT extends AbstractJMSClientArquillionTXTest {

	private static final Log log = LogFactory.getLog(OrderBooksClientJMSArquillianTxIT.class);
	
	private static ArquillianConfiguration configuration;
	
	private static JmxManager jmxManager;

	private static JmsManager jmsManager;

	private OrderBooksProxyForJMS fixture;

	
	static {
		System.setProperty("arquillian.launch", "hornetQ01_local");
	}
	
	public OrderBooksClientJMSArquillianTxIT() {
		System.setProperty("arquillian.launch", "hornetQ01_local");
	}
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		log.info("beforeClass() started");
		log.info("beforeClass() done");
	}
	
	@Before
	public void setUp() throws Exception {
		log.info("setUp() started");
		configuration = new ArquillianConfiguration();
		configuration.setJndiPropertyFileName("provider/hornetq/node1-jndi-local.properties");
		configuration.setJmsPropertyFileName("provider/hornetq/node1-jms-local.properties");
		configuration.initialize();
		
		assertNotNull(configuration.getBindAddress());
		assertNotNull(configuration.getManagementPort());
		assertNotNull(configuration.getLocalConnectionFactoryName());
		assertNotNull(configuration.getRemoteConnectionFactoryName());
		assertNotNull(configuration.getXAConnectionFactoryName());
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

		CheckpointManager.addConfiguration("bookshop2-buyer-client-checks.xml");
		MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();
		MBeanUtil.setMBeanServer(mbeanServer);
		log.info("setUp() done");
	}

	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}

	@Override
	public Class<?> getTestClass() {
		return OrderBooksClientJMSArquillianTxIT.class;
	}
	
	@After
	public void tearDown() throws Exception {
		log.info("tearDown() started");
		MBeanUtil.unregisterMBeans();
		BeanContext.clear();
		fixture = null;
		log.info("tearDown() done");
	}

	@Deployment
	@TargetsContainer("hornetQ01_local")
	public static Archive<?> createTestArchive() {
		log.info("createTestArchive() started");
		PomEquippedResolveStage loadPomFromFile = Maven.resolver().loadPomFromFile("pom.xml");
		MavenStrategyStage resolve = loadPomFromFile.resolve("org.hornetq.jms.client");
		MavenFormatStage withTransitivity = resolve.withTransitivity();
		File[] libraries = withTransitivity.asFile();
		  
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "test.jar");
		jar.addPackage("bookshop2");
		jar.addPackage("bookshop2.buyer.client");
		jar.addPackage("org.aries");
		jar.addPackage("org.aries.runtime");
		jar.addPackage("org.aries.validate.util");
		jar.addPackage("org.aries.jaxb");
		jar.addPackage("org.jboss.as.jmx");
		jar.addPackage("org.jboss.as.server.jmx");
		//jar.addPackage("org.hornetq.jms.client");
		jar.addAsResource("provider/hornetq/node1-jndi-local.properties");
		jar.addAsResource("provider/hornetq/node1-jms-local.properties");
		//jar.addClass(AbstractJMSClient.class);
		//jar.addClass(AbstractClientJMSArquillionTest.class);
		//jar.addClass(OrderBooksClientJMS.class);
		//jar.addClass(OrderBooksClientJMSArquillianTXTest.class);
		//jar.addClass(OrderBooks.class);
		//jar.addClass(OrderRequestMessage.class);
		//jar.addAsResource("hornetq-jms3.xml");
		//jar.addAsResource("jms-ds.xml");
		//jar.addAsResource("jms-ra.xml");
		
		EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "test.ear");
		ear.addAsLibraries(libraries);
		ear.addAsModule(jar);
		log.info("createTestArchive() done");
		return ear;
	}

	protected OrderBooksProxyForJMS createFixture(String correlationId) throws Exception {
		log.info("createFixture() invoked: "+correlationId);
		//OrderBooksClientJMS client = new OrderBooksClientJMS();
		OrderBooksProxyForJMS client = jmxManager.lookupObject("java:/OrderBooksClientJMS");
		client.setUserName(configuration.getUsername());
		client.setPassword(configuration.getPassword());
		client.setInitialContextProperties(configuration.getInitialContextProperties());
		client.setConnectionFactoryName(configuration.getXAConnectionFactoryName());
		client.setDestinationName(configuration.getQueueDestinationName());
		log.info("createFixture() returns: "+client);
		return client;
	}

	@Test
	@Ignore
	@InSequence(value = 1)
	public void testExecute_orderBooks_TX_Success() throws Exception {
		OrderRequestMessage orderRequestMessage = Bookshop2Fixture.create_OrderRequestMessage();
		String correlationId = "dummyCorrelationId";
		
		//$$$ Prepare fixture execution
		jmsManager.removeMessagesFromQueue(configuration.getQueueDestinationName());

		// prepare context
		beginTransaction();

		//$$$ Start fixture execution
		fixture = createFixture(correlationId);
		fixture.send(orderRequestMessage);

		// close context
		commitTransaction();

		//$$$ Validate fixture execution
		Long messageCount = jmsManager.countMessagesForQueue(configuration.getQueueDestinationName());
		Assert.equals(messageCount, 1L, "Message count should be 1");
		
	}

	@Test
	@InSequence(value = 2)
	public void testExecute_orderBooks_Success() throws Exception {
		runTestExecute_orderBooks("dummyCorrelationId", Bookshop2Fixture.create_OrderRequestMessage());
	}
	
	@Test
	public void testExecute_orderBooks_NullRequest() throws Exception {
		log.error("testExecute_orderBooks_NullRequest() started");
		try {
			runTestExecute_orderBooks("dummyCorrelationId", null);
			fail("Exception should be thrown");
		} catch (Throwable e) {
			log.error("testExecute_orderBooks_NullRequest", e);
			Assert.exception(e, IllegalArgumentException.class, "OrderRequestMessage must be specified");
		}
	}

	@Test
	public void testExecute_orderBooks_EmptyRequest() throws Exception {
		try {
			runTestExecute_orderBooks("dummyCorrelationId", Bookshop2Fixture.createEmpty_OrderRequestMessage());
			fail("Exception should be thrown");
		} catch (Throwable e) {
			Assert.exception(e, IllegalArgumentException.class, "OrderRequestMessage must include one or more books(s)");
		}
	}

	@Test
	public void testExecute_orderBooks_NullCorrelationId() throws Exception {
		runTestExecute_orderBooks(null, Bookshop2Fixture.create_OrderRequestMessage());
	}

	@Test
	public void testExecute_orderBooks_EmptyCorrelationId() throws Exception {
		runTestExecute_orderBooks("", Bookshop2Fixture.create_OrderRequestMessage());
	}

	public void runTestExecute_orderBooks(String correlationId, OrderRequestMessage orderRequestMessage) throws Exception {
		runTestExecute_orderBooks(correlationId, orderRequestMessage, false);
	}
	
	public void runTestExecute_orderBooks(String correlationId, OrderRequestMessage orderRequestMessage, boolean transacted) throws Exception {
		log.info("runTestExecute_orderBooks() invoked: "+orderRequestMessage);
		//$$$ Prepare fixture execution
		jmsManager.removeMessagesFromQueue(configuration.getQueueDestinationName());
		log.info("runTestExecute_orderBooks: removeMessagesFromQueue()");
		//jmsManager.lookupObject();
		//jmxManager.printObjectNames();

		try {
			if (transacted)
				// prepare context
				beginTransaction();
				
			//$$$ Start fixture execution
			log.info("runTestExecute_orderBooks: createFixture()");
			fixture = createFixture(correlationId);
			log.info("runTestExecute_orderBooks: orderBooks()");
			fixture.send(orderRequestMessage);
			
		} catch (Exception e) {
			throw e;
		}

		//$$$ Validate fixture execution
		Long messageCount = jmsManager.countMessagesForQueue(configuration.getQueueDestinationName());
		Assert.equals(messageCount, 1L, "Message count should be 1");
		log.info("runTestExecute_orderBooks() complete");
	}
    
}
