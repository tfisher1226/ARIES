package org.aries.test;

import java.io.Serializable;
import java.net.URL;
import java.security.Principal;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;


public class TestDeployerTomcat implements TestDeployer {

	private static final String MAIN_DEPLOYER = "jboss.system:service=MainDeployer";

	private MBeanServerConnection server;
	
	private String username;
	
	private String password;

	
	public TestDeployerTomcat(MBeanServerConnection server) {
		this.server = server;

		username = System.getProperty("jmx.authentication.username");
		if ("${jmx.authentication.username}".equals(username))
			username = null;

		password = System.getProperty("jmx.authentication.password");
		if ("${jmx.authentication.password}".equals(password))
			password = null;
	}

	public void deploy(URL url) throws Exception {
		invokeMainDeployer("deploy", url);
	}

	public void undeploy(URL url) throws Exception {
		invokeMainDeployer("undeploy", url);
	}

	private void invokeMainDeployer(String methodName, URL url) throws Exception {
		Principal prevUsername = null;
		Object prevPassword = null;

		//SPIProvider spiProvider = SPIProviderResolver.getInstance().getProvider();
		//SecurityAdaptor securityAdaptor = spiProvider.getSPI(SecurityAdaptorFactory.class).newSecurityAdapter();
		
		//if (username != null || password != null) {
		//	prevUsername = securityAdaptor.getPrincipal();
		//	prevPassword = securityAdaptor.getCredential();
		//	securityAdaptor.setPrincipal(new SimplePrincipal(username));
		//	securityAdaptor.setCredential(password);
		//}

		try {
			server.invoke(new ObjectName(MAIN_DEPLOYER), methodName, new Object[] { url }, new String[] { "java.net.URL" });
		} finally {
			//if (username != null || password != null) {
			//	securityAdaptor.setPrincipal(prevUsername);
			//	securityAdaptor.setCredential(prevPassword);
			//}
		}
	}

	@SuppressWarnings("serial")
	public static class SimplePrincipal implements Principal, Serializable {
		private String name;

		public SimplePrincipal(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
