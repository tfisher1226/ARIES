package org.aries.launcher;

import java.util.List;
import java.util.Map;

import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.io.StreamListener;

import common.jmx.MBeanUtil;


public class ProcessManager {

	private static Log log = LogFactory.getLog(ProcessManager.class);

	protected static final long INITIAL_RESTART_RETRY_COUNT = 3;

	protected static final long INITIAL_RESTART_SLEEP_INTERVAL = (30*1000); // 30 seconds

	protected static final long EVENTUAL_RESTART_SLEEP_INTERVAL = (15*60*1000); // 15 minutes

	protected static final int INACTIVE = 0;

	protected static final int ACTIVE = 1;

	private ObjectName objectName;

	private String processName;

	private List<String> command;

	private Thread shutdownHook;

	private Thread processThread;

	private Thread monitorThread;

	private Runnable processRunner;

	private Runnable monitorRunner;

	private ProcessLauncher launcher;

	//private long sleepTime;

//	private boolean outputEnabled;

	private boolean monitored;

	private boolean runnable;

	private int retries;

	private Object mutex = new Object();

	//private final CountDownLatch shutdownLatch = new CountDownLatch(1);
	

	public ProcessManager(String name, List<String> command) {
		this.processName = name;
		this.command = command;
		this.launcher = createLauncher();
	}

	public ObjectName getObjectName() {
		return objectName;
	}

	public List<String> getCommand() {
		return command;
	}

	public Map<String, String> getEnvironment() {
		return getLauncher().getEnvironment();
	}

	public void setEnvironment(Map<String, String> environment) {
		getLauncher().setEnvironment(environment);
	}

	protected ProcessLauncher getLauncher() {
		return launcher;
	}

	void setLauncher(ProcessLauncher launcher) {
		this.launcher = launcher;
	}

	protected ProcessLauncher createLauncher() {
		ProcessLauncher launcher = new ProcessLauncher();
		return launcher;
	}

	protected Thread getShutdownHook() {
		return shutdownHook;
	}

	protected Runnable createShutdownRunnable() {
		return new Runnable() {
			public void run() {
				//do nothing for now
				//stopProcess();
				//shutdownLatch.countDown();
			}
		};
	}

	public Thread getProcessThread() {
		return processThread;
	}

	void setProcessThread(Thread thread) {
		processThread = thread;
	}

	public void setOutputEnabled(boolean outputEnabled) {
    	getLauncher().setOutputEnabled(outputEnabled);
	}

    public void setStreamListener(StreamListener streamListener) {
    	getLauncher().setStreamListener(streamListener);
    }

	public boolean isMonitored() {
		return monitored;
	}

	public void setMonitored(boolean value) {
		monitored = value;
	}

	public Thread getMonitorThread() {
		return monitorThread;
	}

	void setMonitorThread(Thread thread) {
		monitorThread = thread;
	}

	//public long getSleepTime() {
		//    return _sleepTime;
		//}


	public void initialize() {
		synchronized(mutex) {
			//_sleepTime = INITIAL_RESTART_SLEEP_INTERVAL;
			shutdownHook = new Thread(createShutdownRunnable());
			Runtime.getRuntime().addShutdownHook(shutdownHook);
			String mbeanName = "Managers:name=ProcessManager";
			objectName = MBeanUtil.makeObjectName(mbeanName);
		}
	}

	public void start() {
		synchronized(mutex) {
			if (!isActive()) {
				startProcess();
				if (isMonitored())
					startMonitor();
				setRunnable(true);
			} else {
				log.warn("Process already started");
			}
		}
	}

	public void join() {
		if (isActive()) {
			try {
				processThread.join();
			} catch (InterruptedException e) {
				log.warn("Interrupted");
			}
		} else {
			log.warn("Process not active");
		}
	}

	public void stop() {
		synchronized(mutex) {
			if (!isActive()) {
				log.warn("Process already stopped");
			} else {
				//stopMonitor();
				stopProcess();
				setRunnable(false);
			}
		}
	}

	protected void startProcess() {
		log.info("Starting process: "+this.processName);
		if (processThread != null)
			throw new IllegalThreadStateException("Process thread already created");
		processThread = new Thread(getProcessRunnable());
		processThread.start();
	}

	protected Runnable getProcessRunnable() {
		if (processRunner == null)
			processRunner = new Runnable() {
			public void run() {
				execute();
			}
		};
		return processRunner;
	}

	void setProcessRunnable(Runnable runnable) {
		processRunner = runnable;
	}

	protected void execute() {
		try {
			launcher.setProcessName(processName);
			launcher.execute(command);
		} finally {
			processThread = null;
		}
	}

	protected void startMonitor() {
		retries = 0;
		if (monitorThread != null)
			throw new IllegalThreadStateException("Monitor thread not have been created");
		monitorThread = new Thread(getMonitorRunnable());
		monitorThread.start();
	}

	protected Runnable getMonitorRunnable() {
		if (monitorRunner == null)
			monitorRunner = new Runnable() {
			public void run() {
				monitor();
			}
		};
		return monitorRunner;
	}

	void setMonitorRunnable(Runnable runnable) {
		monitorRunner = runnable;
	}

	protected void monitor() {
		try {
			while (monitorThread != null && !monitorThread.isInterrupted()) {
				if (retries <= INITIAL_RESTART_RETRY_COUNT)
					Thread.sleep(INITIAL_RESTART_SLEEP_INTERVAL);
				else Thread.sleep(EVENTUAL_RESTART_SLEEP_INTERVAL);
				//log.info("Checking process: "+_processName);
				/* check status on currently running process */
				if (isRunnable() && !launcher.isActive()) {
					restart();
					retries++;
				}
			}
		} catch (InterruptedException e) {
			//do nothing for now
		} finally {
			monitorThread = null;
		}
	}

	protected boolean restart() {
		log.info("Restarting process: "+this.processName);
		try {
			stopProcess();
			startProcess();
			return true;
		} catch (Throwable e) {
			log.error(e);
			return false;
		}
	}

	private boolean isRunnable() {
		synchronized(mutex) {
			return runnable;
		}
	}

	void setRunnable(boolean value) {
		synchronized(mutex) {
			runnable = true;
		}
	}

	public boolean isActive() {
		return processThread != null && processThread.isAlive();
	}


	protected void stopProcess() {
		try {
			if (launcher != null)
				launcher.stop();
		} catch (Throwable e) {
			log.error(e);
		} finally {
			log.info("Stopping process: "+this.processName);
			//stopThread(processThread);
			//while (processThread != null)
			//	Thread.yield();
		}
		try {
			//wait for shutdown
			//shutdownLatch.await();
			log.info("Stopped process: "+this.processName);
		} catch (Throwable e) {
			log.error(e);
		}
	}

//	protected void stopMonitor() {
//		log.info("Stopping monitor: "+this.processName);
//		stopThread(monitorThread);
//		while (monitorThread != null)
//			Thread.yield();
//		log.info("Stopped monitor: "+this.processName);
//		retries = 0;
//	}
//
//	protected void stopThread(Thread thread) {
//		if (thread != null && !thread.isInterrupted()) {
//			thread.interrupt();
//		}
//	}

}
