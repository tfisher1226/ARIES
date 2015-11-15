package org.aries.launcher;

import java.util.List;
import java.util.Map;

import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.io.StreamListener;
import org.aries.util.properties.PropertyManager;

import common.jmx.MBeanUtil;


public abstract class AbstractApplicationLauncher { //implements Launcher {

	public abstract String getMBeanName();

	protected Log log = LogFactory.getLog(getClass());

	protected ObjectName objectName;

	protected Program program;

	protected ProcessManager processManager;

	protected long startTime;

	protected boolean isWin32;

	protected boolean isWinXP;


	public AbstractApplicationLauncher() {
		//does nothing
	}

	public AbstractApplicationLauncher(Program program) {
		setProgram(program);
	}

	public void setProgram(Program program) {
		this.program = program;
	}
	
	public boolean isWin32() {
		return isWin32;
	}

	public boolean isWinXP() {
		return isWinXP;
	}
	
	public long getStartTime() {
		return startTime;
	}

	protected StreamListener getStreamListener() {
		return null;
	}


	/*
	 * Remote Interface Methods
	 * ------------------------
	 */

	public boolean isActive() {
		return processManager.isActive();
	}

	public void start() throws Exception {
		try {
			log.info("Starting: "+program.getName());
			processManager.start();
			log.info("Started: "+program.getName());
		} catch (Exception e) {
			log.error("Problem starting: "+program.getName(), e);
			throw e;
		}
	}

	public void join() throws Exception {
		try {
			log.info("Joining: "+program.getName());
			processManager.join();
			log.info("Returned: "+program.getName());
		} catch (Exception e) {
			log.error("Problem joining: "+program.getName(), e);
			throw e;
		}
	}

	//@Override
	public void stop() throws Exception {
		try {
			log.info("Stopping: "+program.getName());
			if (processManager.isActive())
				processManager.stop();
			MBeanUtil.unregisterMBean(objectName);
			log.info("Stopped: "+program.getName());
		} catch (Throwable e) {
			log.error("Problem closing: "+program.getName(), e);
			throw new RuntimeException(e);
		}
	}


	/*
	 * Internal Methods
	 * ----------------
	 */

	public ObjectName getObjectName() {
		return objectName;
	}

	protected ObjectName makeObjectName() {
		StringBuffer buf = new StringBuffer(getMBeanName());
		buf.append(",application="+program.getName());
		//buf.append(",isWin32="+_isWin32);
		return MBeanUtil.makeObjectName(buf.toString());
	}
	
	public void initialize() throws Exception {
		PropertyManager.getInstance().initialize();
		isWin32 = System.getProperty("os.name").toLowerCase().indexOf("windows") != -1;
		isWinXP = System.getProperty("os.name").toLowerCase().indexOf("windows xp") != -1;
		objectName = makeObjectName();
		MBeanUtil.registerMBean(this, objectName);
		log.info("registered MBean: "+objectName);
	}
	
	public void initializeManager(List<String> command) throws Exception {
		processManager = createProcessManager(command);
		processManager.initialize();
		initializeEnvironment();
	}

	protected void initializeEnvironment() {
		//String ps = System.getProperty("path.separator");
		Map<String, String> environmentVariables = System.getenv();
		//System.out.println(environmentVariables.size());

		if (isWin32()) {
			//add SystemRoot
			if (isWinXP() || true)
				addEnvironmentVariable("SystemRoot", "C:\\WINDOWS");
			//else environmentVariables.put("SystemRoot", "C:\\WINNT");
		}
		//TODO make isSolaris field
		//if (!isWin32()) {
			appendEnvironmentVariable("PATH", environmentVariables.get("PATH"));
			appendEnvironmentVariable("PATH", System.getProperty("PATH"));
			appendEnvironmentVariable("LD_LIBRARY_PATH", environmentVariables.get("LD_LIBRARY_PATH"));
			appendEnvironmentVariable("LD_LIBRARY_PATH", System.getProperty("LD_LIBRARY_PATH"));
			appendEnvironmentVariable("LD_LIBRARY_PATH", System.getProperty("java.library.path"));
		//}
		//log the current state
		log.info("isWin32="+isWin32());
		//log.info("isSolaris="+isSolaris());
		//log.info("isCentos="+isCentos());
		//System.getProperties().list(System.out);
		log.info("PATH="+getEnvironmentVariable("PATH"));
		log.info("LD_LIBRARY_PATH="+getEnvironmentVariable("LD_LIBRARY_PATH"));
	}
	
	protected String getEnvironmentVariable(String name) {
		return processManager.getEnvironment().get(name);
	}

	protected void addEnvironmentVariable(String name, String value) {
		processManager.getEnvironment().put(name, value);
	}

	protected void appendEnvironmentVariable(String name, String value) {
		if (value != null) {
			String currentValue = processManager.getEnvironment().get(name);
			if (currentValue != null)
				value = currentValue+value;
			processManager.getEnvironment().put(name, value);
		}
	}
	protected ProcessManager createProcessManager(List<String> command) {
		ProcessManager processManager = new ProcessManager(program.getName(), command);
		processManager.setOutputEnabled(program.isOutputEnabled());
		processManager.setStreamListener(getStreamListener());
		processManager.setMonitored(program.isMonitored());
		return processManager;
	}

	protected String getArchivePath(String jarFile) {
		String jarDirectory = jarFile.substring(0, jarFile.lastIndexOf(".jar"));
		String archivePath = getApplicationHome()+"/"+jarDirectory;
		return archivePath;
	}

	protected String getApplicationHome() {
		String fs = System.getProperty("file.separator");
		String userDir = System.getProperty("user.dir");
		String appHome = userDir+fs+PropertyManager.getInstance().get("applicationHome");
		return appHome;
	}

	protected String getApplicationDirectory(String archiveFile) {
		int position = archiveFile.lastIndexOf(".jar");
		String name = archiveFile.substring(0, position);
		return name;
	}

}
