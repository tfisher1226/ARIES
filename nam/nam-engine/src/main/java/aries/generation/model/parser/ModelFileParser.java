package aries.generation.model.parser;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nam.model.Project;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ModelFileParser {

	private static Log log = LogFactory.getLog(Project.class);

	private String filePath;

	private Project model;
	
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Project getModel() {
		return model;
	}

	public void setModel(Project model) {
		this.model = model;
	}

	public Project parse() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			XMLStreamReader streamReader = getConfigStreamReader();
			JAXBElement<Project> rootElement = unmarshaller.unmarshal(streamReader, Project.class);
			model = rootElement.getValue();
			return model;
			
		} catch (JAXBException e) {
			String message = "Error reading model file: "+filePath+"\n"+e;
			log.error(message);
			throw new IllegalArgumentException(message);
		
		} catch (XMLStreamException e) {
			String message = "Error reading model file: "+filePath+" "+e.getMessage();
			log.error(message);
			throw new IllegalArgumentException(message);
		}
	}
	
	protected XMLStreamReader getConfigStreamReader() throws XMLStreamException {
		if (filePath == null) {
			throw new IllegalStateException("model file must be specified");
		}
		
		try {
			log.debug(String.format("Reading model: \"%s\"", filePath));
			
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream stream = loader.getResourceAsStream(filePath);
			
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(stream, "UTF-8");
			return reader;
		
		} catch (IllegalArgumentException e) {
			String message = String.format("Error reading model file: \"%s\"", filePath);
			log.error(message);
			throw new IllegalStateException(message);
		}
	}

}
