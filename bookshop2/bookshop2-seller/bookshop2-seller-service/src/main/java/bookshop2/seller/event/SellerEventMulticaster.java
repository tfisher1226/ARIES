package bookshop2.seller.event;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;

import org.aries.common.AbstractEvent;
import org.aries.event.EventListener;
import org.aries.event.multicaster.AbstractEventMulticaster;

//import org.aries.admin.Event;



/**
 * Handles the dispatching of events to local listeners i.e. listeners within the current JVM. 
 * @author tfisher
 */
@Startup
@Singleton
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class SellerEventMulticaster extends AbstractEventMulticaster {

	@Override
	@Asynchronous
	public <T extends AbstractEvent> void fireEvent(T event) {
		try {
			EventListener<T> processor = getListener(event);
			if (processor != null) {
				processor.process(event);
			}
		} catch (Throwable e) {
			//ignore
		}
	}
	
//	public <T extends AbstractEvent> void addShipmentScheduledEventListener(EventHandler<T> eventHandler) {
//		super.addEventListener(eventHandler.getEventId(), eventHandler);
//	}
//
//	public <T extends AbstractEvent> void addShipmentConfirmedEventListener(EventHandler<T> eventHandler) {
//		super.addEventListener(eventHandler.getEventId(), eventHandler);
//	}
//
//	public <T extends AbstractEvent> void removeScheduledEventListener(EventHandler<T> eventHandler) {
//		super.addEventListener(eventHandler.getEventId(), eventHandler);
//	}
//
//	public <T extends AbstractEvent> void removeConfirmedEventListener(EventHandler<T> eventHandler) {
//		super.addEventListener(eventHandler.getEventId(), eventHandler);
//	}

}
