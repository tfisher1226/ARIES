package org.aries.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.management.MBeanServerConnection;
import javax.naming.NamingException;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JaxwsTestSetup extends TestSetup {

	private static Log log = LogFactory.getLog(JaxwsTestSetup.class);

	private JaxwsTestHelper delegate = new JaxwsTestHelper();

	private String[] archives = new String[0];

	private ClassLoader originalClassLoader;


	public JaxwsTestSetup(Class<?> testClass, String archiveList) {
		super(new TestSuite(testClass));
		getArchiveArray(archiveList);
	}

	public JaxwsTestSetup(Test test, String archiveList) {
		super(test);
		getArchiveArray(archiveList);
	}

	public JaxwsTestSetup(Test test) {
		super(test);
	}

	public File getArchiveFile(String archive) {
		return delegate.getArchiveFile(archive);
	}

	public URL getArchiveURL(String archive) throws MalformedURLException {
		return delegate.getArchiveFile(archive).toURL();
	}

	public File getResourceFile(String resource) {
		return delegate.getResourceFile(resource);
	}

	public URL getResourceURL(String resource) throws MalformedURLException {
		return delegate.getResourceFile(resource).toURL();
	}

	private void getArchiveArray(String archiveList) {
		if (archiveList != null) {
			StringTokenizer st = new StringTokenizer(archiveList, ", ");
			archives = new String[st.countTokens()];

			for (int i = 0; i < archives.length; i++)
				archives[i] = st.nextToken();
		}
	}

	protected void setUp() throws Exception {
		// verify integration target
		String integrationTarget = delegate.getIntegrationTarget();
		log.debug("Integration target: " + integrationTarget);

		List<URL> clientJars = new ArrayList<URL>();
		for (int i = 0; i < archives.length; i++) {
			String archive = archives[i];
			try {
				delegate.deploy(archive);
			} catch (Exception ex) {
				ex.printStackTrace();
				delegate.undeploy(archive);
			}

			if (archive.endsWith("-client.jar")) {
				URL archiveURL = getArchiveURL(archive);
				clientJars.add(archiveURL);
			}
		}

		ClassLoader parent = Thread.currentThread().getContextClassLoader();
		originalClassLoader = parent;
		// add client jars to the class loader
		if (!clientJars.isEmpty()) {
			URL[] urls = new URL[clientJars.size()];
			for (int i = 0; i < clientJars.size(); i++) {
				urls[i] = clientJars.get(i);
			}
			URLClassLoader cl = new URLClassLoader(urls, parent);
			Thread.currentThread().setContextClassLoader(cl);
		}
	}

	protected void tearDown() throws Exception {
		try {
			for (int i = 0; i < archives.length; i++) {
				String archive = archives[archives.length - i - 1];
				delegate.undeploy(archive);
			}
		} finally {
			Thread.currentThread().setContextClassLoader(originalClassLoader);
		}
	}

	public MBeanServerConnection getServer() throws NamingException {
		return JaxwsTestHelper.getServer();
	}
}
