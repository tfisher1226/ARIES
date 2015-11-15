package org.aries.tx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.aries.bean.ProxyLocator;
import org.aries.process.Process;
import org.aries.registry.ProcessLocator;
import org.aries.registry.ProcessRegistry;
import org.aries.runtime.BeanContext;
import org.aries.task.TaskExecutorFactory;
import org.aries.task.TaskExecutorService;
import org.aries.tx.util.TaskExecutorImpl;
import org.aries.util.properties.PropertyManager;
import org.aries.util.test.AbstractIT;
import org.junit.After;

import common.tx.management.TXManagerClientSideLauncher;
import common.tx.management.TXManagerServiceSideLauncher;


public class AbstractTransactionalClientIT extends AbstractIT {

	protected ProxyLocator proxyLocator = new ProxyLocator();

	protected ProcessLocator processLocator = new ProcessLocator();

	protected ProcessRegistry processRegistry = new ProcessRegistry();

	protected TaskExecutorFactory taskExecutorFactory = new TaskExecutorFactory();
	
	protected TaskExecutorService taskExecutorService = new TaskExecutorService(1);

	protected TXManagerServiceSideLauncher txManagerServiceSideLauncher;

	protected TXManagerClientSideLauncher txManagerClientSideLauncher;

	protected String correlationId = "dummyCorrelationId";

	//protected String hostName = "127.0.0.1";
	protected String hostName = "localhost";

	protected int httpPort = 8280;

	protected int jmxPort = 1234;

	
	public void setUp() throws Exception {
		super.setUp();
		//System.setProperty("com.sun.management.jmxremote.port", Integer.toString(jmxPort));
		PropertyManager.getInstance().initialize();
		BeanContext.set("org.aries.proxyLocator", proxyLocator);
		BeanContext.set("org.aries.processLocator", processLocator);
		BeanContext.set("org.aries.processRegistry", processRegistry);
		BeanContext.set("org.aries.executorFactory", taskExecutorFactory);
		BeanContext.set("org.aries.executorService", taskExecutorService);
		taskExecutorFactory.setTaskExecutorClassName(TaskExecutorImpl.class.getName());
		startTransactionManagementService();
		startTransactionManagementProxy();
		startMBeanServer(jmxPort);
	}

	protected ExecutorService createFixedThreadPool(int nThreads) {
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        ExecutorService threadPool = new TaskExecutorService(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, workQueue);
		return threadPool;
    }
    
	protected void waitForTermination() throws Throwable {
		taskExecutorService.waitForTermination();
	}

	protected void waitForTermination(long timeout) throws Throwable {
		taskExecutorService.waitForTermination(timeout);
	}
	
	@After
	public void tearDown() throws Exception {
		stopTransactionManagementService();
		stopTransactionManagementProxy();
		stopMBeanServer();
		super.tearDown();
	}

	protected void addProxyInstance(String proxyId, Object proxyInstance) {
		proxyLocator.add(proxyId, proxyInstance, "HTTP");
	}

	protected <T extends Process> T getProcessInstance(Class<T> classObject) {
		T processInstance = (T) processLocator.lookup(classObject, correlationId);
		return processInstance;
	}

//	protected void addProcessInstance(Process processInstance) {
//		processInstance.setCorrelationId(correlationId);
//		processLocator.addProcessInstance(processInstance);
//	}

	protected void startTransactionManagementService() throws Exception {
		txManagerServiceSideLauncher = new TXManagerServiceSideLauncher();
		txManagerServiceSideLauncher.startup(hostName, httpPort);
	}

	protected void startTransactionManagementProxy() throws Exception {
		txManagerClientSideLauncher = new TXManagerClientSideLauncher();
		txManagerClientSideLauncher.startup(hostName, httpPort);
	}

	protected void stopTransactionManagementService() throws Exception {
		txManagerServiceSideLauncher.shutdown();
	}

	protected void stopTransactionManagementProxy() throws Exception {
		txManagerClientSideLauncher.shutdown();
	}

	protected void printStatus(String name) {
		int statusValue = UserTransactionFactory.getUserTransaction().getStatus();
		//String statusLabel = ActionStatus.stringForm(statusValue);
		//System.out.println("TransactionStatus["+name+"]: "+statusLabel);
		System.out.println("TransactionStatus["+name+"]: "+statusValue);
	}
	
}
