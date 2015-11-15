package nam.service.src.test.java;

import nam.model.Service;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


/**
 * Builds a simple, standalone Java application to launch a server-side 
 * service implementation object {@link ModelClass} given a {@link Service} 
 * Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceClassMainGenerator extends AbstractBeanGenerator {

	public ServiceClassMainGenerator(GenerationContext context) {
		this.context = context;
	}

}
