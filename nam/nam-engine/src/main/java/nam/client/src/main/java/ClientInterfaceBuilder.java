package nam.client.src.main.java;

import nam.client.ClientLayerHelper;
import nam.model.Service;
import nam.service.src.main.java.ServiceInterfaceBuilder;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelInterface;


/**
 * Builds a Client Interface {@link ModelInterface} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ClientInterfaceBuilder extends ServiceInterfaceBuilder {

	public ClientInterfaceBuilder(GenerationContext context) {
		super(context);
	}

	protected String getPackageName(Service service) {
		return ClientLayerHelper.getClientPackageName(service);
	}

	protected String getInterfaceName(Service service) {
		return ClientLayerHelper.getClientInterfaceName(service);
	}
	
}
