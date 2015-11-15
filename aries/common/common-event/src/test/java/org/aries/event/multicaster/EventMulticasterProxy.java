package org.aries.event.multicaster;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.common.AbstractEvent;

import common.jmx.client.JmxProxy;


public class EventMulticasterProxy extends JmxProxy {

	private static final Log log = LogFactory.getLog(EventMulticasterProxy.class);
	
	private String mbeanName = null; //EventMulticastManagerMBean.MBEAN_NAME;

	
	public void setMBeanName(String mbeanName) {
		this.mbeanName = mbeanName;
	}
	
	public <T extends AbstractEvent> void dispatchEvent(T event) {
		call(mbeanName, "dispatchEvent", 
				new String[] { "org.aries.common.AbstractEvent" }, 
				new Object[] { event });
	}

	public <T extends AbstractEvent> void dispatchEvents(List<T> eventList) {
		call(mbeanName, "dispatchEvents", 
				new String[] { "java.util.List" }, 
				new Object[] { eventList });
	}

//	public <T extends AbstractEvent> void dispatchEvent(String jndiName, T event) {
//		call(mbeanName, "dispatchEvent", 
//				new String[] { "java.lang.String", "org.aries.common.AbstractEvent" }, 
//				new Object[] { jndiName, event });
//	}
	
//	public <T extends AbstractEvent> void dispatchEvents(String jndiName, List<T> eventList) {
//		call(mbeanName, "dispatchEvents", new String[] { "java.lang.String", "java.util.List" }, new Object[] { jndiName, eventList });
//	}

}
