package nam.service.src.test.java;

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
public class ServiceClassTestBuilder extends AbstractBeanBuilder {

	//private ServiceClassRMITestBuilder serviceClassRMITestBuilder;

	//private ServiceClassEJBTestBuilder serviceClassEJBTestBuilder;

	private ServiceClassHTTPTestBuilder serviceClassHTTPTestBuilder;
	
	private ServiceClassJMSTestBuilder serviceClassJMSTestBuilder;

	
	public ServiceClassTestBuilder(GenerationContext context) {
		this.context = context;
		//serviceClassRMITestBuilder = new ServiceClassRMITestBuilder(context);
		//serviceClassEJBTestBuilder = new ServiceClassEJBTestBuilder(context);
		serviceClassHTTPTestBuilder = new ServiceClassHTTPTestBuilder(context);
		serviceClassJMSTestBuilder = new ServiceClassJMSTestBuilder(context);
	}

	public List<ModelClass> buildClasses(Service service) throws Exception {
		List<ModelClass> modelClasses = new ArrayList<ModelClass>();
		//modelClasses.add(buildClass(service, TransportType.RMI));
		//modelClasses.add(buildClass(service, TransportType.EJB));
		modelClasses.add(buildClass(service, TransportType.HTTP));
		modelClasses.add(buildClass(service, TransportType.JMS));
		return modelClasses;
	}

	public ModelClass buildClass(Service service, TransportType transport) throws Exception {
		switch (transport) {
		//case RMI: return serviceClassRMITestBuilder.build(service);
		//case EJB: return serviceClassEJBTestBuilder.build(service);
		case HTTP: return serviceClassHTTPTestBuilder.build(service);
		case JMS: return serviceClassJMSTestBuilder.build(service);
		default: throw new Exception("Unrecogniozed transport: "+transport);
		}
	}

}
