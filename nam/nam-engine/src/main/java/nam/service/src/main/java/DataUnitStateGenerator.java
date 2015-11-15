package nam.service.src.main.java;

import nam.model.Cache;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates a Cache State module Java class file given a {@link Cache} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class DataUnitStateGenerator extends AbstractBeanGenerator {

	public DataUnitStateGenerator(GenerationContext context) {
		this.context = context;
	}

}
