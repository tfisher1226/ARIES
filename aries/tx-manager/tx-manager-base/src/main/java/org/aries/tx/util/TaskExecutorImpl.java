package org.aries.tx.util;

import org.aries.tx.TransactionContext;
import org.aries.tx.TransactionContextManager;


public class TaskExecutorImpl extends org.aries.task.TaskExecutorImpl {

	private TransactionContext transactionContext;

	
	public TaskExecutorImpl(Object objectInstance) {
		super(objectInstance);
	}

	public void execute() throws Exception {
		getExecutionContext();
		super.execute();
	}

	protected void getExecutionContext() {
		transactionContext = TransactionContextManager.INSTANCE.getTransactionContext();
	}
	
	protected void refreshExecutionContext() {
		if (transactionContext != null)
			TransactionContextManager.INSTANCE.resume(transactionContext);
	}
	
}
