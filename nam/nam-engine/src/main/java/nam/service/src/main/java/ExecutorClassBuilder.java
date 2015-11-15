package nam.service.src.main.java;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nam.model.Application;
import nam.model.Element;
import nam.model.Module;
import nam.model.Process;
import nam.model.util.ProcessUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.ModelOperationFactory;
import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelAnnotation;
import aries.generation.model.ModelAttribute;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


/**
 * Builds an Executor Implementation {@link ModelClass} object given a {@link Process} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ExecutorClassBuilder extends AbstractBeanBuilder {

	private ExecutorClassProvider executorClassProvider;
	
	private Process process;
	
	private ModelClass modelClass;

	private ModelOperationFactory modelOperationFactory;
	
	private Set<String> serviceCompleteOperationsDone;
	
	//private String serviceInterface;

	
	public ExecutorClassBuilder(GenerationContext context) {
		executorClassProvider = new ExecutorClassProvider(context);
		this.context = context;
	}

	public List<ModelClass> buildClasses(Process process) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Set<String> events = ProcessUtil.getIncomingEvents(process);
		Iterator<String> iterator = events.iterator();
		while (iterator.hasNext()) {
			String eventName = iterator.next();
			String baseName = NameUtil.capName(eventName);
			if (baseName.endsWith("Event"))
				baseName = eventName.substring(eventName.length() - 5);
			if (!eventName.endsWith("Event"))
				eventName += "Event";
			//Element element = context.getElementByName(eventName);
			modelClasses.add(buildClass(process, baseName));
		}
		return modelClasses;
	}
	
	public ModelClass buildClass(Process process, String baseName) throws Exception {
		this.process = process;
		modelOperationFactory = new ModelOperationFactory(context);
		serviceCompleteOperationsDone = new HashSet<String>();

		String eventName = baseName + "Event";
		String executorName = baseName + "Executor";
		String packageName = ServiceLayerHelper.getExecutorPackageName(process);
		String className = ServiceLayerHelper.getExecutorClassName(executorName);
		String typeName = ServiceLayerHelper.getExecutorType(process, executorName);
		String beanName = NameUtil.uncapName(className);
		
//		if (processName.equalsIgnoreCase("SellerProcess"))
//			System.out.println();
		
		setBaseName(baseName);
		setBeanName(beanName);
		setPackageName(packageName);
		setClassName(className);
		modelClass = new ModelClass();
		modelClass.setType(typeName);
		modelClass.setPackageName(packageName);
		modelClass.setClassName(className);
		modelClass.setName(beanName);
		String eventClassName = NameUtil.capName(eventName);
		modelClass.setParentClassName("AbstractEventExecutor<"+eventClassName+">");
		modelClass.addImportedClass("org.aries.event.executor.AbstractEventExecutor");
		//modelClass.addImplementedInterface(className+"MBean");

//		Element eventElement = context.getElementByName(eventName);
//		Assert.notNull(eventElement, "Element not found: "+eventName);
//		modelClass.addImportedClass(eventElement);
		
		modelOperationFactory.reset(modelClass);
		initializeClass(modelClass, process, baseName);
		return modelClass; 
	}

	protected void initializeClass(ModelClass modelClass, Process process, String baseName) throws Exception {
		initializeImportedClasses(modelClass, process);
		initializeClassAnnotations(modelClass, process);
		//initializeClassConstructors(modelClass, process);
		initializeInstanceFields(modelClass, process);
		initializeInstanceOperations(modelClass, process, baseName);
		//modelClass.addImportedClass("org.aries.runtime.BeanContext");
		//modelClass.addImportedClass("org.aries.process.ProxyLocator");
	}
	
	protected void initializeImportedClasses(ModelClass modelClass, Process process) {
		modelClass.addImportedClass(ServiceLayerHelper.getProcessQualifiedName(process));
		//modelClass.addImportedClass("java.util.HashMap");
		//modelClass.addImportedClass("java.util.Iterator");
		//modelClass.addImportedClass("java.util.List");
		//modelClass.addImportedClass("java.util.Map");
		
		modelClass.addImportedClass("org.aries.jndi.JndiName");
		modelClass.addImportedClass("org.aries.runtime.BeanLocator");
		modelClass.addImportedClass("org.aries.util.ExceptionUtil");
		//modelClass.addImportedClass("common.jmx.MBeanUtil");
		modelClass.addImportedClass("java.util.TimerTask");

		switch (context.getServiceLayerBeanType()) {
		case EJB:
			modelClass.addImportedClass("javax.inject.Inject");
			modelClass.addImportedClass("javax.ejb.LocalBean");
			modelClass.addImportedClass("javax.ejb.Stateful");
			modelClass.addImportedClass("javax.ejb.Asynchronous");
			modelClass.addImportedClass("javax.ejb.AccessTimeout");
			modelClass.addImportedClass("javax.ejb.TransactionAttribute");
			modelClass.addImportedClass("javax.annotation.PostConstruct");
			//modelClass.addImportedClass("javax.annotation.PreDestroy");
			modelClass.addImportedClass("javax.enterprise.event.Observes");
			modelClass.addStaticImportedClass("javax.ejb.TransactionAttributeType.REQUIRED");
			break;
		}
	}
	
	protected void initializeClassAnnotations(ModelClass modelClass, Process process) throws Exception {
		String className = modelClass.getClassName();
		List<ModelAnnotation> classAnnotations = modelClass.getClassAnnotations();
		switch (context.getServiceLayerBeanType()) {
		case EJB:
			classAnnotations.add(AnnotationUtil.createStatefulAnnotation());
			classAnnotations.add(AnnotationUtil.createLocalBeanAnnotation());
			break;
		}
	}
	
	protected void initializeInstanceFields(ModelClass modelClass, Process process) throws Exception {
		addEventMulticasterField(modelClass, process);
		addEventIdField(modelClass);
		addTimeoutField(modelClass);
	}
	
	public static void addEventMulticasterField(ModelClass modelClass, Process process) {
		modelClass.addInstanceAttribute(createEventMulticasterField(process));
		//modelClass.addImportedClass("org.aries.event.multicaster.EventMulticaster");
	}
	
	public static ModelAttribute createEventMulticasterField(Process process) {
		String packageName = ServiceLayerHelper.getExecutorPackageName(process);
		String rootName = ServiceLayerHelper.getProcessRootName(process);
		ModelAttribute attribute = new ModelAttribute();
		attribute.addAnnotation(AnnotationUtil.createInjectAnnotation());
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName(packageName);
		//attribute.setPackageName("org.aries.event.multicaster");
		attribute.setClassName(rootName+"EventMulticaster");
		attribute.setName("eventMulticaster");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public static void addEventIdField(ModelClass modelClass) {
		modelClass.addInstanceAttribute(createEventIdField());
	}
	
	public static ModelAttribute createEventIdField() {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("java.lang");
		attribute.setClassName("String");
		attribute.setName("eventId");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	public static void addTimeoutField(ModelClass modelClass) {
		modelClass.addInstanceAttribute(createTimeoutField());
	}
	
	public static ModelAttribute createTimeoutField() {
		ModelAttribute attribute = new ModelAttribute();
		attribute.setModifiers(Modifier.PRIVATE);
		attribute.setPackageName("java.lang");
		attribute.setClassName("long");
		attribute.setName("timeout");
		attribute.setGenerateGetter(false);
		attribute.setGenerateSetter(false);
		return attribute;
	}
	
	protected void initializeInstanceOperations(ModelClass modelClass, Process process, String baseName) throws Exception {
		modelClass.addInstanceOperation(createOperation_PostConstruct(process));
		//modelClass.addInstanceOperation(createOperation_PreDestroy(process));
		//modelClass.addInstanceOperation(createOperation_RegisterWithJMX(process));
		//modelClass.addInstanceOperation(createOperation_UnregisterWithJMX(process));
		modelClass.addInstanceOperation(createOperation_GetEventId(process, baseName));
		modelClass.addInstanceOperation(createOperation_GetProcess(process));
		modelClass.addInstanceOperation(createOperation_GetProcessJndiName(process));
		modelClass.addInstanceOperation(createOperation_Initialize(process));
		modelClass.addInstanceOperation(createOperation_Register(process));
		modelClass.addInstanceOperation(createOperation_Cancel(process, baseName));
		modelClass.addInstanceOperation(createOperation_Reset(process, baseName));
		modelClass.addInstanceOperation(createOperation_Process(process, baseName));
		modelClass.addInstanceOperation(createOperation_CreateTimeoutHandler(process, baseName));
	}
	
	protected ModelOperation createOperation_PostConstruct(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createPostConstructAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("postConstruct");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		//buf.putLine2("registerWithJMX();");
		buf.putLine2("initialize();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_PreDestroy(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createPreDestroyAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("preDestroy");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("unregisterWithJMX();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_RegisterWithJMX(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("registerWithJMX");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("MBeanUtil.registerMBean(this, MBEAN_NAME);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_UnregisterWithJMX(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("unregisterWithJMX");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("MBeanUtil.unregisterMBean(MBEAN_NAME);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}

	protected ModelOperation createOperation_GetEventId(Process process, String baseName) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("getEventId");
		modelOperation.setResultType("String");
		//modelOperation.setResultType("Class<"+baseName+"Event>");

		Buf buf = new Buf();
		buf.putLine2("return "+baseName+"Event.class.getName();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_GetProcess(Process process) throws Exception {
		String processClassName = ServiceLayerHelper.getProcessClassName(process);
		ModelOperation modelOperation = new ModelOperation();
		//modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("getProcess");
		modelOperation.setResultType(processClassName);

		Buf buf = new Buf();
		buf.putLine2("String jndiName = getProcessJndiName();");
		buf.putLine2("return BeanLocator.lookup("+processClassName+".class, jndiName);");
		//buf.putLine2("return BeanLocator.lookup("+processClassName+".class);");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_GetProcessJndiName(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("getProcessJndiName");
		modelOperation.setResultType("String");

		Application application = context.getApplication();
		Module module = context.getModule();
		String applicationName = application.getArtifactId();
		String moduleName = module.getName();
		String moduleVersion = module.getVersion();
		String beanName = process.getName();
		
		Buf buf = new Buf();
		buf.putLine2("JndiName jndiName = new JndiName();");
		buf.putLine2("jndiName.setApplicationName(\""+applicationName+"\");");
		buf.putLine2("jndiName.setModuleName(\""+moduleName+"-"+moduleVersion+"\");");
		buf.putLine2("jndiName.setBeanName(\""+beanName+"\");");
		buf.putLine2("return jndiName.toString();");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Initialize(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("initialize");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("shutdownTimer();");
		buf.putLine2("eventId = getEventId();");
		buf.putLine2("timeout = DEFAULT_TIMEOUT;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Register(Process process) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("register");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("shutdownTimer();");
		buf.putLine2("createTimer(createTimeoutHandler(), timeout);");
		buf.putLine2("eventMulticaster.addEventListener(eventId, this);");
		//buf.putLine2("TimerTask timeoutHandler = createTimeoutHandler();");
		//buf.putLine2("createTimer(timeoutHandler);");
		//buf.putLine2("addEventListener();");
		buf.putLine2("log.info(\"registered\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Cancel(Process process, String baseName) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("cancel");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("close();");
		//buf.putLine2("removeEventListener(this);");
		buf.putLine2("eventMulticaster.removeEventListener(eventId, this);");
		buf.putLine2("String reason = \""+baseName+" execution cancelled\";");
		buf.putLine2("getProcess().handle_"+baseName+"_event_cancel(reason);");
		buf.putLine2("log.info(\"cancelled\");");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Reset(Process process, String baseName) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("reset");
		modelOperation.setResultType("void");

		Buf buf = new Buf();
		buf.putLine2("close();");
		buf.putLine2("timeout = DEFAULT_TIMEOUT;");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_Process(Process process, String baseName) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.getAnnotations().add(AnnotationUtil.createOverrideAnnotation());
		modelOperation.getAnnotations().add(AnnotationUtil.createAsynchronousAnnotation());
		modelOperation.getAnnotations().add(AnnotationUtil.createAccessTimeoutAnnotation(60000));
		modelOperation.getAnnotations().add(AnnotationUtil.createTransactionAttributeAnnotation());
		modelOperation.setModifiers(Modifier.PUBLIC);
		modelOperation.setName("process");
		modelOperation.setResultType("void");

		Element eventElement = context.getElementByName(baseName+"Event");
		Assert.notNull(eventElement, "Element not found: "+baseName+"Event");
		String eventPackageName = TypeUtil.getPackageName(eventElement.getType());
		String eventClassName = TypeUtil.getClassName(eventElement.getType());
		modelClass.addImportedClass(eventElement);
		
		ModelParameter modelParameter = new ModelParameter();
		modelParameter.getAnnotations().add(AnnotationUtil.createObservesAnnotation());
		modelParameter.setName("event");
		modelParameter.setClassName(eventClassName);
		modelParameter.setPackageName(eventPackageName);
		modelOperation.addParameter(modelParameter);
		
		Buf buf = new Buf();
		buf.putLine2("log.info(\"invoked\");");
		buf.putLine2("shutdownTimer();");
		buf.putLine2("");
		buf.putLine2("if (isClosed())");
		buf.putLine2("	return;");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	getProcess().handle_"+baseName+"_event(event);");
		buf.putLine2("	log.info(\"processed\");");
		buf.putLine2("");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.info(\"failed\");");
		buf.putLine2("	e = ExceptionUtil.getRootCause(e);");
		buf.putLine2("	getProcess().handle_"+baseName+"_event_exception(e);");
		buf.putLine2("");
		buf.putLine2("} finally {");
		//buf.putLine2("	removeEventListener();");
		buf.putLine2("	eventMulticaster.removeEventListener(eventId, this);");
		buf.putLine2("	reset();");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
	protected ModelOperation createOperation_CreateTimeoutHandler(Process process, String baseName) throws Exception {
		ModelOperation modelOperation = new ModelOperation();
		modelOperation.setModifiers(Modifier.PROTECTED);
		modelOperation.setName("createTimeoutHandler");
		modelOperation.setResultType("TimerTask");

		Buf buf = new Buf();
		buf.putLine2("return new TimerTask() {");
		buf.putLine2("	public void run() {");
		buf.putLine2("		reset();");
		buf.putLine2("		log.info(\"timed-out\");");
		buf.putLine2("		String reason = \""+baseName+" timed-out\";");
		buf.putLine2("		getProcess().handle_"+baseName+"_event_timeout(reason);");
		buf.putLine2("	}");
		buf.putLine2("};");
		modelOperation.addInitialSource(buf.get());
		return modelOperation;
	}
	
}
