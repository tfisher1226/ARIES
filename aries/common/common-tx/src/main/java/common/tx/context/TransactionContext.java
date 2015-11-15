package common.tx.context;


/**
 * This class represents a handle on a stack of transactions.
 * It should only be used for suspending and resuming the
 * thread-to-transaction association.
 *
 * The transaction at the top of the stack is the current transaction.
 */
public interface TransactionContext {

	public String identifier();
	
    public boolean valid();

    public boolean equals(Object o);

}
