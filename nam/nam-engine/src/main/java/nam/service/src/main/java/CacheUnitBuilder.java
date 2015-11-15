package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Application;
import nam.model.Attribute;
import nam.model.Cache;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Process;
import nam.model.Reference;
import nam.model.Type;
import nam.model.util.CacheUtil;
import nam.model.util.ElementUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelField;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelUnit;


public class CacheUnitBuilder extends AbstractDataUnitBuilder {

	private CacheUnitProvider cacheUnitProvider;

	
	public CacheUnitBuilder(GenerationContext context) {
		super(context);
		initialize();
	}
	
	protected boolean isCacheUnit() {
		return true;
	}

	protected void initialize() {
		cacheUnitProvider = new CacheUnitProvider(context);
	}

	
	/*
	 * Interface construction
	 */
	
	public List<ModelInterface> buildInterfaces(Process process) throws Exception {
		return buildInterfaces(process.getNamespace(), process.getCacheUnits());
	}
	
	public List<ModelInterface> buildInterfaces(String namespace, List<Cache> cacheUnits) throws Exception {
		List<ModelInterface> modelInterfaces = new ArrayList<ModelInterface>();
		Iterator<Cache> iterator = cacheUnits.iterator();
		while (iterator.hasNext()) {
			Cache cacheUnit = iterator.next();
			context.setCache(cacheUnit);
			modelInterfaces.add(buildInterface(namespace, cacheUnit));
		}
		return modelInterfaces;
	}
	
	public ModelInterface buildInterface(String namespace, Cache cacheUnit) throws Exception {
		String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cacheUnit);
		String interfaceName = DataLayerHelper.getCacheUnitInterfaceName(cacheUnit);
		String rootName = CacheUtil.getRootName(cacheUnit);
		String beanName = CacheUtil.getCacheName(cacheUnit);
		String typeName = CacheUtil.getType(cacheUnit);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(interfaceName);
		ModelInterface modelInterface = new ModelInterface();
		modelInterface.setType(typeName);
		modelInterface.setPackageName(packageName);
		modelInterface.setClassName(interfaceName);
		modelInterface.setName(beanName);
		//cacheUnitProvider.setCurrentClass(modelInterface);
		initializeInterface(modelInterface, cacheUnit);
		return modelInterface;
	}

	public void initializeInterface(ModelInterface modelInterface, Cache cacheUnit) throws Exception {
		this.modelUnit = modelInterface;
		initializeInstanceMethods(modelInterface, cacheUnit);
		initializeImportedClasses(modelInterface);
	}
	
	protected void initializeInstanceMethods(ModelInterface modelInterface, Cache cacheUnit) throws Exception {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(CacheUtil.getAttributes(cacheUnit));
		fields.addAll(CacheUtil.getReferences(cacheUnit));
		createMethods_DataAccess(modelInterface, fields, SourceType.SharedCache);
	}
	
	
	/*
	 * Class construction
	 */
	
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
	
	public ModelClass buildClass(String namespace, Cache cacheUnit) throws Exception {
		String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cacheUnit);
		String className = DataLayerHelper.getCacheUnitClassName(cacheUnit);
		String interfaceName = DataLayerHelper.getCacheUnitInterfaceName(cacheUnit);
		String rootName = CacheUtil.getRootName(cacheUnit);
		String beanName = CacheUtil.getCacheName(cacheUnit);
		String typeName = CacheUtil.getType(cacheUnit);

		setRootName(rootName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setType(typeName);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setParentClassName("AbstractCachePeer");
		modelClass.addImportedClass("org.aries.cache.AbstractCachePeer");
		modelClass.addImplementedInterface(interfaceName);
		cacheUnitProvider.setCurrentClass(modelClass);
		initializeClass(modelClass, cacheUnit);
		return modelClass; 
	}
	
	public void initializeClass(ModelClass modelClass, Cache cacheUnit) throws Exception {
		this.modelUnit = modelClass;
		initializeStaticFields(modelClass, cacheUnit);
		initializeStaticMethods(modelClass);
		initializeClassConstructor(modelClass, cacheUnit);
		initializeInstanceFields(modelClass, cacheUnit);
		initializeInstanceMethods(modelClass, cacheUnit);
		initializeClassAnnotations(modelClass);
		initializeImportedClasses(modelClass);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		super.initializeImportedClasses(modelClass);
		modelUnit.addImportedClass("java.util.Iterator");
		modelUnit.addImportedClass("java.util.ArrayList");
		modelUnit.addImportedClass("org.aries.Assert");
	}
	
	protected void initializeClassConstructor(ModelClass modelClass, Cache cache) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		//constructor.addInitialSource(cacheUnitProvider.generateSource_Constructor(cache));
		modelClass.addInstanceConstructor(constructor);
	}
	
	protected void initializeStaticFields(ModelClass modelClass, Cache cacheUnit) throws Exception {
		Application application = context.getApplication();
		String applicationId = NameUtil.uncapName(application.getArtifactId());
		String cachePeerName = NameUtil.capName(application.getName()) + "CacheManager";
		String cacheName = DataLayerHelper.getCacheUnitInterfaceName(cacheUnit);
		
		modelClass.addStaticAttribute(createConfigurationFileAttribute(applicationId));
		modelClass.addStaticAttribute(createCachePeerNameAttribute(cachePeerName));
		modelClass.addStaticAttribute(createCacheNameAttribute(cacheName));
	}
	
	public static ModelAttribute createConfigurationFileAttribute(String domainName) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL);
		attribute.setClassName(String.class.getName());
		attribute.setName("CONFIGURATION_FILE_NAME");
		attribute.setDefault("\""+domainName+"-ehcache-jgroups.xml\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public static ModelAttribute createCachePeerNameAttribute(String cachePeerName) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL);
		attribute.setClassName(String.class.getName());
		attribute.setName("CACHE_PEER_NAME");
		attribute.setDefault("\""+cachePeerName+"\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public static ModelAttribute createCacheNameAttribute(String cacheName) {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL);
		attribute.setClassName(String.class.getName());
		attribute.setName("CACHE_NAME");
		attribute.setDefault("\""+cacheName+"\"");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Element element) throws Exception {
		//super.initializeInstanceFields(modelClass, element);
		//modelClass.addInstanceAttribute(CodeUtil.createAttribute_Mutex());
		
//		List<ModelField> modelFields = new ArrayList<ModelField>();
//		modelFields.addAll(modelClass.getInstanceAttributes());
//		modelFields.addAll(modelClass.getInstanceReferences());
//		Iterator<ModelField> iterator = modelFields.iterator();
//		while (iterator.hasNext()) {
//			ModelField modelField = iterator.next();
//			modelField.setGenerateGetter(false);
//			modelField.setGenerateSetter(false);
//		}
	}
	
	protected void initializeInstanceFieldType_NOTUSED(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		String itemType = field.getType();
		String className = TypeUtil.getClassName(itemType);
		String packageName = TypeUtil.getPackageName(itemType);
		String javaType = packageName+"."+className;
		String structure = field.getStructure();
		modelClass.addImportedClass(javaType);

		if (field instanceof Attribute) {
			if (structure == null) {
				modelField.setPackageName(packageName);
				modelField.setClassName(className);

			} else if (structure.equals("list")) {
				modelField.setPackageName(packageName);
				//modelField.setClassName("List<"+className+">");
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.List");

			} else if (structure.equals("set")) {
				modelField.setPackageName(packageName);
				//modelField.setClassName("Set<"+className+">");
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.Set");

			} else if (structure.equals("map")) {
				modelField.setPackageName(packageName);
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.Map");
				//key
				String keyType = field.getKey();
				String keyPackageName = TypeUtil.getPackageName(keyType);
				String keyClassName = TypeUtil.getClassName(keyType);
				modelField.setKeyPackageName(keyPackageName);
				modelField.setKeyClassName(keyClassName);
			}

		} else if (field instanceof Reference) {
			if (structure == null) {
				modelField.setPackageName(packageName);
				modelField.setClassName(className);

			} else if (structure.equals("list")) {
				modelField.setPackageName(packageName);
				//modelField.setClassName("List<"+className+">");
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.List");
				modelClass.addImportedClass("java.util.ArrayList");

			} else if (structure.equals("set")) {
				modelField.setPackageName(packageName);
				//modelField.setClassName("Set<"+className+">");
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.Set");
				modelClass.addImportedClass("java.util.HashSet");

			} else if (structure.equals("map")) {
				modelField.setPackageName(packageName);
				modelField.setClassName(className);
				modelClass.addImportedClass("java.util.Map");
				modelClass.addImportedClass("java.util.HashMap");
				//key
				String keyType = field.getKey();
				String keyPackageName = TypeUtil.getPackageName(keyType);
				String keyClassName = TypeUtil.getClassName(keyType);
				modelClass.addImportedClass(keyPackageName+"."+keyClassName);
				modelField.setKeyPackageName(keyPackageName);
				modelField.setKeyClassName(keyClassName);
			}
		}
	}

	protected void initializeFieldAnnotations(ModelClass modelClass, ModelField modelField, Element element, Field field) throws Exception {
		List<ModelAnnotation> fieldAnnotations = AnnotationUtil.createModelAnnotations(field.getAnnotations());
		modelField.setAnnotations(fieldAnnotations);
	}

	protected void initializeInstanceMethods(ModelClass modelClass, Cache cacheUnit) throws Exception {
		modelClass.addInstanceOperation(createOperation_GetCachePeerName(modelClass));
		modelClass.addInstanceOperation(createOperation_GetCacheName(modelClass));
		modelClass.addInstanceOperation(createOperation_Initialize(modelClass));
		
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(CacheUtil.getAttributes(cacheUnit));
		fields.addAll(CacheUtil.getReferences(cacheUnit));
		createMethods_DataAccess(modelClass, fields, SourceType.SharedCache);
		
		//modelClass.addInstanceOperation(createOperation_Equals(modelClass));
		//modelClass.addInstanceOperation(createOperation_Hashcode(modelClass));
		//TODO modelClass.addInstanceOperation(createToStringOperation());
	}

	protected ModelOperation createOperation_GetCachePeerName(ModelClass modelClass) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("getCachePeerName");
		modelOperation.setResultType("String");

		modelOperation.addInitialSource("\t\treturn CACHE_PEER_NAME;\n");
		return modelOperation;
	}
	
	protected ModelOperation createOperation_GetCacheName(ModelClass modelClass) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("getCacheName");
		modelOperation.setResultType("String");

		modelOperation.addInitialSource("\t\treturn CACHE_NAME;\n");
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Initialize(ModelClass modelClass) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createPostConstructAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize");
		modelOperation.setResultType("void");

		modelOperation.addInitialSource("\t\tinitialize(CONFIGURATION_FILE_NAME);\n");
		return modelOperation;
	}
	
	protected String getDataAccessMethodSuffix(MethodType methodType, String structure) {
		switch (methodType) {
		case GetAsItemByKey:
		case RemoveAsItemByKey: 
			return "";
		case GetAsListByKeys:
		case RemoveAsListByKeys: 
			return "";
		default: 
			return super.getDataAccessMethodSuffix(methodType, structure);
		}
	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, Type dataItem) {
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		
		String type = dataItem.getType();
		String keyType = dataItem.getKey();
		String paramName = TypeUtil.getLocalPart(type);
		String keyParamName = null;
		String keyTypeName = "String";
		if (keyType != null) {
			keyParamName = TypeUtil.getLocalPart(keyType);
			keyTypeName = NameUtil.capName(keyParamName);
		}
		String typeName = NameUtil.capName(paramName);
		String name = NameUtil.capName(dataItem.getName());
		String structure = dataItem.getStructure();

		String helperName = ModelLayerHelper.getModelHelperBeanName(type);
		String fixtureName = ModelLayerHelper.getFixtureClassName(type);

		Element keyElement = context.getElementByType(dataItem.getType()+"Key");
		if (keyElement != null)
			addImportedClass(modelUnit, keyElement.getType());

		Buf buf = new Buf();
		switch (methodType) {
		case GetAllAsList: 
			if (structure.equals("list")) {
				buf.putLine2("return getElements(\""+name+"\");"); 
				//buf.putLine2("return getElements(\""+name+"\", "+helperName+".create"+typeName+"Key("+paramName+"), "+paramName+");");
			} else if (structure.equals("set")) {
				buf.putLine2("return getElementsAsSet(\""+name+"\");"); 
				//buf.putLine2("return getElementsAsSet(\""+name+"\", "+helperName+".create"+typeName+"Key("+paramName+"), "+paramName+");");
			} else if (structure.equals("map")) {
				buf.putLine2("return getElements(\""+name+"\");"); 
			}
			break;
		
		case GetAllAsMap: 
			addImportedClassForHelper(modelUnit, dataItem);
			buf.putLine2("List<"+typeName+"> allElements = getAllElements(\""+name+"\");");
			buf.putLine2("Map<"+keyTypeName+", "+typeName+"> map = "+helperName+".create"+typeName+"Map(allElements);");
			buf.putLine2("return map;");
			break;
		
		case GetAsItem: 
			if (structure.equals("item")) {
				buf.putLine2("return getElement(\""+name+"\");"); 
			} else {
				buf.putLine2("return getElement(\""+name+"\", "+paramName+"Key);"); 
			}
			break;
		
		case GetAsItemById: 
			if (structure.equals("set")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
				buf.putLine2("Set<"+typeName+"> "+paramName+"Set = getFrom"+name+"("+paramName+"Criteria);");
				buf.putLine2("Assert.isTrue("+paramName+"Set.size() <= 1, \"Multiple records found for Book ID: \"+"+paramName+"Id);");
				buf.putLine2("if ("+paramName+"Set.size() == 1)");
				buf.putLine2("	return "+paramName+"Set.iterator().next();");
				buf.putLine2("return null;");
			} else {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
				buf.putLine2("List<"+typeName+"> "+paramName+"List = getFrom"+name+"("+paramName+"Criteria);");
				buf.putLine2("Assert.isTrue("+paramName+"List.size() <= 1, \"Multiple records found for Book ID: \"+"+paramName+"Id);");
				buf.putLine2("if ("+paramName+"List.size() == 1)");
				buf.putLine2("	return "+paramName+"List.get(0);");
				buf.putLine2("return null;");
			}
			break;
		
		case GetAsItemByKey: 
			buf.putLine2("return getElement(\""+name+"\", "+paramName+"Key);"); 
			break;
		
		case GetAsList: 
			buf.putLine2("return getElements(\""+name+"\", "+paramName+"Keys);"); 
			break;
		
		case GetAsListByIds: 
			if (structure.equals("set")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromIds("+paramName+"Ids);");
				buf.putLine2("Set<"+typeName+"> "+paramName+"Set = getFrom"+name+"("+paramName+"Criteria);");
				buf.putLine2("return "+paramName+"Set;");
			} else {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromIds("+paramName+"Ids);");
				buf.putLine2("List<"+typeName+"> "+paramName+"List = getFrom"+name+"("+paramName+"Criteria);");
				buf.putLine2("return "+paramName+"List;");
			}
			break;
			
		case GetAsListByKeys: 
			buf.putLine2("return getElements(\""+name+"\", "+paramName+"Keys);"); 
			break;
		
		case GetAsMapByKeys: 
			addImportedClassForHelper(modelUnit, dataItem);
			buf.putLine2("List<"+typeName+"> elements = getElements(\""+name+"\", "+paramName+"Keys);");
			buf.putLine2("Map<"+keyTypeName+", "+typeName+"> map = "+helperName+".create"+typeName+"Map(elements);");
			buf.putLine2("return map;");
			
			break;
		
		case GetAsListByCriteria: 
			if (structure.equals("set")) {
				addImportedClassForHelper(modelUnit, dataItem);
				modelUnit.addImportedClass("java.util.HashSet");
				buf.putLine2("Set<"+typeName+"> "+paramName+"Set = new HashSet<"+typeName+">();");
				buf.putLine2("List<"+typeName+"> elements = getElements(\""+name+"\");");
				buf.putLine2("Iterator<"+typeName+"> iterator = elements.iterator();");
				buf.putLine2("while (iterator.hasNext()) {");
				buf.putLine2("	"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("	if ("+helperName+".isMatch("+paramName+"Criteria, "+paramName+")) {");
				buf.putLine2("		"+paramName+"Set.add("+paramName+");");
				buf.putLine2("	}");
				buf.putLine2("}");
				buf.putLine2("return "+paramName+"Set;");
			} else {
				addImportedClassForHelper(modelUnit, dataItem);
				modelUnit.addImportedClass("java.util.ArrayList");
				buf.putLine2("List<"+typeName+"> "+paramName+"List = new ArrayList<"+typeName+">();");
				buf.putLine2("List<"+typeName+"> elements = getElements(\""+name+"\");");
				buf.putLine2("Iterator<"+typeName+"> iterator = elements.iterator();");
				buf.putLine2("while (iterator.hasNext()) {");
				buf.putLine2("	"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("	if ("+helperName+".isMatch("+paramName+"Criteria, "+paramName+")) {");
				buf.putLine2("		"+paramName+"List.add("+paramName+");");
				buf.putLine2("	}");
				buf.putLine2("}");
				buf.putLine2("return "+paramName+"List;");
			}
			break;
		
		case GetMatchingAsList:
			if (structure.equals("list") || structure.equals("map")) {
				addImportedClassForFixture(modelUnit, dataItem);
				buf.putLine2("List<"+typeName+"> all"+typeName+"List = getAll"+name+"();");
				buf.putLine2("List<"+typeName+"> matching"+typeName+"List = new ArrayList<"+typeName+">("+paramName+"List.size());");
				buf.putLine2("Iterator<"+typeName+"> iterator = "+paramName+"List.iterator();");
				buf.putLine2("while (iterator.hasNext()) {");
				buf.putLine2("	"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("	if ("+fixtureName+".contains"+typeName+"(all"+typeName+"List, "+paramName+")) {");
				buf.putLine2("		matching"+typeName+"List.add("+paramName+");");
				buf.putLine2("	}");
				buf.putLine2("}");
				buf.putLine2("return matching"+typeName+"List;");

			} else if (structure.equals("set")) {
				addImportedClassForFixture(modelUnit, dataItem);
				buf.putLine2("Set<"+typeName+"> all"+typeName+"Set = getAll"+name+"();");
				buf.putLine2("Set<"+typeName+"> matching"+typeName+"Set = new HashSet<"+typeName+">("+paramName+"Set.size());");
				buf.putLine2("Iterator<"+typeName+"> iterator = "+paramName+"Set.iterator();");
				buf.putLine2("while (iterator.hasNext()) {");
				buf.putLine2("	"+typeName+" "+paramName+" = iterator.next();");
				buf.putLine2("	if ("+fixtureName+".contains"+typeName+"(all"+typeName+"Set, "+paramName+")) {");
				buf.putLine2("		matching"+typeName+"Set.add("+paramName+");");
				buf.putLine2("	}");
				buf.putLine2("}");
				buf.putLine2("return matching"+typeName+"Set;");
			}
			break;
		
		case GetMatchingAsMap:
			addImportedClassForHelper(modelUnit, dataItem);
			buf.putLine2("Collection<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
			buf.putLine2("return getFrom"+name+"AsMap("+paramName+"Keys);");
			break;
		
		case Set: 
			if (structure.equals("item")) {
				buf.putLine2("putElement(\""+name+"\", "+paramName+");");
			} else if (structure.equals("list")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("replaceElements(\""+name+"\", "+helperName+".create"+typeName+"Keys("+paramName+"List), "+paramName+"List);");
			} else if (structure.equals("set")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("replaceElements(\""+name+"\", "+helperName+".create"+typeName+"Keys("+paramName+"Set), "+paramName+"Set);");
			} else if (structure.equals("map")) {
				buf.putLine2("replaceElements(\""+name+"\", "+paramName+"Map);");
			}
			break;
		
		case AddAsItem: 
			if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				//buf.putLine2("putElement(\""+name+"\", "+helperName+".create"+typeName+"Key("+paramName+"), "+paramName+");");
				buf.putLine2(keyTypeName+" "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
				buf.putLine2("putElement(\""+name+"\", "+paramName+"Key, "+paramName+");");
			} else {
				addImportedClassForHelper(modelUnit, dataItem);
				//buf.putLine2("Long "+paramName+"Id = "+paramName+".getId();");
				buf.putLine2("putElement(\""+name+"\", "+helperName+".create"+typeName+"Key("+paramName+"), "+paramName+");");
			}
			break;
		
		case AddAsList: 
			if (structure.equals("list")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("putElements(\""+name+"\", "+helperName+".create"+typeName+"Keys("+paramName+"List), "+paramName+"List);");
			} else if (structure.equals("set")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("putElements(\""+name+"\", "+helperName+".create"+typeName+"Keys("+paramName+"Set), "+paramName+"Set);");
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = "+helperName+".create"+typeName+"Map("+paramName+"List);");
				buf.putLine2("putElements(\""+name+"\", "+paramName+"Map);");
			}
			break;
		
		case AddAsMap: 
			buf.putLine2("putElements(\""+name+"\", "+paramName+"Map);");
			break;
		
		case RemoveAll: 
			if (structure.equals("list")) {
				buf.putLine2("removeAllElements(\""+name+"\");");
				//buf.putLine2("Collection<"+typeName+"> elements = removeAllElements(\""+name+"\");");
				//buf.putLine2("return new ArrayList<"+typeName+">(elements);");
			} else if (structure.equals("set")) {
				buf.putLine2("removeAllElements(\""+name+"\");");
				//buf.putLine2("Collection<"+typeName+"> elements = removeAllElements(\""+name+"\");");
				//buf.putLine2("return new HashSet<"+typeName+">(elements);");
			} else if (structure.equals("map")) {
				buf.putLine2("removeAllElements(\""+name+"\");");
				//buf.putLine2("Collection<"+typeName+"> elements = removeAllElements(\""+name+"\");");
				//buf.putLine2("return new ArrayList<"+typeName+">(elements);");
			}
			break;

		case RemoveAsItem:
			addImportedClassForHelper(modelUnit, dataItem);
			//modelUnit.addImportedClass(TypeUtil.getQualifiedName(dataItem.getType()+"Key"));
			//buf.putLine2("removeElementByKey(\""+name+"\", "+helperName+".create"+typeName+"Key("+paramName+"));");
			buf.putLine2(typeName+"Key "+paramName+"Key = "+helperName+".create"+typeName+"Key("+paramName+");");
			buf.putLine2("removeElementByKey(\""+name+"\", "+paramName+"Key);"); 
			break;
		
		case RemoveAsItemById:
			addImportedClassForHelper(modelUnit, dataItem);
			buf.putLine2(typeName+"Criteria "+paramName+"Criteria = "+helperName+".create"+typeName+"CriteriaFromId("+paramName+"Id);");
			buf.putLine2("removeFrom"+name+"("+paramName+"Criteria);");
			break;
		
		case RemoveAsItemByKey:
			buf.putLine2("removeElementByKey(\""+name+"\", "+paramName+"Key);"); 
			break;
		
		case RemoveAsListByKeys:
			buf.putLine2("removeElementsByKey(\""+name+"\", "+paramName+"Keys);"); 
			break;
		
		case RemoveAsList:
			if (structure.equals("list")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("removeElementsByKey(\""+name+"\", "+helperName+".create"+typeName+"Keys("+paramName+"List));");
				//buf.putLine2("removeElements(\""+name+"\", "+helperName+".create"+typeName+"Keys("+paramName+"List), "+paramName+"List);");
				//buf.putLine2("return new ArrayList<Book>(removeElements(\""+name+"\", "+helperName+".create"+typeName+"Keys("+paramName+"List), "+paramName+"List));");
			} else if (structure.equals("set")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("removeElementsByKey(\""+name+"\", "+helperName+".create"+typeName+"Keys("+paramName+"Set));");
				//buf.putLine2("removeElements(\""+name+"\", "+helperName+".create"+typeName+"Keys("+paramName+"Set), "+paramName+"Set);");
				//buf.putLine2("return new HashSet<Book>(removeElements(\""+name+"\", "+helperName+".create"+typeName+"Keys("+paramName+"Set), "+paramName+"Set));");
			} else if (structure.equals("map")) {
				addImportedClassForHelper(modelUnit, dataItem);
				buf.putLine2("Collection<"+keyTypeName+"> "+paramName+"Keys = "+helperName+".create"+typeName+"Keys("+paramName+"List);");
				buf.putLine2("removeElementsByKey(\""+name+"\", "+paramName+"Keys);"); 
				//buf.putLine2("removeElements(\""+name+"\", "+paramName+"Keys, "+paramName+"List);"); 
				//buf.putLine2("return removeElements(\""+name+"\", "+paramName+"List);"); 
			}
			break;
			
		case RemoveAsListByCriteria:
			if (structure.equals("set")) {
				buf.putLine2("Set<"+typeName+"> "+paramName+"Set = getFrom"+name+"("+paramName+"Criteria);");
				buf.putLine2("removeFrom"+name+"("+paramName+"Set);");
			} else {
				buf.putLine2("List<"+typeName+"> "+paramName+"List = getFrom"+name+"("+paramName+"Criteria);");
				buf.putLine2("removeFrom"+name+"("+paramName+"List);");
			}
			break;
		}
		
		return buf.get();
	}
	
}
