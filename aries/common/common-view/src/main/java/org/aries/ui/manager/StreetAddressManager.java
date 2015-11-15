package org.aries.ui.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.common.StreetAddress;
import org.aries.common.util.StreetAddressUtil;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordManager;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;



@SessionScoped
@Named("streetAddressManager")
@SuppressWarnings("serial")
public class StreetAddressManager extends AbstractRecordManager<StreetAddress> implements Serializable {

	@Inject
	@Updated
	private Event<StreetAddress> updatedEvent;
	
	
	public StreetAddressManager() {
		//nothing for now
	}
	
	@Override
	public String getClientId() {
		return "StreetAddress";
	}
	
	@Override
	public String getLabel() {
		return "Street Address";
	}
	
	public StreetAddress getStreetAddress() {
		return getRecord();
	}
	
//	public void setStreetAddress(StreetAddress streetAddress) {
//		setRecord(streetAddress);
//	}
	
	@Override
	public boolean isEmpty(StreetAddress streetAddress) {
		return getStreetAddressHelper().isEmpty(streetAddress);
	}

	@Override
	public String toString(StreetAddress streetAddress) {
		return getStreetAddressHelper().toString(streetAddress);
	}
	
	@Override
	public Class<StreetAddress> getRecordClass() {
		return StreetAddress.class;
	}
	
	public List<String> getAddress() {
		List<String> label = new ArrayList<String>(2);
		label.add(getAddressLine1());
		label.add(getAddressLine2());
		return label;
	}
	
	public String getAddressLine1() {
		return getStreetAddressHelper().toStringLine1(getRecord()); 
	}

	public String getAddressLine2() {
		return getStreetAddressHelper().toStringLine2(getRecord()); 
	}
	
	
	/*
	 * Externals
	 */
	
	public StreetAddressHelper getStreetAddressHelper() {
		return BeanContext.getFromSession("streetAddressHelper");
	}
	
	
	/*
	 * Lifecycle
	 */
	
	@Override
	public void initialize() {
		super.initialize();
	}
	
	@Override
	public StreetAddress create() {
		return StreetAddressUtil.create();
	}
	
	@Override
	public StreetAddress clone(StreetAddress streetAddress) {
		return StreetAddressUtil.clone(streetAddress);
	}
	
	@Override
	public String submit() {
		setModule("StreetAddressDialog");
		super.submit();
		updatedEvent.fire(getStreetAddress());
		return null;
	}
	
	@Override
	public boolean validate() {
		return validate(getStreetAddress());
	}
	
	@Override
	public boolean validate(StreetAddress streetAddress) {
		if (streetAddress == null)
			return false;
		Validator validator = getValidator();
		boolean isValid = StreetAddressUtil.validate(streetAddress);
		display.addErrors(validator.getMessages());
		setValidated(isValid);
		return isValid;
	}

}
