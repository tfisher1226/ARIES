package nam.client.src.main.java;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Callback;
import nam.model.Element;
import nam.model.Field;
import nam.model.Query;
import nam.model.Service;
import nam.model.TransportType;
import nam.model.Type;
import nam.model.util.ServiceUtil;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public class ClientClassJMSProvider extends AbstractManagementBeanProvider {

	private Service service;

	protected boolean messageLayerRequested;
	
	
	public ClientClassJMSProvider(GenerationContext context) {
		super(context);
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
	 * LookupService operation
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
	 * ServiceMethod operation
	 * -----------------------
	 */

	protected void generateSourceCode_ServiceMethod(ModelOperation modelOperation, Element element, String serviceName) {
		if (messageLayerRequested)
			generateSourceCode_ServiceMethod_MessageLayer(modelOperation, element, serviceName);
		else generateSourceCode_ServiceMethod_JMSLayer(modelOperation, element, serviceName);
	}

	protected void generateSourceCode_ServiceMethod_JMSLayer(ModelOperation modelOperation, Element element, String serviceName) {
		Buf buf = new Buf();
		List<ModelParameter> parameters = modelOperation.getParameters();
		Iterator<ModelParameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			ModelParameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String checkpointName = parameter.getName();
			//if (checkpointName.endsWith("Message"))
			//	checkpointName = checkpointName.substring(0, checkpointName.length()-7);
			//buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
			buf.putLine2("//Check.isValid(\""+checkpointName+"\", "+parameterName+");");
		}
		
		//Callback incomingCallback = context.getIncomingCallbackByName(serviceName);
		//Callback outgoingCallback = context.getOutgoingCallbackByName(serviceName);
		//boolean isCallback = incomingCallback != null || outgoingCallback != null;

		String applicationName = context.getApplication().getName();
		String beanName = NameUtil.uncapName(NameUtil.getLastSegmentFromPackageName(serviceName));
		String interfaceName = NameUtil.capName(NameUtil.getLastSegmentFromPackageName(serviceName));

		buf.putLine2("try {");
		String resultType = modelOperation.getResultType();
		String resultName = modelOperation.getResultName();
		if (resultType != null && !resultType.equals("void")) {
			String className = NameUtil.getClassName(resultType);
			String variableName = NameUtil.uncapName(resultName);

			String text = getParameterString(parameters);
			buf.putLine2("	"+className+" "+variableName+" = invoke(\""+text+"\");");
			//if (modelOperation.getName().startsWith("get") && !resultType.contains("<"))
			//	buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
			buf.putLine2("	return "+variableName+";");

		} else {
			String sendOperationName = "send";
			if (ServiceUtil.isSynchronous(service))
				sendOperationName = "invoke";
			String text = getParameterString(parameters);
			if (service instanceof Callback) {
				buf.putLine2("	String replyToDestination = getReplyToDestination("+beanName+"Message, \""+interfaceName+"\");");
				buf.putLine2("	"+sendOperationName+"(replyToDestination, "+beanName+"Message);");
				buf.putLine2("	log.info(\"#### ["+applicationName+"-client]: "+interfaceName+" response sent\");");
				//buf.putLine2("	log.debug(\""+interfaceName+": replied\");");
			} else {
				buf.putLine2("	"+sendOperationName+"("+text+");");
				//buf.putLine2("	"+sendOperationName+"(DESTINATION, "+text+");");
				buf.putLine2("	log.info(\"#### ["+applicationName+"-client]: "+interfaceName+" request sent\");");
				//buf.putLine2("	log.debug(\""+interfaceName+": sent\");");
			}
		}
		
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	log.error(\"Error\", e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
	
		modelOperation.addInitialSource(buf.get());
	}
	
	
	public static String getParameterString(Collection<ModelParameter> parameters) {
		Buf buf = new Buf();
		Iterator<ModelParameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ModelParameter modelParameter = iterator.next();
			String name = modelParameter.getName();
			if (i > 0)
				buf.put(", ");
			buf.put(name);
		}
		return buf.get();
	}

	protected void generateSourceCode_ServiceMethod_MessageLayer(ModelOperation modelOperation, Element element, String serviceName) {
		//String serviceName = modelClass.getPackageName()+"."+NameUtil.uncapName(modelClass.getClassName());
		//String serviceLocator = "org.aries.serviceLocator";
		
		modelOperation.addImportedClass("org.aries.message.Message");
		modelOperation.addImportedClass("org.aries.runtime.BeanContext");
		modelOperation.addImportedClass("org.aries.registry.ServiceLocator");
		modelOperation.addImportedClass("org.aries.service.ServiceProxy");
		modelOperation.addImportedClass("org.aries.TransportType");
		
		Buf buf = new Buf();
		List<ModelParameter> parameters = modelOperation.getParameters();
		Iterator<ModelParameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			ModelParameter parameter = iterator.next();
			String parameterName = parameter.getName();
			String checkpointName = parameter.getName();
			//if (checkpointName.endsWith("Message"))
			//	checkpointName = checkpointName.substring(0, checkpointName.length()-7);
			buf.putLine2("Check.isValid(\""+checkpointName+"\", "+parameterName+");");
		}
		
		buf.putLine2("try {");
		buf.putLine2("	Message request = createMessage();");
		iterator = parameters.iterator();
		while (iterator.hasNext()) {
			ModelParameter modelParameter = iterator.next();
			String name = modelParameter.getName();
			buf.putLine2("	request.addPart(\""+name+"\", "+name+");");
		}
		
		//buf.putLine2("ServiceProxy proxy = getProxy(\""+serviceName+"\");");
		//buf.putLine2("ServiceLocator locator = BeanContext.get(\""+serviceLocator+"\");");
		//buf.putLine2("ServiceProxy proxy = locator.lookup(\""+serviceContext+"\", \"0.0.1-SNAPSHOT\", TransportType.HTTP);");

		String resultType = modelOperation.getResultType();
		String resultName = modelOperation.getResultName();
		if (resultType != null && !resultType.equals("void")) {
			String className = NameUtil.getClassName(resultType);
			String variableName = NameUtil.uncapName(resultName);
			buf.putLine2("	Message response = invoke(request);");
			buf.putLine2("	"+className+" "+variableName+" = response.getPart(\""+variableName+"\");");
			if (modelOperation.getName().startsWith("get") && !resultType.contains("<"))
				buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
			buf.putLine2("	return "+variableName+";");
		} else {
			buf.putLine2("	invoke(request);");
		}
		
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	log.error(\"Error\", e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
	
		modelOperation.addInitialSource(buf.get());
	}

	/*
	 * ImportElements operation
	 * ------------------------
	 */

	public void generateSourceCode_ImportElements(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("importElements"));
	}
	
	/*
	 * GetAllElements operation
	 * ------------------------
	 */
	
	public void generateSourceCode_GetAllElements(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getAllElements"));
	}
	
	/*
	 * GetElementListByField operation
	 * -------------------------------
	 */
	
	public void generateSourceCode_GetElementListByField(ModelOperation modelOperation, Element element, Field field) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementListByField"));
	}

	/*
	 * GetElementListByPage operation
	 * ------------------------------
	 */

	//TODO add sanity checks for pageIndex and pageSize params
	public void generateSourceCode_GetElementListByPage(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementListByPage"));
	}
	
	/*
	 * GetElementById operation
	 * ------------------------
	 */
	
	public void generateSourceCode_GetElementById(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementById"));
	}
	
	/*
	 * GetElementByKey operation
	 * ------------------------
	 */
	
	public void generateSourceCode_GetElementByKey(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementByKey"));
	}
	
	/*
	 * GetElementsByKeys operation
	 * --------------------------
	 */
	
	public void generateSourceCode_GetElementsByKeys(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementsByKeys"));
	}

	/*
	 * GetElementByField operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_GetElementByField(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementByField"));
	}
	
	/*
	 * GetElementByCriteria operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_GetElementByCriteria(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementByCriteria"));
	}
	
	/*
	 * GetElementListByCriteria operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_GetElementListByCriteria(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementListByCriteria"));
	}

	/*
	 * GetElementByQuery operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_GetElementByQuery(ModelOperation modelOperation, Element element, Query query) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementByQuery"));
	}
	
	/*
	 * GetElementListByQueryCriteria operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_GetElementListByQueryCriteria(ModelOperation modelOperation, Element element, Query query) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementListByQueryCriteria"));
	}
	
	/*
	 * GetElementListByQueryCondition operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_GetElementListByQueryCondition(ModelOperation modelOperation, Element element, Query query) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("getElementListByQueryCondition"));
	}
	
	/*
	 * AddElement operation
	 * --------------------
	 */
	
	public void generateSourceCode_AddElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("addElement"));
	}

	/*
	 * AddElementList operation
	 * ------------------------
	 */
	
	public void generateSourceCode_AddElementList(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("addElementList"));
	}
	
	/*
	 * AddElementMap operation
	 * -----------------------
	 */
	
	public void generateSourceCode_AddElementMap(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("addElementMap"));
	}
	
	/*
	 * MoveElement operation
	 * ---------------------
	 */

	public void generateSourceCode_MoveElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("moveElement"));
	}
	
	/*
	 * SaveElement operation
	 * ---------------------
	 */
	
	public void generateSourceCode_SaveElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("saveElement"));
	}

	/*
	 * SaveElementList operation
	 * -------------------------
	 */
	
	public void generateSourceCode_SaveElementList(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("saveElementList"));
	}
	
	/*
	 * SaveElementMap operation
	 * ------------------------
	 */
	
	public void generateSourceCode_SaveElementMap(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("saveElementMap"));
	}
	
	/*
	 * RemoveAllElements operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_RemoveAllElements(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("removeAllElements"));
	}

	/*
	 * RemoveElement operation
	 * -----------------------
	 */
	
	public void generateSourceCode_RemoveElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("removeElement"));
	}

	/*
	 * RemoveElementById operation
	 * ----------------------------
	 */
	
	public void generateSourceCode_RemoveElementById(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("removeElementById"));
	}
	
	/*
	 * RemoveElementByKey operation
	 * ----------------------------
	 */
	
	public void generateSourceCode_RemoveElementByKey(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("removeElementByKey"));
	}

	/*
	 * RemoveElementListByKeys operation
	 * ---------------------------------
	 */
	
	public void generateSourceCode_RemoveElementListByKeys(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("removeElementListByKeys"));
	}
	
	/*
	 * RemoveElementList operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_RemoveElementList(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("removeElementList"));
	}
	
	/*
	 * RemoveElementMap operation
	 * --------------------------
	 */
	
	public void generateSourceCode_RemoveElementMap(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("removeElementMap"));
	}
	
	/*
	 * RemoveElementListByCriteria operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_RemoveElementListByCriteria(ModelOperation modelOperation, Element element) {
		generateSourceCode_ServiceMethod(modelOperation, element, getServiceName("removeElementList"));
	}
	
}
