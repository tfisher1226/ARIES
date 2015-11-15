package org.aries.ui.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.aries.common.PhoneLocation;
import org.aries.common.PhoneNumber;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractRecordMapManager;
import org.aries.ui.event.Updated;
import org.aries.util.Validator;



@SessionScoped
@Named("phoneNumberManager")
@SuppressWarnings("serial")
public class PhoneNumberManager extends AbstractRecordMapManager<PhoneLocation, PhoneNumber> implements Serializable {
	
//	private Map<PhoneLocation, PhoneNumber> phoneNumberMap = new HashMap<PhoneLocation, PhoneNumber>();

	@Inject
	@Updated
	private Event<PhoneNumber[]> updatedEvent;
	
	
	@Override
	public String getModule() {
		return "PhoneNumbers";
	}

	@Override
	public String getLabel() {
		return "Phone Number(s)";
	}
	
	@Override
	public boolean isEmpty() {
		return isEmpty(getRecordMap()); 
	}

	@Override
	protected boolean isEmpty(Map<PhoneLocation, PhoneNumber> recordMap) {
		return getPhoneNumberHelper().isEmpty(recordMap); 
	}
	
	@Override
	public boolean isEmpty(PhoneNumber phoneNumber) {
		return getPhoneNumberHelper().isEmpty(phoneNumber); 
	}

	@Override
	protected Class<PhoneNumber> getRecordClass() {
		return PhoneNumber.class;
	}
	
//	public String getRecordText() {
//		return "PhoneNumber";
//	}
	
	public PhoneNumber getHomePhone() {
		return getPhoneNumber(PhoneLocation.HOME);
	}

	public void setHomePhone(PhoneNumber phoneNumber) {
		recordMap.put(PhoneLocation.HOME, phoneNumber);
		if (phoneNumber != null)
			phoneNumber.setType(PhoneLocation.HOME);
	}

	public boolean getHomePhoneEmpty() {
		return isEmpty(getHomePhone());
	}
	
	public PhoneNumber getWorkPhone() {
		return getPhoneNumber(PhoneLocation.WORK);
	}

	public void setWorkPhone(PhoneNumber phoneNumber) {
		recordMap.put(PhoneLocation.WORK, phoneNumber);
		if (phoneNumber != null)
			phoneNumber.setType(PhoneLocation.WORK);
	}

	public boolean getWorkPhoneEmpty() {
		return isEmpty(getWorkPhone());
	}
	
	public PhoneNumber getCellPhone() {
		return getPhoneNumber(PhoneLocation.CELL);
	}

	public void setCellPhone(PhoneNumber phoneNumber) {
		recordMap.put(PhoneLocation.CELL, phoneNumber);
		if (phoneNumber != null)
			phoneNumber.setType(PhoneLocation.CELL);
	}

	public boolean getCellPhoneEmpty() {
		return isEmpty(getCellPhone());
	}
	
	public PhoneNumber getOtherPhone() {
		return getPhoneNumber(PhoneLocation.OTHER);
	}

	public void setOtherPhone(PhoneNumber phoneNumber) {
		recordMap.put(PhoneLocation.OTHER, phoneNumber);
		if (phoneNumber != null)
			phoneNumber.setType(PhoneLocation.OTHER);
	}

	public boolean getOtherPhoneEmpty() {
		return isEmpty(getOtherPhone());
	}
	
	
	public PhoneNumber getPhoneNumber(PhoneLocation phoneLocation) {
		if (recordMap != null)
			return recordMap.get(phoneLocation);
		return null;
	}
	
	public Map<PhoneLocation, PhoneNumber> getPhoneNumberMap() {
		return recordMap;
	}

	public void setPhoneNumberMap(Map<PhoneLocation, PhoneNumber> phoneNumberMap) {
		this.recordMap = phoneNumberMap;
	}
	
	
	/*
	 * Externals
	 */
	
	public PhoneNumberHelper getPhoneNumberHelper() {
		return BeanContext.getFromSession("phoneNumberHelper");
	}
	

	/*
	 * Lifecycle
	 */
	
	@Override
	public void initialize() {
		super.initialize();
	}
	
	@Override
	public Map<PhoneLocation, PhoneNumber> create() {
		Map<PhoneLocation, PhoneNumber> map = new HashMap<PhoneLocation, PhoneNumber>();
		return map;
	}

//	@Override
//	public PhoneNumber clone(PhoneNumber phoneNumber) {
//		return PhoneNumberUtil.clone(phoneNumber);
//	}
	
	@Override
	public String submit() {
		setModule("PhoneNumberDialog");
		super.submit();
		Collection<PhoneNumber> list = new ArrayList<PhoneNumber>();
		Collection<PhoneNumber> values = recordMap.values();
		Iterator<PhoneNumber> iterator = values.iterator();
		while (iterator.hasNext()) {
			PhoneNumber phoneNumber = iterator.next();
			if (phoneNumber != null)
				list.add(phoneNumber);
		}
		PhoneNumber[] array = (PhoneNumber[]) list.toArray(new PhoneNumber[list.size()]);
		updatedEvent.fire(array);
		return null;
	}

	@Override
	public boolean validate() {
		return validate(recordMap);
	}
	
	@Override
	public boolean validate(Map<PhoneLocation, PhoneNumber> phoneNumberMap) {
		try {
			Set<PhoneLocation> keySet = phoneNumberMap.keySet();
			Iterator<PhoneLocation> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				PhoneLocation phoneLocation = iterator.next();
				PhoneNumber phoneNumber = getPhoneNumber(phoneLocation);
				if (phoneNumber != null)
					validate(phoneNumber);
			}
			boolean isValid = !display.messagesExist();
			setValidated(isValid);
			return isValid;
		} catch (Exception e) {
			display.warn(e.getMessage());
			return false;
		}
	}

	public boolean validate(PhoneNumber phoneNumber) {
		if (phoneNumber == null)
			return false;
		return validate(phoneNumber.getType(), phoneNumber);
	}
	
	@Override
	public boolean validate(PhoneLocation phoneLocation, PhoneNumber phoneNumber) {
		if (isEmpty(phoneNumber))
			return true;
		
		Validator validator = Validator.getValidator();
		PhoneLocation type = phoneNumber.getType();
		boolean status = true;
		if (phoneNumber.getCountry() == null) {
			display.error(phoneLocation+" phone country must be specified");
			status = false;
		}
		if (phoneNumber.getArea().length() != 3) {
			display.error(phoneLocation+" phone has invalid area-code: "+phoneNumber.getArea());
			status = false;
		}
		if (phoneNumber.getNumber().length() != 7) {
			display.error(phoneLocation+" phone has invalid number: "+phoneNumber.getNumber());
			status = false;
		}
		
		return status;
	}
	
}
