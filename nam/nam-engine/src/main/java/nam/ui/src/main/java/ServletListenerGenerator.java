package nam.ui.src.main.java;

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
public class ServletListenerGenerator extends AbstractBeanGenerator {

	public ServletListenerGenerator(GenerationContext context) {
		this.context = context;
	}

}
