package org.aries.launcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PerlApplicationLauncher extends AbstractApplicationLauncher implements PerlApplicationLauncherMBean {

	private static Log log = LogFactory.getLog(PerlApplicationLauncher.class);

	private PerlProgram program;

	
	public PerlApplicationLauncher(PerlProgram program) throws Exception {
		super(program);
		this.program = program;
	}

	public String getMBeanName() {
		return NAME;
	}

	protected List<String> getPerlCommand() {
		List<String> command = new ArrayList<String>();
		String processName = program.getPath();
		String processLocation = program.getLocation();
		List<String> processArguments = program.getArguments();
		command.add(program.getPerlHome()+"/bin/perl");
		command.add(processLocation+"/"+processName); 
		Iterator<String> iterator = processArguments.iterator();
		while (iterator.hasNext()) {
			String processArgument = iterator.next();
			command.add(processArgument);
		}
		return command;
	}


}
