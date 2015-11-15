package nam.data.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Attribute;
import nam.model.Element;
import nam.model.Enumeration;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Namespace;
import nam.model.Reference;
import nam.model.TransactionOutcome;
import nam.model.TransactionType;
import nam.model.Unit;
import nam.model.util.ElementUtil;
import nam.model.util.FieldUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;

import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;


public class DaoITBuilder extends AbstractDataLayerITBuilder {

	public DaoITBuilder(GenerationContext context) {
		super(context);
		initialize();
	}

	public List<ModelClass> buildClasses(Unit unit) throws Exception {
		this.unit = unit;
		List<Element> elements = UnitUtil.getElements(unit);
		return buildClasses(unit.getNamespace(), elements);
	}
	
	public List<ModelClass> buildClasses(Namespace namespace, List<Element> elements) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			modelClasses.add(buildClass(namespace, element));
		}
		return modelClasses;
	}
	
	protected ModelClass buildClass(Namespace namespace, Element element) throws Exception {
		//String daoQualifiedName = DataLayerHelper.getDAOQualifiedName(element);
		//String daoInterfaceName = DataLayerHelper.getDAOInterfaceName(element);
		String daoNameCapped = DataLayerHelper.getDAONameCapped(element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String packageName = DataLayerHelper.getDAOPackageName(namespace);
		String daoType = org.aries.util.TypeUtil.getDerivedType(element.getType(), daoNameUncapped);
		//String daoType = TypeUtil.getTypeFromNamespace(namespace);

		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(daoNameCapped + "IT");
		modelClass.setName(daoNameUncapped);
		modelClass.setType(daoType);
		
		this.namespace = namespace;
		dummyValueFactory.setNamespace(namespace);
		initializeClass(modelClass, namespace, element);
		return modelClass;
	}

	public void initializeClass(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		initializeClassAnnotations(modelClass, element);
		initializeImportedClasses(modelClass, namespace, element);
		initializeInstanceFields(modelClass, namespace, element);
		initializeInstanceMethods(modelClass, namespace, element);
	}

	protected void initializeImportedClasses(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		super.initializeImportedClasses(modelClass, namespace, element);
		String daoQualifiedName = DataLayerHelper.getDAOQualifiedClassName(namespace, element);
		String entityQualifiedName = DataLayerHelper.getInferredEntityQualifiedName(namespace, element);
		String entityFixtureQualifiedName = DataLayerHelper.getEntityFixtureQualifiedName(namespace);
		modelClass.addImportedClass(daoQualifiedName);
		modelClass.addImportedClass(entityQualifiedName);
		modelClass.addImportedClass(entityFixtureQualifiedName);
		
	}
	
	/*
	 * initializeInstanceFields:
	 */
	
	protected void initializeInstanceFields(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		modelClass.addInstanceReference(createInstanceField_TransactionTestControl(namespace, element));
		modelClass.addInstanceReference(createInstanceField_DataLayerTestControl(namespace, element));
		modelClass.addInstanceReference(createInstanceField_HelperBean(namespace, element));
		modelClass.addInstanceReference(createInstanceField_DAOBean(namespace, element));
		Reference inverseReference = ElementUtil.getInverseReference(element);
		if (inverseReference != null)
			modelClass.addInstanceReference(createInstanceField_ContainerBean(namespace, element));
	}
	
	/*
	 * initializeInstanceMethods:
	 */
	
	protected void initializeInstanceMethods(ModelClass modelClass, Namespace namespace, Element element) throws Exception {
		Reference inverseReference = ElementUtil.getInverseReference(element);

		modelClass.addInstanceOperation(createOperation_BeforeCLass(modelClass, element));
		modelClass.addInstanceOperation(createOperation_AfterCLass(modelClass, element));
		modelClass.addInstanceOperation(createOperation_CreateTransactionTestConotrol(modelClass, element));
		modelClass.addInstanceOperation(createOperation_CreateDataLayerTestConotrol(modelClass, element));
		modelClass.addInstanceOperation(createOperation_SetUp(modelClass, element));
		modelClass.addInstanceOperation(createOperation_TearDown(modelClass, element));
		modelClass.addInstanceOperation(createOperation_CreateHelper(modelClass, element));
		modelClass.addInstanceOperation(createOperation_CreateFixture(modelClass, element));
		modelClass.addInstanceOperation(createOperation_CreateEntityManager2());
		modelClass.addInstanceOperation(createOperation_CreateEntity(modelClass, element));
		modelClass.addInstanceOperation(createOperation_CreateEmptyEntity(modelClass, element));
//		if (inverseReference != null)
//			modelClass.addInstanceOperation(createOperation_CreateContainer(modelClass, element));
		//modelClass.addInstanceOperation(createOperation_OpenEntityManager(modelClass, element));
		//modelClass.addInstanceOperation(createOperation_CloseEntityManager(modelClass, element));
		
		modelClass.addInstanceOperation(createTestOperations_GetAllRecords(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_GetRecordById(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_GetRecordById_null(modelClass, element));
		modelClass.addInstanceOperations(createTestOperations_GetRecordByField(modelClass, element));
		modelClass.addInstanceOperations(createTestOperations_GetRecordByField_null(modelClass, element));
		
		modelClass.addInstanceOperation(createTestOperations_AddAsItem(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_AddAsItem_tx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_AddAsItem_tx_rollback(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_AddAsItem_utx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_AddAsItem_utx_rollback(modelClass, element));
		modelClass.addInstanceOperations(createTestOperations_AddAsItem_exceptions(modelClass, element));

		modelClass.addInstanceOperation(createTestOperations_AddAsList(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_AddAsList_tx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_AddAsList_tx_rollback(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_AddAsList_utx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_AddAsList_utx_rollback(modelClass, element));
		//modelClass.addInstanceOperations(createTestOperations_AddAsList_exceptions(modelClass, element));
		
		modelClass.addInstanceOperation(createTestOperations_SaveAsItem(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_SaveAsItem_tx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_SaveAsItem_tx_rollback(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_SaveAsItem_utx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_SaveAsItem_utx_rollback(modelClass, element));
		//modelClass.addInstanceOperations(createTestOperations_SaveAsItem_exceptions(modelClass, element));

		modelClass.addInstanceOperation(createTestOperations_RemoveAll(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAll_tx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAll_tx_rollback(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAll_utx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAll_utx_rollback(modelClass, element));
		//modelClass.addInstanceOperations(createTestOperations_RemoveAll_exceptions(modelClass, element));

		modelClass.addInstanceOperation(createTestOperations_RemoveAsItem(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAsItem_tx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAsItem_tx_rollback(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAsItem_utx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAsItem_utx_rollback(modelClass, element));
		//modelClass.addInstanceOperations(createTestOperations_RemoveAsItem_exceptions(modelClass, element));
		
		modelClass.addInstanceOperation(createTestOperations_RemoveAsList(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAsList_tx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAsList_tx_rollback(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAsList_utx_commit(modelClass, element));
		modelClass.addInstanceOperation(createTestOperations_RemoveAsList_utx_rollback(modelClass, element));
		//modelClass.addInstanceOperations(createTestOperations_RemoveAsList_exceptions(modelClass, element));

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
		String unitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(unit);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeClassAnnotation());
		modelOperation.setName("beforeClass");
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("createTransactionControl();");
		buf.putLine2("create"+unitNameCapped+"Control();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_AfterCLass(ModelClass modelClass, Element element) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterClassAnnotation());
		modelOperation.setName("afterCLass");
		modelOperation.setModifiers(Modifier.PUBLIC + Modifier.STATIC);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("transactionControl.tearDownTransactionManager();");
		buf.putLine2(unitNameUncapped+"Control.tearDownPersistenceUnit();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_SetUp(ModelClass modelClass, Element element) {
		String unitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(unit);
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameCapped = DataLayerHelper.getDAONameCapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeAnnotation());
		modelOperation.setName("setUp");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		//buf.putLine2("super.setUp();");
		buf.putLine2(unitNameUncapped+"Control.setUp();");
		buf.putLine2("create"+unitNameCapped+"Helper();");
		buf.putLine2("create"+daoNameCapped+"();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_TearDown(ModelClass modelClass, Element element) {
		String unitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(unit);
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterAnnotation());
		modelOperation.setName("tearDown");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("transactionControl.tearDown();");
		buf.putLine2(unitNameUncapped+"Control.tearDown();");
		buf.putLine2(unitNameUncapped+"Helper = null;");
		buf.putLine2(daoNameUncapped+" = null;");
		//buf.putLine2("super.tearDown();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createTestOperations_GetAllRecords(ModelClass modelClass, Element element) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_GetAllRecords";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		buf.putLine2(helperName+".assureAdd"+elementNameCapped+"();");
		//buf.putLine2("");
		//buf.putLine2("// prepare context");
		//buf.putLine2("openEntityManager();");
		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2("List<"+entityClassName+"> records = "+daoNameUncapped+".getAll"+elementLocalPartCapped+"Records();");
		//buf.putLine2("");
		//buf.putLine2("// close context");
		//buf.putLine2("closeEntityManager();");
		buf.putLine2("");
		buf.putLine2("//validate results");
		
		//buf.putLine2("openEntityManager();");
		buf.putLine2(helperName+".verify"+elementNameCapped+"Count(records, 1);");
		//buf.putLine2("closeEntityManager();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createTestOperations_GetRecordById(ModelClass modelClass, Element element) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_GetRecordById";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		buf.putLine2("Long id = "+helperName+".assureAdd"+elementNameCapped+"();");
		//buf.putLine2("");
		//buf.putLine2("// prepare context");
		//buf.putLine2("openEntityManager();");
		buf.putLine2("");
		buf.putLine2("// execute test");
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = "+daoNameUncapped+".get"+elementLocalPartCapped+"RecordById(id);");
		//buf.putLine2("");
		//buf.putLine2("// close context");
		//buf.putLine2("closeEntityManager();");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2("Assert.notNull("+elementNameUncapped+", \""+elementNameCapped+" should exist\");");
		buf.putLine2("Assert.equals("+elementNameUncapped+".getId(), id, \"Id should be correct\");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createTestOperations_GetRecordById_null(ModelClass modelClass, Element element) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		Reference inverseReference = ElementUtil.getInverseReference(element);

		String testName = "runTest_"+elementNameCapped+"_GetRecordById_null";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);
		
		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		buf.putLine2(helperName+".assureAdd"+elementNameCapped+"();");
		buf.putLine2("");
		buf.putLine2("try {");
		//buf.putLine2("	// prepare context");
		//buf.putLine2("	openEntityManager();");
		//buf.putLine2("");
		buf.putLine2("	// execute test");
		buf.putLine2("	"+daoNameUncapped+".get"+elementLocalPartCapped+"RecordById(null);");
		buf.putLine2("	fail(\"Exception should have been thrown\");");
		buf.putLine2("");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	assertAssertionFailure(e, \"Id must be specified\");");
		//buf.putLine2("");
		//buf.putLine2("} finally {");
		//buf.putLine2("	// close context");
		//buf.putLine2("	closeEntityManager();");
		buf.putLine2("}");

		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected List<ModelOperation> createTestOperations_GetRecordByField(ModelClass modelClass, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Field> uniqueFields = ElementUtil.getUniqueFields(element);
		Iterator<Field> iterator = uniqueFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (!FieldUtil.isInverse(field) && !FieldUtil.isMappedBy(field)) {
				ModelOperation modelOperation = createTestOperations_GetRecordByField(modelClass, element, field);
				modelOperations.add(modelOperation);
			}
		}
		return modelOperations;
	}
	
	protected ModelOperation createTestOperations_GetRecordByField(ModelClass modelClass, Element element, Field field) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		String fieldQualifiedName = ModelLayerHelper.getFieldQualifiedName(field);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_GetRecordBy"+fieldNameCapped;
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		buf.putLine2("Long id = "+helperName+".assureAdd"+elementNameCapped+"();");
		//buf.putLine2("");
		//buf.putLine2("// prepare context");
		//buf.putLine2("openEntityManager();");
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
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = "+daoNameUncapped+".get"+elementLocalPartCapped+"RecordBy"+fieldNameForMethod+"("+fieldNameUncapped+");");
		//buf.putLine2("");
		//buf.putLine2("// close context");
		//buf.putLine2("closeEntityManager();");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2("Assert.notNull("+elementNameUncapped+", \""+elementNameCapped+" should exist\");");
		buf.putLine2("Assert.equals("+elementNameUncapped+".get"+fieldNameCapped+"(), "+fieldNameUncapped+", \""+fieldNameCapped+" should be correct\");");

		modelOperation.addInitialSource(buf.get());
		modelClass.addImportedClass(fieldQualifiedName);
		return modelOperation;
	}

	protected List<ModelOperation> createTestOperations_GetRecordByField_null(ModelClass modelClass, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		List<Field> uniqueFields = ElementUtil.getUniqueFields(element);
		Iterator<Field> iterator = uniqueFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (!FieldUtil.isInverse(field) && !FieldUtil.isMappedBy(field)) {
				ModelOperation modelOperation = createTestOperations_GetRecordByField_Null(modelClass, element, field);
				modelOperations.add(modelOperation);
			}
		}
		return modelOperations;
	}
	
	protected ModelOperation createTestOperations_GetRecordByField_Null(ModelClass modelClass, Element element, Field field) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldClassName = ModelLayerHelper.getFieldClassName(field);
		String fieldQualifiedName = ModelLayerHelper.getFieldQualifiedName(field);
		Reference inverseReference = ElementUtil.getInverseReference(element);

		String testName = "runTest_"+elementNameCapped+"_GetRecordBy"+fieldNameCapped+"_null";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		String fieldNameForMethod = fieldNameCapped;
		Enumeration enumeration = context.getEnumerationByType(field.getType());
		if (!ClassUtil.isJavaPrimitiveType(fieldClassName) && !ClassUtil.isJavaDefaultType(fieldClassName) && enumeration == null) {
			fieldNameForMethod = NameUtil.assureEndsWith(fieldNameForMethod, "Id");
		}

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		buf.putLine2(helperName+".assureAdd"+elementNameCapped+"();");
		buf.putLine2("");
		//buf.putLine2("try {");
		//buf.putLine2("	// prepare context");
		//buf.putLine2("	openEntityManager();");
		buf.putLine2("");
		buf.putLine2("	// execute test");
		buf.putLine2("	"+daoNameUncapped+".get"+elementLocalPartCapped+"RecordBy"+fieldNameForMethod+"(null);");
		buf.putLine2("	fail(\"Exception should have been thrown\");");
		buf.putLine2("");
		buf.putLine2("} catch (AssertionFailure e) {");
		buf.putLine2("	Assert.exception(e, \""+fieldNameCapped+" must be specified\");");
		//buf.putLine2("");
		//buf.putLine2("} finally {");
		//buf.putLine2("	// close context");
		//buf.putLine2("	closeEntityManager();");
		buf.putLine2("}");

		modelOperation.addInitialSource(buf.get());
		modelClass.addImportedClass(fieldQualifiedName);
		return modelOperation;
	}

	protected ModelOperation createTestOperations_AddAsItem(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_AddAsItem(modelClass, element, TransactionType.NONE, TransactionOutcome.NONE);
	}
	
	protected ModelOperation createTestOperations_AddAsItem_tx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_AddAsItem(modelClass, element, TransactionType.JTA, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_AddAsItem_tx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_AddAsItem(modelClass, element, TransactionType.JTA, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_AddAsItem_utx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_AddAsItem(modelClass, element, TransactionType.USER, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_AddAsItem_utx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_AddAsItem(modelClass, element, TransactionType.USER, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_AddAsItem(ModelClass modelClass, Element element, TransactionType transactionType, TransactionOutcome transactionOutcome) throws Exception {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_AddAsItem";
		testName += getTestNameSuffix(transactionType, transactionOutcome);
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		
		//prepare context
		buf.put(getPrepareContextSource(transactionType));

		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = create"+entityClassName+"();");
		buf.putLine2("Long id = "+daoNameUncapped+".add"+elementLocalPartCapped+"Record("+elementNameUncapped+");");
		buf.putLine2("Assert.notNull(id, \"Id should exist\");");
		
		//close context");
		buf.put(getCloseContextSource(transactionType, transactionOutcome));
		
		buf.putLine2("");
		buf.putLine2("// validate results");
		switch (transactionOutcome) {
		case NONE:
		case COMMIT:
			buf.putLine2(helperName+".verify"+elementNameCapped+"Count(1);");
			buf.putLine2(helperName+".verify"+elementNameCapped+"Exists(id);");
			break;
		case ROLLBACK:
			buf.putLine2(helperName+".verify"+elementNameCapped+"Count(0);");
			break;
		default:
			throw new Exception("Unexpected transaction outcome: "+transactionOutcome);
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected List<ModelOperation> createTestOperations_AddAsItem_exceptions(ModelClass modelClass, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (!FieldUtil.isItem(field))
				continue;
			if (!FieldUtil.isRequired(field))
				continue;
			modelOperations.add(createTestOperations_AddAsItem_exception_nullViolation(modelClass, element, field));
		}
		
		fields = ElementUtil.getFields(element);
		iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isItem(field))
				continue;
			if (ElementUtil.isEnumeration(field))
				continue;
			Element fieldElement = context.getElementByType(field.getType());
			if (fieldElement == null)
				continue;
			modelOperations.add(createTestOperations_AddAsItem_exception_orphanRemoval(modelClass, element, field));
		}
		
		return modelOperations;
	}
	
	protected ModelOperation createTestOperations_AddAsItem_exception_nullViolation(ModelClass modelClass, Element element, Field field) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String fieldName = NameUtil.capName(field.getName());
		String columnName = field.getColumn();

		String testName = "runTest_"+elementNameCapped+"_AddAsItem_exception_"+columnName+"_null";
		String helperName = unitNameUncapped + "Helper";
		if (field instanceof Reference)
			columnName += "_id";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		buf.putLine2("");
		buf.putLine2("// prepare context");
		buf.putLine2("transactionControl.beginTransaction();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// execute action");
		buf.putLine2("	"+entityClassName+" "+elementNameUncapped+" = create"+entityClassName+"();");
		
		buf.putLine2("	"+elementNameUncapped+".set"+fieldName+"(null);");
		buf.putLine2("	"+daoNameUncapped+".add"+elementLocalPartCapped+"Record("+elementNameUncapped+");");
		buf.putLine2("	fail(\"Exception should have been thrown\");");
		buf.putLine2("");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	assertConstraintViolation(e, \"Column '"+columnName+"' cannot be null\");");
		buf.putLine2("}");
		buf.putLine2("");
		buf.putLine2("// close context");
		buf.putLine2("transactionControl.rollbackTransaction();");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2(helperName+".verify"+elementNameCapped+"Count(0);");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createTestOperations_AddAsItem_exception_orphanRemoval(ModelClass modelClass, Element element, Field field) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String fieldName = NameUtil.capName(field.getName());
		Element fieldElement = context.getElementByType(field.getType());
		String fieldLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(fieldElement);
		String columnName = field.getColumn();

		String testName = "runTest_"+elementNameCapped+"_AddAsItem_exception_"+columnName+"_orphanRemoval";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		buf.putLine2("Long id = "+helperName+".assureAdd"+elementNameCapped+"();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// get existing record");
		buf.putLine2("	"+entityClassName+" "+elementNameUncapped+" = "+daoNameUncapped+".get"+elementLocalPartCapped+"RecordById(id);");
		buf.putLine2("	Assert.notNull("+elementNameUncapped+", \""+elementNameCapped+" record should exist\");");
		buf.putLine2("	");
		buf.putLine2("	// save null "+fieldName);
		buf.putLine2("	"+elementNameUncapped+".set"+fieldName+"(null);");
		buf.putLine2("	"+daoNameUncapped+".save"+elementLocalPartCapped+"Record("+elementNameUncapped+");");
		buf.putLine2("	fail(\"Exception should have been thrown\");");
		buf.putLine2("	");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	Assert.exception(e, \"no longer referenced by the owning entity instance\");");
		buf.putLine2("}");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2(helperName+".verify"+elementLocalPartCapped+fieldLocalPartCapped+"Count(id, 2);");
		buf.putLine2(helperName+".verify"+elementNameCapped+"Count(1);");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
	protected ModelOperation createTestOperations_AddAsList(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_AddAsList(modelClass, element, TransactionType.NONE, TransactionOutcome.NONE);
	}
	
	protected ModelOperation createTestOperations_AddAsList_tx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_AddAsList(modelClass, element, TransactionType.JTA, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_AddAsList_tx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_AddAsList(modelClass, element, TransactionType.JTA, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_AddAsList_utx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_AddAsList(modelClass, element, TransactionType.USER, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_AddAsList_utx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_AddAsList(modelClass, element, TransactionType.USER, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_AddAsList(ModelClass modelClass, Element element, TransactionType transactionType, TransactionOutcome transactionOutcome) throws Exception {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_AddAsList";
		testName += getTestNameSuffix(transactionType, transactionOutcome);
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		
		//prepare context
		buf.put(getPrepareContextSource(transactionType));

		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2("List<"+entityClassName+"> "+elementNameUncapped+"List = "+helperName+".create"+entityClassName+"List();");
		buf.putLine2("List<Long> idList = "+daoNameUncapped+".add"+elementLocalPartCapped+"Records("+elementNameUncapped+"List);");
		buf.putLine2("Assert.notNull(idList, \"Id list should exist\");");
		buf.putLine2("Assert.equals(idList.size(), "+elementNameUncapped+"List.size(), \"Id list should have correct length\");");
		
		//close context");
		buf.put(getCloseContextSource(transactionType, transactionOutcome));
		
		buf.putLine2("");
		buf.putLine2("// validate results");
		switch (transactionOutcome) {
		case NONE:
		case COMMIT:
			buf.putLine2(helperName+".verify"+elementNameCapped+"Count(idList.size());");
			buf.putLine2(helperName+".verify"+elementNameCapped+"Exists(idList);");
			buf.putLine2("List<"+entityClassName+"> "+elementNameUncapped+"List2 = "+helperName+".get"+elementNameCapped+"Records(idList);");
			buf.putLine2(elementNameCapped+"Fixture.assertSame"+elementNameCapped+"("+elementNameUncapped+"List, "+elementNameUncapped+"List2, true);");
			break;
		case ROLLBACK:
			buf.putLine2(helperName+".verify"+elementNameCapped+"Count(0);");
			break;
		default:
			throw new Exception("Unexpected transaction outcome: "+transactionOutcome);
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
	protected ModelOperation createTestOperations_SaveAsItem(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_SaveAsItem(modelClass, element, TransactionType.NONE, TransactionOutcome.NONE);
	}
	
	protected ModelOperation createTestOperations_SaveAsItem_tx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_SaveAsItem(modelClass, element, TransactionType.JTA, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_SaveAsItem_tx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_SaveAsItem(modelClass, element, TransactionType.JTA, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_SaveAsItem_utx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_SaveAsItem(modelClass, element, TransactionType.USER, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_SaveAsItem_utx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_SaveAsItem(modelClass, element, TransactionType.USER, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_SaveAsItem(ModelClass modelClass, Element element, TransactionType transactionType, TransactionOutcome transactionOutcome) throws Exception {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_SaveAsItem";
		testName += getTestNameSuffix(transactionType, transactionOutcome);
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		
		switch (transactionOutcome) {
		case NONE:
		case COMMIT:
			buf.putLine2("Long id = "+helperName+".assureAdd"+elementNameCapped+"();");
			break;
		case ROLLBACK:
			buf.putLine2(entityClassName+" "+elementNameUncapped+"ToAdd = create"+elementNameCapped+"Entity();");
			buf.putLine2("Long id = "+helperName+".assureAdd"+elementNameCapped+"("+elementNameUncapped+"ToAdd);");
			break;
		}
		
		//prepare context
		buf.put(getPrepareContextSource(transactionType));

		buf.putLine2("");
		buf.putLine2("// read prepared record");
		buf.putLine2(entityClassName+" "+elementNameUncapped+"ToSave = "+daoNameUncapped+".get"+elementLocalPartCapped+"RecordById(id);");
		buf.putLine2("Assert.notNull("+elementNameUncapped+"ToSave, \""+elementNameCapped+" record should exist\");");
		buf.putLine2("");
		
		buf.putLine2("// modify and save record");
		List<Attribute> attributes = ElementUtil.getAttributes(element);
		Iterator<Attribute> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			String attributeName = attribute.getName();
			String attributeType = attribute.getType();
			boolean isUnique = FieldUtil.isUnique(attribute);
			String attributeNameCapped = NameUtil.capName(attributeName);
			String dummyValue = dummyValueFactory.getDummyValue(attributeName, attributeType, "111L", false, FieldUtil.isCollection(attribute), isUnique);
			buf.putLine2(elementNameUncapped+"ToSave.set"+attributeNameCapped+"("+dummyValue+");");
		}
		
//		Attribute attribute = ElementUtil.getFirstAttribute(element);
//		String attributeName = attribute.getName();
//		String attributeType = attribute.getType();
//		String attributeNameCapped = NameUtil.capName(attributeName);
//		String dummyValue = dummyValueFactory.getDummyValue(attributeName, attributeType, true, FieldUtil.isCollection(attribute));		
//		buf.putLine2(elementNameUncapped+".set"+attributeNameCapped+"("+dummyValue+");");
		buf.putLine2(daoNameUncapped+".save"+elementLocalPartCapped+"Record("+elementNameUncapped+"ToSave);");
		
		//close context");
		buf.put(getCloseContextSource(transactionType, transactionOutcome));
		
		buf.putLine2("");
		buf.putLine2("// validate results");
		switch (transactionOutcome) {
		case NONE:
		case COMMIT:
			buf.putLine2(entityClassName+" "+elementNameUncapped+" = "+helperName+".get"+elementNameCapped+"RecordById(id);");
			
			attributes = ElementUtil.getAttributes(element);
			iterator = attributes.iterator();
			while (iterator.hasNext()) {
				Attribute attribute = iterator.next();
				String attributeName = attribute.getName();
				String attributeType = attribute.getType();
				String attributeClassName = TypeUtil.getClassName(attributeType);
				String attributeNameCapped = NameUtil.capName(attributeName);
				
				String expectedValue = "111";
				if (attributeClassName.equals("Integer"))
					expectedValue = "new Integer(111)";
				else if (attributeClassName.equals("Short"))
					expectedValue = "new Short(111)";
				else if (attributeClassName.equals("Long"))
					expectedValue = "new Long(111L)";
				else if (attributeClassName.equals("Double"))
					expectedValue = "new Double(111D)";
				else if (attributeClassName.equals("Float"))
					expectedValue = "new Float(111F)";
				else if (attributeClassName.equals("Date"))
					expectedValue = "new Date(111L)";
				
				buf.putLine2("Assert.equals("+elementNameUncapped+".get"+attributeNameCapped+"(), "+expectedValue+", \""+elementNameCapped+" "+attributeNameCapped+" field not correct\");");
			}

			buf.putLine2(elementNameCapped+"Fixture.assertSame"+elementNameCapped+"("+elementNameUncapped+", "+elementNameUncapped+"ToSave);");
			break;
		case ROLLBACK:
			buf.putLine2(entityClassName+" "+elementNameUncapped+" = "+helperName+".get"+elementNameCapped+"RecordById(id);");
			buf.putLine2(elementNameCapped+"Fixture.assertSame"+elementNameCapped+"("+elementNameUncapped+", "+elementNameUncapped+"ToAdd, true);");
			break;
		default:
			throw new Exception("Unexpected transaction outcome: "+transactionOutcome);
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	

	protected ModelOperation createTestOperations_RemoveAsItem(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAsItem(modelClass, element, TransactionType.NONE, TransactionOutcome.NONE);
	}
	
	protected ModelOperation createTestOperations_RemoveAsItem_tx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAsItem(modelClass, element, TransactionType.JTA, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_RemoveAsItem_tx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAsItem(modelClass, element, TransactionType.JTA, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_RemoveAsItem_utx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAsItem(modelClass, element, TransactionType.USER, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_RemoveAsItem_utx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAsItem(modelClass, element, TransactionType.USER, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_RemoveAsItem(ModelClass modelClass, Element element, TransactionType transactionType, TransactionOutcome transactionOutcome) throws Exception {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_RemoveAsItem";
		testName += getTestNameSuffix(transactionType, transactionOutcome);
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		buf.putLine2("Long id = "+helperName+".assureAdd"+elementNameCapped+"();");
		buf.putLine2("Assert.notNull(id, \"Id should exist\");");
		
		//prepare context
		buf.put(getPrepareContextSource(transactionType));

		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2(entityClassName+" "+elementNameUncapped+" = "+daoNameUncapped+".get"+elementLocalPartCapped+"RecordById(id);");
		buf.putLine2("Assert.notNull("+elementNameUncapped+", \""+elementNameCapped+" record should exist\");");
		buf.putLine2(daoNameUncapped+".remove"+elementLocalPartCapped+"Record("+elementNameUncapped+");");
		
		//close context");
		buf.put(getCloseContextSource(transactionType, transactionOutcome));
		
		buf.putLine2("");
		buf.putLine2("// validate results");
		switch (transactionOutcome) {
		case NONE:
		case COMMIT:
			buf.putLine2(helperName+".verify"+elementNameCapped+"Count(0);");
			break;
		case ROLLBACK:
			buf.putLine2(helperName+".verify"+elementNameCapped+"Count(1);");
			break;
		default:
			throw new Exception("Unexpected transaction outcome: "+transactionOutcome);
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}


	protected ModelOperation createTestOperations_RemoveAsList(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAsList(modelClass, element, TransactionType.NONE, TransactionOutcome.NONE);
	}
	
	protected ModelOperation createTestOperations_RemoveAsList_tx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAsList(modelClass, element, TransactionType.JTA, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_RemoveAsList_tx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAsList(modelClass, element, TransactionType.JTA, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_RemoveAsList_utx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAsList(modelClass, element, TransactionType.USER, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_RemoveAsList_utx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAsList(modelClass, element, TransactionType.USER, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_RemoveAsList(ModelClass modelClass, Element element, TransactionType transactionType, TransactionOutcome transactionOutcome) throws Exception {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_RemoveAsList";
		testName += getTestNameSuffix(transactionType, transactionOutcome);
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		buf.putLine2("List<"+entityClassName+"> "+elementNameUncapped+"List = "+helperName+".create"+entityClassName+"List();");
		buf.putLine2("List<Long> idList = "+helperName+".add"+elementNameCapped+"Records("+elementNameUncapped+"List);");
		buf.putLine2("Assert.notNull(idList, \"Id list should exist\");");
		
		//prepare context
		buf.put(getPrepareContextSource(transactionType));

		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2(daoNameUncapped+".remove"+elementLocalPartCapped+"Records("+elementNameUncapped+"List);");
		
		//close context");
		buf.put(getCloseContextSource(transactionType, transactionOutcome));
		
		buf.putLine2("");
		buf.putLine2("// validate results");
		switch (transactionOutcome) {
		case NONE:
		case COMMIT:
			buf.putLine2(helperName+".verify"+elementNameCapped+"Count(0);");
			break;
		case ROLLBACK:
			buf.putLine2(helperName+".verify"+elementNameCapped+"Exists(idList);");
			buf.putLine2(helperName+".verify"+elementNameCapped+"Count(idList.size());");
			buf.putLine2("List<"+entityClassName+"> "+elementNameUncapped+"List2 = "+helperName+".get"+elementNameCapped+"Records(idList);");
			buf.putLine2(elementNameCapped+"Fixture.assertSame"+elementNameCapped+"("+elementNameUncapped+"List, "+elementNameUncapped+"List2, true);");
			break;
		default:
			throw new Exception("Unexpected transaction outcome: "+transactionOutcome);
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	
	protected ModelOperation createTestOperations_RemoveAll(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAll(modelClass, element, TransactionType.NONE, TransactionOutcome.NONE);
	}
	
	protected ModelOperation createTestOperations_RemoveAll_tx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAll(modelClass, element, TransactionType.JTA, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_RemoveAll_tx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAll(modelClass, element, TransactionType.JTA, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_RemoveAll_utx_commit(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAll(modelClass, element, TransactionType.USER, TransactionOutcome.COMMIT);
	}
	
	protected ModelOperation createTestOperations_RemoveAll_utx_rollback(ModelClass modelClass, Element element) throws Exception {
		return createTestOperations_RemoveAll(modelClass, element, TransactionType.USER, TransactionOutcome.ROLLBACK);
	}
	
	protected ModelOperation createTestOperations_RemoveAll(ModelClass modelClass, Element element, TransactionType transactionType, TransactionOutcome transactionOutcome) throws Exception {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_RemoveAll";
		testName += getTestNameSuffix(transactionType, transactionOutcome);
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment");
		buf.putLine2(helperName+".assureRemoveAll();");
		buf.putLine2(helperName+".assureAdd"+elementNameCapped+"();");
		if (inverseReference != null)
			buf.putLine2(helperName+".assureAddContainer();");
		
		//prepare context
		buf.put(getPrepareContextSource(transactionType));

		buf.putLine2("");
		buf.putLine2("// execute action");
		buf.putLine2(daoNameUncapped+".removeAll"+elementLocalPartCapped+"Records();");
		
		//close context");
		buf.put(getCloseContextSource(transactionType, transactionOutcome));
		
		buf.putLine2("");
		buf.putLine2("// validate results");
		switch (transactionOutcome) {
		case NONE:
		case COMMIT:
			buf.putLine2(helperName+".verify"+elementNameCapped+"Count(0);");
			break;
		case ROLLBACK:
			buf.putLine2(helperName+".verify"+elementNameCapped+"Count(1);");
			break;
		default:
			throw new Exception("Unexpected transaction outcome: "+transactionOutcome);
		}
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
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



}
