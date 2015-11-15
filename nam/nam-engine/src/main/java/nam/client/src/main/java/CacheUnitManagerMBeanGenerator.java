package nam.client.src.main.java;

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
public class CacheUnitManagerMBeanGenerator extends AbstractBeanGenerator {

	public CacheUnitManagerMBeanGenerator(GenerationContext context) {
		this.context = context;
	}

}
