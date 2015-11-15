package org.aries.launcher;

import org.aries.launcher.Program;
import org.aries.launcher.SystemProcessLauncher;
import org.aries.util.ObjectUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SystemProcessLauncherIT {

	private SystemProcessLauncher fixture;


	@Before
	public void setUp() throws Exception {
		fixture = createFixture();
		ObjectUtil.writeObjectToTempFile("", "test");
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
	}

	protected SystemProcessLauncher createFixture() throws Exception {
		fixture = new SystemProcessLauncher(createProgramForTest());
		return fixture;
	}

	protected Program createProgramForTest() {
		String cygwinHome = "c:\\software\\cygwin-1.7.9";
		Program program = new Program();
		program.setName("diff");
		program.setPath(cygwinHome+"\\bin\\diff.exe");
		String fs = System.getProperty("file.separator");
		program.getOptions().add(getTestPath()+fs+"sample1.log");
		program.getOptions().add(getTestPath()+fs+"sample2.log");
		return program;
	}

	protected String getTestPath() {
		String fs = System.getProperty("file.separator");
		String userDir = System.getProperty("user.dir");
		String packageName = getClass().getPackage().getName();
		String packagePath = packageName.replace(".", fs);
		String testPath = userDir+fs+"source"+fs+"verify"+fs+"java"+fs+packagePath;
		return testPath;
	}
	
	@Test
	public void testExecution() throws Exception {
		fixture.initialize();
		fixture.start();
		fixture.join();
		//String output = ObjectUtil.readObjectFromTempFile("test");
		//Assert.equals(output, "DONE", "Unexpected output");
	}

}
