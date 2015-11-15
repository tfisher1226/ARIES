package nam.service.src.test.java;

import nam.model.Unit;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


/**
 * Provides the source code for a JMS Listener CIT {@link ModelClass} object given a {@link Unit} Specification as input;
 * 
 * @author tfisher
 */
public class ServiceListenerForJMSCITProvider extends AbstractServiceJMSListenerCITProvider {

	public ServiceListenerForJMSCITProvider(GenerationContext context) {
		super(context);
	}


}
