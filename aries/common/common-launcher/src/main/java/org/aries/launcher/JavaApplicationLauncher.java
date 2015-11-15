package org.aries.launcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.properties.PropertyManager;


public class JavaApplicationLauncher extends AbstractApplicationLauncher implements JavaApplicationLauncherMBean {

	public final String MBEAN_NAME = "Managers:name=JavaApplicationLauncher";

	
	public JavaApplicationLauncher() {
		//does nothing
	}

	public JavaApplicationLauncher(JavaProgram program) throws Exception {
		super(program);
	}

	public String getMBeanName() {
		return MBEAN_NAME;
	}

	public JavaProgram getJavaProgram() {
		return (JavaProgram) program;
	}

	public void initialize() throws Exception {
		super.initialize();
		String classPath = getClassPath();
		log.info("Using CLASSPATH: "+classPath);
		JavaProgram javaProgram = getJavaProgram();
		javaProgram.setJavaHome(System.getProperty("java.home"));
		javaProgram.setClassPath(classPath);
		List<String> command = getCommand(javaProgram);
		initializeManager(command);
	}

	protected void initializeEnvironment() {
		super.initializeEnvironment();
		addEnvironmentVariable("JAVA_HOME", getJavaProgram().getJavaHome());
		addEnvironmentVariable("CLASSPATH", getJavaProgram().getClassPath());
	}

	protected List<String> getCommand(Program program) {
		JavaProgram javaProgram = (JavaProgram) program;
		String mainClass = javaProgram.getMainClass();
		List<String> programOptions = javaProgram.getOptions();
		return getCommand(mainClass, programOptions);
	}

	protected List<String> getCommand(String mainClass, List<String> programOptions) {
		String java = isWin32() ? "javaw" : "java";
		List<String> command = new ArrayList<String>();
		JavaProgram javaProgram = getJavaProgram();
		String javaHome = javaProgram.getJavaHome();
		Assert.notNull(javaHome, "JavaHome must be specified");
		String javaCommand = javaHome+"/bin/"+java;
		javaCommand = javaCommand.replace("/", "\\");
		command.add(javaCommand);
		command.add("-server");
		//TODO command.add("-Duser.dir="+archivePath);
		if (programOptions != null) {
			Iterator<String> iterator = programOptions.iterator();
			while (iterator.hasNext()) {
				String programOption = iterator.next();
				command.add(programOption);
			}
		}
		command.add(mainClass);
		return command;
	}


//	protected void unpackJarFile(String jarFile, String archivePath) {
//		try {
//			JarFileUnpacker unpacker = new JarFileUnpacker();
//			unpacker.unpack(jarFile, getApplicationHome(), archivePath);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}

//	protected String getArchivePath(String jarFile) {
//		String jarDirectory = jarFile.substring(0, jarFile.lastIndexOf(".jar"));
//		String archivePath = getApplicationHome()+"/"+jarDirectory;
//		return archivePath;
//	}

	protected String getApplicationHome() {
		String userDir = System.getProperty("user.dir");
		String applicationHome = PropertyManager.getInstance().get("applicationHome");
		if (applicationHome != null)
			return applicationHome;
		return userDir;
	}

	protected String getApplicationDirectory(String archiveFile) {
		int position = archiveFile.lastIndexOf(".jar");
		String name = archiveFile.substring(0, position);
		return name;
	}

	protected String getClassPath() {
		String fs = System.getProperty("file.separator");
		String ps = System.getProperty("path.separator");
		String classPath = System.getProperty("java.class.path");
		classPath += ps+getClassPath(System.getProperty("user.dir"));
		String applicationHome = getApplicationHome();
		classPath += ps+getClassPath(applicationHome);
		classPath += ps+applicationHome+fs+"target"+fs+"classes";
		classPath += ps+applicationHome+fs+"target"+fs+"test-classes";
		classPath += ps+applicationHome;
		classPath += ps+".";
		return classPath;
	}
	
	protected String getClassPath(String location) {
		File directory = new File(location);
		if (directory.exists() && directory.isDirectory()) {
			StringBuffer buf = new StringBuffer();
			String fs = System.getProperty("file.separator");
			String ps = System.getProperty("path.separator");
			//String libDir = getClassPath(location+fs+"lib");
			File libDir = new File(location+fs+"lib");
			String classPath = getClassPath(libDir);
			if (!StringUtils.isEmpty(classPath))
				buf.append(ps+classPath);
			return buf.toString();
		}
		return "";
	}
	
	protected String getClassPath(File file) {
		String fs = System.getProperty("file.separator");
		String ps = System.getProperty("path.separator");
		if (!file.exists() || !file.canRead())
			return "";
		if (file.isDirectory()) {
			StringBuffer classPath = new StringBuffer();
			String[] list = file.list();
			for (int i=0; i < list.length; i++) {
				File f = new File(file.getAbsolutePath()+fs+list[i]);
				String s = getClassPath(f);
				if (s.length() > 0 && i > 0)
					classPath.append(ps);
				classPath.append(s);
			}
			return classPath.toString();
		}
		String filename = file.getName();
		if (filename.toLowerCase().endsWith(".jar") || filename.toLowerCase().endsWith(".zip"))
			return file.getAbsolutePath();
		return "";
	}


	
	
//	String archivePath = null;
//	String archiveLocation = null;
//	if (program.getArchiveLocation() != null) {
//		String fs = System.getProperty("file.separator");
//		archivePath = getApplicationHome()+fs+program.getArchiveLocation();
//		archiveLocation = program.getArchiveLocation();
//	} else if (program.getArchiveFile() != null) {
//		String archiveFile = program.getArchiveFile();
//		archivePath = getArchivePath(archiveFile);
//		archiveLocation = getApplicationDirectory(archiveFile);
//		//TODO unpackJarFile(archiveFile, archivePath);
//	}
	
//	public void stop() {
//	String name = program.getProgramName();
//	try {
//		AdminClient adminClient = new AdminClient();
//		if (!adminClient.isConnected()) {
//			log.info("Connecting: "+name);
//			int port = Integer.parseInt(program.getJmxPort());
//			adminClient.connect("localhost", port, "Services:name=AdminService");
//		}
//		log.info("Stopping: "+name);
//		String[] request = new String[2];
//		request[0] = "stop";
//		request[1] = name;
//		adminClient.execute(request);
//		adminClient.shutdown();
//		Thread.sleep(1000);
//		log.info("Stopped: "+name);
//	} catch (ConnectException e) {
//		//already stopped
//	} catch (Throwable e) {
//		log.error("Problem stopping: "+name, e);
//		throw new RuntimeException(e);
//	} finally {
//		try {
//			log.info("Killing: "+name);
//			_processManager.stop();
//			log.info("Killed: "+name);
//		} catch (Throwable e) {
//			log.error("Problem killing: "+name, e);
//			throw new RuntimeException(e);
//		}
//	}
//}

//public Object execute(String[] request) {
//	if (!isActive())
//		return null;
//	String processName = request[0];
//	String jobName = request[1];
//	try {
//		AdminClient adminClient = new AdminClient();
//		if (!adminClient.isConnected()) {
//			log.info("Connecting: "+processName);
//			int port = Integer.parseInt(program.getJmxPort());
//			adminClient.connect("localhost", port+1, "Services:name=AdminService");
//		}
//		log.info("Executing: process="+processName+", job="+jobName);
//		Object result = adminClient.execute(request);
//		adminClient.shutdown();
//		return result;
//	} catch (ConnectException e) {
//		throw new RuntimeException("Cannot connect: process="+processName);
//	} catch (Throwable e) {
//		log.error("Problem executing: process="+processName+", job="+jobName, e);
//		throw new RuntimeException(e);
//	}
//}

}
