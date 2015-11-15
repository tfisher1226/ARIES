package org.aries.client;

import java.io.Serializable;

import org.aries.client.Client;


//import common.tx.TransactionManager;


public abstract class AbstractEndpoint implements Endpoint {
	
	protected String userName;

	protected String password;

	protected String correlationId;

	protected String transactionId;

	protected Object transactionContext;

	protected Serializable mutex = new String();
	

	public AbstractEndpoint() {
		//nothing for now
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
//	public String getTransactionId() {
//		String transactionId = TransactionManager.getTransactionId();
//		return transactionId;
//	}

//	public String getTransactionId() {
//		if (transactionId != null)
//			return transactionId;
//		return TransactionManager.getTransactionId();
//	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public void setTransactionId(Object transactionId) {
		this.transactionId = transactionId.toString();
	}

	public Object getTransactionContext() {
		return transactionContext;
	}

	public void setTransactionContext(Object transactionContext) {
		this.transactionContext = transactionContext;
	}
	
}
