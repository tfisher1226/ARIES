package org.aries.common.util;

import org.aries.common.map.AttachmentMapper;
import org.aries.common.map.EmailAccountMapper;
import org.aries.common.map.EmailAddressListMapper;
import org.aries.common.map.EmailAddressMapper;
import org.aries.common.map.EmailBoxMapper;
import org.aries.common.map.EmailMessageMapper;
import org.aries.common.map.PersonMapper;
import org.aries.common.map.PersonNameMapper;
import org.aries.common.map.PhoneNumberMapper;
import org.aries.common.map.PropertyMapper;
import org.aries.common.map.StreetAddressMapper;
import org.aries.common.map.ZipCodeMapper;
import org.aries.util.FieldUtil;


public class CommonMapperFixture {

	private static AttachmentMapper attachmentMapper;

	private static EmailAccountMapper emailAccountMapper;
	
	private static EmailAddressMapper emailAddressMapper;
	
	private static EmailAddressListMapper emailAddressListMapper;
	
	private static EmailBoxMapper emailBoxMapper;
	
	private static EmailMessageMapper emailMessageMapper;
	
	private static PersonMapper personMapper;

	private static PersonNameMapper personNameMapper;

	private static PhoneNumberMapper phoneNumberMapper;

	private static PropertyMapper propertyMapper;
	
	private static StreetAddressMapper streetAddressMapper;
	
	private static ZipCodeMapper zipCodeMapper;

	
	public static void clear() {
		attachmentMapper = null;
		emailAccountMapper = null;
		emailAddressMapper = null;
		emailAddressListMapper = null;
		emailBoxMapper = null;
		emailMessageMapper = null;
		personMapper = null;
		personNameMapper = null;
		phoneNumberMapper = null;
		propertyMapper = null;
		streetAddressMapper = null;
		zipCodeMapper = null;
	}
	
	public static AttachmentMapper getAttachmentMapper() {
		if (attachmentMapper == null) {
			attachmentMapper = new AttachmentMapper();
		}
		return attachmentMapper;
	}

	public static EmailAccountMapper getEmailAccountMapper() {
		if (emailAccountMapper == null) {
			emailAccountMapper = new EmailAccountMapper();
			FieldUtil.setFieldValue(emailAccountMapper, "emailBoxMapper", getEmailBoxMapper());
		}
		return emailAccountMapper;
	}

	public static EmailAddressMapper getEmailAddressMapper() {
		if (emailAddressMapper == null) {
			emailAddressMapper = new EmailAddressMapper();
			FieldUtil.setFieldValue(emailAddressMapper, "phoneNumberMapper", getPhoneNumberMapper());
		}
		return emailAddressMapper;
	}
	
	public static EmailAddressListMapper getEmailAddressListMapper() {
		if (emailAddressListMapper == null) {
			emailAddressListMapper = new EmailAddressListMapper();
			FieldUtil.setFieldValue(emailAddressListMapper, "emailAddressMapper", getEmailAddressMapper());
		}
		return emailAddressListMapper;
	}

	public static EmailBoxMapper getEmailBoxMapper() {
		if (emailBoxMapper == null) {
			emailBoxMapper = new EmailBoxMapper();
			FieldUtil.setFieldValue(emailBoxMapper, "emailMessageMapper", getEmailMessageMapper());
		}
		return emailBoxMapper;
	}

	public static EmailMessageMapper getEmailMessageMapper() {
		if (emailMessageMapper == null) {
			emailMessageMapper = new EmailMessageMapper();
			FieldUtil.setFieldValue(emailMessageMapper, "emailAddressMapper", getEmailAddressMapper());
			FieldUtil.setFieldValue(emailMessageMapper, "emailAddressListMapper", getEmailAddressListMapper());
			FieldUtil.setFieldValue(emailMessageMapper, "attachmentMapper", getAttachmentMapper());
		}
		return emailMessageMapper;
	}

	public static PersonMapper getPersonMapper() {
		if (personMapper == null) {
			personMapper = new PersonMapper();
			FieldUtil.setFieldValue(personMapper, "personNameMapper", getPersonNameMapper());
			FieldUtil.setFieldValue(personMapper, "phoneNumberMapper", getPhoneNumberMapper());
			FieldUtil.setFieldValue(personMapper, "emailAddressMapper", getEmailAddressMapper());
			FieldUtil.setFieldValue(personMapper, "streetAddressMapper", getStreetAddressMapper());
		}
		return personMapper;
	}
	
	public static PersonNameMapper getPersonNameMapper() {
		if (personNameMapper == null) {
			personNameMapper = new PersonNameMapper();
		}
		return personNameMapper;
	}
	
	public static PhoneNumberMapper getPhoneNumberMapper() {
		if (phoneNumberMapper == null) {
			phoneNumberMapper = new PhoneNumberMapper();
		}
		return phoneNumberMapper;
	}
	
	public static PropertyMapper getPropertyMapper() {
		if (propertyMapper == null) {
			propertyMapper = new PropertyMapper();
		}
		return propertyMapper;
	}
	
	public static StreetAddressMapper getStreetAddressMapper() {
		if (streetAddressMapper == null) {
			streetAddressMapper = new StreetAddressMapper();
			FieldUtil.setFieldValue(streetAddressMapper, "zipCodeMapper", getZipCodeMapper());
		}
		return streetAddressMapper;
	}
	
	public static ZipCodeMapper getZipCodeMapper() {
		if (zipCodeMapper == null) {
			zipCodeMapper = new ZipCodeMapper();
		}
		return zipCodeMapper;
	}
	
}
