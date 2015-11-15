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
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


/**
 * Builds a CacheUnit State Processor {@link ModelClass} object given a {@link Cache} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class CacheUnitStateProcessorBuilder extends AbstractDataUnitStateProcessorBuilder {

	public CacheUnitStateProcessorBuilder(GenerationContext context) {
		super(context);
	}
	
	protected boolean isCacheUnitRelated() {
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
			modelClasses.add(buildClass(namespace, cacheUnit));
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(String namespace, Cache cache) throws Exception {
		String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cache);
		String stateClassName = DataLayerHelper.getCacheUnitInterfaceName(cache) + "State";
		String processorClassName = DataLayerHelper.getCacheUnitInterfaceName(cache) + "Processor";
		String rootName = CacheUtil.getRootName(cache);
		String beanName = CacheUtil.getCacheName(cache);
		String typeName = CacheUtil.getType(cache);

		setBeanName(beanName);
		setRootName(rootName);
		setPackageName(packageName);
		setClassName(processorClassName);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(typeName);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(processorClassName);
		modelClass.setName(beanName);
		modelClass.addImplementedInterface("ServiceStateProcessor<"+stateClassName+">");
		modelClass.addImportedClass("common.tx.state.ServiceStateProcessor");
		initializeClass(modelClass, cache);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Cache cache) throws Exception {
		initializeImportedClasses(modelClass, cache);
		initializeClassAnnotations(modelClass);
		initializeClassConstructors(modelClass, cache);
		initializeInstanceFields(modelClass, cache);
		initializeInstanceMethods(modelClass, cache);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Cache cache) throws Exception {
		super.initializeImportedClasses(modelClass);
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("java.util.Iterator");
		List<Field> itemsFromCache = getItemsFromCache(cache);
		Iterator<Field> iterator = itemsFromCache.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Field item = iterator.next();
			String structure = item.getStructure();
			if (structure.equals("list")) {
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass("java.util.ArrayList");
			} else if (structure.equals("set")) {
				modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass("java.util.HashSet");
				//modelClass.addImportedClass("java.util.LinkedHashSet");
			} else if (structure.equals("map")) {
				modelClass.addImportedClass("java.util.ArrayList");
				modelClass.addImportedClass("java.util.Map");
				//modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass("java.util.HashMap");
				//modelClass.addImportedClass("java.util.LinkedHashMap");
			}
		}
	}

	protected void initializeClassConstructors(ModelClass modelClass, Cache cache) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"// nothing for now"
		});
		constructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(constructor);
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Cache cache) throws Exception {
		CodeUtil.addStaticLoggerField(modelClass, className);
		
		List<Field> itemsFromCache = getItemsFromCache(cache);
		Iterator<Field> iterator = itemsFromCache.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Field item = iterator.next();
			String type = item.getType();
			String name = NameUtil.capName(item.getName());
			String packageName = util.getPackageNameFromType(type);
			String className = util.getClassNameFromType(type);
			String structure = item.getStructure();

			ModelAttribute modelAttribute = new ModelAttribute();
			modelAttribute.setModifiers(Modifier.PRIVATE);
			modelAttribute.setName("pending"+name);
			modelAttribute.setClassName(className);
			modelAttribute.setStructure(structure);
			modelAttribute.setGenerateGetter(false);
			modelAttribute.setGenerateSetter(false);
			modelAttribute.setGenerateAddMethod(false);
			modelAttribute.setGenerateRemoveMethod(false);
			modelClass.addImportedClass(packageName+"."+className);
			modelClass.addInstanceAttribute(modelAttribute);

			if (structure.equals("item")) {
				//nothing for now
				
			} else if (structure.equals("list")) {
				modelAttribute.setDefault("new ArrayList<"+className+">()");
				//modelAttribute.setGenerateAddMethod(true);
				//modelAttribute.setGenerateRemoveMethod(true);
				//modelAttribute.setGenerateClearMethod(true);
				modelClass.addImportedClass(packageName+"."+className);

			} else if (structure.equals("set")) {
				modelAttribute.setDefault("new HashSet<"+className+">()");
				//modelAttribute.setGenerateAddMethod(true);
				//modelAttribute.setGenerateRemoveMethod(true);
				//modelAttribute.setGenerateClearMethod(true);
				modelClass.addImportedClass(packageName+"."+className);

			} else if (structure.equals("map")) {
				String key = item.getKey();
				String keyPackageName = util.getPackageNameFromType(key);
				String keyClassName = util.getClassNameFromType(key);
				modelAttribute.setKeyPackageName(keyPackageName);
				modelAttribute.setKeyClassName(keyClassName);
				modelAttribute.setDefault("new HashMap<"+keyClassName+", "+className+">()");
				//modelAttribute.setGenerateAddMethod(true);
				//modelAttribute.setGenerateRemoveMethod(true);
				//modelAttribute.setGenerateClearMethod(true);
				modelClass.addImportedClass(packageName+"."+className);
				modelClass.addImportedClass(keyPackageName+"."+keyClassName);
			}
		}
	}

	protected ModelAttribute createAttribute_SerialVersionUID() {
		ModelAttribute staticAttribute = new ModelAttribute();
		staticAttribute.setModifiers(Modifier.PUBLIC+Modifier.FINAL+Modifier.STATIC);
		staticAttribute.setClassName("long");
		staticAttribute.setName("serialVersionUID");
		staticAttribute.setDefault(1L);
		return staticAttribute;
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Cache cacheUnit) throws Exception {
		createMethod_ResetState(modelClass, cacheUnit);
		createMethod_ValidateState(modelClass, cacheUnit);
		createMethod_UpdateState(modelClass, cacheUnit);
		createMethod_ProcessRequest(modelClass, cacheUnit);
		
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(CacheUtil.getAttributes(cacheUnit));
		fields.addAll(CacheUtil.getReferences(cacheUnit));
		createMethods_DataAccess(modelClass, fields, SourceType.SharedCache);
	}
	
	protected void createMethod_ResetState(ModelClass modelClass, Cache cache) throws Exception {
		String className = CacheUtil.getClassName(cache) + "State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("resetState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = CodeUtil.createParameter(className, "state");
		modelOperation.addParameter(modelParameter);

		List<String> sourceLines = new ArrayList<String>();
		List<Field> itemsFromCache = getItemsFromCache(cache);
		Iterator<Field> iterator = itemsFromCache.iterator();
		while (iterator.hasNext()) {
			Field item = iterator.next();
			String structure = item.getStructure();
			String name = NameUtil.capName(item.getName());
			if (structure.equals("item")) {
				sourceLines.add("state.set"+name+"(null);");
			} else {
				sourceLines.add("state.removeAll"+name+"();");
			}
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ValidateState(ModelClass modelClass, Cache cache) throws Exception {
		String className = CacheUtil.getClassName(cache) + "State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("validateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(boolean.class.getName());
		ModelParameter modelParameter = CodeUtil.createParameter(className, "state");
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		List<Field> itemsFromCache = getItemsFromCache(cache);
		if (itemsFromCache.size() == 0) {
			buf.putLine2("return false;");
		} else {
			buf.put2("//return ");
			Iterator<Field> iterator = itemsFromCache.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				Field item = iterator.next();
				String name = NameUtil.capName(item.getName());
				if (i > 0)
					buf.put(" && ");
				buf.put("pending"+name+" != null");
			}
			buf.putLine(";");
			buf.putLine2("return true;");
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_UpdateState(ModelClass modelClass, Cache cache) throws Exception {
		String className = CacheUtil.getClassName(cache) + "State";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("updateState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = CodeUtil.createParameter(className, "state");
		modelOperation.addParameter(modelParameter);

		List<String> sourceLines = new ArrayList<String>();
		List<Field> itemsFromCache = getItemsFromCache(cache);
		Iterator<Field> iterator = itemsFromCache.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			//String itemType = item.getType();
			//String itemName = item.getName();
			//String className = NameUtil.getLocalNameFromXSDType(itemType);
			String itemNameCapped = NameUtil.capName(field.getName());
			String pendingItemName = "pending"+itemNameCapped;
			String structure = field.getStructure();
			if (structure.equals("item")) {
				sourceLines.add("state.set"+itemNameCapped+"("+pendingItemName+");");
			} else if (structure.equals("list")) {
				sourceLines.add("state.set"+itemNameCapped+"("+pendingItemName+");");
				//sourceLines.add("state.get"+itemNameCapped+"().addAll("+pendingItemName+");");
			} else if (structure.equals("set")) {
				sourceLines.add("state.set"+itemNameCapped+"("+pendingItemName+");");
				//sourceLines.add("state.get"+itemNameCapped+"().addAll("+pendingItemName+");");
			} else if (structure.equals("map")) {
				//sourceLines.add("state.get"+name+"().clear();");
				//sourceLines.add("List<"+className+"> "+itemName+" = "+pendingItemName+".getElements();");
				//sourceLines.add("state.get"+itemNameCapped+"().putAll("+pendingItemName+");");
				sourceLines.add("state.addTo"+itemNameCapped+"("+pendingItemName+");");
			}
		}

		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ProcessRequest(ModelClass modelClass, Cache cache) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("processRequest");
		modelOperation.setModifiers(Modifier.PUBLIC);
		//modelOperation.setResultType(packageName+"."+serviceName+"State");
		modelOperation.setResultType("void");

		/*
		//TODO Assuming only one service operation exists for now
		modelOperation.addParameter(CodeUtil.createParameter_CorrelationId());
		Operation operation = service.getOperations().get(0);
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			modelOperation.addParameter(CodeUtil.createParameter(parameters, parameter));
			modelClass.addImportedClass(TypeUtil.getJavaName(parameter.getType()));
		}
		
		List<String> sourceLines = new ArrayList<String>();
		Cache cache = service.getProcess();
		Cache cache = service.getCache();
		if (process == null || cache == null) {
			System.out.println();
			
		} else {
			String processName = process.getName();
			String cacheName = cache.getName();
			//sourceLines.add("pendingBookOrders = request.getBookOrders();");
			//sourceLines.add("ProcessLocator processLocator = BeanContext.get(\"org.aries.processLocator\");");
			//sourceLines.add(processName+" process = processLocator.lookup("+processName+".class, correlationId);");
			//sourceLines.add("process.receive"+serviceName+"(pendingBookOrders);");
		}

		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelOperation.addInitialSource(sourceCode);
		*/
		
		modelClass.addInstanceOperation(modelOperation);
	}

	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		String type = dataItem.getType();
		String keyType = dataItem.getKey();
		String paramName = TypeUtil.getLocalPart(type);
		String helperName = ModelLayerHelper.getModelHelperBeanName(type);
		String keyParamName = null;
		String keyTypeName = null;
		if (keyType != null) {
			keyParamName = TypeUtil.getLocalPart(keyType);
			keyTypeName = NameUtil.capName(keyParamName);
		}
		String typeName = NameUtil.capName(paramName);
		String fieldName = NameUtil.capName(dataItem.getName());
		String structure = dataItem.getStructure();
		
		Buf buf = new Buf();
		switch (methodType) {
		case GetAsList: 
		case GetAllAsList: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			if (structure.equals("list")) {
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">(pending"+fieldName+");");
				buf.putLine2("	return "+paramName+"List;");
			} else if (structure.equals("set")) {
				buf.putLine2("	Set<"+typeName+"> "+paramName+"Set = new HashSet<"+typeName+">(pending"+fieldName+");");
				buf.putLine2("	return "+paramName+"Set;");
			} else if (structure.equals("map")) {
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">(pending"+fieldName+".values());");
				buf.putLine2("	return "+paramName+"List;");
			}
			buf.putLine2("}");
			break;
			
		case GetAllAsMap: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = new HashMap<"+keyTypeName+", "+typeName+">(pending"+fieldName+");");
			buf.putLine2("	return "+paramName+"Map;");
			buf.putLine2("}");
			break;
			
		case GetAsItem: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	return pending"+fieldName+";");
			buf.putLine2("}");
			break;
			
		case GetAsItemById: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			if (!structure.equals("map"))
				buf.putLine2("	Iterator<"+typeName+"> iterator = pending"+fieldName+".iterator();");
			else buf.putLine2("	Iterator<"+typeName+"> iterator = pending"+fieldName+".values().iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
			buf.putLine2("		Long id = "+paramName+".getId();");
			buf.putLine2("		if (id != null && id.equals("+paramName+"Id)) {");
			buf.putLine2("			return "+paramName+";");
			buf.putLine2("		}");
			buf.putLine2("	}");
			buf.putLine2("	return null;");
			buf.putLine2("}");
			break;
			
		case GetAsListByIds:
			buf.putLine2("return null;");
			break;
			
		case GetAsItemByKey: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	return pending"+fieldName+".get("+paramName+"Key);");
			buf.putLine2("}");
			break;
			
		case GetAsMapByKeys: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	return pending"+fieldName+";");
			buf.putLine2("}");
			break;
			
		case GetAsListByCriteria: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			if (structure.equals("list")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">();");
				buf.putLine2("	Iterator<"+typeName+"> iterator = pending"+fieldName+".iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		if ("+helperName+".isMatch("+paramName+"Criteria, "+paramName+")) {");
				buf.putLine2("			"+paramName+"List.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");
				break;
				
			} else if (structure.equals("set")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("	Set<"+typeName+"> "+paramName+"Set = new HashSet<"+typeName+">();");
				buf.putLine2("	Iterator<"+typeName+"> iterator = pending"+fieldName+".iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		if ("+helperName+".isMatch("+paramName+"Criteria, "+paramName+")) {");
				buf.putLine2("			"+paramName+"Set.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return "+paramName+"Set;");
				buf.putLine2("}");
				break;
				
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">();");
				buf.putLine2("	Iterator<"+typeName+"> iterator = pending"+fieldName+".values().iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		if ("+helperName+".isMatch("+paramName+"Criteria, "+paramName+")) {");
				buf.putLine2("			"+paramName+"List.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");
				break;
			}
			
		case Set: 
			if (structure.equals("item")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+" = "+paramName+";"); 
				buf.putLine2("}");
			} else if (structure.equals("list")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+" = new ArrayList<"+typeName+">("+paramName+"List);"); 
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+" = new HashSet<"+typeName+">("+paramName+"Set);"); 
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+" = new HashMap<"+keyTypeName+", "+typeName+">("+paramName+"Map);"); 
				buf.putLine2("}");
			}
			break;
			
		case AddAsItem: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+".add("+paramName+");"); 
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+".add("+paramName+");"); 
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("	pending"+fieldName+".put("+paramName+"Key, "+paramName+");"); 
				buf.putLine2("}");
			}
			break;
			
		case AddAsList: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+".addAll("+paramName+"List);"); 
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+".addAll("+paramName+"Set);"); 
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = "+helperName+".create"+typeName+"Map("+paramName+"List);");
				buf.putLine2("	pending"+fieldName+".putAll("+paramName+"Map);"); 
				buf.putLine2("}");
			}
			break;
			
		case AddAsMap: 
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	pending"+fieldName+".putAll("+paramName+"Map);"); 
			buf.putLine2("}");
			break;
			
		case RemoveAll: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				//buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">();");
				//buf.putLine2("	"+paramName+"List.addAll(pending"+fieldName+");");
				buf.putLine2("	pending"+fieldName+".clear();");
				//buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				//buf.putLine2("	Set<"+typeName+"> "+paramName+"Set = new LinkedHashSet<"+typeName+">();");
				//buf.putLine2("	"+paramName+"Set.addAll(pending"+fieldName+");");
				buf.putLine2("	pending"+fieldName+".clear();");
				//buf.putLine2("	return "+paramName+"Set;");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				//buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">();");
				//buf.putLine2("	"+paramName+"List.addAll(pending"+fieldName+".values());");
				buf.putLine2("	pending"+fieldName+".clear();");
				//buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsItem:
			if (structure.equals("list")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+".remove("+paramName+");");
				//buf.putLine2("	if (pending"+fieldName+".remove("+paramName+"))");
				//buf.putLine2("		return "+paramName+";");
				//buf.putLine2("	return null;");
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	pending"+fieldName+".remove("+paramName+");");
				//buf.putLine2("	if (pending"+fieldName+".remove("+paramName+"))"); 
				//buf.putLine2("		return "+paramName+";");
				//buf.putLine2("	return null;");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");	
				buf.putLine2("	pending"+fieldName+".remove("+paramName+");");
				//buf.putLine2("	if (pending"+fieldName+".remove("+paramName+"))"); 
				//buf.putLine2("		return "+paramName+";");
				//buf.putLine2("	return null;");
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsItemByKey:
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	pending"+fieldName+".remove("+paramName+"Key);"); 
			buf.putLine2("}");
			break;
			
		case RemoveAsListByKeys:
			buf.putLine2("synchronized (pending"+fieldName+") {");
			buf.putLine2("	Collection<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">();");
			buf.putLine2("	Iterator<"+keyTypeName+"> iterator = "+paramName+"Keys.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+keyTypeName+" key = iterator.next();");
			buf.putLine2("		"+typeName+" "+paramName+" = pending"+fieldName+".remove(key);");
			buf.putLine2("		if ("+paramName+" != null)");
			buf.putLine2("			"+paramName+"List.add("+paramName+");");
			buf.putLine2("	}");
			//buf.putLine2("	return "+paramName+"List;");
			buf.putLine2("}");
			break;
			
		case RemoveAsList:
			if (structure.equals("list")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				//buf.putLine2("	List<"+typeName+"> removed"+typeName+"List = new ArrayList<"+typeName+">();");
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"List.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		pending"+fieldName+".remove("+paramName+");");
				//buf.putLine2("		if (pending"+fieldName+".remove("+paramName+")) {");
				//buf.putLine2("			removed"+typeName+"List.add("+paramName+");");
				//buf.putLine2("		}");
				buf.putLine2("	}");
				//buf.putLine2("	return removed"+typeName+"List;");
				buf.putLine2("}");
				
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized (pending"+fieldName+") {");
				//buf.putLine2("	Set<"+typeName+"> removed"+typeName+"Set = new HashSet<"+typeName+">();");
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"Set.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		pending"+fieldName+".remove("+paramName+");");
				//buf.putLine2("		if (pending"+fieldName+".remove("+paramName+")) {");
				//buf.putLine2("			removed"+typeName+"Set.add("+paramName+");");
				//buf.putLine2("		}");
				buf.putLine2("	}");
				//buf.putLine2("	return removed"+typeName+"Set;");
				buf.putLine2("}");
				
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("synchronized (pending"+fieldName+") {");
				buf.putLine2("	Collection<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
				//buf.putLine2("	List<"+typeName+"> removed"+typeName+"List = new ArrayList<"+typeName+">();");
				buf.putLine2("	Iterator<"+keyTypeName+"> iterator = "+paramName+"Keys.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+keyTypeName+" key = iterator.next();");
				buf.putLine2("		pending"+fieldName+".remove(key);");
				//buf.putLine2("		"+typeName+" "+paramName+" = pending"+fieldName+".remove(key);");
				//buf.putLine2("		if ("+paramName+" != null)");
				//buf.putLine2("			removed"+typeName+"List.add("+paramName+");");
				buf.putLine2("	}");
				//buf.putLine2("	return removed"+typeName+"List;");
				buf.putLine2("}");
			}
			break;
		}
		return buf.get();
	}

}
