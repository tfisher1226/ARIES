package nam.model.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Cache;
import nam.model.Element;
import nam.model.Field;
import nam.model.Information;
import nam.model.ModelLayerHelper;
import nam.model.Module;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Process;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.ExtensionsUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.PersistenceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.ModelOperationFactory;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class ModelHelperClassBuilder extends AbstractBeanBuilder {
	
	private ModelOperationFactory modelOperationFactory;
	
	
	public ModelHelperClassBuilder(GenerationContext context) {
		super(context);
		modelOperationFactory = new ModelOperationFactory(context);
	}

	public List<ModelClass> buildClasses(List<Namespace> namespaces) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Namespace> iterator = namespaces.iterator();
		Module module = context.getModule();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (namespace.getImported() == null || !namespace.getImported() || namespace.getUri().equals(module.getNamespace())) {
				ModelClass modelClass = buildClass(namespace);
				modelClasses.add(modelClass);
			}
		}
		return modelClasses;
	}

	public ModelClass buildClass(Namespace namespace) throws Exception {
		String packageName = ModelLayerHelper.getModelHelperPackageName(namespace);
		String className = ModelLayerHelper.getModelHelperClassName(namespace);
		ModelClass modelClass = new ModelClass();
		modelOperationFactory.reset(modelClass);
		modelClass.setType(TypeUtil.getTypeFromNamespace(namespace));
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(NameUtil.uncapName(className));
		modelClass.setNamespace(namespace.getUri());
		modelClass.setParentClassName("AbstractModelHelper");
		modelClass.addImportedClass("org.aries.util.AbstractModelHelper");
		initializeClass(modelClass, namespace);
		return modelClass;
	}

	protected void initializeClass(ModelClass modelClass, Namespace namespace) throws Exception {
		initializeImportedClasses(modelClass, namespace);
		initializeClassAnnotations(modelClass, namespace);
		initializeInstanceFields(modelClass, namespace);
		initializeInstanceOperations(modelClass, namespace);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Namespace namespace) throws Exception {
	}

	protected void initializeClassAnnotations(ModelClass modelClass, Namespace namespace) throws Exception {
	}

	protected void initializeInstanceFields(ModelClass modelClass, Namespace namespace) throws Exception {
		String uri = namespace.getUri();
		Persistence persistence = ExtensionsUtil.getPersistenceBlock(context.getProject(), uri);
		//Persistence persistence = ModuleUtil.getPersistenceBlock(context.getModule());
		if (persistence != null) {
			Collection<Unit> units = PersistenceUtil.getUnits(persistence);
			Iterator<Unit> unitIterator = units.iterator();
			while (unitIterator.hasNext()) {
				Unit unit = unitIterator.next();
				modelClass.addInstanceReference(modelOperationFactory.createReference_UnitManager(unit, uri));
			}
		}
		
		//Application application = context.getApplication();
		Module module = context.getModule();
		List<Process> processes = ModuleUtil.getProcesses(module);
		Iterator<Process> iterator = processes.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			List<Cache> caches = process.getCacheUnits();
			Iterator<Cache> cacheIterator = caches.iterator();
			while (cacheIterator.hasNext()) {
				Cache cache = cacheIterator.next();
				if (cache.getNamespace().equals(uri)) {
					modelClass.addInstanceReference(modelOperationFactory.createReference_CacheManager(cache, uri));
				}
			}
		}
		
		//String packageName = ProjectLevelHelper.getPackageName(uri);
		//ModelReference reference = modelOperationFactory.createReference_ObjectFactory(packageName, true);
		//reference.setValue("new bookshop2.ObjectFactory()");
		//modelClass.addInstanceReference(reference);
	}

	protected void initializeInstanceOperations(ModelClass modelClass, Namespace namespace) throws Exception {
		Information information = ExtensionsUtil.getInformationBlock(context.getProject(), namespace);
		List<Element> elements = NamespaceUtil.getElements(namespace);
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!ElementUtil.isUserDefined(element))
				continue;
			initializeInstanceOperations(modelClass, namespace, element);
		}
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		//String structure = element.getStructure();
		//boolean isMap = structure.equals("map");
		
		//TODO for now this is how we logically control what additional constructs are generated
		boolean hasKeyElement = context.getElementByType(element.getType()+"Key") != null;
		boolean hasCriteriaElement = context.getElementByType(element.getType()+"Criteria") != null;
		
		if (hasKeyElement) {
			createOperation_createKey(modelClass, namespace, element); 
			createOperation_createKeys(modelClass, namespace, element); 
			//createOperation_createKey2(modelClass, namespace, element); 
			createOperation_createElementList(modelClass, namespace, element); 
			createOperation_createElementMap(modelClass, namespace, element); 
		
			//createOperations_getFieldFromKey(modelClass, namespace, element); 
			//createOperation_getValueFromKey(modelClass, namespace, element); 
		}
		
		if (hasCriteriaElement) {
			createOperation_createCriteriaFromId(modelClass, namespace, element); 
			createOperation_createCriteriaFromIds(modelClass, namespace, element); 
			createOperation_createCriteriaFromKey(modelClass, namespace, element); 
			createOperation_createCriteriaFromKeys(modelClass, namespace, element); 
			createOperation_createCriteriaFromElement(modelClass, namespace, element); 
			createOperation_createCriteriaFromElements(modelClass, namespace, element); 
			createOperation_createCriteriaFromField(modelClass, namespace, element); 
		
			createOperation_addElementIdToCriteria(modelClass, namespace, element); 
			createOperation_addElementKeyToCriteria(modelClass, namespace, element); 
			createOperation_isMatch(modelClass, namespace, element); 
		}
		
		//createOperation_isMatch2(modelClass, namespace, element); 
		//createOperation_isMatch3(modelClass, namespace, element); 
		
		//createOperation_sortElementRecordsByKey(modelClass, namespace, element); 
		//createOperation_sortElementRecordsByField(modelClass, namespace, element); 
		//createOperation_sortElementRecords(modelClass, namespace, element); 
		//createOperation_createElementComparator(modelClass, namespace, element); 
	}

	//createKey(Element element) method
	protected void createOperation_createKey(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"Key");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped));
		modelOperation.setResultType(elementClassName+"Key");
		
		String keyElementType = element.getType()+"Key";
		Element keyElement = context.getElementByType(keyElementType);
		//String keyElementPackageName = ModelLayerHelper.getElementPackageName(keyElement);
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"Key "+elementNameUncapped+"Key = new "+elementClassName+"Key();");
		List<Field> requiredFields = ElementUtil.getRequiredFields(element);
		Iterator<Field> iterator = requiredFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String structure = field.getStructure();
			if (!structure.equals("item"))
				continue;

			if (ElementUtil.isFieldExists(keyElement, field)) {
				String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
				buf.putLine2(elementNameUncapped+"Key.set"+fieldNameCapped+"("+elementNameUncapped+".get"+fieldNameCapped+"());");
			}
		}
		buf.putLine2("return "+elementNameUncapped+"Key;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//createKeys(Collection<Element> elements) method
	protected void createOperation_createKeys(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
				
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"Keys");
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List"));
		modelOperation.setResultType("Collection<"+elementClassName+"Key>");
		
		Buf buf = new Buf();
		buf.putLine2("List<"+elementClassName+"Key> list = new ArrayList<"+elementClassName+"Key>();");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"List.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = iterator.next();");
		buf.putLine2("	"+elementClassName+"Key "+elementNameUncapped+"Key = create"+elementClassName+"Key("+elementNameUncapped+");");
		buf.putLine2("	list.add("+elementNameUncapped+"Key);");
		buf.putLine2("}");
		buf.putLine2("return list;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//createKey(Field field1, Field field2, ...) method
	protected void createOperation_createKey2(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"Key");
		modelOperation.setResultType("String");
		
		List<Field> requiredFields = ElementUtil.getRequiredFields(element);
		Iterator<Field> iterator = requiredFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String structure = field.getStructure();
			if (!structure.equals("item"))
				continue;

			String fieldType = field.getType();
			String fieldName = field.getName();
			String fieldPackageName = TypeUtil.getPackageName(fieldType);
			String fieldClassName = TypeUtil.getClassName(fieldType);
			modelOperation.addParameter(CodeUtil.createParameter("item", fieldPackageName, fieldClassName, fieldName));
		}
		
		Buf buf = new Buf();
		//buf.putLine2("");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//createElementMap(Collection<Element> elements) method
	protected void createOperation_createElementMap(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"Map");
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List"));
		modelOperation.setResultType("Map<"+elementClassName+"Key, "+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("Map<"+elementClassName+"Key, "+elementClassName+"> map = new LinkedHashMap<"+elementClassName+"Key, "+elementClassName+">();");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"List.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = iterator.next();");
		buf.putLine2("	"+elementClassName+"Key "+elementNameUncapped+"Key = create"+elementClassName+"Key("+elementNameUncapped+");");
		buf.putLine2("	map.put("+elementNameUncapped+"Key, "+elementNameUncapped+");");
		buf.putLine2("}");
		buf.putLine2("return map;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	//createElementList(Collection<Element> elements) method
	protected void createOperation_createElementList(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"List");
		modelOperation.addParameter(CodeUtil.createParameter("map", elementPackageName, elementClassName+"Key", elementClassName, elementNameUncapped+"Map"));
		modelOperation.setResultType("List<"+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("List<"+elementClassName+"> "+elementNameUncapped+"List = new ArrayList<"+elementClassName+">();");
		buf.putLine2(elementNameUncapped+"List.addAll("+elementNameUncapped+"Map.values());");
		buf.putLine2("return "+elementNameUncapped+"List;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//getFieldFromKey(Field field1) methods
	protected void createOperations_getFieldFromKey(ModelClass modelClass, Namespace namespace, Element element) {
		List<Field> requiredFields = ElementUtil.getRequiredFields(element);
		Iterator<Field> iterator = requiredFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String structure = field.getStructure();
			if (!structure.equals("item"))
				continue;
			createOperation_getFieldFromKey(modelClass, namespace, element, field);
		}
	}
	
	//getFieldFromKey(Field field1) methods
	protected void createOperation_getFieldFromKey(ModelClass modelClass, Namespace namespace, Element element, Field field) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		String fieldPackageName = ModelLayerHelper.getFieldPackageName(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("get"+fieldNameCapped+"From");
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "String", elementNameUncapped+"Key"));
		modelOperation.setResultType("Map<String, "+elementClassName+">");
		
		Buf buf = new Buf();
		buf.putLine2("return getValueFromKey(bookKey, 1);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//getValueFromKey(String key, int index) method
	protected void createOperation_getValueFromKey(ModelClass modelClass, Namespace namespace, Element element) {
	}

	//createCriteriaFromId(Long id) 
	protected void createOperation_createCriteriaFromId(ModelClass modelClass, Namespace namespace, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"CriteriaFromId");
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "Long", elementNameUncapped+"Id"));
		modelOperation.setResultType(elementClassName+"Criteria");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"Criteria "+elementNameUncapped+"Criteria = new "+elementClassName+"Criteria();");
		buf.putLine2("add"+elementClassName+"IdTo"+elementClassName+"Criteria(bookCriteria, "+elementNameUncapped+"Id);");
		buf.putLine2("return "+elementNameUncapped+"Criteria;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//createCriteriaFromIds(Collection<Long> ids) 
	protected void createOperation_createCriteriaFromIds(ModelClass modelClass, Namespace namespace, Element element) {
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"CriteriaFromIds");
		modelOperation.addParameter(CodeUtil.createParameter("collection", "java.lang", "Long", elementNameUncapped+"Ids"));
		modelOperation.setResultType(elementClassName+"Criteria");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"Criteria "+elementNameUncapped+"Criteria = new "+elementClassName+"Criteria();");
		buf.putLine2("Iterator<Long> iterator = "+elementNameUncapped+"Ids.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	Long "+elementNameUncapped+"Id = iterator.next();");
		buf.putLine2("	add"+elementClassName+"IdTo"+elementClassName+"Criteria("+elementNameUncapped+"Criteria, "+elementNameUncapped+"Id);");
		buf.putLine2("}");
		buf.putLine2("return "+elementNameUncapped+"Criteria;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//createCriteriaFromKey(ElementKey key) 
	protected void createOperation_createCriteriaFromKey(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"CriteriaFromKey");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName+"Key", elementNameUncapped+"Key"));
		modelOperation.setResultType(elementClassName+"Criteria");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"Criteria "+elementNameUncapped+"Criteria = new "+elementClassName+"Criteria();");
		buf.putLine2("add"+elementClassName+"KeyTo"+elementClassName+"Criteria("+elementNameUncapped+"Criteria, "+elementNameUncapped+"Key);");
		buf.putLine2("return "+elementNameUncapped+"Criteria;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	//createCriteriaFromKeys(Collection<ElementKey> keys) 
	protected void createOperation_createCriteriaFromKeys(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"CriteriaFromKeys");
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName+"Key", elementNameUncapped+"Keys"));
		modelOperation.setResultType(elementClassName+"Criteria");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"Criteria "+elementNameUncapped+"Criteria = new "+elementClassName+"Criteria();");
		buf.putLine2("Iterator<"+elementClassName+"Key> iterator = "+elementNameUncapped+"Keys.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+"Key "+elementNameUncapped+"Key = iterator.next();");
		buf.putLine2("	add"+elementClassName+"KeyTo"+elementClassName+"Criteria("+elementNameUncapped+"Criteria, "+elementNameUncapped+"Key);");
		buf.putLine2("}");
		buf.putLine2("return "+elementNameUncapped+"Criteria;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//createCriteriaFromElement(Element element) 
	protected void createOperation_createCriteriaFromElement(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"Criteria");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped));
		modelOperation.setResultType(elementClassName+"Criteria");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"Key "+elementNameUncapped+"Key = create"+elementClassName+"Key("+elementNameUncapped+");");
		buf.putLine2(elementClassName+"Criteria "+elementNameUncapped+"Criteria = new "+elementClassName+"Criteria();");
		buf.putLine2("add"+elementClassName+"KeyTo"+elementClassName+"Criteria("+elementNameUncapped+"Criteria, "+elementNameUncapped+"Key);");
		buf.putLine2("return "+elementNameUncapped+"Criteria;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//createCriteriaFromElements(Collection<Element> elements) 
	protected void createOperation_createCriteriaFromElements(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("create"+elementClassName+"Criteria");
		modelOperation.addParameter(CodeUtil.createParameter("collection", elementPackageName, elementClassName, elementNameUncapped+"List"));
		modelOperation.setResultType(elementClassName+"Criteria");
		
		Buf buf = new Buf();
		buf.putLine2(elementClassName+"Criteria "+elementNameUncapped+"Criteria = new "+elementClassName+"Criteria();");
		buf.putLine2("Iterator<"+elementClassName+"> iterator = "+elementNameUncapped+"List.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	"+elementClassName+" "+elementNameUncapped+" = iterator.next();");
		buf.putLine2("	"+elementClassName+"Key "+elementNameUncapped+"Key = create"+elementClassName+"Key("+elementNameUncapped+");");
		buf.putLine2("	add"+elementClassName+"KeyTo"+elementClassName+"Criteria("+elementNameUncapped+"Criteria, "+elementNameUncapped+"Key);");
		buf.putLine2("}");
		buf.putLine2("return "+elementNameUncapped+"Criteria;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//createCriteriaFromField(Field field1) 
	protected void createOperation_createCriteriaFromField(ModelClass modelClass, Namespace namespace, Element element) {
	}

	//addIdToCriteria(ElementCriteria elementCriteria, Long id) 
	protected void createOperation_addElementIdToCriteria(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("add"+elementClassName+"IdTo"+elementClassName+"Criteria");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName+"Criteria", elementNameUncapped+"Criteria"));
		modelOperation.addParameter(CodeUtil.createParameter("item", "java.lang", "Long", elementNameUncapped+"Id"));
		
		Buf buf = new Buf();
		buf.putLine2(elementNameUncapped+"Criteria.getIdList().add("+elementNameUncapped+"Id);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//addKeyToCriteria(ElementCriteria elementCriteria, ElementKey key) 
	protected void createOperation_addElementKeyToCriteria(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		String keyElementType = element.getType()+"Key";
		Element keyElement = context.getElementByType(keyElementType);
		//String keyElementPackageName = ModelLayerHelper.getElementPackageName(keyElement);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("add"+elementClassName+"KeyTo"+elementClassName+"Criteria");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName+"Criteria", elementNameUncapped+"Criteria"));
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName+"Key", elementNameUncapped+"Key"));
		
		Buf buf = new Buf();
		List<Field> requiredFields = ElementUtil.getRequiredFields(element);
		Iterator<Field> iterator = requiredFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String structure = field.getStructure();
			if (!structure.equals("item"))
				continue;

			if (ElementUtil.isFieldExists(keyElement, field)) {
				String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
				String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
				buf.putLine2(elementNameUncapped+"Criteria.addToFieldMap(\""+fieldNameUncapped+"\", "+elementNameUncapped+"Key.get"+fieldNameCapped+"());");
			}
		}
		
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//isMatch(ElementCriteria elementCriteria, Element element);
	protected void createOperation_isMatch(ModelClass modelClass, Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.setName("isMatch");
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName+"Criteria", elementNameUncapped+"Criteria"));
		modelOperation.addParameter(CodeUtil.createParameter("item", elementPackageName, elementClassName, elementNameUncapped));
		modelOperation.setResultType("boolean");
		
		String keyElementType = element.getType()+"Key";
		Element keyElement = context.getElementByType(keyElementType);
		//String keyElementPackageName = ModelLayerHelper.getElementPackageName(keyElement);
		
		Buf buf = new Buf();
		buf.putLine2("boolean status ="); 
		buf.putLine2("	isMatch("+elementNameUncapped+"Criteria.getIdList(), "+elementNameUncapped+".getId()) &&");
		
		List<Field> requiredFields = ElementUtil.getRequiredFields(element);
		Iterator<Field> iterator = requiredFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String structure = field.getStructure();
			if (!structure.equals("item"))
				continue;

			if (ElementUtil.isFieldExists(keyElement, field)) {
				String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
				String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
				buf.putLine2("	isMatch("+elementNameUncapped+"Criteria.getFieldValue(\""+fieldNameUncapped+"\"), "+elementNameUncapped+".get"+fieldNameCapped+"()) &&");
			}
		}
		
		buf.putLine2("	isMatch("+elementNameUncapped+"Criteria.getKeyList(), create"+elementClassName+"Key("+elementNameUncapped+"));");
		buf.putLine2("return status;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	//isMatch(Object value1, Object value2);
	protected void createOperation_isMatch2(ModelClass modelClass, Namespace namespace, Element element) {
	}

	//isMatch(Collection<Element> elementList, Element element);
	protected void createOperation_isMatch3(ModelClass modelClass, Namespace namespace, Element element) {
	}

	//sortElementRecordsByKey(Collection<Element> elements) 
	protected void createOperation_sortElementRecordsByKey(ModelClass modelClass, Namespace namespace, Element element) {
	}

	//sortElementRecordsByField(Collection<Element> elements) 
	protected void createOperation_sortElementRecordsByField(ModelClass modelClass, Namespace namespace, Element element) {
	}

	//sortElementRecords(Collection<Element> elements, Comparator comparator) 
	protected void createOperation_sortElementRecords(ModelClass modelClass, Namespace namespace, Element element) {
	}

	//createElementComparator()
	protected void createOperation_createElementComparator(ModelClass modelClass, Namespace namespace, Element element) {
	}

}
