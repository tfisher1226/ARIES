package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.data.DataLayerHelper;
import nam.model.Cache;
import nam.model.Field;
import nam.model.ModelLayerHelper;
import nam.model.Process;
import nam.model.Type;
import nam.model.util.CacheUtil;
import nam.model.util.ElementUtil;
import nam.model.util.ModuleUtil;
import nam.model.util.TypeUtil;
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
import aries.generation.model.ModelInterface;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


/**
 * Builds a Cache State Manager CIT {@link ModelClass} object given a {@link Cache} Specification as input;
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
public class CacheUnitManagerCITBuilder extends AbstractDataUnitManagerCITBuilder {

	public CacheUnitManagerCITBuilder(GenerationContext context) {
		super(context);
	}

	public String getBasePackageName(String namespace) {
		return DataLayerHelper.getCacheUnitPackageName(namespace, context.getCache());
	}

	public String getBaseClassName() {
		return DataLayerHelper.getCacheUnitInterfaceName(context.getCache());
	}

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

	public ModelClass buildClass(String namespace, Cache cache) throws Exception {
		String packageName = DataLayerHelper.getCacheUnitPackageName(namespace, cache);
		String className = DataLayerHelper.getCacheUnitInterfaceName(cache) + "ManagerCIT";
		String beanName = NameUtil.uncapName(className);
		String cacheType = CacheUtil.getType(cache);
		context.setUnit(null);

		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		ModelClass modelClass = new ModelClass();
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		modelClass.setType(cacheType);
		modelClass.setParentClassName("AbstractCacheUnitArquillionTest");
		modelClass.addImportedClass("org.aries.tx.AbstractCacheUnitArquillionTest");
		
		testIterationCount = 0;
		this.modelUnit = modelClass;
		initializeClass(modelClass, cache);
		return modelClass; 
	}

	public void initializeClass(ModelClass modelClass, Cache cache) throws Exception {
		provider = new CacheUnitManagerCITProvider(context);
		provider.baseName = getBaseClassName();
		initializeImportedClasses(modelClass);
		initializeClassAnnotations(modelClass);
		//initializeClassConstructors(modelClass, cache);
		initializeInstanceFields(modelClass, cache);
		initializeInstanceMethods(modelClass);
	}

	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		super.initializeImportedClasses(modelClass);
		modelClass.addImportedClass("java.util.concurrent.Callable");
		modelClass.addImportedClass("org.aries.tx.CacheModuleTestControl");
	}

	//	protected void initializeClassConstructors(ModelClass modelClass, Cache cache) throws Exception {
	//		modelClass.addInstanceConstructor(createConstructor(modelClass, cache));
	//	}

	//	protected ModelConstructor createConstructor(ModelClass modelClass, Cache cache) throws Exception {
	//		ModelConstructor constructor = new ModelConstructor();
	//		constructor.setModifiers(Modifier.PUBLIC);
	//		//String mbeanName = NameUtil.splitStringUsingUnderscores(className) + "_MBEAN_NAME";
	//		String sourceCode = CodeUtil.createMethodSource(new String[] {
	//				//"MBeanUtil.registerMBean(this, "+mbeanName.toUpperCase()+");"
	//				//"setLatestStateFilename("+serviceName+"State.LATEST_STATE_FILENAME);",
	//				//"setShadowStateFilename("+serviceName+"State.SHADOW_STATE_FILENAME);"
	//		});
	//		constructor.addInitialSource(sourceCode);
	//		return constructor;
	//	}

	protected void initializeInstanceFields(ModelClass modelClass, Cache cache) throws Exception {
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
		createMethod_InitializeCacheUnitHelper(modelClass);
		createMethod_CreateCacheUnitProxy(modelClass);
		createMethod_CreateCacheUnitControl(modelClass);
		createMethod_JUnitTeardown(modelClass);
		createMethod_CreateTestEAR(modelClass);
		createMethod_CreateTestWAR(modelClass);
		createMethods_DataAccess(modelClass);
		createMethod_RunActionAsRunnable(modelClass);
		createMethod_RunActionAsCallable(modelClass);
	}
	
	protected void createMethod_InitializeCacheUnitHelper(ModelClass modelClass) {
		String className = TypeUtil.getClassName(modelClass.getType());
		String instanceName = NameUtil.uncapName(className);
		String proxyClassName = getBaseClassName() + "Proxy";
		String controlClassName = getBaseClassName() + "Control";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("initialize"+className+"Helper");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		//buf.putLine2(helperBeanName+".setJmxManager(jmxManager);");
		//buf.putLine2(helperBeanName+".initialize(transactionTestControl);");
		buf.putLine2(instanceName+"Helper.setProxy(create"+proxyClassName+"());");
		buf.putLine2(instanceName+"Helper.initialize(create"+controlClassName+"());");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CreateCacheUnitProxy(ModelClass modelClass) {
		String className = TypeUtil.getClassName(modelClass.getType());
		String instanceName = NameUtil.uncapName(className);
		String proxyClassName = getBaseClassName() + "Proxy";
		String proxyInstanceName = instanceName + "Proxy";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+className+"Proxy");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType(className+"Proxy");
		modelOperation.addException("Exception");

		Buf buf = new Buf();
		buf.putLine2(proxyClassName+" "+proxyInstanceName+" = new "+proxyClassName+"();");
		buf.putLine2(proxyInstanceName+".setJmxManager(jmxManager);");
		buf.putLine2("return "+proxyInstanceName+";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CreateCacheUnitControl(ModelClass modelClass) {
		String controlClassName = getBaseClassName() + "Control";
		String controlBeanName = NameUtil.uncapName(controlClassName);
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+controlClassName);
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setResultType("CacheModuleTestControl");
		modelOperation.addException("Exception");
		
		Buf buf = new Buf();
		buf.putLine2("CacheModuleTestControl "+controlBeanName+" = new CacheModuleTestControl(transactionTestControl);");
		buf.putLine2(controlBeanName+".setupCacheLayer();");
		buf.putLine2("return "+controlBeanName+";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethods_DataAccess(ModelUnit modelUnit) throws Exception {
		createMethods_DataAccess(modelUnit, context.getCache(), SourceType.SharedCache);
	}

	protected void createMethods_DataAccess(ModelUnit modelUnit, Cache cache, SourceType sourceType) throws Exception {
		if (cache != null) {
			if (!StringUtils.isEmpty(cache.getRef()))
				cache = ModuleUtil.getCache(context.getModule(), cache.getRef());
			List<Field> fields = ElementUtil.getFields(cache);
			if (fields == null || fields.size() == 0) {
				log.warn("No items found in cache: "+cache.getName());
				//List<Item> items = CacheUtil.getItems(cache);
				//if (items == null || items.size() == 0) {
				//	log.warn("No items found in cache: "+cache.getName());
			} else {
				createMethods_DataAccess(modelUnit, fields, sourceType);
			}
		}
	}

	protected <T extends Type> void createMethods_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType) throws Exception {
		String structure = dataItem.getStructure();
		if (structure.equals("item") && false) {
			modelUnit.addInstanceOperations(createOperations_DataAccess_ReadOperation(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess_ReadOperation(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
			if (this.getClass().equals(CacheUnitManagerBuilder.class))
				modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));

		} else if (structure.equals("list") || structure.equals("set")) {
			modelUnit.addInstanceOperations(createOperations_DataAccess_ReadOperation(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess_ReadOperation(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
			if (this.getClass().equals(CacheUnitManagerBuilder.class))
				modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));

		} else if (structure.equals("map")) {
			modelUnit.addInstanceOperations(createOperations_DataAccess_ReadOperation(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess_ReadOperation(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsMap));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperations(createOperations_DataAccess_WriteOperation(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));

			//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Success));
			//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.TxCommit));
			//			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.TxRollback));
			//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.ManagerNull));
			//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.RepositoryNull));
			//			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.RunTest));
		}
	}

	protected <T extends Type> Collection<ModelOperation> createOperations_DataAccess_ReadOperation(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Success));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Commit + TestType.JTATransaction));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Rollback + TestType.JTATransaction));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Commit + TestType.UserTransaction));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Rollback + TestType.UserTransaction));
		modelOperations.add(createOperation_RunAction_AsCallable(modelUnit, dataItem, sourceType, methodType));
		return modelOperations;
	}

	protected <T extends Type> Collection<ModelOperation> createOperations_DataAccess_WriteOperation(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType) throws Exception {
		List<ModelOperation> modelOperations = new ArrayList<ModelOperation>();
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Success));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Commit + TestType.JTATransaction));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Commit + TestType.UserTransaction));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Manager + TestType.Exception + TestType.Rollback));
		modelOperations.add(createOperation_DataAccess(modelUnit, dataItem, sourceType, methodType, TestType.Cache + TestType.Exception + TestType.Rollback));
		modelOperations.add(createOperation_RunAction_AsRunnable(modelUnit, dataItem, sourceType, methodType));
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

//		if (className.equals("BookKey"))
//			System.out.println();
		
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
			Cache cache = context.getCache();
			if (testType.isManager()) {
				String targetClassName = cache.getName() + "Manager";
				String targetMethodName = getOperationName(sourceType, methodType, dataItem);
				modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(
						targetClassName, 
						targetMethodName, "AT EXIT", 
						"throw new org.aries.ApplicationFailure(\\\"error"+testIterationCount+"\\\")", 
						testIterationCount));
			} else if (testType.isCache()) {
				String targetClassName = cache.getName() + "Impl";
				String targetMethodName = getOperationName(sourceType, methodType, dataItem);
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
			util.setCacheUnitRelated(DataLayerHelper.isCacheUnitRelated(this.getClass()));
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

	protected <T extends Type> String createSource_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType, MethodType methodType, TestType testType) throws Exception {
		String operationName = getOperationName(sourceType, methodType, dataItem, testType);
		Cache cacheUnit = context.getCache();

		int indent = 2;
		Buf buf = new Buf();
		buf.putLine2("String testName = \""+operationName+"\";");
		buf.putLine2("log.info(testName+\": started\");");

		buf.put(createSource_PrepareTestContext(cacheUnit, methodType, testType, dataItem));
		buf.put(createSource_SetupExpectations(cacheUnit, methodType, testType, dataItem));
		
		if (methodType != MethodType.RemoveAll)
			buf.put(createSource_BeginTransaction(testType));
		ActionPreparationSourceGenerator actionPreparationSourceGenerator = new ActionPreparationSourceGenerator(context);
		actionPreparationSourceGenerator.initialize(modelUnit, dataItem, methodType, testType);
		buf.put(actionPreparationSourceGenerator.generate(indent));
		if (methodType == MethodType.RemoveAll)
			buf.put(createSource_BeginTransaction(testType));

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
			indent++;
		}
		
		if (testType.isCommit())
			buf.put(createSource_CommitTransaction(testType));
		if (testType.isRollback())
			buf.put(createSource_RollbackTransaction(testType));

		if (methodType.isReadOperation() && testType.isTransactional())
			buf.put(actionExecutionSourceGenerator.generate2ndReadOperation(indent));
		
		ActionValidationSourceGenerator actionValidationSourceGenerator = new ActionValidationSourceGenerator(context);
		actionValidationSourceGenerator.initialize(modelUnit, dataItem, methodType, testType);
		buf.put(actionValidationSourceGenerator.generate(indent));

		if (testType.isException()) {
			buf.putLine(indent, "");
			buf.putLine2("} finally {");
		}
		
		buf.put(createSource_CleanupTestContext(indent, cacheUnit, dataItem, testType));

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

	
	protected <T extends Type> String createSource_PrepareTestContext(Cache cacheUnit, MethodType methodType, TestType testType, T dataItem) {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
		String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(dataItem);
		String elementClassName = ModelLayerHelper.getElementClassName(dataItem);
		String elementKeyClassName = ModelLayerHelper.getElementKeyClassName(dataItem);
		String elementKeyBeanName = ModelLayerHelper.getElementKeyLocalPart(dataItem);
		
		String type = dataItem.getType();
		String keyType = dataItem.getKey();
		String fixtureName = ModelLayerHelper.getFixtureClassName(type);
		String helperName = ModelLayerHelper.getModelHelperBeanName(type);
		String helperBeanName = NameUtil.uncapName(getHelperClassName());
		String paramName = TypeUtil.getLocalPart(type);
		String structure = dataItem.getStructure();
		
		Buf buf = new Buf();
		buf.putLine2("");
		buf.putLine2("// prepare test context");
		if (testType.isException() && methodType != MethodType.RemoveAll)
			buf.putLine2("setupByteman(testName);");
		
		switch (methodType) {
		case RemoveAsItem:
			addImportedClassForFixture(modelUnit, dataItem);
			addImportedClassForHelper(modelUnit, dataItem);
			buf.putLine2(helperBeanName+".assureRemoveAll();");
			if (structure.equals("map")) {
				buf.putLine2(elementClassName+" "+paramName+" = "+fixtureName+".create_"+elementClassName+"();");
				buf.putLine2(elementKeyClassName+" "+elementKeyBeanName+" = "+helperName+".create"+elementKeyClassName+"("+paramName+");");
				buf.putLine2(helperBeanName+".addTo"+elementNameCapped+"("+elementKeyBeanName+", "+paramName+");");
			} else {
				buf.putLine2(elementClassName+" "+paramName+" = "+fixtureName+".create_"+elementClassName+"();");
				buf.putLine2(helperBeanName+".addTo"+elementNameCapped+"("+paramName+");");
			}
			break;
		case RemoveAsList:
			buf.putLine2("int "+paramName+"Count = 4;");
			buf.putLine2(helperBeanName+".assureRemoveAll();");
			buf.putLine2("List<"+elementClassName+"> "+paramName+"List = "+fixtureName+".createList_"+elementClassName+"("+paramName+"Count);");
			buf.putLine2(helperBeanName+".addTo"+elementNameCapped+"("+paramName+"List);");
			break;
		default:
			buf.putLine2(helperBeanName+".assureRemoveAll();");
			break;
		}
		
		if (testType.isException() && methodType == MethodType.RemoveAll)
			buf.putLine2("setupByteman(testName);");
		return buf.get();
	}


	protected <T extends Type> String createSource_SetupExpectations(Cache cacheUnit, MethodType methodType, TestType testType, T dataItem) {
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

	
	protected <T extends Type> String createSource_CleanupTestContext(int indent, Cache cacheUnit, T dataItem, TestType testType) {
		Buf buf = new Buf();
		if (testType.isException()) {
			//buf.putLine(indent, "");
			buf.putLine(indent, "// cleanup test context");
			buf.putLine(indent, "tearDownByteman(testName);");
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

			String elementClassName = ModelLayerHelper.getElementClassName(dataItem);
			String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
			String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(dataItem);

			switch (methodType) {
			case GetAllAsList:
				addImportedClassForFixture(modelUnit, dataItem);
				buf.putLine2("int "+paramName+"Count = 4;");
				buf.putLine2(structuredType+" "+structuredParam+"ToAdd = "+fixtureName+".create"+structuredName+"_"+elementClassName+"("+paramName+"Count);");
				buf.putLine2(helperBeanName+".addTo"+elementNameCapped+"("+structuredParam+"ToAdd);");
				break;
				
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
			
			case GetMatchingAsMap: 
				buf.putLine(indent, "");
				buf.putLine(indent, "// prepare unmatching data");
				addImportedClassForFixture(modelUnit, dataItem);
				buf.putLine(indent, typeName+" "+paramName+"ToAdd = "+fixtureName+".create_"+typeName+"();");
				buf.putLine(indent, helperBeanName+".addTo"+fieldName+"("+paramName+"ToAdd);");
				
				buf.putLine(indent, "");
				buf.putLine(indent, "// prepare matching data");
				buf.putLine2("int "+paramName+"Count = 4;");
				buf.putLine2("List<"+elementClassName+"> "+paramName+"ListToAdd = "+fixtureName+".createList_"+elementClassName+"("+paramName+"Count);");
				buf.putLine2(helperBeanName+".addTo"+elementNameCapped+"("+paramName+"ListToAdd);");
				return buf.get();
				
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
					//buf.putLine2("List<"+typeName+"> "+paramName+"ListToAdd = "+fixtureName+".createList_"+typeName+"();");
					buf.putLine(indent, structuredType+" "+structuredParam+"ToAdd = "+fixtureName+".create"+structuredName+"_"+typeName+"();");
					
				} else {
					addImportedClassForFixture(modelUnit, dataItem);
					buf.putLine(indent, structuredType+" "+structuredParam+"ToAdd = "+fixtureName+".create"+structuredName+"_"+typeName+"();");
				}
				break;
			
			case RemoveAll: 
				buf.putLine(indent, "int "+paramName+"Count = 4;");
				buf.putLine(indent, structuredType+" "+structuredParam+"ToAdd = "+fixtureName+".create"+structuredName+"_"+typeName+"(bookCount);");
				buf.putLine(indent, helperBeanName+".addTo"+fieldName+"("+structuredParam+"ToAdd);");
				if (testType.isException()) {
					buf.putLine(indent, structuredType+" "+structuredParam+"ToRemove = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.equals("+paramName+"Count, "+structuredParam+"ToRemove.size(), \"The "+fieldName+" records should exist\");");
				}
				break;
			
			case RemoveAsItem:
				if (structure.equals("map")) {
					//buf.putLine2(helperBeanName+".verify"+fieldName+"Count(0);");
					buf.putLine2(typeName+" "+paramName+"ToRemove = "+helperBeanName+".getFrom"+fieldName+"("+keyParamName+");");
					buf.putLine2("Assert.notNull("+paramName+"ToRemove, \"The "+fieldName+" record should exist\");");
				} else {
					buf.putLine2(typeName+" "+paramName+"ToRemove = "+helperBeanName+".getFrom"+fieldName+"("+paramName+".getId());");
					buf.putLine2("Assert.notNull("+paramName+"ToRemove, \"The "+fieldName+" record should exist\");");
				}
				break;
			
			case RemoveAsItemByKey:
				return provider.getNothingForNow();
			
			case RemoveAsListByKeys:
				return provider.getNothingForNow();
			
			case RemoveAsList:
				buf.putLine(indent, structuredType+" "+structuredParam+"ToRemove = "+helperBeanName+".getAll"+fieldName+"();");
				buf.putLine(indent, "Assert.equals("+paramName+"Count, "+structuredParam+"ToRemove.size(), \"The "+fieldName+" records should exist\");");
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
			//buf.putLine(indent, "");
			buf.putLine(indent, "// execute action");
			
			String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
			String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(dataItem);
			String elementClassName = ModelLayerHelper.getElementClassName(dataItem);
			String operationName = "runAction_"+elementNameCapped+"_"+methodType.name();

			switch (methodType) {
			case GetAllAsList:
				buf.putLine(indent, structuredType+" "+structuredParam+" = "+operationName+"();");
				if (testType.isCommit() || testType.isRollback()) {
					buf.putLine(indent, "");
					buf.putLine(indent, "// validate intermediate cache state");
					buf.putLine(indent, "Assert.isEmpty("+structuredParam+", \"No "+fieldName+" records should exist\");");
				}
				break;
				
			case GetAllAsMap: 
				return provider.getNothingForNow();
			
			case GetAsItem: 
			case GetAsItemByKey: 
				return provider.getNothingForNow();
			
			case GetAsList: 
				buf.putLine(indent, structuredType+" "+structuredParam+" = "+operationName+"();");
				if (testType.isCommit() || testType.isRollback()) {
					buf.putLine(indent, "");
					buf.putLine(indent, "// validate intermediate cache state");
					buf.putLine(indent, "Assert.isEmpty("+structuredParam+", \"No "+fieldName+" records should exist\");");
				}
				break;

			case GetAsListByKeys: 
				return provider.getNothingForNow();
			
			case GetAsMapByKeys: 
				return provider.getNothingForNow();
			
			case GetMatchingAsList: 
				return provider.getNothingForNow();
			
			case GetMatchingAsMap: 
				modelUnit.addImportedClass("java.util.Map");
				buf.putLine(indent, "Map<"+keyTypeName+", "+typeName+"> "+paramName+"Map = runAction_"+fieldName+"_GetMatchingAsMap("+paramName+"ListToAdd);");
				if (testType.isCommit() || testType.isRollback()) {
					buf.putLine(indent, "");
					buf.putLine(indent, "// validate intermediate cache state");
					buf.putLine(indent, "Assert.isEmpty(bookMap.values(), \"No BooksInStock records should exist\");");
				}
				break;
				
			case Set: 
				return provider.getNothingForNow();
			
			case AddAsItem:
				buf.putLine(indent, operationName+"("+paramName+"ToAdd);");
				break;
				
			case AddAsList: 
				buf.putLine(indent, operationName+"("+structuredParam+"ToAdd);");
				break;
			
			case RemoveAll: 
				buf.putLine(indent, operationName+"();");
				break;
			
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
		
		public String generate2ndReadOperation(int indent) {
			Buf buf = new Buf();
			buf.putLine(indent, "");
			buf.putLine(indent, "// execute action again");
			
			String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
			//String elementNameUncapped = ModelLayerHelper.getElementNameUncapped(dataItem);
			//String elementClassName = ModelLayerHelper.getElementClassName(dataItem);
			String operationName = "runAction_"+elementNameCapped+"_"+methodType.name();

			switch (methodType) {
			case GetAllAsList:
				buf.putLine(indent, structuredParam+" = "+operationName+"();");
				break;
				
			case GetAllAsMap: 
				return provider.getNothingForNow();
			
			case GetAsItem: 
			case GetAsItemByKey: 
				return provider.getNothingForNow();
			
			case GetAsList: 
				buf.putLine(indent, structuredParam+" = "+operationName+"();");
				break;

			case GetAsListByKeys: 
				return provider.getNothingForNow();
			
			case GetAsMapByKeys: 
				return provider.getNothingForNow();
			
			case GetMatchingAsList: 
				return provider.getNothingForNow();
			
			case GetMatchingAsMap: 
				modelUnit.addImportedClass("java.util.Map");
				buf.putLine(indent, paramName+"Map = "+operationName+"("+paramName+"ListToAdd);");
				break;
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
			buf.putLine(indent, "// validate cache state");

			switch (methodType) {
			case GetAllAsList:
				if (testType.isRollback()) {
					buf.putLine(indent, "Assert.isEmpty("+structuredParam+", \"No "+fieldName+" records should exist\");");
				} else {
					buf.putLine(indent, "Assert.equals("+paramName+"Count, "+structuredParam+".size(), \""+fieldName+" record count should be correct\");");
					buf.putLine(indent, fixtureName+".assertSame"+typeName+"("+structuredParam+"ToAdd, "+structuredParam+", \""+fieldName+" records should be equal\");");
				}
				break;
				
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
				
			case GetMatchingAsMap:
				if (testType.isRollback()) {
					buf.putLine(indent, "Assert.isEmpty("+paramName+"Map.values(), \"No "+fieldName+" records should exist\");");
				} else {
					buf.putLine(indent, "Assert.equals("+paramName+"Count, "+paramName+"Map.size(), \""+fieldName+" record count should be correct\");");
					buf.putLine(indent, fixtureName+".assertSame"+typeName+"("+paramName+"ListToAdd, "+paramName+"Map.values(), \""+fieldName+" records should exist\");");
				}
				break;
			
			case Set: 
				return provider.getNothingForNow();
			
			case AddAsItem:
				if (testType.isException()) {
					//buf.putLine(indent, helperBeanName+".verify"+fieldName+"Count(0);");
					//buf.putLine(indent, typeName+" "+paramName+" = "+helperBeanName+".getFrom"+fieldName+"ById();");
					buf.putLine(indent, structuredType+" "+structuredParam+" = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.isEmpty("+structuredParam+", \"No "+fieldName+" records should exist\");");
				} else {
					addImportedClassForFixture(modelUnit, dataItem);
					//buf.putLine(indent, "List<"+typeName+"> "+paramName+"List = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, structuredType+" "+structuredParam+" = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.equals(1, "+structuredParam+".size(), \"Only 1 "+fieldName+" record should exist\");");
					buf.putLine(indent, fixtureName+".assertSame"+typeName+"("+paramName+"ToAdd, "+structuredParam+".iterator().next());");
				}
				break;
				
			case AddAsList: 
				if (testType.isException()) {
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
				if (testType.isException()) {
					addImportedClassForFixture(modelUnit, dataItem);
					buf.putLine(indent, structuredType+" "+structuredParam+" = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, fixtureName+".assertSame"+typeName+"("+structuredParam+"ToRemove, "+structuredParam+");");
				} else {
					buf.putLine(indent, structuredType+" "+structuredParam+" = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.isEmpty("+structuredParam+", \"No "+fieldName+" records should exist\");");
				}
				break;
			
			case RemoveAsItem:
				if (testType.isException()) {
					//buf.putLine(indent, helperBeanName+".verify"+fieldName+"Count(0);");
					if (structure.equals("map"))
						buf.putLine(indent, paramName+" = "+helperBeanName+".getFrom"+fieldName+"("+keyParamName+");");
					else buf.putLine(indent, paramName+" = "+helperBeanName+".getFrom"+fieldName+"("+paramName+".getId());");
					buf.putLine(indent, "Assert.notNull("+paramName+", \"The "+fieldName+" record should still exist\");");
					buf.putLine(indent, structuredType+" "+structuredParam+" = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.equals(1, "+structuredParam+".size(), \"Only 1 "+fieldName+" record should exist\");");
					
				} else {
					if (structure.equals("map"))
						buf.putLine(indent, paramName+" = "+helperBeanName+".getFrom"+fieldName+"("+keyParamName+");");
					else buf.putLine(indent, paramName+" = "+helperBeanName+".getFrom"+fieldName+"("+paramName+".getId());");
					buf.putLine(indent, "Assert.isNull("+paramName+", \"The "+fieldName+" record should be removed\");");
					buf.putLine(indent, structuredType+" "+structuredParam+"Remaining = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.isEmpty("+structuredParam+"Remaining, \"No "+fieldName+" records should exist\");");
				}
				break;
			
			case RemoveAsItemByKey:
				return provider.getNothingForNow();
			
			case RemoveAsListByKeys:
				return provider.getNothingForNow();
			
			case RemoveAsList:
				if (testType.isException()) {
					//buf.putLine(indent, helperBeanName+".verify"+fieldName+"Count(0);");
					buf.putLine(indent, structuredType+" "+structuredParam+"Remaining = "+helperBeanName+".getAll"+fieldName+"();");
					buf.putLine(indent, "Assert.equals(bookCount, "+structuredParam+"Remaining.size(), \"The "+fieldName+" records should still exist\");");
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

	protected ModelOperation createOperation_RunAction_AsCallable(ModelUnit modelUnit, Type dataItem, SourceType sourceType, MethodType methodType) throws Exception {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
		
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("runAction_"+elementNameCapped+"_"+methodType.name());
		modelOperation.setModifiers(Modifier.PUBLIC);
		
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.setClassName("Callable");
		modelParameter.setName("callable");
		modelOperation.addParameter(modelParameter);
		modelOperation.addException("Exception");
		modelOperation.setResultType("<T> T");
		
		String type = dataItem.getType();
		String packageName = TypeUtil.getPackageName(type);
		String className = TypeUtil.getClassName(type);

		String structure = dataItem.getStructure();
		util.setCacheUnitRelated(DataLayerHelper.isCacheUnitRelated(this.getClass()));
		List<ModelParameter> modelParameters = util.createModelParameters(methodType, modelUnit, dataItem, packageName, className, structure);
		Iterator<ModelParameter> iterator = modelParameters.iterator();
		while (iterator.hasNext()) {
			modelParameter = iterator.next();
			modelParameter.setFinal(true);
		}
		modelOperation.setParameters(modelParameters);
		
		String cacheName = context.getCache().getName();
		String managerName = NameUtil.uncapName(cacheName) + "Manager";
		String managerOperation = getOperationNameForManager(modelUnit, dataItem, sourceType, methodType);
		String argumentList = CodeUtil.getArgumentString(modelOperation);

		Buf buf = new Buf();
		buf.putLine2("return runAction(new Callable<T>() {");
		buf.putLine2("	@SuppressWarnings(\"unchecked\")");
		buf.putLine2("	public T call() {");
		buf.putLine2("		return (T) "+managerName+"."+managerOperation+"("+argumentList+");");
		buf.putLine2("	}");
		buf.putLine2("});");

		modelOperation.addInitialSource(buf.get());
		return modelOperation; 
	}
	
	protected ModelOperation createOperation_RunAction_AsRunnable(ModelUnit modelUnit, Type dataItem, SourceType sourceType, MethodType methodType) throws Exception {
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
		
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("runAction_"+elementNameCapped+"_"+methodType.name());
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
		util.setCacheUnitRelated(DataLayerHelper.isCacheUnitRelated(this.getClass()));
		List<ModelParameter> modelParameters = util.createModelParameters(methodType, modelUnit, dataItem, packageName, className, structure);
		Iterator<ModelParameter> iterator = modelParameters.iterator();
		while (iterator.hasNext()) {
			modelParameter = iterator.next();
			modelParameter.setFinal(true);
		}
		modelOperation.setParameters(modelParameters);
		
		String cacheName = context.getCache().getName();
		String managerName = NameUtil.uncapName(cacheName) + "Manager";
		String managerOperation = getOperationNameForManager(modelUnit, dataItem, sourceType, methodType);
		String argumentList = CodeUtil.getArgumentString(modelOperation);

		Buf buf = new Buf();
		buf.putLine2("runAction(new Runnable() {");
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
		String elementNameCapped = ModelLayerHelper.getElementNameCapped(dataItem);
		String operationName = "runTest_" + elementNameCapped + "_" + methodType.name() + "_" + testType.getName();
		return operationName; 
	}

}
