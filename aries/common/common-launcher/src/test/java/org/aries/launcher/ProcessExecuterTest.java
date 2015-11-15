package org.aries.launcher;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.aries.Assert;
import org.aries.launcher.ProcessExecuter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ProcessExecuterTest extends TestCase {

	private ProcessExecuter fixture;

	private Process mockProcess;

	private Runtime mockRuntime;

	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new ProcessExecuter();
		mockProcess = mock(Process.class); 
		mockRuntime = mock(Runtime.class); 
		fixture.setRuntime(mockRuntime);
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		mockProcess = null;
		mockRuntime = null;
		super.tearDown();
	}

	protected List<String> getSimpleCommandForTest() {
		List<String> commandLine = new ArrayList<String>();
		commandLine.add("echo Hello");
		return commandLine;
	}

	protected Map<String, String> getEnvironmentForTest() {
		Map<String, String> environment = new HashMap<String, String>();
		environment.put("SystemRoot", "C:\\Windows");
		return environment;
	}

	protected File getLocationForTest() {
		File location = new File(getUserDirectory());
		return location;
	}

	protected String getUserDirectory() {
		return System.getProperty("user.dir");
	}

	@Test
	public void testExec_Simple() throws Exception {
		List<String> command = getSimpleCommandForTest();
		Map<String, String> environment = getEnvironmentForTest();
		File location = getLocationForTest();
		String[] commandArray = fixture.toCommandArray(command);
		String[] variableArray = fixture.toVariableArray(environment);
		when(mockRuntime.exec(commandArray, variableArray, location)).thenReturn(mockProcess);
		Process process = fixture.exec(command, environment, location);
		assertNotNull("Process should not be null", process);
		Assert.equals(process, mockProcess, "Process not correct");
		verify(mockRuntime).exec(commandArray, variableArray, location);
	}

}
