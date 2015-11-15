package org.aries.runtime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Remote;
import javax.ejb.Stateless;


//NOTUSED
@Remote(RuntimeContext.class)
@Stateless(name="RuntimeContext")
public class RuntimeContextImpl implements RuntimeContext {

	private Object correlationId;

	private String transactionId;

	private Map<Object, Object> map = new ConcurrentHashMap<Object, Object>();

	
	public Object getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(Object correlationId) {
		this.correlationId = correlationId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Object getResource(String name) {
		return map.get(name);
	}
	
	public void putResource(String name, Object value) {
		map.put(name, value);
	}

}
