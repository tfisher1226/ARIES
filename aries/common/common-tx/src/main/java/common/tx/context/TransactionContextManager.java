package common.tx.context;

import javax.transaction.SystemException;


public class TransactionContextManager {

	private static ThreadLocal<TransactionContext> transactionContextThreadLocal = new ThreadLocal<TransactionContext>();

	
	public TransactionContextManager() {
	}

	public void resume(TransactionContext transactionContext) throws SystemException {
		transactionContextThreadLocal.set(transactionContext);
	}

	public TransactionContext suspend() throws SystemException {
		TransactionContext transactionContext = currentTransaction();
		if (transactionContext != null)
			transactionContextThreadLocal.set(null);
		return transactionContext;
	}

	public TransactionContext currentTransaction() throws SystemException {
		return transactionContextThreadLocal.get();
	}

}
