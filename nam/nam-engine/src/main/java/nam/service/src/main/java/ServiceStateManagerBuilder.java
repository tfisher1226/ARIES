package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;

import nam.model.Cache;
import nam.model.Service;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.SourceType;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;


/**
 * Builds a Service State Manager {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceStateManagerBuilder extends AbstractBeanBuilder {

	public ServiceStateManagerBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelClass build(Service service) throws Exception {
		String namespace = service.getNamespace();
		//setPackageName(NameUtil.getPackageFromNamespace(namespace));
		setPackageName(context.getPackageName(service));
		setRootName(NameUtil.capName(service.getName()));
		setBeanName(service.getName());
		setClassName(rootName+"StateManager");
//		if (!StringUtils.isEmpty(service.getClassName()))
//			setClassName(service.getClassName());
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, NameUtil.uncapName(className)));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(NameUtil.uncapName(className));
		modelClass.setParentClassName("AbstractStateManager<"+rootName+"State>");
		modelClass.addImplementedInterface(className+"MBean");
		initializeClass(modelClass, service);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Service service) throws Exception {
		initializeImportedClasses(modelClass);
		initializeClassConstructors(modelClass, service);
		initializeInstanceFields(modelClass, service);
		initializeInstanceMethods(modelClass, service);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("common.tx.state.AbstractStateManager");
		modelClass.addImportedClass("org.aries.util.MBeanUtil");
	}

	protected void initializeClassConstructors(ModelClass modelClass, Service service) throws Exception {
		String serviceName = NameUtil.capName(service.getName());
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		String mbeanName = NameUtil.splitStringUsingUnderscores(rootName) + "_MBEAN_NAME";
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"MBeanUtil.registerMBean(this, "+mbeanName.toUpperCase()+");"
				//"setLatestStateFilename("+serviceName+"State.LATEST_STATE_FILENAME);",
				//"setShadowStateFilename("+serviceName+"State.SHADOW_STATE_FILENAME);"
		});
		constructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(constructor);
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Service service) throws Exception {
		String className = NameUtil.capName(service.getName()) + "StateManager";
		CodeUtil.addStaticLoggerField(modelClass, className);
		ModelAttribute processorAttribute = createStateProcessorAttribute(service);
		modelClass.addInstanceAttribute(processorAttribute);
	}

	public ModelAttribute createStateProcessorAttribute(Service service) {
		//String packageName = NameUtil.getPackageFromNamespace(service.getNamespace());
		String packageName = context.getPackageName(service);
		String className = NameUtil.capName(service.getName()) + "StateProcessor";
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName("stateProcessor");
		attribute.setDefault("new "+className+"();");
		//attribute.setGenerateGetter(true);
		//attribute.setGenerateSetter(true);
		return attribute;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Service service) throws Exception {
		createMethod_CreateState(modelClass, service);
		createMethod_ResetState(modelClass, service);
		createMethod_UpdateState(modelClass, service);
		Collection<Cache> cacheUnits = ServiceUtil.getCacheUnitReferences(service);
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			createMethods_DataAccess(modelClass, cacheUnit, SourceType.CurrentState);
			createMethods_DataAccess(modelClass, cacheUnit, SourceType.PendingState);
			createMethods_DataAccess(modelClass, cacheUnit, SourceType.PreparedState);
		}
	}
	
	protected void createMethod_CreateState(ModelClass modelClass, Service service) throws Exception {
		String packageName = context.getPackageName(service);
		//String packageName = NameUtil.getPackageFromNamespace(service.getNamespace());
		String serviceName = NameUtil.capName(service.getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(packageName+"."+serviceName+"State");
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"return new "+serviceName+"State();"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ResetState(ModelClass modelClass, Service service) throws Exception {
		String packageName = context.getPackageName(service);
		//String packageName = NameUtil.getPackageFromNamespace(service.getNamespace());
		String className = NameUtil.capName(service.getName()) + "State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("resetState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(packageName+"."+className);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				className+" state = createState();",
				"return state;"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_UpdateState(ModelClass modelClass, Service service) throws Exception {
		String packageName = context.getPackageName(service);
		//String packageName = NameUtil.getPackageFromNamespace(service.getNamespace());
		String className = NameUtil.capName(service.getName()) + "State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("updateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"updateState(stateProcessor);"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	
	
//	protected void createMethods_DataAccess(ModelClass modelClass, Service service) throws Exception {
//		Cache cache = service.getCache();
//		if (cache != null) {
//			if (!StringUtils.isEmpty(cache.getRef()))
//				cache = ModuleUtil.getCache(context.getModule(), cache.getRef());
//			DataItems dataItems = cache.getDataItems();
//			//warn if no items exist
//			if (dataItems == null) {
//				log.warn("No items found in cache: "+cache.getName());
//			} else {
//				List<ListItem> listItems = dataItems.getListItems();
//				Iterator<ListItem> iterator = listItems.iterator();
//				while (iterator.hasNext()) {
//					ListItem listItem = (ListItem) iterator.next();
//					createMethod_DataAccess(modelClass, service, listItem);
//				}
//			}
//		}
//	}

//	protected <T extends Item> void createMethod_DataAccess(ModelClass modelClass, Service service, T item) throws Exception {
//		String type = item.getType();
//		String packageName = getPackageNameFromXSDType(type);
//		String className = getClassNameFromXSDType(type);
//
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setName("get"+className);
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		
//		if (item instanceof MapItem) {
//			String key = ((MapItem) item).getKey();
//			String keyPackageName = getPackageNameFromXSDType(key);
//			String keyClassName = getClassNameFromXSDType(key);
//			modelClass.addImportedClass("java.util.Map");
//			modelClass.addImportedClass(packageName+"."+className);
//			modelClass.addImportedClass(keyPackageName+"."+keyClassName);
//			modelOperation.setReturnType("Map<"+keyClassName+", "+className+">");
//			
//		} else if (item instanceof ListItem) {
//			modelClass.addImportedClass("java.util.Map");
//			modelClass.addImportedClass(packageName+"."+className);
//			modelOperation.setReturnType("List<"+className+">");
//
//		} else if (item instanceof SetItem) {
//			modelClass.addImportedClass("java.util.Map");
//			modelClass.addImportedClass(packageName+"."+className);
//			modelOperation.setReturnType("Set<"+className+">");
//			
//		} else if (item instanceof Item) {
//			modelClass.addImportedClass(packageName+"."+className);
//			modelOperation.setReturnType(className);
//			
//		}
//
//		String sourceCode = CodeUtil.createMethodSource(new String[] {
//				"return currentState.get"+className+"();"
//		});
//		modelOperation.addInitialSource(sourceCode);
//		modelClass.addInstanceOperation(modelOperation);
//	}
//	
//	protected void createMethod_DataAccess(ModelClass modelClass, Service service, ListItem listItem) throws Exception {
//		String packageName = NameUtil.getPackageFromNamespace(service.getNamespace());
//		String itemName = NameUtil.capName(listItem.getName());
//		String itemType = NameUtil.capName(listItem.getType());
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setName("get"+itemName);
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setReturnType(packageName+".List<"+itemType+">");
//		String sourceCode = CodeUtil.createMethodSource(new String[] {
//				"return currentState.get"+itemName+"();"
//		});
//		modelOperation.addInitialSource(sourceCode);
//		modelClass.addInstanceOperation(modelOperation);
//	}
//
//	protected void createMethod_DataAccess(ModelClass modelClass, Service service, MapItem mapItem) throws Exception {
//		String packageName = NameUtil.getPackageFromNamespace(service.getNamespace());
//		String itemName = NameUtil.capName(mapItem.getName());
//		String itemKey = NameUtil.capName(mapItem.getKey());
//		String itemType = NameUtil.capName(mapItem.getValue());
//		ModelOperation modelOperation = new ModelOperation();
//		modelOperation.setName("get"+itemName);
//		modelOperation.setModifiers(Modifier.PUBLIC);
//		modelOperation.setReturnType(packageName+".Map<"+itemKey+", "+itemType+">");
//		String sourceCode = CodeUtil.createMethodSource(new String[] {
//				"return currentState.get"+itemName+"();"
//		});
//		modelOperation.addInitialSource(sourceCode);
//		modelClass.addInstanceOperation(modelOperation);
//	}

}
