package nam.service.src.main.java;

import nam.model.Process;
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
public class ProcessStateObjectGenerator extends AbstractBeanGenerator {

	public ProcessStateObjectGenerator(GenerationContext context) {
		this.context = context;
	}

}
