package nam.service.src.test.java;

import nam.model.Service;
import nam.model.Unit;
import nam.service.ServiceLayerHelper;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


/**
 * Builds a DataUnit State Manager CIT {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * <h3>Properties</h3>
 * The following properties can be used to configure execution of this builder: 
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * <h3>Dependencies</h3>
 * Execution of this builder must come after the following builders: 
 * <ul>
 * <li>ProcessClassBuilder</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceListenerForJMSCITBuilder extends AbstractServiceJMSListenerCITBuilder {

	public ServiceListenerForJMSCITBuilder(GenerationContext context) {
		super(context);
	}

	@Override
	protected String getFixtureClassName(Service service) {
		return getBaseClassName(service) + "ListenerForJMS";
	}

	@Override
	protected String getTestClassName(Service service) {
		return getBaseClassName(service) + "ListenerForJMSCIT";
	}
	
	@Override
	protected String getTestParentClassName() {
		return "org.aries.tx.AbstractJMSListenerArquillionTest";
	}

	public String getBaseClassName(Service service) {
		return ServiceLayerHelper.getServiceInterfaceName(service);
	}

	@Override
	protected AbstractServiceJMSListenerCITProvider createProvider(GenerationContext context) {
		return new ServiceListenerForJMSCITProvider(context);
	}

}
