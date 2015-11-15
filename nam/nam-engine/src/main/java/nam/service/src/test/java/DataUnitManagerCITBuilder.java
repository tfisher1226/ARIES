package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Element;
import nam.model.ModelLayerHelper;
import nam.model.Process;
import nam.model.Type;
import nam.model.Unit;
import nam.model.util.ModuleUtil;
import nam.model.util.TypeUtil;
import nam.model.util.UnitUtil;
import nam.service.src.main.java.CacheUnitManagerBuilder;

import org.apache.commons.lang.StringUtils;
import org.aries.util.NameUtil;

import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.TestType;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelConstructor;
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


/**
 * Builds a DataUnit State Manager CIT {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * <h3>Properties</h3>
 * The following properties can be used to configure execution of this builder: 
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * <h3>Dependencies</h3>
 * Execution of this builder must come after the following builders: 
 * <ul>
 * <li>ProcessClassBuilder</li>
 * </ul>
 * 
 * @author tfisher
 */
public class DataUnitManagerCITBuilder extends AbstractDataUnitManagerCITBuilder {

	public DataUnitManagerCITBuilder(GenerationContext context) {
		super(context);
	}
	
	public String getBasePackageName(String namespace) {
		return DataLayerHelper.getPersistenceUnitPackageName(namespace, context.getUnit());
	}

	public String getBaseClassName() {
		return DataLayerHelper.getPersistenceUnitClassName(context.getUnit());
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
		String className = DataLayerHelper.getPersistenceUnitClassName(unit) + "ManagerCIT";
		String beanName = DataLayerHelper.getPersistenceUnitNameUncapped(unit) + "ManagerCIT";
		String unitName = DataLayerHelper.getPersistenceUnitNameUncapped(unit);
		String unitType = TypeUtil.getTypeFromNamespaceAndLocalPart(unit.getNamespace(), unitName);

		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setType(unitType);
		modelClass.setParentClassName("AbstractDataUnitArquillionTest");
		modelClass.addImportedClass("org.aries.tx.AbstractDataUnitArquillionTest");

		testIterationCount = 0;
		this.modelUnit = modelClass;
		initializeClass(modelClass, unit);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Unit unit) throws Exception {
		provider = new DataUnitManagerCITProvider(context);
		provider.baseName = getBaseClassName();
		initializeImportedClasses(modelClass);
		initializeClassAnnotations(modelClass);
		initializeClassConstructors(modelClass, unit);
		initializeInstanceFields(modelClass, unit);
		initializeInstanceMethods(modelClass);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		super.initializeImportedClasses(modelClass);
		//modelClass.addImportedClass("org.aries.tx.DataModuleTestControl");
		String helperQualifiedName = DataLayerHelper.getHelperQualifiedName(context.getUnit());
		modelClass.addImportedClass(helperQualifiedName);
	}

	protected void initializeClassConstructors(ModelClass modelClass, Unit unit) throws Exception {
		modelClass.addInstanceConstructor(createConstructor(modelClass, unit));
	}
	
	protected ModelConstructor createConstructor(ModelClass modelClass, Unit unit) throws Exception {
		ModelConstructor constructor = new ModelConstructor();
		constructor.setModifiers(Modifier.PUBLIC);
		//String mbeanName = NameUtil.splitStringUsingUnderscores(className) + "_MBEAN_NAME";
		String sourceCode = CodeUtil.createMethodSource(new String[] {
				//"MBeanUtil.registerMBean(this, "+mbeanName.toUpperCase()+");"
				//"setLatestStateFilename("+serviceName+"State.LATEST_STATE_FILENAME);",
				//"setShadowStateFilename("+serviceName+"State.SHADOW_STATE_FILENAME);"
		});
		constructor.addInitialSource(sourceCode);
		return constructor;
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Unit unit) throws Exception {
		String namespace = context.getProcess().getNamespace();
		addReference_Manager(modelClass, namespace);
		addReference_Helper(modelClass, namespace);
	}
	
	protected void initializeInstanceMethods(ModelClass modelClass) throws Exception {
		createMethod_JUnitBeforeClass(modelClass);
		createMethod_JUnitAfterClass(modelClass);
		createMethod_GetTestClass(modelClass);
		createMethod_GetServerName(modelClass);
		createMethod_GetDomainId(modelClass);
		createMethod_GetTargetArchive(modelClass);
		createMethod_GetStateManager(modelClass);
		createMethod_JUnitSetup(modelClass);
		createMethod_CreateTransactionTestControl(modelClass);
		createMethod_InitializeDataUnitHelper(modelClass);
		//createMethod_CreateDataUnitControl(modelClass);
		createMethod_ClearDataUnitContext(modelClass);
		createMethod_JUnitTeardown(modelClass);
		createMethod_CreateTestEAR(modelClass);
		createMethod_CreateTestWAR(modelClass);
		createMethods_DataAccess(modelClass);
		createMethod_RunActionAsRunnable(modelClass);
		//createMethod_RunActionAsCallable(modelClass);
	}
	
	protected void createMethod_InitializeDataUnitHelper(ModelClass modelClass) {
		String className = TypeUtil.getClassName(modelClass.getType());
		String helperBeanName = NameUtil.uncapName(getHelperClassName());
		String controlClassName = getBaseClassName() + "Control";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initialize"+className+"Helper");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2(helperBeanName+".setJmxManager(jmxManager);");
		buf.putLine2(helperBeanName+".initialize(transactionTestControl);");
		//buf.putLine2(helperBeanName+".initialize(create"+controlClassName+"());");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CreateDataUnitControl(ModelClass modelClass) {
		String controlClassName = getBaseClassName() + "Control";
		String controlBeanName = NameUtil.uncapName(controlClassName);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create_"+controlClassName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("DataModuleTestControl");
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("DataModuleTestControl "+controlBeanName+" = new DataModuleTestControl(transactionTestControl);");
		//buf.putLine2(""+controlBeanName+" = new DataModuleTestControl(transactionTestControl);");
		buf.putLine2(controlBeanName+".setupDataLayer();");
		buf.putLine2("return "+controlBeanName+";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_ClearDataUnitContext(ModelClass modelClass) {
		String className = TypeUtil.getClassName(modelClass.getType());
		String helperBeanName = NameUtil.uncapName(getHelperClassName());
		String managerName = getBaseClassName() + "Manager";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("clear"+className+"Context");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2(helperBeanName+".clearContext("+managerName+".MBEAN_NAME);");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethods_DataAccess(ModelUnit modelUnit) throws Exception {
		createMethods_DataAccess(modelUnit, context.getUnit(), SourceType.SharedCache);
	}
	
	protected void createMethods_DataAccess(ModelUnit modelUnit, Unit unit, SourceType sourceType) throws Exception {
		if (unit != null) {
			if (!StringUtils.isEmpty(unit.getRef()))
				unit = ModuleUtil.getUnit(context.getModule(), unit.getRef());
			List<Element> elements = UnitUtil.getElements(unit);
			if (elements == null || elements.size() == 0) {
				log.warn("No items found in unit: "+unit.getName());
				//List<Item> items = CacheUtil.getItems(cache);
				//if (items == null || items.size() == 0) {
				//	log.warn("No items found in cache: "+cache.getName());
			} else {
				createMethods_DataAccess(modelUnit, elements, sourceType);
			}
		}
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

	protected <T extends Type> void createMethods_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType) throws Exception {
		String structure = dataItem.getStructure();
		if (structure.equals("item") && false) {
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
			if (this.getClass().equals(CacheUnitManagerBuilder.class))
				modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));

		} else if (structure.equals("list") || structure.equals("set")) {
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
			if (this.getClass().equals(CacheUnitManagerBuilder.class))
				modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));

		} else if (structure.equals("map")) {
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsMap));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));

//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Success));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.TxCommit));
//			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.TxRollback));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.ManagerNull));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.RepositoryNull));
//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.RunTest));
		}
	}

	protected <T extends Type> Collection<ModelOperation> createOperations_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Success));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Commit + TestType.JTATransaction));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Rollback + TestType.JTATransaction));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Commit + TestType.UserTransaction));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Rollback + TestType.UserTransaction));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Manager + TestType.Exception + TestType.Rollback));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Interface + TestType.Exception + TestType.Rollback));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Repository + TestType.Exception + TestType.Rollback));
		modelOperations.add(createOperation_RunTest(modelUnit, dataItem, sourceType, methodType));
		return modelOperations;
	}

	protected <T extends Type> ModelOperation createOperation_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType, int testTypeValues) throws Exception {
		TestType testType = new TestType(testTypeValues);
		testIterationCount++;
		
		String type = dataItem.getType();
		String name = NameUtil.capName(dataItem.getName());
		String nameUncapped = NameUtil.uncapName(dataItem.getName());
		String packageName = util.getPackageNameFromType(type);
		String className = util.getClassNameFromType(type);
		String structure = dataItem.getStructure();

		String operationName = getOperationName(sourceType, methodType, dataItem, testType);
		modelUnit.addImportedClass(packageName+"."+className);

		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(util.getModifiers(sourceType));
		modelOperation.setName(operationName);
		modelOperation.addException("Exception");

		//@Transactional(TransactionMode.ROLLBACK)
		modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
		modelOperation.addAnnotation(AnnotationUtil.createJUnitIgnoreAnnotationCommented());
		modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		
		if (testType.isException()) {
			Unit unit = context.getUnit();
			if (testType.isInterface()) {
				String targetClassName = unit.getName();
				String targetMethodName = getTargetMethodName(sourceType, methodType, testType, dataItem);
				modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(
						targetClassName, 
						targetMethodName, "AT EXIT", 
						"throw new org.aries.ApplicationFailure(\\\"error"+testIterationCount+"\\\")", 
						testIterationCount));
				
			} else if (testType.isCache()) {
				String targetClassName = unit.getName() + "Impl";
				String targetMethodName = getTargetMethodName(sourceType, methodType, testType, dataItem);
				modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(
						targetClassName, 
						targetMethodName, "AT EXIT", 
						"throw new org.aries.ApplicationFailure(\\\"error"+testIterationCount+"\\\")", 
						testIterationCount));
				
			} else if (testType.isManager()) {
				String targetClassName = unit.getName() + "Manager";
				String targetMethodName = getTargetMethodName(sourceType, methodType, testType, dataItem);
				modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(
						targetClassName, 
						targetMethodName, "AT EXIT", 
						"throw new org.aries.ApplicationFailure(\\\"error"+testIterationCount+"\\\")", 
						testIterationCount));
				
			} else if (testType.isRepository()) {
				String targetClassName = unit.getName() + "RepositoryImpl";
				String targetMethodName = getTargetMethodName(sourceType, methodType, testType, dataItem);
				modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(
						targetClassName, 
						targetMethodName, "AT EXIT", 
						"throw new org.aries.ApplicationFailure(\\\"error"+testIterationCount+"\\\")", 
						testIterationCount));
			}
		}
		
		//if (dataItem.getName().equals("bookOrders") && methodType == MethodType.GetAsListByIds)
		//	System.out.println();

		if (isParameterRequired(methodType, testType)) {
			util.setDataUnitRelated(DataLayerHelper.isDataUnitRelated(this.getClass()));
			List<ModelParameter> modelParameters = util.createModelParameters(methodType, modelUnit, dataItem, packageName, className, structure);
			modelOperation.setParameters(modelParameters);
			//parameterType = getParameterType(methodType, modelUnit, field, packageName, className, structure);
			//modelOperation.addParameter(CodeUtil.createParameter(parameterType, typeName));
		}

		if (modelUnit instanceof ModelInterface)
			return modelOperation;

		Buf buf = new Buf();
		buf.put(createSource_DataAccess(modelUnit, dataItem, sourceType, methodType, testType));
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	public <T extends Type> String getTargetMethodName(SourceType sourceType, MethodType methodType, TestType testType, T dataItem) {
		String targetMethodName = getOperationName(sourceType, methodType, dataItem);
		if (testType.isRepository()) {
			String elementName = ModelLayerHelper.getElementNameCapped(dataItem);
			switch (methodType) {
			case AddAsItem:
				return "add"+elementName+"Record";
			case AddAsList:
				return "add"+elementName+"Records";
			case RemoveAsItem:
				return "remove"+elementName+"Record";
			case RemoveAsList:
				return "remove"+elementName+"Records";
			default:
				return targetMethodName + "Records";
			}
		}
		return targetMethodName;
	}

	protected <T extends Type> String createSource_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType, TestType testType) throws Exception {
		String operationName = getOperationName(sourceType, methodType, dataItem, testType);
		Unit dataUnit = context.getUnit();

		//String methodTypeName = methodType.name();
		//String testTypeName = NameUtil.uncapName(testType.name());

//		if (!operationName.contains("BookOrders_AddAsItem") && 
//			!operationName.contains("BookOrders_AddAsList") &&
//			!operationName.contains("BookOrders_RemoveAsItem") && 
//			!operationName.contains("BookOrders_RemoveAsList"))
//			return "";

		int indent = 2;
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+operationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");

		buf.put(createSource_PrepareTestContext(dataUnit, methodType, testType, dataItem));
		buf.put(createSource_SetupExpectations(dataUnit, methodType, testType, dataItem));
		buf.put(createSource_BeginTransaction(testType));

		ActionPreparationSourceGenerator actionPreparationSourceGenerator = new ActionPreparationSourceGenerator(context);
		actionPreparationSourceGenerator.initialize(modelUnit, dataItem, methodType, testType);
		buf.put(actionPreparationSourceGenerator.generate(indent));

		buf.putLine2("");
		if (testType.isException()) {
			buf.putLine2("try {");
			indent++;
		}
		
		ActionExecutionSourceGenerator actionExecutionSourceGenerator = new ActionExecutionSourceGenerator(context);
		actionExecutionSourceGenerator.initialize(modelUnit, dataItem, methodType, testType);
		buf.put(actionExecutionSourceGenerator.generate(indent));

		if (testType.isException()) {
			buf.putLine(indent, "fail(\"Exception should have been thrown\");");
			buf.putLine(indent--, "");
			buf.putLine(indent, "} catch (Exception e) {");
			buf.putLine(indent, "	Assert.exception(e, org.aries.ApplicationFailure.class);");
			buf.putLine(indent, "	Assert.exception(e, \"error"+testIterationCount+"\");");
		}
			
		if (testType.isCommit())
			buf.put(createSource_CommitTransaction(testType));
		if (testType.isRollback())
			buf.put(createSource_RollbackTransaction(testType));

		ActionValidationSourceGenerator actionValidationSourceGenerator = new ActionValidationSourceGenerator(context);
		actionValidationSourceGenerator.initialize(modelUnit, dataItem, methodType, testType);
		buf.put(actionValidationSourceGenerator.generate(indent));

		//buf.putLine2("	");	
		if (testType.isException()) {
			buf.putLine(indent, "");
//			buf.putLine2("} catch (Throwable e) {");
//			buf.putLine2("	System.out.println(\"ERROR: \"+e.getMessage());");
//			buf.putLine2("	e.printStackTrace();");
			buf.putLine2("} finally {");
		}

		buf.put(createSource_CleanupTestContext(indent, dataUnit, methodType, testType, dataItem));

		if (testType.isException()) {
			buf.putLine2("}");
			indent--;
		}
			
		buf.putLine2("");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		return buf.get();
	}

	
	protected <T extends Type> String createSource_PrepareTestContext(Unit unit, MethodType methodType, TestType testType, T dataItem) {
		String elementName = ModelLayerHelper.getElementNameCapped(dataItem);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(dataItem);
		String helperBeanName = NameUtil.uncapName(getHelperClassName());
		
		Buf buf = new Buf();
		buf.putLine2("");
		buf.putLine2("// prepare test context");
		if (testType.isException())
			buf.putLine2("setupByteman(testName);");
		
		switch (methodType) {
		case RemoveAsItem:
			buf.putLine2(helperBeanName+".assureRemoveAll();");
			buf.putLine2("Long id = "+helperBeanName+".add"+elementName+"();");
			break;
		case RemoveAsList:
			buf.putLine2("int "+elementNameUncapped+"Count = 2;");
			buf.putLine2(helperBeanName+".assureRemoveAll();");
			buf.putLine2(helperBeanName+".add"+elementName+"("+elementNameUncapped+"Count);");
			break;
		default:
			buf.putLine2(helperBeanName+".assureRemoveAll();");
			break;
		}
		
		return buf.get();
	}


	protected <T extends Type> String createSource_SetupExpectations(Unit unitUnit, MethodType methodType, TestType testType, T dataItem) {
		Buf buf1 = new Buf();
		if (testType.isException()) {
			buf1.putLine2("isExceptionExpected = true;");
		}
		
		Buf buf = new Buf();
		if (buf1.length() > 0) {
			buf.putLine2("");
			buf.putLine2("// setup expectations");
			buf.put(buf1.get());
		}
		return buf.get();
	}


	protected <T extends Type> String createSource_CleanupTestContext(int indent, Unit unit, MethodType methodType, TestType testType, T dataItem) {
		Buf buf = new Buf();
		if (testType.isException()) {
			buf.putLine(indent, "// cleanup test context");
			buf.putLine(indent, "tearDownByteman(testName);");
		}
		if (testType.isUserTransaction() || testType.isJTATransaction()) {
			buf.putLine(indent, "clearTransaction();");
		}
		return buf.get();
	}


	public class ActionPreparationSourceGenerator extends AbstractDataItemSourceGenerator {

		protected String helperBeanName;
		

		public ActionPreparationSourceGenerator(GenerationContext context) {
			super(context);
		}

		public void initialize(ModelUnit modelUnit, Type dataItem, MethodType methodType, TestType testType) {
			super.initialize(modelUnit, dataItem, methodType, testType);
			helperBeanName = NameUtil.uncapName(getHelperClassName());
		}

		public String generate(int indent) {
			Buf buf = new Buf();
			String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
			String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(dataItem);

			switch (methodType) {
			case GetAllAsList:
				return provider.getNothingForNow();
				
			case GetAllAsMap: 
				return provider.getNothingForNow();
			
			case GetAsItem: 
			case GetAsItemByKey: 
				return provider.getNothingForNow();
			
			case GetAsList: 
			case GetAsListByKeys: 
				return provider.getNothingForNow();
			
			case GetAsMapByKeys: 
				return provider.getNothingForNow();
			
			case GetMatchingAsList: 
				return provider.getNothingForNow();
			
			case Set: 
				return provider.getNothingForNow();
			
			case AddAsItem:
				if (testType.isException()) {
					addImportedClassForFixture(modelUnit, dataItem);
					buf.putLine(indent, typeName+" "+paramName+"ToAdd = "+fixtureName+".create_"+typeName+"();");
					
				} else {
					addImportedClassForFixture(modelUnit, dataItem);
					buf.putLine(indent, typeName+" "+paramName+"ToAdd = "+fixtureName+".create_"+typeName+"();");
				}
				break;
				
			case AddAsList: 
				if (testType.isException()) {
					addImportedClassForFixture(modelUnit, dataItem);
					buf.putLine(indent, structuredType+" "+structuredParam+"ToAdd = "+fixtureName+".create"+structuredName+"_"+typeName+"();");
					
				} else {
					addImportedClassForFixture(modelUnit, dataItem);
					buf.putLine(indent, structuredType+" "+structuredParam+"ToAdd = "+fixtureName+".create"+structuredName+"_"+typeName+"();");
				}
				break;
			
			case RemoveAll: 
				return provider.getNothingForNow();
			
			case RemoveAsItem:
				//buf.putLine2(helperBeanName+".verify"+fieldName+"Count(0);");
				buf.putLine(indent, typeName+" "+paramName+"ToRemove = "+helperBeanName+".get"+fieldName+"ById(id);");
				buf.putLine(indent, "Assert.notNull("+paramName+"ToRemove, \"The "+fieldName+" record should exist\");");
				break;
			
			case RemoveAsItemByKey:
				return provider.getNothingForNow();
			
			case RemoveAsListByKeys:
				return provider.getNothingForNow();
			
			case RemoveAsList:
				buf.putLine(indent, structuredType+" "+structuredParam+"ToRemove = "+helperBeanName+".getAll"+fieldName+"();");
				buf.putLine(indent, "Assert.equals("+elementNameUncapped+"Count, "+structuredParam+"ToRemove.size(), \"The "+fieldName+" records should exist\");");
				break;
			
			case RemoveMatchingAsList:
				return provider.getNothingForNow();
			
			default: 
			}

			if (buf.length() > 0) {
				Buf buf2 = new Buf();
				buf2.putLine(indent, "");
				buf2.putLine(indent, "// prepare test data");
				buf2.put(buf.get());
				buf = buf2;
			}
			return buf.get();
		}
	}
	
	
	public class ActionExecutionSourceGenerator extends AbstractDataItemSourceGenerator {

		protected String helperBeanName;
		

		public ActionExecutionSourceGenerator(GenerationContext context) {
			super(context);
		}

		public void initialize(ModelUnit modelUnit, Type dataItem, MethodType methodType, TestType testType) {
			super.initialize(modelUnit, dataItem, methodType, testType);
			helperBeanName = NameUtil.uncapName(getHelperClassName());
		}

		public String generate(int indent) {
			Buf buf = new Buf();
			buf.putLine(indent, "// execute test action");
			
			String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
			//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(dataItem);
			String operationName = "runTest_"+elementNameCapped+"_"+methodType.name();

			switch (methodType) {
			case GetAllAsList:
				return provider.getNothingForNow();
				
			case GetAllAsMap: 
				return provider.getNothingForNow();
			
			case GetAsItem: 
			case GetAsItemByKey: 
				return provider.getNothingForNow();
			
			case GetAsList: 
			case GetAsListByKeys: 
				return provider.getNothingForNow();
			
			case GetAsMapByKeys: 
				return provider.getNothingForNow();
			
			case GetMatchingAsList: 
				return provider.getNothingForNow();
			
			case Set: 
				return provider.getNothingForNow();
			
			case AddAsItem:
				buf.putLine(indent, operationName+"("+paramName+"ToAdd);");
				break;
				
			case AddAsList: 
				buf.putLine(indent, operationName+"("+structuredParam+"ToAdd);");
				break;
			
			case RemoveAll: 
				return provider.getNothingForNow();
			
			case RemoveAsItem:
				buf.putLine(indent, operationName+"("+paramName+"ToRemove);");
				break;
			
			case RemoveAsItemByKey:
				return provider.getNothingForNow();
			
			case RemoveAsListByKeys:
				return provider.getNothingForNow();
			
			case RemoveAsList:
				buf.putLine(indent, operationName+"("+structuredParam+"ToRemove);");
				break;
			
			case RemoveMatchingAsList:
				return provider.getNothingForNow();
			
			default: 
			}

			return buf.get();
		}
	}
	
	
	public class ActionValidationSourceGenerator extends AbstractDataItemSourceGenerator {

		protected String helperBeanName;
		

		public ActionValidationSourceGenerator(GenerationContext context) {
			super(context);
		}

		public void initialize(ModelUnit modelUnit, Type dataItem, MethodType methodType, TestType testType) {
			super.initialize(modelUnit, dataItem, methodType, testType);
			helperBeanName = NameUtil.uncapName(getHelperClassName());
		}

		public String generate(int indent) {
			Buf buf = new Buf();
			buf.putLine(indent, "");
			buf.putLine(indent, "// validate persistent state");

			String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
			String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(dataItem);
			//String operationName = "runTest_"+elementNameCapped+"_"+methodType.name();
			
			switch (methodType) {
			case GetAllAsList:
				return provider.getNothingForNow();
				
			case GetAllAsMap: 
				return provider.getNothingForNow();
			
			case GetAsItem: 
			case GetAsItemByKey: 
				return provider.getNothingForNow();
			
			case GetAsList: 
			case GetAsListByKeys: 
				return provider.getNothingForNow();
			
			case GetAsMapByKeys: 
				return provider.getNothingForNow();
			
			case GetMatchingAsList: 
				return provider.getNothingForNow();
			
			case Set: 
				return provider.getNothingForNow();
			
			case AddAsItem:
				if (testType.isException() || testType.isRollback()) {
					//buf.putLine(indent, helperBeanName+".verify"+fieldName+"Count(0);");
					//buf.putLine(indent, typeName+" "+paramName+" = "+helperBeanName+".getFrom"+fieldName+"ById();");
					buf.putLine(indent, structuredType+" "+structuredParam+" = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.isEmpty("+structuredParam+", \"No "+fieldName+" records should exist\");");
					
				} else {
					addImportedClassForFixture(modelUnit, dataItem);
					//buf.putLine(indent, typeName+" "+paramName+" = "+helperBeanName+".getFrom"+fieldName+"ById("+paramName+"Id);");
					buf.putLine(indent, structuredType+" "+structuredParam+" = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.equals(1, "+structuredParam+".size(), \"Only 1 "+fieldName+" record should exist\");");
					buf.putLine(indent, fixtureName+".assertSame"+typeName+"("+paramName+"ToAdd, "+structuredParam+".get(0));");
				}
				break;
				
			case AddAsList: 
				if (testType.isException() || testType.isRollback()) {
					//buf.putLine(indent, helperBeanName+".verify"+fieldName+"Count(0);");
					buf.putLine(indent, structuredType+" "+structuredParam+" = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.isEmpty("+structuredParam+", \"No "+fieldName+" records should exist\");");
					
				} else {
					addImportedClassForFixture(modelUnit, dataItem);
					buf.putLine(indent, structuredType+" "+structuredParam+" = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, fixtureName+".assertSame"+typeName+"("+structuredParam+"ToAdd, "+structuredParam+");");
				}
				break;
			
			case RemoveAll: 
				return provider.getNothingForNow();
			
			case RemoveAsItem:
				if (testType.isException()) {
					//buf.putLine(indent, helperBeanName+".verify"+fieldName+"Count(0);");
					buf.putLine(indent, typeName+" "+paramName+"NotRemoved = "+helperBeanName+".get"+fieldName+"ById(id);");
					buf.putLine(indent, "Assert.notNull("+paramName+"NotRemoved, \"The "+fieldName+" record should still exist\");");
					buf.putLine(indent, structuredType+" "+structuredParam+" = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.equals(1, "+structuredParam+".size(), \"Only 1 "+fieldName+" record should exist\");");
					
				} else if (testType.isRollback()) {
					buf.putLine(indent, typeName+" "+paramName+"NotRemoved = "+helperBeanName+".get"+fieldName+"ById(id);");
					buf.putLine(indent, "Assert.notNull("+paramName+"NotRemoved, \"The "+fieldName+" record should still exist\");");
					buf.putLine(indent, structuredType+" "+structuredParam+"Remaining = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.equals(1, "+structuredParam+"Remaining.size(), \"Only 1 "+fieldName+" record should exist\");");
					
				} else {
					buf.putLine(indent, typeName+" "+paramName+"Removed = "+helperBeanName+".get"+fieldName+"ById(id);");
					buf.putLine(indent, "Assert.isNull("+paramName+"Removed, \"The "+fieldName+" record should be removed\");");
					buf.putLine(indent, structuredType+" "+structuredParam+"Remaining = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.isEmpty("+structuredParam+"Remaining, \"No "+fieldName+" records should exist\");");
				}
				break;
			
			case RemoveAsItemByKey:
				return provider.getNothingForNow();
			
			case RemoveAsListByKeys:
				return provider.getNothingForNow();
			
			case RemoveAsList:
				if (testType.isException() || testType.isRollback()) {
					//buf.putLine(indent, helperBeanName+".verify"+fieldName+"Count(0);");
					buf.putLine(indent, structuredType+" "+structuredParam+"Remaining = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.equals("+elementNameUncapped+"Count, "+structuredParam+"Remaining.size(), \"The "+fieldName+" records should still exist\");");
					buf.putLine(indent, fixtureName+".assertSame"+typeName+"("+structuredParam+"Remaining, "+structuredParam+"ToRemove);");
					
				} else {
					buf.putLine(indent, structuredType+" "+structuredParam+"Remaining = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.isEmpty("+structuredParam+"Remaining, \"No "+fieldName+" records should exist\");");
				}
				break;
			
			case RemoveMatchingAsList:
				return provider.getNothingForNow();
			
			default: 
			}

			return buf.get();
		}
	}

	protected ModelOperation createOperation_RunTest(ModelUnit modelUnit, Type dataItem, SourceType sourceType, MethodType methodType) throws Exception {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
		
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("runTest_"+elementNameCapped+"_"+methodType.name());
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setClassName("Runnable");
		modelParameter.setName("runnable");
		modelOperation.addParameter(modelParameter);
		modelOperation.addException("Exception");
		
		String type = dataItem.getType();
		String packageName = TypeUtil.getPackageName(type);
		String className = TypeUtil.getClassName(type);

		String structure = dataItem.getStructure();
		util.setDataUnitRelated(DataLayerHelper.isDataUnitRelated(this.getClass()));
		List<ModelParameter> modelParameters = util.createModelParameters(methodType, modelUnit, dataItem, packageName, className, structure);
		Iterator<ModelParameter> iterator = modelParameters.iterator();
		while (iterator.hasNext()) {
			modelParameter = iterator.next();
			modelParameter.setFinal(true);
		}
		modelOperation.setParameters(modelParameters);
		
		String unitName = context.getUnit().getName();
		String managerName = NameUtil.uncapName(unitName) + "Manager";
		String managerOperation = getOperationNameForManager(modelUnit, dataItem, sourceType, methodType);
		String argumentList = CodeUtil.getArgumentString(modelOperation);

		Buf buf = new Buf();
		buf.putLine2("runTest(new Runnable() {");
		buf.putLine2("	public void run() {");
		buf.putLine2("		"+managerName+"."+managerOperation+"("+argumentList+");");
		buf.putLine2("	}");
		buf.putLine2("});");

		modelOperation.addInitialSource(buf.get());
		return modelOperation; 
	}

	protected String getOperationNameForManager(ModelUnit modelUnit, Type dataItem, SourceType sourceType, MethodType methodType) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
		String structure = dataItem.getStructure();
		String prefix = util.getDataAccessMethodPrefix(methodType, structure);
		String suffix = util.getDataAccessMethodSuffix(methodType, structure);
		String operationName = prefix + elementNameCapped + suffix;
		return operationName;
	}
	
	protected String getOperationName(SourceType sourceType, MethodType methodType, Type dataItem, TestType testType) {
		String methodTypePart = methodType.name();
//		if (testType.isUserTransaction())
//			methodTypePart += "_bmt";
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
		String testTypeText = testType.getName();
		String operationName = "runTest_" + elementNameCapped + "_" + methodTypePart + "_" + testTypeText;
		return operationName; 
	}
	
}
