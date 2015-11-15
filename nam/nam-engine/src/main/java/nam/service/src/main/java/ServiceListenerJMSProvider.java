package nam.service.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.model.Callback;
import nam.model.Element;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.ParameterUtil;
import nam.model.util.ServiceUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public class ServiceListenerJMSProvider extends AbstractServiceListenerProvider {

	public ServiceListenerJMSProvider(GenerationContext context) {
		super(context);
	}

	public String generate(ModelClass modelClass, Service service, Operation operation) throws Exception {
		if (ServiceUtil.isStateful(service))
			return generateImplementation_Stateful(modelClass, service, operation);
		return generateImplementation_Stateless(modelClass, service, operation);
	}


	public String generateSourceCode_ModelOperation_EJBInvocation(Operation operation, Service service) {
		String operationNameCapped = NameUtil.capName(operation.getName());
		//String initializerName = service.getDomain() + ".initializer";
		String applicationName = context.getApplication().getName();
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String serviceName = ServiceLayerHelper.getServiceNameUncapped(service);
		String handlerInstanceName = ServiceLayerHelper.getHandlerInstanceName(service);
		String handlerClassName = ServiceLayerHelper.getHandlerClassName(service);
		boolean isOneway = CodeUtil.isOneway(operation);
		
		Buf buf = new Buf();
		//buf.putLine2("System.out.println(\"#### "+applicationName+": ("+interfaceName+")\");");
		buf.putLine2("if (!isInitialized(\""+service.getDomain()+"\"))");
		//buf.putLine2("Initializer initializer = BeanContext.get(\""+initializerName+"\");");
		//buf.putLine2("if (initializer == null)");
		buf.putLine2("	return;");
		buf.putLine2("");
		
		//buf.putLine2("Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();");
		//buf.putLine2("String transactionId = transactionKey.toString();");
		//buf.putLine2("");
		//buf.putLine2("//TransactionHelper transactionHelper = new TransactionHelper();");
		//buf.putLine2("//transactionHelper.open();");
		//buf.putLine2("");
		
		buf.putLine2("try {");
		//buf.putLine2("	//logStarted();");
		//buf.putLine2("	String correlationId = getCorrelationIdFromMessage(message);");

		String operationName = operation.getName();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String className = TypeUtil.getClassName(parameter.getType());
			String instanceName = NameUtil.uncapName(className);
			buf.putLine2("	"+className+" "+instanceName+" = MessageUtil.getPayloadFromMessage(message);");
		}

		Process process = service.getProcess();
		String processContextInstanceName = ServiceLayerHelper.getProcessContextInstanceName(process);
		
		String parameterString = ParameterUtil.getArgumentString(operation);
		if (process != null) {
			buf.putLine2("	"+processContextInstanceName+".validate("+parameterString+");");
			//buf.putLine3("");
		}
		
		//buf.put(CodeUtil.createCheckpointsForParameters(3, operation));
		//buf.putLine3("");

		//buf.putLine3(handlerInstanceName+".setCorrelationId(correlationId);");
		//buf.putLine3(handlerInstanceName+".setTransactionId(transactionId);");
		buf.putLine3(handlerInstanceName+"."+operationName+"("+parameterString+");");
		//buf.putLine3("log.info(\""+interfaceName+": received\");");
		buf.putLine3("");
		
		//TODO
		if (!isOneway) {
			Result result = operation.getResults().get(0);
			String resultName = NameUtil.uncapName(result.getName());
			String resultNameCapped = NameUtil.capName(result.getName());
			buf.putLine2("	Assert.notNull("+resultName+", \""+resultNameCapped+" must exist\");");
			buf.putLine2("	message.addPart(\""+resultName+"\", "+resultName+");");
		}

		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.error(e);");
		//buf.putLine2("	logAborted();");
		
		if (process != null) {
			String mainAction = context.getService() instanceof Callback ? "response" : "request";
			String mainServiceName = ServiceLayerHelper.getServiceInterfaceName(context.getService());
			buf.putLine2("	"+processContextInstanceName+".fire_"+mainServiceName+"_incoming_"+mainAction+"_aborted(e);");
			//buf.putLine2("	"+processContextInstanceName+".fire_"+mainServiceName+"_"+mainAction+"Complete();");
		}
		
		//buf.putLine2("	ExceptionUtil.rethrow(e);");
		//buf.putLine2("	//propagate only message and stack trace, 	not any underlying exception(s) classes.");
		//buf.putLine2("	e = InfosgiExceptionUtil.createInfosgiException(e);");
		//buf.putLine2("	throw e;");
		buf.putLine2("}");
		return buf.get();
	}
	
	
	
	public String generateImplementation_Stateless(ModelClass modelClass, Service service, Operation operation) throws Exception {
		//String namespace = ServiceUtil.getNamespace(service);
		//String packageName = ServiceUtil.getPackageName(service);
		//String interfaceName = ServiceUtil.getInterfaceName(service);
		//String className = ServiceUtil.getClassName(service);
		//String qualifiedName = packageName + "." + className;
		//String rootName = ServiceUtil.getRootName(service);
		//String beanName = NameUtil.capName(className);
		
		String serviceName = service.getName();
		String serviceNameCapped = NameUtil.capName(serviceName);
		//String actionPackageName = context.getPackageName(service);
		//String actionClassName = service.getInterfaceName() + "Action";
		//String contextName = NameUtil.uncapName(service.getName()) + "." + NameUtil.uncapName(actionClassName);
		//String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, 2);
		String actionContextName = ServiceLayerHelper.getActionContextName(service);
		
		String operationName = operation.getName();
		String actionName = serviceNameCapped + "Action";
		boolean isOneway = CodeUtil.isOneway(operation);

		if (context.isEnabled("generateServicePerOperation"))
			actionName = NameUtil.capName(operationName) + "Action";
		//String actionNameUncapped = NameUtil.uncapName(actionName);
			
		Buf buf = new Buf();
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String checkpointName = parameter.getName();
			//if (checkpointName.endsWith("Message"))
			//	checkpointName = checkpointName.substring(0, checkpointName.length()-7);
			buf.putLine2("Check.isValid(\""+checkpointName+"\", "+parameterName+");");
		}
		
		buf.putLine2("try {");
		buf.putLine2("	//logStarted();");
		buf.putLine2("	"+actionName+" action = BeanContext.get(\""+actionContextName+"\");");
		
//		Iterator<Parameter> iterator = parameters.iterator();
//		for (int i=0; iterator.hasNext(); i++) {
//			Parameter parameter = iterator.next();
//			String parameterName = parameter.getName();
//			String parameterNameCapped = NameUtil.capName(parameterName);
//			String parameterClassName = TypeUtil.getClassName(parameter.getType());
//			String parameterQualifiedName = TypeUtil.getJavaName(parameter.getType());
//			if (!ClassUtil.isJavaDefaultType(parameterClassName))
//				modelClass.addImportedClass(parameterQualifiedName);
//		}

		modelClass.addImportedClass("org.aries.Assert");
		
		if (!isOneway) {
			Result result = operation.getResults().get(0);
			String resultName = result.getName();
			String resultClassName = TypeUtil.getClassName(result.getType());
			String resultQualifiedName = TypeUtil.getJavaName(result.getType());
			String construct = result.getConstruct();
			if (construct.equals("list")) {
				resultClassName = "List<"+resultClassName+">";
			} else if (construct.equals("set")) {
				resultClassName = "Set<"+resultClassName+">";
			} else if (construct.equals("map")) {
				resultClassName = "Map<Object, "+resultClassName+">";
			}
			
			buf.put3(resultClassName+" "+resultName+" = action."+operationName+"(");
			if (!ClassUtil.isJavaDefaultType(resultClassName))
				modelClass.addImportedClass(resultQualifiedName);
		} else {
			buf.put3("action."+operationName+"(");
		}

		iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(parameter.getName());
			String parameterClassName = TypeUtil.getClassName(parameter.getType());
			String parameterQualifiedName = TypeUtil.getJavaName(parameter.getType());
			if (!ClassUtil.isJavaDefaultType(parameterClassName))
				modelClass.addImportedClass(parameterQualifiedName);
		}

		buf.putLine(");");
		if (!isOneway) {
			Result result = operation.getResults().get(0);
			String resultName = result.getName();
			String resultNameCapped = NameUtil.capName(resultName);
			buf.putLine3("Assert.notNull("+resultName+", \""+resultNameCapped+" not found for "+resultNameCapped+": \"+"+resultName+");");
		}

		buf.putLine3("//logComplete();");
		if (!isOneway) {
			Result result = operation.getResults().get(0);
			String resultName = result.getName();
			buf.putLine3("return "+resultName+";");
		}

		buf.putLine2("");	
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	//logAborted();");
		buf.putLine2("	//propagate only message and stack trace, not any underlying exception(s) classes.");
		//buf.putLine2("	e = InfosgiExceptionUtil.createInfosgiException(e);");
		buf.putLine2("	e = ExceptionUtil.rewrap(e);");
		buf.putLine2("	throw e;");
		buf.putLine2("}");
		return buf.get();
	}

	public String generateImplementation_Stateful(ModelClass modelClass, Service service, Operation operation) throws Exception {
		String serviceName = NameUtil.capName(service.getName());
		List<Parameter> parameters = operation.getParameters();
		boolean isOneway = CodeUtil.isOneway(operation);

		if (isOneway) {
			Buf buf = new Buf();
			Iterator<Parameter> iterator = parameters.iterator();
			while (iterator.hasNext()) {
				Parameter parameter = iterator.next();
				String parameterName = parameter.getName();
				String checkpointName = parameter.getName();
				//if (checkpointName.endsWith("Message"))
				//	checkpointName = checkpointName.substring(0, checkpointName.length()-7);
				buf.putLine2("Check.isValid(\""+checkpointName+"\", "+parameterName+");");
			}
			buf.putLine2("");
			buf.putLine2("try {");
			buf.putLine3("String correlationId = MessageUtil.getCorrelationId(webServiceContext);");
			String operationName = null;
			if (service.getTransacted() != null) {
				buf.putLine3(serviceName+"Transactor transactor = BeanContext.get("+serviceName+"Transactor.class, correlationId);");
				buf.putLine3("TaskExecutorFactory factory = BeanContext.get(\"org.aries.executorFactory\");");
				buf.putLine3("TaskExecutor executor = factory.createExecutor(transactor);");
				operationName = operation.getName();
			} else {
				buf.putLine3("TaskExecutorFactory factory = BeanContext.get(\"org.aries.executorFactory\");");
				buf.putLine3("TaskExecutor executor = factory.createExecutor(this);");
				operationName = operation.getName();
			}
			buf.putLine3("executor.setMethodName(\""+operationName+"\");");

			if (service.getTransacted() == null)
				buf.putLine3("executor.addParameter(correlationId);");

			iterator = parameters.iterator();
			while (iterator.hasNext()) {
				Parameter parameter = iterator.next();
				buf.putLine3("executor.addParameter("+parameter.getName()+");");
			}
			buf.putLine3("executor.execute();");
			buf.putLine3("");
			buf.putLine2("} catch (Exception e) {");
			buf.putLine3("log.error(e);");
			buf.putLine3("ExceptionUtil.rethrow(e);");
			buf.putLine2("}");

			modelClass.addImportedClass("org.aries.task.TaskExecutor");
			modelClass.addImportedClass("org.aries.task.TaskExecutorFactory");
			modelClass.addImportedClass("org.aries.util.ExceptionUtil");
			modelClass.addImportedClass("common.tx.handler.MessageUtil");
			return buf.get();

		} else {
			Buf buf = new Buf();
			buf.putLine2("return null;");
			return buf.get();
		}
	}


	public String generateSourceCode_ProcessMessage(ModelOperation modelOperation, Service service, Element element, Operation operation) {
//		String serviceDomain = service.getDomain();
//		String serviceName = service.getName();
//		String serviceType = service.getElement();
//		String serviceElement = TypeUtil.getLocalPart(serviceType);
//		String serviceNameCapped = NameUtil.capName(serviceName);
//		String servicePackageName = ServiceUtil.getPackageName(service);
//		String serviceClassName = ServiceUtil.getClassName(service);
//		String serviceQualifiedName = ServiceUtil.getQualifiedName(service);
//		String actionClassName = serviceClassName + "Action";
//		String actionQualifiedName = servicePackageName + "." + actionClassName;

		String operationName = operation.getName();
		String operationNameCapped = NameUtil.capName(operation.getName());
		boolean isOneway = CodeUtil.isOneway(operation);

		modelOperation.addImportedClass("org.aries.Assert");
		modelOperation.addImportedClass("org.aries.message.Message");
		modelOperation.addImportedClass("org.aries.runtime.BeanContext");
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	//logStarted();");

		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterNameCapped = NameUtil.capName(parameterName);
			String parameterClassName = TypeUtil.getClassName(parameter.getType());
			String parameterQualifiedName = TypeUtil.getJavaName(parameter.getType());
			
			String construct = parameter.getConstruct();
			if (construct.equals("list")) {
				parameterClassName = "List<"+parameterClassName+">";
				modelOperation.addImportedClass("java.util.List");
			} else if (construct.equals("set")) {
				parameterClassName = "Set<"+parameterClassName+">";
				modelOperation.addImportedClass("java.util.Set");
			} else if (construct.equals("map")) {
				parameterClassName = "Map<Object, "+parameterClassName+">";
				modelOperation.addImportedClass("java.util.Map");
			}
			
//			if (operationName.equals("getOrganizationByPermissionList"))
//				System.out.println(operationName);
			
			buf.putLine2("	"+parameterClassName+" "+parameterName+" = message.getPart(\""+parameterName+"\");");
			buf.putLine2("	Assert.notNull("+parameterName+", \""+parameterNameCapped+" parameter must be specified\");");
			if (!ClassUtil.isJavaDefaultType(parameterClassName))
				modelOperation.addImportedClass(parameterQualifiedName);
		}

		String actionContextName = ServiceLayerHelper.getActionContextName(service);
		buf.putLine2("	"+operationNameCapped+"Action action = BeanContext.get(\""+actionContextName+"\");");
		if (!isOneway) {
			Result result = operation.getResults().get(0);
			String resultName = result.getName();
			String resultClassName = TypeUtil.getClassName(result.getType());
			
			String construct = result.getConstruct();
			if (construct.equals("list")) {
				resultClassName = "List<"+resultClassName+">";
				modelOperation.addImportedClass("java.util.List");
			} else if (construct.equals("set")) {
				resultClassName = "Set<"+resultClassName+">";
				modelOperation.addImportedClass("java.util.Set");
			} else if (construct.equals("map")) {
				resultClassName = "Map<Object, "+resultClassName+">";
				modelOperation.addImportedClass("java.util.Map");
			}
			
			buf.put2("	"+resultClassName+" "+resultName+" = action."+operationName+"(");
		} else {
			buf.put2("	action."+operationName+"(");
		}

		iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameter = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(parameter.getName());
		}
		
		buf.putLine(");");
		if (!isOneway) {
			Result result = operation.getResults().get(0);
			String resultName = result.getName();
			String resultNameCapped = NameUtil.capName(result.getName());
			buf.putLine2("	Assert.notNull("+resultName+", \""+resultNameCapped+" must exist\");");
			buf.putLine2("	message.addPart(\""+resultName+"\", "+resultName+");");
		}
		
		buf.putLine2("	//logComplete();");
		buf.putLine2("	return message;");
		buf.putLine2("");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	//logAborted();");
		buf.putLine2("	//propagate only message and stack trace, not any underlying exception(s) classes.");
		buf.putLine2("	e = InfosgiExceptionUtil.createInfosgiException(e);");
		buf.putLine2("	throw e;");
		buf.putLine2("}");
		return buf.get();
	}

	
	public String generateSourceCode_OnMessage_MessageLevel(ModelOperation modelOperation, Service service, Element element) {
		String operationName = modelOperation.getName();
		String operationNameCapped = NameUtil.capName(modelOperation.getName());
		boolean isOneway = modelOperation.getResultType() == null;

		modelOperation.addImportedClass("org.aries.Assert");
		modelOperation.addImportedClass("org.aries.message.Message");
		modelOperation.addImportedClass("org.aries.runtime.BeanContext");
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	//logStarted();");

		List<ModelParameter> parameters = modelOperation.getParameters();
		Iterator<ModelParameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			ModelParameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String parameterNameCapped = NameUtil.capName(parameterName);
			parameter.getClassName();
			String parameterClassName = parameter.getClassName();
			String parameterQualifiedName = parameter.getClassName() + "." + parameter.getClassName();
			
			String construct = parameter.getConstruct();
			if (construct.equals("list")) {
				parameterClassName = "List<"+parameterClassName+">";
				modelOperation.addImportedClass("java.util.List");
			} else if (construct.equals("set")) {
				parameterClassName = "Set<"+parameterClassName+">";
				modelOperation.addImportedClass("java.util.Set");
			} else if (construct.equals("map")) {
				parameterClassName = "Map<Object, "+parameterClassName+">";
				modelOperation.addImportedClass("java.util.Map");
			}
			
//			if (operationName.equals("getOrganizationByPermissionList"))
//				System.out.println(operationName);
			
			buf.putLine2("	"+parameterClassName+" "+parameterName+" = message.getPart(\""+parameterName+"\");");
			buf.putLine2("	Assert.notNull("+parameterName+", \""+parameterNameCapped+" parameter must be specified\");");
			if (!ClassUtil.isJavaDefaultType(parameterClassName))
				modelOperation.addImportedClass(parameterQualifiedName);
		}

		String actionContextName = ServiceLayerHelper.getActionContextName(service);
		buf.putLine2("	"+operationNameCapped+"Action action = BeanContext.get(\""+actionContextName+"\");");
		if (!isOneway) {
			String resultName = modelOperation.getResultName();
			String resultClassName = modelOperation.getResultType();
			
//			String construct = result.getConstruct();
//			if (construct.equals("list")) {
//				resultClassName = "List<"+resultClassName+">";
//				modelOperation.addImportedClass("java.util.List");
//			} else if (construct.equals("set")) {
//				resultClassName = "Set<"+resultClassName+">";
//				modelOperation.addImportedClass("java.util.Set");
//			} else if (construct.equals("map")) {
//				resultClassName = "Map<Object, "+resultClassName+">";
//				modelOperation.addImportedClass("java.util.Map");
//			}
			
			buf.put2("	"+resultClassName+" "+resultName+" = action."+operationName+"(");
		} else {
			buf.put2("	action."+operationName+"(");
		}

		iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ModelParameter parameter = iterator.next();
			if (i > 0)
				buf.put(", ");
			buf.put(parameter.getName());
		}
		
		buf.putLine(");");
		if (!isOneway) {
			//Result result = operation.getResult();
			String resultName = NameUtil.uncapName(modelOperation.getResultName());
			String resultNameCapped = NameUtil.capName(modelOperation.getResultName());
			buf.putLine2("	Assert.notNull("+resultName+", \""+resultNameCapped+" must exist\");");
			buf.putLine2("	message.addPart(\""+resultName+"\", "+resultName+");");
		}
		
		buf.putLine2("	//logComplete();");
		buf.putLine2("	return message;");
		buf.putLine2("");
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	//logAborted();");
		buf.putLine2("	//propagate only message and stack trace, not any underlying exception(s) classes.");
		buf.putLine2("	e = InfosgiExceptionUtil.createInfosgiException(e);");
		buf.putLine2("	throw e;");
		buf.putLine2("}");
		return buf.get();
	}
	
	
	//	public String generateORIG(ModelClass modelClass, Operation operation) throws Exception {
	//		List<Parameter> parameters = operation.getParameters();
	//		Result result = operation.getResult();
	//		//Service service = context.getService();
	//		Process process = context.getProcess();
	//		
	//		Buf buf = new Buf();
	//		if (result != null) {
	//			String resultTypeName = result.getType();
	//			String resultClassName = TypeUtil.getClassName(resultTypeName);
	//			
	//			if (process != null) {
	//				String processClassName = NameUtil.capName(process.getName())+"Process";
	//				//String processServiceName = CodeUtil.getProcessServiceName(process);
	//				String processOperationName = CodeUtil.getProcessOperationName(process);
	//				
	//				modelClass.addImportedClass("org.aries.registry.ProcessLocator");
	//				modelClass.addImportedClass("org.aries.runtime.BeanContext");
	//				modelClass.addImportedClass(modelClass.getPackageName()+".process."+processClassName);
	//				
	//				buf.putLine2("ProcessLocator processLocator = BeanContext.get(\"org.aries.processLocator\");");
	//				buf.putLine2(processClassName+" process = processLocator.lookup("+processClassName+".class);");
	//				buf.put2(resultClassName+" response = process."+processOperationName+"(");
	//				Iterator<Parameter> iterator = parameters.iterator();
	//				for (int i=0; iterator.hasNext(); i++) {
	//					Parameter parameter = iterator.next();
	//					if (i > 0)
	//						buf.put(", ");
	//					buf.put(parameter.getName());
	//				}
	//				buf.putLine(");");
	//				buf.putLine2("return response;");
	//				
	//			} else {
	//				buf.putLine2(resultClassName+" response = new "+resultClassName+"();");
	//				buf.putLine2("return response;");
	//			}
	//		} else {
	//			buf.putLine2("return;");
	//		}
	//		return buf.get();
	//	}

	//	public String getSource(ModelClass modelClass, Service service) throws Exception {
	//		Buf buf = new Buf();
	//		List<Operation> operations = service.getOperations();
	//		Iterator<Operation> iterator = operations.iterator();
	//		while (iterator.hasNext()) {
	//			Operation operation = iterator.next();
	//			buf.put(getSource(modelClass, operation));
	//		}
	//		return buf.get();
	//	}

}
