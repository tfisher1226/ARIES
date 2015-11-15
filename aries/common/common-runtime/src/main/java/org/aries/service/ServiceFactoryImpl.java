package org.aries.service;

import java.lang.reflect.Constructor;

import org.aries.service.model.ServiceDescripterMap;


public class ServiceFactoryImpl implements ServiceFactory {

	//private static Log log = LogFactory.getLog(ServiceFactory.class);

	private static String DEFAULT_SERVICE_CLASS = "org.aries.DefaultService";

	private ClassLoader classLoader;
	
	private ServiceDescripterMap descripterMap;

	
	public ServiceFactoryImpl() {
		//nothing for now
	}

	public ClassLoader getClassLoader() {
		if (classLoader == null)
			classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	public void initialize(ServiceDescripterMap descripterMap) throws Exception {
		if (descripterMap == null)
			this.descripterMap = descripterMap;
		else this.descripterMap.add(descripterMap);
	}

//	public ServiceMap allocateServiceMap(ServiceDescripterMap Descripters) throws Exception {
//		return allocateServiceMap(Descripters.getServiceDescripters());
//	}
	
//	public ServiceMap allocateServiceMap(Map<String, ServiceDescripter> Descripters) throws Exception {
//		ServiceMap serviceMap = new ServiceMap();
//		Iterator<String> iterator = Descripters.keySet().iterator();
//		while (iterator.hasNext()) {
//			String serviceId = (String) iterator.next();
//			ServiceDescripter Descripter = Descripters.get(serviceId);
//			Object service = createServiceInstance(Descripter);
//			serviceMap.addService(serviceId, service);
//		}
//		return serviceMap;
//	}

//	public Object createServiceInstance(Service service) throws Exception {
//		String serviceId = ServiceUtil.getServiceId(service);
//		String className = service.getClazz();
//		Object serviceInstance = createServiceInstance(serviceId, className);
//		return serviceInstance;
//	}
//	
//	public Object createServiceInstance(ServiceDescripter serviceDescripter) throws Exception {
//		String serviceId = serviceDescripter.getServiceId();
//		String className = serviceDescripter.getClassName();
//		Object serviceInstance = createServiceInstance(serviceId, className);
//		return serviceInstance;
//	}

	public Object createServiceInstance(String serviceId, String className) throws Exception {
		if (className == null)
			className = DEFAULT_SERVICE_CLASS;
		
		//TODO Action creation taken out of here for now: tfisher
		//List<Action> actions = createActions(actionDescripters);
		//List<ProcessDescripter> processDescripters = serviceDescripter.getProcessDescripters();
		//ServiceConfiguration configuration = new ServiceConfiguration(serviceDescripter, processDescripters);
		//List<ActionDescripter> actionDescripters = serviceDescripter.getActionDescripters();
		//ServiceConfiguration configuration = new ServiceConfiguration(serviceDescripter, actionDescripters);
		//log.info("Service found: "+configuration.getServiceName()+", class="+serviceClass);

		Class<?> serviceClass = getClassLoader().loadClass(className);
		//TODO Get all constructor signatures from service descriptor (and use best one)
		//TODO Constructor<?> constructor = serviceClass.getConstructor(String.class);
		//TODO Object instance = constructor.newInstance(serviceId);
		//Constructor<?>[] constructors = serviceClass.getConstructors();
		Constructor<?> constructor = serviceClass.getConstructor();
		Object instance = constructor.newInstance();
		
		//if (instance instanceof AbstractService) {
		//	AbstractService service = (AbstractService) instance;
		//	service.setClassLoader(getClassLoader());
		//}
		return instance;
	}

}
