package nam.ui.src.main.java.admin.data;

import nam.model.Element;
import aries.codegen.AbstractBeanGenerator;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelClass;


/**
 * Builds an Element Record Wizard Manager Generator {@link ModelClass} object given an {@link Element} Specification as input;
 * 
 * Model construction properties:
 * <ul>
 * <li>generateJavadoc</li>
 * </ul>
 * 
 * @author tfisher
 */
public class ElementWizardManagerGenerator extends AbstractBeanGenerator {

	public ElementWizardManagerGenerator(GenerationContext context) {
		this.context = context;
	}

}
