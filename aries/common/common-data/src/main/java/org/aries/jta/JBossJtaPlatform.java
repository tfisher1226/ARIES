package org.aries.jta;

import javax.naming.InitialContext;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.service.jta.platform.internal.AbstractJtaPlatform;
import org.hibernate.service.jta.platform.spi.JtaPlatformException;

import com.arjuna.ats.jta.common.jtaPropertyManager;


public class JBossJtaPlatform extends AbstractJtaPlatform {

	public JBossJtaPlatform() {
		//System.out.println("JBossJtaPlatform: created");
	}
	
	@Override
	protected TransactionManager locateTransactionManager() {
		jtaPropertyManager.getJTAEnvironmentBean().setSupportSubtransactions(true);
		try {
			InitialContext initialContext = new InitialContext();
			TransactionManager transactionManager = (TransactionManager) initialContext.lookup("java:jboss/TransactionManager");
			
			if (transactionManager == null)
				//This will be null when called from outside the container
				transactionManager = jtaPropertyManager.getJTAEnvironmentBean().getTransactionManager();
			//TransactionManager transactionManager2 = com.arjuna.ats.jta.TransactionManager.transactionManager();
			return transactionManager;
		} catch (Exception e) {
			throw new JtaPlatformException("Could not obtain JBoss Transactions transaction manager instance", e);
		}
	}

	@Override
	protected UserTransaction locateUserTransaction() {
		jtaPropertyManager.getJTAEnvironmentBean().setSupportSubtransactions(true);
		try {
			InitialContext initialContext = new InitialContext();
			UserTransaction userTransaction = (UserTransaction) initialContext.lookup("java:jboss/UserTransaction");
			//UserTransaction userTransaction = jtaPropertyManager.getJTAEnvironmentBean().getUserTransaction();
			//UserTransaction userTransaction = com.arjuna.ats.jta.UserTransaction.userTransaction();
			return userTransaction;
		} catch (Exception e) {
			throw new JtaPlatformException("Could not obtain JBoss Transactions user transaction instance", e);
		}
	}
}
