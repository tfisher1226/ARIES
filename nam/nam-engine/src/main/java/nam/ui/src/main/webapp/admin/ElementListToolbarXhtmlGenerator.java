package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementListToolbarXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementListToolbarXhtmlBuilder elementListToolbarXhtmlBuilder;
	
	
	public ElementListToolbarXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementListToolbarXhtmlBuilder = new ElementListToolbarXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementListToolbarXhtmlBuilder.buildFile(information, element));
	}

}
