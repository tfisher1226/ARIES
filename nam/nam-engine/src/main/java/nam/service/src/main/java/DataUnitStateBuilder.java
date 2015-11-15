package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Process;
import nam.model.Type;
import nam.model.Unit;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

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
 * Builds a DataUnit State module {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class DataUnitStateBuilder extends AbstractDataUnitStateBuilder {

	public DataUnitStateBuilder(GenerationContext context) {
		super(context);
	}
	
	protected boolean isCacheUnitRelated() {
		return true;
	}
	
	public List<ModelClass> buildClasses(Process process) throws Exception {
		return buildClasses(process.getNamespace(), process.getDataUnits());
	}
	
	public List<ModelClass> buildClasses(String namespace, List<Unit> units) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Unit> iterator = units.iterator();
		while (iterator.hasNext()) {
			Unit unit = iterator.next();
			context.setUnit(unit);
			modelClasses.add(buildClass(namespace, unit));
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(String namespace, Unit unit) throws Exception {
		String packageName = DataLayerHelper.getPersistenceUnitPackageName(namespace, unit);
		String stateClassName = DataLayerHelper.getPersistenceUnitClassName(unit) + "State";
		String beanName = DataLayerHelper.getPersistenceUnitNameUncapped(unit) + "State";
		String beanType = TypeUtil.getTypeFromNamespaceAndLocalPart(unit.getNamespace(), beanName);

		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(stateClassName);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(beanType);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(stateClassName);
		modelClass.setName(beanName);
		modelClass.setParentClassName("ServiceState");
		modelClass.addImportedClass("common.tx.state.ServiceState");
		initializeClass(modelClass, unit);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Unit unit) throws Exception {
		initializeImportedClasses(modelClass);
		initializeInstanceFields(modelClass, unit);
		initializeClassConstructors(modelClass, unit);
		initializeInstanceMethods(modelClass, unit);
	}

	protected void initializeInstanceFields(ModelClass modelClass, Unit unit) throws Exception {
		CodeUtil.addSerialVersionUIDField(modelClass);
		CodeUtil.addStaticLoggerField(modelClass, getClassName());
		createCurrentStateFilename(modelClass, unit);
		createShadowStateFilename(modelClass, unit);
		createAttributesForDataItems(modelClass, unit);
	}

	protected void createCurrentStateFilename(ModelClass modelClass, Unit unit) {
		String unitName = DataLayerHelper.getPersistenceUnitNameCapped(unit);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		//attribute.setPackageName(String.class.getPackage().getName());
		attribute.setClassName(String.class.getName());
		attribute.setName("LATEST_STATE_FILENAME");
		attribute.setDefault("\""+unitName+"_CurrentState\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(attribute);
	}

	protected void createShadowStateFilename(ModelClass modelClass, Unit unit) {
		String unitName = DataLayerHelper.getPersistenceUnitNameCapped(unit);
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PUBLIC + Modifier.FINAL + Modifier.STATIC);
		//attribute.setPackageName(String.class.getPackage().getName());
		attribute.setClassName(String.class.getName());
		attribute.setName("SHADOW_STATE_FILENAME");
		attribute.setDefault("\""+unitName+"_ShadowState\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		modelClass.addStaticAttribute(attribute);
	}

	protected void createAttributesForDataItems(ModelClass modelClass, Unit unit) {
		//List<Item> items = CacheUtil.getItems(unit);
		List<Element> elements = UnitUtil.getElements(unit);
		if (elements != null && elements.size() > 0) {
			//TODO sort these into alphabetical order as an option
			createAttributes(modelClass, elements);
		}
	}
	
	public <T extends Element> void createAttributes(ModelClass modelClass, List<T> items) {
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

	protected void initializeClassConstructors(ModelClass modelClass, Unit unit) throws Exception {
		createConstructor_Default(modelClass, unit);
		createConstructor_ParentState(modelClass, unit);
	}
	
	protected void createConstructor_Default(ModelClass modelClass, Unit unit) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		
		List<String> sourceLines = new ArrayList<String>();
		List<Element> elements = UnitUtil.getElements(unit);
		if (elements != null && elements.size() > 0) {
			sourceLines.addAll(createSourceForFactoryMethod(modelClass, elements));
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelConstructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void createConstructor_ParentState(ModelClass modelClass, Unit unit) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setName("parent");
		modelParameter.setPackageName(packageName);
		modelParameter.setClassName(className);
		modelConstructor.addParameter(modelParameter);
		
		List<String> sourceLines = new ArrayList<String>();
		List<Element> elements = UnitUtil.getElements(unit);
		if (elements != null && elements.size() > 0) {
			sourceLines.add("super(parent);");
			sourceLines.addAll(createSourceForFactoryMethod(modelClass, elements, true));
		}
		
		String sourceCode = CodeUtil.createMethodSource(sourceLines);
		modelConstructor.addInitialSource(sourceCode);
		modelClass.addInstanceConstructor(modelConstructor);
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Unit unit) throws Exception {
		createMethod_CreateState(modelClass, unit);
		createMethod_ResetState(modelClass, unit);
		createMethod_GetDerivedState(modelClass, unit);
		
		List<Element> elements = UnitUtil.getElements(unit);
		createMethods_DataAccess(modelClass, elements, SourceType.SharedCache);
	}
	
	protected void createMethod_CreateState(ModelClass modelClass, Unit unit) throws Exception {
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

	protected void createMethod_ResetState(ModelClass modelClass, Unit unit) throws Exception {
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

	protected void createMethod_GetDerivedState(ModelClass modelClass, Unit unit) throws Exception {
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

//	protected boolean isResultRequired(MethodType methodType) {
//		if (methodType == MethodType.RemoveAll ||
//			methodType == MethodType.RemoveAsItem ||
//			methodType == MethodType.RemoveAsItemByKey ||
//			methodType == MethodType.RemoveAsListByKeys ||
//			methodType == MethodType.RemoveAsList ||
//			methodType == MethodType.RemoveMatchingAsList)
//				return false;
//		return super.isResultRequired(methodType);
//	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		addImportedClassForHelper(modelUnit, dataItem);

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
		
		Buf buf = new Buf();		
		switch (methodType) {
		case GetAllAsList: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">("+beanName+");"); 
				buf.putLine2("	return "+paramName+"List;"); 
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
			buf.putLine2("	return new HashMap<"+keyTypeName+", "+typeName+">("+beanName+");");
			buf.putLine2("}");
			break;
			
		case GetAsItem: 
		case GetAsItemById: 
			buf.putLine2("synchronized ("+beanName+") {");
			if (!structure.equals("map"))
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+beanName+".iterator();");
			else buf.putLine2("	Iterator<"+typeName+"> iterator = "+beanName+".values().iterator();");
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
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	List<"+typeName+"> list = new ArrayList<"+typeName+">("+paramName+"Ids.size());");
			buf.putLine2("	Iterator<Long> iterator = "+paramName+"Ids.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		Long "+paramName+"Id = iterator.next();");
			buf.putLine2("		"+typeName+" "+paramName+" = getFrom"+fieldName+"("+paramName+"Id);");
			buf.putLine2("		if ("+paramName+" != null) {");
			buf.putLine2("			list.add("+paramName+");");
			buf.putLine2("		}");
			buf.putLine2("	}");
			buf.putLine2("	return list;");
			buf.putLine2("}");
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
			
		case GetAsListByCriteria: 
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	List<"+typeName+"> list = new ArrayList<"+typeName+">();");
			if (!structure.equals("map"))
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+beanName+".iterator();");
			else buf.putLine2("	Iterator<"+typeName+"> iterator = "+beanName+".values().iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
			buf.putLine2("		if ("+helperName+".isMatch("+paramName+"Criteria, "+paramName+")) {");
			buf.putLine2("			list.add("+paramName+");");
			buf.putLine2("		}");
			buf.putLine2("	}");
			buf.putLine2("	return list;");
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
				buf.putLine2("		if ("+beanName+".contains("+paramName+"))");
				buf.putLine2("			matching"+typeName+"List.add("+paramName+");");
				buf.putLine2("	}");
				buf.putLine2("	return matching"+typeName+"List;");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
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
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	Map<"+keyTypeName+", "+typeName+"> matching"+typeName+"Map = new LinkedHashMap<"+keyTypeName+", "+typeName+">();");
			buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"List.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
			buf.putLine2("		"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
			buf.putLine2("		"+typeName+" matching"+typeName+" = "+beanName+".get("+paramName+"Key);");
			buf.putLine2("		if (matching"+typeName+" != null)");
			buf.putLine2("			matching"+typeName+"Map.put("+paramName+"Key, matching"+typeName+");");
			buf.putLine2("	}");
			buf.putLine2("	return matching"+typeName+"Map;");
			buf.putLine2("}");
			break;
			
		case Set: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+" = new ArrayList<"+typeName+">("+paramName+"List);");
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
			} else if (structure.equals("map")) {
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
			} else if (structure.equals("map")) {
				buf.putLine2("Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = "+helperName+".create"+typeName+"Map("+paramName+"List);");
				buf.putLine2("addTo"+fieldName+"("+paramName+"Map);");
				//buf.putLine2("synchronized ("+beanName+") {");
				//buf.putLine2("	"+beanName+".putAll("+paramName+"List);");
				//buf.putLine2("}");
			}
			break;
			
		case AddAsMap: 
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".addAll("+paramName+"List);");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".putAll("+paramName+"Map);");
				buf.putLine2("}");
			}
			break;
			
		case RemoveAll: 
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	"+beanName+".clear();");
			buf.putLine2("}");
			break;
			
		case RemoveAsItem:
			if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("	"+beanName+".remove("+paramName+"Key);"); 
				buf.putLine2("}");
			} else {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	"+beanName+".remove("+paramName+");"); 
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsItemById:
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	"+typeName+" "+paramName+" = getFrom"+fieldName+"("+paramName+"Id);");
			buf.putLine2("	if ("+paramName+" != null) {");
			buf.putLine2("		removeFrom"+fieldName+"("+paramName+");");
			buf.putLine2("	}");
			buf.putLine2("}");
			break;
			
		case RemoveAsItemByKey:
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	"+beanName+".remove("+paramName+"Key);"); 
			buf.putLine2("}");
			break;
			
		case RemoveAsListByKeys:
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	Iterator<"+keyTypeName+"> iterator = "+paramName+"Keys.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+keyTypeName+" "+paramName+"Key = iterator.next();");
			buf.putLine2("		"+beanName+".remove("+paramName+"Key);");
			buf.putLine2("	}");
			buf.putLine2("}");
			break;
			
		case RemoveAsList:
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"List.iterator();");
			buf.putLine2("	while (iterator.hasNext()) {");
			buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
			buf.putLine2("		removeFrom"+fieldName+"("+paramName+");");
			buf.putLine2("	}");
			buf.putLine2("}");
			break;
			
		case RemoveMatchingAsList:
			if (structure.equals("list")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"List.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		"+beanName+".remove("+paramName+");");
				buf.putLine2("	}");
				buf.putLine2("}");
			} else if (structure.equals("map")) {
				buf.putLine2("synchronized ("+beanName+") {");
				buf.putLine2("	Iterator<"+typeName+"> iterator = "+paramName+"List.iterator();");
				buf.putLine2("	while (iterator.hasNext()) {");
				buf.putLine2("		"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("		"+keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("		"+beanName+".remove("+paramName+"Key);");
				buf.putLine2("	}");
				buf.putLine2("}");
			}
			break;
			
		case RemoveAsListByCriteria:
			buf.putLine2("synchronized ("+beanName+") {");
			buf.putLine2("	List<"+typeName+"> "+paramName+"List = getFrom"+fieldName+"("+paramName+"Criteria);");
			buf.putLine2("	removeFrom"+fieldName+"("+paramName+"List);");
			buf.putLine2("}");
			break;
		}
		return buf.get();
	}

}
