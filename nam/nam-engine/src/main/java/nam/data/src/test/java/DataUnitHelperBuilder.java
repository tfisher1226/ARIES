package nam.data.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.data.DataLayerHelper;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Reference;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


public class DataUnitHelperBuilder extends AbstractDataUnitHelperBuilder {

	public DataUnitHelperBuilder(GenerationContext context) {
		super(context);
	}

	protected void initialize() {
	}
	
	public ModelClass buildClass(Unit unit) throws Exception {
		return buildClass(unit, unit.getNamespace());
	}
	
	protected ModelClass buildClass(Unit unit, Namespace namespace) throws Exception {
		String packageName = DataLayerHelper.getHelperPackageName(unit);
		String className = DataLayerHelper.getHelperClassName(unit);
		String beanName = DataLayerHelper.getHelperNameUncapped(unit);

		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		initializeClass(modelClass, unit, namespace);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Unit unit, Namespace namespace) throws Exception {
		List<Element> elements = UnitUtil.getElements(unit);
		//List<Element> elements = UnitUtil.getFunctionalElementsBasedOnName(unit);
		initializeImportedClasses(modelClass, unit, namespace, elements);
		initializeClassAnnotations(modelClass);
		initializeInstanceConstructor(modelClass, unit, namespace, elements);
		initializeInstanceFields(modelClass, unit, namespace, elements);
		initializeInstanceMethods(modelClass, unit, namespace, elements);
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Unit unit, Namespace namespace, List<Element> elements) {
		String mapperFixtureQualifiedName = DataLayerHelper.getMapperFixtureQualifiedName(namespace);
		
		modelClass.addImportedClass("javax.persistence.EntityManager");
		modelClass.addImportedClass(mapperFixtureQualifiedName);
		
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);
			String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, element);
			String modelFixtureQualifiedName = ModelLayerHelper.getModelFixtureQualifiedName(element);
			modelClass.addImportedClass(elementQualifiedName);
			modelClass.addImportedClass(entityQualifiedName);
			modelClass.addImportedClass(modelFixtureQualifiedName);
		}
	}

	protected void initializeInstanceConstructor(ModelClass modelClass, Unit unit, Namespace namespace, List<Element> elements) { 
		modelClass.getInstanceConstructors().add(createInstanceConstructor(modelClass, unit, namespace, elements));
	}

	protected ModelConstructor createInstanceConstructor(ModelClass modelClass, Unit unit, Namespace namespace, List<Element> elements) {
		ModelConstructor modelConstructor = new ModelConstructor();
		modelConstructor.setModifiers(Modifier.PUBLIC);
		
		Buf buf = new Buf();
		buf.putLine2("jmxProxy = new JmxProxy();");
		modelConstructor.addInitialSource(buf.get());
		modelClass.addImportedClass("javax.persistence.EntityManager");
		return modelConstructor;
	}
	

	/*
	 * Instance fields:
	 */
	
	protected void initializeInstanceFields(ModelClass modelClass, Unit unit, Namespace namespace, List<Element> elements) throws Exception {
		modelClass.addInstanceReference(createStaticField_EntityManagerFactory(modelClass));
		modelClass.addInstanceReference(createInstanceField_TransactionTestControl(modelClass));
		modelClass.addInstanceReference(createInstanceField_DataLayerTestControl(modelClass, unit));
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			initializeInstanceFields(modelClass, namespace, element);
		}
		modelClass.addInstanceReference(createInstanceField_EntityManager(modelClass));
		modelClass.addInstanceAttribute(createInstanceField_RunningInContainer(modelClass));
		modelClass.addInstanceReference(createInstanceField_JMXProxy(modelClass));
	}

	protected void initializeInstanceFields(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		modelClass.addInstanceReference(createInstanceField_DAOBean(modelClass, namespace, element));
		modelClass.addInstanceReferences(createInstanceField_DAOBean_ForReferences(modelClass, namespace, element));
	}

	protected ModelReference createStaticField_EntityManagerFactory(ModelClass modelClass) {
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE + Modifier.STATIC);
		modelReference.setPackageName("javax.persistence");
		modelReference.setClassName("EntityManagerFactory");
		modelReference.setName("entityManagerFactory");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass("javax.persistence.EntityManagerFactory");
		return modelReference;
	}

	protected ModelReference createInstanceField_TransactionTestControl(ModelClass modelClass) {
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName("org.aries.tx");
		modelReference.setClassName("TransactionTestControl");
		modelReference.setName("transactionControl");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass("org.aries.tx.TransactionTestControl");
		return modelReference;
	}
	
	protected ModelReference createInstanceField_DataLayerTestControl(ModelClass modelClass, Unit unit) {
		String unitName = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String unitType = DataLayerHelper.getPersistenceUnitType(unit);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(" org.aries.tx");
		modelReference.setClassName("DataLayerTestControl");
		modelReference.setName(unitName + "Control");
		modelReference.setType(unitType + "Control");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass("org.aries.tx.DataLayerTestControl");
		return modelReference;
	}
	
	protected ModelReference createInstanceField_DAOBean(ModelClass modelClass, Namespace namespace, Element element) {
		String packageName = DataLayerHelper.getDAOPackageName(namespace);
		String interfaceName = DataLayerHelper.getDAOInterfaceName(element);
		//String className = DataLayerHelper.getDAOClassName(element);
		String qualifiedName = DataLayerHelper.getDAOQualifiedInterfaceName(namespace, element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		//String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		String elementBeanName = ModelLayerHelper.getInferredElementBeanName(namespace, element);
		String localPart = DataLayerHelper.getDAONameUncapped(element);
		String typeName = org.aries.util.TypeUtil.getDerivedType(element.getType(), localPart);
		//String beanName = NameUtil.uncapName(interfaceName);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(interfaceName+"<"+entityClassName+">");
		modelReference.setName(elementBeanName+"Dao");
		modelReference.setType(typeName);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass(qualifiedName);
		return modelReference;
	}
	
	protected ModelReference createInstanceField_DAOBean(ModelClass modelClass, Element element, Reference reference) {
		String referenceType = reference.getType();
		String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
		Namespace referencedNamespace = context.getNamespaceByUri(referenceNamespaceUri);
		Element referencedElement = context.getElementByType(referenceType);
		String referenceNameCapped = NameUtil.capName(reference.getName());
		String referenceNameUncapped = NameUtil.uncapName(reference.getName());
		
		String packageName = DataLayerHelper.getDAOPackageName(referencedNamespace);
		String interfaceName = DataLayerHelper.getDAOInterfaceName(referencedElement);
		//String className = DataLayerHelper.getDAOClassName(element);
		String qualifiedName = DataLayerHelper.getDAOQualifiedInterfaceName(referencedNamespace, referencedElement);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(referencedNamespace, referencedElement);
		//String entityBeanName = DataLayerHelper.getInferredEntityBeanName(referencedNamespace, referencedElement);
		String elementBeanName = ModelLayerHelper.getInferredElementBeanName(referencedNamespace, referencedElement);
		String localPart = DataLayerHelper.getDAONameUncapped(referencedElement);
		String typeName = org.aries.util.TypeUtil.getDerivedType(referencedElement.getType(), localPart);
		//String beanName = NameUtil.uncapName(interfaceName);

		if (reference.getContained()) {
			entityClassName = DataLayerHelper.getContainedEntityClassName(element, reference);
			String fieldClassName = ModelLayerHelper.getContainedFieldClassName(element, reference);
			referenceNameUncapped = NameUtil.uncapName(fieldClassName);
		}
		
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(packageName);
		modelReference.setClassName(interfaceName+"<"+entityClassName+">");
		modelReference.setName(referenceNameUncapped+"Dao");
		modelReference.setType(typeName);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass(qualifiedName);
		return modelReference;
	}
	
	protected List<ModelReference> createInstanceField_DAOBean_ForReferences(ModelClass modelClass, Namespace namespace, Element element) {
		List<ModelReference> modelReferences = new ArrayList<ModelReference>();
		List<Reference> references = ElementUtil.getReferences(element);
		//Set<Reference> nonContainmentReferences = ElementUtil.getNonContainmentReferences(element);
		Iterator<Reference> iterator = references.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);
			if (!NamespaceUtil.equals(referenceNamespace, namespace)) {
				modelReferences.add(createInstanceField_DAOBean(modelClass, element, reference));
				String daoQualifiedName = DataLayerHelper.getDAOQualifiedInterfaceName(referenceNamespace, referencedElement);
				modelClass.addImportedClass(daoQualifiedName);
			}
		}
		return modelReferences;
	}

	protected ModelReference createInstanceField_EntityManager(ModelClass modelClass) {
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName("javax.persistence");
		modelReference.setClassName("EntityManager");
		modelReference.setName("entityManager");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass("javax.persistence.EntityManager");
		return modelReference;
	}
	
	protected ModelAttribute createInstanceField_RunningInContainer(ModelClass modelClass) {
		ModelAttribute modelAttribute = new ModelAttribute();
		modelAttribute.setModifiers(Modifier.PRIVATE);
		modelAttribute.setPackageName("java.lang");
		modelAttribute.setClassName("boolean");
		modelAttribute.setName("runningInContainer");
		modelAttribute.setGenerateGetter(false);
		modelAttribute.setGenerateSetter(false);
		return modelAttribute;
	}

	protected ModelReference createInstanceField_JMXProxy(ModelClass modelClass) {
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName("common.jmx.client");
		modelReference.setClassName("JmxProxy");
		modelReference.setName("jmxProxy");
		modelReference.setType(TypeUtil.getTypeFromPackageAndClass("common.jmx.client", "JmxProxy"));
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(true);
		modelReference.addImportedClass("common.jmx.client.JmxProxy");
		return modelReference;
	}


	/*
	 * Instance methods
	 */
	
	protected void initializeInstanceMethods(ModelClass modelClass, Unit unit, Namespace namespace, List<Element> elements) throws Exception {
		modelClass.addInstanceOperation(createOperation_SetJmxManager(modelClass));
		modelClass.addInstanceOperation(createOperation_ClearContext(modelClass));
		modelClass.addInstanceOperation(createOperation_RefreshContext_forObject(modelClass));
		modelClass.addInstanceOperation(createOperation_RefreshContext_forList(modelClass));
		modelClass.addInstanceOperation(createOperation_Initialize(modelClass));
		modelClass.addInstanceOperation(createOperation_Initialize2(modelClass, unit));
		modelClass.addInstanceOperation(createOperation_Initialize3(modelClass, unit));
		modelClass.addInstanceOperation(createOperation_InitializeAsClient(modelClass, unit));
		modelClass.addInstanceOperation(createOperation_AssureEntityManagerFactory(modelClass, unit));
		modelClass.addInstanceOperation(createOperation_ResetEntityManagerFactory(modelClass));
		modelClass.addInstanceOperation(createOperation_AssureEntityManager(modelClass, unit, elements));
		modelClass.addInstanceOperation(createOperation_CreateDaoInstances(modelClass, namespace, elements));
		modelClass.addInstanceOperations(createOperations_CreateDao(modelClass, namespace, elements));

		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			initializeInstanceMethods(modelClass, unit, namespace, element, null);
			
			List<Reference> references = ElementUtil.getReferences(element);
			Iterator<Reference> iterator2 = references.iterator();
			while (iterator2.hasNext()) {
				Reference reference = iterator2.next();
				initializeInstanceMethods(modelClass, unit, namespace, element, reference);

			}
		}
	}

	private void initializeInstanceMethods(ModelClass modelClass, Unit unit, Namespace namespace, Element element, Reference reference) {
		modelClass.addInstanceOperation(createOperations_GetMapper(modelClass, namespace, element, reference));
		modelClass.addInstanceOperation(createOperations_CreateEntity(modelClass, namespace, element, reference));
		modelClass.addInstanceOperation(createOperations_CreateEmptyEntity(modelClass, namespace, element, reference));
		modelClass.addInstanceOperation(createOperations_CreateEntityList(modelClass, namespace, element, reference));

		modelClass.addInstanceOperation(createOperations_GetAllObjects(modelClass, namespace, element, reference, false));
		modelClass.addInstanceOperation(createOperations_GetAllObjects(modelClass, namespace, element, reference, true));
		modelClass.addInstanceOperation(createOperations_GetAllRecords(modelClass, namespace, element, reference, false));
		modelClass.addInstanceOperation(createOperations_GetAllRecords(modelClass, namespace, element, reference, true));

		modelClass.addInstanceOperation(createOperations_GetObjectById(modelClass, namespace, element, reference, false));
		modelClass.addInstanceOperation(createOperations_GetObjectById(modelClass, namespace, element, reference, true));
		modelClass.addInstanceOperation(createOperations_GetRecordById(modelClass, namespace, element, reference, false));
		modelClass.addInstanceOperation(createOperations_GetRecordById(modelClass, namespace, element, reference, true));

		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		Element generalElement = entityClassHelper.getGeneralElement();
		Element criteriaElement = context.getElementByType(generalElement.getType()+"Criteria");
		if (criteriaElement != null) {
			
			modelClass.addInstanceOperation(createOperations_GetObjectsByIds(modelClass, namespace, element, reference, false));
			modelClass.addInstanceOperation(createOperations_GetObjectsByIds(modelClass, namespace, element, reference, true));
			modelClass.addInstanceOperation(createOperations_GetRecordsByIds(modelClass, namespace, element, reference, false));
			modelClass.addInstanceOperation(createOperations_GetRecordsByIds(modelClass, namespace, element, reference, true));
		
			List<Field> uniqueFields = ElementUtil.getUniqueFields(element);
			Iterator<Field> iterator3 = uniqueFields.iterator();
			while (iterator3.hasNext()) {
				Field field = iterator3.next();
				modelClass.addInstanceOperation(createOperations_GetObjectByField(modelClass, namespace, element, field, false));
				modelClass.addInstanceOperation(createOperations_GetObjectByField(modelClass, namespace, element, field, true));
				modelClass.addInstanceOperation(createOperations_GetRecordByField(modelClass, namespace, element, field, false));
				modelClass.addInstanceOperation(createOperations_GetRecordByField(modelClass, namespace, element, field, true));
			}
		}
		
		modelClass.addInstanceOperation(createOperations_AddObjectAsItem(modelClass, namespace, element, reference, true));
		modelClass.addInstanceOperation(createOperations_AddObjectAsItem(modelClass, namespace, element, reference, false));
		modelClass.addInstanceOperation(createOperations_AddEntityAsItem(modelClass, namespace, element, reference, false));
		//modelClass.addInstanceOperation(createOperations_AddEntityAsItem(modelClass, namespace, element, reference, false));

		modelClass.addInstanceOperation(createOperations_AddObjectAsList(modelClass, namespace, element, reference, true));
		modelClass.addInstanceOperation(createOperations_AddObjectAsList(modelClass, namespace, element, reference, false));
		modelClass.addInstanceOperation(createOperations_AddEntityAsList(modelClass, namespace, element, reference, false));
		//modelClass.addInstanceOperation(createOperations_AddEntityAsList(modelClass, namespace, element, reference, false));

		modelClass.addInstanceOperation(createOperations_RemoveAll(modelClass, namespace, element, null));
		modelClass.addInstanceOperation(createOperations_RemoveObjectAsItem(modelClass, namespace, element, reference));
		modelClass.addInstanceOperation(createOperations_RemoveEntityAsItem(modelClass, namespace, element, reference));
		modelClass.addInstanceOperation(createOperations_RemoveObjectAsList(modelClass, namespace, element, reference));
		modelClass.addInstanceOperation(createOperations_RemoveEntityAsList(modelClass, namespace, element, reference));

		modelClass.addInstanceOperation(createOperations_VerifyElementCount(modelClass, namespace, element, reference));
		modelClass.addInstanceOperation(createOperations_VerifyNoElementsExist(modelClass, namespace, element, reference));
		modelClass.addInstanceOperation(createOperations_VerifyElementExistence(modelClass, namespace, element, reference, false));
		modelClass.addInstanceOperation(createOperations_VerifyElementExistenceById(modelClass, namespace, element, reference, false));
		//modelClass.addInstanceOperation(createOperations_VerifyElementListExistence(modelClass, namespace, element, reference, false));
		modelClass.addInstanceOperation(createOperations_VerifyElementExistence(modelClass, namespace, element, reference, true));
		modelClass.addInstanceOperation(createOperations_VerifyElementExistenceById(modelClass, namespace, element, reference, true));
		//modelClass.addInstanceOperation(createOperations_VerifyElementListExistence(modelClass, namespace, element, reference, true));
		
		if (criteriaElement != null) {
			modelClass.addInstanceOperation(createOperations_VerifyElementExistenceByIds(modelClass, namespace, element, reference, true));
			modelClass.addInstanceOperation(createOperations_VerifyElementExistenceByIds(modelClass, namespace, element, reference, false));
		}
	}

	protected ModelOperation createOperation_SetJmxManager(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("setJmxManager");
		modelOperation.setModifiers(Modifier.PUBLIC);
		//modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter("JmxManager", "jmxManager"));
		
		Buf buf = new Buf();
		buf.putLine2("jmxProxy.setJmxManager(jmxManager);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_ClearContext(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("clearContext");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("String", "mbeanName"));
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("jmxProxy.call(mbeanName, \"clearContext\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_RefreshContext_forObject(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("refreshContext");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("T", "entity"));
		modelOperation.setResultType("<T> void");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("entityManager.refresh(entity);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_RefreshContext_forList(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("refreshContext");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("List<T>", "entityList"));
		modelOperation.setResultType("<T> void");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("Iterator<T> iterator = entityList.iterator();");
		buf.putLine2("while (iterator.hasNext()) {");
		buf.putLine2("	T entity = iterator.next();");
		buf.putLine2("	entityManager.refresh(entity);");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Initialize(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initialize");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("TransactionTestControl", "transactionControl"));
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("this.transactionControl = transactionControl;");
		buf.putLine2("assureEntityManagerFactory();");
		buf.putLine2("runningInContainer = true;");
		buf.putLine2("assureEntityManager();");
		buf.putLine2("createDaoInstances();");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Initialize2(ModelClass modelClass, Unit unit) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initialize");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("DataLayerTestControl", unitNameUncapped+"Control"));
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("this.transactionControl = "+unitNameUncapped+"Control.getTransactionTestControl();");
		buf.putLine2("this."+unitNameUncapped+"Control = "+unitNameUncapped+"Control;");
		buf.putLine2("assureEntityManager();");
		buf.putLine2("createDaoInstances();");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Initialize3(ModelClass modelClass, Unit unit) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initialize");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("DataModuleTestControl", unitNameUncapped+"Control"));
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("this.transactionControl = "+unitNameUncapped+"Control.getTransactionTestControl();");
		buf.putLine2("this."+unitNameUncapped+"Control = "+unitNameUncapped+"Control;");
		buf.putLine2("runningInContainer = true;");
		buf.putLine2("createDaoInstances();");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_InitializeAsClient(ModelClass modelClass, Unit unit) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initializeAsClient");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addParameter(createParameter("DataLayerTestControl", unitNameUncapped+"Control"));
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("this.transactionControl = "+unitNameUncapped+"Control.getTransactionTestControl();");
		buf.putLine2("this."+unitNameUncapped+"Control = "+unitNameUncapped+"Control;");
		buf.putLine2("entityManager = "+unitNameUncapped+"Control.setupEntityManager();");
		buf.putLine2("createDaoInstances();");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_AssureEntityManagerFactory(ModelClass modelClass, Unit unit) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("assureEntityManagerFactory");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("if (entityManagerFactory == null) {");
		//buf.putLine2("	Map properties = new HashMap();");
		buf.putLine2("	entityManagerFactory = Persistence.createEntityManagerFactory(\""+unitNameUncapped+"\");");
		buf.putLine2("}");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_ResetEntityManagerFactory(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("resetEntityManagerFactory");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("entityManagerFactory = null;");
		buf.putLine2("assureEntityManagerFactory();");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_AssureEntityManager(ModelClass modelClass, Unit unit, List<Element> elements) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("assureEntityManager");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("if (entityManager == null) {");
		buf.putLine2("	if (runningInContainer) {");
		buf.putLine2("		entityManager = entityManagerFactory.createEntityManager();");
		buf.putLine2("	} else {");
		buf.putLine2("		entityManager = "+unitNameUncapped+"Control.createEntityManager();");
		buf.putLine2("	}");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_CreateDaoInstances(ModelClass modelClass, Namespace namespace, List<Element> elements) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createDaoInstances");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String daoNameCapped = DataLayerHelper.getDAONameCapped(element);
			String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
			buf.putLine2(daoNameUncapped+" = create"+daoNameCapped+"();");
			
			List<Reference> references = ElementUtil.getReferences(element);
			Iterator<Reference> iterator2 = references.iterator();
			while (iterator2.hasNext()) {
				Reference reference = iterator2.next();
				String referenceType = reference.getType();
				String referenceNameCapped = NameUtil.capName(reference.getName());
				String referenceNameUncapped = NameUtil.uncapName(reference.getName());

				if (reference.getContained()) {
					referenceNameCapped = ModelLayerHelper.getContainedFieldClassName(element, reference);
					referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
				}
				
				//if (!NamespaceUtil.equals(referenceNamespace, namespace)) {
				buf.putLine2(referenceNameUncapped+"Dao = create"+referenceNameCapped+"Dao();");
				//}
			}
		}

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createOperations_CreateDao(ModelClass modelClass, Namespace namespace, List<Element> elements) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			modelClass.addInstanceOperation(createOperation_CreateDao(modelClass, namespace, element, null));
			
			List<Reference> references = ElementUtil.getReferences(element);
			Iterator<Reference> iterator2 = references.iterator();
			while (iterator2.hasNext()) {
				Reference reference = iterator2.next();
				modelClass.addInstanceOperation(createOperation_CreateDao(modelClass, namespace, element, reference));
			}
		}
		return modelOperations;
	}

	protected ModelOperation createOperation_CreateDao(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		Element generalElement = element;
		Element targetElement = element;
		
		if (reference != null) {
			String referenceType = reference.getType();
			String referenceLocalPart = TypeUtil.getLocalPart(referenceType);
			String referenceNameCapped = NameUtil.capName(referenceLocalPart);
			String referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);
			String referencedElementNameCapped = ModelLayerHelper.getElementNameCapped(referencedElement);
			entityClassName = DataLayerHelper.getInferredEntityClassName(referenceNamespace, referencedElement);
			targetElement = referencedElement;

			if (reference.getContained()) {
				referenceNameCapped = ModelLayerHelper.getContainedFieldClassName(element, reference);
				referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
				entityClassName = DataLayerHelper.getContainedEntityClassName(element, reference);
				elementNameCapped += referencedElementNameCapped;
			} else elementNameCapped = referencedElementNameCapped;
		}
		
		String className = DataLayerHelper.getDAOClassName(targetElement);
		String interfaceName = DataLayerHelper.getDAOInterfaceName(targetElement);
		String daoInterfaceName = DataLayerHelper.getDAOInterfaceName(element);
		String daoBeanName = NameUtil.uncapName(daoInterfaceName);
		String resultClassName = interfaceName+"<"+entityClassName+">";
		daoBeanName = "dao";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+elementNameCapped+"Dao");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(resultClassName);
		//modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2(resultClassName+" "+daoBeanName+" = new "+className+"<"+entityClassName+">();");
		//buf.putLine2(resultClassName+" "+daoBeanName+" = SupplierDataTestFixture.create"+elementNameCapped+"Dao();");
		buf.putLine2(daoBeanName+".setEntityClass("+entityClassName+".class);");
		buf.putLine2(daoBeanName+".setEntityManager(entityManager);");
		buf.putLine2(daoBeanName+".initialize(\""+elementNameCapped+"\");");
		buf.putLine2("return "+daoBeanName+";");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_GetMapper(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		String mapperClassName = DataLayerHelper.getInferredMapperClassName(namespace, element);
		String mapperFixtureClassName = DataLayerHelper.getMapperFixtureClassName(namespace);

		if (reference != null) {
			String referenceType = reference.getType();
			String referenceLocalPart = TypeUtil.getLocalPart(referenceType);
			String referenceNameCapped = NameUtil.capName(referenceLocalPart);
			//String referenceNameCapped = NameUtil.capName(reference.getName());
			String referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
			//String referenceNameUncapped = NameUtil.uncapName(reference.getName());
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);

			mapperFixtureClassName = DataLayerHelper.getMapperFixtureClassName(namespace);
			mapperClassName = DataLayerHelper.getInferredMapperClassName(namespace, referencedElement);

			if (reference.getContained()) {
				referenceNameCapped = ModelLayerHelper.getContainedFieldClassName(element, reference);
				referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
				String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
				mapperClassName = elementNameCapped + mapperClassName; 
			}
		}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("get"+mapperClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(mapperClassName);
		
		Buf buf = new Buf();
		buf.putLine2("return "+mapperFixtureClassName+".get"+mapperClassName+"();");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_CreateEntity(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityFixtureClassName = DataLayerHelper.getEntityFixtureClassName(namespace);
		String parentEntityClassName = null;
		String parentEntityBeanName = null;
		
		if (reference != null) {
			String referenceType = reference.getType();
			String referenceLocalPart = TypeUtil.getLocalPart(referenceType);
			String referenceNameCapped = NameUtil.capName(referenceLocalPart);
			//String referenceNameCapped = NameUtil.capName(reference.getName());
			String referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
			//String referenceNameUncapped = NameUtil.uncapName(reference.getName());
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);

			entityFixtureClassName = DataLayerHelper.getEntityFixtureClassName(namespace);
			entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, referencedElement);

			if (reference.getContained()) {
				referenceNameCapped = ModelLayerHelper.getContainedFieldClassName(element, reference);
				referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
				String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
				parentEntityClassName = DataLayerHelper.getEntityName(elementNameCapped);
				parentEntityBeanName = NameUtil.uncapName(parentEntityClassName);
				entityClassName = elementNameCapped + entityClassName; 
			}
		}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+entityClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (parentEntityClassName != null)
			modelOperation.addParameter(createParameter(parentEntityClassName, parentEntityBeanName));
		modelOperation.setResultType(entityClassName);
		
		Buf buf = new Buf();
		if (parentEntityClassName != null)
			buf.putLine2("return "+entityFixtureClassName+".create"+entityClassName+"("+parentEntityBeanName+");");
		else buf.putLine2("return "+entityFixtureClassName+".create"+entityClassName+"();");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_CreateEmptyEntity(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityFixtureClassName = DataLayerHelper.getEntityFixtureClassName(namespace);

		if (reference != null) {
			String referenceType = reference.getType();
			String referenceLocalPart = TypeUtil.getLocalPart(referenceType);
			String referenceNameCapped = NameUtil.capName(referenceLocalPart);
			//String referenceNameCapped = NameUtil.capName(reference.getName());
			String referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
			//String referenceNameUncapped = NameUtil.uncapName(reference.getName());
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);

			entityFixtureClassName = DataLayerHelper.getEntityFixtureClassName(namespace);
			entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, referencedElement);

			if (reference.getContained()) {
				referenceNameCapped = ModelLayerHelper.getContainedFieldClassName(element, reference);
				referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
				String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
				entityClassName = elementNameCapped + entityClassName; 
			}
		}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createEmpty"+entityClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(entityClassName);
		
		Buf buf = new Buf();
		buf.putLine2("return "+entityFixtureClassName+".createEmpty"+entityClassName+"();");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_CreateEntityList(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityFixtureClassName = DataLayerHelper.getEntityFixtureClassName(namespace);
		String parentEntityClassName = null;
		String parentEntityBeanName = null;

		if (reference != null) {
			String referenceType = reference.getType();
			String referenceLocalPart = TypeUtil.getLocalPart(referenceType);
			String referenceNameCapped = NameUtil.capName(referenceLocalPart);
			//String referenceNameCapped = NameUtil.capName(reference.getName());
			String referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
			//String referenceNameUncapped = NameUtil.uncapName(reference.getName());
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);

			entityFixtureClassName = DataLayerHelper.getEntityFixtureClassName(namespace);
			entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, referencedElement);

			if (reference.getContained()) {
				referenceNameCapped = ModelLayerHelper.getContainedFieldClassName(element, reference);
				referenceNameUncapped = NameUtil.uncapName(referenceNameCapped);
				String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
				parentEntityClassName = DataLayerHelper.getEntityName(elementNameCapped);
				parentEntityBeanName = NameUtil.uncapName(parentEntityClassName);
				entityClassName = elementNameCapped + entityClassName; 
			}
		}
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+entityClassName+"List");
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (parentEntityClassName != null)
			modelOperation.addParameter(createParameter(parentEntityClassName, parentEntityBeanName));
		modelOperation.setResultType("List<"+entityClassName+">");
		
		Buf buf = new Buf();
		if (parentEntityClassName != null)
			buf.putLine2("return "+entityFixtureClassName+".create"+entityClassName+"List("+parentEntityBeanName+");");
		else buf.putLine2("return "+entityFixtureClassName+".create"+entityClassName+"List();");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected List<ModelOperation> createOperations_CreateEntity(ModelClass modelClass, Namespace namespace, List<Element> elements) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<String> elementsProcessSoFar = new HashSet<String>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementType = element.getType();
			if (!elementsProcessSoFar.contains(elementType)) {
				modelOperations.addAll(createOperations_CreateEntity_ForReferences(modelClass, namespace, element));
				Reference inverseReference = ElementUtil.getInverseReference(element);
				if (inverseReference != null)
					modelOperations.add(createOperation_CreateEntity1(modelClass, namespace, element));
				modelOperations.add(createOperation_CreateEntity2(modelClass, namespace, element));
				elementsProcessSoFar.add(elementType);
			}
		}
		return modelOperations;
	}
	
	protected List<ModelOperation> createOperations_CreateEntity_ForReferences(ModelClass modelClass, Namespace namespace, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<Reference> nonContainmentReferences = ElementUtil.getNonContainmentReferences(element);
		Iterator<Reference> iterator = nonContainmentReferences.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);
			if (!NamespaceUtil.equals(referenceNamespace, namespace)) {
				modelOperations.add(createOperation_CreateEntity2(modelClass, referenceNamespace, referencedElement));
				String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(referencedElement);
				String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(referenceNamespace, referencedElement);
				modelClass.addImportedClass(elementQualifiedName);
				modelClass.addImportedClass(entityQualifiedName);
			}
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_CreateEntity1(ModelClass modelClass, Namespace namespace, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+entityClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(entityClassName);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_CreateEntity1(modelClass, namespace, element));
		return modelOperation;
	}

	protected String createSourceCode_CreateEntity1(ModelClass modelClass, Namespace namespace, Element element) {
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		Element container = context.getElementByType(inverseReference.getType());
		String containerClassName = DataLayerHelper.getEntityClassName(container);
		
		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+entityNameUncapped+" = create"+entityClassName+"(create"+containerClassName+"());");
		buf.putLine2("return "+entityNameUncapped+";");
		return buf.get();
	}
	
	protected ModelOperation createOperation_CreateEntity2(ModelClass modelClass, Namespace namespace, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+entityClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(entityClassName);
		modelOperation.addException("Exception");
		if (inverseReference != null) {
			Element container = context.getElementByType(inverseReference.getType());
			String containerName = DataLayerHelper.getEntityNameUncapped(container);
			String containerClassName = DataLayerHelper.getEntityClassName(container);
			Set<Reference> containingReferences = ElementUtil.getContainingReferences(container, element);
			modelOperation.addParameter(createParameter(containerClassName, containerName));
		}
		
		modelOperation.addInitialSource(createSourceCode_CreateEntity2(modelClass, namespace, element));
		return modelOperation;
	}

	protected String createSourceCode_CreateEntity2(ModelClass modelClass, Namespace namespace, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementClassName = ModelLayerHelper.getElementClassName(element);
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);

		String modelFixtureClassName = ModelLayerHelper.getModelFixtureClassName(element);
		String mapperFixtureClassName = DataLayerHelper.getMapperFixtureClassName(namespace);
		modelClass.addImportedClass(ModelLayerHelper.getModelFixtureQualifiedName(namespace));
		modelClass.addImportedClass(DataLayerHelper.getMapperFixtureQualifiedName(namespace));
		
		String containerEntityName = null;
		if (inverseReference != null) {
			Element container = context.getElementByType(inverseReference.getType());
			containerEntityName = DataLayerHelper.getEntityNameUncapped(container);
			//Set<Reference> containingReferences = ElementUtil.getContainingReferences(container, element);
		}
		
		Buf buf = new Buf();
		if (inverseReference != null) {
			buf.putLine2(elementClassName+" "+elementNameUncapped+" = "+modelFixtureClassName+".create"+elementClassName+"(null);");
			buf.putLine2(entityClassName+" "+entityNameUncapped+" = "+mapperFixtureClassName+".get"+elementClassName+"Mapper().toEntity("+containerEntityName+", "+elementNameUncapped+");");
		} else {
			buf.putLine2(elementClassName+" "+elementNameUncapped+" = "+modelFixtureClassName+".create"+elementClassName+"();");
			buf.putLine2(entityClassName+" "+entityNameUncapped+" = "+mapperFixtureClassName+".get"+elementClassName+"Mapper().toEntity("+elementNameUncapped+");");
		}
		
		Set<Reference> nonContainmentReferences = ElementUtil.getNonContainmentReferences(element);
		Iterator<Reference> iterator = nonContainmentReferences.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceNameCapped = NameUtil.capName(reference.getName());
			Element referencedElement = context.getElementByType(reference.getType());
			String referencedEntityClassName = DataLayerHelper.getEntityClassName(referencedElement);
			buf.putLine2(entityNameUncapped+".set"+referenceNameCapped+"(createAndPersist"+referencedEntityClassName+"());");
		}

		if (inverseReference != null && FieldUtil.isMappedBy(inverseReference)) {
			buf.putLine2(containerEntityName+".set"+elementNameCapped+"("+entityNameUncapped+");");
		}
		
		buf.putLine2("return "+entityNameUncapped+";");
		return buf.get();
	}
	
	
	protected ModelOperation createOperations_GetAllObjects(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean inContext) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);

		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (inContext)
			modelOperation.setName("getAll"+objectName+"IC");
		else modelOperation.setName("getAll"+objectName);
		if (inContext)
			modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRED"));
		else modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.setResultType("List<"+objectClassName+">");
		modelOperation.addException("Exception");

		if (objectClassName.equals("BookOrdersBook"))
			System.out.println();
		
		Buf buf = new Buf();
		if (!inContext)
			buf.putLine2("preProcessExecution();");
		buf.putLine2("List<"+entityClassName+"> entityList = "+daoBeanName+".getAll"+objectClassName+"Records();");
		buf.putLine2("List<"+objectClassName+"> "+objectBeanName+"List = get"+mapperName+"().toModelList(entityList);");
		if (!inContext)
			buf.putLine2("postProcessExecution();");
		buf.putLine2("return "+objectBeanName+"List;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_GetAllRecords(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean inContext) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";
		
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);

		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (inContext)
			modelOperation.setName("getAll"+objectName+"RecordsIC");
		else modelOperation.setName("getAll"+objectName+"Records");
		if (inContext)
			modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRED"));
		else modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.setResultType("List<"+entityClassName+">");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (!inContext)
			buf.putLine2("preProcessExecution();");
		buf.putLine2("List<"+entityClassName+"> "+entityBeanName+"List = "+daoBeanName+".getAll"+objectClassName+"Records();");
		if (!inContext)
			buf.putLine2("postProcessExecution();");
		buf.putLine2("return "+entityBeanName+"List;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_GetObjectById(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean inContext) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (inContext)
			modelOperation.setName("get"+objectName+"ByIdIC");
		else modelOperation.setName("get"+objectName+"ById");
		if (inContext)
			modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRED"));
		else modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addParameter(createParameter("Long", "id"));
		modelOperation.setResultType(objectClassName);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (!inContext)
			buf.putLine2("preProcessExecution();");
		buf.putLine2(entityClassName+" entity = "+daoBeanName+".get"+objectClassName+"RecordById(id);");
		buf.putLine2(objectClassName+" "+objectBeanName+" = get"+mapperName+"().toModel(entity);");
		if (!inContext)
			buf.putLine2("postProcessExecution();");
		buf.putLine2("return "+objectBeanName+";");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_GetRecordById(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean inContext) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (inContext)
			modelOperation.setName("get"+objectName+"RecordByIdIC");
		else modelOperation.setName("get"+objectName+"RecordById");
		if (inContext)
			modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRED"));
		else modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addParameter(createParameter("Long", "id"));
		modelOperation.setResultType(entityClassName);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (!inContext)
			buf.putLine2("preProcessExecution();");
		buf.putLine2(entityClassName+" "+entityBeanName+" = "+daoBeanName+".get"+objectClassName+"RecordById(id);");
		buf.putLine2("refreshContext(entity);");
		if (!inContext)
			buf.putLine2("postProcessExecution();");

		buf.putLine2("return "+entityBeanName+";");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_GetObjectsByIds(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean inContext) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (inContext)
			modelOperation.setName("get"+objectName+"ByIdsIC");
		else modelOperation.setName("get"+objectName+"ByIds");
		if (inContext)
			modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRED"));
		else modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addParameter(createParameter("List<Long>", "ids"));
		modelOperation.setResultType("List<"+objectClassName+">");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (!inContext)
			buf.putLine2("preProcessExecution();");
		buf.putLine2(objectClassName+"Criteria "+objectBeanName+"Criteria = new "+objectClassName+"Criteria();");
		buf.putLine2(objectBeanName+"Criteria.setIdList(ids);");
		buf.putLine2("List<"+entityClassName+"> entityList = "+daoBeanName+".get"+objectClassName+"RecordsByCriteria("+objectBeanName+"Criteria);");
		buf.putLine2("List<"+objectClassName+"> "+objectBeanName+"List = get"+mapperName+"().toModelList("+entityBeanName+"List);");
		if (!inContext)
			buf.putLine2("postProcessExecution();");
		buf.putLine2("return "+objectBeanName+"List;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_GetRecordsByIds(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean inContext) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (inContext)
			modelOperation.setName("get"+objectName+"RecordsByIdsIC");
		else modelOperation.setName("get"+objectName+"RecordsByIds");
		if (inContext)
			modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRED"));
		else modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addParameter(createParameter("List<Long>", "ids"));
		modelOperation.setResultType("List<"+entityClassName+">");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (!inContext)
			buf.putLine2("preProcessExecution();");
		buf.putLine2(objectClassName+"Criteria "+objectBeanName+"Criteria = new "+objectClassName+"Criteria();");
		buf.putLine2(objectBeanName+"Criteria.setIdList(ids);");
		buf.putLine2("List<"+entityClassName+"> entityList = "+daoBeanName+".get"+objectClassName+"RecordsByCriteria("+objectBeanName+"Criteria);");
		buf.putLine2("refreshContext(entityList);");
		if (!inContext)
			buf.putLine2("postProcessExecution();");

		buf.putLine2("return "+entityBeanName+"List;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_GetObjectByField(ModelClass modelClass, Namespace namespace, Element element, Field field, boolean inContext) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, field);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldBeanName = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (inContext)
			modelOperation.setName("get"+objectName+"By"+fieldNameCapped+"IC");
		else modelOperation.setName("get"+objectName+"By"+fieldNameCapped);
		if (inContext)
			modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRED"));
		else modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addParameter(createParameter(fieldClassName, fieldBeanName));
		modelOperation.setResultType(objectClassName);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (!inContext)
			buf.putLine2("preProcessExecution();");
		buf.putLine2(objectClassName+"Criteria "+objectBeanName+"Criteria = new "+objectClassName+"Criteria();");
		buf.putLine2(objectBeanName+"Criteria.addToFieldMap(\""+fieldBeanName+"\",  "+fieldBeanName+");");
		buf.putLine2(entityClassName+" entity = "+daoBeanName+".get"+objectClassName+"RecordByCriteria("+objectBeanName+"Criteria);");
		buf.putLine2(objectClassName+" "+objectBeanName+" = get"+mapperName+"().toModel(entity);");
		if (!inContext)
			buf.putLine2("postProcessExecution();");
		buf.putLine2("return "+objectBeanName+";");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_GetRecordByField(ModelClass modelClass, Namespace namespace, Element element, Field field, boolean inContext) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, field);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldBeanName = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldBaseName = fieldBeanName;
		if (field instanceof Reference) {
			fieldClassName += "Entity";
			fieldBeanName += "Entity";
		}

		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (inContext)
			modelOperation.setName("get"+objectName+"RecordBy"+fieldNameCapped+"IC");
		else modelOperation.setName("get"+objectName+"RecordBy"+fieldNameCapped);
		if (inContext)
			modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRED"));
		else modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addParameter(createParameter(fieldClassName, fieldBeanName));
		modelOperation.addParameter(createParameter("Long", "id"));
		modelOperation.setResultType(entityClassName);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (!inContext)
			buf.putLine2("preProcessExecution();");
		buf.putLine2(objectClassName+"Criteria "+objectBeanName+"Criteria = new "+objectClassName+"Criteria();");
		buf.putLine2(objectBeanName+"Criteria.addToFieldMap(\""+fieldBaseName+"\",  "+fieldBeanName+");");
		buf.putLine2(entityClassName+" entity = "+daoBeanName+".get"+objectClassName+"RecordByCriteria("+objectBeanName+"Criteria);");
		if (!inContext)
			buf.putLine2("postProcessExecution();");
		buf.putLine2("return "+entityBeanName+";");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_AddEntityAsItem(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean createItem) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("add"+objectName+"Record");
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		if (!createItem)
			modelOperation.addParameter(createParameter(entityClassName, entityBeanName));
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("preProcessExecution();");
		if (createItem)
			buf.putLine2(entityClassName+" "+entityBeanName+" = create"+elementNameCapped+"Entity();");
		buf.putLine2("Long id = "+daoBeanName+".add"+objectClassName+"Record("+entityBeanName+");");
		buf.putLine2("postProcessExecution();");
		buf.putLine2("return id;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_AddEntityAsItemIC(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean createItem) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("add"+objectName+"RecordIC");
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRED"));
		if (!createItem)
			modelOperation.addParameter(createParameter(entityClassName, entityBeanName));
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (createItem)
			buf.putLine2(entityClassName+" "+entityBeanName+" = create"+elementNameCapped+"Entity();");
		buf.putLine2("Long id = "+daoBeanName+".add"+objectClassName+"Record("+entityBeanName+");");
		buf.putLine2("return id;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_AddObjectAsItem(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean createItem) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		String fixtureClassName = ModelLayerHelper.getFixtureClassName(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("add"+objectName+"");
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		if (!createItem)
			modelOperation.addParameter(createParameter(objectClassName, objectBeanName));
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("preProcessExecution();");
		if (createItem)
			buf.putLine2(objectClassName+" "+objectBeanName+" = "+fixtureClassName+".create_"+objectClassName+"();");
		buf.putLine2(entityClassName+" "+entityBeanName+" = get"+mapperName+"().toEntity("+objectBeanName+");");
		buf.putLine2("Long id = "+daoBeanName+".add"+objectClassName+"Record("+entityBeanName+");");
		buf.putLine2("postProcessExecution();");
		buf.putLine2("return id;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_AddObjectAsItemIC(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean createItem) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("add"+objectName+"IC");
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRED"));
		if (!createItem)
			modelOperation.addParameter(createParameter(objectClassName, objectBeanName));
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (createItem)
			buf.putLine2(objectClassName+" "+objectBeanName+" = create"+elementNameCapped+"();");
		buf.putLine2(entityClassName+" "+entityBeanName+" = get"+mapperName+"().toEntity("+objectBeanName+");");
		buf.putLine2("Long id = "+daoBeanName+".add"+objectClassName+"Record("+entityBeanName+");");
		buf.putLine2("return id;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_AddEntityAsList(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean createList) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("add"+objectName+"Records");
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		if (!createList)
			modelOperation.addParameter(createParameter("List<"+entityClassName+">", entityBeanName+"List"));
		modelOperation.setResultType("List<Long>");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("preProcessExecution();");
		if (createList)
			buf.putLine2("List<"+entityClassName+"> "+entityBeanName+"List = create"+elementNameCapped+"EntityList();");
		buf.putLine2("List<Long> idList = "+daoBeanName+".add"+objectClassName+"Records("+entityBeanName+"List);");
		buf.putLine2("postProcessExecution();");
		buf.putLine2("return idList;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_AddObjectAsList(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean createList) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("add"+objectName+"");
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		if (!createList)
			modelOperation.addParameter(createParameter("List<"+objectClassName+">", objectBeanName+"List"));
		modelOperation.setResultType("List<Long>");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("preProcessExecution();");
		if (createList)
			buf.putLine2("List<"+objectClassName+"> "+objectBeanName+"List = create"+elementNameCapped+"List();");
		buf.putLine2("List<"+entityClassName+"> "+entityBeanName+"List = get"+mapperName+"().toEntityList("+objectBeanName+"List);");
		buf.putLine2("List<Long> idList = "+daoBeanName+".add"+objectClassName+"Records("+entityBeanName+"List);");
		buf.putLine2("postProcessExecution();");
		buf.putLine2("return idList;");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_RemoveAll(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("removeAll"+objectName+"");
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("preProcessExecution();");
		buf.putLine2(daoBeanName+".removeAll"+objectClassName+"Records();");
		buf.putLine2("postProcessExecution();");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_RemoveObjectAsItem(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("remove"+objectName);
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addParameter(createParameter(objectClassName, objectBeanName));
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("preProcessExecution();");
		buf.putLine2(entityClassName+" "+entityBeanName+" = get"+mapperName+"().toEntity("+objectBeanName+");");
		buf.putLine2(daoBeanName+".remove"+objectClassName+"Record("+entityBeanName+");");
		buf.putLine2("postProcessExecution();");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_RemoveEntityAsItem(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("remove"+objectName+"Record");
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addParameter(createParameter(entityClassName, entityBeanName));
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("preProcessExecution();");
		buf.putLine2(daoBeanName+".remove"+objectClassName+"Record("+entityBeanName+");");
		buf.putLine2("postProcessExecution();");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_RemoveObjectAsList(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("remove"+objectName);
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addParameter(createParameter("List<"+objectClassName+">", objectBeanName+"List"));
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("preProcessExecution();");
		buf.putLine2("List<"+entityClassName+"> "+entityBeanName+"List = get"+mapperName+"().toEntityList("+objectBeanName+"List);");
		buf.putLine2(daoBeanName+".remove"+objectClassName+"Records("+entityBeanName+"List);");
		buf.putLine2("postProcessExecution();");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_RemoveEntityAsList(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("remove"+objectName+"Record");
		modelOperation.addAnnotation(AnnotationUtil.createTransactionAttributeAnnotation("REQUIRES_NEW"));
		modelOperation.addParameter(createParameter("List<"+entityClassName+">", entityBeanName+"List"));
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("preProcessExecution();");
		buf.putLine2(daoBeanName+".remove"+objectClassName+"Records("+entityBeanName+"List);");
		buf.putLine2("postProcessExecution();");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
	
	protected ModelOperation createOperations_VerifyNoElementsExist(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("verify"+objectName+"DoNotExist");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("verify"+objectName+"Count(0);");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_VerifyElementExistence(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean doesExist) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		Namespace entityNamespace = entityClassHelper.getEntityNamespace();
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		String fixtureClassName = DataLayerHelper.getEntityFixtureClassName(entityNamespace);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (doesExist)
			modelOperation.setName("verify"+objectName+"Exist");
		else modelOperation.setName("verify"+objectName+"DoNotExist");
		modelOperation.addParameter(createParameter(entityClassName, entityBeanName));
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		if (doesExist) {
			buf.putLine2(entityClassName+" currentEntity = get"+objectName+"RecordById(entity.getId());");
			buf.putLine2("Assert.notNull(currentEntity, \""+objectName+" record should exist\");");
			buf.putLine2(fixtureClassName+".assertSame"+objectName+"Entity(currentEntity, entity, true);");
		} else buf.putLine2("verify"+objectName+"DoNotExist("+entityBeanName+".getId());");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_VerifyElementExistenceById(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean doesExist) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (doesExist)
			modelOperation.setName("verify"+objectName+"Exist");
		else modelOperation.setName("verify"+objectName+"DoNotExist");
		modelOperation.addParameter(createParameter("Long", "id"));
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+entityBeanName+" = get"+objectName+"RecordById(id);");
		if (doesExist)
			buf.putLine2("Assert.notNull("+entityBeanName+", \""+objectName+" record should exist\");");
		else buf.putLine2("Assert.isNull("+entityBeanName+", \""+objectName+" record should not exist\");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperations_VerifyElementExistenceByIds(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean doesExist) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (doesExist)
			modelOperation.setName("verify"+objectName+"Exist");
		else modelOperation.setName("verify"+objectName+"DoNotExist");
		modelOperation.addParameter(createParameter("List<Long>", "ids"));
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("Collection<"+entityClassName+"> "+entityBeanName+"List = get"+objectName+"RecordsByIds(ids);");
		buf.putLine2("Assert.notNull("+entityBeanName+"List, \""+objectName+" results should not be null\");");
		if (doesExist) {
			buf.putLine2("Assert.notEmpty("+entityBeanName+"List, \""+objectName+" records should exist\");");
			buf.putLine2("Assert.equals("+entityBeanName+"List.size(), ids.size(), \""+objectName+" record count should be correct\");");
		} else buf.putLine2("Assert.isEmpty("+entityBeanName+"List, \""+objectName+" records should not exist\");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_VerifyElementListExistence(ModelClass modelClass, Namespace namespace, Element element, Reference reference, boolean doesExist) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		if (doesExist)
			modelOperation.setName("verify"+objectName+"Exist");
		else modelOperation.setName("verify"+objectName+"DoNotExist");
		modelOperation.addParameter(createParameter("List<"+entityClassName+">", entityBeanName+"List"));
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("List<"+entityClassName+"> records = getAll"+objectName+"Records();");
		buf.putLine2("verify"+objectName+"Count("+entityBeanName+"List.size());");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperations_VerifyElementCount(ModelClass modelClass, Namespace namespace, Element element, Reference reference) {
		EntityClassHelper entityClassHelper = new EntityClassHelper(context);
		entityClassHelper.initialize(namespace, element, reference);
		String entityClassName = entityClassHelper.getEntityClassName();
		String entityBeanName = entityClassHelper.getEntityBeanName();
		String parentEntityClassName = entityClassHelper.getParentEntityClassName();
		String parentEntityBeanName = entityClassHelper.getParentEntityBeanName();
		Element generalElement = entityClassHelper.getGeneralElement();
		Element targetElement = entityClassHelper.getTargetElement();
		Element referencedElement = entityClassHelper.getReferencedElement();
		entityBeanName = "entity";

		String elementNameCapped = ModelLayerHelper.getElementNameCapped(targetElement);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(targetElement);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(generalElement);
		String elementLocalPartUncapped = ModelLayerHelper.getElementTypeLocalPartUncapped(generalElement);
		
		String daoBeanName = DataLayerHelper.getDAONameUncapped(targetElement);
		String mapperName = DataLayerHelper.getMapperNameCapped(targetElement);
		String objectName = elementNameCapped;
		String objectBeanName = elementLocalPartUncapped;
		String objectClassName = elementLocalPartCapped;

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("verify"+objectName+"Count");
		modelOperation.addParameter(createParameter("int", "expectedCount"));
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("List<"+entityClassName+"> "+entityBeanName+"List = getAll"+objectName+"Records();");
		buf.putLine2("Assert.notNull("+entityBeanName+"List, \""+objectName+" results should not be null\");");
		buf.putLine2("Assert.equals("+entityBeanName+"List.size(), expectedCount, \""+objectName+" record count not correct\");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected List<ModelOperation> createOperations_PersistEntity(ModelClass modelClass, Namespace namespace, List<Element> elements) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<String> elementsProcessSoFar = new HashSet<String>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			String elementType = element.getType();
			if (!elementsProcessSoFar.contains(elementType)) {
				modelClass.addInstanceOperations(createOperations_CreateAndPersistEntity_ForReferences(modelClass, namespace, element));
				modelClass.addInstanceOperation(createOperation_CreateAndPersistEntity(modelClass, element));
				modelClass.addInstanceOperations(createOperations_PersistEntity_ForReferences(modelClass, namespace, element));
				modelClass.addInstanceOperation(createOperation_PersistEntity(modelClass, element));
				elementsProcessSoFar.add(elementType);
			}
		}
		return modelOperations;
	}

	protected List<ModelOperation> createOperations_CreateAndPersistEntity_ForReferences(ModelClass modelClass, Namespace namespace, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<Reference> nonContainmentReferences = ElementUtil.getNonContainmentReferences(element);
		Iterator<Reference> iterator = nonContainmentReferences.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);
			if (!NamespaceUtil.equals(referenceNamespace, namespace)) {
				modelOperations.add(createOperation_CreateAndPersistEntity(modelClass, referencedElement));
				String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(referenceNamespace, referencedElement);
				modelClass.addImportedClass(entityQualifiedName);
			}
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_CreateAndPersistEntity(ModelClass modelClass, Element element) {
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createAndPersist"+entityClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType(entityClassName);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_createAndPersistEntity(modelClass, element));
		return modelOperation;	
	}
	
	protected List<ModelOperation> createOperations_PersistEntity_ForReferences(ModelClass modelClass, Namespace namespace, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		Set<Reference> nonContainmentReferences = ElementUtil.getNonContainmentReferences(element);
		Iterator<Reference> iterator = nonContainmentReferences.iterator();
		while (iterator.hasNext()) {
			Reference reference = iterator.next();
			String referenceType = reference.getType();
			String referenceNamespaceUri = TypeUtil.getNamespace(referenceType);
			Namespace referenceNamespace = context.getNamespaceByUri(referenceNamespaceUri);
			Element referencedElement = context.getElementByType(referenceType);
			if (!NamespaceUtil.equals(referenceNamespace, namespace)) {
				modelOperations.add(createOperation_PersistEntity(modelClass, referencedElement));
				String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(referenceNamespace, referencedElement);
				modelClass.addImportedClass(entityQualifiedName);
			}
		}
		return modelOperations;
	}
	
	protected String createSourceCode_createAndPersistEntity(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+entityNameUncapped+" = create"+entityClassName+"();");
		buf.putLine2("Long id = persist"+entityClassName+"("+entityNameUncapped+");");
		buf.putLine2(entityNameUncapped+".setId(id);");
		buf.putLine2("return "+entityNameUncapped+";");
		return buf.get();
	}

	protected ModelOperation createOperation_PersistEntity(ModelClass modelClass, Element element) {
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("persist"+entityClassName);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter(entityClassName, entityNameUncapped));
		modelOperation.addInitialSource(createSourceCode_PersistEntity(modelClass, element));
		return modelOperation;	
	}
	
	protected String createSourceCode_PersistEntity(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		Buf buf = new Buf();
		buf.putLine2("Long id = "+daoNameUncapped+".add"+elementNameCapped+"("+entityNameUncapped+");");
		buf.putLine2("return id;");
		return buf.get();
	}

}
