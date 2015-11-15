package nam.ui.src.main.webapp;

import nam.model.Module;
import nam.model.Project;
import aries.codegen.AbstractFileBuilder;
import aries.generation.engine.GenerationContext;


public abstract class AbstractXHTMLBuilder extends AbstractFileBuilder {

	public AbstractXHTMLBuilder(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) {
	}
	
	public void build(Project project, Module module) {
	}

}
