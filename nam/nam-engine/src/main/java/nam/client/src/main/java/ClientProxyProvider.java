package nam.client.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Element;
import nam.model.Field;
import nam.model.Query;
import nam.model.Service;
import nam.model.TransportType;
import nam.model.Type;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public class ClientProxyProvider extends AbstractManagementBeanProvider {

	private Service service;

	
	public ClientProxyProvider(GenerationContext context) {
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
	 * ProxyInvocation operation
	 * -------------------------
	 */

	protected void generateSourceCode_ProxyInvocation(ModelOperation modelOperation, Element element, String serviceName) {
		//String serviceName = modelClass.getPackageName()+"."+NameUtil.uncapName(modelClass.getClassName());
		//String serviceLocator = "org.aries.serviceLocator";
		String operationName = modelOperation.getName();
		
		Buf buf = new Buf();
		buf.putLine2("try {");
		buf.putLine2("	log.info(\"#### [admin]: "+operationName+"() sending...\");");
		buf.putLine2("	Message request = createMessage(\""+operationName+"\");");
		
		List<ModelParameter> parameters = modelOperation.getParameters();
		Iterator<ModelParameter> iterator = parameters.iterator();
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
			String variableName = resultName != null ? NameUtil.uncapName(resultName) : "result";
			buf.putLine2("	Message response = getProxy().invoke(request);");
			buf.putLine2(className+" "+variableName+" = response.getPart(\""+variableName+"\");");
			if (operationName.startsWith("get") && !resultType.contains("<"))
				buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));
			buf.putLine2("	return "+variableName+";");
		} else {
			buf.putLine2("	getProxy().invoke(request);");
		}
		
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
	
		modelOperation.addInitialSource(buf.get());
	}

	/*
	 * GetAllElements operation
	 * ------------------------
	 */
	
	public void generateSourceCode_GetAllElements(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("getAll"+getElementClassName(element)+"Records"));
	}
	
	/*
	 * GetElementListByField operation
	 * -------------------------------
	 */
	
	public void generateSourceCode_GetElementListByField(ModelOperation modelOperation, Element element, Field field) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByField"));
	}

	/*
	 * GetElementListByPage operation
	 * ------------------------------
	 */

	//TODO add sanity checks for pageIndex and pageSize params
	public void generateSourceCode_GetElementListByPage(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByPage"));
	}
	
	/*
	 * GetElementByCriteria operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_GetElementByCriteria(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByCriteria"));
	}
	
	/*
	 * GetElementListByCriteria operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_GetElementListByCriteria(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByCriteria"));
	}
	
	/*
	 * GetElementByQuery operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_GetElementByQuery(ModelOperation modelOperation, Element element, Query query) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordByQuery"));
	}
	
	/*
	 * GetElementListByQueryCriteria operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_GetElementListByQueryCriteria(ModelOperation modelOperation, Element element, Query query) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByQueryCriteria"));
	}
	
	/*
	 * GetElementListByQueryCondition operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_GetElementListByQueryCondition(ModelOperation modelOperation, Element element, Query query) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByQueryCondition"));
	}
	
	/*
	 * GetElementById operation
	 * ------------------------
	 */
	
	public void generateSourceCode_GetElementById(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordById"));
	}
	
	/*
	 * GetElementByKey operation
	 * ------------------------
	 */
	
	public void generateSourceCode_GetElementByKey(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordByKey"));
	}
	
	/*
	 * GetElementsByKeys operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_GetElementsByKeys(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByKeys"));
	}

	/*
	 * GetElementByField operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_GetElementByField(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordByField"));
	}
	
	/*
	 * AddElement operation
	 * --------------------
	 */
	
	public void generateSourceCode_AddElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("add"+getElementClassName(element)+"Record"));
	}
	
	/*
	 * AddElementList operation
	 * ------------------------
	 */
	
	public void generateSourceCode_AddElementList(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("add"+getElementClassName(element)+"Records"));
	}
	
	/*
	 * AddElementMap operation
	 * ------------------------
	 */
	
	public void generateSourceCode_AddElementMap(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("add"+getElementClassName(element)+"Map"));
	}
	
	/*
	 * MoveElement operation
	 * ---------------------
	 */

	public void generateSourceCode_MoveElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("move"+getElementClassName(element)+"Record"));
	}
	
	/*
	 * SaveElement operation
	 * ---------------------
	 */
	
	public void generateSourceCode_SaveElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("save"+getElementClassName(element)+"Record"));
	}

	/*
	 * SaveElementList operation
	 * -------------------------
	 */
	
	public void generateSourceCode_SaveElementList(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("save"+getElementClassName(element)+"Records"));
	}
	
	/*
	 * SaveElementMap operation
	 * ------------------------
	 */
	
	public void generateSourceCode_SaveElementMap(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("save"+getElementClassName(element)+"Map"));
	}
	
	/*
	 * RemoveAllElements operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_RemoveAllElements(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("removeAll"+getElementClassName(element)+"Records"));
	}
	
	/*
	 * RemoveElement operation
	 * -----------------------
	 */
	
	public void generateSourceCode_RemoveElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"Record"));
	}

	/*
	 * RemoveElementById operation
	 * ----------------------------
	 */
	
	public void generateSourceCode_RemoveElementById(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"RecordById"));
	}
	
	/*
	 * RemoveElementByKey operation
	 * ----------------------------
	 */
	
	public void generateSourceCode_RemoveElementByKey(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"RecordByKey"));
	}

	/*
	 * RemoveElementListByKeys operation
	 * ---------------------------------
	 */
	
	public void generateSourceCode_RemoveElementListByKeys(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"RecordsByKeys"));
	}


	/*
	 * RemoveElementList operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_RemoveElementList(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"Records"));
	}

	/*
	 * RemoveElementMap operation
	 * --------------------------
	 */
	
	public void generateSourceCode_RemoveElementMap(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"Map"));
	}
	
	/*
	 * RemoveElementListByCriteria operation
	 * -------------------------------------
	 */
	
	public void generateSourceCode_RemoveElementListByCriteria(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"Records"));
	}
	
	/*
	 * ImportElements operation
	 * ------------------------
	 */

	public void generateSourceCode_ImportElements(ModelOperation modelOperation, Element element) {
		generateSourceCode_ProxyInvocation(modelOperation, element, getServiceName("import"+getElementClassName(element)+"Records"));
	}
	
}
