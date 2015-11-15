package org.aries.runtime;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateful;


@Stateful
@Local(RequestContext.class)
public class RequestContext implements Serializable {

	private String correlationId;

	
	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
}
