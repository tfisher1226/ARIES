package org.aries.launcher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.io.StreamListener;
import org.aries.util.io.StreamPrinter;

import common.jmx.MBeanUtil;


public class ProcessLauncher {

	private static Log log = LogFactory.getLog(ProcessLauncher.class);

	private ObjectName objectName;

	private String processName;

	protected List<String> command;

	private Map<String, String> environment;
	
	protected Process process;

	protected Thread thread;

	private Runnable runnable;

	private ProcessExecuter executer;

	private boolean isWin32;

	private boolean isWinXP;

	private boolean outputEnabled = true;

    private StreamListener streamListener;

	private Object mutex = new Object();


	public ProcessLauncher() {
		String mbeanName = "Services:name=ProcessLauncher";
		objectName = MBeanUtil.makeObjectName(mbeanName);
		isWin32 = System.getProperty("os.name").toLowerCase().indexOf("windows") != -1;
		isWinXP = System.getProperty("os.name").toLowerCase().indexOf("windows xp") != -1;
		environment = new HashMap<String, String>();
		executer = createExecuter();
	}

	public boolean isWin32() {
		return isWin32;
	}

	public boolean isWinXP() {
		return isWinXP;
	}

	public boolean isOutputEnabled() {
		return outputEnabled;
	}

	public void setOutputEnabled(boolean enabled) {
		outputEnabled = enabled;
	}

    public StreamListener getStreamListener() {
        return streamListener;
    }
    
    public void setStreamListener(StreamListener streamListener) {
    	this.streamListener = streamListener;
    }

	public ObjectName getObjectName() {
		return objectName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String name) {
		processName = name;
	}

	public List<String> getCommand() {
		return command;
	}

	public void setCommand(List<String> command) {
		this.command = command;
	}

	public Thread getThread() {
		return thread;
	}

	protected void setThread(Thread thread) {
		this.thread = thread;
	}

	public Process getProcess() {
		synchronized(mutex) {
			return process;
		}
	}

	protected void setProcess(Process process) {
		this.process = process;
	}

	protected ProcessExecuter createExecuter() {
		return new ProcessExecuter();
	}

	protected ProcessExecuter getExecuter() {
		return executer;
	}

	protected void setExecuter(ProcessExecuter executer) {
		this.executer = executer;
	}

	protected String getUserDirectory() {
		return System.getProperty("user.dir");
	}


	protected List<String> getCommandLine(List<String> command) {
		List<String> commandLine = new ArrayList<String>();
		if (isWin32()) {
			commandLine.add("cmd.exe");
			commandLine.add("/C");
		}
		Iterator<String> iterator = command.iterator();
		while (iterator.hasNext()) {
			String token = iterator.next();
			commandLine.add(token);
		}
		return commandLine;
	}

	protected String getCommandLineAsString(List<String> command) {
		List<String> commandLine = getCommandLine(command);
		StringBuffer buf = new StringBuffer();
		Iterator<String> iterator = commandLine.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String token = iterator.next();
			if (i > 0) 
				buf.append(" ");
			buf.append(token);
		}
		String commandString = buf.toString();
		commandString = commandString.replace("/", "\\");
		return commandString;
	}

	public Map<String, String> getEnvironment() {
		return environment;
	}
	
    public void setEnvironment(Map<String, String> environment) {
    	this.environment = environment;
    }
    
	protected Runnable getRunner() {
		if (runnable == null)
			runnable = new Runnable() {
			public void run() {
				execute(command);
			}
		};
		return runnable;
	}

	protected void setRunner(Runnable runnable) {
		this.runnable = runnable;
	}


	public void start() {
		thread = new Thread(getRunner());
		thread.start();
	}


	public void stop() {
		destroy();
	}


	public int execute(List<String> command) {
		Map<String, String> environment = getEnvironment();
		File location = new File(getUserDirectory());
		int exitValue = execute(command, environment, location);
		return exitValue;
	}


	public int execute(List<String> command, Map<String, String> environment, File location) {
		List<String> commandLine = getCommandLine(command);
		String commandString = getCommandLineAsString(command);
		int exitValue = -1;

		try {
			synchronized(mutex) {
				log.info("COMMAND ENVIRONMENT ("+processName+"): "+environment);
				log.info("COMMAND STARTING ("+processName+"): "+commandString);
				// start(fork) process using specified enviroment at specified location
				process = executer.exec(commandLine, environment, location);
				log.info("COMMAND STARTED ("+processName+"): "+commandString);

				// any error messages or other output?
				StreamPrinter errorPrinter = new StreamPrinter(process.getErrorStream(), System.out, processName, processName+" [stderr] ", "\n");            
				StreamPrinter outputPrinter = new StreamPrinter(process.getInputStream(), System.out, processName, processName+" [stdout] ", "\n");
				errorPrinter.setStreamListener(streamListener);
				outputPrinter.setStreamListener(streamListener);
				outputPrinter.setOutputEnabled(outputEnabled);
				errorPrinter.start();
				outputPrinter.start();

				exitValue = process.waitFor();
				log.info("COMMAND EXITED ("+processName+"): "+commandString);
			}

		} catch (InterruptedException e) {
			/* command was interrrupted */
			log.info("COMMAND INTERRUPTED ("+processName+"): "+commandString);
			log.info("COMMAND EXITED ("+processName+"): "+commandString);

		} catch (Throwable e) {
			/* command failed */
			log.error(e);
			throw new RuntimeException(e);

		} finally {
			destroy();
		}

		return exitValue;
	}


	//TODO Takeout - doesn't work
	public void destroy() {
		try {
			if (process != null) {
				process.destroy();
				process = null;
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			awaitShutdown();
		}
	}

	public void awaitShutdown() {
		//NOT YET
	}
	

	public boolean isActive() {
		try {
			if (process == null)
				return false;
			process.exitValue();
			return false;
		} catch (IllegalThreadStateException e) {
			/* trying to get exitValue of running process */
			return true;
		}
	}

}
