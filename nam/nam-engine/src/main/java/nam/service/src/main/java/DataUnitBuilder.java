package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerFactory;
import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Process;
import nam.model.Type;
import nam.model.Unit;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.TestType;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelField;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelUnit;


public class DataUnitBuilder extends AbstractDataUnitBuilder {

	private DataUnitProvider dataUnitProvider;

	
	public DataUnitBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {
		dataUnitProvider = new DataUnitProvider(context);
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
		String className = DataLayerHelper.getPersistenceUnitClassName(unit);
		String beanName = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String beanType = TypeUtil.getTypeFromNamespaceAndLocalPart(unit.getNamespace(), beanName);

		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setType(beanType);
		modelClass.setParentClassName("AbstractDataUnit");
		modelClass.addImportedClass("org.aries.data.AbstractDataUnit");
		dataUnitProvider.setCurrentClass(modelClass);
		initializeClass(modelClass, unit);
		return modelClass; 
	}
	
	public void initializeClass(ModelClass modelClass, Unit unit) throws Exception {
		this.modelUnit = modelClass;
		initializeStaticFields(modelClass);
		initializeStaticMethods(modelClass);
		initializeClassConstructor(modelClass, unit);
		initializeInstanceFields(modelClass, unit);
		initializeInstanceMethods(modelClass, unit);
		initializeClassAnnotations(modelClass);
		initializeImportedClasses(modelClass);
	}

	protected void initializeClassConstructor(ModelClass modelClass, Unit unit) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		//constructor.addInitialSource(dataUnitProvider.generateSource_Constructor(cache));
		modelClass.addInstanceConstructor(constructor);
	}
	
	protected void initializeStaticFields(ModelClass modelClass) throws Exception {
		String unitName = modelClass.getClassName();
		modelClass.addStaticAttribute(createUnitNameAttribute(unitName));
	}
		
	public static ModelAttribute createUnitNameAttribute(String unitName) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL);
		attribute.setClassName(String.class.getName());
		attribute.setName("UNIT_NAME");
		attribute.setDefault("\""+unitName+"\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}

	protected void initializeInstanceFields(ModelClass modelClass, Unit unit) throws Exception {
		List<Element> elements = UnitUtil.getElements(unit);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			initializeInstanceFields(modelClass, unit, element);
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Unit unit, Element element) throws Exception {
		modelClass.addInstanceAttribute(DataLayerFactory.createRepositoryAttribute(context.getProcess(), unit));
		modelClass.addInstanceAttribute(CodeUtil.createAttribute_Mutex());
	}
	
	protected void initializeFieldAnnotations(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		List<ModelAnnotation> fieldAnnotations = AnnotationUtil.createModelAnnotations(field.getAnnotations());
		modelField.setAnnotations(fieldAnnotations);
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Unit unit) throws Exception {
		modelClass.addInstanceOperation(createOperation_GetUnitName(modelClass));
		modelClass.addInstanceOperation(createOperation_Initialize(modelClass));
		modelClass.addInstanceOperation(createOperation_ClearContext(modelClass));
		initializeInstanceMethods(modelClass, UnitUtil.getElements(unit));
		//modelClass.addInstanceOperation(createOperation_Equals(modelClass));
		//modelClass.addInstanceOperation(createOperation_Hashcode(modelClass));
	}

	protected void initializeInstanceMethods(ModelClass modelClass, List<Element> elements) throws Exception {
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			createMethods_DataAccess(modelClass, element, SourceType.SharedCache);
		}
	}

	protected ModelOperation createOperation_GetUnitName(ModelClass modelClass) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getUnitName");
		modelOperation.setResultType("String");

		modelOperation.addInitialSource("\t\treturn UNIT_NAME;\n");
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Initialize(ModelClass modelClass) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createPostConstructAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("initialize");
		modelOperation.setResultType("void");

		//modelOperation.addInitialSource("\t\t;\n");
		return modelOperation;
	}
	
	protected ModelOperation createOperation_ClearContext(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("clearContext");
		modelOperation.setResultType("void");

		String unitName = DataLayerHelper.getPersistenceUnitNameUncapped(context.getUnit());
		modelOperation.addInitialSource("\t\t"+unitName+"Repository.clearContext();\n");
		return modelOperation;
	}
	
	protected boolean isResultRequired(MethodType methodType) {
		if (methodType == MethodType.RemoveAll ||
			methodType == MethodType.RemoveAsItem ||
			methodType == MethodType.RemoveAsItemByKey ||
			methodType == MethodType.RemoveAsListByKeys ||
			methodType == MethodType.RemoveAsList ||
			methodType == MethodType.RemoveMatchingAsList)
				return false;
		return super.isResultRequired(methodType);
	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, TestType testType, Type dataItem) {
		String unitName = DataLayerHelper.getPersistenceUnitNameUncapped(context.getUnit());
		
		String type = dataItem.getType();
		String keyType = dataItem.getKey();
		String helperName = ModelLayerHelper.getModelHelperBeanName(type);
		String paramName = TypeUtil.getLocalPart(type);
		String keyClassName = TypeUtil.getClassName(keyType);
		//String keyParamName = TypeUtil.getLocalPart(keyType);
		String typeName = NameUtil.capName(paramName);
		String name = NameUtil.capName(dataItem.getName());
		String structure = dataItem.getStructure();
		
		Buf buf = new Buf();		
		switch (methodType) {
		case GetAllAsList: 
			buf.putLine2("return "+unitName+"Repository.getAll"+name+"Records();"); 
			break;
			
		case GetAllAsMap: 
			buf.putLine2("List<"+typeName+"> "+paramName+"List = "+unitName+"Repository.getAll"+name+"Records();");
			buf.putLine2("Map<"+keyClassName+", "+typeName+"> "+paramName+"Map = "+helperName+".create"+typeName+"Map("+paramName+"List);");
			buf.putLine2("return "+paramName+"Map;");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
			
		case GetAsItem: 
		case GetAsItemById:
			buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
			buf.putLine2("List<"+typeName+"> "+paramName+"List = "+unitName+"Repository.get"+name+"Records("+paramName+"Criteria);");
			buf.putLine2("Assert.isTrue("+paramName+"List.size() <= 1, \"Unexpected number of results\");");
			buf.putLine2("if ("+paramName+"List.size() == 1)");
			buf.putLine2("	return "+paramName+"List.get(0);");
			buf.putLine2("return null;");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
			
		case GetAsItemByKey:
			buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromKey("+paramName+"Key);");
			buf.putLine2("List<"+typeName+"> "+paramName+"List = "+unitName+"Repository.get"+name+"Records("+paramName+"Criteria);");
			buf.putLine2("Assert.isTrue("+paramName+"List.size() <= 1, \"Unexpected number of results\");");
			buf.putLine2("if ("+paramName+"List.size() == 1)");
			buf.putLine2("	return "+paramName+"List.get(0);");
			buf.putLine2("return null;");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
			
		//case GetAsList: 
		case GetAsListByIds:
			buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromIds("+paramName+"Ids);");
			buf.putLine2("return "+unitName+"Repository.get"+name+"Records("+paramName+"Criteria);");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
			
		case GetAsListByKeys:
			buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromKeys("+paramName+"Keys);");
			buf.putLine2("return "+unitName+"Repository.get"+name+"Records("+paramName+"Criteria);");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
			
		case GetAsMapByKeys:
			buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromKeys("+paramName+"Keys);");
			buf.putLine2("List<"+typeName+"> "+paramName+"List = "+unitName+"Repository.get"+name+"Records("+paramName+"Criteria);");
			buf.putLine2("Map<"+keyClassName+", "+typeName+"> "+paramName+"Map = "+helperName+".create"+typeName+"Map("+paramName+"List);");
			buf.putLine2("return "+paramName+"Map;");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
			
		case GetAsListByCriteria: 
			buf.putLine2("return "+unitName+"Repository.get"+name+"Records("+paramName+"Criteria);"); 
			break;
			
		case GetMatchingAsList:
			if (structure.equals("item")) {
			} else if (structure.equals("list")) {
				buf.putLine2("Collection<Long> "+paramName+"Ids = "+helperName+".getOrderIds("+paramName+"List);");
				buf.putLine2(typeName+"Criteria "+paramName+"Criteria = new "+typeName+"Criteria();");
				buf.putLine2(paramName+"Criteria.getIdList().addAll("+paramName+"Ids);");
				buf.putLine2("return getFrom"+name+"("+paramName+"Criteria);");
				addImportedClassForHelper(modelUnit, dataItem);
			} else if (structure.equals("set")) {
			} else if (structure.equals("map")) {
				buf.putLine2("Collection<"+keyClassName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
				buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromKeys("+paramName+"Keys);");
				buf.putLine2("List<"+typeName+"> matching"+typeName+"List = "+unitName+"Repository.get"+name+"Records("+paramName+"Criteria);");
				buf.putLine2("return matching"+typeName+"List;");
				addImportedClassForHelper(modelUnit, dataItem);
			}
			break;
			
		case GetMatchingAsMap:
			//buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromKeys("+paramName+"Keys);");
			buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"Criteria("+paramName+"List);");
			buf.putLine2("Collection<"+typeName+"> resultList = "+unitName+"Repository.get"+name+"Records("+paramName+"Criteria);");
			buf.putLine2("Map<"+keyClassName+", "+typeName+"> "+paramName+"Map = "+helperName+".create"+typeName+"Map(resultList);");
			buf.putLine2("return "+paramName+"Map;");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
			
		case Set: 
			buf.putLine2(unitName+"Repository.removeAll"+name+"Records();");
			buf.putLine2(unitName+"Repository.add"+name+"Records("+paramName+"List);");
			break;
			
		case AddAsItem: 
			buf.putLine2("return "+unitName+"Repository.add"+name+"Record("+paramName+");");
			break;
		
		case AddAsList: 
			buf.putLine2("return "+unitName+"Repository.add"+name+"Records("+paramName+"List);");
			break;
		
		case AddAsMap: 
			//buf.putLine2("List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">("+paramName+"Map.values());");
			buf.putLine2("return "+unitName+"Repository.add"+name+"Records("+paramName+"Map.values());");
			break;
		
		case RemoveAll: 
			buf.putLine2(""+unitName+"Repository.removeAll"+name+"Records();");
			break;
		
		case RemoveAsItem:
			buf.putLine2(""+unitName+"Repository.remove"+name+"Record("+paramName+");"); 
			break;
		
		case RemoveAsItemById:
			buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
			buf.putLine2(unitName+"Repository.remove"+name+"Records("+paramName+"Criteria);");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
			
		case RemoveAsItemByKey:
			buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromKey("+paramName+"Key);");
			buf.putLine2(unitName+"Repository.remove"+name+"Records("+paramName+"Criteria);");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
		
		case RemoveAsList:
			buf.putLine2(""+unitName+"Repository.remove"+name+"Records("+paramName+"List);"); 
			break;
		
		case RemoveAsListByKeys:
			buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromKeys("+paramName+"Keys);");
			buf.putLine2(unitName+"Repository.remove"+name+"Records("+paramName+"Criteria);");
			addImportedClassForHelper(modelUnit, dataItem);
			break;
		
		case RemoveAsListByCriteria:
			buf.putLine2(unitName+"Repository.remove"+name+"Records("+paramName+"Criteria);");
			break;
		}
		
		return buf.get();
	}
	
}
