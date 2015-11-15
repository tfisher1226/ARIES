package nam.service;

import java.util.List;

import nam.ProjectLevelHelper;
import nam.model.Callback;
import nam.model.Channel;
import nam.model.Domain;
import nam.model.Fault;
import nam.model.Module;
import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Process;
import nam.model.Service;
import nam.model.util.ModuleUtil;
import nam.model.util.OperationUtil;
import nam.model.util.ProjectUtil;
import nam.model.util.TypeUtil;

import org.apache.commons.lang.StringUtils;
import org.aries.Assert;
import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;


public class ServiceLayerHelper {

	public static GenerationContext context;
	
	/*
	 * Service properties
	 * ------------------
	 */
	
	public static String getServiceType(Service service) {
		return service.getElement();
	}

	public static String getServiceName(Service service) {
		return service.getName();
	}

	public static String getServiceNameCapped(Service service) {
		return NameUtil.capName(getServiceName(service));
	}

	public static String getServiceNameUncapped(Service service) {
		return NameUtil.uncapName(getServiceName(service));
	}
	
	//TODO make this a QName or better
	public static String getServiceId(Service service) {
		//TODO - Externalize this properly
		String serviceId = service.getDomain();
		if (context != null && !context.getPropertyAsBoolean("appendProjectToGroupId"))
			serviceId += "." + context.getProject().getName();
		if (service.getName() != null)
			serviceId += "." + NameUtil.uncapName(service.getName());
		//if (service.getRef() != null)
		//	return service.getRef();
		return serviceId;
	}
	
	public static String getServiceDomainName(Service service) {
		//TODO - Externalize this properly
		String serviceDomain = service.getDomain();
		if (!StringUtils.isEmpty(serviceDomain)) {
			if (context != null && !context.getPropertyAsBoolean("appendProjectToGroupId") && false)
				serviceDomain += "." + context.getProject().getName();
			return serviceDomain;
		}
		
		//serviceDomain = ProjectLevelHelper.getPackageName(service.getNamespace());
		return serviceDomain;
	}
	
	public static String getServicePackageName(Service service) {
		if (service instanceof Callback) {
//			if (service.getPackageName() != null)
//				return service.getPackageName();
			String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
			String serviceName = NameUtil.uncapName(service.getName());
			Service receivingService = ((Callback) service).getReceivingService();
			if (receivingService != null)
				serviceName = receivingService.getName();
			
			packageName = packageName + ".incoming." + serviceName;
			service.setPackageName(packageName);
			return packageName;
		}
		
		String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		String serviceName = NameUtil.uncapName(service.getName());
//		if (serviceName.equals("reservedBooksManager"))
//			System.out.println();
		if (serviceName.endsWith("Manager"))
			packageName += ".management." + serviceName;
		else packageName += ".incoming." + serviceName;
		return packageName;

////		Service receivingService = null;
//		if (service instanceof Callback)
//			return service.getPackageName();
////		if (receivingService != null)
////			service = receivingService;
//		String serviceName = NameUtil.uncapName(service.getName());
//		return ProjectLevelHelper.getPackageName(service.getNamespace()) + ".service." + serviceName;
	}

	public static String getChannelPackageName(Service service, Channel channel) {
		String packageName = null;
		if (service.getDomain() != null)
			packageName = service.getDomain();
		else packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		return packageName + NameUtil.uncapName(channel.getName());
	}

	public static String getServiceInterfaceName(Service service) {
		return NameUtil.capName(getServiceName(service));
	}

	public static String getServiceClassName(Service service) {
		return getServiceInterfaceName(service) + "Impl";
	}

	public static String getServiceProxyClassName(Service service) {
		return getServiceInterfaceName(service) + "Proxy";
	}

	public static String getServiceQualifiedName(Service service) {
		return getServicePackageName(service) + "." + getServiceClassName(service);
	}

	public static String getServiceQualifiedInterfaceName(Service service) {
		return getServicePackageName(service) + "." + getServiceInterfaceName(service);
	}

	public static String getServiceProxyQualifiedName(Service service) {
		return getServicePackageName(service) + "." + getServiceProxyClassName(service);
	}
	
	public static String getServiceContextName(Service service) {
		String serviceNameUncapped = getServiceNameUncapped(service);
		if (context.isEnabled("useQualifiedContextNames")) {
			String qualifiedName = getServiceQualifiedName(service);
			int segmentCount = NameUtil.getSegmentCount(qualifiedName);
			String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, segmentCount-3);
			String contextName = contextPrefix + "." + serviceNameUncapped;
			return contextName; 
		}
		return serviceNameUncapped;
	}

	
	/*
	 * Handler properties
	 * ------------------
	 */

	public static String getHandlerPackageName(Service service) {
		return getServicePackageName(service);
	}
	
	public static String getHandlerInterfaceName(Service service) {
		return getServiceInterfaceName(service) + "Handler";
	}

	public static String getHandlerClassName(Service service) {
		return getServiceInterfaceName(service) + "HandlerImpl";
	}

	public static String getHandlerInstanceName(Service service) {
		return NameUtil.uncapName(getHandlerInterfaceName(service));
	}


	/*
	 * Process properties
	 * ------------------
	 */

	public static String getProcessType(Process process) {
		String namespace = process.getNamespace();
		String localPart = NameUtil.uncapName(getProcessName(process));
		return org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
	}
	
	public static String getProcessName(Process process) {
		Assert.notNull(process, "Process must be specified");
		if (!StringUtils.isEmpty(process.getName()))
			return process.getName();
		if (!StringUtils.isEmpty(process.getRef()))
			return process.getRef();
		String processName = NameUtil.uncapName(getProcessClassName(process));
		return processName;
	}

	public static String getProcessName(Module module, Domain domain, String baseName) {
		return getProcessName(ModuleUtil.getDomains(module), domain, baseName);
	}
	
	public static String getProcessName(List<Domain> domains, Domain domain, String baseName) {
		if (domains.size() > 1) {
			String name = domain.getName();
			int lastIndexOf = name.lastIndexOf(".");
			if (lastIndexOf > -1)
				name = name.substring(lastIndexOf + 1);
			name = NameUtil.capName(name);
			baseName += name;
		}
		String processName = baseName + "Process";
		return processName;
	}
	
	public static String getProcessRootName(Process process) {
		return ProjectUtil.getBaseName(process.getName(), "Process");
	}
	
	public static String getProcessInstanceName(Process process) {
		return NameUtil.uncapName(getProcessName(process));
	}
	
	public static String getProcessPackageName(Process process) {
		return ProjectLevelHelper.getPackageName(process.getNamespace());
	}

	public static String getProcessClassName(Process process) {
		return getProcessClassName(process.getName());
	}

	public static String getProcessClassName(String processName) {
		processName = NameUtil.toCamelCase(processName);
		processName = NameUtil.capName(processName);
		if (!processName.toLowerCase().endsWith("process"))
			processName += "Process";
		String className = processName;
		return className;
	}
	
	public static String getProcessQualifiedName(Process process) {
		return getProcessPackageName(process) + "." + getProcessClassName(process);
	}

	
	/*
	 * ProcessContext properties
	 * -------------------------
	 */

	public static String getProcessContextType(Process process) {
		String namespace = process.getNamespace();
		String localPart = NameUtil.uncapName(getProcessContextName(process));
		return org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
	}
	
	public static String getProcessContextName(Process process) {
		Assert.notNull(process, "Process must be specified");
		String processName = process.getName();
		if (StringUtils.isEmpty(processName))
			return process.getName().replace("Process", "Context");
		processName = NameUtil.uncapName(getProcessContextClassName(process));
		return processName;
	}
	
	public static String getProcessContextPackageName(Process process) {
		return getProcessPackageName(process);
	}
	
	public static String getProcessContextInstanceName(Process process) {
		return NameUtil.uncapName(getProcessContextName(process));
	}
	
	public static String getProcessContextClassName(Process process) {
		return getProcessClassName(process.getName()).replace("Process", "Context");
	}

	public static String getProcessContextQualifiedName(Process process) {
		return getProcessPackageName(process) + "." + getProcessContextClassName(process);
	}
	
	
	/*
	 * Executor properties
	 * -------------------
	 */

	public static String getExecutorType(Process process, String executorName) {
		String namespace = process.getNamespace();
		String localPart = NameUtil.uncapName(executorName);
		return org.aries.util.TypeUtil.getTypeFromNamespaceAndLocalPart(namespace, localPart);
	}
	
	public static String getExecutorInstanceName(String executorName) {
		return NameUtil.uncapName(getExecutorClassName(executorName));
	}
	
	public static String getExecutorPackageName(Process process) {
		return ProjectLevelHelper.getPackageName(process.getNamespace()) + ".event";
	}

	public static String getExecutorClassName(String executorName) {
		executorName = NameUtil.capName(executorName);
		if (!executorName.toLowerCase().endsWith("executor"))
			executorName += "Executor";
		if (executorName.toLowerCase().endsWith("event"))
			executorName = executorName.substring(0, executorName.length()-5);
		String className = executorName;
		return className;
	}
	
	public static String getExecutorQualifiedName(Process process, String executorName) {
		return getExecutorPackageName(process) + "." + getExecutorClassName(executorName);
	}

	
	/*
	 * Message properties
	 * ------------------
	 */
	
	public static String getMessageType(Operation operation) {
		return "{"+getMessageNamespace(operation)+"}"+getMessageBeanName(operation);
	}
	
	public static String getMessagePackageName(Operation operation) {
		Parameter parameter = OperationUtil.getParameter(operation);
		return TypeUtil.getPackageName(parameter.getType());
	}

	public static String getMessageClassName(Operation operation) {
		Parameter parameter = OperationUtil.getParameter(operation);
		return TypeUtil.getClassName(parameter.getType());
	}

	public static String getMessageQualifiedName(Operation operation) {
		return getMessagePackageName(operation) + "." + getMessageClassName(operation);
	}
	
	public static String getMessageNamespace(Operation operation) {
		Parameter parameter = OperationUtil.getParameter(operation);
		return TypeUtil.getNamespace(parameter.getType());
	}

	public static String getMessageBeanName(Operation operation) {
		return NameUtil.uncapName(getMessageClassName(operation));
	}

	public static String getMessageBaseName(Operation operation) {
		String messageBaseName = getMessageClassName(operation);
		if (messageBaseName.endsWith("Message"))
			messageBaseName = messageBaseName.replace("Message", "");
		return messageBaseName;
	}
	
	public static String getMessageReceivedFlagName(Operation operation) {
		String messageBaseName = getMessageBaseName(operation);
		return NameUtil.uncapName(messageBaseName) + "Received";
	}
	
	public static String getFailureMessageReason(Operation operation) {
		String messageBaseName = getMessageBaseName(operation);
		return NameUtil.uncapName(messageBaseName) + "Reason";
	}
	
	
	/*
	 * Fault properties
	 * ----------------
	 */
	
	public static String getFaultPackageName(Fault fault) {
		return TypeUtil.getPackageName(fault.getType());
	}
	
	public static String getFaultClassName(Fault fault) {
		return TypeUtil.getClassName(fault.getType());
	}
	
	public static String getFaultQualifiedName(Fault fault) {
		return getFaultPackageName(fault) + "." + getFaultClassName(fault);
	}
	
	
	/*
	 * Action properties
	 * -----------------
	 */
	
	public static String getActionContextName(Service service) {
		String servicePackageName = getServicePackageName(service);
		String serviceInterfaceName = getServiceInterfaceName(service);
		String actionClassName = serviceInterfaceName + "Action";
		String actionQualifiedName = servicePackageName + "." + actionClassName;
		String actionBeanName = NameUtil.uncapName(actionClassName);
		String contextName = NameUtil.uncapName(service.getName()) + "." + actionBeanName;
		String contextPrefix = NameUtil.getQualifiedContextNamePrefix(actionQualifiedName, 2);
		String actionContextName = contextPrefix + "." + contextName;
		return actionContextName;
	}
	

}
