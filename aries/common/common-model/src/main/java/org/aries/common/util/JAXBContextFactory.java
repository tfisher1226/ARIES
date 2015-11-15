package org.aries.common.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.aries.common.Attachment;
import org.aries.common.EmailAccount;
import org.aries.common.EmailAddress;
import org.aries.common.EmailAddressList;
import org.aries.common.EmailBox;
import org.aries.common.EmailMessage;
import org.aries.common.ObjectFactory;
import org.aries.common.PersonName;
import org.aries.common.PhoneNumber;
import org.aries.common.StreetAddress;


public class JAXBContextFactory {

    public static JAXBContext createContext(ClassLoader classLoader) throws JAXBException {
		return JAXBContext.newInstance(new Class[] {
    			EmailAccount.class, 
    			EmailBox.class, 
    			Attachment.class, 
    			StreetAddress.class, 
    			PhoneNumber.class, 
    			EmailAddressList.class, 
    			PersonName.class, 
    			EmailMessage.class, 
    			EmailAddress.class, 
    			ObjectFactory.class
    	});
    }

}
