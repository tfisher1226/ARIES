package nam.service.src.main.java;

import nam.model.Cache;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates a Cache State Manager Java class file given a {@link Cache} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class CacheUnitStateProcessorGenerator extends AbstractBeanGenerator {

	public CacheUnitStateProcessorGenerator(GenerationContext context) {
		this.context = context;
	}

}
