package org.aries.ejb;

import java.io.Serializable;


@SuppressWarnings("serial")
public class SubscriberDescripter implements Serializable {

	private String hostName;
	
	private String subscriberId;
	
	private String correlationId;

	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
}
