package org.aries.ui;

import javax.management.MXBean;


@MXBean
public interface JmxEventHandlerMBean {
	
	public static final String MBEAN_NAME = "bookshop2.buyer:name=JmxEventHandlerMBean";

	public void dispatch(String correlationId, JmxEvent jmxEvent);
	
}
