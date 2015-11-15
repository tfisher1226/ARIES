package org.aries.jaxb;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Unmarshaller.Listener;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.util.FileUtil;
import org.aries.util.ResourceUtil;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.xml.bind.v2.model.annotation.RuntimeAnnotationReader;


public class JAXBReaderImpl extends AbstractJAXBSession implements JAXBReader {

	private JAXBContext context;

	private XMLStreamReader reader;
	
	private Unmarshaller unmarshaller;

	//private Validator validator;

	private ValidationEventCollector eventCollector;

	private Listener listener;

	
	public JAXBReaderImpl() {
	}
	
	@Override
	public void initialize() throws JAXBException {
		//ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		//Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
		
		try {
			Schema schema = getSchema();
			//validator = schema.newValidator();
			//validator.setErrorHandler(createErrorHandler());

			Map<String, Object> jaxbConfig = new HashMap<String, Object>();
			TransientAnnotationReader annotationReader = new TransientAnnotationReader();
			annotationReader.addTransientField(Throwable.class.getDeclaredField("stackTrace"));
			annotationReader.addTransientMethod(Throwable.class.getDeclaredMethod("getStackTrace"));
			jaxbConfig.put(RuntimeAnnotationReader.class.getName(), annotationReader);
			addPackagesToLoad(TransientAnnotationReader.getPackagesToLoad());
			
			//context = JAXBContext.newInstance(getClassArray(), jaxbConfig);
			context = JAXBContext.newInstance(getPackagesToLoadAsString(), TransientAnnotationReader.class.getClassLoader(), jaxbConfig);
			//ClassLoader classLoader = new JAXBSessionClassLoader(this.getClass().getClassLoader().getParent(), Collections.unmodifiableMap(objectFactories));
			//context = JAXBContext.newInstance("org.aries.common:org.aries.message:org.aries.nam.model:org.aries.nam.process:org.sgiusa.model", this.getClass().getClassLoader());
			unmarshaller = context.createUnmarshaller();
			
			eventCollector = new ValidationEventCollector();
			unmarshaller.setEventHandler(eventCollector);
			unmarshaller.setSchema(schema);

		} catch (Exception e) {
			throw new JAXBException(e);
		} finally {
			//Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public <T> T unmarshal(String text) throws JAXBException {
		Assert.notNull(text, "Text content must be specified");
		return unmarshal(text.getBytes());
	}

	@Override
	public <T> T unmarshal(byte[] text) throws JAXBException {
		Assert.notNull(text, "Text content must be specified");
		InputStream stream = new ByteArrayInputStream(text);
		reader = getXMLStreamReader(stream);
		@SuppressWarnings("unchecked") T object = (T) unmarshal(reader);
		return object;
	}

	@Override
	public <T> T unmarshalFromFileSystem(String filePath) throws JAXBException {
		Assert.notNull(filePath, "FilePath must be specified");
		log.info(String.format("Finding model: \"%s\"", filePath));
		
//		try {
//			Unmarshaller um = context.createUnmarshaller();
//			SAXParserFactory sax = SAXParserFactory.newInstance();
//			sax.setNamespaceAware(true);
//			XMLReader reader = sax.newSAXParser().getXMLReader();
//			Source er = new SAXSource(reader, new InputSource(new FileReader(filePath)));
//			@SuppressWarnings("unchecked") T object = (T) um.unmarshal(er);
//			return object;
//			
//		} catch (Throwable e) {
//			e.printStackTrace();
//			return null;
//		}
		
		InputStream stream = FileUtil.openFileAsStream(filePath);
		if (stream == null)
			throw new RuntimeException("File not found: "+filePath);
		log.debug(String.format("Found model: \"%s\"", filePath));
		reader = getXMLStreamReader(stream);
		@SuppressWarnings("unchecked") T object = (T) unmarshal(reader);
		
//		String namespaceURI = reader.getNamespaceURI("common");
//		int namespaceCount = reader.getNamespaceCount();
//		String localName = reader.getLocalName();
//		Location location = reader.getLocation();
//		String namespaceURI2 = reader.getNamespaceURI();
//		NamespaceContext namespaceContext = reader.getNamespaceContext();
//		String namespaceURI3 = namespaceContext.getNamespaceURI("common");
		
		return object;
	}

	//@Override
	public <T> T unmarshalFromFileSystemORIG(String filePath) throws JAXBException {
		Assert.notNull(filePath, "FilePath must be specified");
		log.debug(String.format("Searching for model: \"%s\"", filePath));
		InputStream stream = FileUtil.openFileAsStream(filePath);
		if (stream == null)
			throw new RuntimeException("File not found: "+filePath);
		reader = getXMLStreamReader(stream);
		@SuppressWarnings("unchecked") T object = (T) unmarshal(reader);
		return object;
	}
	
	@Override
	public <T> T unmarshalFromClasspath(String fileUrl) throws JAXBException {
		Assert.notNull(fileUrl, "FilePath must be specified");
		log.debug(String.format("Searching for model: \"%s\"", fileUrl));
		URL resource = ResourceUtil.getResource(fileUrl);
		if (resource == null)
			throw new RuntimeException("File not found: "+fileUrl);
		InputStream stream = FileUtil.openFileAsStream(resource.getFile());
		if (stream == null) {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			//URL resource2 = loader.getResource(fileUrl);
			stream = loader.getResourceAsStream(fileUrl);
		}
		if (stream == null)
			throw new RuntimeException("File not found: "+resource.getFile());
		log.debug(String.format("Found model: \"%s\"", resource.getFile()));
		reader = getXMLStreamReader(stream);
		@SuppressWarnings("unchecked") T object = (T) unmarshal(reader);
		return object;
	}

	@Override
	//TODO sync this?
	public <T> T unmarshal(final XMLStreamReader reader) throws JAXBException {
		Assert.notNull(getPackagesToLoad(), "Classes must be specified");
		Assert.isTrue(getPackagesToLoad().size() > 0, "Classes must be exist");

		try {
			//Validator validator = schema.newValidator();
			//validator.setErrorHandler(createErrorHandler());
			//validator.validate(source);
			//validator.validate(new StreamSource(new File("payload.xml"));

			if (listener != null)
				unmarshaller.setListener(listener);
			
			@SuppressWarnings("unchecked") T object = (T) unmarshaller.unmarshal(reader);
			return object;
		} catch (JAXBException e) {
			logEvents(eventCollector);
			throw e;
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
	
	@Override
	public String getNamespaceURI(String prefix) {
		return reader.getNamespaceURI(prefix);
	}

	@Override
	public void addListener(Listener listener) {
		this.listener = listener;
	}
	
}
