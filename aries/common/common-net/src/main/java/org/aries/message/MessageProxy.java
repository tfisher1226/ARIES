package org.aries.message;




public class MessageProxy {

	protected String userName;
	
	protected String password;
	
	protected String systemId;

	protected Object correlationId;

	
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

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public Object getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(Object correlationId) {
		this.correlationId = correlationId;
	}

}
