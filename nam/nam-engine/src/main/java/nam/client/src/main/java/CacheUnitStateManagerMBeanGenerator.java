package nam.client.src.main.java;

import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates an MBean interface for the Process State Manager {@link Process} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class CacheUnitStateManagerMBeanGenerator extends AbstractBeanGenerator {

	public CacheUnitStateManagerMBeanGenerator(GenerationContext context) {
		this.context = context;
	}

}
