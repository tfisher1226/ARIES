package nam.client.src.main.java;

import java.util.ArrayList;
import java.util.List;

import nam.model.Service;
import nam.model.TransportType;
import aries.codegen.AbstractBeanBuilder;
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
public class ClientClassBuilder extends AbstractBeanBuilder {

	private ClientClassRMIBuilder clientClassRMIBuilder;

	private ClientClassEJBBuilder clientClassEJBBuilder;

	private ClientClassJAXWSBuilder clientClassJAXWSBuilder;
	
	private ClientClassJMSBuilder clientClassJMSBuilder;

	
	public ClientClassBuilder(GenerationContext context) {
		this.context = context;
		clientClassRMIBuilder = new ClientClassRMIBuilder(context);
		clientClassEJBBuilder = new ClientClassEJBBuilder(context);
		clientClassJAXWSBuilder = new ClientClassJAXWSBuilder(context);
		clientClassJMSBuilder = new ClientClassJMSBuilder(context);
	}

	public List<ModelClass> buildClasses(Service service) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		modelClasses.add(buildClass(service, TransportType.RMI));
		modelClasses.add(buildClass(service, TransportType.EJB));
		modelClasses.add(buildClass(service, TransportType.HTTP));
		modelClasses.add(buildClass(service, TransportType.JMS));
		return modelClasses;
	}

	public ModelClass buildClass(Service service, TransportType transport) throws Exception {
		switch (transport) {
		case RMI: return clientClassRMIBuilder.build(service);
		case EJB: return clientClassEJBBuilder.build(service);
		case HTTP: return clientClassJAXWSBuilder.build(service);
		case JMS: return clientClassJMSBuilder.build(service);
		default: throw new Exception("Unrecogniozed transport: "+transport);
		}
	}

}
