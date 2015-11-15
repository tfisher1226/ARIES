package nam.service.src.test.java;

import nam.model.Service;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


/**
 * Builds a simple, standalone Java application to launch a client request 
 * via the Service Proxy (i.e. client-side) Implementation {@link ModelClass} 
 * object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceHandlerUnitTestGenerator extends AbstractBeanGenerator {

	public ServiceHandlerUnitTestGenerator(GenerationContext context) {
		this.context = context;
	}

}
