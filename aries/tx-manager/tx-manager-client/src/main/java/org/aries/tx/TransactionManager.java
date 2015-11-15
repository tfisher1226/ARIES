package org.aries.tx;

import common.tx.exception.CannotRegisterException;
import common.tx.exception.SystemException;


public abstract class TransactionManager /*implements javax.transaction.TransactionManager*/ {

	private static TransactionManager INSTANCE = new TransactionManagerImpl();

	
	public static synchronized TransactionManager getTransactionManager() {
		return INSTANCE;
	}

	public static synchronized void setTransactionManager(TransactionManager manager) {
		INSTANCE = manager;
	}

	/**
	 * The currentTransaction method returns the TxContext for the current
	 * transaction, or null if there is none. Unlike suspend, this method does
	 * not disassociate the current thread from the transaction(s). This can
	 * be used to enable multiple threads to execute within the scope of the
	 * same transaction.
	 */
	public abstract TransactionContext currentTransaction();

	/**
	 * The resume method can be used to (re-)associate a thread with a
	 * transaction(s) via its TxContext. Prior to association, the thread is
	 * disassociated with any transaction(s) with which it may be currently
	 * associated. If the TxContext is null, then the thread is associated with
	 * no transaction. The UnknownTransactionException exception is thrown if
	 * the transaction that the TxContext refers to is invalid in the scope of
	 * the invoking thread.
	 */
	public abstract void resume(TransactionContext transactionContext) throws SystemException;

	/**
	 * A thread of control may require periods of non-transactionality so that
	 * it may perform work that is not associated with a specific transaction.
	 * In order to do this it is necessary to disassociate the thread from any
	 * transactions. The suspend method accomplishes this, returning a
	 * TxContext instance, which is a handle on the transaction. The thread is
	 * then no longer associated with any transaction.
	 */
	public abstract TransactionContext suspend() throws SystemException;

	public abstract int replay () throws SystemException;


    /**
     * Enlist the specified participant with current transaction such that it
     * will participate in the 2PC protocol; a unique identifier for the
     * participant is also required. If there is no transaction associated with
     * the invoking thread then the UnknownTransactionException exception is
     * thrown. If the coordinator already has a participant enrolled with the
     * same identifier, then AlreadyRegisteredException will be thrown. If the
     * transaction is not in a state where participants can be enrolled (e.g.,
     * it is terminating) then WrongStateException will be thrown.
     */
	public abstract void enlistForDurableTwoPhase(String transactionId, Participant participant) throws CannotRegisterException, SystemException;
	
    /**
     * Enlist the specified participant with current transaction such that it
     * will participate in the Volatile 2PC protocol; a unique identifier for
     * the participant is also required. If there is no transaction associated
     * with the invoking thread then the UnknownTransactionException exception
     * is thrown. If the coordinator already has a participant enrolled with
     * the same identifier, then AlreadyRegisteredException will be thrown. If
     * the transaction is not in a state where participants can be enrolled
     * (e.g., it is terminating) then WrongStateException will be thrown.
     */
    public abstract void enlistForVolatileTwoPhase(String transactionId, Participant participant) throws CannotRegisterException, SystemException;
    

    
    public static String getTransactionId() {
		TransactionManager transactionManager = getTransactionManager();
		TransactionContext currentTransaction = transactionManager.currentTransaction();
		if (currentTransaction != null)
			return currentTransaction.getTransactionId();
		return null;
    }

}