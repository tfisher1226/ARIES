package org.aries.tx;

import javax.management.ObjectName;

import org.aries.util.concurrent.FutureResult;


public abstract class AbstractJMSHandlerArquillionTest extends AbstractServiceSideArquillionTest {

	public abstract String getServiceId();
	
	public abstract String getTargetArchive();

	
	protected String username;

	protected String password;

	protected String providerUrl;

	
	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	
	/*
	 * Runtime support
	 */
	
	protected FutureResult<Object> registerForResult() throws Exception {
		return registerForResult(getServiceId());
	}
	
	
	/* 
	 * JMX related calls
	 */
	
	public void clearContext(String mbeanName) throws Exception {
		ObjectName objectName = new ObjectName(mbeanName);
		jmxManager.invoke(objectName, "clearContext");
	}


}
