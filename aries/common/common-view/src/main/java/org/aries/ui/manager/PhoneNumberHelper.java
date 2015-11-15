package org.aries.ui.manager;

import java.io.Serializable;
import java.util.Map;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.aries.common.PhoneLocation;
import org.aries.common.PhoneNumber;
import org.aries.common.util.PhoneNumberUtil;



@SessionScoped
@Named("phoneNumberHelper")
@SuppressWarnings("serial")
public class PhoneNumberHelper implements Serializable {

	public boolean isValid(PhoneNumber phoneNumber) {
		return PhoneNumberUtil.isEmpty(phoneNumber);
	}

	public boolean isEmpty(PhoneNumber phoneNumber) {
		return PhoneNumberUtil.isEmpty(phoneNumber);
	}
	
	public boolean isEmpty(Map<PhoneLocation, PhoneNumber> phoneNumbers) {
		return PhoneNumberUtil.isEmpty(phoneNumbers);
	}
	
	public String toString(PhoneLocation phoneNumberType) {
		return PhoneNumberUtil.toString(phoneNumberType);
	}
	
	public String toString(PhoneNumber phoneNumber) {
		return PhoneNumberUtil.toPhoneNumberString(phoneNumber);
	}

}
