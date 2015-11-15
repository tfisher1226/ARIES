package org.aries.rmi;

import org.aries.util.concurrent.ConcurrentExecutor;
import org.aries.util.concurrent.ConcurrentExecutorImpl;


public class TestExecutor {

	private static final ConcurrentExecutor executor = new ConcurrentExecutorImpl("RMITestExecutor", 3);

	public static ConcurrentExecutor getExecutor() {
		return executor;
	}

}
