package nam.ui.src.main.java.admin.data;

import nam.model.Service;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;


/**
 * Generates an Element Record Data Java class file given a {@link Service} object as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementDataManagerGenerator extends AbstractBeanGenerator {

	public ElementDataManagerGenerator(GenerationContext context) {
		this.context = context;
	}

}
