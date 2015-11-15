package nam.model.src.main.java;

import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


/**
 * Generates a Service Proxy (i.e. client-side) Implementation Java class file given a {@link ModelClass} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceFaultGenerator extends AbstractBeanGenerator {

	public ServiceFaultGenerator(GenerationContext context) {
		this.context = context;
	}

}
