package org.aries.workflow;



public interface Executor {

	public <T> T getPart(String key);
	
	public <T> void addPart(String key, T value);
	
	//public void setWebServiceContext(WebServiceContext webServiceContext);
	
	//public void setServiceDescriptor(ServiceDescriptor serviceDescriptor);
	
	public void execute() throws Exception;
	
}
