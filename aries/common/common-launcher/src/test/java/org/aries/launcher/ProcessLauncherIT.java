package org.aries.launcher;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.aries.launcher.ProcessLauncher;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ProcessLauncherIT extends TestCase {

	private ProcessLauncher fixture;


	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new ProcessLauncher();
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		super.tearDown();
	}

	protected String createCommandForTest() {
		return "java ";
	}

	@Test
	public void testProcessLauncher() throws Exception {
		assertNotNull("ObjectName should be set", fixture.getObjectName());
	}

	@Test
	public void testGetUserDirectory() throws Exception {
		assertNotNull("ObjectName should be set", fixture.getUserDirectory());
		String userDir = System.getProperty("user.dir");
		assertEquals("UserDirectory should be correct", userDir, fixture.getUserDirectory());
	}

	@Test
	@Ignore //needs a real command
	public void testGetRunnable() throws Exception {
		fixture.setRunner(null);
		Runnable runnable = fixture.getRunner();
		assertNotNull("Runnable should be set", runnable);
		assertEquals("Runnable should be same", runnable, fixture.getRunner());
		MockProcessExecuter mockExecuter = new MockProcessExecuter();
		fixture.setExecuter(mockExecuter);
		List<String> command = new ArrayList<String>();
		command.add("command");
		fixture.setCommand(command);
		runnable.run();
		assertTrue("ProcessExecuter should have been run", mockExecuter.wasRun());
	}

	@Test
	public void testStart() throws Exception {
		MockRunnable mockRunnable = new MockRunnable();
		fixture.setRunner(mockRunnable);
		fixture.setThread(null);
		fixture.start();
		assertTrue("Thread should be alive", fixture.getThread().isAlive());
		assertFalse("Thread should not be interrupted", fixture.getThread().isInterrupted());
		fixture.getThread().join();
		assertTrue("Runnable should have been run", mockRunnable.wasRun());
	}

	@Test
	public void testDestroy() throws Exception {
		MockProcess mockProcess = new MockProcess();
		fixture.setProcess(mockProcess);
		fixture.destroy();
		assertTrue("Process should have been destroyed", mockProcess.wasDestroyed());
		assertNull("Process should now be null", fixture.getProcess());
	}

}
