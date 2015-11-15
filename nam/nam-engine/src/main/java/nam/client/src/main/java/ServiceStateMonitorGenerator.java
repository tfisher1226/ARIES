package nam.client.src.main.java;

import nam.model.Service;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates a Service State module Java class file given a {@link Service} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceStateMonitorGenerator extends AbstractBeanGenerator {

	public ServiceStateMonitorGenerator(GenerationContext context) {
		this.context = context;
	}

}
