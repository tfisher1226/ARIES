package org.aries.util.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.runtime.BeanContext;
import org.junit.After;
import org.junit.Before;

import common.jmx.server.JmxServer;


public abstract class AbstractIT {

	protected final Log log = LogFactory.getLog(getClass());
	
	protected JmxServer jmxServer;
	
	
	@Before
	public void setUp() throws Exception {
		System.out.println("");
		System.out.println("-------------------------------------------------------------------");
		System.out.println("Starting test: "+getClass().getName());
		System.out.println("-------------------------------------------------------------------");
		System.out.println("");
	}

	@After
	public void tearDown() throws Exception {
		BeanContext.clear();
		System.out.println("");
		System.out.println("-------------------------------------------------------------------");
		System.out.println("Finished test: "+getClass().getName());
		System.out.println("-------------------------------------------------------------------");
		System.out.println("");
	}
	
	protected String getTestResourcePath(String testName) {
		String fs = System.getProperty("file.separator");
		String userDir = System.getProperty("user.dir");
		//String className = getClass().getName();
		//String packageName = getClass().getPackage().getName();
		String classPath = getClass().getName().replace(".", fs);
		String testPath = userDir+fs+"source"+fs+"test"+fs+"resources"+fs+classPath+fs+testName+"_Results"+fs;
		return testPath;
	}
	
	protected void startMBeanServer(int jmxPort) throws Exception {
		jmxServer = new JmxServer(jmxPort);
		jmxServer.initialize();
		jmxServer.start();
	}

	protected void stopMBeanServer() throws Exception {
		if (jmxServer != null) {
			jmxServer.unregisterAllMBeans();
			jmxServer.stop();
		}
	}

}
