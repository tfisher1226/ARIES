package org.aries.tx.service.ejb;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.client.AbstractClient;


public abstract class AbstractClientEJBImpl extends AbstractClient {

	private Log log = LogFactory.getLog(getClass());
	
	protected String serviceId;
	
	protected String host;

	protected int port;


	public AbstractClientEJBImpl() {
		//nothing for now
	}
	
	public AbstractClientEJBImpl(String serviceId, String host, int port) {
		this.serviceId = serviceId;
		this.host = host;
		this.port = port;
	}
	
	public void initialize() throws Exception {
		//nothing for now
	}
	
	public void reset() throws Exception {
		//nothing for now
	}

	public void close() throws Exception {
		//nothing for now
	}

	@Override
	public <T extends Serializable> T invoke(Serializable message) throws Exception {
		return null;
	}

	@Override
	public <T extends Serializable> T invoke(Serializable message, String correlationId, long timeout) throws Exception {
		return null;
	}
	
}
