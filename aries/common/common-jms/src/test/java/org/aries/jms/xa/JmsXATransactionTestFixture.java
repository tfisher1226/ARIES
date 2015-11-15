package org.aries.jms.xa;

import javax.transaction.TransactionManager;


public class JmsXATransactionTestFixture {
	
	private static TransactionManagers transactionManagers = new TransactionManagers();

	public static synchronized TransactionManager getTransactionManager() {
		return transactionManagers.getTransactionManager();
	}


	static class TransactionManagers extends ThreadLocal<TransactionManager> {
		public TransactionManager initialValue() {
			try {
				TransactionManager transactionManager = createTransactionManager();
				return transactionManager;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public TransactionManager getTransactionManager() { 
			TransactionManager transactionManager = super.get();
			return transactionManager;
		}
	}
	
	
	public static TransactionManager createTransactionManager() throws Exception {
		TransactionManager manager = null; //TODO new TransactionManagerImple();
		return manager;
	}

	public static XATransactor createTransactor() throws Exception {
		TransactionManager transactionManager = createTransactionManager();
		return createTransactor(transactionManager);
	}

	public static XATransactor createTransactor(TransactionManager transactionManager) throws Exception {
		XATransactor transactor = new XATransactor();
		transactor.setTransactionManager(transactionManager);
		return transactor;
	}

}
