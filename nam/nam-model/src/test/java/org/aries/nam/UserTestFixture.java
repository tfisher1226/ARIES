package org.aries.nam;

import nam.model.User;

import org.aries.common.Country;
import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.common.PhoneLocation;
import org.aries.common.PhoneNumber;


public class UserTestFixture {

	public static String getUserIdForTest() {
		return "tfisher";
	}

	public static User createUserForTest() {
		return createUserForTest(getUserIdForTest());
	}

	public static User createUserForTest(String userId) {
		PersonName personName = new PersonName();
		personName.setFirstName("Tom");
		personName.setLastName("Fisher");
		User user = new User();
		user.setPersonName(personName);
		user.setUserName(userId); 
		user.setPasswordHash("tfisher");
		user.setHomePhone(createPhoneNumberForTest());
		user.setCellPhone(createPhoneNumberForTest());
		user.setEmailAddress(createEmailAddressForTest(userId));
		user.setEnabled(true);
		return user;
	}

	public static PhoneNumber createPhoneNumberForTest() {
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setCountry(Country.USA);
		phoneNumber.setArea("213");
		phoneNumber.setNumber("379-3806");
		phoneNumber.setType(PhoneLocation.CELL);
		return phoneNumber;
	}

	public static EmailAddress createEmailAddressForTest(String userId) {
		EmailAddress emailAddress = new EmailAddress();
		emailAddress.setUserId(userId); 
		emailAddress.setUrl("tfisher@aaa.com");
		emailAddress.setFirstName("Tom");
		emailAddress.setLastName("Fisher");
		emailAddress.setOrganization("aries");
		return emailAddress;
	}
	
}
