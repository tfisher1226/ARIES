package bookshop2.shipper.event;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.aries.common.AbstractEvent;

import common.jmx.MBeanUtil;


@Startup
@Singleton
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class ShipperEventReceiver implements ShipperEventReceiverMBean, Serializable {

	@Inject
	private ShipperEventMulticaster eventMulticaster;
	
	
	@PostConstruct
	protected void postConstruct() {
		MBeanUtil.registerMBean(this, MBEAN_NAME);
	}
	
	@PreDestroy
	protected void preDestroy() {
		MBeanUtil.unregisterMBean(MBEAN_NAME);
	}
	
	@Override
	public <T extends AbstractEvent> void dispatchEvent(T event) {
		//ShipperEventMulticaster multicaster = BeanLocator.lookup(ShipperEventMulticaster.class, name);
		eventMulticaster.fireEvent(event);
	}

	@Override
	public <T extends AbstractEvent> void dispatchEvents(List<T> eventList) {
		//ShipperEventMulticaster multicaster = BeanLocator.lookup(ShipperEventMulticaster.class, name);
		eventMulticaster.fireEvents(eventList);
	}

}
