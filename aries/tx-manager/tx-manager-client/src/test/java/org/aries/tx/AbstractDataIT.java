package org.aries.tx;

import javax.sql.DataSource;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.aries.TestContext;
import org.aries.TestContextFactory;
import org.aries.common.AbstractDataTest;
import org.aries.common.DataSourceTestFixture;
import org.aries.common.TestXADataSource;
import org.aries.common.util.SqlScriptExecutor;
import org.junit.After;
import org.junit.Before;

import com.arjuna.ats.internal.arjuna.thread.ThreadActionData;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple;


public abstract class AbstractDataIT extends AbstractDataTest {

	private static final int DEFAULT_TRANSACTION_TIMEOUT = 30;

	protected static TestXADataSource testDataSource;

	protected static TransactionManager transactionManager;

	protected static SqlScriptExecutor scriptExecutor;
	
	protected static String databaseName;
	
	protected static String dataSourceName;
	
	protected static String persistenceUnitName;
	
	
	//TODO get from property
	public static String getUsername() {
		return "root";
	}

	//TODO get from property
	public static String getPassword() {
		return "";
	}

	public static void setDatabaseName(String databaseName) {
		AbstractDataIT.databaseName = databaseName;
	}

	public static void setDataSourceName(String dataSourceName) {
		AbstractDataIT.dataSourceName = dataSourceName;
	}

	public static void setPersistenceUnitName(String persistenceUnitName) {
		AbstractDataIT.persistenceUnitName = persistenceUnitName;
	}
	
	public static void setupTransactionManager() throws Exception {
		createTransactionManager();
	}

	public static void setupPersistenceUnit() throws Exception {
		createDataSource(databaseName,  getUsername(), getPassword());
		createNamingServiceContext("java:/"+dataSourceName);
		setupEntityManagerFactory(persistenceUnitName);
	}

	public static void tearDownTransactionManager() throws Exception {
		resetTransactionContext();
		transactionManager = null;
	}

	public static void tearDownPersistenceUnit() throws Exception {
		teardownEntityManagerFactory();
		teardownEntityManager();
	}

	
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
		resetDataSource();
	}

	@After
	public void tearDown() throws Exception {
		resetEntityManager();
		resetDataSource();
		resetTransactionContext();
		super.tearDown();
	}

	
	protected static void createTransactionManager() throws Exception {
		//transactionManager = TransactionManagerFactory.getTransactionManager();
        transactionManager = new TransactionManagerImple();
	}

	protected static String createMysqlDatabaseServerUrl(String databaseName) {
		return "jdbc:mysql://localhost:3306/" + databaseName;
	}

	protected static void createDataSource(String databaseName, String userName, String password) throws Exception {
        String serverUrl = createMysqlDatabaseServerUrl(databaseName);
        DataSource dataSource = DataSourceTestFixture.createTestDataSource(serverUrl, userName, password);
        testDataSource = DataSourceTestFixture.createTestXADataSource(serverUrl, userName, password);
		testDataSource.setTransactionProvider(TransactionRegistry.getInstance());
        scriptExecutor = new SqlScriptExecutor(dataSource);
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
	
	protected static void resetTransactionContext() throws Exception {
		ThreadActionData.popAction();		
	}

	protected static void resetDataSource() throws Exception {
		if (testDataSource != null) {
			testDataSource.clear();
			testDataSource.setTransactionProvider(TransactionRegistry.getInstance());
		}
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

	public void beginTransaction() throws Exception {
		beginTransaction(DEFAULT_TRANSACTION_TIMEOUT);
	}
	
	public void beginTransaction(int timeout) throws Exception {
		transactionManager.setTransactionTimeout(timeout);
		transactionManager.begin();
		String transactionId = registerTransaction();
		testDataSource.setTransactionId(transactionId);
	}
	
	public String registerTransaction() throws Exception {
		Transaction transaction = transactionManager.getTransaction();
		String transactionId = registerTransaction(transaction);
		return transactionId;
	}
	
	public String registerTransaction(Transaction transaction) throws Exception {
		String transactionId = getTransactionId();
		registerTransaction(transaction, transactionId);
		return transactionId;
	}
	
	public void registerTransaction(Transaction transaction, String transactionId) throws Exception {
		TransactionRegistry.getInstance().registerTransaction(transactionId, transaction);
	}
	
	public void commitTransaction() throws Exception {
		Transaction transaction = getTransaction();
		transaction.commit();
		testDataSource.clear();
		ThreadActionData.popAction();
	}
	
	public void rollbackTransaction() throws Exception {
		Transaction transaction = getTransaction();
		transaction.rollback();
		testDataSource.clear();
	}
	
	public void assureDeleteAll() throws Exception {
		scriptExecutor.executeScript("/sql/clean.sql");
	}


	
}
