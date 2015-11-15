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


public class EntityITBuilder extends AbstractDataLayerITBuilder {

	public EntityITBuilder(GenerationContext context) {
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
		String entityNameCapped = DataLayerHelper.getEntityNameCapped(element);
		String entityBeanName = DataLayerHelper.getEntityNameUncapped(element);
		String packageName = DataLayerHelper.getEntityPackageName(namespace);
		//String interfaceName = DataLayerHelper.getDAOInterfaceName(element);
		//String daoQualifiedName = DataLayerHelper.getDAOQualifiedName(element);
		String entityType = org.aries.util.TypeUtil.getDerivedType(element.getType(), entityBeanName);
		//String daoType = TypeUtil.getTypeFromNamespace(namespace);

		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(entityNameCapped + "IT");
		modelClass.setName(entityBeanName);
		modelClass.setType(entityType);
		
		this.namespace = namespace;
		this.modelUnit = modelClass;
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
		String entityQualifiedName = DataLayerHelper.getInferredEntityQualifiedName(namespace, element);
		String entityFixtureQualifiedName = DataLayerHelper.getEntityFixtureQualifiedName(namespace);
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
		Reference inverseReference = ElementUtil.getInverseReference(element);
		if (inverseReference != null)
			modelClass.addInstanceReference(createInstanceField_ContainerBean(namespace, element));
		modelClass.addInstanceReference(createInstanceField_EntityBean(namespace, element));
		modelClass.addInstanceReference(createInstanceField_EntityManager());
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
		modelClass.addInstanceOperation(createOperation_CreateEntityManager());
		modelClass.addInstanceOperation(createOperation_CreateEntity(modelClass, element));
		modelClass.addInstanceOperation(createOperation_CreateEmptyEntity(modelClass, element));
//		if (inverseReference != null)
//			modelClass.addInstanceOperation(createOperation_CreateContainer(modelClass, element));
		//modelClass.addInstanceOperation(createOperation_OpenEntityManager(modelClass, element));
		//modelClass.addInstanceOperation(createOperation_CloseEntityManager(modelClass, element));
		
//		modelClass.addInstanceOperation(createTestOperations_GetAllRecords(modelClass, element));
//		modelClass.addInstanceOperation(createTestOperations_GetRecordById(modelClass, element));
//		modelClass.addInstanceOperation(createTestOperations_GetRecordById_null(modelClass, element));
//		modelClass.addInstanceOperations(createTestOperations_GetRecordByField(modelClass, element));
//		modelClass.addInstanceOperations(createTestOperations_GetRecordByField_null(modelClass, element));
		
		modelClass.addInstanceOperation(createTestOperations_AddAsItem(modelClass, element));
		modelClass.addInstanceOperations(createTestOperations_AddAsItem_exceptions(modelClass, element));

//		modelClass.addInstanceOperation(createTestOperations_AddAsList(modelClass, element));
//		modelClass.addInstanceOperation(createTestOperations_AddAsList_tx_commit(modelClass, element));
//		modelClass.addInstanceOperation(createTestOperations_AddAsList_tx_rollback(modelClass, element));
//		modelClass.addInstanceOperation(createTestOperations_AddAsList_utx_commit(modelClass, element));
//		modelClass.addInstanceOperation(createTestOperations_AddAsList_utx_rollback(modelClass, element));
//		//modelClass.addInstanceOperations(createTestOperations_AddAsList_exceptions(modelClass, element));
		
		modelClass.addInstanceOperation(createTestOperations_SaveAsItem(modelClass, element));
		modelClass.addInstanceOperations(createTestOperations_SaveAsItem_exceptions(modelClass, element));

		modelClass.addInstanceOperation(createTestOperations_RemoveAsItem(modelClass, element));
		//modelClass.addInstanceOperations(createTestOperations_RemoveAsItem_exceptions(modelClass, element));
		
		modelClass.addInstanceOperation(createTestOperations_RemoveAsList(modelClass, element));
//		modelClass.addInstanceOperation(createTestOperations_RemoveAsList_tx_commit(modelClass, element));
//		modelClass.addInstanceOperation(createTestOperations_RemoveAsList_tx_rollback(modelClass, element));
//		modelClass.addInstanceOperation(createTestOperations_RemoveAsList_utx_commit(modelClass, element));
//		modelClass.addInstanceOperation(createTestOperations_RemoveAsList_utx_rollback(modelClass, element));
//		//modelClass.addInstanceOperations(createTestOperations_RemoveAsList_exceptions(modelClass, element));

//		if (inverseReference != null) {
//			modelClass.addInstanceOperation(createOperation_AssureAddContainer1(modelClass, element));
//			modelClass.addInstanceOperation(createOperation_AssureAddContainer2(modelClass, element));
//		}
//		modelClass.addInstanceOperation(createOperation_AssureAddRecord1(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_AssureAddRecord2(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_AssureDeleteRecord1(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_AssureDeleteRecord2(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_VerifyRecordCount1(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_VerifyRecordCount2(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_VerifyRecordExists1(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_VerifyRecordExists2(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_VerifyRecordNotExists1(modelClass, element));
//		modelClass.addInstanceOperation(createOperation_VerifyRecordNotExists2(modelClass, element));
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
		buf.putLine2("createEntityManager();");
		buf.putLine2("removeAll();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_TearDown(ModelClass modelClass, Element element) {
		//String unitNameCapped = DataLayerHelper.getPersistenceUnitNameCapped(unit);
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String entityBeanName = DataLayerHelper.getEntityNameUncapped(element);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterAnnotation());
		modelOperation.setName("tearDown");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2("entityManager.close();");
		buf.putLine2("transactionControl.tearDown();");
		buf.putLine2(unitNameUncapped+"Control.tearDown();");
		buf.putLine2(unitNameUncapped+"Helper = null;");
		buf.putLine2(entityBeanName+" = null;");
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
		//String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		//Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_AddAsItem";
		//String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// execute");
		buf.putLine2("	removeAll();");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	"+entityBeanName+" = entityManager.merge("+entityBeanName+");");
		buf.putLine2("	entityManager.flush();");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("	");
		buf.putLine2("	// validate results");
		buf.putLine2("	Long id = "+entityBeanName+".getId();");
		buf.putLine2("	"+entityClassName+" entity = entityManager.find("+entityClassName+".class, id);");
		buf.putLine2("	Assert.equals(entity, "+entityBeanName+", \""+elementNameCapped+" records should be equal\");");
		buf.putLine2("	");			
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	Assert.exception(e, \"Exception should not occur\");");
		buf.putLine2("	");		
		buf.putLine2("} finally {");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("}");
		
		
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
			modelOperations.add(createTestOperations_AddAsItem_constraint_nullViolation(modelClass, element, field));
		}
		
//		fields = ElementUtil.getFields(element);
//		iterator = fields.iterator();
//		while (iterator.hasNext()) {
//			Field field = iterator.next();
//			if (FieldUtil.isItem(field))
//				continue;
//			modelOperations.add(createTestOperations_AddAsItem_exception_orphanRemoval(modelClass, element, field));
//		}
		
		modelOperations.add(createTestOperations_AddAsItem_exception_entityNotManaged(modelClass, element));
		modelOperations.add(createTestOperations_AddAsItem_exception_detachedEntity(modelClass, element));
		return modelOperations;
	}
	
	protected ModelOperation createTestOperations_AddAsItem_constraint_nullViolation(ModelClass modelClass, Element element, Field field) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String fieldName = NameUtil.capName(field.getName());
		String columnName = field.getColumn();
		if (field instanceof Reference)
			columnName += "_id";

		String testName = "runTest_"+elementNameCapped+"_AddAsItem_constraint_"+fieldNameCapped+"_null";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2(entityBeanName+".set"+fieldNameCapped+"(null);");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	entityManager.merge("+entityBeanName+");");
		buf.putLine2("	fail(\"Exception should have occured\");");
		buf.putLine2("	");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	Assert.exception(e, \"Column '"+columnName+"' cannot be null\");");
		buf.putLine2("	");		
		buf.putLine2("} finally {");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("}");
		
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
	
	protected ModelOperation createTestOperations_AddAsItem_exception_entityNotManaged(ModelClass modelClass, Element element) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);

		String testName = "runTest_"+elementNameCapped+"_AddAsItem_exception_entityNotManaged";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	entityManager.refresh("+entityBeanName+");");
		buf.putLine2("	fail(\"Exception should have occured\");");
		buf.putLine2("	");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	Assert.exception(e, \"Entity not managed\");");
		buf.putLine2("	");		
		buf.putLine2("} finally {");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createTestOperations_AddAsItem_exception_detachedEntity(ModelClass modelClass, Element element) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);

		String testName = "runTest_"+elementNameCapped+"_AddAsItem_exception_detachedEntity";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	entityManager.persist("+entityBeanName+");");
		buf.putLine2("	fail(\"Exception should have occured\");");
		buf.putLine2("	");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	Assert.exception(e, \"detached entity passed to persist\");");
		buf.putLine2("	");		
		buf.putLine2("} finally {");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	

	protected ModelOperation createTestOperations_SaveAsItem(ModelClass modelClass, Element element) throws Exception {
		//String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		//Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_SaveAsItem";
		//String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment ");
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2("Long id = add"+elementNameCapped+"Record("+entityBeanName+");");
		buf.putLine2(entityClassName+" entity = get"+elementNameCapped+"Record(id);");
		buf.putLine2("Assert.equals("+entityBeanName+", entity, \""+elementNameCapped+" records should be equal\");");
		buf.putLine2("");

		buf.putLine2("try {");
		buf.putLine2("	// modify record");
		buf.put(getSource_SetAttributes(element, 111));
		buf.putLine2("	");
		buf.putLine2("	// save record");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	"+entityBeanName+" = entityManager.merge(entity);");
		buf.putLine2("	entityManager.flush();");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("}");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2(entityClassName+" entity2 = get"+elementNameCapped+"Record(id);");
		buf.putLine2("Assert.equals(entity, entity2, \""+elementNameCapped+" records should be equal\");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected List<ModelOperation> createTestOperations_SaveAsItem_exceptions(ModelClass modelClass, Element element) {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		
		List<Field> fields = ElementUtil.getFields(element);
		Iterator<Field> iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (!FieldUtil.isItem(field))
				continue;
			if (!FieldUtil.isRequired(field))
				continue;
			modelOperations.add(createTestOperations_SaveAsItem_constraint_notNull(modelClass, element, field));
		}
		
		fields = ElementUtil.getFields(element);
		iterator = fields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			if (FieldUtil.isItem(field))
				continue;
			Element fieldElement = context.getElementByType(field.getType());
			if (fieldElement == null)
				continue;
			modelOperations.add(createTestOperations_SaveAsItem_constraint_notNull_orphanRemoval(modelClass, element, field));
		}
		
		modelOperations.add(createTestOperations_SaveAsItem_exception_entityNotManaged(modelClass, element));
		modelOperations.add(createTestOperations_SaveAsItem_exception_detachedEntity(modelClass, element));
		return modelOperations;
	}
	
	protected ModelOperation createTestOperations_SaveAsItem_constraint_notNull(ModelClass modelClass, Element element, Field field) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		String fieldNameCapped = ModelLayerHelper.getFieldNameCapped(field);
		String fieldNameUncapped = ModelLayerHelper.getFieldNameUncapped(field);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String columnName = field.getColumn();
		if (field instanceof Reference)
			columnName += "_id";

		String testName = "runTest_"+elementNameCapped+"_SaveAsItem_constraint_"+fieldNameCapped+"_null";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment ");
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2("Long id = add"+elementNameCapped+"Record("+entityBeanName+");");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// lookup record");
		buf.putLine2("	"+entityClassName+" entity = get"+elementNameCapped+"Record(id);");
		buf.putLine2("	Assert.equals("+entityBeanName+", entity, \""+elementNameCapped+" records should be equal\");");
		buf.putLine2("	");
		buf.putLine2("	// modify record");
		buf.putLine2("	entity.set"+fieldNameCapped+"(null);");
		buf.putLine2("	");
		buf.putLine2("	// save record");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	entityManager.merge(entity);");
		buf.putLine2("	entityManager.flush();");
		buf.putLine2("	fail(\"Exception should have occured\");");
		buf.putLine2("	");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	Assert.exception(e, \"Column '"+columnName+"' cannot be null\");");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	rollbackTransaction();");
		buf.putLine2("}");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2(entityClassName+" entity = get"+elementNameCapped+"Record(id);");
		buf.putLine2("Assert.notNull(entity.get"+fieldNameCapped+"(), \""+elementNameCapped+" "+fieldNameCapped+" should exist\");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createTestOperations_SaveAsItem_constraint_notNull_orphanRemoval(ModelClass modelClass, Element element, Field field) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String fieldName = NameUtil.capName(field.getName());
		Element fieldElement = context.getElementByType(field.getType());
		String fieldLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(fieldElement);
		String columnName = field.getColumn();

		String testName = "runTest_"+elementNameCapped+"_SaveAsItem_constraint_"+columnName+"_notNull_orphanRemoval";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment ");
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2("Long id = add"+elementNameCapped+"Record("+entityBeanName+");");
		buf.putLine2(entityClassName+" entity = get"+elementNameCapped+"Record(id);");
		buf.putLine2("Assert.equals("+entityBeanName+", entity, \""+elementNameCapped+" records should be equal\");");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// modify and save record");
		buf.put(getSource_SetAttributes(element, 111));
		buf.putLine2("	openTransaction();");
		buf.putLine2("	"+entityBeanName+" = entityManager.merge(entity);");
		buf.putLine2("	entityManager.flush();");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("}");
		buf.putLine2("");
		buf.putLine2("// validate results");
		buf.putLine2(entityClassName+" entity2 = get"+elementNameCapped+"Record(id);");
		buf.putLine2("Assert.equals(entity, entity2, \""+elementNameCapped+" records should be equal\");");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createTestOperations_SaveAsItem_exception_entityNotManaged(ModelClass modelClass, Element element) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);

		String testName = "runTest_"+elementNameCapped+"_SaveAsItem_exception_entityNotManaged";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment ");
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2("add"+elementNameCapped+"Record("+entityBeanName+");");
		buf.putLine2("entityManager.clear();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// execute action");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	entityManager.refresh("+entityBeanName+");");
		buf.putLine2("	fail(\"Exception should have occured\");");
		buf.putLine2("	");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	Assert.exception(e, \"Entity not managed\");");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	rollbackTransaction();");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createTestOperations_SaveAsItem_exception_detachedEntity(ModelClass modelClass, Element element) {
		String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String daoNameUncapped = DataLayerHelper.getDAONameUncapped(element);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		Reference inverseReference = ElementUtil.getInverseReference(element);

		String testName = "runTest_"+elementNameCapped+"_SaveAsItem_exception_detachedEntity";
		String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment ");
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2("Long id = add"+elementNameCapped+"Record("+entityBeanName+");");
		buf.putLine2("entityManager.detach("+entityBeanName+");");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// execute action");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	entityManager.persist("+entityBeanName+");");
		buf.putLine2("	fail(\"Exception should have occured\");");
		buf.putLine2("	");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	Assert.exception(e, \"detached entity passed to persist\");");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	rollbackTransaction();");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createTestOperations_RemoveAsItem(ModelClass modelClass, Element element) throws Exception {
		//String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		//Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_RemoveAsItem";
		//String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment ");
		buf.putLine2("openTransaction();");
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2(entityBeanName+" = entityManager.merge("+entityBeanName+");");
		buf.putLine2("entityManager.persist("+entityBeanName+");");
		buf.putLine2("commitTransaction();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// lookup record");
		buf.putLine2("	Long id = bookOrdersEntity.getId();");
		buf.putLine2("	BookOrdersEntity entity = entityManager.find(BookOrdersEntity.class, id);");
		buf.putLine2("	Assert.equals(bookOrdersEntity, entity, \"BookOrders records should be equal\");");
		buf.putLine2("	");
		buf.putLine2("	// remove record");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	entityManager.remove(bookOrdersEntity);");
		buf.putLine2("	entityManager.flush();");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("	");
		buf.putLine2("	// verify results");
		buf.putLine2("	entity = entityManager.find(BookOrdersEntity.class, id);");
		buf.putLine2("	Assert.isNull(entity, \"BookOrders record should be removed\");");
		buf.putLine2("	Assert.isTrue(getAllBookOrdersRecords().size() == 0, \"No BookOrders record should exist\");");
		buf.putLine2("	Assert.isTrue(getAllBookOrdersBookRecords().size() == 0, \"No BookOrdersBook record should exist\");");
		buf.putLine2("	Assert.isTrue(getAllPersonNameRecords().size() == 0, \"No PersonName record should exist\");");
		buf.putLine2("	");
		buf.putLine2("} catch (Exception e) {");
		//buf.putLine2("	e.printStackTrace();");
		buf.putLine2("	fail(\"Exception should not occur: \"+e.getMessage());");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	
	protected ModelOperation createTestOperations_RemoveAsList(ModelClass modelClass, Element element) throws Exception {
		//String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		//Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "runTest_"+elementNameCapped+"_RemoveAsList";
		//String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("// prepare environment ");
		buf.putLine2("openTransaction();");
		buf.putLine2(entityBeanName+" = create"+entityClassName+"();");
		buf.putLine2(entityBeanName+" = entityManager.merge("+entityBeanName+");");
		buf.putLine2("entityManager.persist("+entityBeanName+");");
		buf.putLine2("commitTransaction();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	// lookup record");
		buf.putLine2("	Long id = bookOrdersEntity.getId();");
		buf.putLine2("	BookOrdersEntity entity = entityManager.find(BookOrdersEntity.class, id);");
		buf.putLine2("	Assert.equals(bookOrdersEntity, entity, \"BookOrders records should be equal\");");
		buf.putLine2("	");
		buf.putLine2("	// remove record");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	entityManager.remove(bookOrdersEntity);");
		buf.putLine2("	entityManager.flush();");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("	");
		buf.putLine2("	// verify results");
		buf.putLine2("	entity = entityManager.find(BookOrdersEntity.class, id);");
		buf.putLine2("	Assert.isNull(entity, \"BookOrders record should be removed\");");
		buf.putLine2("	Assert.isTrue(getAllBookOrdersRecords().size() == 0, \"No BookOrders record should exist\");");
		buf.putLine2("	Assert.isTrue(getAllBookOrdersBookRecords().size() == 0, \"No BookOrdersBook record should exist\");");
		buf.putLine2("	Assert.isTrue(getAllPersonNameRecords().size() == 0, \"No PersonName record should exist\");");
		buf.putLine2("	");
		buf.putLine2("} catch (Exception e) {");
		//buf.putLine2("	e.printStackTrace();");
		buf.putLine2("	fail(\"Exception should not occur: \"+e.getMessage());");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	
	
	protected ModelOperation createSupportOperations_RemoveAll(ModelClass modelClass, Element element) throws Exception {
		//String unitNameUncapped = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(element);
		//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(element);
		//String elementLocalPartCapped = ModelLayerHelper.getElementTypeLocalPartCapped(element);
		String entityClassName = DataLayerHelper.getInferredEntityClassName(namespace, element);
		//String entityBeanName = DataLayerHelper.getInferredEntityBeanName(namespace, element);
		//Reference inverseReference = ElementUtil.getInverseReference(element);
		
		String testName = "removeAll"+elementNameCapped;
		//String helperName = unitNameUncapped + "Helper";

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		modelOperation.setName(testName);

		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	openTransaction();");
		buf.putLine2("	");
		buf.putLine2("	Query query = entityManager.createNamedQuery(\"getAll"+elementNameCapped+"Records\");");
		buf.putLine2("	@SuppressWarnings(\"unchecked\") List<"+entityClassName+"> records = query.getResultList();");
		buf.putLine2("	");
		buf.putLine2("	Iterator<"+entityClassName+"> iterator = records.iterator();");
		buf.putLine2("	while (iterator.hasNext()) {");
		buf.putLine2("		"+entityClassName+" entity = iterator.next();");
		buf.putLine2("		entityManager.remove(entity);");
		buf.putLine2("	}");
		buf.putLine2("	entityManager.flush();");
		buf.putLine2("	");
		buf.putLine2("} finally {");
		buf.putLine2("	commitTransaction();");
		buf.putLine2("}");
		
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected String getSource_SetAttributes(Element element, int seed) {
		Buf buf = new Buf();
		List<Attribute> attributes = ElementUtil.getAttributes(element);
		Iterator<Attribute> iterator = attributes.iterator();
		while (iterator.hasNext()) {
			Attribute attribute = iterator.next();
			String attributeName = attribute.getName();
			String attributeType = attribute.getType();
			boolean isUnique = FieldUtil.isUnique(attribute);
			String attributeNameCapped = NameUtil.capName(attributeName);
			String dummyValue = dummyValueFactory.getDummyValue(attributeName, attributeType, ""+seed, false, FieldUtil.isCollection(attribute), isUnique);
			buf.putLine2("	entity.set"+attributeNameCapped+"("+dummyValue+");");

			String attributeQualifiedName = TypeUtil.getQualifiedName(attributeType);
			modelUnit.addImportedClass(attributeQualifiedName);
		}
		return buf.get();
	}
	
}
