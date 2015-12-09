package nam.ui.src.main.webapp.admin;

import nam.model.Element;
import nam.model.Information;
import nam.model.Module;
import nam.model.Project;
import aries.generation.engine.GenerationContext;


public class ElementNameListTableXhtmlGenerator extends AbstractCompositionXHTMLGenerator {
	
	private ElementNameListTableXhtmlBuilder elementNameListTableXhtmlBuilder;
	
	
	public ElementNameListTableXhtmlGenerator(GenerationContext context) {
		super(context);
	}
	
	public void initialize(Project project, Module module) throws Exception {
		elementNameListTableXhtmlBuilder = new ElementNameListTableXhtmlBuilder(context);
		super.initialize(project, module);
	}
	
	public void generate(Information information, Element element) throws Exception {
		generateFile(elementNameListTableXhtmlBuilder.buildFile(information, element));
	}

}
