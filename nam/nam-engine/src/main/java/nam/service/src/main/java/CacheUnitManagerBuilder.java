package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Cache;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Process;
import nam.model.Type;
import nam.model.util.CacheUtil;
import nam.model.util.ElementUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerFactory;
import nam.service.ServiceLayerHelper;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


/**
 * Builds a Cache State Manager {@link ModelClass} object given a {@link Cache} Specification as input;
 * 
 * <h3>Properties</h3>
 * The following properties can be used to configure execution of this builder: 
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * <h3>Dependencies</h3>
 * Execution of this builder must come after the following builders: 
 * <ul>
 * <li>ProcessClassBuilder</li>
 * </ul>
 * 
 * @author tfisher
 */
public class CacheUnitManagerBuilder extends AbstractDataUnitManagerBuilder {

	protected List<ModelOperation> logStateOperations;
	
	
	public CacheUnitManagerBuilder(GenerationContext context) {
		super(context);
	}
	
	protected boolean isDataUnitManager() {
		return true;
	}
	
	public List<ModelClass> buildClasses(Process process) throws Exception {
		return buildClasses(process.getNamespace(), process.getCacheUnits());
	}
	
	public List<ModelClass> buildClasses(String namespace, List<Cache> cacheUnits) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			context.setCache(cacheUnit);
			modelClasses.add(buildClass(namespace, cacheUnit));
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(String namespace, Cache cache) throws Exception {
		String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cache);
		String stateClassName = DataLayerHelper.getCacheUnitInterfaceName(cache) + "State";
		String managerClassName = DataLayerHelper.getCacheUnitInterfaceName(cache) + "Manager";
		String rootName = CacheUtil.getRootName(cache);
		String beanName = NameUtil.uncapName(CacheUtil.getCacheName(cache));
		String typeName = CacheUtil.getType(cache);

		setBeanName(beanName);
		setRootName(rootName);
		setPackageName(packageName);
		setClassName(managerClassName);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(typeName);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(managerClassName);
		modelClass.setName(beanName);
		modelClass.setParentClassName("AbstractStateManager<"+stateClassName+">");
		modelClass.addImplementedInterface(managerClassName+"MBean");
		modelClass.addImportedClass(packageName+"."+managerClassName+"MBean");
		initializeClass(modelClass, cache);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Cache cache) throws Exception {
		this.modelUnit = modelClass;
		initializeImportedClasses(modelClass);
		initializeClassAnnotations(modelClass);
		initializeClassConstructors(modelClass, cache);
		initializeInstanceFields(modelClass, cache);
		initializeInstanceMethods(modelClass, cache);
	}

	protected void initializeClassConstructors(ModelClass modelClass, Cache cache) throws Exception {
		modelClass.addInstanceConstructor(createConstructor(modelClass, cache));
	}
	
	protected ModelConstructor createConstructor(ModelClass modelClass, Cache cache) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		//String mbeanName = NameUtil.splitStringUsingUnderscores(className) + "_MBEAN_NAME";
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				//"MBeanUtil.registerMBean(this, "+mbeanName.toUpperCase()+");"
				//"setLatestStateFilename("+serviceName+"State.LATEST_STATE_FILENAME);",
				//"setShadowStateFilename("+serviceName+"State.SHADOW_STATE_FILENAME);"
		});
		constructor.addInitialSource(sourceCode);
		return constructor;
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Cache cache) throws Exception {
		CodeUtil.addStaticLoggerField(modelClass, className);
		ServiceLayerFactory.addReference_StatefulContext(modelClass, context.getProcess());
		ServiceLayerFactory.addReference_CacheUnit(modelClass, context.getProcess().getNamespace(), cache);
		modelClass.addInstanceAttribute(createStateProcessorAttribute(cache));
	}

	public ModelAttribute createStateProcessorAttribute(Cache cache) throws Exception {
		String packageName = CacheUtil.getPackageName(cache);
		String className = CacheUtil.getClassName(cache) + "Processor";
		String beanName = NameUtil.uncapName(className);
		//String typeName = CacheUtil.getType(cache);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName(beanName);
		//attribute.setValue("new "+className+"()");
		//attribute.setGenerateGetter(true);
		//attribute.setGenerateSetter(true);
		attribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
		return attribute;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Cache cache) throws Exception {
		createMethod_GetName(modelClass, cache);
		createMethod_RegisterWithJMX(modelClass, cache);
		createMethod_UnregisterWithJMX(modelClass, cache);
		createMethod_CreateState(modelClass, cache);
		createMethod_ResetState(modelClass, cache);
		createMethod_UpdateState(modelClass, cache);
		createMethod_SaveState(modelClass, cache);
		createMethod_CommitState(modelClass, cache);
		logStateOperations = new ArrayList<ModelOperation>();
		createMethods_DataAccess(modelClass, cache, SourceType.SharedCache);
		//createMethods_DataAccess(modelClass, cache, SourceType.CurrentState);
		//createMethods_DataAccess(modelClass, cache, SourceType.PendingState);
		//createMethods_DataAccess(modelClass, cache, SourceType.PreparedState);
		modelClass.addInstanceOperations(logStateOperations);
	}
	
	protected void createMethod_GetName(ModelClass modelClass, Cache cache) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getName");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"return \""+modelClass.getClassName()+"\";"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_RegisterWithJMX(ModelClass modelClass, Cache cache) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createPostConstructAnnotation());
		modelOperation.setName("registerWithJMX");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"MBeanUtil.registerMBean(this, MBEAN_NAME);"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_UnregisterWithJMX(ModelClass modelClass, Cache cache) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createPreDestroyAnnotation());
		modelOperation.setName("unregisterWithJMX");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"MBeanUtil.unregisterMBean(MBEAN_NAME);"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_CreateState(ModelClass modelClass, Cache cache) throws Exception {
		String packageName = CacheUtil.getPackageName(cache);
		String className = CacheUtil.getClassName(cache) + "State";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(packageName+"."+className);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"return new "+className+"();"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ResetState(ModelClass modelClass, Cache cache) throws Exception {
		String packageName = CacheUtil.getPackageName(cache);
		String className = CacheUtil.getClassName(cache) + "State";

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

	protected void createMethod_UpdateState(ModelClass modelClass, Cache cache) throws Exception {
		String packageName = CacheUtil.getPackageName(cache);
		String className = CacheUtil.getClassName(cache) + "State";
		String typeName = CacheUtil.getType(cache);
		String beanName = CacheUtil.getCacheName(cache);
		String processorName = DataLayerHelper.getCacheUnitNameUncapped(context.getCache()) + "Processor";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("updateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		Buf buf = new Buf();
		buf.putLine2("//TODO if this fails then we need to mark global TX as rollback only");
		buf.putLine2("updateState("+processorName+");");
//		List<Field> fields = ElementUtil.getFields(cache);
//		Iterator<Field> iterator = fields.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			//String fieldType = field.getType();
//			String fieldName = field.getName();
//			String fieldNameCapped = NameUtil.capName(fieldName);
//			String structure = field.getStructure();
//			//String fieldClassName = TypeUtil.getClassName(fieldType);
//			//String fieldPackageName = TypeUtil.getPackageName(fieldType);
//			if (structure.equals("item"))
//				buf.putLine2(cacheName+".set"+fieldNameCapped+"(stateProcessor.getPending"+fieldNameCapped+"());");
//			else buf.putLine2(cacheName+".addTo"+fieldNameCapped+"(stateProcessor.getPending"+fieldNameCapped+"());");
//		}
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_SaveState(ModelClass modelClass, Cache cache) throws Exception {
		String packageName = CacheUtil.getPackageName(cache);
		String className = CacheUtil.getClassName(cache) + "State";
		String typeName = CacheUtil.getType(cache);
		//String beanName = NameUtil.uncapName(className);
		String beanName = "state";
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("saveState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("boolean");
		
		ModelParameter modelParameter = CodeUtil.createParameter(className, beanName);
		modelOperation.addParameter(modelParameter);
		
		//buf.putLine2(cacheName+" .setUser(bookCacheState.getUser());");
		//buf.putLine2(cacheName+" .addToBookOrders(bookCacheState.getBookOrders());");
		//buf.putLine2(cacheName+" .addToAvailableBooks(bookCacheState.getAvailableBooks());");
		//buf.putLine2(cacheName+" .addToUnavailableBooks(bookCacheState.getUnavailableBooks());");

		Buf buf = new Buf();
		buf.putLine2("try {");

		List<Field> fields = ElementUtil.getFields(cache);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			//String fieldType = field.getType();
			String fieldName = field.getName();
			String fieldNameCapped = NameUtil.capName(fieldName);
			String structure = field.getStructure();
			//String fieldClassName = TypeUtil.getClassName(fieldType);
			//String fieldPackageName = TypeUtil.getPackageName(fieldType);
			if (structure.equals("item")) {
				buf.putLine3(beanName+".set"+fieldNameCapped+"("+beanName+".get"+fieldNameCapped+"());");
			} else if (structure.equals("list")) {
				buf.putLine3(beanName+".addTo"+fieldNameCapped+"("+beanName+".getAll"+fieldNameCapped+"());");
			} else if (structure.equals("set")) {
				buf.putLine3(beanName+".addTo"+fieldNameCapped+"("+beanName+".getAll"+fieldNameCapped+"());");
			} else if (structure.equals("map")) {
				buf.putLine3(beanName+".addTo"+fieldNameCapped+"("+beanName+".getAll"+fieldNameCapped+"AsMap());");
			}
		}
		
		buf.putLine2("	return true;");
		buf.putLine3("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	return false;");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CommitState(ModelClass modelClass, Cache cache) throws Exception {
		String processorName = DataLayerHelper.getCacheUnitNameUncapped(context.getCache()) + "Processor";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("commitState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		Buf buf = new Buf();
		buf.putLine2(processorName+".updateState(currentState);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethods_DataAccess(ModelUnit modelUnit, Cache cache, SourceType sourceType) throws Exception {
		if (cache != null) {
			if (!StringUtils.isEmpty(cache.getRef()))
				cache = ModuleUtil.getCache(context.getModule(), cache.getRef());
			List<Field> fields = ElementUtil.getFields(cache);
			if (fields == null || fields.size() == 0) {
				log.warn("No items found in cache: "+cache.getName());
			//List<Item> items = CacheUtil.getItems(cache);
			//if (items == null || items.size() == 0) {
			//	log.warn("No items found in cache: "+cache.getName());
			} else {
				createMethods_DataAccess(modelUnit, fields, sourceType);
			}
		}
	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		String structure = dataItem.getStructure();

		String contextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
		String processorName = DataLayerHelper.getCacheUnitNameUncapped(context.getCache()) + "Processor";
		//String cacheName = modelUnit.getName();
		String cacheName = getBeanName();

		String type = dataItem.getType();
		String keyType = dataItem.getKey();
		String helperName = ModelLayerHelper.getModelHelperBeanName(type);
		String paramName = TypeUtil.getLocalPart(type);
		String keyParamName = null;
		String keyTypeName = null;
		if (keyType != null) {
			keyParamName = TypeUtil.getLocalPart(keyType);
			keyTypeName = NameUtil.capName(keyParamName);
		}
		String typeName = NameUtil.capName(paramName);
		String fieldName = NameUtil.capName(dataItem.getName());
		String instanceName = NameUtil.uncapName(fieldName);
		String methodName = null;
		
		Buf buf = new Buf();
		switch (methodType) {
		case GetAllAsList: 
			methodName = "getAll"+fieldName;
			buf.putLine2("logState_"+methodName+"();");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState."+methodName+"();"); 
			buf.putLine2("return "+cacheName+"."+methodName+"();");
			createOperation_LogState(methodName);
			break;
			
		case GetAllAsMap: 
			methodName = "getAll"+fieldName+"AsMap";
			buf.putLine2("logState_getAll"+fieldName+"AsMap();");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getAll"+fieldName+"AsMap();");
			buf.putLine2("return "+beanName+".getAll"+fieldName+"AsMap();");
			createOperation_LogState(methodName);
			break;
			
		case GetAsItem: 
			methodName = "get"+fieldName;
			buf.putLine2("logState_get"+fieldName+"();");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.get"+fieldName+"();"); 
			buf.putLine2("return "+cacheName+".get"+fieldName+"();");
			createOperation_LogState(methodName);
			break;
			
		case GetAsItemById: 
			methodName = "getFrom"+fieldName;
			buf.putLine2("logState_getFrom"+fieldName+"("+paramName+"Id);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"("+paramName+"Id);"); 
			buf.putLine2("return "+cacheName+".getFrom"+fieldName+"("+paramName+"Id);");
			createOperation_LogState(methodName, "Long", paramName+"Id");
			break;
			
		case GetAsItemByKey: 
			methodName = "getFrom"+fieldName;
			buf.putLine2("logState_getFrom"+fieldName+"("+paramName+"Key);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"("+paramName+"Key);"); 
			buf.putLine2("return "+cacheName+".getFrom"+fieldName+"("+paramName+"Key);");
			createOperation_LogState(methodName, keyTypeName, paramName+"Key");
			break;
			
		case GetAsList: 
		case GetAsListByIds: 
			methodName = "getFrom"+fieldName;
			buf.putLine2("logState_getFrom"+fieldName+"("+paramName+"Ids);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"("+paramName+"Ids);"); 
			buf.putLine2("return "+cacheName+".getFrom"+fieldName+"("+paramName+"Ids);");
			createOperation_LogState(methodName, "Long", paramName+"Ids", "list");
			break;
			
		case GetAsListByKeys: 
			methodName = "getFrom"+fieldName;
			buf.putLine2("logState_getFrom"+fieldName+"("+paramName+"Keys);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"("+paramName+"Keys);"); 
			buf.putLine2("return "+cacheName+".getFrom"+fieldName+"("+paramName+"Keys);");
			createOperation_LogState(methodName, keyTypeName, paramName+"Keys", "list");
			break;
			
		case GetAsMapByKeys: 
			methodName = "getFrom"+fieldName+"AsMap";
			buf.putLine2("logState_getFrom"+fieldName+"AsMap("+paramName+"Keys);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"AsMap("+paramName+"Keys);"); 
			buf.putLine2("return "+cacheName+".getFrom"+fieldName+"AsMap("+paramName+"Keys);");
			createOperation_LogState(methodName, keyTypeName, paramName+"Keys", "list");
			break;
			
		case GetAsListByCriteria: 
			methodName = "getFrom"+fieldName;
			buf.putLine2("logState_getFrom"+fieldName+"("+paramName+"Criteria);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getFrom"+fieldName+"("+paramName+"Criteria);"); 
			buf.putLine2("return "+cacheName+".getFrom"+fieldName+"("+paramName+"Criteria);");
			createOperation_LogState(methodName, typeName+"Criteria", paramName+"Criteria");
			break;
			
		case GetMatchingAsList: 
			//addImportedClassForHelper(modelUnit, dataItem);
			methodName = "getMatching"+fieldName;
			if (structure.equals("list")) paramName += "List";
			if (structure.equals("set")) paramName += "Set";
			if (structure.equals("map")) paramName += "List";
			buf.putLine2("logState_getMatching"+fieldName+"("+paramName+");");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getMatching"+fieldName+"("+paramName+");"); 
			buf.putLine2("return "+cacheName+".getMatching"+fieldName+"("+paramName+");");
			//buf.putLine2("List<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
			if (structure.equals("set"))
				createOperation_LogState(methodName, typeName, paramName, "set");
			else createOperation_LogState(methodName, typeName, paramName, "list");
			break;
			
		case GetMatchingAsMap: 
			methodName = "getMatching"+fieldName+"AsMap";
			buf.putLine2("logState_getMatching"+fieldName+"AsMap("+paramName+"List);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	return currentState.getMatching"+fieldName+"AsMap("+paramName+"List);"); 
			buf.putLine2("return "+cacheName+".getMatching"+fieldName+"AsMap("+paramName+"List);");
			createOperation_LogState(methodName, typeName, paramName+"List", "list");
			break;
			
		case Set: 
			methodName = "set"+fieldName;
			if (structure.equals("list")) 
				paramName += "List";
			if (structure.equals("set")) 
				paramName += "Set";
			if (structure.equals("map")) 
				paramName += "Map";
			if (structure.equals("collection")) 
				paramName += "Collection";
			buf.putLine2("logState_set"+fieldName+"("+paramName+");");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	"+processorName+".setPending"+fieldName+"("+paramName+");");
			buf.putLine2("else "+cacheName+".set"+fieldName+"("+paramName+");");
			createOperation_LogState(methodName, typeName, keyTypeName, paramName, structure);
			break;
			
		case AddAsItem: 
			methodName = "addTo"+fieldName;
			if (structure.equals("list")) {
			} else if (structure.equals("set")) {
			} else if (structure.equals("map")) {
			}
			buf.putLine2("logState_addTo"+fieldName+"("+paramName+");");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	"+processorName+".addToPending"+fieldName+"("+paramName+");"); 
			buf.putLine2("else "+cacheName+".addTo"+fieldName+"("+paramName+");");
			createOperation_LogState(methodName, typeName, paramName);
			break;
			
		case AddAsList: 
			methodName = "addTo"+fieldName;
			if (structure.equals("list")) {
				buf.putLine2("logState_addTo"+fieldName+"("+paramName+"List);");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	"+processorName+".addToPending"+fieldName+"("+paramName+"List);"); 
				buf.putLine2("else "+cacheName+".addTo"+fieldName+"("+paramName+"List);");
				createOperation_LogState(methodName, typeName, paramName+"List", "list");

			} else if (structure.equals("set")) {
				buf.putLine2("logState_addTo"+fieldName+"("+paramName+"Set);");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	"+processorName+".addToPending"+fieldName+"("+paramName+"Set);"); 
				buf.putLine2("else "+cacheName+".addTo"+fieldName+"("+paramName+"Set);");
				createOperation_LogState(methodName, typeName, paramName+"Set", "set");

			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = "+helperName+".create"+typeName+"Map("+paramName+"List);");
				buf.putLine2("logState_addTo"+fieldName+"("+paramName+"Map);");
				buf.putLine2("addTo"+fieldName+"("+paramName+"Map);");
				createOperation_LogState(methodName, typeName, keyTypeName, paramName+"Map", "map");
			}
			break;
			
		case AddAsMap: 
			methodName = "addTo"+fieldName;
			buf.putLine2("logState_addTo"+fieldName+"("+paramName+"Map);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	"+processorName+".addToPending"+fieldName+"("+paramName+"Map);"); 
			buf.putLine2("else "+cacheName+".addTo"+fieldName+"("+paramName+"Map);");
			createOperation_LogState(methodName, typeName, keyTypeName, paramName+"Map", "map");
			break;
			
		case RemoveAll: 
			methodName = "removeAll"+fieldName;
			buf.putLine2("logState_removeAll"+fieldName+"();");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	"+processorName+".removeAllPending"+fieldName+"();"); 
			buf.putLine2("else "+cacheName+".removeAll"+fieldName+"();");
			createOperation_LogState(methodName);
			break;
			
		case RemoveAsItem:
			methodName = "removeFrom"+fieldName;
			if (structure.equals("list") || structure.equals("set")) {
				buf.putLine2("logState_removeFrom"+fieldName+"("+paramName+");");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	"+processorName+".removeFromPending"+fieldName+"("+paramName+");"); 
				buf.putLine2("else "+cacheName+".removeFrom"+fieldName+"("+paramName+");");
				createOperation_LogState(methodName, typeName, paramName);

			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2(keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("logState_removeFrom"+fieldName+"("+paramName+"Key);");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	"+processorName+".removeFromPending"+fieldName+"("+paramName+"Key);"); 
				buf.putLine2("else "+cacheName+".removeFrom"+fieldName+"("+paramName+"Key);");
				createOperation_LogState(methodName, keyTypeName, paramName+"Key");
			}
			break;
			
		case RemoveAsItemByKey:
			methodName = "removeFrom"+fieldName;
			buf.putLine2("logState_removeFrom"+fieldName+"("+paramName+"Key);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	"+processorName+".removeFromPending"+fieldName+"("+paramName+"Key);"); 
			buf.putLine2("else "+cacheName+".removeFrom"+fieldName+"("+paramName+"Key);");
			createOperation_LogState(methodName, keyTypeName, paramName+"Key");
			break;
			
		case RemoveAsItemById:
			methodName = "removeFrom"+fieldName;
			buf.putLine2("logState_removeFrom"+fieldName+"("+paramName+"Id);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	"+processorName+".removeFromPending"+fieldName+"("+paramName+"Id);"); 
			buf.putLine2("else "+cacheName+".removeFrom"+fieldName+"("+paramName+"Id);");
			createOperation_LogState(methodName, "Long", paramName+"Id");
			break;
			
		case RemoveAsListByKeys:
			methodName = "removeFrom"+fieldName;
			buf.putLine2("logState_removeFrom"+fieldName+"("+paramName+"Keys);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	"+processorName+".removeFromPending"+fieldName+"("+paramName+"Keys);"); 
			buf.putLine2("else "+cacheName+".removeFrom"+fieldName+"("+paramName+"Keys);");
			createOperation_LogState(methodName, keyTypeName, paramName+"Keys", "list");
			break;
			
		case RemoveAsList:
			methodName = "removeFrom"+fieldName;
			if (structure.equals("list")) {
				buf.putLine2("logState_removeFrom"+fieldName+"("+paramName+"List);");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	"+processorName+".removeFromPending"+fieldName+"("+paramName+"List);"); 
				buf.putLine2("else "+cacheName+".removeFrom"+fieldName+"("+paramName+"List);");
				createOperation_LogState(methodName, typeName, paramName+"List", "list");

			} else if (structure.equals("set")) {
				buf.putLine2("logState_removeFrom"+fieldName+"("+paramName+"Set);");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	"+processorName+".removeFromPending"+fieldName+"("+paramName+"Set);"); 
				buf.putLine2("else "+cacheName+".removeFrom"+fieldName+"("+paramName+"Set);");
				createOperation_LogState(methodName, typeName, paramName+"Set", "set");

			} else if (structure.equals("map")) {
				//addImportedClassForHelper(modelUnit, dataItem);
				//buf.putLine2("List<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
				buf.putLine2("logState_removeFrom"+fieldName+"("+paramName+"List);");
				buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
				buf.putLine2("	"+processorName+".removeFromPending"+fieldName+"("+paramName+"List);"); 
				buf.putLine2("else "+cacheName+".removeFrom"+fieldName+"("+paramName+"List);");
				createOperation_LogState(methodName, typeName, paramName+"List", "list");
			}
			break;
			
		case RemoveAsListByCriteria:
			methodName = "removeFrom"+fieldName;
			buf.putLine2("logState_removeFrom"+fieldName+"("+paramName+"Criteria);");
			buf.putLine2("if ("+contextName+".isGlobalTransactionActive())");
			buf.putLine2("	"+processorName+".removeFromPending"+fieldName+"("+paramName+"Criteria);"); 
			buf.putLine2("else "+cacheName+".removeFrom"+fieldName+"("+paramName+"Criteria);");
			createOperation_LogState(methodName, typeName+"Criteria", paramName+"Criteria");
			break;
		}
		return buf.get();
	}

	protected ModelOperation createOperation_LogState(String methodName) {
		return createOperation_LogState(methodName, null, null);
	}

	protected ModelOperation createOperation_LogState(String methodName, String paramClass, String paramName) {
		return createOperation_LogState(methodName, paramClass, null, paramName, "item");
	}
	
	protected ModelOperation createOperation_LogState(String methodName, String paramClass, String paramName, String paramStructure) {
		return createOperation_LogState(methodName, paramClass, null, paramName, paramStructure);
	}
	
	protected ModelOperation createOperation_LogState(String methodName, String paramClass, String paramKeyClass, String paramName, String paramStructure) {
		String processContextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());

//		String type = dataItem.getType();
//		String keyType = dataItem.getKey();
//		String typeName = TypeUtil.getLocalPart(type);
//		String typeClassName = TypeUtil.getClassName(type);
//		
//		String keyTypeName = null;
//		String keyTypeClassName = null;
//		if (keyType != null) {
//			keyTypeName = TypeUtil.getLocalPart(keyType);
//			keyTypeClassName = TypeUtil.getClassName(keyType);
//		}

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("logState_"+methodName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("void");
		
		if (paramName != null) {
			if (paramStructure.equals("list") || paramStructure.equals("set"))
				paramStructure = "collection";
			ModelParameter modelParameter = new ModelParameter();
			modelParameter.setName(paramName);
			modelParameter.setClassName(paramClass);
			modelParameter.setKeyClassName(paramKeyClass);
			modelParameter.setConstruct(paramStructure);
			modelOperation.addParameter(modelParameter);
		}
		
		Buf buf = new Buf();
		buf.putLine2("ActionState action = new ActionState();");
		buf.putLine2("action.setActionId(\""+methodName+"\");");
		if (paramName != null) {
			buf.putLine2("action.addElement(\""+paramName+"\", "+paramName+");");
//			if (paramStructure.equals("item")) {
//				buf.putLine2("action.addElement(\""+paramName+"\", "+paramName+");");
//			} else if (paramStructure.equals("list")) {
//				buf.putLine2("action.addElement(\""+paramName+"List\", "+paramName+"List);");
//			} else if (paramStructure.equals("set")) {
//				buf.putLine2("action.addElement(\""+paramName+"Set\", "+paramName+"Set);");
//			} else if (paramStructure.equals("map")) {
//				buf.putLine2("action.addElement(\""+paramName+"Map\", "+paramName+"Map);");
//			} else if (paramStructure.equals("collection")) {
//				buf.putLine2("action.addElement(\""+paramName+"Collection\", "+paramName+"Collection);");
//			}
		}
		buf.putLine2(processContextName+".logAction(action);");

		modelOperation.addInitialSource(buf.get());
		logStateOperations.add(modelOperation);
		return modelOperation;
	}

}
