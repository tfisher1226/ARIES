/*
 * ProcessManagerTest.java
 * Created on Jun 2, 2005
 * 
 * 
 */
package org.aries.launcher;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.aries.Assert;
import org.aries.launcher.ProcessManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ProcessManagerTest extends TestCase {

    private ProcessManager fixture;
    
	private boolean processWasRun;
	
	private boolean monitorWasRun;
	

	@Before
	public void setUp() throws Exception {
		fixture = new ProcessManager("name", createCommandForTest());
	}

	@After
	public void tearDown() throws Exception {
        if (fixture.getProcessThread() != null)
            fixture.getProcessThread().interrupt();
        if (fixture.getMonitorThread() != null)
            fixture.getMonitorThread().interrupt();
		fixture = null;
	}

	private List<String> createCommandForTest() {
		List<String> command = new ArrayList<String>();
		command.add("java");
        return command;
    }
	
	@Test
	@Ignore
	public void testProcessManager() throws Exception {
	    Assert.equals(createCommandForTest(), fixture.getCommand(), "Command should be correct");
	    //TODO assertEquals("SleepTime should be correct", ProcessManager.INITIAL_RESTART_SLEEP_INTERVAL, fixture.getSleepTime());
	    assertNotNull("Launcher should not be null", fixture.getLauncher());
	    assertNotNull("ShutdownHook should not be null", fixture.getShutdownHook());
	    boolean status = Runtime.getRuntime().removeShutdownHook(fixture.getShutdownHook());
	    assertTrue("ShutdownHook should have been set", status);
	}

	@Test
	@Ignore
	public void testShutdownRunnable() throws Exception {
	    Runnable runnable = fixture.createShutdownRunnable();
	    assertNotNull("ShutdownRunnable should not be null", runnable);
	    //make sure stopProcess() was called
	    fixture.setProcessThread(new Thread());
	    runnable.run();
	    assertNull("Process thread should be null", fixture.getProcessThread());
	    assertFalse("Process state should not be active", fixture.isActive());  
	}
	
	@Test
	@Ignore
	public void testIsProcessActive() throws Exception {
	    fixture.setProcessThread(null);
	    assertFalse("Process state should not be active", fixture.isActive());
	    Thread thread = new Thread();
	    fixture.setProcessThread(thread);
	    assertFalse("Process state should not be active", fixture.isActive());
	    thread.start();
	    assertTrue("Process state should be active", fixture.isActive());
	}
	
	@Test
	@Ignore
	public void testStart_Normal() throws Exception {
	    fixture.setProcessThread(null);
	    fixture.setMonitorThread(null);
	    fixture.start();
	    //just make sure process and monitor were started
	    assertNotNull("Process thread should not be null", fixture.getProcessThread());
	    assertNotNull("Monitor thread should not be null", fixture.getMonitorThread());
	    assertTrue("Process thread should be alive", fixture.getProcessThread().isAlive());
	    assertTrue("Monitor thread should be alive", fixture.getMonitorThread().isAlive());
	    assertFalse("Process thread should not be interrupted", fixture.getProcessThread().isInterrupted());
	    assertFalse("Monitor thread should not be interrupted", fixture.getMonitorThread().isInterrupted());
	}
	
	@Test
	@Ignore
    public void testStart_AlreadyStarted() throws Exception {
        try {
            Thread processThread = new Thread();
            Thread monitorThread = new Thread();
            processThread.start();
            monitorThread.start();
            fixture.setProcessThread(processThread);
            fixture.setMonitorThread(monitorThread);
            fixture.start();
        } catch (IllegalThreadStateException e) {
            String expected = "Process thread not have been created";
            assertEquals("Exception should be thrown", expected, e.getMessage());
        }
    }
    
	private Runnable createProcessRunnableForTest() {
	    return new Runnable() {
            public void run() {
                processWasRun = true;
            }
	    };
	}
	
	@Test
	@Ignore
	public void testStartProcess_Normal() throws Exception {
	    fixture.setProcessRunnable(createProcessRunnableForTest());
	    fixture.setProcessThread(null);
	    fixture.startProcess();
	    assertTrue("Process thread should be alive", fixture.getProcessThread().isAlive());
	    assertFalse("Process thread should not be interrupted", fixture.getProcessThread().isInterrupted());
	    fixture.getProcessThread().join();
	    assertTrue("Process runnable should have been run", processWasRun);
	}

	@Test
	@Ignore
	public void testStartProcess_IllegalState() throws Exception {
	    try {
	        fixture.setProcessThread(new Thread());
	        fixture.startProcess();
	        fail("Exception should have been thrown");
	    } catch (IllegalThreadStateException e) {
	        //do nothing
	    }
	}
	
	private Runnable createMonitorRunnableForTest() {
	    return new Runnable() {
            public void run() {
                monitorWasRun = true;
            }
	    };
	}
	
	@Test
	@Ignore
	public void testStartMonitor_Normal() throws Exception {
	    fixture.setMonitorRunnable(createMonitorRunnableForTest());
	    fixture.setMonitorThread(null);
	    fixture.startMonitor();
	    assertTrue("Monitor thread should be alive", fixture.getMonitorThread().isAlive());
	    assertFalse("Monitor thread should not be interrupted", fixture.getMonitorThread().isInterrupted());
	    fixture.getMonitorThread().join();
	    assertTrue("Monitor runnable should have been run", monitorWasRun);
	}

	@Test
	@Ignore
	public void testStartMonitor_IllegalState() throws Exception {
	    try {
	        fixture.setMonitorThread(new Thread());
	        fixture.startMonitor();
	        fail("Exception should have been thrown");
	    } catch (IllegalThreadStateException e) {
	        //do nothing
	    }
	}

	@Test
	@Ignore
	public void testExecute() throws Exception {
	    assertNotNull("Launcher should not be null", fixture.getLauncher());
	    MockProcessLauncher mockLauncher = new MockProcessLauncher();
	    fixture.setLauncher(mockLauncher);
	    assertFalse("Launcher should not have been run", mockLauncher.wasRun);
	    fixture.execute();
	    assertTrue("Launcher should have been run", mockLauncher.wasRun);
	}

	@Test
	@Ignore
	public void testMonitor() throws Exception {
        //do nothing
	}
	
}
