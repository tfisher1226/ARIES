package org.aries.message.init;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.aries.message.Fault;
import org.aries.message.Header;
import org.aries.message.Message;
import org.aries.message.ObjectFactory;


public class Context {

    public static JAXBContext createContext(ClassLoader classLoader) throws JAXBException {
        return JAXBContext.newInstance(new Class[] {
    			Fault.class, 
    			Header.class, 
    			Header.Properties.class, 
    			Header.Properties.Entry.class, 
    			Message.class, 
    			Message.Parts.class, 
    			Message.Parts.Entry.class, 
    			ObjectFactory.class
    	});
    }

}
