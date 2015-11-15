package nam.service.src.main.java;

import nam.model.Process;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates a Process State Manager Java class file given a {@link Process} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ProcessStateProcessorGenerator extends AbstractBeanGenerator {

	public ProcessStateProcessorGenerator(GenerationContext context) {
		this.context = context;
	}

}
