package nam.service;

import java.lang.reflect.Modifier;

import nam.data.DataLayerHelper;
import nam.model.Cache;
import nam.model.Process;
import nam.model.Service;
import nam.model.Unit;

import org.aries.util.NameUtil;

import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelReference;


public class ServiceLayerFactory {


	/*
	 * More ARIES specific operations and structures
	 */
	
	public static void addReference_ServiceHandler(ModelClass modelClass, Service service) {
		modelClass.addInstanceReference(createReference_ServiceHandler(modelClass, service));
		modelClass.addImportedClass("javax.inject.Inject");
	}

	public static ModelReference createReference_ServiceHandler(ModelClass modelClass, Service service) {
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String handlerClassName = service.getInterfaceName() + "Handler";
		String handlerInstanceName = NameUtil.uncapName(handlerClassName);
		modelClass.addImportedClass(packageName + "." + handlerClassName);
		
		ModelReference reference = new ModelReference();
		reference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		reference.setModifiers(Modifier.PRIVATE);
		reference.setClassName(handlerClassName);
		reference.setName(handlerInstanceName);
		reference.setGenerateGetter(false);
		reference.setGenerateSetter(false);
		return reference;
	}
	
	public static void addReference_ServiceInterceptor(ModelClass modelClass, Service service) {
		ModelReference modelReference = createReference_ServiceInterceptor(modelClass, service);
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		modelClass.addInstanceReference(modelReference);
		modelClass.addImportedClass("javax.inject.Inject");
	}

	public static ModelReference createReference_ServiceInterceptor(ModelClass modelClass, Service service) {
		String packageName = ServiceLayerHelper.getServicePackageName(service);
		String handlerClassName = service.getInterfaceName() + "Interceptor";
		String handlerInstanceName = NameUtil.uncapName(handlerClassName);
		modelClass.addImportedClass(packageName + "." + handlerClassName);
		
		ModelReference reference = new ModelReference();
		reference.setModifiers(Modifier.PRIVATE);
		reference.setClassName(handlerClassName);
		reference.setName(handlerInstanceName);
		reference.setGenerateGetter(false);
		reference.setGenerateSetter(false);
		return reference;
	}
	
	public static void addReference_StatefulProcess(ModelClass modelClass, Service service) {
		modelClass.addInstanceReference(createReference_StatefulProcess(modelClass, service));
		modelClass.addImportedClass("javax.inject.Inject");
	}

	public static ModelReference createReference_StatefulProcess(ModelClass modelClass, Service service) {
		Process process = service.getProcess();
		String packageName = ServiceLayerHelper.getProcessPackageName(process);
		String className = ServiceLayerHelper.getProcessClassName(process);
		String instanceName = ServiceLayerHelper.getProcessInstanceName(process);
		modelClass.addImportedClass(packageName + "." + className);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(instanceName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		return modelReference;
	}

	public static void addReference_StatefulContext(ModelClass modelClass, Service service) {
		addReference_StatefulContext(modelClass, service.getProcess());
	}
	
	public static void addReference_StatefulContext(ModelClass modelClass, Process process) {
		ModelReference modelReference = createReference_StatefulContext(modelClass, process);
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		modelClass.addInstanceReference(modelReference);
		modelClass.addImportedClass("javax.inject.Inject");
	}
	
	public static ModelReference createReference_StatefulContext(ModelClass modelClass, Process process) {
		String packageName = ServiceLayerHelper.getProcessPackageName(process);
		String className = ServiceLayerHelper.getProcessContextClassName(process);
		String instanceName = ServiceLayerHelper.getProcessContextInstanceName(process);
		modelClass.addImportedClass(packageName + "." + className);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(instanceName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	public static void addReference_Event(ModelClass modelClass, Process process, String eventName) {
		ModelReference modelReference = createReference_Event(modelClass, process, eventName);
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		modelClass.addInstanceReference(modelReference);
	}
	
	public static ModelReference createReference_Event(ModelClass modelClass, Process process, String eventName) {
		String packageName = ServiceLayerHelper.getExecutorPackageName(process);
		if (!eventName.endsWith("Event"))
			eventName += "Event";
		String className = eventName;
		String instanceName = NameUtil.uncapName(className);
		//modelClass.addImportedClass(packageName + "." + className);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(instanceName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	public static void addReference_EventHandler(ModelClass modelClass, Process process, String eventName) {
		modelClass.addInstanceReference(createReference_EventHandler(modelClass, process, eventName));
	}
	
	public static ModelReference createReference_EventHandler(ModelClass modelClass, Process process, String eventName) {
		String packageName = ServiceLayerHelper.getExecutorPackageName(process);
		String className = ServiceLayerHelper.getExecutorClassName(eventName);
		String beanName = ServiceLayerHelper.getExecutorInstanceName(eventName);
		modelClass.addImportedClass(packageName + "." + className);
		modelClass.addImportedClass("javax.inject.Inject");

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(beanName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		return modelReference;
	}

	public static void addReference_EventMulticaster(ModelClass modelClass, Process process) {
		modelClass.addInstanceReference(createReference_EventMulticaster(modelClass, process));
	}
	
	public static ModelReference createReference_EventMulticaster(ModelClass modelClass, Process process) {
		String processName = ServiceLayerHelper.getProcessClassName(process);
		processName = processName.substring(0, processName.length() - 7);

		String packageName = ServiceLayerHelper.getProcessPackageName(process);
		String className = processName + "EventMulticaster";
		String instanceName = NameUtil.uncapName(className);
		modelClass.addImportedClass(packageName + "." + className);
		modelClass.addImportedClass("javax.inject.Inject");

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(instanceName);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		return modelReference;
	}
	
	public static void addReference_CacheUnit(ModelClass modelClass, String namespace, Cache cacheUnit) throws Exception {
		modelClass.addInstanceReference(createReference_CacheUnit(modelClass, namespace, cacheUnit));
		modelClass.addImportedClass("javax.inject.Inject");
	}
	
	public static ModelReference createReference_CacheUnit(ModelClass modelClass, String namespace, Cache cacheUnit) throws Exception {
		String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cacheUnit);
		String className = DataLayerHelper.getCacheUnitInterfaceName(cacheUnit);
		String beanName = DataLayerHelper.getCacheUnitNameUncapped(cacheUnit);
		String typeName = DataLayerHelper.getCacheUnitTypeName(namespace, cacheUnit);
		modelClass.addImportedClass(packageName + "." + className);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(beanName);
		modelReference.setType(typeName);
		modelReference.setGenerateGetter(true);
		modelReference.setGenerateSetter(true);
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		return modelReference;
	}
	
	public static void addReference_DataUnit(ModelClass modelClass, String namespace, Unit unit) throws Exception {
		modelClass.addInstanceReference(createReference_DataUnit(modelClass, namespace, unit));
		modelClass.addImportedClass("javax.inject.Inject");
	}
	
	public static ModelReference createReference_DataUnit(ModelClass modelClass, String namespace, Unit unit) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(namespace, unit);
		String className = DataLayerHelper.getPersistenceUnitClassName(unit);
		String beanName = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String typeName = DataLayerHelper.getPersistenceUnitTypeName(namespace, unit);
		modelClass.addImportedClass(packageName + "." + className);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(className);
		modelReference.setName(beanName);
		modelReference.setType(typeName);
		modelReference.setGenerateGetter(true);
		modelReference.setGenerateSetter(true);
		modelReference.addAnnotation(AnnotationUtil.createInjectAnnotation());
		return modelReference;
	}
	
}
