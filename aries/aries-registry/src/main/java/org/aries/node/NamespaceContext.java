package org.aries.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import nam.model.Namespace;
import nam.model.Operation;
import nam.model.Service;
import nam.model.Type;
import nam.model.util.ServiceUtil;

import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBReaderImpl;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jaxb.JAXBWriterImpl;
import org.aries.nam.model.old.NamespaceDescripter;
import org.aries.runtime.BeanContext;
import org.aries.service.registry.ServiceState;
import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;
import org.aries.util.TypeMap;


public class NamespaceContext {

	private String domain;
	
	//private AriesModelParser ariesModelParser;
	
	
	public NamespaceContext(String domain) throws Exception {
		this.domain = domain;
		initialize();
	}

	private JAXBSessionCache getJAXBSessionCache() {
		JAXBSessionCache jaxbSessionCache = BeanContext.get(domain + ".jaxbSessionCache");
		return jaxbSessionCache;
	}

	public synchronized void initialize() throws Exception {
		JAXBSessionCache jaxbSessionCache = getJAXBSessionCache();
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
	}
	
	public synchronized void initialize(ServiceState serviceState) throws Exception {
		JAXBSessionCache jaxbSessionCache = getJAXBSessionCache();
		String serviceId = serviceState.getServiceId();
		JAXBReader jaxbReader = createJAXBReader();
		JAXBWriter jaxbWriter = createJAXBWriter();
		jaxbSessionCache.addReader(serviceId, jaxbReader);
		jaxbSessionCache.addWriter(serviceId, jaxbWriter);
		addClassesFromNamespaceDescripters(serviceState.getNamespaceDescripters(), jaxbReader, jaxbWriter);
		addBaseClasses(jaxbReader, jaxbWriter);
		jaxbReader.initialize();
		jaxbWriter.initialize();
	}

	public void initialize(Service service, List<Namespace> namespaces) throws Exception {
		synchronized (NamespaceContext.class) {
			JAXBReader jaxbReader = createJAXBReader();
			JAXBWriter jaxbWriter = createJAXBWriter();
	
			//TODO eventually use a more complete serviceId
			String serviceId = service.getDomain() + "." + service.getName();
			JAXBSessionCache jaxbSessionCache = getJAXBSessionCache();
			jaxbSessionCache.addReader(serviceId, jaxbReader);
			jaxbSessionCache.addWriter(serviceId, jaxbWriter);
			
			List<Operation> operations = ServiceUtil.getOperations(service);
			Iterator<Operation> iterator = operations.iterator();
			while (iterator.hasNext()) {
				Operation operation = iterator.next();
				serviceId = service.getDomain() + "." + service.getName() + "." + operation.getName();
				jaxbSessionCache.addReader(serviceId, jaxbReader);
				jaxbSessionCache.addWriter(serviceId, jaxbWriter);
			}
			
			addClassesFromNamespaces(namespaces, jaxbReader, jaxbWriter);
			addBaseClasses(jaxbReader, jaxbWriter);
			jaxbReader.initialize();
			jaxbWriter.initialize();
		}
	}
	
	protected void addBaseClasses(JAXBReader reader, JAXBWriter writer) throws ClassNotFoundException {
		reader.getClasses().add(ArrayList.class);
		writer.getClasses().add(HashSet.class);
		writer.getClasses().add(HashMap.class);
	}
	
	protected void addClassesFromNamespaceDescripters(List<NamespaceDescripter> namespaces, JAXBReader reader, JAXBWriter writer) throws ClassNotFoundException {
		Iterator<NamespaceDescripter> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			NamespaceDescripter namespaceDescripter = iterator.next();
			List<String> types = namespaceDescripter.getTypes();
			Iterator<String> typeIterator = types.iterator();
			while (typeIterator.hasNext()) {
				String typeName = typeIterator.next();
				String className = NameUtil.getClassNameFromType(typeName);
				String packageName = NameUtil.getPackageNameFromType(typeName);
				Class<?> classObject = ClassUtil.loadClass(packageName+"."+className);
				TypeMap.INSTANCE.addType(typeName, namespaceDescripter.getUri(), classObject);
				//TODO put these out:
				reader.getClasses().add(classObject);
				writer.getClasses().add(classObject);
			}
		}
	}
	
	protected void addClassesFromNamespaces(List<Namespace> namespaces, JAXBReader reader, JAXBWriter writer) throws ClassNotFoundException {
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			List<Serializable> types = namespace.getTypesAndEnumerationsAndElements();
			Iterator<Serializable> typeIterator = types.iterator();
			while (typeIterator.hasNext()) {
				Serializable object = typeIterator.next();
				if (object instanceof Type) {
					Type type = (Type) object;
					String typeName = type.getType();
					String className = NameUtil.getClassNameFromType(typeName);
					String packageName = NameUtil.getPackageNameFromType(typeName);
//					if ("bookshop2.seller.Advertisement".equals(packageName+"."+className))
//						System.out.println();
					Class<?> classObject = ClassUtil.loadClass(packageName+"."+className);
					TypeMap.INSTANCE.addType(typeName, namespace.getUri(), classObject);
					//TODO put these out:
					reader.getClasses().add(classObject);
					writer.getClasses().add(classObject);
				}
			}
		}
	}
	
//	public JAXBReader createJAXBReader(Collection<Class<?>> classes, Schema schema) throws Exception {
//		JAXBReader reader = createJAXBReader();
//		reader.setClasses(classes);
//		reader.setSchema(schema);
//		return reader;
//	}
//
//	public JAXBWriter createJAXBWriter(Collection<Class<?>> classes, Schema schema) throws Exception {
//		JAXBWriter writer = createJAXBWriter();
//		writer.setClasses(classes);
//		writer.setSchema(schema);
//		return writer;
//	}

	protected JAXBReader createJAXBReader() throws Exception {
		JAXBSessionCache jaxbSessionCache = getJAXBSessionCache();
		JAXBReader jaxbReader = new JAXBReaderImpl();
		jaxbReader.setSchema(jaxbSessionCache.getSchema());
		jaxbReader.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		//jaxbReader.setClasses(jaxbSessionCache.getClassesToLoad());
		return jaxbReader;
	}

	protected JAXBWriter createJAXBWriter() throws Exception {
		JAXBSessionCache jaxbSessionCache = getJAXBSessionCache();
		JAXBWriter jaxbWriter = new JAXBWriterImpl();
		jaxbWriter.setSchema(jaxbSessionCache.getSchema());
		jaxbWriter.setPackagesToLoad(jaxbSessionCache.getPackagesToLoad());
		//jaxbWriter.setClasses(jaxbSessionCache.getClassesToLoad());
		return jaxbWriter;
	}

//	protected Collection<Class<?>> getClassesForContext(List<Namespace> namespaces) {
//		Collection<Class<?>> typeClasses = TypeMap.INSTANCE.getClassesAsSet();
//		List<Class<?>> classes = new ArrayList<Class<?>>();
//		classes.addAll(typeClasses);
//		//TODO get these from a configurable list:
//		classes.add(ArrayList.class);
//		classes.add(LinkedList.class);
//		classes.add(HashSet.class);
//		classes.add(HashMap.class);
//		classes.add(LinkedHashMap.class);
//		classes.add(ConcurrentHashMap.class);
//		
//		Iterator<Namespace> iterator = namespaces.iterator();
//		while (iterator.hasNext()) {
//			Namespace namespace = iterator.next();
//			List<Type> types = NamespaceUtil.getTypes(namespace);
//			Iterator<Type> typeIterator = types.iterator();
//			while (typeIterator.hasNext()) {
//				Type type = typeIterator.next();
//				Assert.notNull(type.getClass(), "Class is null for type: "+type.getName()+"["+type.getType()+"]");
//				classes.add(type.getClass());
//			}
//		}
//		return typeClasses;
//	}
	
//	protected Class<?>[] getClassesForContext() {
//		Collection<Class<?>> typeClasses = TypeMap.INSTANCE.getClassesAsSet();
//		List<Class<?>> classes = new ArrayList<Class<?>>();
//		classes.addAll(typeClasses);
//		classes.add(ArrayList.class);
//		classes.add(LinkedList.class);
//		classes.add(HashSet.class);
//		classes.add(HashMap.class);
//		Class<?>[] array = classes.toArray(new Class<?>[classes.size()]);
//		return array;
//	}
	
}
