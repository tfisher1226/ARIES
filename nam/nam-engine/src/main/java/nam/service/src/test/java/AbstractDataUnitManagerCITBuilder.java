package nam.service.src.test.java;

import java.lang.reflect.Modifier;
import java.util.List;

import nam.ProjectLevelHelper;
import nam.data.DataLayerHelper;
import nam.model.Application;
import nam.model.Type;
import nam.model.Unit;
import nam.model.util.TypeUtil;
import nam.service.src.main.java.CacheUnitManagerBuilder;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.MethodType;
import aries.codegen.SourceType;
import aries.codegen.TestType;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


/**
 * Builds a DataUnit Manager {@link ModelClass} object given a {@link Unit} Specification as input;
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
public abstract class AbstractDataUnitManagerCITBuilder extends AbstractBeanBuilder {

	public abstract String getBasePackageName(String namespace);

	public abstract String getBaseClassName();

	protected abstract void createMethods_DataAccess(ModelUnit modelUnit) throws Exception;
	
	
	protected AbstractDataUnitManagerCITProvider provider;
	
	protected int testIterationCount;
	
	
	public AbstractDataUnitManagerCITBuilder(GenerationContext context) {
		super(context);
	}
	
	public String getManagerClassName() {
		return getBaseClassName() + "Manager";
	}

	public String getHelperClassName() {
		return getBaseClassName() + "Helper";
	}
	
	public String getFixtureClassName() {
		return getBaseClassName() + "Fixture";
	}
	
	protected void initializeImportedClasses(ModelClass modelClass) throws Exception {
		//modelClass.addImportedClass("java.util.ArrayList");
		modelClass.addImportedClass("java.util.Collection");
		modelClass.addImportedClass("java.util.List");
		//modelClass.addImportedClass("java.util.Set");
		
		//modelClass.addImportedClass("org.aries.tx.TransactionHelper");
		modelClass.addImportedClass("common.tx.state.AbstractStateManager");
		//modelClass.addImportedClass("common.jmx.MBeanUtil");

		//Module module = context.getModule();
		//String namespace = module.getNamespace();
		//String helperClassName = getHelperClassName();
		//String fixtureClassName = getFixtureClassName();
		//String packageName = ProjectLevelHelper.getPackageName(namespace);
		String packageName = TypeUtil.getPackageName(modelClass.getType());

		//modelClass.addImportedClass(packageName + ".util." + helperClassName);
		//modelClass.addImportedClass(packageName + ".util." + fixtureClassName);

		modelClass.addImportedClass("org.junit.Test");
		modelClass.addImportedClass("org.junit.After");
		modelClass.addImportedClass("org.junit.AfterClass");
		modelClass.addImportedClass("org.junit.Before");
		modelClass.addImportedClass("org.junit.BeforeClass");
		modelClass.addImportedClass("org.junit.runner.RunWith");
		
		modelClass.addImportedClass("org.jboss.arquillian.container.test.api.Deployment");
		modelClass.addImportedClass("org.jboss.arquillian.container.test.api.TargetsContainer");
		modelClass.addImportedClass("org.jboss.arquillian.junit.Arquillian");
		modelClass.addImportedClass("org.jboss.arquillian.junit.InSequence");
		modelClass.addImportedClass("org.jboss.shrinkwrap.api.spec.EnterpriseArchive");
		modelClass.addImportedClass("org.jboss.shrinkwrap.api.spec.WebArchive");

		modelClass.addImportedClass("org.aries.Assert");
		modelClass.addImportedClass("org.aries.tx.AbstractArquillianTest");
		modelClass.addImportedClass("org.aries.tx.BytemanRule");
		//modelClass.addImportedClass("org.aries.tx.BytemanUtil");
		modelClass.addImportedClass("org.aries.tx.TransactionTestControl");
		
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			modelClass.addImportedClass("javax.inject.Inject");
			//modelClass.addImportedClass("javax.ejb.Asynchronous");
			//modelClass.addImportedClass("javax.ejb.AccessTimeout");
			break;
			
		case SEAM:
			break;
		}
	}

	protected void initializeClassAnnotations(ModelClass modelClass) throws Exception {
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		classAnnotations.add(AnnotationUtil.createJUnitSuiteRunWithAnnotation("Arquillian"));
	}

	public void addReference_Helper(ModelClass modelClass, String namespace) throws Exception {
		ModelAttribute modelAttribute = createReference_Helper(namespace);
		modelClass.addInstanceAttribute(modelAttribute);
	}

	public ModelAttribute createReference_Helper(String namespace) throws Exception {
		String packageName = getBasePackageName(namespace);
		String className = getBaseClassName() + "Helper";
		String beanName = NameUtil.uncapName(className);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName(beanName);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		attribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
		return attribute;
	}

	public void addReference_Manager(ModelClass modelClass, String namespace) throws Exception {
		ModelAttribute modelAttribute = createReference_Manager(namespace);
		modelClass.addInstanceAttribute(modelAttribute);
	}

	public ModelAttribute createReference_Manager(String namespace) throws Exception {
		String packageName = getBasePackageName(namespace);
		String className = getBaseClassName() + "Manager";
		String beanName = NameUtil.uncapName(className);
		
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		attribute.setClassName(className);
		attribute.setName(beanName);
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		attribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
		return attribute;
	}
	
	protected void createMethod_JUnitBeforeClass(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeClassAnnotation());
		modelOperation.setName("beforeClass");
		modelOperation.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		//buf.putLine2("BytemanUtil.initialize();");
		buf.putLine2("AbstractArquillianTest.beforeClass();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_JUnitAfterClass(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterClassAnnotation());
		modelOperation.setName("afterClass");
		modelOperation.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		Buf buf = new Buf();
		buf.putLine2("AbstractArquillianTest.afterClass();");
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_GetTestClass(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getTestClass");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("Class<?>");
		Buf buf = new Buf();
		buf.putLine2("return "+modelClass.getClassName()+".class;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_GetServerName(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getServerName");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		Buf buf = new Buf();
		buf.putLine2("return \"hornetQ01_local\";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_GetDomainId(ModelClass modelClass) {
		Application application = context.getApplication();
		String domainId = ProjectLevelHelper.getPackageName(application.getNamespace());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getDomainId");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		Buf buf = new Buf();
		buf.putLine2("return \""+domainId+"\";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_GetTargetArchive(ModelClass modelClass) {
		String applicationName = NameUtil.capName(context.getApplication().getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getTargetArchive");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("String");
		Buf buf = new Buf();
		buf.putLine2("return "+applicationName+"TestEARBuilder.NAME;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_GetStateManager(ModelClass modelClass) {
		String beanName = NameUtil.uncapName(getManagerClassName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("getStateManager");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setResultType("AbstractStateManager<?>");
		Buf buf = new Buf();
		buf.putLine2("return "+beanName+";");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_JUnitSetup(ModelClass modelClass) {
		String className = TypeUtil.getClassName(modelClass.getType());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitBeforeAnnotation());
		modelOperation.setName("setUp");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("if (!isServerStarted()) {");
		buf.putLine2("	startServer();");
		buf.putLine2("} else {");
		buf.putLine2("	super.setUp();");
		buf.putLine2("	createTransactionControl();");
		buf.putLine2("	initialize"+className+"Helper();");
		if (DataLayerHelper.isDataUnitRelated(this.getClass()))
			buf.putLine2("	clear"+className+"Context();");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_CreateTransactionTestControl(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("createTransactionControl");
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("transactionTestControl = new TransactionTestControl();");
		buf.putLine2("transactionTestControl.assureTransactionManager();");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}

	protected void createMethod_JUnitTeardown(ModelClass modelClass) {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createJUnitAfterAnnotation());
		modelOperation.setName("tearDown");
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.addException("Exception");
		Buf buf = new Buf();
		buf.putLine2("super.tearDown();");
		buf.putLine2("clearTransaction();");
		if (DataLayerHelper.isDataUnitRelated(this.getClass())) {
			String unitClassName = TypeUtil.getClassName(modelClass.getType());
			buf.putLine2("clear"+unitClassName+"Context();");
		}
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_CreateTestEAR(ModelClass modelClass) {
		String applicationName = NameUtil.capName(context.getApplication().getName());
		String applicationNameUncapped = NameUtil.uncapName(applicationName);
		String containerName = "hornetQ01_local";
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.addAnnotation(AnnotationUtil.createArquillianTargetsContainerAnnotation(containerName));
		modelOperation.addAnnotation(AnnotationUtil.createArquillianDeploymentAnnotation(applicationNameUncapped+"EAR", 1));
		modelOperation.setName("create"+applicationName+"EAR");
		modelOperation.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		modelOperation.setResultType("EnterpriseArchive");
		Buf buf = new Buf();
		buf.putLine2(applicationName+"TestEARBuilder builder = new "+applicationName+"TestEARBuilder();");
		buf.putLine2("builder.addAsModule(create"+applicationName+"WAR(), \""+applicationNameUncapped+"\");");
		buf.putLine2("EnterpriseArchive ear = builder.createEAR();");
		buf.putLine2("return ear;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
		String namespace = context.getApplication().getNamespace();
		String packageName = ProjectLevelHelper.getPackageName(namespace);
		modelClass.addImportedClass(packageName + "." +applicationName + "TestEARBuilder");
		modelClass.addImportedClass(packageName + "." +applicationName + "TestWARBuilder");
	}
	
	protected void createMethod_CreateTestWAR(ModelClass modelClass) {
		String applicationName = NameUtil.capName(context.getApplication().getName());
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setName("create"+applicationName+"WAR");
		modelOperation.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
		modelOperation.setResultType("WebArchive");
		Buf buf = new Buf();
		buf.putLine2(applicationName+"TestWARBuilder builder = new "+applicationName+"TestWARBuilder(\"test.war\");");
		buf.putLine2("builder.addClass("+modelClass.getClassName()+".class);");
		buf.putLine2("WebArchive war = builder.create();");
		buf.putLine2("return war;");
		modelOperation.addInitialSource(buf.get());
		modelClass.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_RunActionAsRunnable(ModelUnit modelUnit) {
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("runAction");
		modelOperation.setModifiers(Modifier.PUBLIC);
		ModelParameter parameter = new ModelParameter();
		parameter.setClassName("Runnable");
		parameter.setName("runnable");
		modelOperation.addParameter(parameter);
		modelOperation.addException("Exception");
		modelOperation.addInitialSource(provider.getSource_RunTestAsRunnable(modelUnit, modelOperation));
		modelUnit.addInstanceOperation(modelOperation);
	}
	
	protected void createMethod_RunActionAsCallable(ModelUnit modelUnit) {
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.addAnnotation(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setName("runAction");
		modelOperation.setModifiers(Modifier.PUBLIC);
		ModelParameter parameter = new ModelParameter();
		parameter.setClassName("Callable<T>");
		parameter.setName("callable");
		modelOperation.addParameter(parameter);
		modelOperation.addException("Exception");
		modelOperation.setResultType("<T> T");
		modelOperation.addInitialSource(provider.getSource_RunTestAsCallable(modelUnit, modelOperation));
		modelUnit.addInstanceOperation(modelOperation);
	}
	
	
	protected <T extends Type> String getOperationName(SourceType sourceType, MethodType methodType, TestType testType, T dataItem) {
		String name = NameUtil.capName(dataItem.getName());
		String structure = dataItem.getStructure();
		String methodName = getDataAccessMethodPrefix(methodType, structure);
		String operationName = "test" + NameUtil.capName(methodName) + name;
		//operationName += getDataAccessMethodSuffix(methodType, structure);
		if (testType.isExecute())
			operationName = "run_" + operationName;
		else operationName = operationName + "_" + testType;
		return operationName;
	}

	protected boolean isParameterRequired(MethodType methodType, TestType testType) {
		return testType != null && testType.isExecute();
	}
	
	protected <T extends Type> void createMethods_DataAccess(ModelUnit modelUnit, T dataItem, SourceType sourceType) throws Exception {
		String structure = dataItem.getStructure();
		if (structure.equals("item")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
			if (this.getClass().equals(CacheUnitManagerBuilder.class))
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
			
		} else if (structure.equals("list") || structure.equals("set")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
			if (this.getClass().equals(CacheUnitManagerBuilder.class))
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));

		} else if (structure.equals("map")) {
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsMap));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Success));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Commit));
			//modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Rollback));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Manager + TestType.Null));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Repository + TestType.Null));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList, TestType.Execute));

			/*
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAllAsMap));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetAsMap));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.GetMatchingAsList));
			if (this.getClass().equals(CacheUnitManagerBuilder.class))
				modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.Set));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsList));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.AddAsMap));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAll));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItem));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsItemByKey));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsListByKeys));
			modelUnit.addInstanceOperation(createOperation_DataAccess(modelUnit, dataItem, sourceType, MethodType.RemoveAsList));
			*/
		}
	}
	
	protected String getSharedStateSource(ModelUnit modelUnit, ModelOperation modelOperation, MethodType methodType, TestType testType, Type item) {
		provider.initialize(item);
		
		modelOperation.addException("Exception");
		if (testType != null && !testType.isException()) {
			testIterationCount++;
			modelOperation.addAnnotation(AnnotationUtil.createJUnitTestAnnotation());
			modelOperation.addAnnotation(AnnotationUtil.createArquillianInSequenceAnnotation(testIterationCount));
		}

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
			return provider.getNothingForNow();
		case AddAsList: 
			if (testType != null)
				return provider.getTestAddAsList(modelUnit, modelOperation, testType);
			return "";
		case RemoveAll: 
			return provider.getNothingForNow();
		case RemoveAsItem:
			return provider.getNothingForNow();
		case RemoveAsItemByKey:
			return provider.getNothingForNow();
		case RemoveAsListByKeys:
			return provider.getNothingForNow();
		case RemoveAsList:
			return provider.getNothingForNow();
		case RemoveMatchingAsList:
			return provider.getNothingForNow();
		default: 
			return "";
		}
	}
	
	protected <T extends Type> String createSource_BeginTransaction(TestType testType) {
		Buf buf1 = new Buf();
		if (testType.isJTATransaction()) {
			buf1.putLine2("beginJTATransaction();");
			
		} else if (testType.isUserTransaction()) {
			buf1.putLine2("beginUserTransaction();");

		} else if (testType.isGlobalTransaction()) {
			buf1.putLine2("beginGlobalTransaction();");
		}
		
		Buf buf = new Buf();
		if (buf1.length() > 0) {
			buf.putLine2("");
			buf.putLine2("// begin transaction");
			buf.put(buf1.get());
		}
		return buf.get();
	}
	
	protected <T extends Type> String createSource_CommitTransaction(TestType testType) {
		Buf buf1 = new Buf();
		if (testType.isJTATransaction()) {
			buf1.putLine2("commitJTATransaction();");
			
		} else if (testType.isUserTransaction()) {
			buf1.putLine2("commitUserTransaction();");

		} else if (testType.isGlobalTransaction()) {
			buf1.putLine2("commitGlobalTransaction();");
		}
		
		Buf buf = new Buf();
		if (buf1.length() > 0) {
			buf.putLine2("");
			buf.putLine2("// commit transaction");
			buf.put(buf1.get());
		}
		return buf.get();
	}

	protected <T extends Type> String createSource_RollbackTransaction(TestType testType) {
		Buf buf1 = new Buf();
		if (testType.isJTATransaction()) {
			buf1.putLine2("rollbackJTATransaction();");
			
		} else if (testType.isUserTransaction()) {
			buf1.putLine2("rollbackUserTransaction();");

		} else if (testType.isGlobalTransaction()) {
			buf1.putLine2("rollbackGlobalTransaction();");
		}
		
		Buf buf = new Buf();
		if (buf1.length() > 0) {
			buf.putLine2("");
			buf.putLine2("// rollback transaction");
			buf.put(buf1.get());
		}
		return buf.get();
	}
	
}
