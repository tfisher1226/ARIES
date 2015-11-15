package org.aries.jaxb;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


abstract class AbstractJAXBSession {

	protected Log log = LogFactory.getLog(getClass());

	private Schema schema;

//	private Source[] schemas;

	private Class<?>[] classArray;

	private Set<String> packagesToLoad = new HashSet<String>();
	
	private Set<Class<?>> classesToLoad = new HashSet<Class<?>>();

	protected Object mutex = new Object();
	
	
	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

//	public Source[] getSchemas() {
//		return schemas;
//	}
//
//	public void setSchemas(Source[] schemas) {
//		this.schemas= schemas;
//	}

	public Collection<String> getPackagesToLoad() {
		return packagesToLoad;
	}
	
	public String getPackagesToLoadAsString() {
		StringBuffer buf = new StringBuffer();
		Iterator<String> iterator = packagesToLoad.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			String packageToLoad = iterator.next();
			if (i > 0)
				buf.append(":");
			buf.append(packageToLoad);
		}
		return buf.toString();
	}

	public void addPackageToLoad(String packageName) {
		this.packagesToLoad.add(packageName);
	}
	
	public void addPackagesToLoad(Collection<String> packagesToLoad) {
		this.packagesToLoad.addAll(packagesToLoad);
	}
	
	public void setPackagesToLoad(Collection<String> packagesToLoad) {
		this.packagesToLoad = new HashSet<String>(packagesToLoad);
	}

	public Collection<Class<?>> getClasses() {
		return classesToLoad;
	}

	public Class<?>[] getClassArray() {
		this.classArray = classesToLoad.toArray(new Class<?>[classesToLoad.size()]);
		return classArray;
	}

	public void addClass(Class<?> classObject) {
		this.classesToLoad.add(classObject);
	}

	public void addClasses(Collection<Class<?>> classes) {
		this.classesToLoad.addAll(classes);
	}

	public void setClasses(Collection<Class<?>> classes) {
		this.classesToLoad = new HashSet<Class<?>>(classes);
	}


	protected void logEvents(ValidationEventCollector eventCollector) {
		StringBuffer buf = new StringBuffer("Error:");
		for (ValidationEvent event: eventCollector.getEvents()) {
			logEvent(event);
//			ValidationEventLocator locator = event.getLocator();
//			int severity = event.getSeverity();
//			if (severity > -1)
//				buf.append(" severity="+severity);
//			int line = locator.getLineNumber();
//			if (line > -1)
//				buf.append(" line="+line);
//			int column = locator.getColumnNumber();
//			if (column > -1)
//				buf.append(" column="+column);
//			buf.append(event.getMessage());
//			log.error(buf.toString());
		}
	}

	public void logEvent(ValidationEvent event) {
		StringBuffer buf = new StringBuffer("Error:");
		buf.append("EVENT\n");
        buf.append("SEVERITY:          " + event.getSeverity() + "\n");
        buf.append("MESSAGE:           " + event.getMessage() + "\n");
        buf.append("LINKED EXCEPTION:  " + event.getLinkedException() + "\n");
        buf.append("LOCATOR\n");
        buf.append("    LINE NUMBER:    " + event.getLocator().getLineNumber() + "\n");
        buf.append("    COLUMN NUMBER:  " + event.getLocator().getColumnNumber() + "\n");
        buf.append("    OFFSET:         " + event.getLocator().getOffset() + "\n");
        buf.append("    OBJECT:         " + event.getLocator().getObject() + "\n");
        buf.append("    NODE:           " + event.getLocator().getNode() + "\n");
        buf.append("    URL:            " + event.getLocator().getURL() + "\n");
        log.error(buf.toString());
    }
	
	protected XMLStreamReader getXMLStreamReader(InputStream stream) {
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(stream, "UTF-8");
			return reader;
		} catch (XMLStreamException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	
}
