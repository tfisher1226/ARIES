package nam.client.src.main.java;

import java.util.Iterator;
import java.util.List;

import nam.client.ClientLayerHelper;
import nam.model.Callback;
import nam.model.Element;
import nam.model.Field;
import nam.model.Query;
import nam.model.Service;

import org.aries.util.NameUtil;

import aries.codegen.AbstractManagementBeanProvider;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelOperation;
import aries.generation.model.ModelParameter;


public class ClientDelegateProvider extends AbstractManagementBeanProvider {

	private Service service;

	
	public ClientDelegateProvider(GenerationContext context) {
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
		//String serviceName = getServiceName();
		//if (!serviceName.endsWith(operationName))
		//	return serviceName + "." + operationName;
		return operationName;
	}

	public String getServiceContext() {
		return service.getNamespace() + "/" + NameUtil.uncapName(service.getName());
	}

	public String getServiceContext(String operationName) {
		return getServiceContext() + "/" + operationName;
	}
	
	
	/*
	 * getDomain() operation
	 * -----------------------
	 */
	
	public void generateSourceCode_GetDomain(ModelOperation modelOperation, Service service) {
		Buf buf = new Buf();
		String domainName = service.getDomain();
		buf.putLine2("return \""+domainName+"\";");
		modelOperation.addInitialSource(buf.get());
	}
	
	
	/*
	 * getServiceId() operation
	 * -----------------------
	 */
	
	public void generateSourceCode_GetServiceId(ModelOperation modelOperation, Service service) {
		Buf buf = new Buf();
		String interfaceName = service.getInterfaceName();
		buf.putLine2("return "+interfaceName+".ID;");
		modelOperation.addInitialSource(buf.get());
	}
	
	
	/*
	 * getProxy() operation
	 * -----------------------
	 */

	public void generateSourceCode_GetProxy(ModelOperation modelOperation, Service service) {
		Buf buf = new Buf();
		String interfaceName = service.getInterfaceName();
		buf.putLine2("return getProxy("+interfaceName+".ID);");
		//buf.putLine2("return ("+resultType+") endpoint;");
		modelOperation.addInitialSource(buf.get());
	}
	
	
	/*
	 * ProxyInvocation operation
	 * -------------------------
	 */

	protected void generateSourceCode_DelegateInvocation(ModelOperation modelOperation, Element element, String serviceName) {
		Buf buf = new Buf();
		buf.putLine2("try {");

		//buf.put(checkAddAssureConsistencyInvocation(modelOperation, element));

		String operationName = modelOperation.getName();
		String resultType = modelOperation.getResultType();
		String resultName = modelOperation.getResultName();
		if (resultType != null && !resultType.equals("void")) {
			String className = NameUtil.getClassName(resultType);
			String variableName = resultName != null ? NameUtil.uncapName(resultName) : "result";
			buf.put3(className+" "+variableName+" = getProxy()."+operationName+"(");
		} else {
			buf.put3("getProxy()."+operationName+"(");
		}

		List<ModelParameter> parameters = modelOperation.getParameters();
		Iterator<ModelParameter> iterator = parameters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			ModelParameter modelParameter = iterator.next();
			String name = modelParameter.getName();
			if (i > 0)
				buf.put(", ");
			buf.put(name);
		}
		buf.putLine(");");
		
		if (resultType != null && !resultType.equals("void")) {
			String variableName = resultName != null ? NameUtil.uncapName(resultName) : "result";
			buf.putLine2("	return "+variableName+";");
		}
		
		buf.putLine2("} catch (Exception e) {");
		buf.putLine2("	log.error(\"Error\", e);");
		buf.putLine2("	throw ExceptionUtil.rewrap(e);");
		buf.putLine2("}");
		modelOperation.addInitialSource(buf.get());
	}

	/*
	 * GetAllElements operation
	 * ------------------------
	 */
	
	public void generateSourceCode_GetAllElements(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("getAll"+getElementClassName(element)+"Records"));
	}
	
	/*
	 * GetElementListByField operation
	 * -------------------------------
	 */
	
	public void generateSourceCode_GetElementListByField(ModelOperation modelOperation, Element element, Field field) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByField"));
	}

	/*
	 * GetElementListByPage operation
	 * ------------------------------
	 */

	//TODO add sanity checks for pageIndex and pageSize params
	public void generateSourceCode_GetElementListByPage(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByPage"));
	}
	
	/*
	 * GetElementByCriteria operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_GetElementByCriteria(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordByCriteria"));
	}
	
	/*
	 * GetElementListByCriteria operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_GetElementListByCriteria(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByCriteria"));
	}
	
	/*
	 * GetElementByQuery operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_GetElementByQuery(ModelOperation modelOperation, Element element, Query query) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordByQuery"));
	}
	
	/*
	 * GetElementListByQueryCriteria operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_GetElementListByQueryCriteria(ModelOperation modelOperation, Element element, Query query) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByQueryCriteria"));
	}
	
	/*
	 * GetElementListByQueryCondition operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_GetElementListByQueryCondition(ModelOperation modelOperation, Element element, Query query) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByQueryCondition"));
	}
	
	/*
	 * GetElementById operation
	 * ------------------------
	 */
	
	public void generateSourceCode_GetElementById(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordById"));
	}
	
	/*
	 * GetElementByKey operation
	 * -------------=-----------
	 */
	
	public void generateSourceCode_GetElementByKey(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordByKey"));
	}
	
	/*
	 * GetElementsByKeys operation
	 * ---------------------------
	 */

	public void generateSourceCode_GetElementsByKeys(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordsByKey"));
	}

	/*
	 * GetElementByField operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_GetElementByField(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("get"+getElementClassName(element)+"RecordByField"));
	}
	
	/*
	 * AddElement operation
	 * --------------------
	 */
	
	public void generateSourceCode_AddElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("add"+getElementClassName(element)+"Record"));
	}

	/*
	 * AddElementList operation
	 * ------------------------
	 */
	
	public void generateSourceCode_AddElementList(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("add"+getElementClassName(element)+"Records"));
	}

	/*
	 * AddElementMap operation
	 * ------------------------
	 */
	
	public void generateSourceCode_AddElementMap(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("add"+getElementClassName(element)+"Map"));
	}

	/*
	 * MoveElement operation
	 * ---------------------
	 */

	public void generateSourceCode_MoveElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("move"+getElementClassName(element)+"Record"));
	}
	
	/*
	 * SaveElement operation
	 * ---------------------
	 */
	
	public void generateSourceCode_SaveElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("save"+getElementClassName(element)+"Record"));
	}

	/*
	 * SaveElementList operation
	 * -------------------------
	 */
	
	public void generateSourceCode_SaveElementList(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("save"+getElementClassName(element)+"Records"));
	}
	
	/*
	 * SaveElementMap operation
	 * ------------------------
	 */
	
	public void generateSourceCode_SaveElementMap(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("save"+getElementClassName(element)+"Map"));
	}
	
	/*
	 * RemoveAllElements operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_RemoveAllElements(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("removeAll"+getElementClassName(element)+"Records"));
	}
	
	/*
	 * RemoveElement operation
	 * -----------------------
	 */
	
	public void generateSourceCode_RemoveElement(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"Record"));
	}

	/*
	 * RemoveElementById operation
	 * ----------------------------
	 */
	
	public void generateSourceCode_RemoveElementById(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"RecordById"));
	}
	
	/*
	 * RemoveElementByKey operation
	 * ----------------------------
	 */
	
	public void generateSourceCode_RemoveElementByKey(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"RecordByKey"));
	}

	/*
	 * RemoveElementListByKeys operation
	 * ---------------------------------
	 */
	
	public void generateSourceCode_RemoveElementListByKeys(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"RecordsByKeys"));
	}

	/*
	 * RemoveElementList operation
	 * ---------------------------
	 */
	
	public void generateSourceCode_RemoveElementList(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"Records"));
	}

	/*
	 * RemoveElementMap operation
	 * --------------------------
	 */
	
	public void generateSourceCode_RemoveElementMap(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"Map"));
	}

	/*
	 * RemoveElementListByCriteria operation
	 * ----------------------------------
	 */
	
	public void generateSourceCode_RemoveElementListByCriteria(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("remove"+getElementClassName(element)+"Records"));
	}
	
	/*
	 * ImportElements operation
	 * ------------------------
	 */

	public void generateSourceCode_ImportElements(ModelOperation modelOperation, Element element) {
		generateSourceCode_DelegateInvocation(modelOperation, element, getServiceName("import"+getElementClassName(element)+"Records"));
	}
	
}
