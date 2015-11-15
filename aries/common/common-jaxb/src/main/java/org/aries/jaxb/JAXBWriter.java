package org.aries.jaxb;

import java.util.Collection;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;


public interface JAXBWriter {

	public void setSchema(Schema schema);

	//public void setSchemas(Source[] schemas);

	public Collection<Class<?>> getClasses();

	public void setPackagesToLoad(Collection<String> packages);

	public void setClasses(Collection<Class<?>> classes);

	public void initialize() throws JAXBException;

	public <T> String marshal(T object) throws JAXBException;
	
}
