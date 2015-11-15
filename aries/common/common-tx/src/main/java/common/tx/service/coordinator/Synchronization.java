package common.tx.service.coordinator;

import common.tx.exception.SystemException;


/**
 * This is the interface that all synchronization participants must define.
 */
public interface Synchronization {

    /**
     * The transaction that the instance is enrolled with is about to
     * commit.
     * 
     * @exception SystemException Thrown if any error occurs. This will cause
     * the transaction to roll back.
     */
    public void beforeCompletion() throws SystemException;

    /**
     * The transaction that the instance is enrolled with has completed and
     * the state in which is completed is passed as a parameter.
     *
     * @param status The state in which the transaction completed.
     *
     * @exception SystemException Thrown if any error occurs. This has no
     * affect on the outcome of the transaction.
     */
    public void afterCompletion(int status) throws SystemException;
    
}

