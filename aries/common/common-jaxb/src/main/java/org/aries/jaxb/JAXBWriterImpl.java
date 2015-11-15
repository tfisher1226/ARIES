package org.aries.jaxb;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.xml.bind.v2.model.annotation.RuntimeAnnotationReader;


public class JAXBWriterImpl extends AbstractJAXBSession implements JAXBWriter {

	private JAXBContext context;
	
	private Marshaller marshaller;

	private Validator validator;

	private ValidationEventCollector eventCollector;
	
	
	public JAXBWriterImpl() {
		
	}
	
	@Override
	public void initialize() throws JAXBException {
		try {
			Schema schema = getSchema();
			validator = schema.newValidator();
	        validator.setErrorHandler(createErrorHandler());

			Map<String, Object> jaxbConfig = new HashMap<String, Object>();
			TransientAnnotationReader annotationReader = new TransientAnnotationReader();
			annotationReader.addTransientField(Throwable.class.getDeclaredField("stackTrace"));
			annotationReader.addTransientMethod(Throwable.class.getDeclaredMethod("getStackTrace"));
			jaxbConfig.put(RuntimeAnnotationReader.class.getName(), annotationReader);
			addPackagesToLoad(TransientAnnotationReader.getPackagesToLoad());

			//context = JAXBContext.newInstance(getClassArray());
			context = JAXBContext.newInstance(getPackagesToLoadAsString(), TransientAnnotationReader.class.getClassLoader(), jaxbConfig);
			marshaller = context.createMarshaller();
			//marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	
			eventCollector = new ValidationEventCollector();
			marshaller.setEventHandler(eventCollector);
			marshaller.setSchema(schema);
			
		} catch (Exception e) {
			throw new JAXBException(e);
		}
	}
	
	@Override
	public <T> String marshal(T object) throws JAXBException {
		try {
			JAXBSource source = new JAXBSource(context, object);
	        //validator.validate(source);

	        StringWriter writer = new StringWriter();
			marshaller.marshal(object, writer);
			String xml = writer.toString();
			return xml;
			
		} catch (JAXBException e) {
			e.printStackTrace();
			logEvents(eventCollector);
			throw e;
//		} catch (SAXException e) {
//			log.error(e);
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			log.error(e);
//			throw new RuntimeException(e);
		}
	}
	
	protected ErrorHandler createErrorHandler() {
		return new ErrorHandler() {
			protected Log log = LogFactory.getLog(getClass());
			
			@Override
			public void warning(SAXParseException exception) throws SAXException {
				log.warn(exception);
			}

			@Override
			public void error(SAXParseException exception) throws SAXException {
				log.error(exception);
			}

			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				log.fatal(exception);
			}
		};
	}
	
	
}
