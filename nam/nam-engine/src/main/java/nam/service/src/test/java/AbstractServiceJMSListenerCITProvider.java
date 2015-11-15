package nam.service.src.test.java;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.model.Callback;
import nam.model.Channel;
import nam.model.Operation;
import nam.model.Process;
import nam.model.Service;
import nam.model.Type;
import nam.model.Unit;
import nam.model.util.ProcessUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.AbstractBeanBuilder;
import aries.codegen.TestType;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.AnnotationUtil;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;
import aries.generation.model.ModelUnit;


/**
 * Provides the source code for a DataUnit Manager CIT {@link ModelClass} object given a {@link Unit} Specification as input;
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
public abstract class AbstractServiceJMSListenerCITProvider extends AbstractBeanBuilder {

	protected Type item;
	protected String type;
	protected String keyType;
	protected String paramName;
	protected String keyParamName;
	protected String keyTypeName;
	protected String typeName;
	protected String fieldName;
	protected String instanceName;
	protected String structure;
	protected String contextName;
	protected String helperName;
	protected String baseName;
	protected String baseNameUncapped;
	protected String modelFixtureName;
	protected String modelHelperName;

	
	public AbstractServiceJMSListenerCITProvider(GenerationContext context) {
		super(context);
	}
	
	protected void initialize(Type item) {
		this.item = item;
		type = item.getType();
		keyType = item.getKey();
		paramName = TypeUtil.getLocalPart(type);
		keyParamName = null;
		keyTypeName = null;
		if (keyType != null) {
			keyParamName = TypeUtil.getLocalPart(keyType);
			keyTypeName = NameUtil.capName(keyParamName);
		}
		typeName = NameUtil.capName(paramName);
		fieldName = NameUtil.capName(item.getName());
		instanceName = NameUtil.uncapName(fieldName);

		context.getUnit().getName();
		structure = item.getStructure();
		String groupId = context.getModule().getGroupId();
		modelFixtureName = NameUtil.capName(groupId) + "Fixture";
		modelHelperName = NameUtil.capName(groupId) + "Helper";
		contextName = ServiceLayerHelper.getProcessContextInstanceName(context.getProcess());
		baseNameUncapped = NameUtil.uncapName(baseName);
		helperName = baseNameUncapped + "Helper";
	}

	
	public String getNothingForNow() {
		return "// nothing for now";
	}

	public String getTestAddAsList(ModelUnit modelUnit, ModelOperation modelOperation, TestType testType) {
		if (testType.isExecute()) 
			return getExecuteTestAddAsList(modelUnit, modelOperation);
		if (testType.isSuccess()) 
			return getTestAddAsList_Success(modelUnit, modelOperation);
		if (testType.isCommit()) 
			return getTestAddAsList_TxCommit(modelUnit, modelOperation);
		if (testType.isManager() && testType.isNull()) 
			return getTestAddAsList_ManagerNull(modelUnit, modelOperation);
		if (testType.isRepository() && testType.isNull()) 
			return getTestAddAsList_RepositoryNull(modelUnit, modelOperation);
		return "";
	}

	protected String getTestAddAsList(ModelUnit modelUnit, ModelOperation modelOperation) {
		Buf buf = new Buf();
		return buf.get();
	}
	
	protected String getTestAddAsList_Success(ModelUnit modelUnit, ModelOperation modelOperation) {
		Buf buf = new Buf();
		buf.putLine2("String testName = \"testAddTo"+fieldName+"_Success\";");
		buf.putLine2("log.info(testName+\": started\");");
		buf.putLine2(helperName+".assureRemoveAll();");
		buf.putLine2("List<"+typeName+"> "+paramName+"ListToAdd = "+modelFixtureName+".create"+typeName+"_List();");
		buf.putLine2("run_testAddTo"+fieldName+"("+paramName+"ListToAdd);");
		buf.putLine2("List<"+typeName+"> "+paramName+"List = "+helperName+".getAll"+fieldName+"Records();");
		buf.putLine2(modelHelperName+".assert"+typeName+"ListEquals("+paramName+"ListToAdd, "+paramName+"List);");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		return buf.get();
	}

	protected String getTestAddAsList_TxCommit(ModelUnit modelUnit, ModelOperation modelOperation) {
		Buf buf = new Buf();
		buf.putLine2("String testName = \"testAddTo"+fieldName+"_TxCommit\";");
		buf.putLine2("log.info(testName+\": started\");");
		buf.putLine2(helperName+".assureRemoveAll();");
		buf.putLine2("List<"+typeName+"> "+paramName+"ListToAdd = "+modelFixtureName+".create"+typeName+"_List();");
		
		//indicate test should run in a UserTransaction
		buf.putLine2("isUserTransaction = true;");
		
		buf.putLine2("run_testAddTo"+fieldName+"("+paramName+"ListToAdd);");
		buf.putLine2("List<"+typeName+"> "+paramName+"List = "+helperName+".getAll"+fieldName+"Records();");
		buf.putLine2(modelHelperName+".assert"+typeName+"ListEquals("+paramName+"ListToAdd, "+paramName+"List);");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		return buf.get();
	}

	protected String getTestAddAsList_ManagerNull(ModelUnit modelUnit, ModelOperation modelOperation) {
		int index = 3;
		String targetClass = baseName + "Manager";
		String targetMethod = "addTo"+fieldName;
		String targetLocation = "AT EXIT";
		String action = "throw new java.lang.RuntimeException(\\\"error"+index+"\\\")";
		modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(targetClass, targetMethod, targetLocation, action, index));

		Buf buf = new Buf();
		buf.putLine2("String testName = \"testAddTo"+fieldName+"_ManagerNull\";");
		buf.putLine2("log.info(testName+\": started\");");

		//setup for Byteman
		buf.putLine2("setupByteman(testName);");
		
		buf.putLine2(helperName+".assureRemoveAll();");
		//buf.putLine2(instanceName+"Manager.set"+fieldName+"(null);");//???
		buf.putLine2("List<"+typeName+"> "+paramName+"ListToAdd = "+modelFixtureName+".create"+typeName+"_List();");
		
		//indicate test should is expected to rollback
		buf.putLine2("isRollbackExpected = true;");
		
		buf.putLine2("run_testAddTo"+fieldName+"("+paramName+"ListToAdd);");
		buf.putLine2("List<"+typeName+"> "+paramName+"List = "+helperName+".getAll"+fieldName+"Records();");
		buf.putLine2("Assert.isEmpty("+paramName+"List, \""+fieldName+" should be empty\");");
		buf.putLine2("log.info(testName+\": done\");");
		buf.putLine2("tearDownByteman(testName);");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		return buf.get();
	}

	protected String getTestAddAsList_RepositoryNull(ModelUnit modelUnit, ModelOperation modelOperation) {
		int index = 4;
		String targetClass = baseName + "Repository";
		String targetMethod = "add"+fieldName+"Records";
		String targetLocation = "AT EXIT";
		String action = "throw new java.lang.RuntimeException(\\\"error"+index+"\\\")";
		modelOperation.addAnnotation(AnnotationUtil.createByteManRuleAnnotation(targetClass, targetMethod, targetLocation, action, index));

		Buf buf = new Buf();
		buf.putLine2("String testName = \"testAddTo"+fieldName+"_RepositoryNull\";");
		buf.putLine2("log.info(testName+\": started\");");

		//setup Byteman
		buf.putLine2("setupByteman(testName);");
		
		buf.putLine2(helperName+".assureRemoveAll();");
		buf.putLine2("List<"+typeName+"> "+paramName+"ListToAdd = "+modelFixtureName+".create"+typeName+"_List();");
		
		//indicate test should is expected to rollback
		buf.putLine2("isRollbackExpected = true;");
		
		buf.putLine2("run_testAddTo"+fieldName+"("+paramName+"ListToAdd);");
		buf.putLine2("List<"+typeName+"> "+paramName+"List = "+helperName+".getAll"+fieldName+"Records();");
		buf.putLine2("Assert.isEmpty("+paramName+"List, \""+fieldName+" should be empty\");");
		buf.putLine2("log.info(testName+\": done\");");

		//teardown Byteman
		buf.putLine2("tearDownByteman(testName);");
		buf.putLine2("if (errorMessage != null)");
		buf.putLine2("	fail(errorMessage);");
		return buf.get();
	}
	
	protected String getExecuteTestAddAsList(ModelUnit modelUnit, ModelOperation modelOperation) {
		List<ModelParameter> parameters = modelOperation.getParameters();
		Iterator<ModelParameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			ModelParameter modelParameter = iterator.next();
			modelParameter.setFinal(true);
		}
		Buf buf = new Buf();
		buf.putLine2("runTest(new Runnable() {");
		buf.putLine2("	public void run() {");
		buf.putLine2("		"+baseNameUncapped+"Manager.addTo"+fieldName+"("+paramName+"List);");
		buf.putLine2("	}");
		buf.putLine2("});");
		return buf.get();
	}

	protected String getRunTest(ModelUnit modelUnit, ModelOperation modelOperation, Service service) {
		String serviceNameCapped = ServiceLayerHelper.getServiceNameCapped(service);
		String serviceNameUncapped = ServiceLayerHelper.getServiceNameUncapped(service);
		Operation operation = ServiceUtil.getDefaultOperation(service);
		String messageBeanName = AbstractListenerTestBuilder.getMessageBeanName(operation);
		
		List<Channel> channels = ServiceUtil.getChannels(service);
		Collection<Service> remoteServices = ServiceUtil.getRemoteServices(context.getProject(), service);
		List<Callback> outgoingCallbacks = ServiceUtil.getDistinctOutgoingCallbacks(service);
		
		Buf buf = new Buf();
		buf.putLine2("this."+messageBeanName+" = "+messageBeanName+";");
		buf.putLine2("");
		buf.putLine2("// prepare the environment");
		buf.putLine2("removeMessagesFromDestinations();");
		buf.putLine2("");
		buf.putLine2("// prepare context");
		buf.putLine2("//beginTransaction();");
		buf.putLine2("");
		buf.putLine2("// prepare mocks");
		buf.putLine2("registerForResult();");

		if (!remoteServices.isEmpty()) {
			buf.putLine2("");
			buf.putLine2("// start handlers for calls to remote service(s)");
			Iterator<Service> iterator = remoteServices.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				Service remoteService = iterator.next();
				String serviceName = ServiceUtil.getDomainServiceName(remoteService);
				String extendedName = ServiceUtil.getExtendedDomainServiceName(remoteService);
				buf.putLine2("remote"+serviceName+"Handler = start_remote_"+extendedName+"_handler();");
				//buf.putLine2("remote"+serviceInterfaceName+"Handler = startRemote"+serviceInterfaceName+"MessageHandler();");
			}
		}
		
		if (!outgoingCallbacks.isEmpty()) {
			buf.putLine2("");
			buf.putLine2("// start local handlers for responses from target service");
			Iterator<Callback> iterator2 = outgoingCallbacks.iterator();
			while (iterator2.hasNext()) {
				Callback callback = iterator2.next();
				String callbackName = ServiceUtil.getDomainServiceName(callback);
				String extendedName = ServiceUtil.getExtendedDomainServiceName(callback);
				buf.putLine2("local"+callbackName+"Handler = start_local_"+extendedName+"_handler();");
				//buf.putLine2("local"+serviceInterfaceName+"Handler = startLocal"+serviceInterfaceName+"ResponseHandler();");
			}
		}
		
		if (ServiceUtil.isSynchronous(service)) {
			buf.putLine2("");
			buf.putLine2("Message message = new Message();");
			buf.putLine2("message.addPart(\""+messageBeanName+"\", "+messageBeanName+");");
			buf.putLine2("message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, \""+serviceNameUncapped+"\");");
			modelUnit.addImportedClass("org.aries.message.Message");
			modelUnit.addImportedClass("org.aries.message.util.MessageConstants");
		}
		
		buf.putLine2("");
		buf.putLine2("// start fixture execution");
		buf.putLine2(serviceNameUncapped+"Client = start_"+serviceNameCapped+"_client();");
		buf.putLine2(serviceNameUncapped+"Client.send("+messageBeanName+", correlationId, null);");
		
		Process process = service.getProcess();
		if (ServiceUtil.isStateful(service) && ProcessUtil.hasIncomingEvents(process)) {
			buf.putLine2("");
			buf.putLine2("// fire events if needed");
			buf.putLine2("if (eventsExist()) {");
			buf.putLine2("	Thread.sleep(2000);");
			buf.putLine2("	fireEvents();");
			buf.putLine2("}");
		}
		
		buf.putLine2("");
		buf.putLine2("// wait for completion result");
		buf.putLine2("Object result = waitForCompletion();");
		buf.putLine2("validateResult(result);");
		buf.putLine2("");
		buf.putLine2("// close context");
		buf.putLine2("//commitTransaction();");
		buf.putLine2("");
		buf.putLine2("// validate state");
		buf.putLine2("assertEmptyTargetDestination();");
		//buf.putLine2("");
		//buf.putLine2("// cleanup");
		//buf.putLine2("removeMessagesFromDestinations();");
		return buf.get();
	}

	protected String getRunTest_FOR_FUTURE(ModelUnit modelUnit, ModelOperation modelOperation) {
		Buf buf = new Buf();
		buf.putLine2("if (isTransactional())");
		buf.putLine2("	beginTransaction();");
		buf.putLine2("");
		buf.putLine2("try {");
		buf.putLine2("	runnable.run();");
		buf.putLine2("	if (isRollbackExpected)");
		buf.putLine2("		fail(\"Exception should have been thrown\");");
		buf.putLine2("	if (isTransactional())");
		buf.putLine2("		commitTransaction();");
		buf.putLine3("");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	if (isTransactional())");
		buf.putLine2("		rollbackTransaction();");
		buf.putLine2("}");
		return buf.get();
	}

}
