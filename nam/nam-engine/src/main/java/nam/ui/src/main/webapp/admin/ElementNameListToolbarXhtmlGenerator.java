package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementNameListToolbarXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementNameListToolbarXhtmlBuilder elementNameListToolbarXhtmlBuilder;
	
	
	public ElementNameListToolbarXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementNameListToolbarXhtmlBuilder = new ElementNameListToolbarXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementNameListToolbarXhtmlBuilder.buildFile(information, element));
	}

}
