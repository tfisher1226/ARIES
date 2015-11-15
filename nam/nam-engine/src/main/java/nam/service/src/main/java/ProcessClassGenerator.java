package nam.service.src.main.java;

import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates a Process Java class file given a {@link Process} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ProcessClassGenerator extends AbstractBeanGenerator {

	public ProcessClassGenerator(GenerationContext context) {
		this.context = context;
	}

}
