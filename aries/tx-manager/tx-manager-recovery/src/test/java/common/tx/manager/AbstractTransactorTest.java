package common.tx.manager;

import static org.mockito.Mockito.mock;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import org.aries.notification.NotificationDispatcher;
import org.aries.registry.ProcessLocator;

import common.jmx.MBeanUtil;


public abstract class AbstractTransactorTest {

	protected ProcessLocator mockProcessLocator;

	protected NotificationDispatcher mockNotificationDispatcher;

	
	public ProcessLocator getMockProcessLocator() {
		return mockProcessLocator;
	}
	
	public NotificationDispatcher getMockNotificationDispatcher() {
		return mockNotificationDispatcher;
	}
	
	public void setUp() throws Exception {
		mockProcessLocator = mock(ProcessLocator.class);
		mockNotificationDispatcher = mock(NotificationDispatcher.class);
		MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer();
		MBeanUtil.setMBeanServer(mbeanServer);
	}
	
	public void tearDown() throws Exception {
		MBeanUtil.unregisterMBeans();
		mockProcessLocator = null;
		mockNotificationDispatcher = null;
	}
}
