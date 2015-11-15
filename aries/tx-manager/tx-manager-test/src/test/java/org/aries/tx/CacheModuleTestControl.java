package org.aries.tx;

import org.aries.TestContext;
import org.aries.TestContextFactory;


public class CacheModuleTestControl {

	protected String userName = "root";

	protected String password = "";

	protected String cacheName;

	protected String cacheManagerName;

	protected TransactionTestControl transactionTestControl;

	
	public CacheModuleTestControl(TransactionTestControl transactionTestControl) {
		this.transactionTestControl = transactionTestControl;
	}

	public TransactionTestControl getTransactionTestControl() {
		return transactionTestControl;
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
	
	public void setupCacheLayer() throws Exception {
	}

	public void setUp() throws Exception {
		//transactionTestControl.setUp();
	}

	public void tearDown() throws Exception {
		//transactionTestControl.tearDown();
		resetContext();
	}

	protected void createNamingServiceContext(String dataSource) throws Exception {
		TestContext context = new TestContext();
		context.bind("java:jboss/TransactionManager", transactionTestControl.getTransactionManager());
		//context.bind("java:comp/TransactionSynchronizationRegistry", transactionTestControl.getTransactionSynchronizationRegistry());
		context.bind("java:comp/UserTransaction", com.arjuna.ats.jta.UserTransaction.userTransaction());
		context.bind("java:/UserTransaction", com.arjuna.ats.jta.UserTransaction.userTransaction());
		TestContextFactory.setContext(context);		
	}

	
	/*
	 * Reset-related methods
	 */

	public void resetContext() throws Exception {
		//nothing for now
	}

}
