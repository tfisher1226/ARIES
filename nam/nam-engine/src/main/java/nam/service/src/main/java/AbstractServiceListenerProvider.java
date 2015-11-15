package nam.service.src.main.java;

import nam.model.Service;
import aries.codegen.AbstractBeanProvider;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


/**
 * Abstract class to provide source for a Service Implementation {@link ModelClass} object given a {@link Service} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public abstract class AbstractServiceListenerProvider extends AbstractBeanProvider {

	public AbstractServiceListenerProvider(GenerationContext context) {
		super(context);
	}

}
