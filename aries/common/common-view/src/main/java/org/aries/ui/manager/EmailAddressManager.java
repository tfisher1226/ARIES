package org.aries.ui.manager;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.common.EmailAddress;
import org.aries.common.util.EmailAddressUtil;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;


@SessionScoped
@Named("emailAddressManager")
@SuppressWarnings("serial")
public class EmailAddressManager extends AbstractRecordManager<EmailAddress> implements /*ValueChangeListener, StateHolder,*/ Serializable {

//	private EmailAddress emailAddress;
	
	@Inject
	@Updated
	private Event<EmailAddress> updatedEvent;
	

	@Override
	public String getClientId() {
		return "EmailAddress";
	}
	
	@Override
	public String getLabel() {
		return "Email Address";
	}
	
	@Override
	public boolean isEmpty() {
		return isEmpty(getRecord());
	}
	
	@Override
	public boolean isEmpty(EmailAddress emailAddress) {
		return getEmailAddressHelper().isEmpty(emailAddress);
	}
	
	@Override
	public String toString(EmailAddress emailAddress) {
		return getEmailAddressHelper().toString(emailAddress);
	}
	
	@Override
	public Class<EmailAddress> getRecordClass() {
		return EmailAddress.class;
	}
	
	public EmailAddress getEmailAddress() {
		return getRecord();
	}
	
//	public void setEmailAddress(EmailAddress emailAddress) {
//		setRecord(emailAddress);
//	}
	
//	public void setRecordInstance(String instanceName) {
//		this.emailAddress = BeanContext.getFromSession(instanceName);
//		setActionEvent(instanceName+"Entered");
//		setCancelEvent(instanceName+"Cancelled");
//		setRecord(emailAddress);
//	}
	
//	public String getRecordText() {
//		return getEmailAddressHelper().toString(emailAddress);
//	}
	
	
	/*
	 * Externals
	 */

	public EmailAddressHelper getEmailAddressHelper() {
		return BeanContext.getFromSession("emailAddressHelper");
	}

	
	/*
	 * Lifecycle
	 */
	
	@Override
	public void initialize() {
		super.initialize();
	}
	
	@Override
	public EmailAddress create() {
		return EmailAddressUtil.create();
	}
	
	@Override
	public EmailAddress clone(EmailAddress emailAddress) {
		return EmailAddressUtil.clone(emailAddress);
	}
	
	@Override
	public String submit() {
		setModule("EmailAddressDialog");
		super.submit();
		updatedEvent.fire(getEmailAddress());
		return null;
	}
	
	@Override
	public boolean validate() {
		return validate(getEmailAddress());
	}
	
	@Override
	public boolean validate(EmailAddress emailAddress) {
		if (emailAddress == null)
			return false;
		Validator validator = getValidator();
		boolean isValid = EmailAddressUtil.validate(emailAddress);
		display.addErrors(validator.getMessages());
		setValidated(isValid);
		return isValid;
	}

	
//	private boolean isTransient;
//	
//	
//	@Override
//	public boolean isTransient() {
//		return isTransient;
//	}
//
//	@Override
//	public void setTransient(boolean newTransientValue) {
//		this.isTransient = newTransientValue;
//	}
//	
//	@Override
//	public Object saveState(FacesContext context) {
//		System.out.println("saveState: context="+context);
//		return null;
//	}
//
//	@Override
//	public void restoreState(FacesContext context, Object state) {
//		System.out.println("restoreState: context="+context+", state="+state);
//	}
//
//	@Override
//	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
//		System.out.println("processValueChange: event="+event);
//	}

}
