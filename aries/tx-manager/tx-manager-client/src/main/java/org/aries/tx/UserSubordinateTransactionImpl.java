package org.aries.tx;


import common.tx.exception.HeuristicMixedException;
import common.tx.exception.HeuristicRollbackException;
import common.tx.exception.NotSupportedException;
import common.tx.exception.RollbackException;
import common.tx.exception.SystemException;


/**
 * Implementation of class used to create a subordinate AT transaction
 *
 * This class normally redirects all calls to call the corresponding method of the singleton instance
 * which implements UserTransaction. In the case of a begin call it redirects to a beginSubordinate call
 * on the UserTransaction singleton. In the case of a commit or rollback it throws a WrongStateException,
 * irrespective of whether the current transaction is top-level or subordinate since these operations
 * should only be attempted via the UserTransactionImple singleton obtained by calling
 * TransactionFactory.userTransaction()
 */
public class UserSubordinateTransactionImpl extends UserTransactionImpl {
	
    public String getTransactionId() {
        return UserTransactionFactory.getUserTransaction().getTransactionId();
    }

    public UserTransactionImpl getUserSubordinateTransaction() {
        return this;
    }

    public String begin() throws NotSupportedException, SystemException {
    	return UserTransactionFactory.getUserTransaction().beginSubordinate(0);
    }

    /**
     * Start a new subordinate transaction with the specified timeout as its lifetime.
     * If an AT transaction is not currently associated with this thread then the
     * WrongStateException will be thrown.
     */
    public void begin(int timeout) throws NotSupportedException, SystemException {
    	UserTransactionFactory.getUserTransaction().beginSubordinate(timeout);
    }

    /**
     * it is inappropriate to call this even if the current transaction is a top level AT
     * transaction so we always throw a WrongStateException.
     */
	public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
        throw new SystemException("Wrong state");
    }

    /**
     * it is inappropriate to call this even if the current transaction is a top level AT
     * transaction so we always throw a WrongStateException.
     */
	public void rollback() throws IllegalStateException, SecurityException, SystemException {
        throw new SystemException("Wrong state");
    }

}
