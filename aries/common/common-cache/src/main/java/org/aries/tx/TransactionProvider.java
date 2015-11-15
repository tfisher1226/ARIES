package org.aries.tx;

import javax.transaction.Transaction;


public interface TransactionProvider {

	public Transaction getTransaction();
	
	public Transaction getTransaction(Object transactionId);
	
}
