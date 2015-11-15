package org.aries.ui.manager;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.aries.common.PersonName;
import org.aries.common.util.PersonNameUtil;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.event.Updated;



@SessionScoped
@Named("personNameManager")
@SuppressWarnings("serial")
public class PersonNameManager extends AbstractRecordManager<PersonName> implements Serializable {

	//private PersonName personName;

	private boolean middleInitialEnabled;
	
	@Inject
	@Updated
	private Event<PersonName> updatedEvent;
	

	public PersonName getPersonName() {
		return getRecord();
	}
	
//	public void setPersonName(PersonName personName) {
//		setRecord(personName);
//	}
	
	@Override
	public String getClientId() {
		return "PersonName";
	}
	
	@Override
	public String getLabel() {
		return "Person Name";
	}
	
	@Override
	public boolean isEmpty(PersonName personName) {
		return getPersonNameHelper().isEmpty(personName);
	}
	
	@Override
	public String toString(PersonName personName) {
		return getPersonNameHelper().toString(personName);
	}
	
	public boolean getMiddleInitialEnabled() {
		return middleInitialEnabled;
	}

	public void setMiddleInitialEnabled(boolean middleInitialEnabled) {
		this.middleInitialEnabled = middleInitialEnabled;
	}

	@Override
	public Class<PersonName> getRecordClass() {
		return PersonName.class;
	}
	

	/*
	 * Externals
	 */

	public PersonNameHelper getPersonNameHelper() {
		return BeanContext.getFromSession("personNameHelper");
	}


	/*
	 * Lifecycle
	 */

	@Override
	public void initialize() {
		super.initialize();
	}
	
	@Override
	public PersonName create() {
		return PersonNameUtil.create();
	}

	@Override
	public PersonName clone(PersonName personName) {
		return PersonNameUtil.clone(personName);
	}
	
	@Override
	public void activate() {
		super.activate();
	}
	
	@Override
	public String submit() {
		setModule("PersonNameDialog");
		super.submit();
		updatedEvent.fire(getPersonName());
		return null;
	}
	
	@Override
	public boolean validate() {
		return validate(getPersonName());
	}
	
	@Override
	public boolean validate(PersonName personName) {
		if (personName == null)
			return false;
		if (StringUtils.isEmpty(personName.getLastName()))
			display.error("Last name must be specified");
		if (StringUtils.isEmpty(personName.getFirstName()))
			display.error("First name must be specified");
		if (middleInitialEnabled)
			if (StringUtils.isEmpty(personName.getMiddleInitial()))
				display.error("Middle initial must be specified");
		boolean isValid = !display.messagesExist();
		setValidated(isValid);
		return isValid;
	}

}
