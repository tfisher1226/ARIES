package org.aries.tx;

import org.aries.Assert;
import org.aries.util.ObjectUtil;


public class TransactionManagerFactory {
	
	private static String transactionManagerClassName = "org.aries.tx.TransactionManagerImpl";

	private static TransactionManagers transactionManagers = new TransactionManagers();


	public static synchronized TransactionManager getTransactionManager() {
		return transactionManagers.getTransactionManager();
	}

	public static void setTransactionManagerClassName(String className) {
		transactionManagerClassName = className;
	}
	
	static class TransactionManagers extends ThreadLocal<TransactionManager> {
		public TransactionManager initialValue() {
			Assert.notNull(transactionManagerClassName, "TransactionManager class name not specified");
			TransactionManager transactionManager = ObjectUtil.newInstance(transactionManagerClassName);
			return transactionManager;
		}

	    protected <T> T newInstance(String className) {
	        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    	T object = ObjectUtil.loadObject(className, classLoader);
			return object;
		}
	    
		public TransactionManager getTransactionManager() { 
			TransactionManager transactionManager = super.get();
			return transactionManager;
		}
	}

}
