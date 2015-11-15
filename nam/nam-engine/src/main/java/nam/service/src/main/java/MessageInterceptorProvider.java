package nam.service.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.Service;
import nam.model.TransportType;
import nam.model.Type;
import nam.model.util.ParameterUtil;
import nam.model.util.TypeUtil;
import nam.service.ServiceLayerHelper;

import org.aries.util.NameUtil;

import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;


public class MessageInterceptorProvider {

	protected GenerationContext context;
	
	private Service service;

	
	public MessageInterceptorProvider(GenerationContext context) {
		this.context = context;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getServiceName() {
		String serviceInterface = ClientLayerHelper.getClientQualifiedInterfaceName(service);
		String serviceNamePrefix = NameUtil.getQualifiedContextNamePrefix(serviceInterface, 2);
		return serviceNamePrefix + "." + NameUtil.uncapName(service.getName());
	}

	public String getServiceName(String operationName) {
		String serviceName = getServiceName();
		if (!serviceName.endsWith(operationName))
			return serviceName + "." + operationName;
		return serviceName;
	}

	public String getServiceContext() {
		return service.getNamespace() + "/" + NameUtil.uncapName(service.getName());
	}

	public String getServiceContext(String operationName) {
		return getServiceContext() + "/" + operationName;
	}
	

	/*
	 * CreateMessage operation
	 * -----------------------
	 */

	public void generateSourceCode_CreateMessage(ModelOperation modelOperation, Service service) {
		Buf buf = new Buf();
		buf.putLine2("Message request = new Message();");
		buf.putLine2("User user = BeanContext.get(\""+service.getDomain()+".user\");");
		buf.putLine2("if (user != null)");
		buf.putLine2("	request.addPart(\""+service.getDomain()+".userName\", user.getUserName());");
		buf.putLine2("return request;");
		modelOperation.addInitialSource(buf.get());
		Type type = context.findTypeByName("user");
		modelOperation.addImportedClass(type);
	}
	
	/*
	 * GetProxy operation
	 * -----------------------
	 */
	
	public void generateSourceCode_GetProxy(ModelOperation modelOperation, TransportType transportType) {
		Buf buf = new Buf();
		buf.putLine2("ServiceLocator serviceLocator = BeanContext.get(\"org.aries.serviceLocator\");");
		buf.putLine2("ServiceProxy serviceProxy = serviceLocator.lookup(serviceId, \"0.0.1-SNAPSHOT\", TransportType."+transportType+");");
		buf.putLine2("return serviceProxy;");
		modelOperation.addInitialSource(buf.get());
	}
	
	/*
	 * GetHandler operation
	 * --------------------
	 */
	
	public void generateSourceCode_GetHandler(ModelOperation modelOperation, Service service) {
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);

		Buf buf = new Buf();
		buf.putLine2("return BeanContext.getFromJNDI("+interfaceName+"Handler.class, \""+interfaceName+"HandlerImpl\");");
		modelOperation.addInitialSource(buf.get());
	}
	
	/*
	 * ProxyInvocation operation
	 * -------------------------
	 */

	protected void generateSourceCode_ProxyInvocation(ModelOperation modelOperation, Operation operation) {
		String interfaceName = ServiceLayerHelper.getServiceInterfaceName(service);
		String messageName = NameUtil.uncapName(interfaceName) + "Message";
		String handlerName = NameUtil.uncapName(interfaceName) + "Handler";
		String operationName = operation.getName();
		
//		modelOperation.addImportedClass("org.aries.message.Message");
//		modelOperation.addImportedClass("org.aries.runtime.BeanContext");
//		modelOperation.addImportedClass("org.aries.registry.ServiceLocator");
//		modelOperation.addImportedClass("org.aries.service.ServiceProxy");
//		modelOperation.addImportedClass("org.aries.TransportType");
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		
		List<Parameter> parameters = operation.getParameters();
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = iterator.next();
			String type = parameter.getType();
			String typeSignature = ParameterUtil.getTypeSignature(parameter);
			String paramName = parameter.getName();
			buf.putLine2("	"+typeSignature+" "+paramName+" = message.getPart(\""+paramName+"\");");
		}
		
		List<Result> results = operation.getResults();
		Result result = null;
		if (!results.isEmpty())
			result = results.get(0);
		if (result == null) {
			String parameterString = ParameterUtil.getArgumentString(operation);
			buf.putLine2("	"+handlerName+"."+operationName+"("+parameterString+");");
			
		} else {
			String resultName = result.getName();
			String resultNameCapped = NameUtil.capName(result.getName());
			String resultClassName = TypeUtil.getClassName(result.getType());
			
			String construct = result.getConstruct();
			if (construct.equals("list")) {
				resultClassName = "List<"+resultClassName+">";
			} else if (construct.equals("set")) {
				resultClassName = "Set<"+resultClassName+">";
			} else if (construct.equals("map")) {
				String resultKeyClassName = TypeUtil.getClassName(result.getKey());
				resultClassName = "Map<"+resultKeyClassName+", "+resultClassName+">";
			}
			
			String parameterString = ParameterUtil.getArgumentString(operation);
			buf.putLine2("	"+resultClassName+" "+resultName+" = "+handlerName+"."+operationName+"("+parameterString+");");
			buf.putLine2("	Assert.notNull("+resultName+", \""+resultNameCapped+" must exist\");");
			buf.putLine2("	message.addPart(\""+resultName+"\", "+resultName+");");
			modelOperation.addImportedClass("org.aries.Assert");
		}

		buf.putLine2("	");
		buf.putLine2("} catch (Throwable e) {");
		buf.putLine2("	log.error(e);");
		buf.putLine2("	//send error to event queue");
		buf.putLine2("	//forward message to invalid queue");
		buf.putLine2("	message.addPart(\"exception\", e);");
		buf.putLine2("}");
	
		buf.putLine2("");
		buf.putLine2("return message;");
		modelOperation.addInitialSource(buf.get());
	}
	
}
