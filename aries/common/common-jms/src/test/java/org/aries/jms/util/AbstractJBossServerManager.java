package org.aries.jms.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AbstractJBossServerManager {

	protected Log log = LogFactory.getLog(getClass());
	
	protected String _host;
	
	protected int _port;

	
	public AbstractJBossServerManager(String host, int port) {
		_host = host;
		_port = port;
	}

	public String getHost() {
		return _host;
	}
	
	public void setHost(String value) { 
		_host = value;
	}
	
	public int getJndiPort() {
		return _port;
	}
	
	public void setJndiPort(int value) {
		_port = value;
	}

}
