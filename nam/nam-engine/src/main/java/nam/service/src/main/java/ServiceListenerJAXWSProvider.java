package nam.service.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Fault;
import nam.model.Module;
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


public class ServiceListenerJAXWSProvider extends AbstractServiceListenerProvider {

	public ServiceListenerJAXWSProvider(GenerationContext context) {
		super(context);
	}

//	public String generate(ModelClass modelClass, Service service, Operation operation) throws Exception {
//		
////		if (ServiceUtil.isStateful(service))
////			return generateImplementation_Stateful(modelClass, service, operation);
////		return generateImplementation_Stateless(modelClass, service, operation);
//		
////		String serviceTransport = context.getProperty("generateServiceTransport");
////		if (serviceTransport.equals("RMI"))
////		if (serviceTransport.equals("JAXWS"))
//
//		return generateSourceCode_ModelOperation_EJBInvocation(modelOperation, service);
//	}


	protected String getModuleId() {
		Module module = context.getModule();
		String moduleId = module.getName();
		//moduleId = moduleName.replace("-",  ".");
		//moduleId = moduleId.replace("_",  ".");
		return moduleId;
	}
	
	public String generateSourceCode_ModelOperation_HTTPInvocation(Operation operation, Service service) {
		//String operationNameCapped = NameUtil.capName(operation.getName());
		//String initializerName = service.getDomain() + ".initializer";
		//String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		//String serviceName = ServiceLayerHelper.getServiceNameUncapped(service);
		String handlerInstanceName = ServiceLayerHelper.getHandlerInstanceName(service);
		//String handlerClassName = ServiceLayerHelper.getHandlerClassName(service);
		boolean isManager = service.getName().endsWith("Manager");
		boolean isOneway = CodeUtil.isOneway(operation);
		
		Buf buf = new Buf();
		//buf.putLine2("if (!isInitialized(\""+service.getDomain()+"\"))");
		//buf.putLine2("Initializer initializer = BeanContext.get(\""+initializerName+"\");");
		//buf.putLine2("if (initializer == null)");
		buf.putLine2("if (!Bootstrapper.isInitialized(\""+getModuleId()+"\"))");
		if (ServiceUtil.hasResult(service, operation))
			buf.putLine2("	return null;");
		else buf.putLine2("	return;");
		buf.putLine2("");
		
		//buf.put(CodeUtil.createCheckpointsForParameters(operation));
		//buf.putLine2("");
		//buf.putLine2("MessageContext messageContext = webServiceContext.getMessageContext();");
		//buf.putLine2("String correlationId = (String) messageContext.get(\"correlationId\");");
		//buf.putLine2("");
		//buf.putLine2("Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();");
		//buf.putLine2("String transactionId = transactionKey.toString();");
		//buf.putLine2("");
		//buf.putLine2("//TransactionHelper transactionHelper = new TransactionHelper();");
		//buf.putLine2("//transactionHelper.open();");
		//buf.putLine2("");
		
		//Operation operation = ServiceUtil.getOperation(service);
		String operationName = operation.getName();
		
		buf.putLine2("try {");
		//buf.putLine2("	//logStarted();");

		String parameterString = ParameterUtil.getArgumentString(operation);
		Process process = service.getProcess();
		if (process != null && !isManager) {
			String processContextInstanceName = ServiceLayerHelper.getProcessContextInstanceName(process);
			buf.putLine2("	"+processContextInstanceName+".validate("+parameterString+");");
			//buf.putLine2("");
		}
		
		if (ServiceUtil.hasResult(service, operation)) {
			List<Result> results = operation.getResults();
			Result result = results.get(0);
			String structure = result.getConstruct();
			String resultType = CodeUtil.getResultType(result);
			//String typeName = CodeUtil.getResultTypeName(result);
			//String resultName = CodeUtil.getResultName(result);
			String resultName = CodeUtil.getStructuredName(structure, "result");
			buf.putLine3(resultType+" "+resultName+" = "+handlerInstanceName+"."+operationName+"("+parameterString+");");
		} else {
			//buf.putLine2("	"+handlerInstanceName+".setCorrelationId(correlationId);");
			//buf.putLine2("	"+handlerInstanceName+".setTransactionId(transactionId);");
			buf.putLine3(handlerInstanceName+"."+operationName+"("+parameterString+");");
		}
		
		//TODO
		if (!isOneway && ServiceUtil.hasResult(service, operation)) {
			Result result = operation.getResults().get(0);
			String structure = result.getConstruct();
			//String typeName = CodeUtil.getResultTypeName(result);
			//String resultName = NameUtil.uncapName(result.getName());
			String resultName = CodeUtil.getStructuredName(structure, "result");
			String resultNameCapped = NameUtil.capName(result.getName());
			String resultType = TypeUtil.getLocalPart(result.getType());
			String resultTypeCapped = NameUtil.capName(resultType);
			String errorString = operation.getName()+"()";
//			if (structure.equals("item"))
//				errorString += " result";
//			else if (structure.equals("list"))
//				errorString += " result list";
//			else if (structure.equals("set"))
//				errorString += " result set";
//			else if (structure.equals("map"))
//				errorString += " result map";
			errorString += " result must exist";
			
//			if (resultNameCapped.equals("BookIds"))
//				System.out.println();
			
			if (!structure.equals("item"))
				buf.putLine3("Assert.notNull("+resultName+", \""+errorString+"\");");
			//buf.putLine3("message.addPart(\""+resultName+"\", "+resultName+");");
			buf.putLine3("return "+resultName+";");
		}

		//System.out.println(">>> "+service.getName()+"");
		List<Fault> faults = ServiceUtil.getFaults(service);
		Iterator<Fault> iterator = faults.iterator();
		while (iterator.hasNext()) {
			Fault fault = (Fault) iterator.next();
			String faultClassName = ServiceLayerHelper.getFaultClassName(fault);

			buf.putLine3("");
			buf.putLine2("} catch (Throwable e) {");
			buf.putLine2("	log.error(e);");
			buf.putLine3("	//throw ExceptionUtil.rewrap(e);");
			buf.putLine3("	Exception cause = ExceptionUtil.getRootCause(e);");
			buf.putLine3("	throw new "+faultClassName+"(cause.getMessage(), cause);");
		}
		
		if (faults.isEmpty()) {
			buf.putLine3("");
			buf.putLine2("} catch (Throwable e) {");
			buf.putLine2("	log.error(e);");
			//buf.putLine2("	//logAborted();");
			buf.putLine2("	throw ExceptionUtil.rewrap(e);");
			//buf.putLine2("	//propagate only message and stack trace, not any underlying exception(s) classes.");
			//buf.putLine2("	e = InfosgiExceptionUtil.createInfosgiException(e);");
			//buf.putLine2("	throw e;");
		}
		
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
		buf.put(CodeUtil.createCheckpointsForParameters(operation));
		
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

		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
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
			buf.put(CodeUtil.createCheckpointsForParameters(operation));

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

			Iterator<Parameter> iterator = parameters.iterator();
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
