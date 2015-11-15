package org.aries.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.aries.launcher.ProcessExecuter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ProcessExecuterIT extends TestCase {

	private ProcessExecuter fixture;


	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = new ProcessExecuter();
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		super.tearDown();
	}

	protected List<String> getSimpleCommandForTest() {
		List<String> commandLine = new ArrayList<String>();
		commandLine.add("cmd.exe");
		commandLine.add("/C");
		commandLine.add("echo Hello");
		return commandLine;
	}

	protected List<String> getLongerCommandForTest() {
		List<String> commandLine = new ArrayList<String>();
		commandLine.add("cmd.exe");
		commandLine.add("/C");
		commandLine.add("ping berkeley.edu");
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
	public void testExecSimple_ExitValue() throws Exception {
		Process process = fixture.exec(
				getSimpleCommandForTest(), 
				getEnvironmentForTest(), 
				getLocationForTest());
		assertNotNull("Process should not be null", process);
		Thread.sleep(1000); //this should be enough for process to complete
		assertEquals("Process exit value should be correct", 0, process.exitValue());
		assureOutputCorrect(process, "Hello");
	}

	@Test
	public void testExecSimple_WaitFor() throws Exception {
		Process process = fixture.exec(
				getSimpleCommandForTest(), 
				getEnvironmentForTest(), 
				getLocationForTest());
		assertNotNull("Process should not be null", process);
		int exitValue = process.waitFor();
		assertEquals("Process exit value should be correct", 0, exitValue);
		assertEquals("Process exit value should be correct", 0, process.exitValue());
		assureOutputCorrect(process, "Hello");
	}

	@Test
	public void testExecLongerCommand() throws Exception {
		Process process = fixture.exec(
				getLongerCommandForTest(), 
				getEnvironmentForTest(), 
				getLocationForTest());
		assertNotNull("Process should not be null", process);
		try {
			process.exitValue();
			fail("Exception should have been thrown");
		} catch (IllegalThreadStateException e) {
			String expected = "process has not exited";
			assertEquals("Exception should be correct", expected, e.getMessage());
		}
	}

	private void assureOutputCorrect(Process process, String expectedOutput) throws Exception {
		InputStream stream = process.getInputStream();
		assertNotNull("InputStream should not be null", stream);
		assertEquals("InputStream should have available data", expectedOutput.length()+2, stream.available());
		try {
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader buffer = new BufferedReader(reader);
			StringBuffer output = new StringBuffer();
			String line = null;
			while ((line = buffer.readLine()) != null)
				output.append(line);
			assertEquals("Process output should be correct", expectedOutput, output.toString());
		} catch (Exception e) {
			fail("Exception should not have been thrown");
		}
	}

}
