package bookshop2.shipper.event;

import java.util.List;

import javax.management.MXBean;

import org.aries.common.AbstractEvent;


@MXBean
public interface ShipperEventReceiverMBean {
	
	public static final String MBEAN_NAME = "bookshop2.shipper:name=ShipperEventReceiverMBean";
	
	public <T extends AbstractEvent> void dispatchEvent(T event);
	
	public <T extends AbstractEvent> void dispatchEvents(List<T> eventList);
	
}
