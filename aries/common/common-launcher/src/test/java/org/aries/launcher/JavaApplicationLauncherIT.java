package org.aries.launcher;

import org.aries.Assert;
import org.aries.launcher.JavaApplicationLauncher;
import org.aries.launcher.JavaProgram;
import org.aries.util.ObjectUtil;
import org.aries.util.test.AbstractMultiThreadedIT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Generated by Nam.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JavaApplicationLauncherIT extends AbstractMultiThreadedIT {

	private JavaApplicationLauncher fixture;


	@Before
	public void setUp() throws Exception {
		super.setUp();
		fixture = createFixture();
		ObjectUtil.writeObjectToTempFile("", "test");
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
		super.tearDown();
	}

	protected JavaApplicationLauncher createFixture() throws Exception {
		fixture = new JavaApplicationLauncher(createProgramForTest());
		return fixture;
	}

	protected JavaProgram createProgramForTest() {
		JavaProgram program = new JavaProgram();
		program.setName("test_program");
		program.setMainClass(getClass().getCanonicalName());
		return program;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Executing within process...");
		ObjectUtil.writeObjectToTempFile("DONE", "test");
	}
	
	@Test
	public void testExecution() throws Exception {
		fixture.initialize();
		fixture.start();
		fixture.join();
		String output = ObjectUtil.readObjectFromTempFile("test");
		Assert.equals(output, "DONE", "Unexpected output");
	}

}