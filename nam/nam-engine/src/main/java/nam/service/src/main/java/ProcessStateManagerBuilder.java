package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Cache;
import nam.model.Process;
import nam.model.util.ProcessUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

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
 * Builds a Process State Manager {@link ModelClass} object given a {@link Process} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ProcessStateManagerBuilder extends AbstractBeanBuilder {

	public ProcessStateManagerBuilder(GenerationContext context) {
		super(context);
	}
	
	public ModelClass build(Process process) throws Exception {
		String namespace = ProcessUtil.getNamespace(process);
		//String processName = ProcessUtil.getProcessName(process);
		String packageName = ServiceLayerHelper.getProcessPackageName(process);
		String className = ServiceLayerHelper.getProcessClassName(process) + "StateManager";
		String rootName = ServiceLayerHelper.getProcessRootName(process);
		String beanName = NameUtil.uncapName(className);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, beanName));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setParentClassName("AbstractStateManager<"+rootName+"State>");
		modelClass.addImplementedInterface(className+"MBean");
		initializeClass(modelClass, process);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Process process) throws Exception {
		initializeImportedClasses(modelClass);
		initializeClassConstructors(modelClass, process);
		initializeInstanceFields(modelClass, process);
		initializeInstanceMethods(modelClass, process);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		modelClass.addImportedClass("common.tx.state.AbstractStateManager");
		modelClass.addImportedClass("org.aries.util.MBeanUtil");
	}

	protected void initializeClassConstructors(ModelClass modelClass, Process process) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		String mbeanName = NameUtil.splitStringUsingUnderscores(className) + "_MBEAN_NAME";
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"MBeanUtil.registerMBean(this, "+mbeanName.toUpperCase()+");"
				//"setLatestStateFilename("+serviceName+"State.LATEST_STATE_FILENAME);",
				//"setShadowStateFilename("+serviceName+"State.SHADOW_STATE_FILENAME);"
		});
		constructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(constructor);
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Process process) throws Exception {
		CodeUtil.addStaticLoggerField(modelClass, className);
		ModelAttribute processorAttribute = createStateProcessorAttribute(process);
		modelClass.addInstanceAttribute(processorAttribute);
	}

	public ModelAttribute createStateProcessorAttribute(Process process) throws Exception {
		String packageName = ProjectLevelHelper.getPackageName(ProcessUtil.getNamespace(process));
		String className = rootName + "StateProcessor";
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

	protected void initializeInstanceMethods(ModelClass modelClass, Process process) throws Exception {
		createMethod_CreateState(modelClass, process);
		createMethod_ResetState(modelClass, process);
		createMethod_UpdateState(modelClass, process);
		List<Cache> cacheUnits = process.getCacheUnits();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			createMethods_DataAccess(modelClass, cache, SourceType.SharedCache);
			createMethods_DataAccess(modelClass, cache, SourceType.CurrentState);
			createMethods_DataAccess(modelClass, cache, SourceType.PendingState);
			createMethods_DataAccess(modelClass, cache, SourceType.PreparedState);
		}
	}
	
	protected void createMethod_CreateState(ModelClass modelClass, Process process) throws Exception {
		String packageName = ProjectLevelHelper.getPackageName(ProcessUtil.getNamespace(process));
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(packageName+"."+rootName+"State");
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"return new "+rootName+"State();"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ResetState(ModelClass modelClass, Process process) throws Exception {
		String packageName = ProjectLevelHelper.getPackageName(ProcessUtil.getNamespace(process));
		String className = rootName + "State";
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

	protected void createMethod_UpdateState(ModelClass modelClass, Process process) throws Exception {
		//String packageName = ProcessUtil.getPackageName(process);
		//String className = ProcessUtil.getClassName(process) + "State";
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
