package org.aries.client;

import org.aries.bean.ProxyLocator;
import org.aries.runtime.BeanContext;


//@MXBean
public class ProxyManager implements ProxyManagerMBean {

	public static final String MBEAN_NAME = "ApplicationManagment:name=ProxyManager";


	public String getMBeanName() {
		return MBEAN_NAME;
	}

	public void addClient(String className, String clientId) {
		try {
			Client client = ClientFactory.createClient(className);
			ProxyLocator proxyLocator = BeanContext.get("org.aries.proxyLocator");
			proxyLocator.add(clientId, client, "RMI");
			proxyLocator.add(clientId, client, "EJB");
			proxyLocator.add(clientId, client, "HTTP");
			proxyLocator.add(clientId, client, "JMS");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
