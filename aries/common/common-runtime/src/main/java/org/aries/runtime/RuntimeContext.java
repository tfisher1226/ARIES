package org.aries.runtime;


//NOTUSED
public interface RuntimeContext {

	public Object getCorrelationId();

	public void setCorrelationId(Object correlationId);
	
	public Object getResource(String name);
	
	public void putResource(String name, Object value);	

}
