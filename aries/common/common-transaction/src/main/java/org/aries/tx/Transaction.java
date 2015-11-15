package org.aries.tx;



public interface Transaction {

	public String getTransactionId();

	public String begin();

	public void commit();

	public void rollback();

	public String beginSubordinate();

	public String rollbackSubordinate();
	
}
