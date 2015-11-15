package nam.client;

import nam.model.Callback;
import nam.model.Service;
import nam.model.util.ServiceUtil;

import org.aries.util.NameUtil;

import aries.generation.engine.GenerationContext;


public class ClientLayerHelper {
	
	public static GenerationContext context;

	public static String getRootName(Service service) {
		return ServiceUtil.getRootName(service);
	}
	
	public static String getNamespace(Service service) {
		return ServiceUtil.getNamespace(service);
	}
	
	/*
	 * Client properties
	 * ------------------
	 */
	
	public static String getClientType(Service service) {
		return service.getElement();
	}

	public static String getClientName(Service service) {
		if (service instanceof Callback)
			return service.getName() + "Reply";
		return service.getName();
	}

	public static String getClientNameCapped(Service service) {
		return NameUtil.capName(getClientName(service));
	}

	public static String getClientNameUncapped(Service service) {
		return NameUtil.uncapName(getClientName(service));
	}

	public static String getClientPackageName(Service service) {
		if (service instanceof Callback) {
			//if (service.getPackageName() != null)
				//return service.getPackageName();
			String packageName = service.getDomain();
			//String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
			String serviceName = NameUtil.uncapName(service.getName());
			//if (serviceName.equals("shipmentScheduled"))
			//	System.out.println();
			Service receivingService = ((Callback) service).getReceivingService();
			if (receivingService != null)
				serviceName = receivingService.getName();
			//if (receivingService == null)
			//	receivingService = ((Callback) service).getSendingService();
			if (receivingService != null)
				serviceName = receivingService.getName();
			//if (receivingService == null)
			//	System.out.println();
			
			service.setPackageName(packageName + ".outgoing." + serviceName);
			return service.getPackageName();
		}
		
//		if (service == null)
//			System.out.println();
		String packageName = service.getDomain();
		//String packageName = ProjectLevelHelper.getPackageName(service.getNamespace());
		String serviceName = NameUtil.uncapName(service.getName());
		if (serviceName.endsWith("Manager"))
			packageName += ".management." + serviceName;
		else packageName += ".client." + serviceName;
		return packageName;
	}

	public static String getClientInterfaceName(Service service) {
		return NameUtil.capName(getClientName(service));
	}

	public static String getClientClassName(Service service) {
		if (service instanceof Callback)
			return getClientInterfaceName(service);
		return getClientInterfaceName(service) + "Client";
	}

	public static String getClientProxyClassName(Service service) {
		return getClientInterfaceName(service) + "Proxy";
	}

	public static String getClientQualifiedName(Service service) {
		return getClientPackageName(service) + "." + getClientClassName(service);
	}

	public static String getClientQualifiedInterfaceName(Service service) {
		return getClientPackageName(service) + "." + getClientInterfaceName(service);
	}

	public static String getClientQualifiedClassName(Service service) {
		return getClientPackageName(service) + "." + getClientClassName(service);
	}
	
	public static String getClientProxyQualifiedName(Service service) {
		return getClientPackageName(service) + "." + getClientProxyClassName(service);
	}
	
	public static String getClientContextName(Service service) {
		String serviceNameUncapped = getClientNameUncapped(service);
		if (context.isEnabled("useQualifiedContextNames")) {
			String qualifiedName = getClientQualifiedName(service);
			int segmentCount = NameUtil.getSegmentCount(qualifiedName);
			String contextPrefix = NameUtil.getQualifiedContextNamePrefix(qualifiedName, segmentCount-3);
			String contextName = contextPrefix + "." + serviceNameUncapped;
			return contextName; 
		}
		return serviceNameUncapped;
	}
	
}
