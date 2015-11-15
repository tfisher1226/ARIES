package org.aries.jms.util;

import java.net.URL;

import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.jmx.client.MBeanClient;


public class AbstractJBossServerProxy {

	protected Log log = LogFactory.getLog(getClass());
	
	public final static String SERVER_INFO = "jboss.system:type=ServerInfo";

	public final static String SERVER_CONFIG = "jboss.system:type=ServerConfig";

	protected MBeanClient client;


	public AbstractJBossServerProxy(String host, int port) {
		client = new MBeanClient();
		client.setBindAddress(host);
		client.setManagementPort(Integer.toString(port));
	}

	public void initialize() throws Exception {
		//nothing for now
	}
	
	public boolean ping() throws Exception {
		try {
			getHostName();
		} catch (Throwable e) {
			log.error(e);
		}
		return true;
	}
	
	public String getHostName() throws Exception {
		ObjectName objectName = new ObjectName(SERVER_INFO);
		String name = (String) client.getMBeanServer().getAttribute(objectName, "HostName");
		//String name = (String) client.invoke(objectName, "getHostName");
		return name;
	}
	
	public URL getHomeURL() throws Exception {
		ObjectName objectName = new ObjectName(SERVER_CONFIG);
		//URL url = (URL) client.getMBeanServer().getAttribute(objectName, "jbossHome");
		URL url = (URL) client.invoke(objectName, "getJBossHome");
		return url;
	}
	
	public URL getServerHomeURL() throws Exception {
		ObjectName objectName = new ObjectName(SERVER_CONFIG);
		//URL url = (URL) client.getMBeanServer().getAttribute(objectName, "serverHomeLocation");
		URL url = (URL) client.invoke(objectName, "getServerHomeLocation");
		return url;
	}
	


}
