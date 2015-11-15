package nam.data.src.test.java;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Persistence;
import nam.model.Reference;
import nam.model.TransactionOutcome;
import nam.model.TransactionType;
import nam.model.Unit;
import nam.model.src.main.java.DummyValueFactory;
import nam.model.util.ElementUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.TypeUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelReference;


public abstract class AbstractDataLayerITBuilder extends AbstractBeanBuilder {

	protected Unit unit;
	
	protected DummyValueFactory dummyValueFactory;

	
	public AbstractDataLayerITBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	protected void initialize() {
		dummyValueFactory = new DummyValueFactory(context);
	}

	protected void initializeClassAnnotations(ModelClass modelClass, Element element) throws Exception {
		modelClass.addClassAnnotation(AnnotationUtil.createMockitoJUnitRunnerAnnotation());
		modelClass.addClassAnnotation(AnnotationUtil.createAnnotation("FixMethodOrder", "NAME_ASCENDING"));
		modelClass.addImportedClass("org.junit.runner.RunWith");
		modelClass.addImportedClass("org.mockito.runners.MockitoJUnitRunner");
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("java.util.Iterator");
		modelClass.addImportedClass("java.util.List");
		
		modelClass.addImportedClass("javax.persistence.EntityManager");
		modelClass.addImportedClass("javax.persistence.Query");
		
//		modelClass.addImportedClass("javax.sql.DataSource");
//		modelClass.addImportedClass("javax.transaction.Transaction");
//		modelClass.addImportedClass("javax.transaction.TransactionManager");
		
		modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.aries.AssertionFailure");
		
//		modelClass.addImportedClass("org.aries.common.AbstractDaoTest");
//		modelClass.addImportedClass("org.aries.common.DataSourceTestFixture");
//		modelClass.addImportedClass("org.aries.common.TestXADataSource");
//		modelClass.addImportedClass("org.aries.common.entity.EmailAddressEntity");
//		modelClass.addImportedClass("org.aries.common.util.SqlScriptExecutor");
//		modelClass.addImportedClass("com.arjuna.ats.internal.arjuna.thread.ThreadActionData");
//		modelClass.addImportedClass("com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple");
//		modelClass.addImportedClass("tx.manager.registry.TransactionRegistry");
//		modelClass.addImportedClass("org.aries.TestContext");
//		modelClass.addImportedClass("org.aries.TestContextFactory");
		
		modelClass.addStaticImportedClass("org.junit.Assert.fail");
		modelClass.addStaticImportedClass("org.junit.runners.MethodSorters.NAME_ASCENDING");

		modelClass.addImportedClass("org.junit.After");
		modelClass.addImportedClass("org.junit.AfterClass");
		modelClass.addImportedClass("org.junit.Before");
		modelClass.addImportedClass("org.junit.BeforeClass");
		modelClass.addImportedClass("org.junit.FixMethodOrder");
		modelClass.addImportedClass("org.junit.Test");
	}
	

	/*
	 * Instance fields:
	 */
	
	protected ModelReference createInstanceField_TransactionTestControl(Namespace namespace, Element element) {
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE + Modifier.STATIC);
		modelReference.setPackageName("org.aries.tx");
		modelReference.setClassName("TransactionTestControl");
		modelReference.setName("transactionControl");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass("org.aries.tx.TransactionTestControl");
		return modelReference;
	}
	
	protected ModelReference createInstanceField_DataLayerTestControl(Namespace namespace, Element element) {
		String unitName = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String unitType = DataLayerHelper.getPersistenceUnitType(unit);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE + Modifier.STATIC);
		modelReference.setPackageName(" org.aries.tx");
		modelReference.setClassName("DataLayerTestControl");
		modelReference.setName(unitName + "Control");
		modelReference.setType(unitType + "Control");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass("org.aries.tx.DataLayerTestControl");
		return modelReference;
	}
	
	protected ModelReference createInstanceField_HelperBean(Namespace namespace, Element element) {
		String unitName = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String unitType = DataLayerHelper.getPersistenceUnitType(unit);
		
		String helperClassName = NameUtil.capName(unit.getName()) + "Helper";
		String helperPackageName = DataLayerHelper.getHelperPackageName(unit);
		String helperQualifiedName = DataLayerHelper.getHelperQualifiedName(unit);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(helperPackageName);
		modelReference.setClassName(helperClassName);
		modelReference.setName(unitName + "Helper");
		modelReference.setType(unitType + "Helper");
		modelReference.setStructure("item");
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass(helperQualifiedName);
		return modelReference;
	}
	
	protected ModelReference createInstanceField_DAOBean(Namespace namespace, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String daoPackageName = DataLayerHelper.getDAOPackageName(namespace);
		String daoInterfaceName = DataLayerHelper.getDAOInterfaceName(element);
		String daoQualifiedName = DataLayerHelper.getDAOQualifiedClassName(namespace, element);
		String daoType = org.aries.util.TypeUtil.getDerivedType(element.getType(), daoNameUncapped);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(daoPackageName);
		modelReference.setClassName(daoInterfaceName+"<"+elementNameCapped+"Entity>");
		modelReference.setName(daoNameUncapped);
		modelReference.setType(daoType);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass(daoQualifiedName);
		return modelReference;
	}

	protected ModelReference createInstanceField_EntityBean(Namespace namespace, Element element) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String entityNameUncapped = DataLayerHelper.getEntityNameUncapped(element);
		String entityPackageName = DataLayerHelper.getEntityPackageName(namespace);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityQualifiedName = entityPackageName + "." + entityClassName;
		String entityType = org.aries.util.TypeUtil.getDerivedType(element.getType(), entityNameUncapped);

		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName(entityPackageName);
		modelReference.setClassName(entityClassName);
		modelReference.setName(entityNameUncapped);
		modelReference.setType(entityType);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass(entityQualifiedName);
		return modelReference;
	}

	protected ModelReference createInstanceField_EntityManager() {
		ModelReference modelReference = new ModelReference();
		modelReference.setModifiers(Modifier.PRIVATE);
		modelReference.setPackageName("javax.persistence");
		modelReference.setClassName("EntityManager");
		modelReference.setName("entityManager");
		//modelReference.setType(entityType);
		modelReference.setGenerateGetter(false);
		modelReference.setGenerateSetter(false);
		modelReference.addImportedClass("javax.persistence.EntityManager");
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
	 * Instance methods:
	 */


	protected ModelOperation createOperation_CreateTransactionTestConotrol(ModelClass modelClass, Element element) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createTransactionControl");
		modelOperation.setModifiers(Modifier.PROTECTED + Modifier.STATIC);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("transactionControl = new TransactionTestControl();");
		buf.putLine2("transactionControl.setupTransactionManager();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_CreateDataLayerTestConotrol(ModelClass modelClass, Element element) {
		String unitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(unit);
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+unitNameCapped+"Control");
		modelOperation.setModifiers(Modifier.PROTECTED + Modifier.STATIC);
		modelOperation.addException("Exception");

		String namespace = unit.getNamespace().getUri();
		Persistence persistenceBlock = ProjectUtil.getPersistenceBlockByNamespace(context.getProject(), namespace);
		String databaseName = DataLayerHelper.getDatabaseName(persistenceBlock);
		String dataSourceName = DataLayerHelper.getDataSourceName(persistenceBlock);
		
		Buf buf = new Buf();
		buf.putLine2(unitNameUncapped+"Control = new DataLayerTestControl(transactionControl);");
		buf.putLine2(unitNameUncapped+"Control.setDatabaseName(\""+databaseName+"\");");
		buf.putLine2(unitNameUncapped+"Control.setDataSourceName(\""+dataSourceName+"\");");
		buf.putLine2(unitNameUncapped+"Control.setPersistenceUnitName(\""+unitNameUncapped+"\");");
		buf.putLine2(unitNameUncapped+"Control.setupDataLayer();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_CreateHelper(ModelClass modelClass, Element element) {
		String unitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(unit);
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+unitNameCapped+"Helper");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2(unitNameUncapped+"Helper = new "+unitNameCapped+"Helper();");
		buf.putLine2(unitNameUncapped+"Helper.initialize("+unitNameUncapped+"Control);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_CreateFixture(ModelClass modelClass, Element element) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameCapped = DataLayerHelper.getDAONameCapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+daoNameCapped);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2(daoNameUncapped+" = "+unitNameUncapped+"Helper.create"+daoNameCapped+"();");
		buf.putLine2(daoNameUncapped+".setEntityManager(createEntityManager());");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_CreateEntityManager() {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createEntityManager");
		modelOperation.setModifiers(Modifier.PROTECTED);
		//modelOperation.setResultType("EntityManager");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("entityManager = "+unitNameUncapped+"Control.createEntityManager();");
		//buf.putLine2("EntityManager entityManager = "+unitNameUncapped+"Control.setupEntityManager();");
		//buf.putLine2("return entityManager;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_CreateEntityManager2() {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createEntityManager");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("EntityManager");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("return "+unitNameUncapped+"Control.createEntityManager();");
		//buf.putLine2("EntityManager entityManager = "+unitNameUncapped+"Control.setupEntityManager();");
		//buf.putLine2("return entityManager;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_CreateEntity(ModelClass modelClass, Element element) {
		return createOperation_CreateEntity(modelClass, element, false);
	}

	protected ModelOperation createOperation_CreateEmptyEntity(ModelClass modelClass, Element element) {
		return createOperation_CreateEntity(modelClass, element, true);
	}

	protected ModelOperation createOperation_CreateEntity(ModelClass modelClass, Element element, boolean isEmpty) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = elementNameCapped + "Entity";
		//String entityNameUncapped = elementNameUncapped + "Entity";
		String createtionName = isEmpty ? "createEmpty" : "create";
			
		Reference inverseReference = ElementUtil.getInverseReference(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName(createtionName + entityClassName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(entityClassName);
		//modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		if (inverseReference != null)
			buf.putLine2("return "+unitNameUncapped+"Helper."+createtionName+entityClassName+"(container);");
		else buf.putLine2("return "+unitNameUncapped+"Helper."+createtionName+entityClassName+"();");
		
//		if (inverseReference != null)
//			buf.putLine2(entityClassName+" entity = "+unitNameUncapped+"Helper."+createtionName+entityClassName+"(container);");
//		else buf.putLine2(entityClassName+" entity = "+unitNameUncapped+"Helper."+createtionName+entityClassName+"();");
//		buf.putLine2("return entity;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
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
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		Buf buf = new Buf();
		buf.putLine2("entityManager = createEntityManager();");
		buf.putLine2(""+daoNameUncapped+".em = entityManager;");
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
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		Buf buf = new Buf();
		buf.putLine2("if (entityManager.isOpen())");
		buf.putLine2("	entityManager.close();");
		buf.putLine2(""+daoNameUncapped+".em = null;");
		return buf.get();
	}


	
	
	protected ModelOperation createOperation_AssureAddContainer1(ModelClass modelClass, Element element) {
		Reference inverseReference = ElementUtil.getInverseReference(element);
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		Element containerElement = context.getElementByType(inverseReference.getType());
		String containerEntityNameUncapped = DataLayerHelper.getEntityNameUncapped(containerElement);
		String containerEntityClassName = DataLayerHelper.getEntityClassName(containerElement);
		//TODO use this as the local name?
		containerEntityNameUncapped = "container";

		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("assureAddContainer");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("Long");
		modelOperation.addException("Exception");

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
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
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
		buf.putLine2(entityClassName+" "+entityNameUncapped+" = create"+entityClassName+"();");
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
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		Buf buf = new Buf();
		buf.putLine2("openEntityManager();");
		buf.putLine2("beginTransaction();");
		buf.putLine2("Long id = "+daoNameUncapped+".add"+elementNameCapped+"Record("+elementNameUncapped+");");		
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
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = "+daoNameUncapped+".get"+elementNameCapped+"RecordById(id);");
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
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		Buf buf = new Buf();
		buf.putLine2("openEntityManager();");
		buf.putLine2("beginTransaction();");
		buf.putLine2(""+daoNameUncapped+".remove"+elementNameCapped+"Record("+elementNameUncapped+");");
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
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Buf buf = new Buf();
		buf.putLine2("List<"+entityClassName+"> records = "+daoNameUncapped+".getAll"+elementNameCapped+"Records();");
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
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = "+daoNameUncapped+".get"+elementNameCapped+"RecordById(id);");
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
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getEntityClassName(element);
		Buf buf = new Buf();
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = "+daoNameUncapped+".get"+elementNameCapped+"RecordById(id);");
		buf.putLine2("Assert.isNull("+elementNameUncapped+", \""+elementNameCapped+" should not exist\");");
		return buf.get();
	}


	
	
	
	public static String getTestNameSuffix(TransactionType transactionType, TransactionOutcome transactionOutcome) throws Exception {
		switch (transactionType) {
		case JTA:
			switch (transactionOutcome) {
			case COMMIT:
				return "_commit";
			case ROLLBACK:
				return "_rollback";
			case NONE:
				return "";
			default:
				throw new Exception("Unexpected transaction outcome: "+transactionOutcome);
			}
		case USER:
			switch (transactionOutcome) {
			case COMMIT:
				return "_utx_commit";
			case ROLLBACK:
				return "_utx_rollback";
			case NONE:
				return "";
			default:
				throw new Exception("Unexpected transaction outcome: "+transactionOutcome);
			}
		case NONE:
			return "";
		default:
			throw new Exception("Unexpected transaction type: "+transactionType);
		}
	}

	public static String getPrepareContextSource(TransactionType transactionType) throws Exception {
		Buf buf = new Buf();
		if (transactionType != TransactionType.NONE) {
			buf.putLine2("");
			buf.putLine2("// prepare context");
			switch (transactionType) {
			case JTA:
				//buf.putLine2("openEntityManager();");
				buf.putLine2("transactionControl.beginTransaction();");
				break;
			case USER:
				//buf.putLine2("openEntityManager();");
				buf.putLine2("transactionControl.beginUserTransaction();");
				break;
			case NONE:
				break;
			default:
				throw new Exception("Unexpected transaction type: "+transactionType);
			}
		}
		if (buf.isEmpty())
			return null;
		return buf.get();
	}

	public static String getCloseContextSource(TransactionType transactionType, TransactionOutcome transactionOutcome) throws Exception {
		Buf buf = new Buf();
		if (transactionType != TransactionType.NONE) {
			buf.putLine2("");
			buf.putLine2("// close context");
			switch (transactionType) {
			case JTA:
				switch (transactionOutcome) {
				case COMMIT:
					buf.putLine2("transactionControl.commitTransaction();");
					//buf.putLine2("closeEntityManager();");
					break;
				case ROLLBACK:
					buf.putLine2("transactionControl.rollbackTransaction();");
					//buf.putLine2("closeEntityManager();");
					break;
				case NONE:
					break;
				default:
					throw new Exception("Unexpected transaction outcome: "+transactionOutcome);
				}
				break;
			case USER:
				switch (transactionOutcome) {
				case COMMIT:
					buf.putLine2("transactionControl.commitUserTransaction();");
					//buf.putLine2("closeEntityManager();");
					break;
				case ROLLBACK:
					buf.putLine2("transactionControl.rollbackUserTransaction();");
					//buf.putLine2("closeEntityManager();");
					break;
				case NONE:
					break;
				default:
					throw new Exception("Unexpected transaction outcome: "+transactionOutcome);
				}
				break;
			case NONE:
				break;
			default:
				throw new Exception("Unexpected transaction type: "+transactionType);
			}
		}
		if (buf.isEmpty())
			return null;
		return buf.get();
	}
	
}

