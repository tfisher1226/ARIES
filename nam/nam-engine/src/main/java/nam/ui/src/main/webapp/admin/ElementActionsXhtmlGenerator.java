package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementActionsXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementActionsXhtmlBuilder elementActionsXhtmlBuilder;
	
	
	public ElementActionsXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementActionsXhtmlBuilder = new ElementActionsXhtmlBuilder(context);
		super.initialize(project, module);
	}

	public void generate(Information information, Element element) throws Exception {
		generateFile(elementActionsXhtmlBuilder.buildFile(information, element));
	}

}
