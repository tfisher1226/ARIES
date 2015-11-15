package org.aries.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import nam.model.Service;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.service.model.ServiceMap;
import org.aries.util.ReflectionUtil;


public class ServiceRepositoryImpl implements ServiceRepository {

	//private static Log log = LogFactory.getLog(ServiceRepository.class);

	//private ApplicationProfile applicationProfile;
	
	private ServiceMap serviceInstances;

	private Map<String, Object> serviceDescripters;

	private Object mutex = new Object();
	
	
	public ServiceRepositoryImpl() {
		serviceInstances = new ServiceMap();
		serviceDescripters = new HashMap<String, Object>();
	}

	@Override
	public Collection<String> getServiceIds() {
		synchronized (mutex) {
			ArrayList<String> list = new ArrayList<String>(serviceInstances.getServices().keySet());
			return list;
		}
	}
	
	@Override
	public Object getServiceDescripter(String serviceId) {
		synchronized (mutex) {
			return serviceDescripters.get(serviceId);
		}
	}

	@Override
	public Collection<Object> getServiceDescripters() {
		synchronized (mutex) {
			Collection<Object> list = Collections.unmodifiableCollection(serviceDescripters.values());
			return list;
		}
	}

//	@Override
//	public String getServiceMethod(String serviceId) {
//		ServiceDescripter serviceDescripter = getServiceDescripter(serviceId);
//		if (serviceDescripter != null && serviceDescripter.getMethodName() != null)
//			return serviceDescripter.getMethodName();
//		ProcessDescripter processDescripter = serviceDescripter.getProcessDescripters().get(0);
//		if (processDescripter != null && processDescripter.getProcessName() != null)
//			return processDescripter.getProcessName();
//		return ServiceDescripter.DEFAULT_SERVICE_METHOD;
//	}

	@Override
	public Collection<Object> getServiceInstances() {
		synchronized (mutex) {
			Collection<Object> list = Collections.unmodifiableCollection(serviceInstances.getServices().values());
			return list;
		}
	}

	@Override
	public Object getServiceInstance(String serviceId) {
		synchronized (mutex) {
			Object serviceInstance = serviceInstances.getService(serviceId);
	        if (serviceInstance == null) {
				try {
					serviceInstance = BeanContext.get(serviceId);
					serviceInstances.addService(serviceId, serviceInstance);
				} catch (Throwable e) {
					//ignore
				}
			}
	        Assert.notNull(serviceInstance, "Service not found: "+serviceId);
			return serviceInstance;
		}
	}

	@Override
	public void addServiceInstance(String serviceId, Object serviceDescripter, Object serviceInstance) {
		synchronized (mutex) {
			serviceInstances.addService(serviceId, serviceInstance);
			serviceDescripters.put(serviceId, serviceDescripter);
		}
	}
	
	@Override
	public void removeServiceInstance(String serviceId) {
		synchronized (mutex) {
			serviceInstances.removeService(serviceId);
		}
	}

	public void close() {
		synchronized (mutex) {
			Map<String, Object> services = serviceInstances.getServices();
			Iterator<Object> iterator = services.values().iterator();
			while (iterator.hasNext()) {
				Object service = iterator.next();
				close(service);
			}
		}
	}

    //TODO move this to shared class
	protected void close(Object service) {
		try {
	    	String methodName = "close";
	    	Class<?>[] parameterTypes = new Class<?>[] {};
	    	Object[] parameterValues = new Object[] {};
	    	Class<?> resultType = void.class;
			Method method = ReflectionUtil.findMethod(service.getClass(), methodName, parameterTypes, resultType);
			ReflectionUtil.invokeMethod(service, method, parameterValues);
		} catch (Throwable e) {
			//ignore
		}
	}
	
}

