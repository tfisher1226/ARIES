package org.aries.common;

import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
		//nothing for now
    }

	
    public Attachment createAttachment() {
        return new Attachment();
    }
    
    public EmailAccount createEmailAccount() {
        return new EmailAccount();
    }
    
    public EmailAddress createEmailAddress() {
        return new EmailAddress();
    }

    public EmailAddressList createEmailAddressList() {
        return new EmailAddressList();
    }
    
    public EmailBox createEmailBox() {
        return new EmailBox();
    }

    public EmailMessage createEmailMessage() {
        return new EmailMessage();
    }

    public Map createMap() {
        return new Map();
    }

    public MapEntry createMapEntry() {
        return new MapEntry();
    }
    
    public Person createPerson() {
        return new Person();
    }

    public PersonName createPersonName() {
        return new PersonName();
    }

    public PhoneNumber createPhoneNumber() {
        return new PhoneNumber();
    }

    public Properties createProperties() {
        return new Properties();
    }

	public Property createProperty() {
		return new Property();
    }
    
    public Sequence createSequence() {
        return new Sequence();
    }
    
    public StreetAddress createStreetAddress() {
        return new StreetAddress();
    }

    public ZipCode createZipCode() {
        return new ZipCode();
    }
    
}
