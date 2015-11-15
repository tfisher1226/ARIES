package org.aries.model;

import org.aries.common.Country;
import org.aries.common.EmailAddress;
import org.aries.common.PersonName;
import org.aries.common.PhoneNumber;
import org.aries.common.State;
import org.aries.common.StreetAddress;



public class CommonModelTestFixture {

	public static PersonName createPersonNameForTest() {
		PersonName personName = new PersonName ();
		personName.setFirstName("Suger Ray");
		personName.setLastName("Leaneord");
		personName.setMiddleInitial("A");
		return personName;
	}

	public static EmailAddress createEmailAddressForTest() {
		EmailAddress emailAddress = new EmailAddress();
		emailAddress.setUrl("tom.fisher@aries.org");
		return emailAddress;
	}

	public static StreetAddress createStreetAddressForTest() {
		StreetAddress streetAddress = new StreetAddress();
		streetAddress.setStreet("999 Valley #111");
		streetAddress.setCity("Alhambra");
//		streetAddress.setState(State.CA);
//		streetAddress.setZip(getZipCodeForTest());
//		streetAddress.setCountry(CountryCode.US);
		return streetAddress;
	}

	public static int getZipCodeForTest() {
		return 91801;
	}

	public static State getStateCodeForTest() {
		return State.CA;
	}

	public static Country getCountryCodeForTest() {
		return Country.USA;
	}

	public static PhoneNumber createPhoneNumberForTest() {
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setArea("213");
		phoneNumber.setNumber("379-3806");
//		phoneNumber.setCountry(CountryCode.US);
		return phoneNumber;
	}

}
