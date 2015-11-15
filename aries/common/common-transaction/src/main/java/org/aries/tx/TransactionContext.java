package org.aries.tx;


/**
 * This class represents a handle on a stack of transactions.
 * It should only be used for suspending and resuming the
 * thread-to-transaction association.
 *
 * The transaction at the top of the stack is the current transaction.
 */
public interface TransactionContext {

	public String identifier();
	
	public boolean isSecure();
	
    /**
     * @return true if the context is valid, false otherwise.
     */
    public boolean isValid();

    /**
     * @return true if the parameter represents the same context as
     * the target object, false otherwise.
     */
    public boolean equals(TransactionContext transactionContext);

}
