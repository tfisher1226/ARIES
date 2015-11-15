package org.aries.jta;

import javax.transaction.TransactionManager;

import org.aries.util.ObjectUtil;


public class TransactionManagerFactory {

	private static String transactionManagerClassName = "com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple";

	private static TransactionManagers transactionManagers = new TransactionManagers();

	public static synchronized TransactionManager getTransactionManager() {
		return transactionManagers.getTransactionManager();
	}


	static class TransactionManagers extends ThreadLocal<TransactionManager> {
		public TransactionManager initialValue() {
			TransactionManager transactionManager = ObjectUtil.loadObject(transactionManagerClassName);
			return transactionManager;
		}

		public TransactionManager getTransactionManager() { 
			TransactionManager transactionManager = super.get();
			return transactionManager;
		}
	}

}
