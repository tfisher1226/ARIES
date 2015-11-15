package org.aries.launcher.rmi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import nam.model.RmiTransport;

import org.aries.Assert;
import org.aries.Processor;
import org.aries.launcher.ServiceLauncher;
import org.aries.rmi.RMIMessageDispatcher;

import common.rmi.RMIService;
import common.rmi.RMIServiceImpl;


public class RMILauncher implements ServiceLauncher {
	
    /** This is to hold strong references to each server object. Prevents all levels of GC from reclaiming it. */
	private static final Map<String, RMIService> serviceReferenceHolder = new HashMap<String, RMIService>();


	private int port;

	private String serviceId;

	private Object instance;

	//private String address;
	
	private RMIService service;

	private RmiTransport rmiTransport;

	//private ServiceDescripter serviceDescripter;
	
	//private ChannelDescripter channelDescripter;


	public RMILauncher(RmiTransport rmiTransport) {
		this.rmiTransport = rmiTransport;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Object getServiceInstance() {
        return instance;
	}

	public void setServiceInstance(Object instance) {
        this.instance = instance;
	}

	@Override
	public String getAddress() {
		throw new RuntimeException("NOT YET");
		//return "";
	}
	
	
//	public ServiceDescripter getServiceDescripter() {
//		return serviceDescripter;
//	}
//
//	public void setServiceDescripter(ServiceDescripter serviceDescripter) {
//		this.serviceDescripter = serviceDescripter;
//	}
//
//	public ChannelDescripter getChannelDescripter() {
//		return channelDescripter;
//	}
//
//	public void setChannelDescripter(ChannelDescripter channelDescripter) {
//		this.channelDescripter = channelDescripter;
//	}
	
	
	public void launch() {
		validate();
		
    	try {
    		Processor<Serializable, Serializable> processor = new RMIMessageDispatcher(serviceId, instance);
			service = new RMIServiceImpl(port, serviceId, processor);
			serviceReferenceHolder.put(serviceId, service);
    		service.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

//	public void launch() {
//		validate();
//		
//    	try {
//    		String port = channelDescripter.getPort();
//    		int portNumber = Integer.parseInt(port);
//
//    		String serviceId = serviceDescripter.getServiceId();
//    		Processor<String, String> processor = new RMIMessageDispatcher(serviceId);
//			service = new RMIServiceImpl(portNumber, serviceId, processor);
//			serviceReferenceHolder.put(serviceId, service);
//    		service.initialize();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	protected Processor<T, T> createProcessor(String serviceId) {
//		return new Processor<T, T>() {
//
//			@Override
//			public T process(T object) {
//				return null;
//			}
//		};
//	}

	//validate inputs
	public void validate() {
		Assert.notNull(serviceId, "ServiceId not specified");
		Assert.isTrue(port != 0, "RMI port must be specified");
		//Assert.notNull(serviceDescripter, "Service Descripter not specified");
		//Assert.notNull(channelDescripter, "Channel Descripter not specified");
		//Assert.isTrue(!StringUtils.isEmpty(channelDescripter.getPort()), "RMI port must be specified");
		//Integer.parseInt(channelDescripter.getPort());
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
    
}
