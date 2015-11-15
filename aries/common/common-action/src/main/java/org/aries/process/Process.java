package org.aries.process;


public interface Process {

	public String getName();

	public String getVersion();

//	public Object getCorrelationId();
//
//	public void setCorrelationId(Object correlationId);

//	public String getTransactionId();
//
//	public void setTransactionId(String transactionId);

	public <V> V getValue(String key);

	public <V> void setValue(String key, V value);
	
}
