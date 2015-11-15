package bookshop2.shipper.event;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.aries.event.executor.AbstractEventExecutor;
import org.aries.jndi.JndiName;
import org.aries.runtime.BeanLocator;
import org.aries.util.ExceptionUtil;

import bookshop2.ShipmentScheduledEvent;
import bookshop2.shipper.ShipperProcess;


@Stateful
@LocalBean
public class ShipmentScheduledExecutor extends AbstractEventExecutor<ShipmentScheduledEvent> {

	@Inject
	private ShipperEventMulticaster eventMulticaster;

	private String eventId;

	private long timeout;
	
	
	@PostConstruct
	protected void postConstruct() {
		initialize();
	}
	
	@Override
	public String getEventId() {
		return ShipmentScheduledEvent.class.getName();
	}

	protected ShipperProcess getProcess() {
		String jndiName = getProcessJndiName();
		return BeanLocator.lookup(ShipperProcess.class, jndiName);
	}
	
	protected String getProcessJndiName() {
		JndiName jndiName = new JndiName();
		jndiName.setApplicationName("bookshop2-shipper");
		jndiName.setModuleName("bookshop2-shipper-service-0.0.1-SNAPSHOT");
		jndiName.setBeanName("ShipperProcess");
		return jndiName.toString();
	}

	@Override
	public void initialize() {
		shutdownTimer();
		eventId = getEventId();
		timeout = DEFAULT_TIMEOUT;
	}

	@Override
	public void register(String correlationId) {
		shutdownTimer();
		createTimer(createTimeoutHandler(correlationId), timeout);
		eventMulticaster.addEventListener(eventId, this);
		log.info("registered");
	}

	@Override
	public void cancel(String correlationId) {
		close();
		eventMulticaster.removeEventListener(eventId, this);
		String reason = "ShipmentScheduled execution cancelled";
		getProcess().handle_ShipmentScheduled_event_cancel(correlationId, reason);
		log.info("cancelled");
	}

	@Override
	public void reset() {
		close();
		timeout = DEFAULT_TIMEOUT;
	}
	
	@Override
	@Asynchronous
	@AccessTimeout(value = 60000)
	@TransactionAttribute(REQUIRED)
	public void process(@Observes ShipmentScheduledEvent event) {
		log.info("invoked");
		shutdownTimer();
		
		if (isClosed())
			return;

		try {
			getProcess().handle_ShipmentScheduled_event(event);
			log.info("processed");

		} catch (Throwable e) {
			log.info("failed");
			e = ExceptionUtil.getRootCause(e);
			String correlationId = event.getCorrelationId();
			getProcess().handle_ShipmentScheduled_event_exception(correlationId, e);

		} finally {
			eventMulticaster.removeEventListener(eventId, this);
			reset();
		}
	}

	protected TimerTask createTimeoutHandler(final String correlationId) {
		return new TimerTask() {
			public void run() {
				reset();
				log.info("timed-out");
				String reason = "ShipmentScheduled timed-out";
				getProcess().handle_ShipmentScheduled_event_timeout(correlationId, reason);
			}
		};
	}

}
