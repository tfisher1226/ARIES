package redhat.jee_migration_example;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
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
import org.aries.process.ActionState;
import org.aries.process.TimeoutHandler;
import org.aries.util.ExceptionUtil;

import redhat.jee_migration_example.client.logEvent.LogEvent;
import redhat.jee_migration_example.data.itemInventory.ItemInventoryManager;
import redhat.jee_migration_example.outgoing.item.ItemReply;
import common.jmx.MBeanUtil;


@Startup
@Singleton
@LocalBean
@AccessTimeout(value = 60000)
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class EventLoggerProcess extends AbstractProcess implements EventLoggerProcessMBean {
	
	public static long DEFAULT_TIMEOUT = 60000L;
	
	@Inject
	private EventLoggerContext eventLoggerContext;
	
	@Inject
	protected ItemInventoryManager itemInventoryManager;
	
	private long logEventTimeout = DEFAULT_TIMEOUT;
	
	
	@Override
	public String getDomainId() {
		return "redhat.jee_migration_example";
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
	
	public void handle_PopulateCatalog_request() {
		eventLoggerContext.initializeContext();
		fire_PopulateCatalog_request_done();
	}
	
	public void handle_PopulateCatalog_request_exception(Throwable cause) {
		//TODO
		fire_PopulateCatalog_error_sent();
		fire_PopulateCatalog_incoming_request_aborted(cause);
	}
	
	public void handle_PopulateCatalog_request_done() {
		fire_PopulateCatalog_request_done();
	}
	
	public Item handle_LookupItem_request(Long id) {
		eventLoggerContext.initializeContext(id);
		Item item = itemInventoryManager.getFromItemStore(id);
		Date date = new Date();
		String text = "Returning Item: "+item;
		Event event = new Event();
		event.setDate(date);
		event.setMessage(text);
		send_JeeMigrationExample_LogEvent_request(event);
		fire_LookupItem_request_handled();
		handle_LookupItem_request_done();
		return item;
	}
	
	public void handle_LookupItem_request_exception(Throwable cause) {
		//TODO
		fire_LookupItem_error_sent();
		fire_LookupItem_incoming_request_aborted(cause);
	}
	
	public void handle_LookupItem_request_timeout(String reason) {
		//TODO
		fire_LookupItem_error_sent();
		fire_LookupItem_incoming_request_aborted(reason);
	}
	
	public void handle_LookupItem_request_done() {
		fire_LookupItem_request_done();
	}
	
	protected void handle_JeeMigrationExample_LogEvent_request_exception(Throwable cause) {
		fire_LogEvent_outgoing_request_aborted(cause);
	}
	
	protected void handle_JeeMigrationExample_LogEvent_request_timeout(String reason) {
		fire_LogEvent_outgoing_request_aborted(reason);
	}
	
	public void handle_LogEvent_request(Event event) {
		eventLoggerContext.initializeContext(event);
		eventLoggerContext.setEvent(event);
	}
	
	public void handle_LogEvent_request_exception(Throwable cause) {
		//TODO
		fire_LogEvent_error_sent();
		fire_LogEvent_incoming_request_aborted(cause);
	}
	
	public void handle_LogEvent_request_timeout(String reason) {
		//TODO
		fire_LogEvent_error_sent();
		fire_LogEvent_incoming_request_aborted(reason);
	}
	
	public void handle_LogEvent_request_done() {
		fire_LogEvent_request_done();
	}
	
	protected Event createEvent() {
		Event message = eventLoggerContext.createEvent();
		return message;
	}
	
	protected void send_JeeMigrationExample_LogEvent_request() {
		Event event = eventLoggerContext.getEvent();
		send_JeeMigrationExample_LogEvent_request(event);
	}
	
	protected void send_JeeMigrationExample_LogEvent_request(Event event) {
		Runnable timeoutHandler = create_JeeMigrationExample_LogEvent_request_timeoutHandler();
		try {
			send_JeeMigrationExample_LogEvent_request(event, timeoutHandler);
		} catch (Throwable e) {
			e = ExceptionUtil.getRootCause(e);
			handle_JeeMigrationExample_LogEvent_request_exception(e);
		}
	}
	
	protected void send_JeeMigrationExample_LogEvent_request(Event message, Runnable timeoutHandler) {
		TimeoutHandler timeoutHelper = startTimeoutHandler(timeoutHandler, logEventTimeout);
		try {
			LogEvent client = getClient(LogEvent.ID);
			client.logEvent(message);
			logState_JeeMigrationExample_LogEvent(message);
			fire_LogEvent_request_sent();
		} finally {
			timeoutHelper.reset();
		}
	}
	
	protected Runnable create_JeeMigrationExample_LogEvent_request_timeoutHandler() {
		return new Runnable() {
			public void run() {
				logEventTimeout = DEFAULT_TIMEOUT;
				String reason = "LogEvent timed-out";
				handle_JeeMigrationExample_LogEvent_request_timeout(reason);
			}
		};
	}
	
	protected void reply_item(Item item) {
		ItemReply replyTo = getReplyTo(ItemReply.ID);
		replyTo.replyItem(item);
		fire_Item_response_sent();
	}
	
	protected void logState_JeeMigrationExample_LogEvent(Event event) {
		ActionState action = new ActionState();
		action.setActionId("logEvent");
		eventLoggerContext.logAction(action);
	}
	
	protected void fire_PopulateCatalog_request_received() {
		eventLoggerContext.fire_PopulateCatalog_request_received();
	}
	
	protected void fire_PopulateCatalog_request_handled() {
		eventLoggerContext.fire_PopulateCatalog_request_handled();
	}
	
	protected void fire_PopulateCatalog_request_done() {
		eventLoggerContext.fire_PopulateCatalog_request_done();
	}
	
	protected void fire_PopulateCatalog_request_cancel_done() {
		eventLoggerContext.fire_PopulateCatalog_request_cancel_done();
	}
	
	protected void fire_PopulateCatalog_request_undo_done() {
		eventLoggerContext.fire_PopulateCatalog_request_undo_done();
	}
	
	protected void fire_PopulateCatalog_incoming_request_aborted(Throwable cause) {
		eventLoggerContext.fire_PopulateCatalog_incoming_request_aborted(cause);
	}
	
	protected void fire_PopulateCatalog_incoming_request_aborted(String reason) {
		eventLoggerContext.fire_PopulateCatalog_incoming_request_aborted(reason);
	}
	
	protected void fire_PopulateCatalog_error_sent() {
		eventLoggerContext.fire_PopulateCatalog_error_sent();
	}
	
	protected void fire_LookupItem_request_received() {
		eventLoggerContext.fire_LookupItem_request_received();
	}
	
	protected void fire_LookupItem_request_handled() {
		eventLoggerContext.fire_LookupItem_request_handled();
	}
	
	protected void fire_LookupItem_request_done() {
		eventLoggerContext.fire_LookupItem_request_done();
	}
	
	protected void fire_LookupItem_request_cancel_done() {
		eventLoggerContext.fire_LookupItem_request_cancel_done();
	}
	
	protected void fire_LookupItem_request_undo_done() {
		eventLoggerContext.fire_LookupItem_request_undo_done();
	}
	
	protected void fire_LookupItem_incoming_request_aborted(Throwable cause) {
		eventLoggerContext.fire_LookupItem_incoming_request_aborted(cause);
	}
	
	protected void fire_LookupItem_incoming_request_aborted(String reason) {
		eventLoggerContext.fire_LookupItem_incoming_request_aborted(reason);
	}
	
	protected void fire_LookupItem_error_sent() {
		eventLoggerContext.fire_LookupItem_error_sent();
	}
	
	protected void fire_Item_response_sent() {
		eventLoggerContext.fire_Item_response_sent();
	}
	
	protected void fire_Item_outgoing_response_aborted(Throwable cause) {
		eventLoggerContext.fire_Item_outgoing_response_aborted(cause);
	}
	
	protected void fire_Item_outgoing_response_aborted(String reason) {
		eventLoggerContext.fire_Item_outgoing_response_aborted(reason);
	}
	
	protected void fire_LogEvent_request_sent() {
		eventLoggerContext.fire_LogEvent_request_sent();
	}
	
	protected void fire_LogEvent_outgoing_request_aborted(Throwable cause) {
		eventLoggerContext.fire_LogEvent_outgoing_request_aborted(cause);
	}
	
	protected void fire_LogEvent_outgoing_request_aborted(String reason) {
		eventLoggerContext.fire_LogEvent_outgoing_request_aborted(reason);
	}
	
	protected void fire_LogEvent_error_received() {
		eventLoggerContext.fire_LogEvent_error_received();
	}
	
	protected void fire_LogEvent_request_received() {
		eventLoggerContext.fire_LogEvent_request_received();
	}
	
	protected void fire_LogEvent_request_handled() {
		eventLoggerContext.fire_LogEvent_request_handled();
	}
	
	protected void fire_LogEvent_request_done() {
		eventLoggerContext.fire_LogEvent_request_done();
	}
	
	protected void fire_LogEvent_request_cancel_done() {
		eventLoggerContext.fire_LogEvent_request_cancel_done();
	}
	
	protected void fire_LogEvent_request_undo_done() {
		eventLoggerContext.fire_LogEvent_request_undo_done();
	}
	
	protected void fire_LogEvent_incoming_request_aborted(Throwable cause) {
		eventLoggerContext.fire_LogEvent_incoming_request_aborted(cause);
	}
	
	protected void fire_LogEvent_incoming_request_aborted(String reason) {
		eventLoggerContext.fire_LogEvent_incoming_request_aborted(reason);
	}
	
	protected void fire_LogEvent_error_sent() {
		eventLoggerContext.fire_LogEvent_error_sent();
	}
	
}
