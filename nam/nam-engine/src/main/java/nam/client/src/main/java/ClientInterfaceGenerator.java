package nam.client.src.main.java;

import nam.model.Service;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates a Service Interface Java class file given a {@link Service} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ClientInterfaceGenerator extends AbstractBeanGenerator {

	public ClientInterfaceGenerator(GenerationContext context) {
		this.context = context;
	}

}
