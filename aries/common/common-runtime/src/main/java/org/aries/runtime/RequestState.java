package org.aries.runtime;

import javax.inject.Singleton;


@Singleton
public class RequestState {

	private String correlationId;

	
	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
}
