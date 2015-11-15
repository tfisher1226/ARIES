package org.aries.tx;


/**
 * This class represents a handle on a stack of transactions.
 * It should only be used for suspending and resuming the
 * thread-to-transaction association.
 *
 * The transaction at the top of the stack is the current transaction.
 */
public interface TransactionContext {

	public String getTransactionId();

	public Object getCorrelationId();

	public TransactionIsolationLevel getTransactionIsolationLevel();
	
    public boolean isValid();

	public boolean isSecure();

//	public int getStatus();
//
//	public String getState();

//	public boolean equals(Object o);

}
