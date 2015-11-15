package aries.generation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.Unmarshaller.Listener;

import nam.model.Field;
import nam.ui.View;

import org.aries.Assert;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBReaderImpl;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jaxb.JAXBWriterImpl;

import aries.generation.engine.GenerationContext;


public class AriesModelParser {

	private static Map<String, Serializable> modelCache = new HashMap<String, Serializable>();
	
	private GenerationContext context;

	private JAXBReader jaxbReader;

	private JAXBWriter jaxbWriter;
	
	private Object mutex;
	

	public AriesModelParser(GenerationContext context) throws Exception {
		this.context = context;
		this.mutex = new Object();
		createJAXBContext();
	}

	public void createJAXBContext() throws Exception {
		synchronized (mutex) {
			jaxbReader = new JAXBReaderImpl();
			jaxbWriter = new JAXBWriterImpl();
	
			JAXBSessionCache jaxbSessionCache = new JAXBSessionCache("default");
			jaxbSessionCache.addReader("default", jaxbReader);
			jaxbSessionCache.addWriter("default", jaxbWriter);
	
			//jaxbSessionCache.addObjectFactory(org.aries.common.ObjectFactory.class);
			//jaxbSessionCache.addObjectFactory(org.aries.message.ObjectFactory.class);
			//jaxbSessionCache.addObjectFactory(org.aries.validate.ObjectFactory.class);
			//jaxbSessionCache.addObjectFactory(org.aries.nam.model.old.ObjectFactory.class);
			
			//URL resource1 = ResourceUtil.getResource("/schema/aries-common-1.0.xsd");
			//URL resource2 = ResourceUtil.getResource("/schema/aries-message-1.0.xsd");
			//URL resource3 = ResourceUtil.getResource("/schema/aries-validate-1.0.xsd");
			//URL resource4 = ResourceUtil.getResource("/schema/nam-1.0.xsd");
	
			jaxbSessionCache.addSchema("/schema/common/aries-common-1.0.xsd", org.aries.common.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/common/aries-message-1.0.xsd", org.aries.message.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/common/aries-validate-1.0.xsd", org.aries.validate.ObjectFactory.class);
			
			jaxbSessionCache.addSchema("/schema/nam/nam-1.0.xsd", nam.model.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/nam/nam-common-1.0.xsd", nam.model.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/nam/nam-security-1.0.xsd", nam.model.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/nam/nam-execution-1.0.xsd", nam.model.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/nam/nam-operation-1.0.xsd", nam.model.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/nam/nam-messaging-1.0.xsd", nam.model.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/nam/nam-information-1.0.xsd", nam.model.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/nam/nam-persistence-1.0.xsd", nam.model.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/nam/nam-application-1.0.xsd", nam.model.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/nam/nam-workspace-1.0.xsd", nam.model.ObjectFactory.class);
			jaxbSessionCache.addSchema("/schema/nam/nam-view-1.0.xsd", nam.model.ObjectFactory.class);
			
			jaxbReader.setSchema(jaxbSessionCache.getSchema());
			jaxbWriter.setSchema(jaxbSessionCache.getSchema());
			jaxbReader.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
			jaxbWriter.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
			//jaxbWriter.setClasses(jaxbSessionCache.getClassesToLoad());
			jaxbReader.addListener(createJAXBUnmarshalListener());
			jaxbReader.initialize();
			jaxbWriter.initialize();
		}
	}

//	protected void initializeJAXBReader() throws Exception {
//		if (jaxbReader == null) {
//			NamespaceContext namespaceContext = BeanContext.get("org.aries.namespaceContext");
//			if (namespaceContext == null) {
//				namespaceContext = new NamespaceContext();
//				BeanContext.set("org.aries.namespaceContext", namespaceContext);
//			}
//			jaxbReader = namespaceContext.createJAXBReader();
//			jaxbReader.addListener(createJAXBUnmarshalListener());
//			jaxbReader.initialize();
//		}
//	}
	
	protected Listener createJAXBUnmarshalListener() {
		return new Listener() {
			public void afterUnmarshal(Object object1, Object object2) {
//				if (object1 instanceof View)
//					System.out.println();
				if (object1 instanceof Field) {
					Field field = (Field) object1;
					String type = field.getType();
					if (type != null) {
						int indexOf = type.indexOf(":");
						if (indexOf != -1) {
							String prefix = type.substring(0, indexOf);
							String namespaceUri = jaxbReader.getNamespaceURI(prefix);
//							if (prefix.equals("common") && namespaceUri == null)
//								System.out.println();
							if (namespaceUri != null) {
								Assert.notNull(namespaceUri, "Namespace URI not found for prefix: "+prefix);
								context.populateNamespaceUri(prefix, namespaceUri);
							}
						}
					}
				}
			}
		};
	}
	
	public void cleanup() throws Exception {
		jaxbReader = null;
		jaxbWriter = null;
		context = null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getCachedModel(String fileName) throws Exception {
		synchronized (modelCache) {
			if (modelCache.containsKey(fileName))
				return (T) modelCache.get(fileName);
			return null;
		}
	}
	
	public void putCachedModel(String fileName, Serializable model) throws Exception {
		synchronized (modelCache) {
			modelCache.put(fileName, model);
		}
	}

	public <T extends Serializable> T unmarshalFromFileSystem(String fileName) throws Exception {
		return unmarshalFromFileSystem(fileName, true);
	}
	
	public <T extends Serializable> T unmarshalFromFileSystem(String fileName, boolean readFromCache) throws Exception {
		if (readFromCache) {
			T model = getCachedModel(fileName);
			if (model != null)
				return model;
		}
		T model = jaxbReader.unmarshalFromFileSystem(fileName);
		putCachedModel(fileName, model);
		return model;
	}

	public <T extends Serializable> T unmarshalFromClasspath(String fileName) throws Exception {
		return unmarshalFromClasspath(fileName, true);
	}
	
	public <T extends Serializable> T unmarshalFromClasspath(String fileName, boolean readFromCache) throws Exception {
		if (readFromCache) {
			T model = getCachedModel(fileName);
			if (model != null)
				return model;
		}
		T model = jaxbReader.unmarshalFromClasspath(fileName);
		return model;
	}

	public <T extends Serializable> String marshal(T model) throws Exception {
		String xml = jaxbWriter.marshal(model);
		return xml;
	}

}
