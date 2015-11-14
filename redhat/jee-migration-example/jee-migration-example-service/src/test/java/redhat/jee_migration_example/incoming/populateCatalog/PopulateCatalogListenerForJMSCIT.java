package redhat.jee_migration_example.incoming.populateCatalog;

import org.aries.jms.client.JmsClient;
import org.aries.tx.AbstractArquillianTest;
import org.aries.tx.AbstractJMSListenerArquillionTest;
import org.aries.tx.TransactionTestControl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import redhat.jee_migration_example.EventLoggerTestEARBuilder;
import redhat.jee_migration_example.client.populateCatalog.PopulateCatalog;
import redhat.jee_migration_example.client.populateCatalog.PopulateCatalogProxyForJMS;


@RunAsClient
@RunWith(Arquillian.class)
public class PopulateCatalogListenerForJMSCIT extends AbstractJMSListenerArquillionTest {
	
	private JmsClient populateCatalogClient;
	
	private TransactionTestControl transactionTestControl;
	
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		AbstractArquillianTest.beforeClass();
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		AbstractArquillianTest.afterClass();
	}
	
	@Override
	public Class<?> getTestClass() {
		return PopulateCatalogListenerForJMSCIT.class;
	}
	
	@Override
	public String getServerName() {
		return "hornetQ01_local";
	}
	
	@Override
	public String getDomainId() {
		return "redhat.jee-migration-example";
	}
	
	@Override
	public String getServiceId() {
		return "redhat.jee-migration-example.populateCatalog";
	}
	
	@Override
	public String getTargetArchive() {
		return EventLoggerTestEARBuilder.NAME;
	}
	
	@Override
	public String getTargetDestination() {
		return get_target_JeeMigrationExample_PopulateCatalog_destination();
	}
	
	public String get_target_JeeMigrationExample_PopulateCatalog_destination() {
		return getJNDINameForQueue("public_redhat_populate_catalog_queue");
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		startServer();
		createTransactionControl();
		removeMessagesFromDestinations();
	}
	
	protected void createTransactionControl() throws Exception {
		transactionTestControl = new TransactionTestControl();
		transactionTestControl.setupTransactionManager();
	}
	
	protected void registerNotificationListeners() throws Exception {
		addRequestNotificationListeners("JeeMigrationExample_PopulateCatalog");
		super.registerNotificationListeners();
	}
	
	@After
	public void tearDown() throws Exception {
		clearStructures();
		clearState();
		super.tearDown();
	}
	
	protected void clearStructures() throws Exception {
		populateCatalogClient.reset();
		populateCatalogClient = null;
		super.clearStructures();
	}
	
	protected void clearState() throws Exception {
		super.clearState();
	}
	
	protected void removeMessagesFromDestinations() throws Exception {
		removeMessagesFromQueue(getTargetArchive(), get_target_JeeMigrationExample_PopulateCatalog_destination());
	}
	
	@TargetsContainer("hornetQ01_local")
	@Deployment(name = "eventLoggerEAR", order = 2)
	public static EnterpriseArchive createEventLoggerEAR() {
		EventLoggerTestEARBuilder builder = new EventLoggerTestEARBuilder();
		builder.setRunningAsClient(true);
		return builder.createEAR();
	}
	
	protected JmsClient start_PopulateCatalog_client() throws Exception {
		JmsClient client = new PopulateCatalogProxyForJMS(PopulateCatalog.ID);
		configureClient(client, getTargetDestination());
		client.setCreateTemporaryQueue(true);
		client.initialize();
		return client;
	}
	
}
