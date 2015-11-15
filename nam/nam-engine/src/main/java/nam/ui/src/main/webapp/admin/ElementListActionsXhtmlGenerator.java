package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementListActionsXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementListActionsXhtmlBuilder elementListActionsXhtmlBuilder;
	
	
	public ElementListActionsXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementListActionsXhtmlBuilder = new ElementListActionsXhtmlBuilder(context);
		super.initialize(project, module);
	}

	public void generate(Information information, Element element) throws Exception {
		generateFile(elementListActionsXhtmlBuilder.buildFile(information, element));
	}

}
