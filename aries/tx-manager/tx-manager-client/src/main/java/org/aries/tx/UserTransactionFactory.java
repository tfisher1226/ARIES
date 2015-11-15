package org.aries.tx;

import org.aries.Assert;
import org.aries.util.ObjectUtil;


public class UserTransactionFactory {
	
	private static String userTransactionClassName = "org.aries.tx.UserTransactionImpl";
	
	private static UserTransactions userTransactions = new UserTransactions();


	public static synchronized UserTransaction getUserTransaction() {
		UserTransactionImpl userTransaction = (UserTransactionImpl) userTransactions.getUserTransaction();
		return userTransaction;
	}

//	public static UserTransaction getUserSubordinateTransaction() {
//		return userTransactions.getUserTransaction().getUserSubordinateTransaction();
//	}

	public static void setUserTransactionClassName(String className) {
		userTransactionClassName = className;
	}

	public static synchronized void clearUserTransactions() {
		userTransactions = new UserTransactions();
	}

	static class UserTransactions extends ThreadLocal<UserTransaction> {
		public UserTransaction initialValue() {
			Assert.notNull(userTransactionClassName, "UserTransaction class name not specified");
			UserTransaction userTransaction = newInstance(userTransactionClassName);
			return userTransaction;
		}

	    protected <T> T newInstance(String className) {
	        //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    	//T object = ObjectUtil.loadObject(className, classLoader);
	    	T object = ObjectUtil.loadObject(className);
			return object;
		}
	    
		public UserTransaction getUserTransaction() { 
			UserTransaction userTransaction = super.get();
			return userTransaction;
		}
	}
	
}