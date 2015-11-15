package org.aries.launcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aries.Assert;


public class SystemProcessLauncher extends AbstractApplicationLauncher implements SystemProcessLauncherMBean {

	public final String MBEAN_NAME = "Managers:name=SystemProcessLauncher";

	private Program program;

	
	public SystemProcessLauncher(Program program) throws Exception {
		super(program);
		this.program = program;
	}

	public String getMBeanName() {
		return MBEAN_NAME;
	}

	public void initialize() throws Exception {
		super.initialize();
		List<String> command = getCommand(program);
		initializeManager(command);
	}

	protected void initializeEnvironment() {
		super.initializeEnvironment();
	}

	protected List<String> getCommand(Program program) {
		Assert.notNull(program.getPath(), "Path must be specified");
		List<String> command = new ArrayList<String>();
		List<String> programOptions = program.getOptions();
		command.add(program.getPath());
		if (programOptions != null) {
			Iterator<String> iterator = programOptions.iterator();
			while (iterator.hasNext()) {
				String programOption = iterator.next();
				command.add(programOption);
			}
		}
		return command;
	}
	
}
