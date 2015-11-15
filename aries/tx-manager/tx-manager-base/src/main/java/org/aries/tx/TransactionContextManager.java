package org.aries.tx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TransactionContextManager {

	private static ThreadLocal<TransactionContext> transactionContextThreadLocal = new ThreadLocal<TransactionContext>();
	
	private static Map<String, TransactionContext> transactionContextCache = new ConcurrentHashMap<String, TransactionContext>();

	public static TransactionContextManager INSTANCE = new TransactionContextManager();

	public static TransactionContextManager getInstance() {
		return INSTANCE;
	}

//	private static TransactionContexts transactionContexts = new TransactionContexts();
//	
//	static class TransactionContexts extends ThreadLocal<TransactionContext> {
//		public TransactionContext initialValue() {
//			TransactionContext transactionContext = new TransactionContextImpl(coordinationContext);
//			Assert.notNull(userTransactionClassName, "UserTransaction class name not specified");
//			UserTransaction userTransaction = ObjectUtil.newInstance(userTransactionClassName);
//			return userTransaction;
//		}
//
//	    protected <T> T newInstance(String className) {
//	        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//	    	T object = ObjectUtil.loadObject(className, classLoader);
//			return object;
//		}
//	    
//		public UserTransaction getUserTransaction() { 
//			UserTransaction userTransaction = super.get();
//			return userTransaction;
//		}
//	}
	
	
	private TransactionContextManager() {
		//nothing for now
	}

	public TransactionContext getTransactionContext() {
		return transactionContextThreadLocal.get();
	}

	public TransactionContext getTransactionContext(String transactionId) {
		return transactionContextCache.get(transactionId);
	}

	public void start(TransactionContext transactionContext) {
		start(transactionContext, transactionContext.getTransactionId());
	}

	public void start(TransactionContext transactionContext, String transactionId) {
		transactionContextThreadLocal.set(transactionContext);
		transactionContextCache.put(transactionId, transactionContext);
	}

	public void resume() {
		TransactionContext transactionContext = getTransactionContext();
		if (transactionContext != null)
			resume(transactionContext);
	}
	
	public void resume(TransactionContext transactionContext) {
		transactionContextThreadLocal.set(transactionContext);
	}

	public TransactionContext suspend() {
		TransactionContext transactionContext = getTransactionContext();
		suspend(transactionContext);
		return transactionContext;
	}

	public void suspend(TransactionContext transactionContext) {
		if (transactionContext != null)
			transactionContextThreadLocal.set(null);
	}


}
