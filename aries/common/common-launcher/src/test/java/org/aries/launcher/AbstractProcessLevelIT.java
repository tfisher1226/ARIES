package org.aries.launcher;

import org.aries.util.test.AbstractEventTrackedIT;


public abstract class AbstractProcessLevelIT extends AbstractEventTrackedIT {

	protected ApplicationLauncher startService(String name) throws Exception {
		MavenProgram program = createProgram(name);
		program.setHostName("localhost");
		program.setHttpPort(9080);
		program.setJmxPort(1199);
		
		ApplicationLauncher launcher = new MavenApplicationLauncher(program);
		launcher.setEventTracker(getEventTracker());
		launcher.addWaitToken("APPLICATION READY");
		launcher.initialize();
		launcher.start();
		launcher.waitFor();
		return launcher;
	}

	protected ApplicationLauncher startClient(String name) throws Exception {
		MavenProgram program = createProgram(name);
		program.setHostName("localhost");
		program.setHttpPort(9080);
		program.setJmxPort(1299);
		
		ApplicationLauncher launcher = new MavenApplicationLauncher(program);
		launcher.setEventTracker(getEventTracker());
		launcher.addWaitToken("CLIENT DONE");
		launcher.addWaitToken("CLIENT ABORTED");
		launcher.initialize();
		launcher.start();
		launcher.waitFor();
		return launcher;
	}

	protected MavenProgram createProgram(String programName) {
		MavenProgram program = new MavenProgram();
		program.setMavenHome("c:/software/apache-maven-3.0.4");
		program.setMainClass(MavenExecutionHarness.class.getName());
		program.setName(programName);
		program.addProfile(programName);
		//program.addGoal("validate");
		program.initialize();
		return program;
	}

}
