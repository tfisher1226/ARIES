package nam.service.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.model.Element;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.codegen.util.CodeUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;


public class ServiceListenerRMIProvider extends AbstractServiceListenerProvider {

	public ServiceListenerRMIProvider(GenerationContext context) {
		super(context);
	}
	
	public String generateSourceCode_ModelOperation_RMIInvocation(Operation operation, Service service) {
		String operationNameCapped = NameUtil.capName(operation.getName());
		String initializerName = service.getDomain() + ".initializer";
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String serviceName = ServiceLayerHelper.getServiceNameUncapped(service);
		String handlerInstanceName = ServiceLayerHelper.getHandlerInstanceName(service);
		String handlerClassName = ServiceLayerHelper.getHandlerClassName(service);
		boolean isOneway = CodeUtil.isOneway(operation);
		
		Buf buf = new Buf();
		buf.putLine2("Initializer initializer = BeanContext.get(\""+initializerName+"\");");
		buf.putLine2("if (initializer == null)");
		buf.putLine2("	return;");
		buf.putLine2("");
		buf.put(CodeUtil.createCheckpointsForParameters(operation));
		buf.putLine2("");
		buf.putLine2("MessageContext messageContext = webServiceContext.getMessageContext();");
		buf.putLine2("String correlationId = (String) messageContext.get(\"correlationId\");");
		buf.putLine2("");
		buf.putLine2("Object transactionKey = transactionSynchronizationRegistry.getTransactionKey();");
		buf.putLine2("String transactionId = transactionKey.toString();");
		buf.putLine2("");
		buf.putLine2("//TransactionHelper transactionHelper = new TransactionHelper();");
		buf.putLine2("//transactionHelper.open();");
		buf.putLine2("");
		
		//Operation operation = ServiceUtil.getOperation(service);
		String operationName = operation.getName();
		List<Parameter> parameters = operation.getParameters();
		String parameterString = CodeUtil.getArgumentString(parameters);
		
		buf.putLine2("try {");
		//buf.putLine2("	//logStarted();");
		buf.putLine2("	String correlationId = getCorrelationIdFromMessage(message);");

		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String className = TypeUtil.getClassName(parameter.getType());
			String instanceName = NameUtil.uncapName(className);
			buf.putLine2("	"+className+" "+instanceName+" = getPayloadFromMessage(message);");
		}

		buf.putLine3(handlerInstanceName+".setCorrelationId(correlationId);");
		buf.putLine3(handlerInstanceName+".setTransactionId(transactionId);");
		buf.putLine3(handlerInstanceName+"."+operationName+"("+parameterString+");");
		buf.putLine3("");
		
		//TODO
		if (!isOneway) {
			List<Result> results = operation.getResults();
			Result result = null;
			if (!results.isEmpty()) {
				result = results.get(0);
				String resultName = NameUtil.uncapName(result.getName());
				String resultNameCapped = NameUtil.capName(result.getName());
				buf.putLine3("Assert.notNull("+resultName+", \""+resultNameCapped+" must exist\");");
				buf.putLine3("message.addPart(\""+resultName+"\", "+resultName+");");
			}
		}
		
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.error(e);");
		//buf.putLine2("	//logAborted();");
		buf.putLine2("	ExceptionUtil.rethrow(e);");
		//buf.putLine2("	//propagate only message and stack trace, not any underlying exception(s) classes.");
		//buf.putLine2("	e = InfosgiExceptionUtil.createInfosgiException(e);");
		//buf.putLine2("	throw e;");
		buf.putLine2("}");
		return buf.get();
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
			List<Result> results = operation.getResults();
			Result result = null;
			if (!results.isEmpty()) {
				result = results.get(0);
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
			List<Result> results = operation.getResults();
			Result result = null;
			if (!results.isEmpty()) {
				result = results.get(0);
				String resultName = result.getName();
				String resultNameCapped = NameUtil.capName(result.getName());
				buf.putLine2("	Assert.notNull("+resultName+", \""+resultNameCapped+" must exist\");");
				buf.putLine2("	message.addPart(\""+resultName+"\", "+resultName+");");
			}
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


}
