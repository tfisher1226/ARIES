package nam.data.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Elements;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Reference;
import nam.model.Unit;
import nam.model.src.main.java.DummyValueFactory;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.NamespaceUtil;
import nam.model.util.TypeUtil;

import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


public class DaoITBuilderOrig extends AbstractBeanBuilder {

	private DummyValueFactory dummyValueFactory;

	
	public DaoITBuilderOrig(GenerationContext context) {
		super(context);
		initialize();
	}

	protected void initialize() {
		dummyValueFactory = new DummyValueFactory(context);
	}
	
	public List<ModelClass> buildClasses(Unit unit) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		modelClasses.addAll(buildClasses(unit.getNamespace()));
		modelClasses.addAll(buildClasses(unit.getNamespace(), unit.getElements()));
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(List<Namespace> namespaces) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Namespace> iterator = namespaces.iterator();
		while (iterator.hasNext()) {
			Namespace namespace = iterator.next();
			if (namespace.getImported() == null || !namespace.getImported()) {
				modelClasses.addAll(buildClasses(namespace));
			}
		}
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(Namespace namespace) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Element> elements = NamespaceUtil.getElements(namespace);
		Iterator<Element> iterator = elements.iterator();
		this.namespace = namespace;
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (!ElementUtil.isTransient(element)) {
				ModelClass modelClass = buildClass(namespace, element);
				modelClasses.add(modelClass);
			}
		}
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(Namespace namespace, Elements elements) throws Exception {
		List<Element> list = ElementUtil.getElements(elements);
		return buildClassesFromElements(namespace, list);
	}
	
	public List<ModelClass> buildClassesFromElements(Namespace namespace, List<Element> elements) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (ElementUtil.isAbstract(element))
				continue;
			if (ElementUtil.isTransient(element))
				continue;
			ModelClass modelClass = buildClass(namespace, element);
			modelClasses.add(modelClass);
		}
		return modelClasses;
	}
	
	protected ModelClass buildClass(Namespace namespace, Element element) throws Exception {
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String packageName = DataLayerHelper.getDAOPackageName(namespace);
		String interfaceName = DataLayerHelper.getDAOInterfaceName(element);
		//String daoQualifiedName = DataLayerHelper.getDAOQualifiedClassName(element);
		String daoType = org.aries.util.TypeUtil.getDerivedType(element.getType(), daoNameUncapped);
		//String daoType = TypeUtil.getTypeFromNamespace(namespace);

		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(interfaceName + "IT");
		modelClass.setName(daoNameUncapped);
		modelClass.setType(daoType);
		modelClass.setParentClassName("AbstractDaoIT");
		initializeClass(modelClass, namespace, element);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		initializeClassAnnotations(modelClass, element);
		initializeImportedClasses(modelClass, namespace, element);
		initializeInstanceFields(modelClass, namespace, element);
		initializeInstanceMethods(modelClass, namespace, element);
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Element element) throws Exception {
		modelClass.addClassAnnotation(AnnotationUtil.createMockitoJUnitRunnerAnnotation());
		modelClass.addImportedClass("org.mockito.runners.MockitoJUnitRunner");
		modelClass.addImportedClass("org.junit.runner.RunWith");
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		//String elementQualifiedName = ModelLayerHelper.getElementQualifiedName(element);
		//String modelFixtureQualifiedName = ModelLayerHelper.getModelFixtureQualifiedName(namespace.getUri());
		//String mapperFixtureQualifiedName = DataLayerHelper.getMapperFixtureQualifiedName(namespace.getUri());
		String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, element);
		String daoQualifiedName = DataLayerHelper.getDAOQualifiedClassName(namespace, element);

		//modelClass.addImportedClass(elementQualifiedName);
		//modelClass.addImportedClass(modelFixtureQualifiedName);
		//modelClass.addImportedClass(mapperFixtureQualifiedName);
		modelClass.addImportedClass(entityQualifiedName);
		modelClass.addImportedClass(daoQualifiedName);
		
		modelClass.addImportedClass("java.util.List");
//		modelClass.addImportedClass("javax.persistence.EntityManager");
//		modelClass.addImportedClass("javax.sql.DataSource");
//		modelClass.addImportedClass("javax.transaction.Transaction");
//		modelClass.addImportedClass("javax.transaction.TransactionManager");
		modelClass.addImportedClass("org.junit.After");
		modelClass.addImportedClass("org.junit.AfterClass");
		modelClass.addImportedClass("org.junit.Before");
		modelClass.addImportedClass("org.junit.BeforeClass");
		modelClass.addImportedClass("org.junit.Test");
		modelClass.addImportedClass("org.aries.Assert");
//		modelClass.addImportedClass("org.aries.common.AbstractDaoTest");
//		modelClass.addImportedClass("org.aries.common.DataSourceTestFixture");
//		modelClass.addImportedClass("org.aries.common.TestXADataSource");
//		modelClass.addImportedClass("org.aries.common.entity.EmailAddressEntity");
//		modelClass.addImportedClass("org.aries.common.util.SqlScriptExecutor");
//		modelClass.addImportedClass("com.arjuna.ats.internal.arjuna.thread.ThreadActionData");
//		modelClass.addImportedClass("com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple");
		modelClass.addImportedClass("tx.manager.registry.TransactionRegistry");
		modelClass.addImportedClass("common.tx.AbstractDaoIT");
//		modelClass.addImportedClass("org.aries.TestContext");
//		modelClass.addImportedClass("org.aries.TestContextFactory");
	}
	
	/*
	 * initializeInstanceFields:
	 */
	
	protected void initializeInstanceFields(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		modelClass.addInstanceReference(createInstanceField_FixtureBean(namespace, element));
		modelClass.addInstanceReference(createInstanceField_HelperBean(namespace, element));
		Reference inverseReference = ElementUtil.getInverseReference(element);
		if (inverseReference != null)
			modelClass.addInstanceReference(createInstanceField_ContainerBean(namespace, element));
	}
	
	protected ModelReference createInstanceField_FixtureBean(Namespace namespace, Element element) {
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String daoPackageName = DataLayerHelper.getDAOPackageName(namespace);
		String daoClassName = DataLayerHelper.getDAOClassName(element);
		String daoQualifiedName = DataLayerHelper.getDAOQualifiedClassName(namespace, element);
		String daoType = org.aries.util.TypeUtil.getDerivedType(element.getType(), daoNameUncapped);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(daoPackageName);
		modelReference.setClassName(daoClassName);
		modelReference.setName("fixture");
		modelReference.setType(daoType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass(daoQualifiedName);
		return modelReference;
	}
	
	protected ModelReference createInstanceField_HelperBean(Namespace namespace, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String namespaceBaseName = NameUtil.getLastSegmentFromPackageName(elementPackageName);

		String daoHelperClassName = namespaceBaseName + "DaoHelper";
		String daoHelperPackageName = DataLayerHelper.getDAOPackageName(namespace);
		String daoHelperNameUncapped = NameUtil.uncapName(daoHelperClassName);
		String daoHelperType = org.aries.util.TypeUtil.getDerivedType(element.getType(), daoHelperNameUncapped);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(daoHelperPackageName);
		modelReference.setClassName(daoHelperClassName);
		modelReference.setName("helper");
		modelReference.setType(daoHelperType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		return modelReference;
	}
	
	protected ModelReference createInstanceField_ContainerBean(Namespace namespace, Element element) {
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String daoPackageName = DataLayerHelper.getDAOPackageName(namespace);
		String daoClassName = DataLayerHelper.getDAOClassName(element);
		String daoQualifiedName = DataLayerHelper.getDAOQualifiedClassName(namespace, element);
		String daoType = org.aries.util.TypeUtil.getDerivedType(element.getType(), daoNameUncapped);
		
		Reference inverseReference = ElementUtil.getInverseReference(element);
		Element containerElement = context.getElementByType(inverseReference.getType());
		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
		String entityClassName = DataLayerHelper.getEntityClassName(containerElement);
		String entityQualifiedName = DataLayerHelper.getEntityQualifiedName(namespace, containerElement);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(entityPackageName);
		modelReference.setClassName(entityClassName);
		modelReference.setName("container");
		modelReference.setType(daoType);
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass(entityQualifiedName);
		return modelReference;
	}
	
	
	/*
	 * initializeInstanceMethods:
	 */
	
	protected void initializeInstanceMethods(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		Reference inverseReference = ElementUtil.getInverseReference(element);

		modelClass.addInstanceOperation(createOperation_BeforeCLass(modelClass, element));
		modelClass.addInstanceOperation(createOperation_AfterCLass(modelClass, element));
		modelClass.addInstanceOperation(createOperation_SetUp(modelClass, element));
		modelClass.addInstanceOperation(createOperation_TearDown(modelClass, element));
		modelClass.addInstanceOperation(createOperation_CreateFixture(modelClass, element));
		modelClass.addInstanceOperation(createOperation_CreateEntity(modelClass, element));
//		if (inverseReference != null)
//			modelClass.addInstanceOperation(createOperation_CreateContainer(modelClass, element));
		modelClass.addInstanceOperation(createOperation_OpenEntityManager(modelClass, element));
		modelClass.addInstanceOperation(createOperation_CloseEntityManager(modelClass, element));
		modelClass.addInstanceOperation(createOperation_TestGetAllRecords(modelClass, element));
		modelClass.addInstanceOperation(createOperation_TestGetRecordById(modelClass, element));
		modelClass.addInstanceOperation(createOperation_TestGetRecordById_Null(modelClass, element));
		modelClass.addInstanceOperations(createOperations_TestGetRecordByField(modelClass, element));
		modelClass.addInstanceOperations(createOperations_TestGetRecordByField_Null(modelClass, element));
		modelClass.addInstanceOperation(createOperation_TestAddRecord_Commit(modelClass, element));
		modelClass.addInstanceOperation(createOperation_TestAddRecord_Rollback(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_TestAddRecord_Exception(modelClass, element));
		
		if (inverseReference != null) {
			modelClass.addInstanceOperation(createOperation_AssureAddContainer1(modelClass, element));
			modelClass.addInstanceOperation(createOperation_AssureAddContainer2(modelClass, element));
		}
		modelClass.addInstanceOperation(createOperation_AssureAddRecord1(modelClass, element));
		modelClass.addInstanceOperation(createOperation_AssureAddRecord2(modelClass, element));
		modelClass.addInstanceOperation(createOperation_AssureDeleteRecord1(modelClass, element));
		modelClass.addInstanceOperation(createOperation_AssureDeleteRecord2(modelClass, element));
		modelClass.addInstanceOperation(createOperation_VerifyRecordCount1(modelClass, element));
		modelClass.addInstanceOperation(createOperation_VerifyRecordCount2(modelClass, element));
		modelClass.addInstanceOperation(createOperation_VerifyRecordExists1(modelClass, element));
		modelClass.addInstanceOperation(createOperation_VerifyRecordExists2(modelClass, element));
		modelClass.addInstanceOperation(createOperation_VerifyRecordNotExists1(modelClass, element));
		modelClass.addInstanceOperation(createOperation_VerifyRecordNotExists2(modelClass, element));
	}

	protected ModelOperation createOperation_BeforeCLass(ModelClass modelClass, Element element) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeClassAnnotation());
		modelOperation.setName("beforeCLass");
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_BeforeClass(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_BeforeClass(ModelClass modelClass, Element element) {
		String projectName = context.getProjectName();
		String domainName = projectName.toLowerCase();
		String databaseName = domainName + "DB";
		String userName = "root";
		String password = "";
		
		Buf buf = new Buf();
		buf.putLine2("createDataSource(\""+databaseName+"\", \""+userName+"\", \""+password+"\");");
		buf.putLine2("createTransactionManager();");
		buf.putLine2("createNamingServiceContext();");
		buf.putLine2("setupEntityManagerFactory(\""+domainName+"\");");
		return buf.get();
	}

	protected ModelOperation createOperation_AfterCLass(ModelClass modelClass, Element element) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterClassAnnotation());
		modelOperation.setName("afterCLass");
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_AfterClass(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_AfterClass(ModelClass modelClass, Element element) {
		Buf buf = new Buf();
		buf.putLine2("teardownEntityManagerFactory();");
		buf.putLine2("teardownEntityManager();");
		return buf.get();
	}

	protected ModelOperation createOperation_SetUp(ModelClass modelClass, Element element) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeAnnotation());
		modelOperation.setName("setUp");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_SetUp(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_SetUp(ModelClass modelClass, Element element) {
		String elementPackageName = ModelLayerHelper.getElementPackageName(element);
		String namespaceBaseName = NameUtil.getLastSegmentFromPackageName(elementPackageName);

		Buf buf = new Buf();
		buf.putLine2("testDataSource.setTransactionProvider(TransactionRegistry.getInstance());");
		buf.putLine2("helper = new "+namespaceBaseName+"DaoHelper(createEntityManager());");
		buf.putLine2("fixture = createFixture();");
		buf.putLine2("super.setUp();");
		return buf.get();
	}

	protected ModelOperation createOperation_TearDown(ModelClass modelClass, Element element) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterAnnotation());
		modelOperation.setName("tearDown");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_TearDown(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_TearDown(ModelClass modelClass, Element element) {
		Buf buf = new Buf();
		buf.putLine2("resetTransactionContext();");
		buf.putLine2("teardownEntityManager();");
		buf.putLine2("testDataSource.clear();");
		buf.putLine2("fixture = null;");
		buf.putLine2("super.tearDown();");
		return buf.get();
	}

	protected ModelOperation createOperation_CreateFixture(ModelClass modelClass, Element element) {
		String className = DataLayerHelper.getDAOClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createFixture");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(className);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_CreateFixture(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_CreateFixture(ModelClass modelClass, Element element) {
		String className = DataLayerHelper.getDAOClassName(element);
		Buf buf = new Buf();
		buf.putLine2("fixture = new "+className+"();");
		buf.putLine2("fixture.em = entityManager;");
		buf.putLine2("return fixture;");
		return buf.get();
	}
	
	protected ModelOperation createOperation_CreateEntity(ModelClass modelClass, Element element) {
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+entityClassName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(entityClassName);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_CreateEntity(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_CreateEntity(ModelClass modelClass, Element element) {
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		Buf buf = new Buf();
		if (inverseReference != null)
			buf.putLine2(entityClassName+" "+entityNameUncapped+" = helper.create"+entityClassName+"(container);");
		else buf.putLine2(entityClassName+" "+entityNameUncapped+" = helper.create"+entityClassName+"();");
		buf.putLine2("return "+entityNameUncapped+";");
		return buf.get();
	}
	
	//NOTUSED
	protected ModelOperation createOperation_CreateContainer(ModelClass modelClass, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		Element containerElement = context.getElementByType(inverseReference.getType());
		String containerEntityClassName = DataLayerHelper.getEntityClassName(containerElement);
		
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+containerEntityClassName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(containerEntityClassName);
		modelOperation.addInitialSource(createSourceCode_CreateContainer(modelClass, element));
		return modelOperation;
	}

	//NOTUSED
	protected String createSourceCode_CreateContainer(ModelClass modelClass, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		Element containerElement = context.getElementByType(inverseReference.getType());
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(containerElement);
		String entityClassName = DataLayerHelper.getEntityClassName(containerElement);
		
		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+entityNameUncapped+" = helper.create"+entityClassName+"();");
		List<Field> fieldsByType = ElementUtil.getFieldsByType(containerElement, element.getStructure(), element.getType());
		Iterator<Field> iterator = fieldsByType.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			buf.putLine2("container.set"+fieldNameCapped+"(null);");
		}
		buf.putLine2("return "+entityNameUncapped+";");
		return buf.get();
	}
	
	
	protected ModelOperation createOperation_OpenEntityManager(ModelClass modelClass, Element element) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("openEntityManager");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addInitialSource(createSourceCode_OpenEntityManager(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_OpenEntityManager(ModelClass modelClass, Element element) {
		Buf buf = new Buf();
		buf.putLine2("entityManager = createEntityManager();");
		buf.putLine2("fixture.em = entityManager;");
		return buf.get();
	}

	protected ModelOperation createOperation_CloseEntityManager(ModelClass modelClass, Element element) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("closeEntityManager");
		modelOperation.setModifiers(Modifier.PROTECTED);
		//modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_CloseEntityManager(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_CloseEntityManager(ModelClass modelClass, Element element) {
		Buf buf = new Buf();
		buf.putLine2("if (entityManager.isOpen())");
		buf.putLine2("	entityManager.close();");
		buf.putLine2("fixture.em = null;");
		return buf.get();
	}

	protected ModelOperation createOperation_TestGetAllRecords(ModelClass modelClass, Element element) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setName("testGetAllRecords");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_TestGetAllRecords(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_TestGetAllRecords(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2("assureDeleteAll();");
		if (inverseReference != null)
			buf.putLine2("assureAddContainer();");
		buf.putLine2("assureAdd"+elementNameCapped+"();");
		buf.putLine2("");
		buf.putLine2("// prepare context");
		buf.putLine2("openEntityManager();");
		buf.putLine2("");
		buf.putLine2("//execute test");
		buf.putLine2("List<"+entityClassName+"> records = fixture.getAll"+elementNameCapped+"Records();");
		buf.putLine2("");
		buf.putLine2("// close context");
		buf.putLine2("closeEntityManager();");
		buf.putLine2("");
		buf.putLine2("//validate results");
		
		buf.putLine2("openEntityManager();");
		buf.putLine2("verify"+elementNameCapped+"Count(records, 1);");
		buf.putLine2("closeEntityManager();");
		return buf.get();
	}

	protected ModelOperation createOperation_TestGetRecordById(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setName("testGet"+elementNameCapped+"ById");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_TestGetRecordById(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_TestGetRecordById(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2("assureDeleteAll();");
		if (inverseReference != null)
			buf.putLine2("assureAddContainer();");
		buf.putLine2("Long id = assureAdd"+elementNameCapped+"();");
		buf.putLine2("");
		buf.putLine2("// prepare context");
		buf.putLine2("openEntityManager();");
		buf.putLine2("");
		buf.putLine2("// execute test");
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = fixture.get"+elementNameCapped+"ById(id);");
		buf.putLine2("");
		buf.putLine2("// close context");
		buf.putLine2("closeEntityManager();");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2("Assert.notNull("+elementNameUncapped+", \""+elementNameCapped+" should exist\");");
		buf.putLine2("Assert.equals("+elementNameUncapped+".getId(), id, \"Id should be correct\");");
		return buf.get();
	}
	
	protected ModelOperation createOperation_TestGetRecordById_Null(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setName("testGet"+elementNameCapped+"ById_Null");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_TestGetRecordById_Null(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_TestGetRecordById_Null(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2("assureDeleteAll();");
		if (inverseReference != null)
			buf.putLine2("assureAddContainer();");
		buf.putLine2("assureAdd"+elementNameCapped+"();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// prepare context");
		buf.putLine2("	openEntityManager();");
		buf.putLine2("");
		buf.putLine2("	// execute test");
		buf.putLine2("	fixture.get"+elementNameCapped+"ById(null);");
		buf.putLine2("	fail(\"Exception should have been thrown\");");
		buf.putLine2("");
		buf.putLine2("} catch (AssertionError e) {");
		buf.putLine2("	Assert.exception(e, \"Id must be specified\");");
		buf.putLine2("");
		buf.putLine2("} finally {");
		buf.putLine2("	// close context");
		buf.putLine2("	closeEntityManager();");
		buf.putLine2("}");
		return buf.get();
	}

	protected List<ModelOperation> createOperations_TestGetRecordByField(ModelClass modelClass, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Field> uniqueFields = ElementUtil.getUniqueFields(element);
		Iterator<Field> iterator = uniqueFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (!FieldUtil.isInverse(field) && !FieldUtil.isMappedBy(field)) {
				ModelOperation modelOperation = createOperation_TestGetRecordByField(modelClass, element, field);
				modelOperations.add(modelOperation);
			}
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_TestGetRecordByField(ModelClass modelClass, Element element, Field field) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldQualifiedName = ModelLayerHelper.getFieldQualifiedName(field);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setName("testGet"+elementNameCapped+"By"+fieldNameCapped);
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_TestGetRecordByField(modelClass, element, field));
		modelClass.addImportedClass(fieldQualifiedName);
		return modelOperation;
	}

	protected String createSourceCode_TestGetRecordByField(ModelClass modelClass, Element element, Field field) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2("assureDeleteAll();");
		if (inverseReference != null)
			buf.putLine2("assureAddContainer();");
		buf.putLine2("Long id = assureAdd"+elementNameCapped+"();");
		buf.putLine2("");
		buf.putLine2("// prepare context");
		buf.putLine2("openEntityManager();");
		buf.putLine2("");
		
		String dummyValue = null;
		if (field instanceof Attribute) {
			dummyValue = dummyValueFactory.createSampleValueForAttribute(element, (Attribute) field, 0);
		} else if (field instanceof Reference) {
			dummyValue = dummyValueFactory.createSampleValueForReference(element, (Reference) field, 0);
		}
		
		String fieldNameForMethod = fieldNameCapped;
		Enumeration enumeration = context.getEnumerationByType(field.getType());
		if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName) && enumeration == null) {
			fieldNameForMethod = NameUtil.assureEndsWith(fieldNameForMethod, "Id");
		}
		
		buf.putLine2("// execute test");
		if (fieldClassName.equals("String"))
			buf.putLine2("int index = 0;");
		buf.putLine2(fieldClassName+" "+fieldNameUncapped+" = "+dummyValue+";");
		//buf.putLine2(fieldClassName+" "+fieldNameUncapped+" = \"dummy"+fieldNameCapped+"0\";");
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = fixture.get"+elementNameCapped+"By"+fieldNameForMethod+"("+fieldNameUncapped+");");
		buf.putLine2("");
		buf.putLine2("// close context");
		buf.putLine2("closeEntityManager();");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2("Assert.notNull("+elementNameUncapped+", \""+elementNameCapped+" should exist\");");
		buf.putLine2("Assert.equals("+elementNameUncapped+".get"+fieldNameCapped+"(), "+fieldNameUncapped+", \""+fieldNameCapped+" should be correct\");");
		return buf.get();
	}

	protected List<ModelOperation> createOperations_TestGetRecordByField_Null(ModelClass modelClass, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Field> uniqueFields = ElementUtil.getUniqueFields(element);
		Iterator<Field> iterator = uniqueFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (!FieldUtil.isInverse(field) && !FieldUtil.isMappedBy(field)) {
				ModelOperation modelOperation = createOperation_TestGetRecordByField_Null(modelClass, element, field);
				modelOperations.add(modelOperation);
			}
		}
		return modelOperations;
	}
	
	protected ModelOperation createOperation_TestGetRecordByField_Null(ModelClass modelClass, Element element, Field field) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldQualifiedName = ModelLayerHelper.getFieldQualifiedName(field);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setName("testGet"+elementNameCapped+"By"+fieldNameCapped+"_Null");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_TestGetRecordByField_Null(modelClass, element, field));
		modelClass.addImportedClass(fieldQualifiedName);
		return modelOperation;
	}

	protected String createSourceCode_TestGetRecordByField_Null(ModelClass modelClass, Element element, Field field) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		Reference inverseReference = ElementUtil.getInverseReference(element);

		String fieldNameForMethod = fieldNameCapped;
		Enumeration enumeration = context.getEnumerationByType(field.getType());
		if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName) && enumeration == null) {
			fieldNameForMethod = NameUtil.assureEndsWith(fieldNameForMethod, "Id");
		}

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2("assureDeleteAll();");
		if (inverseReference != null)
			buf.putLine2("assureAddContainer();");
		buf.putLine2("assureAdd"+elementNameCapped+"();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// prepare context");
		buf.putLine2("	openEntityManager();");
		buf.putLine2("");
		buf.putLine2("	// execute test");
		buf.putLine2("	fixture.get"+elementNameCapped+"By"+fieldNameForMethod+"(null);");
		buf.putLine2("	fail(\"Exception should have been thrown\");");
		buf.putLine2("");
		buf.putLine2("} catch (AssertionError e) {");
		buf.putLine2("	Assert.exception(e, \""+fieldNameCapped+" must be specified\");");
		buf.putLine2("");
		buf.putLine2("} finally {");
		buf.putLine2("	// close context");
		buf.putLine2("	closeEntityManager();");
		buf.putLine2("}");
		return buf.get();
	}

	protected ModelOperation createOperation_TestAddRecord_Commit(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setName("testAdd"+elementNameCapped+"_Commit");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_TestAddRecord_Commit(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_TestAddRecord_Commit(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2("assureDeleteAll();");
		if (inverseReference != null)
			buf.putLine2("assureAddContainer();");
		buf.putLine2("");
		buf.putLine2("// prepare context");
		buf.putLine2("openEntityManager();");
		buf.putLine2("beginTransaction();");
		buf.putLine2("");
		buf.putLine2("//execute test");
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = create"+entityClassName+"();");
		buf.putLine2("Long id = fixture.add"+elementNameCapped+"("+elementNameUncapped+");");
		buf.putLine2("Assert.notNull(id, \"Id should exist\");");
		buf.putLine2("");
		buf.putLine2("// close context");
		buf.putLine2("commitTransaction();");
		buf.putLine2("closeEntityManager();");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2("openEntityManager();");
		buf.putLine2("verify"+elementNameCapped+"Count(1);");
		buf.putLine2("verify"+elementNameCapped+"Exists(id);");
		buf.putLine2("closeEntityManager();");
		return buf.get();
	}
	
	protected ModelOperation createOperation_TestAddRecord_Rollback(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setName("testAdd"+elementNameCapped+"_Rollback");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_TestAddRecord_Rollback(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_TestAddRecord_Rollback(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2("assureDeleteAll();");
		if (inverseReference != null)
			buf.putLine2("assureAddContainer();");
		buf.putLine2("");
		buf.putLine2("// prepare context");
		buf.putLine2("openEntityManager();");
		buf.putLine2("beginTransaction();");
		buf.putLine2("");
		buf.putLine2("//execute test");
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = create"+entityClassName+"();");
		buf.putLine2("Long id = fixture.add"+elementNameCapped+"("+elementNameUncapped+");");
		buf.putLine2("Assert.notNull(id, \"Id should exist\");");
		buf.putLine2("");
		buf.putLine2("// close context");
		buf.putLine2("rollbackTransaction();");
		buf.putLine2("closeEntityManager();");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2("openEntityManager();");
		buf.putLine2("verify"+elementNameCapped+"Count(0);");
		buf.putLine2("closeEntityManager();");
		return buf.get();
	}
	
	protected ModelOperation createOperation_TestAddRecord_Exception(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setName("testAdd"+elementNameCapped+"_Exception");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_TestAddRecord_Exception(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_TestAddRecord_Exception(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2("assureDeleteAll();");
		if (inverseReference != null)
			buf.putLine2("assureAddContainer();");
		buf.putLine2("");
		buf.putLine2("// prepare context");
		buf.putLine2("openEntityManager();");
		buf.putLine2("beginTransaction();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	//execute test");
		buf.putLine2("	"+entityClassName+" "+elementNameUncapped+" = create"+entityClassName+"();");
		
		String fieldName = null;
		String columnName = null;
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isRequired(field)) {
				fieldName = NameUtil.capName(field.getName());
				columnName = field.getColumn();
				break;
			}
		}
		
		buf.putLine2("	"+elementNameUncapped+".set"+fieldName+"(null);");
		buf.putLine2("	fixture.add"+elementNameCapped+"("+elementNameUncapped+");");
		buf.putLine2("	fail(\"Exception should have been thrown\");");
		buf.putLine2("");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	Assert.exception(e, org.hibernate.exception.ConstraintViolationException.class, \"Column '"+columnName+"' cannot be null\");");
		buf.putLine2("	Assert.exception(e.getCause(), com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException.class, \"Column '"+columnName+"' cannot be null\");");
		buf.putLine2("}");
		buf.putLine2("");
		buf.putLine2("// close context");
		buf.putLine2("rollbackTransaction();");
		buf.putLine2("closeEntityManager();");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2("openEntityManager();");
		buf.putLine2("verify"+elementNameCapped+"Count(0);");
		buf.putLine2("closeEntityManager();");
		return buf.get();
	}
	

	protected ModelOperation createOperation_AssureAddContainer1(ModelClass modelClass, Element element) {
		//String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("assureAddContainer");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_AssureAddContainer1(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_AssureAddContainer1(ModelClass modelClass, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		Element containerElement = context.getElementByType(inverseReference.getType());
		String containerEntityNameUncapped = DataLayerHelper.getEntityNameUncapped(containerElement);
		String containerEntityClassName = DataLayerHelper.getEntityClassName(containerElement);
		//TODO use this as the local name?
		containerEntityNameUncapped = "container";
		
		Buf buf = new Buf();
		buf.putLine2(containerEntityNameUncapped+" = helper.create"+containerEntityClassName+"();");
		List<Field> fieldsByType = ElementUtil.getFieldsByType(containerElement, element.getStructure(), element.getType());
		Iterator<Field> iterator = fieldsByType.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
			buf.putLine2(containerEntityNameUncapped+".set"+fieldNameCapped+"(null);");
		}
		buf.putLine2("Long id = assureAddContainer("+containerEntityNameUncapped+");");
		buf.putLine2(containerEntityNameUncapped+".setId(id);");
		buf.putLine2("return id;");
		return buf.get();
	}
	
	protected ModelOperation createOperation_AssureAddContainer2(ModelClass modelClass, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		Element containerElement = context.getElementByType(inverseReference.getType());
		String containerEntityNameUncapped = DataLayerHelper.getEntityNameUncapped(containerElement);
		String containerEntityClassName = DataLayerHelper.getEntityClassName(containerElement);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("assureAddContainer");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter(containerEntityClassName, containerEntityNameUncapped));
		modelOperation.addInitialSource(createSourceCode_AssureAddContainer2(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_AssureAddContainer2(ModelClass modelClass, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		Element containerElement = context.getElementByType(inverseReference.getType());
		String containerEntityNameCapped = DataLayerHelper.getEntityNameCapped(containerElement);
		String containerEntityNameUncapped = DataLayerHelper.getEntityNameUncapped(containerElement);
		
		Buf buf = new Buf();
		buf.putLine2("Long id = helper.persist"+containerEntityNameCapped+"("+containerEntityNameUncapped+");");
		buf.putLine2("return id;");
		return buf.get();
	}
	

	protected ModelOperation createOperation_AssureAddRecord1(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("assureAdd"+elementNameCapped);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(createSourceCode_AssureAddRecord1(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_AssureAddRecord1(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+entityNameUncapped+" = create"+elementNameCapped+"Entity();");
		buf.putLine2("Long id = assureAdd"+elementNameCapped+"("+entityNameUncapped+");");
		buf.putLine2("return id;");
		return buf.get();
	}

	protected ModelOperation createOperation_AssureAddRecord2(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("assureAdd"+elementNameCapped);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter(entityClassName, elementNameUncapped));
		modelOperation.addInitialSource(createSourceCode_AssureAddRecord2(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_AssureAddRecord2(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		Buf buf = new Buf();
		buf.putLine2("openEntityManager();");
		buf.putLine2("beginTransaction();");
		buf.putLine2("Long id = fixture.add"+elementNameCapped+"("+elementNameUncapped+");");		
		buf.putLine2("commitTransaction();");
		buf.putLine2("verify"+elementNameCapped+"Exists(id);");
		buf.putLine2("closeEntityManager();");
		buf.putLine2("return id;");
		return buf.get();
	}
	
	protected ModelOperation createOperation_AssureDeleteRecord1(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("assureDelete"+elementNameCapped);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter("Long", "id"));
		modelOperation.addInitialSource(createSourceCode_AssureDeleteRecord1(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_AssureDeleteRecord1(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = fixture.get"+elementNameCapped+"ById(id);");
		buf.putLine2("assureDelete"+elementNameCapped+"("+elementNameUncapped+");");
		return buf.get();
	}
	
	protected ModelOperation createOperation_AssureDeleteRecord2(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("assureDelete"+elementNameCapped);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter(entityClassName, elementNameUncapped));
		modelOperation.addInitialSource(createSourceCode_AssureDeleteRecord2(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_AssureDeleteRecord2(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		Buf buf = new Buf();
		buf.putLine2("openEntityManager();");
		buf.putLine2("beginTransaction();");
		buf.putLine2("fixture.delete"+elementNameCapped+"("+elementNameUncapped+");");
		buf.putLine2("commitTransaction();");
		buf.putLine2("verify"+elementNameCapped+"NotExists("+elementNameUncapped+");");
		buf.putLine2("closeEntityManager();");
		return buf.get();
	}
	
	protected ModelOperation createOperation_VerifyRecordCount1(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("verify"+elementNameCapped+"Count");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter("int", "expectedCount"));
		modelOperation.addInitialSource(createSourceCode_VerifyRecordCount1(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_VerifyRecordCount1(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Buf buf = new Buf();
		buf.putLine2("List<"+entityClassName+"> records = fixture.getAll"+elementNameCapped+"Records();");
		buf.putLine2("verify"+elementNameCapped+"Count(records, expectedCount);");
		return buf.get();
	}
	
	protected ModelOperation createOperation_VerifyRecordCount2(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("verify"+elementNameCapped+"Count");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter("List<"+entityClassName+">", "records"));
		modelOperation.addParameter(createParameter("int", "expectedCount"));
		modelOperation.addInitialSource(createSourceCode_VerifyRecordCount2(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_VerifyRecordCount2(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Buf buf = new Buf();
		buf.putLine2("Assert.notNull(records, \"Result not found\");");
		buf.putLine2("if (expectedCount > 0)");
		buf.putLine2("	Assert.notNull(records, \""+elementNameCapped+" records should exist\");");
		buf.putLine2("Assert.equals(records.size(), expectedCount, \""+elementNameCapped+" record count not correct\");");
		return buf.get();
	}
	
	protected ModelOperation createOperation_VerifyRecordExists1(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("verify"+elementNameCapped+"Exists");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter(entityClassName, elementNameUncapped));
		modelOperation.addInitialSource(createSourceCode_VerifyRecordExists1(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_VerifyRecordExists1(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		Buf buf = new Buf();
		buf.putLine2("verify"+elementNameCapped+"Exists("+elementNameUncapped+".getId());");
		return buf.get();
	}
	
	protected ModelOperation createOperation_VerifyRecordExists2(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("verify"+elementNameCapped+"Exists");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter("Long", "id"));
		modelOperation.addInitialSource(createSourceCode_VerifyRecordExists2(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_VerifyRecordExists2(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = fixture.get"+elementNameCapped+"ById(id);");
		buf.putLine2("Assert.notNull("+elementNameUncapped+", \""+elementNameCapped+" should exist\");");
		buf.putLine2("Assert.equals("+elementNameUncapped+".getId(), id, \"Ids should be same\");");
		return buf.get();
	}
	
	protected ModelOperation createOperation_VerifyRecordNotExists1(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("verify"+elementNameCapped+"NotExists");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter(entityClassName, elementNameUncapped));
		modelOperation.addInitialSource(createSourceCode_VerifyRecordNotExists1(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_VerifyRecordNotExists1(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		Buf buf = new Buf();
		buf.putLine2("verify"+elementNameCapped+"NotExists("+elementNameUncapped+".getId());");
		return buf.get();
	}
	
	protected ModelOperation createOperation_VerifyRecordNotExists2(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("verify"+elementNameCapped+"NotExists");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.addParameter(createParameter("Long", "id"));
		modelOperation.addInitialSource(createSourceCode_VerifyRecordNotExists2(modelClass, element));
		return modelOperation;
	}

	protected String createSourceCode_VerifyRecordNotExists2(ModelClass modelClass, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = fixture.get"+elementNameCapped+"ById(id);");
		buf.putLine2("Assert.isNull("+elementNameUncapped+", \""+elementNameCapped+" should not exist\");");
		return buf.get();
	}
	
}
