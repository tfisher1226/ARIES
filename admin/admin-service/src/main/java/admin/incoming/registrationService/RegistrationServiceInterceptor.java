package admin.incoming.registrationService;

import static javax.ejb.ConcurrencyManagementType.BEAN;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.message.Message;
import org.aries.message.MessageInterceptor;

import admin.Registration;


@Stateful
@LocalBean
@ConcurrencyManagement(BEAN)
@TransactionManagement(CONTAINER)
public class RegistrationServiceInterceptor extends MessageInterceptor<RegistrationService> {
	
	private static final Log log = LogFactory.getLog(RegistrationServiceInterceptor.class);
	
	@Inject
	protected RegistrationServiceHandler registrationServiceHandler;
	

	@TransactionAttribute(REQUIRED)
	public Message getRegistrationList(Message message) {
		try {
			List<Registration> registrationList = registrationServiceHandler.getRegistrationList();
			Assert.notNull(registrationList, "RegistrationList must exist");
			message.addPart("registrationList", registrationList);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
	@TransactionAttribute(REQUIRED)
	public Message getRegistrationById(Message message) {
		try {
			Long id = message.getPart("id");
			Registration registration = registrationServiceHandler.getRegistrationById(id);
			Assert.notNull(registration, "Registration must exist");
			message.addPart("registration", registration);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message addRegistration(Message message) {
		try {
			Registration registration = message.getPart("registration");
			Long id = registrationServiceHandler.addRegistration(registration);
			Assert.notNull(id, "Id must exist");
			message.addPart("id", id);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message saveRegistration(Message message) {
		try {
			Registration registration = message.getPart("registration");
			registrationServiceHandler.saveRegistration(registration);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}

	@TransactionAttribute(REQUIRED)
	public Message deleteRegistration(Message message) {
		try {
			Registration registration = message.getPart("registration");
			registrationServiceHandler.deleteRegistration(registration);
			
		} catch (Throwable e) {
			log.error(e);
			//send error to event queue
			//forward message to invalid queue
			message.addPart("exception", e);
		}

		return message;
	}
	
}
