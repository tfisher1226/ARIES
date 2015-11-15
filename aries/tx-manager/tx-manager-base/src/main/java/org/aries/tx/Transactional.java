package org.aries.tx;


public interface Transactional {

	public String getName();

	//public String getCorrelationId();

	//public String getTransactionId();
	
	public boolean prepare(String transactionId);
	
	public void commit(String transactionId);

	public void rollback(String transactionId);
	
}
