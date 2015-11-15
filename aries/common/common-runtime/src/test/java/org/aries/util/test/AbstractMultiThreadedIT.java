package org.aries.util.test;

import org.junit.After;
import org.junit.Before;


public abstract class AbstractMultiThreadedIT extends AbstractIT {

	private ClassLoader contextClassLoader;


	@Before
	public void setUp() throws Exception {
		contextClassLoader = Thread.currentThread().getContextClassLoader();
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		Thread.currentThread().setContextClassLoader(contextClassLoader);
	}

	protected void stopProcesses() throws Exception {
	}

}
