package nam.service.src.main.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nam.model.Channel;
import nam.model.Service;
import nam.model.TransportType;
import nam.model.util.ServiceUtil;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


/**
 * Builds a set of Service Client (i.e. client-side) Implementations {@link ModelClass} object 
 * given a {@link Service} Specification and a {@link TransportType} as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceListenerBuilder {

	private ServiceListenerRMIBuilder serviceListenerRMIBuilder;

	private ServiceListenerEJBBuilder serviceListenerEJBBuilder;

	private ServiceListenerJAXWSBuilder serviceListenerHTTPBuilder;
	
	private ServiceListenerJMSBuilder serviceListenerJMSBuilder;

	private GenerationContext context;

	
	public ServiceListenerBuilder(GenerationContext context) {
		serviceListenerRMIBuilder = new ServiceListenerRMIBuilder(context);
		serviceListenerEJBBuilder = new ServiceListenerEJBBuilder(context);
		serviceListenerHTTPBuilder = new ServiceListenerJAXWSBuilder(context);
		serviceListenerJMSBuilder = new ServiceListenerJMSBuilder(context);
		this.context = context;
	}

	public <T extends Service> List<ModelClass> buildClasses(List<T> services) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		Iterator<T> iterator = services.iterator();
		while (iterator.hasNext()) {
			T service = (T) iterator.next();
			modelClasses.addAll(buildClasses(service));
		}
		return modelClasses;
	}
	
	public List<ModelClass> buildClasses(Service service) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		List<Channel> channels = ServiceUtil.getChannels(service);
		Iterator<Channel> iterator = channels.iterator();
		while (iterator.hasNext()) {
			Channel channel = iterator.next();
			modelClasses.addAll(buildClasses(service, channel));
			modelClasses.addAll(buildClasses(ServiceUtil.getIncomingCallbacks(service)));
		}
		return modelClasses;
	}

	public List<ModelClass> buildClasses(Service service, Channel channel) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		modelClasses.addAll(buildClasses(service, channel, TransportType.RMI));
		modelClasses.addAll(buildClasses(service, channel, TransportType.EJB));
		modelClasses.addAll(buildClasses(service, channel, TransportType.HTTP));
		modelClasses.addAll(buildClasses(service, channel, TransportType.JMS));
		return modelClasses;
	}

	public List<ModelClass> buildClasses(Service service, Channel channel, TransportType transport) throws Exception {
		switch (transport) {
		case RMI: return serviceListenerRMIBuilder.buildClasses(service, channel);
		case EJB: return serviceListenerEJBBuilder.buildClasses(service, channel);
		case HTTP: return serviceListenerHTTPBuilder.buildClasses(service, channel);
		case JMS: return serviceListenerJMSBuilder.buildClasses(service, channel);
		default: throw new Exception("Unrecogniozed transport: "+transport);
		}
	}

}
