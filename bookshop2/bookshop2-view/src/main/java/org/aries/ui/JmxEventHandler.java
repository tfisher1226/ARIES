package org.aries.ui;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import org.aries.notification.ServiceEventDispatcher;
import org.aries.notification.ServiceEventNotification;
import org.aries.runtime.BeanContext;
import org.aries.util.concurrent.FutureResult;

import common.jmx.MBeanUtil;
import common.jmx.client.JmxManager;
import common.jmx.client.JmxProxy;


//@SessionScoped
//@Named("jmxEventHandler")
public class JmxEventHandler implements JmxEventHandlerMBean, Serializable {

	private JmxProxy jmxProxy;
	
	private JmxManager jmxManager;
	
	private NotificationListener notificationListener;
	
	private Map<Object, FutureResult<Object>> notificationListenerMap;
	
	private Map<String, Map<String, Notification>> notificationStore;

	
	public JmxEventHandler() {
		//nothing for now
	}
	
	protected void register() {
		try {
			registerMBean();
			initializeJmxManager();
			registerNotificationListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void unregister() {
		unregisterMBean();
	}
	
	protected void registerMBean() {
		MBeanUtil.registerMBean(this, MBEAN_NAME);
	}
	
	protected void unregisterMBean() {
		MBeanUtil.unregisterMBean(MBEAN_NAME);
	}
	
	public void initializeJmxManager() throws Exception {
		JmxConfiguration configuration = new JmxConfiguration();
		configuration.setJndiPropertyFileName("provider/hornetq/node1-jndi-local.properties");
		configuration.setJmsPropertyFileName("provider/hornetq/node1-jms-remote.properties");
		//configuration.setJndiPropertyFileName("provider/hornetq/node1-jndi-remote.properties");
		//configuration.setJmsPropertyFileName("provider/hornetq/node1-jms-remote.properties");
		configuration.initialize();

		jmxManager = new JmxManager();
		jmxManager.setUsername(configuration.getUsername());
		jmxManager.setPassword(configuration.getPassword());
		jmxManager.setBindAddress(configuration.getBindAddress());
		jmxManager.setManagementPort(configuration.getManagementPort());
		jmxManager.setJndiProperties(configuration.getInitialContextProperties());
		jmxManager.setLocal(true);
		jmxManager.initialize();
		
		jmxProxy = new JmxProxy();
		jmxProxy.setJmxManager(jmxManager);
	}
	
	protected void registerNotificationListener() throws Exception {
		if (notificationListener != null)
			throw new Exception("NotificationListener already initialized");
		ObjectName objectName = new ObjectName(ServiceEventDispatcher.MBEAN_NAME);
		notificationListener = createNotificationListener();
		jmxManager.addNotificationListener(objectName, notificationListener);
		System.out.println("JMX Listener["+this.hashCode()+"]: registered");
	}
	
	protected NotificationListener createNotificationListener() {
		return new NotificationListener() {
			public void handleNotification(Notification notification, Object handback) {
				//Object userData = notification.getUserData();
				if (notification instanceof ServiceEventNotification) {
					ServiceEventNotification serviceEventNotification = (ServiceEventNotification) notification;
					String notificationId = serviceEventNotification.getEventId();
					String correlationId = serviceEventNotification.getCorrelationId();
					//System.out.println("JMX Listener["+this.hashCode()+"] type["+notificationId+"]: received");
					//recordNotification(correlationId, notificationId, notification);
					//FutureResult<Object> futureResult = notificationListenerMap.get(notificationId);
					//if (futureResult != null)
					//	futureResult.set(notification);
					System.out.println("JMX Listener["+this.hashCode()+"] type["+notificationId+"]: dispatched");
					
					JmxEvent jmxEvent = new JmxEvent();
					jmxEvent.setSequenceNumber(notification.getSequenceNumber());
					jmxEvent.setTimeStamp(new Date(notification.getTimeStamp()));
					jmxEvent.setEventId(notificationId);
					jmxEvent.setCorrelationId(correlationId);
					jmxEvent.setMessage((String) notification.getUserData());
					jmxEvent.setData((Serializable) notification.getUserData());
					
//					ObjectName objectName = MBeanUtil.makeObjectName(JmxEventHandlerMBean.MBEAN_NAME);
//					JmxEventHandlerMBean fooProxy = JMX.newMBeanProxy(jmxManager.getMBeanServerConnection(), objectName, JmxEventHandlerMBean.class);
//					fooProxy.dispatch(jmxEvent);

					String[] signature = new String[] {"java.lang.String", "org.aries.ui.JmxEvent"};
					Object[] parameters = new Object[] {correlationId, jmxEvent};
					jmxProxy.call(JmxEventHandlerMBean.MBEAN_NAME, "dispatch", signature, parameters);
					
//					dispatch(correlationId, jmxEvent);
				}
			}
		};
	}
	
	protected boolean isNotificationExist(String correlationId, String serviceId) {
		if (notificationStore == null)
			notificationStore = new HashMap<String, Map<String, Notification>>();
		Map<String, Notification> map = notificationStore.get(correlationId);
		if (map != null)
			return map.containsKey(serviceId);
		return false;
	}
	
	protected void recordNotification(String correlationId, String serviceId, Notification notification) {
		if (notificationStore == null)
			notificationStore = new HashMap<String, Map<String, Notification>>();
		Map<String, Notification> map = notificationStore.get(correlationId);
		if (map == null) {
			map = new HashMap<String, Notification>();
			notificationStore.put(correlationId, map);
		}
		map.put(serviceId, notification);
	}
	
	
	public void dispatch(String correlationId, JmxEvent jmxEvent) {
		JmxEventListManager jmxEventListManager = BeanContext.get("jmxEventListManager");
		jmxEventListManager.addJmxEvent(jmxEvent);
	}
	
}
