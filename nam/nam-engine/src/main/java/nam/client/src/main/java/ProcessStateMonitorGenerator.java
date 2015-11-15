package nam.client.src.main.java;

import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates a Process State module Java class file given a {@link Process} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ProcessStateMonitorGenerator extends AbstractBeanGenerator {

	public ProcessStateMonitorGenerator(GenerationContext context) {
		this.context = context;
	}

}
