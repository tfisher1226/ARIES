package nam.model.src.main.java;

import nam.model.Service;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates a Bootstrap Interceptor Java class file given a {@link Service} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementUtilGenerator extends AbstractBeanGenerator {

	public ElementUtilGenerator(GenerationContext context) {
		this.context = context;
	}

}
