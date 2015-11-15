package org.aries.tx;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.aries.TestContext;
import org.aries.TestContextFactory;
import org.aries.common.DataSourceTestFixture;
import org.aries.common.EntityManagerTestControl;
import org.aries.common.TestXADataSource;
import org.aries.common.util.SqlScriptExecutor;


public class DataLayerTestControl {

	protected TestXADataSource testDataSource;

	protected SqlScriptExecutor scriptExecutor;

	protected String userName = "root";

	protected String password = "";

	protected String databaseName;

	protected String dataSourceName;

	protected String persistenceUnitName;

	protected TransactionTestControl transactionControl;
	
	protected EntityManagerTestControl entityManagerControl;


	public DataLayerTestControl() {
	}
	
	public DataLayerTestControl(TransactionTestControl transactionTestControl) {
		this.transactionControl = transactionTestControl;
		this.entityManagerControl = new EntityManagerTestControl();
	}
	
	public TransactionTestControl getTransactionTestControl() {
		return transactionControl;
	}
	
	public EntityManagerTestControl getEntityManagerTestControl() {
		return entityManagerControl;
	}
	
	//TODO get from property
	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName= userName;
	}

	//TODO get from property
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password= password;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	
//	/////// custom setup for running in container
//	
//	public void setupSpecialCase() throws Exception {
//		createDataSource(databaseName,  getUsername(), getPassword());
//	}
//	
//	/////////
	
	
	public void setupDataLayer() throws Exception {
		setupDataSource(databaseName, dataSourceName);
		setupPersistenceUnit(persistenceUnitName);
	}
	
	public void setupDataSource() throws Exception {
		setupDataSource(databaseName, dataSourceName);
	}
	
	public void setupDataSource(String databaseName, String dataSourceName) throws Exception {
		createDataSource(databaseName,  getUsername(), getPassword());
		createNamingServiceContext("java:/"+dataSourceName);
	}

	public void setupPersistenceUnit() throws Exception {
		setupPersistenceUnit(persistenceUnitName);
	}
	
	public void setupPersistenceUnit(String persistenceUnitName) throws Exception {
		createDataSource(databaseName,  getUsername(), getPassword());
		createNamingServiceContext("java:/"+dataSourceName);
		entityManagerControl.setupEntityManagerFactory(persistenceUnitName);
	}

	public void tearDownPersistenceUnit() throws Exception {
		entityManagerControl.teardownEntityManagerFactory();
		entityManagerControl.teardownEntityManager();
	}


	public void setUp() throws Exception {
		transactionControl.setUp();
		resetDataSource();
	}

	public void tearDown() throws Exception {
		entityManagerControl.resetEntityManager();
		//TODO remove this below - should not touch this, just use it
		transactionControl.tearDown();
		resetContext();
	}

	public EntityManager setupEntityManager() throws Exception {
		return entityManagerControl.setupEntityManager();
	}

	public EntityManager createEntityManager() throws Exception {
		return entityManagerControl.createEntityManager();
	}

	public void resetEntityManagerFactory() throws Exception {
		entityManagerControl.teardownEntityManagerFactory();
		entityManagerControl.setupEntityManagerFactory(persistenceUnitName);
	}
	
	public void resetEntityManager() throws Exception {
		entityManagerControl.resetEntityManager();
	}

	public void closeEntityManager(EntityManager entityManager) throws Exception {
		entityManagerControl.closeEntityManager(entityManager);
	}

	public EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) throws Exception {
		return entityManagerControl.createEntityManagerFactory(persistenceUnitName);
	}
	
	protected String createMysqlDatabaseServerUrl(String databaseName) {
		return "jdbc:mysql://localhost:3306/" + databaseName;
	}

	protected void createDataSource(String databaseName, String userName, String password) throws Exception {
		String serverUrl = createMysqlDatabaseServerUrl(databaseName);
		DataSource dataSource = DataSourceTestFixture.createTestDataSource(serverUrl, userName, password);
		testDataSource = DataSourceTestFixture.createTestXADataSource(serverUrl, userName, password);
		testDataSource.setTransactionProvider(TransactionRegistry.getInstance());
		transactionControl.addParticipant(testDataSource);
		scriptExecutor = new SqlScriptExecutor(dataSource);
	}

	protected void createNamingServiceContext(String dataSource) throws Exception {
		TestContext context = new TestContext();
		context.bind("java:jboss/TransactionManager", transactionControl.getTransactionManager());
		//context.bind("java:comp/TransactionSynchronizationRegistry", transactionTestControl.getTransactionSynchronizationRegistry());
		context.bind("java:comp/UserTransaction", com.arjuna.ats.jta.UserTransaction.userTransaction());
		context.bind("java:/UserTransaction", com.arjuna.ats.jta.UserTransaction.userTransaction());
		context.bind(dataSource, testDataSource);
		TestContextFactory.setContext(context);		
	}

	
	/*
	 * Reset-related methods
	 */

	public void resetContext() throws Exception {
		resetDataSource();
	}

	public void resetDataSource() throws Exception {
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
	//		ThreadActionData.purgeActions();
	//		teardownEntityManager();
	//		testDataSource.clear();
	//		fixture = null;
	//		super.tearDown();
	//	}


//	public String beginTransaction() throws Exception {
//		return beginTransaction(TransactionTestControl.DEFAULT_TRANSACTION_TIMEOUT);
//	}
//	
//	public String beginTransaction(int timeout) throws Exception {
//		String transactionId = transactionTestControl.beginTransaction(timeout);
//		if (testDataSource != null)
//			testDataSource.setTransactionId(transactionId);
//		return transactionId;
//	}
//	
//	public void commitTransaction() throws Exception {
//		transactionTestControl.commitTransaction();
//		if (testDataSource != null)
//			testDataSource.clear();
//	}
//
//	public void rollbackTransaction() throws Exception {
//		transactionTestControl.rollbackTransaction();
//		if (testDataSource != null)
//			testDataSource.clear();
//	}
//
//	public void clearTransactions() throws Exception {
//		transactionTestControl.clearTransactions();
//	}

	public void assureDeleteAll() throws Exception {
		scriptExecutor.executeScript("/sql/clean.sql");
	}

}
