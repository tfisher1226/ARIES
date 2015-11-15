package bookshop2.shipper;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.aries.process.AbstractProcess;

import bookshop2.Invoice;
import bookshop2.Order;
import bookshop2.Payment;
import bookshop2.Shipment;
import bookshop2.ShipmentCompleteMessage;
import bookshop2.ShipmentConfirmedEvent;
import bookshop2.ShipmentFailedMessage;
import bookshop2.ShipmentRequestMessage;
import bookshop2.ShipmentScheduledEvent;
import bookshop2.ShipmentScheduledMessage;
import bookshop2.shipper.event.ShipmentConfirmedExecutor;
import bookshop2.shipper.event.ShipmentScheduledExecutor;
import bookshop2.shipper.outgoing.shipmentComplete.ShipmentCompleteReply;
import bookshop2.shipper.outgoing.shipmentFailed.ShipmentFailedReply;
import bookshop2.shipper.outgoing.shipmentScheduled.ShipmentScheduledReply;

import common.jmx.MBeanUtil;


@Startup
@Singleton
@LocalBean
@AccessTimeout(value = 60000)
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class ShipperProcess extends AbstractProcess implements ShipperProcessMBean {
	
	public static long DEFAULT_TIMEOUT = 60000L;
	
	@Inject
	private ShipmentConfirmedExecutor shipmentConfirmedExecutor;
	
	@Inject
	private ShipmentScheduledExecutor shipmentScheduledExecutor;

	
	@Override
	public String getDomainId() {
		return "bookshop2.shipper";
	}

	@PostConstruct
	protected void postConstruct() {
		registerWithJMX();
	}
	
	@PreDestroy
	protected void preDestroy() {
		unregisterWithJMX();
	}

	protected void registerWithJMX() {
		MBeanUtil.registerMBean(this, MBEAN_NAME);
	}
	
	protected void unregisterWithJMX() {
		MBeanUtil.unregisterMBean(MBEAN_NAME);
	}
	
	protected void updateState() {
		//nothing for now
	}

	protected ShipperContext getContext(String correlationId) {
		return ShipperContext.getInstance(correlationId);
	}

	public void handle_ShipBooks_request(ShipmentRequestMessage message) {
		String correlationId = message.getCorrelationId();
		ShipperContext shipperContext = getContext(correlationId);
		String serviceId = bookshop2.shipper.incoming.shipBooks.ShipBooks.ID;
		shipperContext.initializeContext(serviceId, message);
		shipmentScheduledExecutor.register(correlationId);
		shipmentConfirmedExecutor.register(correlationId);
		fire_ShipBooks_request_handled(correlationId);
	}

	public void handle_ShipBooks_request_cancel(ShipmentRequestMessage message) {
		String correlationId = message.getCorrelationId();
		ShipperContext shipperContext = getContext(correlationId);
		String serviceId = bookshop2.shipper.incoming.shipBooks.ShipBooks.ID;
		shipperContext.initializeContext(serviceId, message);
		shipmentConfirmedExecutor.cancel(correlationId);
		shipmentScheduledExecutor.cancel(correlationId);
		handle_ShipBooks_request_undo(message);
		fire_ShipBooks_request_cancel_done(correlationId);
	}
	
	public void handle_ShipBooks_request_undo(ShipmentRequestMessage message) {
		String correlationId = message.getCorrelationId();
		fire_ShipBooks_request_undo_done(correlationId);
	}
	
	public void handle_ShipBooks_request_exception(String correlationId, Throwable cause) {
		//TODO
		fire_ShipBooks_error_sent(correlationId);
		fire_ShipBooks_incoming_request_aborted(correlationId, cause);
	}
	
	public void handle_ShipBooks_request_timeout(String correlationId, String reason) {
		//TODO
		fire_ShipBooks_error_sent(correlationId);
		fire_ShipBooks_incoming_request_aborted(correlationId, reason);
	}
	
	public void handle_ShipBooks_request_done(String correlationId) {
		shipmentConfirmedExecutor.close();
		shipmentScheduledExecutor.close();
		fire_ShipBooks_request_done(correlationId);
	}
	
	protected void reply_ShipmentScheduled(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		String serviceId = bookshop2.shipper.incoming.shipBooks.ShipBooks.ID;
		ShipmentScheduledMessage message = shipperContext.createShipmentScheduledMessage(serviceId);
		reply_ShipmentScheduled(message);
	}
	
	protected void reply_ShipmentScheduled(ShipmentScheduledMessage message) {
		ShipmentScheduledReply client = getClient(ShipmentScheduledReply.ID);
		client.shipmentScheduled(message);
		String correlationId = message.getCorrelationId();
		fire_ShipmentScheduled_response_sent(correlationId);
	}
	
	public void handle_ShipmentScheduled_event(ShipmentScheduledEvent event) {
		String correlationId = event.getCorrelationId();
		reply_ShipmentScheduled(correlationId);
		fire_ShipmentScheduled_process_complete(correlationId);
	}

	public void handle_ShipmentScheduled_event_cancel(String correlationId, String reason) {
		fire_ShipmentScheduled_process_aborted(correlationId, reason);
	}
	
	protected void reply_ShipmentFailed(String correlationId, String reason) {
		ShipperContext shipperContext = getContext(correlationId);
		String serviceId = bookshop2.shipper.incoming.shipBooks.ShipBooks.ID;
		ShipmentFailedMessage message = shipperContext.createShipmentFailedMessage(serviceId);
		message.setReason(reason);
		message.setReason(reason);
		reply_ShipmentFailed(message);
	}
	
	protected void reply_ShipmentFailed(ShipmentFailedMessage message) {
		ShipmentFailedReply client = getClient(ShipmentFailedReply.ID);
		client.shipmentFailed(message);
		String correlationId = message.getCorrelationId();
		fire_ShipmentFailed_response_sent(correlationId);
	}
	
	public void handle_ShipmentScheduled_event_timeout(String correlationId, String reason) {
		fire_ShipmentScheduled_process_aborted(correlationId, reason);
		reply_ShipmentFailed(correlationId, reason);
		fire_ShipBooks_request_handled(correlationId);
		handle_ShipBooks_request_done(correlationId);
	}
	
	protected void reply_ShipmentFailed(String correlationId, Throwable cause) {
		ShipperContext shipperContext = getContext(correlationId);
		String serviceId = bookshop2.shipper.incoming.shipBooks.ShipBooks.ID;
		ShipmentFailedMessage message = shipperContext.createShipmentFailedMessage(serviceId);
		message.setReason(cause.getMessage());
		reply_ShipmentFailed(message);
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void handle_ShipmentScheduled_event_exception(String correlationId, Throwable cause) {
		fire_ShipmentScheduled_process_aborted(correlationId, cause);
		reply_ShipmentFailed(correlationId, cause);
		fire_ShipBooks_request_handled(correlationId);
		handle_ShipBooks_request_done(correlationId);
	}

	protected void reply_ShipmentComplete(String correlationId, Invoice invoice) {
		ShipperContext shipperContext = getContext(correlationId);
		String serviceId = bookshop2.shipper.incoming.shipBooks.ShipBooks.ID;
		ShipmentCompleteMessage message = shipperContext.createShipmentCompleteMessage(serviceId);
		message.setInvoice(invoice);
		reply_ShipmentComplete(message);
	}
	
	protected void reply_ShipmentComplete(ShipmentCompleteMessage message) {
		ShipmentCompleteReply client = getClient(ShipmentCompleteReply.ID);
		client.shipmentComplete(message);
		String correlationId = message.getCorrelationId();
		fire_ShipmentComplete_response_sent(correlationId);
	}
	
	public void handle_ShipmentConfirmed_event(ShipmentConfirmedEvent event) {
		String correlationId = event.getCorrelationId();
		ShipperContext shipperContext = getContext(correlationId);
		Shipment shipment = shipperContext.getShipment();
		Order order = shipment.getOrder();
		Payment payment = shipperContext.getPayment();
		Invoice invoice = new Invoice();
		invoice.setOrder(order);
		invoice.setPayment(payment);
		Date dateTime = new Date();
		invoice.setDateTime(dateTime);
		reply_ShipmentComplete(correlationId, invoice);
		fire_ShipmentConfirmed_process_complete(correlationId);
		fire_ShipBooks_request_handled(correlationId);
		handle_ShipBooks_request_done(correlationId);
	}

	public void handle_ShipmentConfirmed_event_cancel(String correlationId, String reason) {
		fire_ShipmentConfirmed_process_aborted(correlationId, reason);
		fire_ShipBooks_request_handled(correlationId);
		handle_ShipBooks_request_done(correlationId);
	}
	
	public void handle_ShipmentConfirmed_event_timeout(String correlationId, String reason) {
		fire_ShipmentConfirmed_process_aborted(correlationId, reason);
		reply_ShipmentFailed(correlationId, reason);
		fire_ShipBooks_request_handled(correlationId);
		handle_ShipBooks_request_done(correlationId);
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void handle_ShipmentConfirmed_event_exception(String correlationId, Throwable cause) {
		fire_ShipmentConfirmed_process_aborted(correlationId, cause);
		reply_ShipmentFailed(correlationId, cause);
		fire_ShipBooks_request_handled(correlationId);
		handle_ShipBooks_request_done(correlationId);
	}
	
	protected void fire_ShipBooks_request_received(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipBooks_request_received();
	}

	protected void fire_ShipBooks_request_handled(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipBooks_request_handled();
	}
	
	protected void fire_ShipBooks_request_done(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipBooks_request_done();
	}
	
	protected void fire_ShipBooks_request_cancel_done(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipBooks_request_cancel_done();
	}
	
	protected void fire_ShipBooks_request_undo_done(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipBooks_request_undo_done();
	}
	
	protected void fire_ShipBooks_incoming_request_aborted(String correlationId, Throwable cause) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipBooks_incoming_request_aborted(cause);
	}
	
	protected void fire_ShipBooks_incoming_request_aborted(String correlationId, String reason) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipBooks_incoming_request_aborted(reason);
	}
	
	protected void fire_ShipBooks_error_sent(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipBooks_error_sent();
	}
	
	protected void fire_ShipmentFailed_response_sent(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentFailed_response_sent();
	}
	
	protected void fire_ShipmentFailed_outgoing_response_aborted(String correlationId, Throwable cause) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentFailed_outgoing_response_aborted(cause);
	}
	
	protected void fire_ShipmentFailed_outgoing_response_aborted(String correlationId, String reason) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentFailed_outgoing_response_aborted(reason);
	}
	
	protected void fire_ShipmentScheduled_response_sent(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentScheduled_response_sent();
	}
	
	protected void fire_ShipmentScheduled_outgoing_response_aborted(String correlationId, Throwable cause) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentScheduled_outgoing_response_aborted(cause);
	}
	
	protected void fire_ShipmentScheduled_outgoing_response_aborted(String correlationId, String reason) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentScheduled_outgoing_response_aborted(reason);
	}
	
	protected void fire_ShipmentComplete_response_sent(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentComplete_response_sent();
	}
	
	protected void fire_ShipmentComplete_outgoing_response_aborted(String correlationId, Throwable cause) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentComplete_outgoing_response_aborted(cause);
	}
	
	protected void fire_ShipmentComplete_outgoing_response_aborted(String correlationId, String reason) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentComplete_outgoing_response_aborted(reason);
	}
	
	protected void fire_ShipmentConfirmed_event_received(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentConfirmed_event_received();
	}
	
	protected void fire_ShipmentConfirmed_process_complete(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentConfirmed_process_complete();
	}
	
	protected void fire_ShipmentConfirmed_process_aborted(String correlationId, Throwable cause) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentConfirmed_process_aborted(cause);
	}
	
	protected void fire_ShipmentConfirmed_process_aborted(String correlationId, String reason) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentConfirmed_process_aborted(reason);
	}
	
	protected void fire_ShipmentScheduled_event_received(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentScheduled_event_received();
	}
	
	protected void fire_ShipmentScheduled_process_complete(String correlationId) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentScheduled_process_complete();
	}
	
	protected void fire_ShipmentScheduled_process_aborted(String correlationId, Throwable cause) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentScheduled_process_aborted(cause);
	}
	
	protected void fire_ShipmentScheduled_process_aborted(String correlationId, String reason) {
		ShipperContext shipperContext = getContext(correlationId);
		shipperContext.fire_ShipmentScheduled_process_aborted(reason);
	}

	
	protected <T> T getClientNEW(String clientId) {
		//"http://"+host+":"+port+"/bookshop2/shipper/shipmentComplete?wsdl"
		//getContext();
		return null;
	}



//	protected void scheduleShipmentScheduledTimeoutHandler(Timer timer) {
//	ShipmentScheduledTimeoutHandler timeoutHandler = shipperContext.createShipmentScheduledTimeoutHandler(timer);
//	timeoutHandler.schedule();
//}

//protected void closeShipmentScheduledTimeoutHandler() {
//
//}

//protected void registerShipmentScheduledEventHandler(Timer timer) {
//	//ShipmentScheduledEventHandler eventHandler = new ShipmentScheduledEventHandler(this, timer);
//	shipmentScheduledEventHandler.setTimer(timer);
//	shipmentScheduledEventHandler.register();
//}

//protected void closeShipmentScheduledEventHandler() {
//}


//	protected void setupShipmentConfirmedExecution() {
//		Timer timer = new Timer("ShipmentConfirmedTimer");
//		scheduleShipmentConfirmedTimeoutHandler(timer);
//		registerShipmentConfirmedEventHandler(timer);
//	}
//
//	protected void closeShipmentConfirmedExecution() {
////		shipmentConfirmedEventHandler.close();
//	}

//	protected void scheduleShipmentConfirmedTimeoutHandler(Timer timer) {
//		ShipmentConfirmedTimeoutHandler timeoutHandler = new ShipmentConfirmedTimeoutHandler(this, timer);
//		timeoutHandler.schedule();
//	}

//	protected void registerShipmentConfirmedEventHandler(Timer timer) {
//		//ShipmentConfirmedEventHandler eventHandler = new ShipmentConfirmedEventHandler(this, timer);
//		shipmentConfirmedExecuter.register();
//	}


	

//	protected void setupShipmentScheduledListenerORIG() {
//		final Timer timeoutManager = TransportTimer.getTimer();
//		TimerTask timeoutHandler = createShipmentScheduledTimeoutHandler(); 
//		timeoutManager.schedule(timeoutHandler, TransportTimer.getTransportPeriod());
//		
//		eventMulticaster.addEventListener(ShipmentScheduledEvent.class, new EventAdapter<ShipmentScheduledEvent>() {
//			public void process(ShipmentScheduledEvent event) {
//				timeoutManager.cancel();
//				timeoutManager.purge();
//				handleShipmentScheduledEvent();
//			}
//		});
//	}
	
//	protected void setupShipmentConfirmedListener() {
//		TimerTask timeoutHandler = createShipmentConfirmedTimeoutHandler(); 
//		timeoutManager.schedule(timeoutHandler, TransportTimer.getTransportPeriod());
//		eventMulticaster.addEventListener(ShipmentConfirmedEvent.class, new EventAdapter<ShipmentConfirmedEvent>() {
//			public void process(ShipmentConfirmedEvent event) {
//				timeoutManager.cancel();
//				timeoutManager.purge();
//				handleShipmentConfirmedEvent();
//			}
//		});
//	}
	
//	protected TimerTask createShipmentScheduledTimeoutHandler() {
//		return new TimerTask() {
//			public void run() {
//				handleShipmentScheduledTimeout();
//			}
//		};
//	}
	
//	protected void scheduleShipmentScheduledExecuter() {
//		ShipmentScheduledEventHandler eventHandler = new ShipmentScheduledEventHandler();
//		ShipmentScheduledTimeoutHandler timeoutHandler = new ShipmentScheduledTimeoutHandler();
//		TimerTask timeoutHandler = createShipmentScheduledTimeoutHandler(); 
//		timeoutManager.schedule(timeoutHandler, TransportTimer.getTransportPeriod());
//		eventMulticaster.addEventListener(ShipmentScheduledEvent.class, eventHandler);
//	}
	
//	protected Runnable createShipmentScheduledHandler() {
//		return new Runnable() {
//			public void run() {
//				handleShipmentScheduled();
//			}
//		};
//	}
//	
//	protected void addShipmentConfirmedListener() {
//		Executor executor = new Executor();
//		executor.execute(new Runnable() {
//			public void run() {
//				handleShipmentConfirmed();
//			}
//		});
//	}
//
//	protected Runnable createShipmentConfirmedListener() {
//		return new Runnable() {
//			public void run() {
//				handleShipmentConfirmed();
//			}
//		};
//	}
//	
//	protected void handleShipmentConfirmed() {
//		try {
//			replyShipmentComplete();
//
//		} catch (Throwable e) {
//			e = ExceptionUtil.getRootCause(e);
//			fireShipmentCompleteAborted(e);
//			replyShipmentFailed(e);
//		}
//
//		fireShipBooksDone();
//	}


//	protected void executeShipmentScheduledListener() {
//		Runnable runnable = createShipmentScheduledListener();
//		Executor executor = new Executor();
//		executor.setTimeoutValue(10);
//		executor.setTimeoutUnit(TimeUnit.SECONDS);
//		executor.execute(runnable);
//		executor.waitFor();
//	}
//	
//	protected Runnable createShipmentScheduledListener() {
//		return new Runnable() {
//			public void run() {
//				addShipmentScheduledListener();
//			}
//		};
//	}
	
}
