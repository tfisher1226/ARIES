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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.aries.Assert;
import org.aries.tx.ConversationContext;


@Singleton
@LocalBean
@AccessTimeout(value = 60000)
@ConcurrencyManagement(BEAN)
@TransactionAttribute(REQUIRED)
@TransactionManagement(CONTAINER)
public class EventLoggerContext extends ConversationContext {
	
	public Event event;
	
	private Date date;
	
	private String message;

	private String replyTo;

	private Message jmsMessage;

	private Destination jmsReplyTo;
	
	private redhat.jee_migration_example.ObjectFactory jeeMigrationExampleObjectFactory;
	
	
	public Event getEvent() {
		return event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getReplyTo() {
		return replyTo;
	}
	
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public Message getJMSMessage() {
		return jmsMessage;
	}

	public Destination getJMSReplyTo() {
		return jmsReplyTo;
	}

	@Override
	public String getDomainId() {
		return "redhat";
	}
	
//	@Override
//	public String getConversationId() {
//		return "redhat.jee_migration_example.EventLoggerProcess";
//	}
	
	@PostConstruct
	protected void initialize() {
		registerWithJMX();
		jeeMigrationExampleObjectFactory = new redhat.jee_migration_example.ObjectFactory();
	}
	
	@PreDestroy
	protected void close() {
		unregisterWithJMX();
	}
	
	public Event createEvent() {
		Event event = jeeMigrationExampleObjectFactory.createEvent();
		return event;
	}
	
	public void initializeContext() {
		setDate(new Date());
	}
	
	public void initializeContext(Long id) {
		validate(id);
	}
	
	public void initializeContext(Event event) {
		setDate(event.getDate());
		setMessage(event.getMessage());
	}

	public void initializeContext(Message message) throws JMSException {
		this.jmsMessage = message;
		this.jmsReplyTo = message.getJMSReplyTo();
	}

	public void validate(Long id) {
		Assert.notNull(id, "Id null");
	}

	public void validate(Event event) {
		validate("event", event);
	}

	public void fire_PopulateCatalog_request_received() {
		fireServiceCompleted("Jee-migration-example_PopulateCatalog_Request_Received");
	}
	
	public void fire_PopulateCatalog_request_handled() {
		fireServiceCompleted("Jee-migration-example_PopulateCatalog_Request_Handled");
	}
	
	public void fire_PopulateCatalog_request_done() {
		fireServiceCompleted("Jee-migration-example_PopulateCatalog_Request_Done");
	}
	
	public void fire_PopulateCatalog_request_cancel_done() {
		fireServiceCompleted("Jee-migration-example_PopulateCatalog_Request_Cancel_Done");
	}
	
	public void fire_PopulateCatalog_request_undo_done() {
		fireServiceCompleted("Jee-migration-example_PopulateCatalog_Request_Undo_Done");
	}
	
	public void fire_PopulateCatalog_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("Jee-migration-example_PopulateCatalog_Incoming_Request_Aborted", cause);
	}
	
	public void fire_PopulateCatalog_incoming_request_aborted(String reason) {
		fireServiceAborted("Jee-migration-example_PopulateCatalog_Incoming_Request_Aborted", reason);
	}
	
	public void fire_PopulateCatalog_error_sent() {
		fireServiceCompleted("Jee-migration-example_PopulateCatalog_Error_Sent");
	}
	
	public void fire_LookupItem_request_received() {
		fireServiceCompleted("Jee-migration-example_LookupItem_Request_Received");
	}
	
	public void fire_LookupItem_request_handled() {
		fireServiceCompleted("Jee-migration-example_LookupItem_Request_Handled");
	}
	
	public void fire_LookupItem_request_done() {
		fireServiceCompleted("Jee-migration-example_LookupItem_Request_Done");
	}
	
	public void fire_LookupItem_request_cancel_done() {
		fireServiceCompleted("Jee-migration-example_LookupItem_Request_Cancel_Done");
	}
	
	public void fire_LookupItem_request_undo_done() {
		fireServiceCompleted("Jee-migration-example_LookupItem_Request_Undo_Done");
	}
	
	public void fire_LookupItem_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("Jee-migration-example_LookupItem_Incoming_Request_Aborted", cause);
	}
	
	public void fire_LookupItem_incoming_request_aborted(String reason) {
		fireServiceAborted("Jee-migration-example_LookupItem_Incoming_Request_Aborted", reason);
	}
	
	public void fire_LookupItem_error_sent() {
		fireServiceCompleted("Jee-migration-example_LookupItem_Error_Sent");
	}
	
	public void fire_Item_response_sent() {
		fireServiceCompleted("Jee-migration-example_Item_Response_Sent");
	}
	
	public void fire_Item_outgoing_response_aborted(Throwable cause) {
		fireServiceAborted("Jee-migration-example_Item_Outgoing_Response_Aborted", cause);
	}
	
	public void fire_Item_outgoing_response_aborted(String reason) {
		fireServiceAborted("Jee-migration-example_Item_Outgoing_Response_Aborted", reason);
	}
	
	public void fire_LogEvent_request_sent() {
		fireServiceCompleted("Jee-migration-example_LogEvent_Request_Sent");
	}
	
	public void fire_LogEvent_outgoing_request_aborted(Throwable cause) {
		fireServiceAborted("Jee-migration-example_LogEvent_Outgoing_Request_Aborted", cause);
	}
	
	public void fire_LogEvent_outgoing_request_aborted(String reason) {
		fireServiceAborted("Jee-migration-example_LogEvent_Outgoing_Request_Aborted", reason);
	}
	
	public void fire_LogEvent_error_received() {
		fireServiceCompleted("Jee-migration-example_LogEvent_Error_Received");
	}
	
	public void fire_LogEvent_request_received() {
		fireServiceCompleted("Jee-migration-example_LogEvent_Request_Received");
	}
	
	public void fire_LogEvent_request_handled() {
		fireServiceCompleted("Jee-migration-example_LogEvent_Request_Handled");
	}
	
	public void fire_LogEvent_request_done() {
		fireServiceCompleted("Jee-migration-example_LogEvent_Request_Done");
	}
	
	public void fire_LogEvent_request_cancel_done() {
		fireServiceCompleted("Jee-migration-example_LogEvent_Request_Cancel_Done");
	}
	
	public void fire_LogEvent_request_undo_done() {
		fireServiceCompleted("Jee-migration-example_LogEvent_Request_Undo_Done");
	}
	
	public void fire_LogEvent_incoming_request_aborted(Throwable cause) {
		fireServiceAborted("Jee-migration-example_LogEvent_Incoming_Request_Aborted", cause);
	}
	
	public void fire_LogEvent_incoming_request_aborted(String reason) {
		fireServiceAborted("Jee-migration-example_LogEvent_Incoming_Request_Aborted", reason);
	}
	
	public void fire_LogEvent_error_sent() {
		fireServiceCompleted("Jee-migration-example_LogEvent_Error_Sent");
	}

}
