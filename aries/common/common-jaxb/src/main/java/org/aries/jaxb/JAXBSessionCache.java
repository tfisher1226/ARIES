package org.aries.jaxb;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.NameUtil;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class JAXBSessionCache {

	private Map<String, JAXBReader> readerCache = new HashMap<String, JAXBReader>();

	private Map<String, JAXBWriter> writerCache = new HashMap<String, JAXBWriter>();

	//private Map<String, Source> sourceCache = new HashMap<String, Source>();

	private Map<Object, Schema> schemaCache = new HashMap<Object, Schema>();

	private Set<String> fileNameCache = new LinkedHashSet<String>();

	private Set<String> packagesToLoad = new HashSet<String>();
	
	private List<Class<?>> classesToLoad = new ArrayList<Class<?>>();

	//private Source[] schemaSources;

	private String domain;


	public JAXBSessionCache(String domain) {
		this.domain = domain;
	}

	public void clear() {
		readerCache.clear();
		writerCache.clear();
		//sourceCache.clear();
		schemaCache.clear();
		fileNameCache.clear();
		packagesToLoad.clear();
		classesToLoad.clear();
		//schemaSources = null;
	}
	
	public JAXBReader getReader() throws Exception {
		synchronized (readerCache) {
			String name = "default";
			JAXBReader reader = readerCache.get(name);
			if (reader == null) {
				reader = new JAXBReaderImpl();
				reader.initialize();
				addReader(name, reader);
			}
			return reader;
		}
	}

	public JAXBWriter getWriter() throws Exception {
		synchronized (writerCache) {
			String name = "default";
			JAXBWriter writer = writerCache.get(name);
			if (writer == null) {
				writer = new JAXBWriterImpl();
				//writer.setSchema(getSchema());
				//writer.setClasses(getClasses());
				writer.initialize();
				addWriter(name, writer);
			}
			return writer;
		}
	}
	
	public JAXBReader getReader(String serviceId) {
		synchronized (readerCache) {
			JAXBReader reader = readerCache.get(serviceId);
			return reader;
		}
	}

	public JAXBWriter getWriter(String serviceId) {
		synchronized (writerCache) {
			JAXBWriter writer = writerCache.get(serviceId);
			return writer;
		}
	}

	public void addReader(String serviceId, JAXBReader reader) {
		synchronized (readerCache) {
			readerCache.put(serviceId, reader);
			//Schema schema = getSchema(serviceId);
			//reader.setSchema(schema);
		}
	}

	public void addWriter(String serviceId, JAXBWriter writer) {
		synchronized (writerCache) {
			writerCache.put(serviceId, writer);
			//Schema schema = getSchema(serviceId);
			//writer.setSchema(schema);
		}
	}
	
	public void addSchema(String schemaFileName) throws Exception {
		synchronized (fileNameCache) {
			schemaFileName = NameUtil.normalizePathToUnix(schemaFileName);
			fileNameCache.add(schemaFileName);

//			System.out.println("$$$$$$$ "+schemaFileName);
//			System.out.flush();
			
//			Source source = sourceCache.get(schemaFileName);
//			if (source == null) {
//				source = getSchemaSource(schemaFileName);
//				sourceCache.put(schemaFileName, source);
//				schemaSources = null;
//			}
		}
	}

	public void addSchema(String schemaFileName, Class<?> classObject) throws Exception {
		synchronized (classesToLoad) {
			Package packageObject = classObject.getPackage();
			String packageName = packageObject.getName();
			if (!packagesToLoad.contains(packageName)) {
				packagesToLoad.add(packageName);
				classesToLoad.add(classObject);
			}
			addSchema(schemaFileName);
		}
	}
	
	public Schema getSchema() throws Exception {
		return getSchema(getSchemaSources());
	}

	public Schema getSchema(String serviceId) throws Exception {
		synchronized (schemaCache) {
			Source[] sources = getSchemaSources();
			Schema schema = schemaCache.get(sources);
			if (schema == null) {
				schema = getSchema(sources);
				schemaCache.put(sources, schema);
			}
			return schema;
		}
	}
	
	public Schema getSchema(Source[] sources) {
		Schema schema = schemaCache.get(sources);
		if (schema != null)
			return schema;
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			schemaFactory.setErrorHandler(createErrorHandler());
			schemaFactory.setResourceResolver(createResourceResolver());
			schema = schemaFactory.newSchema(sources);
			schemaCache.put(sources, schema);
			return schema;
		} catch (SAXException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public void addObjectFactory(Class<?> classObject) throws Exception {
		synchronized (classesToLoad) {
			Package packageObject = classObject.getPackage();
			packagesToLoad.add(packageObject.getName());
			classesToLoad.add(classObject);
		}
	}
	
	private LSResourceResolver createResourceResolver() {
		return new LSResourceResolver() {
			protected Log log = LogFactory.getLog(getClass());
			private DOMImplementationLS domImplementationLS;
			
			@Override
			public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
				//log.info("type="+type+", namespaceURI="+namespaceURI+", publicId="+publicId+", systemId="+systemId+", baseURI="+baseURI);
				System.setProperty(DOMImplementationRegistry.PROPERTY, "org.apache.xerces.dom.DOMImplementationSourceImpl");
				try {
					DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
					domImplementationLS = (DOMImplementationLS) registry.getDOMImplementation("LS");
					LSInput lsInput = domImplementationLS.createLSInput();
					ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
					InputStream is = null;
					if (systemId.startsWith(".."))
						systemId = systemId.substring(3);
					is = classLoader.getResourceAsStream("model/" + systemId);
					if (is == null)
						is = classLoader.getResourceAsStream("schema/nam/" + systemId);
					lsInput.setByteStream(is);
					lsInput.setSystemId(systemId);
					return lsInput;
				} catch (ClassCastException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
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
				//exception.printStackTrace();
				log.error(exception);
			}

			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				log.fatal(exception);
			}
		};
	}

	public Source[] getSchemaSources() throws Exception {
		synchronized (fileNameCache) {
			Source[] array = new Source[fileNameCache.size()]; 
			Iterator<String> iterator = fileNameCache.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				String schemaFileName = iterator.next();
				Source source = getSchemaSource(schemaFileName);
				array[i] = source;
			}
			return array;
			
//			if (schemaSources != null) {
//				resetSchemaSources();
//				return schemaSources;
//			}
//			Collection<Source> sources = sourceCache.values();
//			Source[] array = new Source[sources.size()]; 
//			schemaSources = sources.toArray(array);
//			return schemaSources;
		}
	}

//	protected void resetSchemaSources() throws Exception {
//		for (int i=0; i < schemaSources.length; i++) {
//			StreamSource streamSource = (StreamSource) schemaSources[i];
//			streamSource.getInputStream().reset();
//		}
//	}

	protected Source getSchemaSource(String filename) throws Exception {
		if (filename.startsWith("file:")) {
			URL resource = new URL(filename);
			FileInputStream stream = new FileInputStream(resource.getFile());
			StreamSource source = new StreamSource(stream);
			return source;
			
		} else {
			if (filename.startsWith("/"))
				filename = filename.substring(1);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL resource1 = getClass().getResource(filename);
			URL resource2 = classLoader.getResource(filename);
			InputStream stream = classLoader.getResourceAsStream(filename);
			StreamSource source = new StreamSource(stream);
			return source;
		}
	}
	
	public Set<String> getPackagesToLoad() {
		return packagesToLoad;
	}
	
	public List<Class<?>> getClassesToLoad() {
		return classesToLoad;
	}
	

}
