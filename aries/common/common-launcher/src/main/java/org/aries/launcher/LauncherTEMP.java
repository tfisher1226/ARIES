package org.aries.launcher;


public class LauncherTEMP {

	private boolean fork;
	
	private Program program;
	
	private MavenApplicationLauncher processManager;

	
	public static void main(String[] args) throws Exception {
		MavenProgram program = new MavenProgram();
		program.setName("server");
		program.setMavenHome("c:/software/apache-maven-3.0.4");
	}

	
	public LauncherTEMP(Program program) {
		this.program = program;
	}
	
	public void initialize() throws Exception {
		processManager = new MavenApplicationLauncher((MavenProgram) program);
		processManager.initialize();
	}
	
	public void launch() throws Exception {
		//processManager.execute();
	}

}
