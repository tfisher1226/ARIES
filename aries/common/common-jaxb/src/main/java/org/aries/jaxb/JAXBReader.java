package org.aries.jaxb;

import java.util.Collection;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller.Listener;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;


public interface JAXBReader {

	public void setSchema(Schema schema);

	//public void setSchemas(Source[] schemas);

	public Collection<Class<?>> getClasses();

	public void setPackagesToLoad(Collection<String> packages);
	
	public void setClasses(Collection<Class<?>> classes);

	public void initialize() throws JAXBException;
	
	public <T> T unmarshal(String text) throws JAXBException;
	
	public <T> T unmarshal(byte[] text) throws JAXBException;

	public <T> T unmarshalFromFileSystem(String filePath) throws JAXBException;

	public <T> T unmarshalFromClasspath(String fileName) throws JAXBException;

	public <T> T unmarshal(XMLStreamReader reader) throws JAXBException;

	public String getNamespaceURI(String prefix);

	public void addListener(Listener listener);

}
