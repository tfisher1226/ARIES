package common.tx;

import javax.sql.DataSource;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.aries.TestContext;
import org.aries.TestContextFactory;
import org.aries.common.AbstractDataTest;
import org.aries.common.DataSourceTestFixture;
import org.aries.common.TestXADataSource;
import org.aries.common.util.SqlScriptExecutor;
import org.aries.tx.TransactionRegistry;
import org.junit.After;
import org.junit.Before;

import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;


public abstract class AbstractDaoIT extends AbstractDataTest {

	protected static TestXADataSource testDataSource;

	protected static TransactionManager transactionManager;

	protected static SqlScriptExecutor scriptExecutor;
	

//	@BeforeClass  
//	public static void beforeClass() throws Exception {
//		createDataSource("adminDB", "root", "");
//		createTransactionManager();
//		createNamingServiceContext();
//		setupEntityManagerFactory("admin");
//	} 

//	@AfterClass  
//	public static void afterClass() {
//        //logger.info("Stopping Transaction Manager Threads");
//        //TxControl.disable();
//        //RecoveryManager.manager().terminate();
//        //TransactionReaper.terminate(true)
//        
//		teardownEntityManagerFactory();
//		teardownEntityManager();
//	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	protected static String createMysqlDatabaseServerUrl(String databaseName) {
		return "jdbc:mysql://localhost:3306/" + databaseName;
	}

	protected static void createDataSource(String databaseName, String userName, String password) throws Exception {
        String serverUrl = createMysqlDatabaseServerUrl(databaseName);
        DataSource dataSource = DataSourceTestFixture.createTestDataSource(serverUrl, userName, password);
        testDataSource = DataSourceTestFixture.createTestXADataSource(serverUrl, userName, password);
        scriptExecutor = new SqlScriptExecutor(dataSource);
	}

	protected static void createTransactionManager() throws Exception {
        transactionManager = new TransactionManagerImple();
	}

	protected static void createNamingServiceContext(String dataSource) throws Exception {
        TestContext context = new TestContext();
        context.bind("java:jboss/TransactionManager", transactionManager);
        //context.bind("java:comp/TransactionSynchronizationRegistry", transactionManager);
        context.bind("java:comp/UserTransaction", com.arjuna.ats.jta.UserTransaction.userTransaction());
        context.bind("java:/UserTransaction", com.arjuna.ats.jta.UserTransaction.userTransaction());
        context.bind(dataSource, testDataSource);
        TestContextFactory.setContext(context);		
	}
	
	
	
//	@Before
//	public void setUp() throws Exception {
//        testDataSource.setTransactionProvider(ConversationRegistry.getInstance());
//		fixture = createFixture();
//		super.setUp();
//	}
	
//	@After
//	public void tearDown() throws Exception {
//		//dataSource.getXAConnection().close();
//		//Transaction transaction = transactionManager.getTransaction();
//		//BasicAction currentAction = ThreadActionData.currentAction();
//		//transaction.delistResource(dataSource.getXAConnection().getXAResource(), 0);
//		//transactionManager.begin();
//		ThreadActionData.popAction();
//		teardownEntityManager();
//		testDataSource.clear();
//		fixture = null;
//		super.tearDown();
//	}

	
	
	protected String getTransactionId() throws Exception {
		String transactionId = ThreadActionData.currentAction().get_uid().toString();
		return transactionId;
	}
	
	protected Transaction getTransaction() throws Exception {
		Transaction transaction = getTransaction(getTransactionId());
		return transaction;
	}

	protected Transaction getTransaction(String transactionId) throws Exception {
		Transaction transaction = TransactionRegistry.getInstance().getTransaction(transactionId);
		return transaction;
	}

	protected void beginTransaction() throws Exception {
		beginTransaction(30);
	}
	
	protected void beginTransaction(int timeout) throws Exception {
		transactionManager.setTransactionTimeout(timeout);
		transactionManager.begin();
		String transactionId = registerTransaction();
		testDataSource.setTransactionId(transactionId);
	}
	
	protected String registerTransaction() throws Exception {
		Transaction transaction = transactionManager.getTransaction();
		String transactionId = registerTransaction(transaction);
		return transactionId;
	}
	
	protected String registerTransaction(Transaction transaction) throws Exception {
		String transactionId = getTransactionId();
		registerTransaction(transaction, transactionId);
		return transactionId;
	}
	
	protected void registerTransaction(Transaction transaction, String transactionId) throws Exception {
		TransactionRegistry.getInstance().registerTransaction(transactionId, transaction);
	}
	
	protected void commitTransaction() throws Exception {
		Transaction transaction = getTransaction();
		transaction.commit();
		testDataSource.clear();
		ThreadActionData.popAction();
	}
	
	protected void rollbackTransaction() throws Exception {
		Transaction transaction = getTransaction();
		transaction.rollback();
		testDataSource.clear();
	}
	
	protected void resetTransactionContext() throws Exception {
		ThreadActionData.popAction();		
	}

	
	
	protected void assureDeleteAll() throws Exception {
		scriptExecutor.executeScript("/sql/clean.sql");
	}


	
}
