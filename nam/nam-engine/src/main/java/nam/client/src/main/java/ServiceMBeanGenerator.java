package nam.client.src.main.java;

import nam.model.Service;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates an MBean interface for the Service Manager {@link Service} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceMBeanGenerator extends AbstractBeanGenerator {

	public ServiceMBeanGenerator(GenerationContext context) {
		this.context = context;
	}

}
