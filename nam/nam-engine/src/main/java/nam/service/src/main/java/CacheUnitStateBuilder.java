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
import nam.model.util.TypeUtil;

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
 * Builds a Cache State module {@link ModelClass} object given a {@link Cache} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class CacheUnitStateBuilder extends AbstractDataUnitStateBuilder {

	public CacheUnitStateBuilder(GenerationContext context) {
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
		String className = DataLayerHelper.getCacheUnitInterfaceName(cache) + "State";
		String rootName = CacheUtil.getRootName(cache);
		String beanName = CacheUtil.getCacheName(cache);
		String typeName = CacheUtil.getType(cache);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(typeName);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setParentClassName("ServiceState");
		modelClass.addImportedClass("common.tx.state.ServiceState");
		initializeClass(modelClass, cache);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Cache cache) throws Exception {
		initializeImportedClasses(modelClass);
		initializeInstanceFields(modelClass, cache);
		initializeClassConstructors(modelClass, cache);
		initializeInstanceMethods(modelClass, cache);
	}

	protected void initializeInstanceFields(ModelClass modelClass, Cache cache) throws Exception {
		String className = CacheUtil.getClassName(cache) + "State";
		CodeUtil.addSerialVersionUIDField(modelClass);
		CodeUtil.addStaticLoggerField(modelClass, className);
		createCurrentStateFilename(modelClass, cache);
		createShadowStateFilename(modelClass, cache);
		createAttributesForDataItems(modelClass, cache);
	}

	protected void createCurrentStateFilename(ModelClass modelClass, Cache cache) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		//attribute.setPackageName(String.class.getPackage().getName());
		attribute.setClassName(String.class.getName());
		attribute.setName("LATEST_STATE_FILENAME");
		attribute.setDefault("\""+rootName+"_CurrentState\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(attribute);
	}

	protected void createShadowStateFilename(ModelClass modelClass, Cache cache) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		//attribute.setPackageName(String.class.getPackage().getName());
		attribute.setClassName(String.class.getName());
		attribute.setName("SHADOW_STATE_FILENAME");
		attribute.setDefault("\""+rootName+"_ShadowState\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(attribute);
	}

	protected void createAttributesForDataItems(ModelClass modelClass, Cache cache) {
		//List<Item> items = CacheUtil.getItems(cache);
		List<Field> fields = ElementUtil.getFields(cache);
		if (fields != null && fields.size() > 0) {
			//TODO sort these into alphabetical order as an option
			createAttributes(modelClass, fields);
		}
	}
	
	public <T extends Field> void createAttributes(ModelClass modelClass, List<T> items) {
		Iterator<T> iterator = items.iterator();
		while (iterator.hasNext()) {
			T item = iterator.next();
			String name = item.getName();
			String type = item.getType();
			String structure = item.getStructure();
			String packageName = util.getPackageNameFromType(type);
			String className = util.getClassNameFromType(type);

			ModelAttribute instanceAttribute = new ModelAttribute();
			instanceAttribute.setModifiers(Modifier.PRIVATE);
			instanceAttribute.setName(name);
			instanceAttribute.setClassName(className);
			instanceAttribute.setPackageName(packageName);
			instanceAttribute.setStructure(structure);
			instanceAttribute.setGenerateGetter(false);
			instanceAttribute.setGenerateSetter(false);
			instanceAttribute.setGenerateUnsetMethod(false);
			
			if (structure.equals("map")) {
				String key = item.getKey();
				String keyPackageName = util.getPackageNameFromType(key);
				String keyClassName = util.getClassNameFromType(key);
				instanceAttribute.setKeyPackageName(keyPackageName);
				instanceAttribute.setKeyClassName(keyClassName);
				instanceAttribute.setGenerateAddMethod(false);
				instanceAttribute.setGenerateRemoveMethod(false);
				instanceAttribute.setGenerateClearMethod(false);
				modelClass.addImportedClass("java.util.ArrayList");
				modelClass.addImportedClass("java.util.Iterator");
				modelClass.addImportedClass("java.util.LinkedHashMap");
				modelClass.addImportedClass("java.util.Map");
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass(keyPackageName+"."+keyClassName);

			} else if (structure.equals("list")) {
				instanceAttribute.setGenerateAddMethod(false);
				instanceAttribute.setGenerateRemoveMethod(false);
				instanceAttribute.setGenerateClearMethod(false);
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass("java.util.Collection");

			} else if (structure.equals("set")) {
				instanceAttribute.setGenerateAddMethod(false);
				instanceAttribute.setGenerateRemoveMethod(false);
				instanceAttribute.setGenerateClearMethod(false);
				modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass("java.util.Collection");
				
			} else if (structure.equals("item")) {
				instanceAttribute.setGenerateSetter(false);
			}
			
			modelClass.addImportedClass(packageName+"."+className);
			modelClass.addInstanceAttribute(instanceAttribute);
		}
	}

	protected void initializeClassConstructors(ModelClass modelClass, Cache cache) throws Exception {
		createConstructor_Default(modelClass, cache);
		createConstructor_ParentState(modelClass, cache);
	}
	
	protected void createConstructor_Default(ModelClass modelClass, Cache cache) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		
		List<String> sourceLines = new ArrayList<String>();
		List<Field> fields = ElementUtil.getFields(cache);
		//List<Item> items = CacheUtil.getItems(cache);
		if (fields != null && fields.size() > 0) {
			sourceLines.addAll(createSourceForFactoryMethod(modelClass, fields));
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelConstructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void createConstructor_ParentState(ModelClass modelClass, Cache cache) {
		String packageName = CacheUtil.getPackageName(cache);
		String className = CacheUtil.getClassName(cache) + "State";

		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName("parent");
		modelParameter.setPackageName(packageName);
		modelParameter.setClassName(className);
		modelConstructor.addParameter(modelParameter);
		
		List<String> sourceLines = new ArrayList<String>();
		List<Field> fields = ElementUtil.getFields(cache);
		//List<Item> items = CacheUtil.getItems(cache);
		if (fields != null && fields.size() > 0) {
			sourceLines.add("//super(parent);");
			sourceLines.addAll(createSourceForFactoryMethod(modelClass, fields, true));
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelConstructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Cache cacheUnit) throws Exception {
		createMethod_CreateState(modelClass, cacheUnit);
		createMethod_ResetState(modelClass, cacheUnit);
		createMethod_GetDerivedState(modelClass, cacheUnit);
		
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(CacheUtil.getAttributes(cacheUnit));
		fields.addAll(CacheUtil.getReferences(cacheUnit));
		createMethods_DataAccess(modelClass, fields, SourceType.SharedCache);
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

	protected void createMethod_GetDerivedState(ModelClass modelClass, Cache cache) throws Exception {
		String packageName = CacheUtil.getPackageName(cache);
		String className = CacheUtil.getClassName(cache) + "State";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createSuppressWarningsAnnotation("unchecked"));
		modelOperation.setName("getDerivedState");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(packageName+"."+className);
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				"return new "+className+"(this);"
		});
		modelOperation.addInitialSource(sourceCode);
		modelClass.addInstanceOperation(modelOperation);
	}

	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		modelUnit.addImportedClass("org.aries.Assert");
		
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
		String beanName = NameUtil.uncapName(fieldName);
		String structure = dataItem.getStructure();
		
		if (structure.equals("item")) {
		} else if (structure.equals("list")) {
		} else if (structure.equals("set")) {
		} else if (structure.equals("map")) {
		}
		
		Buf buf = new Buf();		
		switch (methodType) {
		case GetAllAsList: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	return "+beanName+";"); 
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	return "+beanName+";"); 
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">("+beanName+".values());"); 
				buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");
			}
			break;
			
		case GetAllAsMap: 
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = new HashMap<"+keyTypeName+", "+typeName+">("+beanName+");");
			buf.putLine2("	return "+paramName+"Map;");
			buf.putLine2("}");
			break;
			
		case GetAsItem: 
			if (structure.equals("item")) {
				buf.putLine2("return "+paramName+";"); 
			} else if (structure.equals("list")) {
			} else if (structure.equals("set")) {
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	return "+beanName+".get("+paramName+"Key);"); 
				buf.putLine2("}");
			}
			break;
			
		case GetAsItemById: 
			addImportedClassForHelper(modelUnit, dataItem);
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = getFrom"+fieldName+"("+paramName+"Criteria);");
				buf.putLine2("	Assert.isTrue("+paramName+"List.size() <= 1, \"Multiple records found for ID: \"+"+paramName+"Id);");
				buf.putLine2("	if ("+paramName+"List.size() == 1)");
				buf.putLine2("		return "+paramName+"List.get(0);");
				buf.putLine2("	return null;");
				buf.putLine2("}");

			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
				buf.putLine2("	Set<"+typeName+"> "+paramName+"Set = getFrom"+fieldName+"("+paramName+"Criteria);");
				buf.putLine2("	Assert.isTrue("+paramName+"Set.size() <= 1, \"Multiple records found for ID: \"+"+paramName+"Id);");
				buf.putLine2("	if ("+paramName+"Set.size() == 1)");
				buf.putLine2("		return "+paramName+"Set.iterator().next();");
				buf.putLine2("	return null;");
				buf.putLine2("}");

			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = getFrom"+fieldName+"("+paramName+"Criteria);");
				buf.putLine2("	Assert.isTrue("+paramName+"List.size() <= 1, \"Multiple records found for ID: \"+"+paramName+"Id);");
				buf.putLine2("	if ("+paramName+"List.size() == 1)");
				buf.putLine2("		return "+paramName+"List.get(0);");
				buf.putLine2("	return null;");
				buf.putLine2("}");
			}
			break;
			
		case GetAsListByIds: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">("+paramName+"Ids.size());");
				buf.putLine2("	Iterator<Long> iterator = "+paramName+"Ids.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		Long "+paramName+"Id = iterator.next();");
				buf.putLine2("		"+typeName+" "+paramName+" = getFrom"+fieldName+"("+paramName+"Id);");
				buf.putLine2("		if ("+paramName+" != null) {");
				buf.putLine2("			"+paramName+"List.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");

			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	Set<"+typeName+"> "+paramName+"Set = new HashSet<"+typeName+">("+paramName+"Ids.size());");
				buf.putLine2("	Iterator<Long> iterator = "+paramName+"Ids.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		Long "+paramName+"Id = iterator.next();");
				buf.putLine2("		"+typeName+" "+paramName+" = getFrom"+fieldName+"("+paramName+"Id);");
				buf.putLine2("		if ("+paramName+" != null) {");
				buf.putLine2("			"+paramName+"Set.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return "+paramName+"Set;");
				buf.putLine2("}");

			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">("+paramName+"Ids.size());");
				buf.putLine2("	Iterator<Long> iterator = "+paramName+"Ids.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		Long "+paramName+"Id = iterator.next();");
				buf.putLine2("		"+typeName+" "+paramName+" = getFrom"+fieldName+"("+paramName+"Id);");
				buf.putLine2("		if ("+paramName+" != null) {");
				buf.putLine2("			"+paramName+"List.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");
			}
			break;
			
		case GetAsItemByKey: 
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	return "+beanName+".get("+paramName+"Key);"); 
			buf.putLine2("}");
			break;
			
		case GetAsListByKeys: 
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">("+paramName+"Keys.size());");
			buf.putLine2("	Iterator<"+keyTypeName+"> iterator = "+paramName+"Keys.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+keyTypeName+" "+paramName+"Key = iterator.next();");
			buf.putLine2("		"+typeName+" "+paramName+" = "+beanName+".get("+paramName+"Key);");
			buf.putLine2("		"+paramName+"List.add("+paramName+");");
			buf.putLine2("	}");
			buf.putLine2("	return "+paramName+"List;");
			buf.putLine2("}");
			break;
			
		case GetAsList: 
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">("+paramName+"Keys.size());");
			buf.putLine2("	Iterator<"+keyTypeName+"> iterator = "+paramName+"Keys.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+keyTypeName+" "+paramName+"Key = iterator.next();");
			buf.putLine2("		"+typeName+" "+paramName+" = "+beanName+".get("+paramName+"Key);");
			buf.putLine2("		"+paramName+"List.add("+paramName+");");
			buf.putLine2("	}");
			buf.putLine2("	return "+paramName+"List;");
			buf.putLine2("}");
			break;
			
		case GetAsMapByKeys: 
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = new HashMap<"+keyTypeName+", "+typeName+">("+paramName+"Keys.size());");
			buf.putLine2("	Iterator<"+keyTypeName+"> iterator = "+paramName+"Keys.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+keyTypeName+" "+paramName+"Key = iterator.next();");
			buf.putLine2("		"+typeName+" "+paramName+" = "+beanName+".get("+paramName+"Key);");
			buf.putLine2("		"+paramName+"Map.put("+paramName+"Key, "+paramName+");");
			buf.putLine2("	}");
			buf.putLine2("	return "+paramName+"Map;");
			buf.putLine2("}");
			break;
			
		case GetMatchingAsList: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	List<"+typeName+"> matching"+typeName+"List = new ArrayList<"+typeName+">("+paramName+"List.size());");
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"List.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		if ("+beanName+".contains("+paramName+")) {");
				buf.putLine2("			matching"+typeName+"List.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return matching"+typeName+"List;");
				buf.putLine2("}");
				
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	Set<"+typeName+"> matching"+typeName+"Set = new HashSet<"+typeName+">("+paramName+"Set.size());");
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"Set.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		if ("+beanName+".contains("+paramName+")) {");
				buf.putLine2("			matching"+typeName+"Set.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return matching"+typeName+"Set;");
				buf.putLine2("}");	
				
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	List<"+typeName+"> matching"+typeName+"List = new ArrayList<"+typeName+">("+paramName+"List.size());");
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"List.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("		"+typeName+" matching"+typeName+" = "+beanName+".get("+paramName+"Key);");
				buf.putLine2("		if (matching"+typeName+" != null)");
				buf.putLine2("			matching"+typeName+"List.add(matching"+typeName+");");
				buf.putLine2("	}");
				buf.putLine2("	return matching"+typeName+"List;");
				buf.putLine2("}");
			}
			break;
			
		case GetMatchingAsMap: 
			addImportedClassForHelper(modelUnit, dataItem);
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	Map<"+keyTypeName+", "+typeName+"> map = new HashMap<"+keyTypeName+", "+typeName+">();");
			buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"List.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
			buf.putLine2("		"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
			buf.putLine2("		"+typeName+" matching"+typeName+" = "+beanName+".get("+paramName+"Key);");
			buf.putLine2("		if (matching"+typeName+" != null)");
			buf.putLine2("			map.put("+paramName+"Key, matching"+typeName+");");
			buf.putLine2("	}");
			buf.putLine2("	return map;");
			buf.putLine2("}");
			break;
			
		case GetAsListByCriteria: 
			addImportedClassForHelper(modelUnit, dataItem);
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">();");
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+beanName+".iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		if ("+helperName+".isMatch("+paramName+"Criteria, "+paramName+")) {");
				buf.putLine2("			"+paramName+"List.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");
				
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	Set<"+typeName+"> "+paramName+"Set = new HashSet<"+typeName+">();");
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+beanName+".iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		if ("+helperName+".isMatch("+paramName+"Criteria, "+paramName+")) {");
				buf.putLine2("			"+paramName+"Set.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return "+paramName+"Set;");
				buf.putLine2("}");
				
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">();");
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+beanName+".values().iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		if ("+helperName+".isMatch("+paramName+"Criteria, "+paramName+")) {");
				buf.putLine2("			"+paramName+"List.add("+paramName+");");
				buf.putLine2("		}");
				buf.putLine2("	}");
				buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");
			}
			break;
			
		case Set:
			if (structure.equals("item")) {
				buf.putLine2("this."+beanName+" = "+paramName+";");
			} else if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+" = new ArrayList<"+typeName+">("+paramName+"List);");
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+" = new HashSet<"+typeName+">("+paramName+"Set);");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+" = new LinkedHashMap<"+keyTypeName+", "+typeName+">("+paramName+"Map);");
				buf.putLine2("}");
			}
			break;
			
		case AddAsItem: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".add("+paramName+");");
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".add("+paramName+");");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("	"+beanName+".put("+paramName+"Key, "+paramName+");");
				buf.putLine2("}");
			}
			break;
			
		case AddAsList: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".addAll("+paramName+"List);");
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".addAll("+paramName+"Set);");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = "+helperName+".create"+typeName+"Map("+paramName+"List);");
				buf.putLine2("addTo"+fieldName+"("+paramName+"Map);");
			}
			break;
			
		case AddAsMap: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".addAll("+paramName+"List);");
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".addAll("+paramName+"Set);");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".putAll("+paramName+"Map);");
				buf.putLine2("}");
			}
			break;
			
		case RemoveAll: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				//buf.putLine2("	List<"+typeName+"> "+paramName+"List = get"+fieldName+"();");
				buf.putLine2("	"+beanName+".clear();");
				//buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				//buf.putLine2("	Set<"+typeName+"> "+paramName+"Set = get"+fieldName+"();");
				buf.putLine2("	"+beanName+".clear();");
				//buf.putLine2("	return "+paramName+"Set;");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				//buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">("+beanName+".size());");
				//buf.putLine2("	List<"+typeName+"> "+paramName+"List = get"+fieldName+"();");
				buf.putLine2("	"+beanName+".clear();");
				//buf.putLine2("	return "+paramName+"List;");
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsItem:
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".remove("+paramName+");"); 
				//buf.putLine2("	if ("+beanName+".remove("+paramName+"))"); 
				//buf.putLine2("		return "+paramName+";");
				//buf.putLine2("	return null;");
				buf.putLine2("}");
				
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".remove("+paramName+");"); 
				//buf.putLine2("	if ("+beanName+".remove("+paramName+"))"); 
				//buf.putLine2("		return "+paramName+";");
				//buf.putLine2("	return null;");
				buf.putLine2("}");
				
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("	"+beanName+".remove("+paramName+"Key);"); 
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsItemById:
			addImportedClassForHelper(modelUnit, dataItem);
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = getFrom"+fieldName+"("+paramName+"Criteria);");
				buf.putLine2("	Assert.isTrue("+paramName+"List.size() <= 1, \"Multiple records found for ID: \"+"+paramName+"Id);");
				buf.putLine2("	if ("+paramName+"List.size() == 1) {");
				buf.putLine2("		"+typeName+" "+paramName+" = "+paramName+"List.get(0);");
				buf.putLine2("		removeFrom"+fieldName+"("+paramName+");");
				buf.putLine2("	}");
				buf.putLine2("}");

			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
				buf.putLine2("	Set<"+typeName+"> "+paramName+"Set = getFrom"+fieldName+"("+paramName+"Criteria);");
				buf.putLine2("	Assert.isTrue("+paramName+"Set.size() <= 1, \"Multiple records found for ID: \"+"+paramName+"Id);");
				buf.putLine2("	if ("+paramName+"Set.size() == 1) {");
				buf.putLine2("		"+typeName+" "+paramName+" = "+paramName+"Set.iterator().next();");
				buf.putLine2("		removeFrom"+fieldName+"("+paramName+");");
				buf.putLine2("	}");
				buf.putLine2("}");

			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = getFrom"+fieldName+"("+paramName+"Criteria);");
				buf.putLine2("	Assert.isTrue("+paramName+"List.size() <= 1, \"Multiple records found for ID: \"+"+paramName+"Id);");
				buf.putLine2("	if ("+paramName+"List.size() == 1) {");
				buf.putLine2("		"+typeName+" "+paramName+" = "+paramName+"List.get(0);");
				buf.putLine2("		removeFrom"+fieldName+"("+paramName+");");
				buf.putLine2("	}");
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsItemByKey:
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	"+beanName+".remove("+paramName+"Key);"); 
			buf.putLine2("}");
			break;
			
		case RemoveAsListByKeys:
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">("+paramName+"Keys.size());");
			buf.putLine2("	Iterator<"+keyTypeName+"> iterator = "+paramName+"Keys.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+keyTypeName+" "+paramName+"Key = iterator.next();");
			buf.putLine2("		"+typeName+" "+paramName+" = "+beanName+".remove("+paramName+"Key);");
			buf.putLine2("		if ("+paramName+" != null)");
			buf.putLine2("			"+paramName+"List.add("+paramName+");");
			buf.putLine2("	}");
			buf.putLine2("	return "+paramName+"List;");
			buf.putLine2("}");
			break;
			
		case RemoveAsList:
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				//buf.putLine2("	List<"+typeName+"> removed"+typeName+"List = new ArrayList<"+typeName+">("+paramName+"List);");
				buf.putLine2("	"+beanName+".clear();");
				//buf.putLine2("	return removed"+typeName+"List;");
				buf.putLine2("}");
				
			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				//buf.putLine2("	Set<"+typeName+"> removed"+typeName+"Set = new HashSet<"+typeName+">("+paramName+"Set);");
				buf.putLine2("	"+beanName+".clear();");
				//buf.putLine2("	return removed"+typeName+"Set;");
				buf.putLine2("}");
				
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("synchronized ("+beanName+") {");
				//buf.putLine2("	List<"+typeName+"> removed"+typeName+"List = new ArrayList<"+typeName+">("+paramName+"Keys.size());");
				buf.putLine2("	Collection<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
				buf.putLine2("	Iterator<"+keyTypeName+"> iterator = "+paramName+"Keys.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+keyTypeName+" "+paramName+"Key = iterator.next();");
				buf.putLine2("		"+beanName+".remove("+paramName+"Key);");
				//buf.putLine2("		"+typeName+" "+paramName+" = "+beanName+".remove("+paramName+"Key);");
				//buf.putLine2("		if ("+paramName+" != null)");
				//buf.putLine2("			removed"+typeName+"List.add("+paramName+");");
				buf.putLine2("	}");
				//buf.putLine2("	return removed"+typeName+"List;");
				buf.putLine2("}");
			}
			break;
			
		case RemoveMatchingAsList:
			addImportedClassForHelper(modelUnit, dataItem);
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	List<"+typeName+"> matching"+typeName+"List = new ArrayList<"+typeName+">("+paramName+"List.size());");
			buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"List.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
			buf.putLine2("		"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
			buf.putLine2("		"+typeName+" matching"+typeName+" = "+beanName+".remove("+paramName+"Key);");
			buf.putLine2("		if (matching"+typeName+" != null)");
			buf.putLine2("			matching"+typeName+"List.add(matching"+typeName+");");
			buf.putLine2("	}");
			buf.putLine2("	return matching"+typeName+"List;");
			buf.putLine2("}");
			break;
			
		case RemoveAsListByCriteria:
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = getFrom"+fieldName+"("+paramName+"Criteria);");
				buf.putLine2("	removeFrom"+fieldName+"("+paramName+"List);");
				buf.putLine2("}");

			} else if (structure.equals("set")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	Set<"+typeName+"> "+paramName+"Set = getFrom"+fieldName+"("+paramName+"Criteria);");
				buf.putLine2("	removeFrom"+fieldName+"("+paramName+"Set);");
				buf.putLine2("}");

			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = getFrom"+fieldName+"("+paramName+"Criteria);");
				buf.putLine2("	removeFrom"+fieldName+"("+paramName+"List);");
				buf.putLine2("}");
			}
			break;
			
		}
		return buf.get();
	}

}
