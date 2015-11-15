package nam.service.src.main.java;

import nam.model.Service;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates a Service Launcher Java class file given a {@link Service} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ServiceLauncherGenerator extends AbstractBeanGenerator {

	public ServiceLauncherGenerator(GenerationContext context) {
		this.context = context;
	}

}
